package a5;

import java.util.Iterator;

public class WindowIterator implements Iterator<SubPicture> {
	
	private Picture _source;
	private int _window_width;
	private int _window_height;
	private int _currentx;
	private int _currenty;

	public WindowIterator(Picture source, int window_width, int window_height) {
		_source = source;
		_window_width = window_width;
		_window_height = window_height;
	}
	//Check height for validity using source
	@Override
	public boolean hasNext() {
		Boolean placeholder = false;
		if( _currenty + _window_height > _source.getHeight()) {
			return placeholder;
		} else {
			placeholder = true;
			return placeholder;
		}
	}
 
	//Moves from left to right then up to down, goes back to 0 in x
	@Override
	public SubPicture next() {
		if(hasNext() == false) {
			throw new RuntimeException("False error hasNext");
		}
		SubPicture subpic = _source.extract(_currentx, _currenty, _window_width, _window_height);
		if(subpic.getWidth() + _currentx < _source.getWidth()) {
			_currentx++;
		} else {
			_currenty++;
			_currentx = 0; 
		}
		return subpic;
	}

}
