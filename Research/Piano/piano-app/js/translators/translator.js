function Translator() {
    this.symbn = [];
    this.chord = [];
    this.time = [];
    this.defaults = function () {
        this.symbn = [0];
        this.chord = [""];
        this.time = [0, 0]
    };
    this.choose = function () {
        $("s1").value = mus_sheet[$("sheet_choose").selectedIndex][1];
        win_fnc.stop();
        loc.set("sheet_choose", $("sheet_choose").selectedIndex)
    };
    this.compile = function () {
        for (var t = $("s1").value, r, n = 0, i = 0, u; t.length > n;) switch (t[n]) {
            case "{":
                n++;
                this.time[this.time.length - 1] = this.time[this.time.length - 1] + parseInt(t.substring(n, t.indexOf("}", n)));
                n = t.indexOf("}", n) + 1;
                break;
            case "[":
                for (n != 0 && t[n - 1] == "]" && (this.time[this.time.length - 1] = this.time[this.time.length - 1] + 200), r = t.substring(n + 1, t.indexOf("]", n)), i = 0, n++; r.length > i;) switch (r[i]) {
                    case " ":
                    case "\n":
                        this.time[this.time.length - 1] = this.time[this.time.length - 1] + 75;
                        i++;
                        n++;
                        break;
                    case "|":
                        this.time[this.time.length - 1] = this.time[this.time.length - 1] + 105;
                        i++;
                        n++;
                        break;
                    default:
                        notes.w_n.indexOf(t[n]) != -1 || notes.b_n.indexOf(t[n]) != -1 ? (i != 0 && r[i - 1] != " " && r[i - 1] != "\n" && r[i - 1] != "|" && (this.time[this.time.length - 1] = this.time[this.time.length - 1] + 10), this.time[this.time.length] = this.time[this.time.length - 1], notes.w_n.indexOf(t[n]) != -1 ? this.chord[this.chord.length] = "a" + notes.w_c[notes.w_n.indexOf(r[i])] : notes.b_n.indexOf(t[n]) != -1 && (this.chord[this.chord.length] = "b" + notes.b_c[notes.b_n.indexOf(r[i])]), this.symbn[this.symbn.length] = ++n, i++) : n++
                }
                n++;
                i = 0;
                break;
            case " ":
            case "\n":
                this.time[this.time.length - 1] = this.time[this.time.length - 1] + 350;
                n++;
                break;
            case "|":
                this.time[this.time.length - 1] = this.time[this.time.length - 1] + 500;
                n++;
                break;
            default:
                notes.w_n.indexOf(t[n]) != -1 || notes.b_n.indexOf(t[n]) != -1 ? (n != 0 && t[n - 1] != " " && t[n - 1] != "\n" && t[n - 1] != "|" && t[n - 1] != "}" && (this.time[this.time.length - 1] = this.time[this.time.length - 1] + 200), this.time[this.time.length] = this.time[this.time.length - 1], notes.w_n.indexOf(t[n]) != -1 ? this.chord[this.chord.length] = "a" + notes.w_c[notes.w_n.indexOf(t[n])] : notes.b_n.indexOf(t[n]) != -1 && (this.chord[this.chord.length] = "b" + notes.b_c[notes.b_n.indexOf(t[n])]), this.symbn[this.symbn.length] = ++n) : n++
        }
        return this.time.length != 0 && (this.time.length = this.time.length - 1), u = [this.time, this.chord, this.symbn], this.defaults(), u
    }
}
var trans = new Translator;
trans.defaults();