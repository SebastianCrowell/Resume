"""Comp521 support module with tools for downloading assignments, lecture notes, and exams
and uploading student submissions.

Gary Bishop Fall 2020
"""

import urllib
import urllib.request
import os
import os.path as osp
import json
import time
import hashlib
import pickle
import io
import random
import getpass
import inspect
import sqlite3
import threading
import socket
from datetime import datetime

Site = "https://comp521-1sp21.cs.unc.edu"
UploadSite = Site

#############################
#
# functions for submitting assignments
#
#############################

ATTEMPTS = 10


def fileHash(filename):
    """Compute the checksum to be sure the file is what we expect"""
    BLOCKSIZE = 65536
    hasher = hashlib.sha1()
    with open(filename, "rb") as fp:
        buf = fp.read(BLOCKSIZE)
        while len(buf) > 0:
            hasher.update(buf)
            buf = fp.read(BLOCKSIZE)
    return hasher.hexdigest()


def pushNotebook(name, uuid, url="/notebook"):
    """Upload the notebook to our server"""

    if not name.endswith(".ipynb"):
        fname = name + ".ipynb"
    else:
        fname = name
    try:
        book = open(fname, "rb").read()
    except IOError:
        raise UserWarning("Notebook %s not found." % fname)
    check = fileHash(fname)
    try:
        assignment = expected["_assignment"]
    except KeyError:
        raise UserWarning(
            "Missing assignment, you must run your notebook before submitting it."
        )

    data = {
        "filename": name,
        "book": book,
        "token": uuid,
        "assignment": assignment,
        "check": check,
    }

    postdata = urllib.parse.urlencode(data)
    postdata = postdata.encode("UTF-8")  # data should be bytes
    req = urllib.request.Request(UploadSite + url, postdata)
    # try to post it to the server
    for i in range(10):
        try:
            resp = urllib.request.urlopen(req)
        except urllib.error.URLError:
            raise UserWarning(
                "Submission failed: are you online? Make sure you have wireless on"
            )
        except urllib.error.HTTPError as e:
            print(e)
            raise
        if resp.getcode() == 200:
            break
        time.sleep(0.1 * i)
    else:
        code = resp.getcode()
        msg = resp.read()
        raise UserWarning('upload failed code={} msg="{}"'.format(code, msg))


##################################################
#
# functions for checking student answers
#
##################################################


def unpack(O):
    """A helper to remove unnecessary wrapping from db results."""
    if isinstance(O, (list, tuple)):
        if len(O) == 1:
            return unpack(O[0])
        return [unpack(o) for o in O]
    return O


def check_query(tag, query, dbs, params=[], **kwargs):
    """Run the query, unpack the result, print it, and check it"""
    for i, db in enumerate(dbs):
        points = kwargs.get("points", 0) if i == len(dbs) - 1 else 0
        t = threading.Timer(30, db.interrupt)
        t.start()
        r = db.execute(query, params).fetchall()
        t.cancel()
        Tag = tag + "-%d" % i
        r = unpack(r)
        print(Tag, r, "\n")
        if check(Tag, r, **dict(kwargs, points=points, test=True)) is False:
            break


def check_float(tag, given, ev, extra):
    """Compare floats for approximate equality"""
    rtol = extra.get("relative_tolerance", 1e-5)
    atol = extra.get("absolute_tolerance", 1e-8)
    if not isinstance(given, (float, int)):
        print(tag, "incorrect type")
        print(" expected float")
        return 0.0
    OK = abs(given - ev) < atol
    if not OK:
        print(tag, "incorrect")
        print("  expected", ev)
    return float(OK)


def check_relation(tag, value, ev, extra):
    """Compare Dee Relations"""
    if not isinstance(value, Dee.Relation):
        print(tag, "incorrect type")
        # Helpful
        print("expected Dee.Relation")
        # _Helpful
        return 0.0
    OK = value == ev
    if not OK:
        print(tag, "incorrect")
        # Helpful
        print("expected", ev)
        # _Helpful
    return float(OK)


def check_choice(tag, value, ev, extra):
    choice = extra["choice"]

    if not isinstance(value, str):
        print(tag, "answer should be string")
        return 0

    out = False
    for c in value.upper():
        if c not in choice:
            print(tag, "letter '%s' is not among the choices" % c)
            out = True
    if out:
        return 0
    return 1


def listit(t):
    """convert nested list,tuples into nested lists"""

    return list(map(listit, t)) if isinstance(t, (list, tuple)) else t


def check_list(tag, value, ev, extra):
    ev = listit(ev)
    value = listit(value)
    if "sort" in extra:
        ev = sorted(ev)
        value = sorted(value)

    OK = ev == value
    if not OK:
        print(tag, "incorrect")
        print("expected", ev)

    return float(OK)


try:
    from grading import record_grade
except ImportError:

    def record_grade(expected):
        pass


try:
    from solution import start, check, report
except ImportError:

    # contains the expected answers
    expected = {}

    def test_online(host="8.8.8.8", port=53, timeout=1):
        """Test to see if the user is online"""
        try:
            socket.setdefaulttimeout(timeout)
            socket.socket(socket.AF_INET, socket.SOCK_STREAM).connect((host, port))
            return True
        except Exception as ex:
            return False

    def start(assignment):
        """Initialize expected values for checking a student submission"""
        pname = assignment + ".pickle"
        expected.update(pickle.load(open(pname, "rb")))
        if (
            expected.get("_exam", False)
            and time.time() - osp.getmtime(pname) < 3 * 60 * 60
        ):
            expected["_monitor"] = True
        return check, report

    def check(tag, value, *args, **kwargs):
        """Provide feedback on a single value"""

        if expected.get("_monitor") and test_online():
            import IPython.display as ipd

            ipd.display(
                ipd.HTML(
                    """
<p style="background:pink;height:8em;display:flex;align-items:center">
You appear to be online.  Disable wireless before continuing. %s</p>"""
                    % datetime.now().isoformat()
                )
            )

        assert tag in expected
        e = expected[tag]

        dv = describe_answer(value)
        score = 1.0
        extra = e["extra"]

        if e["description"] != dv:
            print(tag, "incorrect type")
            print("your answer type is", dv)
            print("expected type is", e["description"])
            score = 0.0

        elif "hash" in e:
            if "choice" in extra:
                score = 1 if value in extra["choice"] else 0
                e["correct"] = score
                if score == 0:
                    print(tag, "not answered")
                else:
                    print(tag, "answered")
                return

            if "sort" in extra:
                value = sorted(value)
            hv = hash_answer(value, extra)
            if hv != e["hash"]:
                score = 0.0
                print(tag, "incorrect")

        else:
            ev = e["value"]
            if isinstance(ev, (list, tuple)) and isinstance(value, (list, tuple)):
                score = check_list(tag, value, ev, extra)

            elif isinstance(ev, float):
                score = check_float(tag, value, ev, extra)

            elif value == ev:
                pass

            else:
                print(tag, "incorrect")
                print("  expected", ev)
                score = 0.0

        e["correct"] = score

        if score == 1.0:
            print(tag, "appears correct")

        elif score > 0:
            print(tag, "partially correct")

        if kwargs.get("test") and score != 1.0:
            return False

    def tagSort(tags):
        return sorted(
            tags,
            key=lambda tag: "".join(
                [
                    s.isdigit() and "%02d" % int(s) or s
                    for s in re.findall(r"\d+|\D+", tag)
                ]
            ),
        )

    def report(author, extra):
        """Summarize the student's performance"""
        expected["_score"] = 0.0
        correct = 0
        problems = 0
        answered = 0
        choice = 0
        choice_points = 0
        total_choice_points = 0
        points = 0
        max_points = 0
        for tag in tagSort(expected.keys()):
            if tag.startswith("_"):
                continue
            problems += 1
            c = expected[tag]["correct"]
            if "choice" in expected[tag]["extra"]:
                choice += 1
                if c > 0:
                    answered += 1
                    choice_points += expected[tag]["points"]
                else:
                    print(tag, "not answered")
                total_choice_points += expected[tag]["points"]
                max_points += expected[tag]["points"]
                continue

            if c > 0:
                correct += c
                points += expected[tag]["points"] * c
                if c < 1:
                    print(tag, "partially incorrect")
            else:
                print(tag, "incorrect")
            max_points += expected[tag]["points"]
        if "_author" in expected and author == expected["_author"]:
            print(
                "You must fill in your onyen into the author variable at the top of the script"
            )
            return
        if "_exam" in expected and expected["_exam"]:
            if not extra:
                print("You must type your name as the value of the pledge variable")
                return
            else:
                print("  Pledged on my honor:", extra)
                print("   ", getpass.getuser())

        elif "_collaborators" in expected and extra == expected["_collaborators"]:
            print("You must fill in the collaborators variable")
            return
        else:
            print("  Collaborators:", extra)
        print("Report for", author)
        if total_choice_points > 0:
            print(
                "  %d of %d answered for up to %d points"
                % (answered, choice, choice_points)
            )
        print(
            "  %d of %d appear correct, %d of %d points"
            % (correct, problems - choice, points, max_points - total_choice_points)
        )
        expected["_score"] = points

        record_grade(expected)


from collections import OrderedDict
import re


def describe_answer(o):
    """describe the type of an object in English"""

    def wrap(d):
        """Enclose description in parenthesis if it contains comma or and."""
        if ", " in d or " and " in d:
            return "(" + d + ")"
        else:
            return d

    def and_list(items):
        """comma separated list with and at the end"""
        if len(items) <= 2:
            return " and ".join(items)
        return ", ".join(items[:-1]) + ", and " + items[-1]

    def describe_sequence(o, typ, memo):
        """describe a list or tuple"""
        if len(o) == 0:
            return "empty " + typ
        if id(o) in memo:
            return "[...]"
        memo.add(id(o))
        et = [wrap(describe_any(e, memo)) for e in o]
        uet = list(OrderedDict.fromkeys(et))
        if len(uet) == 1:
            et = "{} {}".format(len(et), uet[0])
        else:
            et = and_list(et)
        return typ + " of " + et

    def describe_dict(o, memo):
        if len(o) == 0:
            return "empty dict"
        return "dictionary"
        if id(o) in memo:
            return "{...}"
        memo.add(id(o))
        it = [(k + ":" + wrap(describe_any(v, memo))) for k, v in o.items()]
        uit = list(OrderedDict.fromkeys(it))
        if len(uit) == 1:
            it = "{} {}".format(len(o), uit[0])
        else:
            it = and_list(it)
        return "dictionary of {}".format(it)

    def describe_array(a):
        """Describe a numpy array in English"""
        if issubclass(a.dtype.type, np.integer):
            t = "integer"
        elif issubclass(a.dtype.type, np.float):
            t = "float"
        elif issubclass(a.dtype.type, np.complex):
            t = "complex"
        elif issubclass(a.dtype.type, np.bool_):
            t = "boolean"
        else:
            t = str(a.dtype)
        s = " x ".join(str(i) for i in a.shape)
        if s == "0":
            return "empty array of " + t
        return "array of {} {}".format(s, t)

    def describe_plot(o):
        """describe a plot"""
        o = figureState(o)
        terms = []
        nlines = len(o["lines"])
        if nlines > 0:
            terms.append("{} line{}".format(nlines, "s"[nlines == 1 :]))
        nbars = o["bars"].shape[-1]
        if nbars > 0:
            terms.append("{} bar{}".format(nbars, "s"[nbars == 1 :]))
        for t in ["title", "xlabel", "ylabel"]:
            if o[t]:
                terms.append(t)
        if len(terms) == 0:
            return "empty plot"
        else:
            return "plot with " + and_list(terms)

    def describe_function(f):
        return "function with %d parameter" % len(inspect.signature(f).parameters)

    def describe_any(o, memo):
        if isinstance(o, str):
            return "string"
        if isinstance(o, bool):
            return "boolean"
        if isinstance(o, int):
            return "integer"
        if isinstance(o, float):
            return "float"
        if o is None:
            return "None"
        if isinstance(o, list):
            return describe_sequence(o, "list", memo)
        if isinstance(o, tuple):
            return describe_sequence(o, "tuple", memo)
        if isinstance(o, dict):
            return describe_dict(o, memo)
        if callable(o):
            return describe_function(o)
        return str(type(o))

    desc = describe_any(o, set())

    def pluralize(m):
        n = int(m.group(1))
        w = m.group(3)
        if n != 1:
            if w == "dictionary":
                w = "dictionaries"
            else:
                w = w + "s"
        return m.group(1) + " " + m.group(2) + w

    desc = re.sub(
        r"(\d+) (\(?)(tuple|list|dictionary|string|integer|float|boolean|plot|parameter)",
        pluralize,
        desc,
    )
    return desc


import hashlib


def hash_answer(o, extra):
    """return a hash to represent the answer in equality tests"""
    precision = extra.get("precision", 5)

    def str_answer(o, memo):
        """compute a hash for an answer"""
        if id(o) in memo:
            return "..."
        memo.add(id(o))
        if isinstance(o, float):
            s = "float" + format(o, ".{}e".format(precision))
        elif isinstance(o, (list, tuple)):
            s = (
                str(type(o).__name__)
                + "("
                + ",".join([str_answer(i, memo) for i in o])
                + ")"
            )
        elif isinstance(o, dict):
            s = "dict" + str_answer(sorted(o.items()), memo)
        elif "choice" in extra:
            s = str(o in extra["choice"])
        else:
            s = str(o)
        return s

    sa = str_answer(o, set())

    return hashlib.md5(str_answer(o, set()).encode("utf-8")).hexdigest()

