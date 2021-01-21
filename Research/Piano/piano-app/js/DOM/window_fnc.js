function Windows_FNC() {
    this.coos = ["0px", "5px"];
    this.close = (n, t, i) => {
        const expertElement = $('expert');
        expertElement.checked && (expertElement.checked = !1, loc.set('exp', ''));
        rec.exp();
        this.viewer(n, 1);
        this.stop();
        $('sheet_stop').style.visibility = 'hidden';
        $('sheet_stop_sep').style.visibility = 'hidden';
        $('sheet_choose').selectedIndex = 0;
        $('s1').value = '';

        const winElement = $('win');
        winElement.style.left = t;
        winElement.style.top = i;
        n === 'recorder' && ($('composition').value = '')
    };
    this.stop = () => {
        piano.stp = !0;
        $('sheet_stop').style.visibility = 'hidden';
        $('sheet_stop_sep').style.visibility = 'hidden'
    };
    this.viewer = (n, t) => {
        var i = $('win'),
            r = $(n);
        i.style.visibility === 'visible' && (n === loc.get('win').split(',')[1] || t) ? (i.style.visibility = 'hidden', $('hh_vis').style.visibility = 'hidden', $('hh_sep').style.visibility = 'hidden', $('recplay').style.visibility = 'hidden', $('expbar').style.visibility = 'hidden', $('rec_tb2').style.visibility = 'hidden', $('rec_tb3').style.visibility = 'hidden', $('sheet_choose').style.visibility = 'hidden', $('sheet_stop').style.visibility = 'hidden', $('sheet_stop_sep').style.visibility = 'hidden') : (i.style.visibility === 'hidden' || n != loc.get('win').split(',')[1]) && (i.style.visibility = 'visible', piano.play_pause && ($('sheet_stop').style.visibility = 'visible', $('sheet_stop_sep').style.visibility = 'visible'), n === 'sheet' ? (n != loc.get('win').split(',')[1] && ($('sheet_choose').selectedIndex = rest.Null('sheet_choose') ? loc.get('sheet_choose') : 0, trans.choose()), $('s1').style.top = '62px', $('s1').style.height = '325px', $('s1').readOnly = !0, $('rec_bkg_title').innerHTML = 'Sheets', $('hh_vis').style.visibility = 'hidden', $('hh_sep').style.visibility = 'hidden', $('recplay').style.visibility = 'hidden', $('expbar').style.visibility = 'hidden', $('rec_tb2').style.visibility = 'hidden', $('rec_tb3').style.visibility = 'hidden', $('sheet_choose').style.visibility = 'visible') : (n != loc.get('win').split(',')[1] && (piano.stp = !0, $('s1').value = ''), $('s1').style.top = '92px', $('s1').style.height = '270px', $('s1').readOnly = !1, $('rec_bkg_title').innerHTML = 'Recorder', $('expert').checked && ($('hh_vis').style.visibility = 'visible', $('hh_sep').style.visibility = 'visible'), $('recplay').style.visibility = 'visible', $('expbar').style.visibility = 'visible', $('rec_tb2').style.visibility = 'visible', $('rec_tb3').style.visibility = 'visible', $('sheet_choose').style.visibility = 'hidden'));
        loc.set('win', [i.style.visibility, n, i.style.left, i.style.top])
    };
    this.hider = function() {
        $('sheet_play').value = '► Play';
        $('sheet_stop').style.visibility = 'hidden';
        $('sheet_stop_sep').style.visibility = 'hidden'
    }
}

function Recorder() {
    this.tmp = '';
    var n = $('s1');
    this.exp = function() {
        var n = $('expert');
        n.checked ? ($('hh_vis').style.visibility = 'hidden', $('hh_sep').style.visibility = 'hidden', n.checked = !1, loc.set('exp', '')) : ($('hh_vis').style.visibility = 'visible', $('hh_sep').style.visibility = 'visible', n.checked = !0, loc.set('exp', 'x'))
    };
    this.btn = function(n) {
        var t = select('s1'),
            i = $('s1');
        i.value = i.value.substring(0, t[0]) + n + i.value.substring(t[1]);
        select('s1', t[0] + 1, t[0] + 1)
    };
    this.erase = function() {
        var t = select('s1'),
            n = $('s1');
        n.value = n.value.substring(0, t[0]) + n.value.substring(t[1])
    };
    this.copy = function() {
        var n = select('s1'),
            t = $('s1');
        this.tmp = t.value.substring(n[0], n[1])
    };
    this.paste = function() {
        var n = select('s1'),
            t = $('s1');
        t.value = t.value.substring(0, n[0]) + this.tmp + t.value.substring(n[1]);
        select('s1', n[0] + this.tmp.length, n[0] + this.tmp.length)
    };
    this.brackets = function(n) {
        var t = select('s1'),
            u = [
                ['[', ']'],
                ['{', '}']
            ],
            i = $('s1'),
            r;
        if (t[0] === t[1]) i.value = i.value.lastIndexOf(u[n][0]) != -1 && i.value.lastIndexOf(u[n][1], t[0]) > i.value.lastIndexOf(u[n][0], t[0]) || i.value.lastIndexOf(u[n][0], t[0]) === -1 ? i.value.substring(0, t[0]) + u[n][0] + i.value.substring(t[1]) : i.value.substring(0, t[0]) + u[n][1] + i.value.substring(t[1]), select('s1', t[1] + 1, t[1] + 1);
        else {
            for (r = 0; r < 2; r++) i.value = i.value.substring(0, t[r] + r) + u[n][r] + i.value.substring(t[r] + r);
            select('s1', t[1] + 2, t[1] + 2)
        }
    }
}
var win_fnc = new Windows_FNC,
    rec = new Recorder;