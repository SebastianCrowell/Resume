package a3;

public class HorizontalStackPicture implements Picture{
	
    private Pixel[][] _arraypixels;
    private int _width;
    private int _height;

	public HorizontalStackPicture(Picture left, Picture right) {
		if(left == null ||
		   right == null) {
		   throw new IllegalArgumentException("Error in dimensions");
			} 
		if (left.getHeight() != right.getHeight()) {
		   throw new IllegalArgumentException("Error in dimensions");
			}
			_width = left.getWidth() + right.getWidth();
			_height = left.getHeight();
			_arraypixels = new Pixel[_width][_height];
			
			// Copy picture left
			for (int x = 0; x < left.getWidth(); x++) {
	            for (int y = 0; y < left.getHeight(); y++) {
	            	_arraypixels[x][y] = left.getPixel(x, y);
	            }
	        }

			// Copy picture right
			for (int x = 0; x < right.getWidth(); x++) {
	            for (int y = 0; y < right.getHeight(); y++) {
	            	_arraypixels[left.getWidth() + x][y] = right.getPixel(x, y);
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
		if(x < 0 ||
		   y < 0 ||
		   x >= _width ||
		   y >= _height) {
		   throw new IllegalArgumentException("Error in dimensions");
			} else {
				return _arraypixels[x][y];
			}
	}
	@Override
	public Picture paint(int x, int y, Pixel p) {
		if(x < 0 ||
		   y < 0 ||
		   p == null ||
		   x >= _width ||
		   y >= _height) {
		   throw new IllegalArgumentException("Error in dimensions");
			} else {
				_arraypixels[x][y] = p;
			}
		return this;
	}
	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		if(x < 0 ||
		   y < 0 || 
		   factor < 0 ||
		   factor > 1 ||
		   x >= _width ||
		   y >= _height ||
		   p == null) {
		   throw new IllegalArgumentException("Error in dimensions");
			} else {
				_arraypixels[x][y] = _arraypixels[x][y].blend(p, factor);
			}
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

