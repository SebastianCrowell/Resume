package a3;

public class Threshold implements PixelTransformation {
	
	private double value_needed;

	public Threshold (double threshold) {
		value_needed = threshold;
	}  

	@Override
	public Pixel transform(Pixel p) {
		// Check to see what pixel to return
		if(p.getIntensity() > value_needed) {
			return new GrayPixel(1.0);
		} else {
			return new GrayPixel(0.0);
		}
	} 
}
