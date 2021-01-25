
LAlt::
CoordMode, Pixel, Relative
CoordMode, Mouse, Relative

Loop
{
Sleep 1000
PixelSearch, Px, Py, 430, 60, 1210, 850, 0x529BC8, 5, Fast RGB
if ErrorLevel,
Return
else
Click, %Px%, %Py%

Sleep 5000

}

Esc::ExitApp