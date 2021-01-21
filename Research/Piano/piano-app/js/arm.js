function $(n) {
    return document.getElementById(n)
}

function Loc_St() {
    this.get = function (n) {
        return localStorage.getItem(n)
    };
    this.set = function (n, t) {
        return localStorage.setItem(n, t)
    }
}

function random(n, t) {
    return t == undefined && (t = n, n = 0), Math.floor(Math.random() * (t - n + 1)) + n
}

function select(n, t, i) {
    if (t == i && t == undefined) return [$(n).selectionStart, $(n).selectionEnd];
    t == -1 && i == undefined && (t = $(n).value.length, i = t);
    i == undefined && t != undefined && (i = $(n).value.length);
    t > i && (t = t - i, i = t + i, t = i - t);
    $(n).selectionStart = t;
    $(n).selectionEnd = i;
    $(n).focus()
}
var loc = new Loc_St,
    drag = function () {
        return {
            startMoving: function (n, t) {
                n = n || window.event;
                var f = n.clientX,
                    e = n.clientY,
                    u = $(t),
                    i = u.style.top,
                    r = u.style.left;
                i = i.replace("px", "");
                r = r.replace("px", "");
                diffX = f - r;
                diffY = e - i;
                document.onmousemove = function (n) {
                    n = n || window.event;
                    var i = n.clientX,
                        r = n.clientY,
                        u = i - diffX,
                        f = r - diffY;
                    drag.move(t, u, f)
                }
            },
            stopMoving: function (n) {
                var t = $(n);
                document.onmousemove = function () { };
                loc.set(n, [t.style.visibility, loc.get(n).split(",")[1], t.style.left, t.style.top])
            },
            move: function (n, t, i) {
                var r = $(n);
                r.style.left = t + "px";
                r.style.top = i + "px"
            }
        }
    }();