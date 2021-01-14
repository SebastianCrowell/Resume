package a5;

import java.util.Iterator;

public class TileIterator implements Iterator<SubPicture> {
	
	private Picture _source;
	private int _init_x;
	private int _init_y;
	private int _tile_width;
	private int _tile_height;
	
	//Error checking for negative and null
	public TileIterator(Picture source, int tile_width, int tile_height) {
		_source = source;
		_tile_width = tile_width;
		_tile_height = tile_height;
	}
	
	// Checking tile validity by height and width
	@Override
	public boolean hasNext() {
		Boolean placeholder = true;
		if(_init_y + _tile_height > _source.getHeight()) {
			placeholder = false;
		} else {
			placeholder = true;
		}
		return placeholder;
	}
	
	// Moves from left to right then up to down, goes back to 0 in x
	@Override
	public SubPicture next() {
		if (hasNext() == false) {
			throw new RuntimeException("False error hasNext");
		}
		SubPicture subpic = _source.extract(_init_x, _init_y, _tile_width, _tile_height);
		if(_tile_width + _init_x <= _source.getWidth() - _tile_width) {
			_init_x = _init_x + _tile_width;
		} else {
			_init_x = 0;
			_init_y = _init_y + _tile_height;
		}
		return subpic;
	}

}
