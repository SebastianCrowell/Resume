document.onkeydown = function (n) {
    var t = window.event ? n.keyCode : n.which;
    n.shiftKey ? notes.b_c.indexOf(t) != -1 && piano.music("b" + t, 3) : notes.w_c.indexOf(t) != -1 && piano.music("a" + t, 1)
};
document.onmouseup = function () {
    piano.isMouseDown = !1
};
document.getElementById("piano").onmousedown = function () {
    piano.isMouseDown = !0
};