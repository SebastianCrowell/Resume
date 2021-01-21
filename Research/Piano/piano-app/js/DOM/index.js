function index() {
    for (var i, n, r = [
            ["a", notes.w_c],
            ["b", notes.b_c]
    ], u = $("white"), f = $("black"), e = $("audio"), t = 0; t < notes.w_c.length; t++) n = document.createElement("div"), n.id = "a" + notes.w_c[t] + "d", n.className = "w", n.setAttribute("onmousedown", "piano.music('a" + notes.w_c[t] + "',1)"), n.setAttribute("onmouseover", "piano.chkdsk('a" + notes.w_c[t] + "',1)"), u.appendChild(n);
    for (t = 0; t < notes.b_c.length; t++) n = document.createElement("div"), n.id = "b" + notes.b_c[t] + "d", n.className = "b", n.setAttribute("onmousedown", "piano.music('b" + notes.b_c[t] + "',3)"), n.setAttribute("onmouseover", "piano.chkdsk('b" + notes.b_c[t] + "',3)"), n.style.left = notes.b_coo[t] + "px", f.appendChild(n);
    for (t = 0; t < 2; t++)
        for (i = 0; i < r[t][1].length; i++) n = document.createElement("audio"), n.id = r[t][0] + r[t][1][i], n.canPlayType("audio/mpeg;") ? (n.type = "audio/mpeg", n.src = "notes\\" + n.id + ".mp3") : (n.type = "audio/mpeg", n.src = "notes\\" + n.id + ".ogg"), $("audio").appendChild(n)
}
index();