package a6;

public class RegionImpl implements Region {
	
	//Int storage
	private int _left;
	private int _top;
	private int _right;
	private int _bottom;
	
	public RegionImpl(int left,int top,int right, int bottom) {
		if(left > right) {
			throw new IllegalArgumentException("Left greater than right");
		}
		if(top > bottom) {
			throw new IllegalArgumentException("Right greater than left");
		}
		_left = left;
		_top = top;
		_right = right;
		_bottom = bottom;
	}

	@Override
	public int getTop() {
		// The top
		return _top;
	}

	@Override
	public int getBottom() {
		// The bottom
		return _bottom;
	}

	@Override
	public int getLeft() {
		// The left
		return _left;
	}

	@Override
	public int getRight() {
		// The right
		return _right;
	}

	@Override
	public Region intersect(Region other) throws NoIntersectionException {
		// Checking if other region is null or is not overlapping
		if ((other.equals(null)) || (other.getLeft() > getRight()) || (other.getRight() < getLeft()) || (other.getTop() > getBottom()) || (other.getBottom() < getTop())){
			throw new NoIntersectionException();
		}
		//Finding the regions that do overlap
		int max_top = (getTop() > other.getTop()) ? other.getTop() : getTop();
		int min_bottom = (getBottom() < other.getBottom()) ? other.getBottom() : getBottom();
		int max_left = (getLeft() > other.getLeft()) ? other.getLeft() : getLeft();
		int min_right = (getRight() < other.getRight()) ? other.getRight() : getRight();
		
		return new RegionImpl(max_left, max_top, min_right, min_bottom);
	}

	@Override
	public Region union(Region other) {
		// Checking for all maxs and mins of the possible size of union area
		if ((other.equals(null))) {
			return this;
		}
		int max_top = (getTop() < other.getTop()) ? other.getTop() : getTop();
		int min_bottom = (getBottom() > other.getBottom()) ? other.getBottom() : getBottom();
		int max_left = (getLeft() < other.getLeft()) ? other.getLeft() : getLeft();
		int min_right = (getRight() > other.getRight()) ? other.getRight() : getRight();
		
		return new RegionImpl(max_left, max_top, min_right, min_bottom);
	}

}