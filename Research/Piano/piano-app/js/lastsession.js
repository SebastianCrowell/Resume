function Restoring() {
    this.Null = function (n) {
        return loc.get(n) != null ? !0 : !1
    };
    this.defaults = function () {
        var n = $("win");
        this.Null("exp") && ($("expert").checked = loc.get("exp") == !1 ? !1 : !0);
        rest.Null("sheet_choose") && ($("sheet_choose").selectedIndex = loc.get("win").split(",")[1] == "sheet" ? loc.get("sheet_choose") : 0, trans.choose());
        this.Null("win") ? (n.style.visibility = loc.get("win").split(",")[0] == "hidden" ? "visible" : "hidden", n.style.left = loc.get("win").split(",")[2], n.style.top = loc.get("win").split(",")[3], win_fnc.viewer(loc.get("win").split(",")[1])) : loc.set("win", ["visible", "sheet"]);
        this.Null("assist") && (piano.as = !loc.get("assist"), piano.assist());
        this.Null("bg") && (piano.col = loc.get("bg") != "NaN" ? parseInt(loc.get("bg")) - 1 : 3, piano.styler())
    }
}
var rest = new Restoring;
rest.defaults();