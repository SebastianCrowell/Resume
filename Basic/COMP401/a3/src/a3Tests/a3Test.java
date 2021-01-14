package a3Tests;
import static org.junit.Assert.*;
import org.junit.Test;
import a3.*;
public class a3Test {


	@Test

	public void colorPixelConstructorTest() {
		Pixel p = new ColorPixel(0.2, 0.3, 0.4);
		assertEquals(0.2, p.getRed(), 0.01);
		assertEquals(0.3,  p.getGreen(), 0.01);
		assertEquals(0.4, p.getBlue(), 0.01);
	}
	@Test
	public void colorPixelIntensityTest() {
		Pixel p = new ColorPixel(0.34, 0.34, 0.34);
		assertEquals(0.34, p.getIntensity(), 0.01);
		p = new ColorPixel(0.1, 0.5, 0.8);
		assertEquals(0.299*0.1+0.587*0.5+0.8*0.114, p.getIntensity(), 0.01);
		p = new ColorPixel(1.0, 0, 0.0);
		assertEquals(0.299, p.getIntensity(), 0.001);
		p = new ColorPixel(0.0, 1.0, 0.0);
		assertEquals(0.587, p.getIntensity(), 0.001);
		p = new ColorPixel(0.0, 0.0, 1.0);
		assertEquals(0.114, p.getIntensity(), 0.001);
	}
	@Test
	public void grayPixelConstructorTest() {
		Pixel p = new GrayPixel(0.2);
		assertEquals(0.2, p.getRed(), 0.01);
		assertEquals(0.2,  p.getGreen(), 0.01);
		assertEquals(0.2, p.getBlue(), 0.01);
	}
	@Test

	public void grayPixelRGBTest() {
		Pixel p = new GrayPixel(0.34);
		assertEquals(0.34, p.getIntensity(), 0.001);
		assertEquals(0.34, p.getRed(), 0.001);
		assertEquals(0.34, p.getGreen(), 0.001);
		assertEquals(0.34, p.getBlue(), 0.001);
	}
	@Test

	public void blendTest() {

		Pixel p1 = new ColorPixel(0.5, 0.3, 0.8);

		Pixel p2 = new ColorPixel(0.4, 0.4, 0.2);

		

		Pixel blend = p1.blend(p2, 1.0);

		assertEquals(0.5, blend.getRed(), 0.001);

		assertEquals(0.3,  blend.getGreen(), 0.001);

		assertEquals(0.8,  blend.getBlue(), 0.001);

		

		blend = p1.blend(p2, 0.0);

		assertEquals(0.4, blend.getRed(), 0.001);

		assertEquals(0.4, blend.getGreen(), 0.001);

		assertEquals(0.2, blend.getBlue(), 0.001);



		blend = p1.blend(p2,  0.5);

		assertEquals(0.45, blend.getRed(), 0.001);

		assertEquals(0.35, blend.getGreen(), 0.001);

		assertEquals(0.50, blend.getBlue(), 0.001);		

		

		assertFalse(blend == p1);

		assertFalse(blend == p2);

	}



	@Test

	public void lightenTest() {

		Pixel p = new ColorPixel(0.5, 0.3, 0.8);

		

		Pixel lighter = p.lighten(0.0);

		assertEquals(0.5, lighter.getRed(), 0.001);

		assertEquals(0.3,  lighter.getGreen(), 0.001);

		assertEquals(0.8,  lighter.getBlue(), 0.001);

		

		lighter = p.lighten(1.0);

		assertEquals(1.0, lighter.getRed(), 0.001);

		assertEquals(1.0, lighter.getGreen(), 0.001);

		assertEquals(1.0, lighter.getBlue(), 0.001);



		lighter = p.lighten(0.5);

		assertEquals(0.75, lighter.getRed(), 0.001);

		assertEquals(0.65, lighter.getGreen(), 0.001);

		assertEquals(0.9, lighter.getBlue(), 0.001);		

		

		assertFalse(lighter == p);

	}



	@Test

	public void darkenTest() {

		Pixel p = new ColorPixel(0.5, 0.3, 0.8);

		

		Pixel darker = p.darken(0.0);

		assertEquals(0.5, darker.getRed(), 0.001);

		assertEquals(0.3,  darker.getGreen(), 0.001);

		assertEquals(0.8,  darker.getBlue(), 0.001);

		

		darker = p.darken(1.0);

		assertEquals(0.0, darker.getRed(), 0.001);

		assertEquals(0.0, darker.getGreen(), 0.001);

		assertEquals(0.0, darker.getBlue(), 0.001);



		darker = p.darken(0.5);

		assertEquals(0.25, darker.getRed(), 0.001);

		assertEquals(0.15, darker.getGreen(), 0.001);

		assertEquals(0.4, darker.getBlue(), 0.001);		

		

		assertFalse(darker == p);

	}


	}
