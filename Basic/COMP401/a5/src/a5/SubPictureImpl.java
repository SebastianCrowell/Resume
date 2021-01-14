package a5;

import java.util.Iterator;

public class SubPictureImpl implements SubPicture {

	private Picture _source;
	private int _xoffset;
	private int _yoffset;
	private int _width;
	private int _height;
	private Pixel[][] _pixels;
	private String _caption;
	
	public SubPictureImpl(Picture source, int xoffset, int yoffset, int width, int height) {
		if(source == null) {
				throw new IllegalArgumentException("Null error source");
			}
		if (xoffset < 0 || 
			xoffset >= source.getWidth() ||
			yoffset < 0 || 
			yoffset >= source.getHeight() ||
			width < 0 ||
			xoffset + width > source.getWidth() ||
			height < 0 ||
			yoffset + height > source.getHeight()) {
			throw new IllegalArgumentException("Subpicture geometry is invalid.");
		}
		
		_source = source;
		_xoffset = xoffset;
		_yoffset = yoffset;
		_width = width - xoffset;
		_height = height - yoffset;
		_caption = source.getCaption(); 
		_pixels = new Pixel[width][height];
	}

	@Override
	public int getWidth() {
		return _pixels.length;
	}

	@Override
	public int getHeight() {
		return _pixels[0].length;
	}

	@Override
	public Picture getSource() {
		return _source;
	}
	
	@Override
	public int getXOffset() {
		return _xoffset;
	}

	@Override
	public int getYOffset() {
		return _yoffset;
	}
	@Override
	public Pixel getPixel(int x, int y) {
		//Pixel location and error check for outside of area
		if (x < 0 || 
			x >= getWidth() || 
			y < 0 || 
			y >= getHeight()) {
			throw new IllegalArgumentException("Out of bounds");
		}
		return _source.getPixel(_xoffset + x, _yoffset + y);
	}

	@Override
	public Picture paint(int x, int y, Pixel p) {
		// Painting of picture by pixel
		if (x < 0 || 
			y < 0 || 
			x > _source.getWidth() || 
			y > _source.getHeight()) {
			throw new IllegalArgumentException("Out of bounds");
		}
		if (p == null) {
				throw new IllegalArgumentException("Pixel p is null");
		} 
		Picture _stored = _source.paint(x + _xoffset, y + _yoffset, p);
		if (_stored == (_source)) {
			return _stored;
		} else {
			return new SubPictureImpl(_source, _xoffset, _yoffset, _width, _height);
		}
	}

	@Override
	public Picture paint(int x, int y, Pixel p, double factor) {
		//Blended painting of picture by pixel
		if (x < 0 || 
			y < 0 || x 
			> _source.getWidth() || 
			y > _source.getHeight()) {
			throw new IllegalArgumentException("Out of bounds");
			} else {
			if (p == null) {
				throw new IllegalArgumentException("Pixel p is null");
			}
			
			if (factor < 0 || 
				factor > 1.0) {
				throw new IllegalArgumentException("Factor out of range");			
			} else {
		Picture _stored = _source.paint(x + _xoffset, y + _yoffset, p, factor);
		if (_stored == (_source)) {
			return _stored;
		} else {
			return new SubPictureImpl(_source, _xoffset, _yoffset, _width, _height);
		}
		}
	}
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
		// Rectangle paint
		if (ax < 0 || 
			ay < 0 || 
			ax > _source.getWidth() || 
			ay > _source.getHeight()) {
			throw new IllegalArgumentException("Out of bounds");
		} else {
			if (p == null) {
				throw new IllegalArgumentException("Pixel p is null");
		} else {
		Picture _stored = _source.paint(ax + _xoffset, ay + _yoffset, bx + _yoffset, by + _yoffset, p);
		if (_stored == (_source)) {
			return _stored;
		} else {
			return new SubPictureImpl(_source, _xoffset, _yoffset, _width, _height);
		}
		}
	}
	}

	@Override
	public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
		// Blended rectangle paint
		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}
		
		if (factor < 0 || 
			factor > 1.0) {
			throw new IllegalArgumentException("Factor out of range");			
		}
		
		if (    ax < 0 || 
				ay < 0 || 
				ax > _source.getWidth() || 
				ay > _source.getHeight()) {
				throw new IllegalArgumentException("Out of bounds");
			} else {
			Picture _stored = _source.paint(ax + _xoffset, ay + _yoffset, bx + _yoffset, by + _yoffset, p, factor);
			if (_stored == (_source)) {
				return _stored;
			} else {
				return new SubPictureImpl(_source, _xoffset, _yoffset, _width, _height);
			}
			}
		}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p) {
		// Circle paint
		if (cx < 0 || 
				cy < 0 || 
			    cx > _source.getWidth() || 
			    cy > _source.getHeight()) {
				throw new IllegalArgumentException("Out of bounds");
			} else {
			
			if (p == null) {
				throw new IllegalArgumentException("Pixel p is null");
			}
			
			if (radius < 0) {
				throw new IllegalArgumentException("Radius is negative");
			} else {
		Picture _stored = _source.paint(cx + _xoffset, cy + _yoffset, radius, p);
		if (_stored == (_source)) {
			return _stored;
		} else {
			return new SubPictureImpl(_source, _xoffset, _yoffset, _width, _height);
		}
		}
	}
	}

	@Override
	public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
		// Blended circle paint
		if (cx < 0 || 
			cy < 0 || 
		    cx > _source.getWidth() || 
		    cy > _source.getHeight()) {
			throw new IllegalArgumentException("Out of bounds");
		} else {
		
		if (p == null) {
			throw new IllegalArgumentException("Pixel p is null");
		}
		
		if (factor < 0 || factor > 1.0) {
			throw new IllegalArgumentException("Factor out of range");			
		}
		
		if (radius < 0) {
			throw new IllegalArgumentException("Radius is negative");
		} else {
			Picture _stored = _source.paint(cx + _xoffset, cy + _yoffset, radius, p, factor);
			if (_stored == (_source)) {
				return _stored;
			} else {
				return new SubPictureImpl(_source, _xoffset, _yoffset, _width, _height);
			}
			}
		}
		}

	@Override
	public Picture paint(int x, int y, Picture p) {
		// Paints everything from (0,0) to bottom and right
		if (x < 0 || 
			y < 0 || 
		    x > _source.getWidth() || 
		    y > _source.getHeight()) {
			throw new IllegalArgumentException("Out of bounds");
		} else {
			if (p == null) {
				throw new IllegalArgumentException("Picture p is null");
		} else {
		Picture _stored = _source.paint(x + _xoffset, y + _yoffset, p);
		if (_stored == (_source)) {
			return _stored;
		} else {
			return new SubPictureImpl(_source, _xoffset, _yoffset, _width, _height);
		}
		}
	}
	}
	@Override
	public Picture paint(int x, int y, Picture p, double factor) {
		// Blended painting of area from (0,0) to bottom and right
		if (x < 0 || 
			y < 0 || 
		    x > _source.getWidth() || 
		    y > _source.getHeight()) {
			throw new IllegalArgumentException("Out of bounds");
		} else {
			Picture _stored = _source.paint(x + _xoffset, y + _yoffset, p, factor);
			if (_stored == (_source)) {
				return new SubPictureImpl(_stored, _xoffset, _yoffset, _width, _height);
			} else {
				return _stored;
			}
		}
		
	}

	@Override
	public String getCaption() {
		return _caption;
	}

	@Override
	public void setCaption(String caption) {
		if (caption == null) {
			throw new IllegalArgumentException("Caption is null");
		}
		_caption = caption;
	}
	
	@Override
	public SubPicture extract(int x, int y, int width, int height) {
		return new SubPictureImpl(this, x, y, _width, _height);
	}

	@Override
	public Iterator<Pixel> sample(int init_x, int init_y, int dx, int dy) {
		return new SampleIterator(this, init_x, init_y, dx, dy);
	}

	@Override
	public Iterator<SubPicture> window(int window_width, int window_height) {
		return new WindowIterator(this, window_width, window_height);
	}

	@Override
	public Iterator<SubPicture> tile(int tile_width, int tile_height) {
		return new TileIterator(this, tile_width, tile_height);
	}

	@Override
	public Iterator<Pixel> zigzag() {
		// Diagonal checking for painting
		return new ZigZagIterator(this);
	}



}
