function ReTranslator() {
    this.compile = function () {
        var e = "",
            t = [
                [500, 105, "|"],
                [350, 75, " "],
                [200, 10, ""],
                [1, 1, ""]
            ],
            f = [""],
            h = 0,
            l = 0,
            c = !1,
            n, i;
        for ($("s1").value = "", i = 1; i < piano.time.length; i++) {
            var u = "",
                s, o = 0,
                a = 0,
                r = piano.time[i] - piano.time[i - 1];
            if (r < 200) {
                for (h != 0 && (l = 1), f[f.length] = "", f[f.length - 1 - l] += "["; r < 200 && i < piano.time.length;) {
                    s = "";
                    i++;
                    n: for (n = 0; n < t.length - 1; n++)
                        while (r >= t[n][1]) {
                            if (parseInt(r / t[n][1]) == 1 && r % t[n][1] > r % t[n + 1][1]) {
                                r = r - t[n + 1][1];
                                s += t[n + 1][2];
                                continue n
                            }
                            r = r - t[n][1];
                            s += t[n][2];
                            t[n][2] == "|" && a++
                        }
                    f[f.length] = s;
                    r = piano.time[i] - piano.time[i - 1]
                }
                f[f.length - 1] = "]";
                h++;
                c = !0
            } else c = !1;
            n: for (n = 0; n < t.length - 1; n++)
                while (r >= t[n][0]) {
                    if (parseInt(r / t[n][0]) == 1 && r % t[n][0] > r % t[n + 1][0]) {
                        r = r - t[n + 1][0];
                        u += t[n + 1][2];
                        continue n
                    }
                    r = r - t[n][0];
                    u += t[n][2];
                    t[n][2] == "|" && o++
                }
            if (u.indexOf("|  ") != -1) {
                for (u = " ", n = 0; n < o; n++) u += "|";
                u += " "
            } else if (u.indexOf("|| ") != -1) {
                for (u = "", n = 0; n < o; n++) u += "|";
                u = u.substr(0, parseInt(o / 2)) + " " + u.substr(parseInt(o / 2))
            }
            c ? f[f.length - 1] += u : f[f.length] = u
        }
        if ($("expert").checked) {
            for (i = 1; i < piano.chord.length; i++) e += "{" + (piano.time[i] - piano.time[i - 1]) + "}" + notes.w_n[notes.w_c.indexOf(parseInt(piano.chord[i].substr(1)))];
            e = e.substr(3)
        } else {
            for (i = 1; i < piano.chord.length; i++) e += f[i] + notes.w_n[notes.w_c.indexOf(parseInt(piano.chord[i].substr(1)))];
            e.lastIndexOf("[") > e.lastIndexOf("]") && (e += "]")
        }
        e.indexOf("]") == 2 && (e = e[1] + e.substr(3));
        $("s1").value = e;
        h = 0
    }
}
var retrans = new ReTranslator;
trans.defaults();