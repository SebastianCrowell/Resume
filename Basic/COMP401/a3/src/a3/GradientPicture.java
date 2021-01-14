package a3;
public class GradientPicture implements Picture{

	private int _width;
	private int _height;
	private Pixel _upper_left;
	private Pixel _upper_right;
	private Pixel _lower_left;
	private Pixel _lower_right;
	
	// constructor
	public GradientPicture(int width, int height, Pixel upper_left, Pixel upper_right, Pixel lower_left, Pixel lower_right) {
		if(width <= 0 ||
		   height <= 0 || 
		   upper_left == null || 
		   upper_right == null || 
		   lower_left == null || 
		   lower_right == null) {
			throw new IllegalArgumentException("Error in dimensions");
		} else {
			_width = width;
			_height = height;
			_upper_left = upper_left;
			_upper_right = upper_right;
			_lower_left = lower_left;
			_lower_right = lower_right;
		}
	}
	public Pixel[][] paintPictureArray(int width, int height, Pixel upper_left, Pixel upper_right, Pixel lower_left, Pixel lower_right) {
		
		Pixel[][] parray = new Pixel[width][height];
		// Paint parray
		for(int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				double firstBlendAmount = (1.0 / (height-1.0)) * y;
				
				//Blend from start of row on left to end of row on right
				Pixel left_row = upper_left.blend(lower_left, firstBlendAmount);
				Pixel right_row = upper_right.blend(lower_right, firstBlendAmount);	
				
				//Blend pixels from parray
				double secondBlendAmount = (1.0 / (width-1.0)) * x;
				parray[x][y] = left_row.blend(right_row, secondBlendAmount);
			}
		}
		return parray;
	}
	
	public Picture paintPicture(Pixel[][] pixel_array) {
		MutablePixelArrayPicture mutPicture = new MutablePixelArrayPicture(pixel_array);		
		return mutPicture;
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
		return this.paintPicture(this.paintPictureArray(_width, _height, _upper_left, _upper_right, _lower_left, _lower_right)).getPixel(x, y);
	}
	@Override
	public Picture paint(int x, int y, Pixel p) {
		return this.paintPicture(this.paintPictureArray(_width, _height, _upper_left, _upper_right, _lower_left, _lower_right)).paint(x, y, p);
	}
	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		return this.paintPicture(this.paintPictureArray(_width, _height, _upper_left, _upper_right, _lower_left, _lower_right)).paint(x, y, p, factor);
	}
	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		return this.paintPicture(this.paintPictureArray(_width, _height, _upper_left, _upper_right, _lower_left, _lower_right)).paint(ax,ay,bx,by,p);
	}
	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		return this.paintPicture(this.paintPictureArray(_width, _height, _upper_left, _upper_right, _lower_left, _lower_right)).paint(ax,ay,bx,by,p,factor);
	}
	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		return this.paintPicture(this.paintPictureArray(_width, _height, _upper_left, _upper_right, _lower_left, _lower_right)).paint(cx, cy, radius, p);
	}
	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		return this.paintPicture(this.paintPictureArray(_width, _height, _upper_left, _upper_right, _lower_left, _lower_right)).paint(cx, cy, radius, p,factor);
	}
}