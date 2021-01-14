package a3;

public class TransformedPicture implements Picture{
	
	private Picture source_picture;
	private PixelTransformation transformer;
    private Pixel[][] _arraypixels;
    private int _width;
    private int _height;

	public TransformedPicture (Picture source, PixelTransformation xform) {
		source_picture = source;
		transformer = xform;
		_width = source.getWidth();
		_height = source.getHeight();

		_arraypixels = new Pixel[_width][_height];
		for (int i = 0; i < _width; i++) {
            for (int j = 0; j < _height; j++) {
            	_arraypixels[i][j] = source.getPixel(i,j);
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
            throw new IllegalArgumentException("Error in values");
        } else {
        	return transformer.transform(source_picture.getPixel(x, y));
        }
    }
    @Override
    public Picture paint(int x, int y, Pixel p) {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_arraypixels);
            paintPic.paint(x, y, p);
            return paintPic;
        }
    @Override
    public Picture paint(int x, int y, Pixel p, double factor) {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_arraypixels); 
            paintPic.paint(x, y, p, factor);
            return paintPic;
        }
    @Override
    public Picture paint(int ax, int bx, int ay, int by, Pixel p) {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_arraypixels); 
            paintPic.paint(ax, bx, ay, by, p);
            return paintPic;
        }
    @Override
    public Picture paint(int ax, int bx, int ay, int by, Pixel p, double factor) {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_arraypixels); 
            paintPic.paint(ax, bx, ay, by, p, factor);
            return paintPic;
        }
    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p) {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_arraypixels); 
            paintPic.paint(cx, cy, radius, p);
            return paintPic;
        }
    @Override
    public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
            MutablePixelArrayPicture paintPic = new MutablePixelArrayPicture(_arraypixels); 
            paintPic.paint(cx, cy, radius, p, factor);
            return paintPic;
        }
    }

