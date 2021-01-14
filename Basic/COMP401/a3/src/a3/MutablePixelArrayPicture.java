package a3;

public class MutablePixelArrayPicture implements Picture {

    private Pixel[][] _arraypixels;
    private Pixel start_value;
    private int _width;
    private int _height;
    
    //New MutablePixelArrayPicture object for paint
    public MutablePixelArrayPicture(Pixel[][] pixel_array) {
        _width = pixel_array.length;
        _height = pixel_array[0].length;
        _arraypixels = new Pixel[_width][_height];
        
        for(int i=0; i < pixel_array.length; i++) {
            for(int j=0; j < pixel_array[0].length; j++) {
                _arraypixels[i][j] = pixel_array[i][j];
            }
        }
    }
    
    //Give a value to each pixel
    public MutablePixelArrayPicture(int width, int height, Pixel initial_value) {
        if (width <= 0 ||
            height <= 0) {
            throw new IllegalArgumentException("Error in dimensions");
            } else {
                _width = width;
                _height =  height;
                _arraypixels = new Pixel[this._width][this._height];
        
        for (int i=0; i < width; i++) {
            for (int j=0; j < height; j++) {
                _arraypixels[i][j] = initial_value; 
                }
            }
        }
    }
    
    //Grayscale autosetting for a picture 
    public MutablePixelArrayPicture(int width, int height) {
        start_value = new GrayPixel(0.5);
        if(width <= 0 ||
           height <= 0) {
           throw new IllegalArgumentException("Error in dimensions");
        } else {
                _width = width;
                _height = height;
                _arraypixels = new Pixel[width][height];
            
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    _arraypixels[i][j] = start_value;
                }
            }
        }
    }
    
    @Override
    public int getWidth() {
        return _width;
    }

    @Override
    public int getHeight() {
        return _height;
    }

    @Override
    public Pixel getPixel(int x, int y) {
        if(x > getWidth()||
           x < 0 ||
           y > getHeight() ||
           y < 0) {
           throw new IllegalArgumentException("Error in dimensions");
        }
        return _arraypixels[x][y];
    }

    @Override
    public Picture paint(int x, int y, Pixel p) {
        if (p == null) {
            throw new IllegalArgumentException("Pixel p is null");
        }
        _arraypixels[x][y] = p;
        return this;
    }

    @Override
    public Picture paint(int x, int y, Pixel p, double factor) {
        if (p == null) {
            throw new IllegalArgumentException("Pixel p is null");
        }
        if (factor < 0 && factor > 1) {
            throw new IllegalArgumentException("Factor oor");
        }
        _arraypixels[x][y] = _arraypixels[x][y].blend(p,  factor);
        return this;
    }

    @Override
    public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
        if(ax > getWidth()||
           ax < 0 ||
           ay > getHeight() ||
           ay < 0) {
           throw new IllegalArgumentException("Error in dimensions");
        }
            if(bx > getWidth()||
               bx < 0 ||
               by > getHeight() ||
               by < 0) {
               throw new IllegalArgumentException("Error in dimensions");
            }
        if (p == null) {
            throw new IllegalArgumentException("Pixel p is null");
        }
        if(ax >= 0 && bx >= 0 && ay >= 0 && by >= 0) {
            //For cases of corners (KMP said not to assume where corners are)
            if((ax < bx) && (ay < by)) {
                for(int x = ax; x <= bx; x++) {
                    for(int y = ay; y <= by; y++) {
                        _arraypixels[x][y] = p;
                    }
                }
            } else if ((bx < ax) && (by < ay)) {
                for(int x = bx; x <= ax; x++) {
                    for(int y = by; y <= ay; y++) {
                        _arraypixels[x][y] = p;
                    }
                }
            } else if ((ax < bx) && (ay > by)) {
                for(int x = ax; x <= bx; x++) {
                    for(int y = by; y <= ay; y++) {
                        _arraypixels[x][y] = p;
                    }
                }
            } else if ((bx < ax) && (by > ay)) {
                for(int x = bx; x <= bx; x++) {
                    for(int y = ay; y <= by; y++) {
                        _arraypixels[x][y] = p;
                    }
                }
                //Rows for right/left
            } else if (by == ay) {
                if(bx > ax) {
                    for(int x = ax; x <= bx; x++) {
                        _arraypixels[x][ay] = p;
                    }
            } else {
                for(int x = bx; x <= ax; x++) {
                    _arraypixels[x][ay] = p;
                }
            }
                //Columns for top/bottom
            } else if (bx == ax) {
                if(by > ay) {
                    for(int y = ay; y <= by; y++) {
                        _arraypixels[ax][y] = p;
                    }
            } else {
                for(int y = by; y <= ay; y++) {
                    _arraypixels[ax][y] = p;
                }
            } 
        } else if ((ax == bx) && (ay == by)) {
            _arraypixels[ax][ay] = p;
        } else {
            throw new IllegalArgumentException("Pixel error in array");
        }
        }
        return this;
    }        
    

    @Override
    public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
        if(ax > getWidth()||
           ax < 0 ||
           ay > getHeight() ||
           ay < 0) {
           throw new IllegalArgumentException("Error in dimensions");
         }
                if(bx > getWidth()||
                   bx < 0 ||
                   by > getHeight() ||
                   by < 0) {
                   throw new IllegalArgumentException("Error in dimensions");
                }
        if (p == null) {
            throw new IllegalArgumentException("Pixel p is null");
        }
        if (factor < 0 && factor > 1) {
            throw new IllegalArgumentException("Factor oor");
        }
        if(ax >= 0 && bx >= 0 && ay >= 0 && by >= 0) {
            //For cases of corners (KMP said not to assume where corners are)
            if((ax < bx) && (ay < by)) {
                for(int x = ax; x <= bx; x++) {
                    for(int y = ay; y <= by; y++) {
                        _arraypixels[x][y] = _arraypixels[x][y].blend(p, factor);
                    }
                }
            } else if ((bx < ax) && (by < ay)) {
                for(int x = bx; x <= ax; x++) {
                    for(int y = by; y <= ay; y++) {
                        _arraypixels[x][y] = _arraypixels[x][y].blend(p, factor);
                    }
                }
            } else if ((ax < bx) && (ay > by)) {
                for(int x = ax; x <= bx; x++) {
                    for(int y = by; y <= ay; y++) {
                        _arraypixels[x][y] = _arraypixels[x][y].blend(p, factor);
                    }
                }
            } else if ((bx < ax) && (by > ay)) {
                for(int x = bx; x <= bx; x++) {
                    for(int y = ay; y <= by; y++) {
                        _arraypixels[x][y] = _arraypixels[x][y].blend(p, factor);
                    }
                }
                //Rows for right/left
            } else if (by == ay) {
                if(bx > ax) {
                    for(int x = ax; x <= bx; x++) {
                        _arraypixels[x][ay] = _arraypixels[x][ay].blend(p, factor);
                    }
            } else {
                for(int x = bx; x <= ax; x++) {
                    _arraypixels[x][ay] = _arraypixels[x][ay].blend(p, factor);
                }
            }
                //Columns for top/bottom
            } else if (bx == ax) {
                if(by > ay) {
                    for(int y = ay; y <= by; y++) {
                        _arraypixels[ax][y] = _arraypixels[ax][y].blend(p, factor);
                    }
            } else {
                for(int y = by; y <= ay; y++) {
                    _arraypixels[ax][y] = _arraypixels[ax][y].blend(p, factor);
                }
            } 
        } else if ((ax == bx) && (ay == by)) {
            _arraypixels[ax][ay] = _arraypixels[ax][ay].blend(p, factor);
        } else {
            throw new IllegalArgumentException("Pixel error in array");
        }
        }
        return this;
    }

    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p) {
        if(cx > getWidth()||
           cx < 0 ||
           cy > getHeight() ||
           cy < 0) {
           throw new IllegalArgumentException("Error in dimensions");
         }
            if(p == null) {
                throw new IllegalArgumentException("Pixel is null");
            }
            if(radius < 0) {
                throw new IllegalArgumentException("Radius is negative");
            }
            for(int x=0; x < _width; x++) {
                for(int y=0; y < _height; y++) {
                    if (Math.sqrt((cx - x)*(cx - x)+(cy - y)*(cy - y)) <= radius) {
                        _arraypixels[x][y] = p;
                    }
                }
            }
    return this;
    }

    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
        if(cx > getWidth()||
           cx < 0 ||
           cy > getHeight() ||
           cy < 0) {
           throw new IllegalArgumentException("Error in dimensions");
        }
        if (p == null) {
            throw new IllegalArgumentException("Pixel p is null");
        }
        if (factor < 0 ||
            factor > 1.0) {
            throw new IllegalArgumentException("Factor oor");            
        }
        if (radius < 0) {
            throw new IllegalArgumentException("Radius is negative");
        }
            for(int x=0; x < _width; x++) {
                for(int y=0; y < _height; y++) {
                    if (Math.sqrt((cx - x)*(cx - x)+(cy - y)*(cy - y)) <= radius) {
                        _arraypixels[x][y] = _arraypixels[x][y].blend(p, factor);
                    }
                }
            }
    return this;
    }
}

