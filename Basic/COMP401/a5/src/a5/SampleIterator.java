package a5;

import java.util.Iterator;

public class SampleIterator implements Iterator<Pixel> {
	
	private Picture _source;
	private int _init_x;
	private int _init_y;
	private int _dx;
	private int _dy;
	private int _staticx;
	protected Pixel currentloc;

	public SampleIterator(Picture source, int init_x, int init_y, int dx, int dy) {
		_source = source;
		_init_x = init_x - dx;
		_init_y = init_y;
		_dx = dx;
		_dy = dy;
		_staticx = init_x;
	}
	
	//Checks next pieces to see the limit in x and y directions
	@Override
	public boolean hasNext() {
		return _init_y + _dy < _source.getHeight() ||
			_init_x + _dx < _source.getWidth(); 
	}

	//Moves from left to right then up to down
	@Override
	public Pixel next() {
			if(_init_x + _dx < _source.getWidth()) {
			   _init_x = _init_x + _dx;
			   return _source.getPixel(_init_x, _init_y);
			} else {
			   _init_y = _init_y + _dy;
			   _init_x = _staticx;
			   return _source.getPixel(_init_x, _init_y);
			}
		}
	}

