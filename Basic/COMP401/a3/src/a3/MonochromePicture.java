
package a3;

public class MonochromePicture implements Picture{
    
    private int _width;
    private int _height;
    private Pixel _value;
    
    public MonochromePicture(int width, int height, Pixel value) {
        if( width <= 0 ||
            height <= 0 ||
            value == null) {
            throw new IllegalArgumentException("Error in values");
        } else {
            _width = width;
            _height = height;
            _value = value;
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
            throw new IllegalArgumentException("Error in values");
        } else {
            return _value;
        }
    }
    @Override
    public Picture paint(int x, int y, Pixel p) {
        // TODO Auto-generated method stub
        if(p == _value) {
            return this;
        } else {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_width, _height, _value);
            paintPic.paint(x, y, p);
            return paintPic;
        }
    }
    
    @Override
    public Picture paint(int x, int y, Pixel p, double factor) {
        if (p == _value) {
            return this;
        } else {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_width, _height, _value); 
            paintPic.paint(x, y, p, factor);
            return paintPic;
        }
    }
    @Override
    public Picture paint(int ax, int bx, int ay, int by, Pixel p) {
        if (p == _value) {
            return this;
        } else {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_width, _height, _value); 
            paintPic.paint(ax, bx, ay, by, p);
            return paintPic;
        }
    }
    @Override
    public Picture paint(int ax, int bx, int ay, int by, Pixel p, double factor) {
        if (p == _value) {
            return this;
        } else {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_width, _height, _value); 
            paintPic.paint(ax, bx, ay, by, p, factor);
            return paintPic;
        }
    }
    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p) {
        if (p == _value) {
            return this;
        } else {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_width, _height, _value); 
            paintPic.paint(cx, cy, radius, p);
            return paintPic;
        }
    }
    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
        if (p == _value) {
            return this;
        } else {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_width, _height, _value); 
            paintPic.paint(cx, cy, radius, p, factor);
            return paintPic;
        }
    }
}



