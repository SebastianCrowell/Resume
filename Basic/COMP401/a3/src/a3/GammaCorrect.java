package a3;

public class GammaCorrect implements PixelTransformation {

	private double value_needed;

	public GammaCorrect (double gamma) {
		value_needed = gamma;
	}

	@Override
	public Pixel transform(Pixel p) {
		// Store RGB values then correct as needed
		double tempRed = p.getRed();
		double tempBlue = p.getBlue();
		double tempGreen = p.getGreen();
		
		tempRed = Math.pow(tempRed, (1.0/value_needed));
		tempBlue = Math.pow(tempBlue, (1.0/value_needed));
		tempGreen = Math.pow(tempGreen, (1.0/value_needed));
		
		ColorPixel fixedPixel = new ColorPixel(tempRed, tempBlue, tempGreen);
		return fixedPixel;
	}
}