package a5;

import java.util.Iterator;

public class ZigZagIterator implements Iterator<Pixel> {
	
	private Picture _source;
	private int _height;
	private int _width;
	private int _currentx = 0;
	private int _currenty = 0;
	private int _movement = 0;

	public ZigZagIterator(Picture source) {
		_source = source;
		_height = source.getHeight();
		_width = source.getWidth();
	}
	
	//Checking to see if we can move
	@Override
	public boolean hasNext() {
		return (_movement < _width * _height);
	}

	//Start (0,0), go down (0,1) , diagonal up (1,1), right (2,1) ...
	@Override
	public Pixel next() {
		if(!hasNext()) {
			throw new RuntimeException("Out of pixels");
		}
		_movement++;
		Pixel position = _source.getPixel(_currentx, _currenty);
		if( _currentx == 0 && _currenty == 0) {
			_currentx++;
		} else if (_width%2 == 0 && _currentx == _width - 1 && _currenty == 0) {
			_currentx--;
			_currenty++;
		} else if (_width%2 == 1 && _currentx == _width - 1 && _currenty == 0) {
			_currenty++;
		} else if (_height%2 == 0 && _currentx == 0 && _currenty == _height - 1) {
			_currentx++;
		} else if (_height%2 == 1 && _currentx == 0 && _currenty == _height - 1) {
			_currentx++;
			_currenty--;
		} else if (_currentx%2 == 0 && _currenty == 0) {
			_currentx++;
		} else if (_currentx%2 == 1 && _currenty == 0) {
			_currentx--;
			_currenty++;
		} else if ((_currentx + _currenty)%2 == 1 && _currenty == _height - 1) {
			_currentx++;
		} else if ((_currentx + _currenty)%2 == 0 && _currenty == _height - 1) {
			_currentx++;
			_currenty--;
		} else if (_currenty%2 == 1 && _currentx == 0) {
			_currenty++;
		} else if (_currenty%2 == 0 && _currentx == 0) {
			_currentx++;
			_currenty--;
		} else if ((_currentx + _currenty)%2 == 0 && _currentx == _width - 1) {
			_currenty++;
		} else if ((_currentx + _currenty)%2 == 1 && _currentx == _width - 1) {
			_currentx--;
			_currenty++;
		} else if ((_currentx + _currenty)%2 == 0) {
			if (_currentx != _width - 1 && _currenty != _height - 1) {
				_currentx++;
				_currenty--;
			} else if (_currentx != _width) {
				_currentx++;
			} else {
				_currenty++;
			}
		} else if ((_currentx + _currenty)%2 != 0) {
			if (_currentx != _width && _currenty != _height) {
				_currentx--;
				_currenty++;
			} else if (_currentx != _width) {
				_currentx--;
			} else {
				_currenty--;
			}
		}
		return position;
	}
}
