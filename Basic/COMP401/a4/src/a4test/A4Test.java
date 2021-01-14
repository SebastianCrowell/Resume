package a4test;

import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;

import a4.*;

public class A4Test {	
	
	private static final Pixel RED = new ColorPixel(1,0,0);
	private static final Pixel GREEN = new ColorPixel(0,1,0);
	private static final Pixel BLUE = new ColorPixel(0,0,1);
	private static final Pixel WHITE = new GrayPixel(1);
	private static final Pixel BLACK = new GrayPixel(0);

	static public String[] getTestNames() {
		String[] test_names = new String[10];

		test_names[0] = "testExample";
		test_names[1] = "testSampleIter";
		test_names[2] = "testPictureEquals";
		test_names[3] = "testMutableSubPic";
		test_names[4] = "testColorPixel";
		test_names[5] = "testColorPixelInt"; 
		test_names[6] = "testGrayPixel";
		test_names[7] = "testGrayPixelRGB";
		test_names[8] = "testBlend";
		test_names[9] = "testLight";

		return test_names;
	}

	@Test
	public void testExample() {
	}
	@Test
	public void testSampleIter() {
		Pixel[][] parray = new Pixel[3][3];
		
		for (int x=0; x < 3; x++) {
			for (int y=0; y < 3; y++) {
				parray[x][y] = new ColorPixel(0, 0, 0);
			}
		}
		
		Picture picture = new MutablePixelArrayPicture(parray, "My caption");
		Iterator<Pixel> iter = picture.sample(0, 0, 2, 2);
		assertTrue(iter.hasNext());
		assertEquals(parray[0][0], iter.next());
		assertTrue(iter.hasNext());
		assertEquals(parray[2][0], iter.next());
		assertTrue(iter.hasNext());
		assertEquals(parray[0][2], iter.next());
		assertTrue(iter.hasNext());
		assertEquals(parray[2][2], iter.next());
	}
	@Test
	public void testPictureEquals() {
		Picture p1 = new MutablePixelArrayPicture(new Pixel[][] {{Pixel.BLACK}}, "");
		Picture p2 = new MutablePixelArrayPicture(new Pixel[][] {{Pixel.BLACK}}, "");
		assertEquals(p1.getPixel(0, 0), p2.getPixel(0, 0));
	}
	@Test
	public void testMutableSubPic() {
		Pixel[][] pixels = new Pixel[3][3];
		for(int i=0; i < 3; i++) {
			for (int j=0; j < 3; j++) {
				if (i%2 == 0) {
					pixels[i][j] = Pixel.BLACK;
				} else {
					pixels[i][j] = Pixel.WHITE;
				}
				Picture source_pic = new MutablePixelArrayPicture(pixels, "Mutable");
				Picture sub_pic = source_pic.extract(1, 1, 2, 2);
				
				assertEquals(source_pic.getPixel(1, 1),sub_pic.getPixel(0, 0));
				assertEquals(source_pic.getPixel(1, 2),sub_pic.getPixel(0, 0));
				assertEquals(source_pic.getPixel(2, 1),sub_pic.getPixel(0, 0));
				assertEquals(source_pic.getPixel(2, 2),sub_pic.getPixel(0, 0));
				
				sub_pic.paint(1, 1, Pixel.WHITE);
				
				assertEquals(source_pic.getPixel(2, 2),Pixel.WHITE);
				assertEquals(source_pic.getPixel(1, 1),Pixel.WHITE);
			}
			
		}
	}
	@Test
	public void testColorPixel() {
		Pixel p = new ColorPixel(0.2, 0.3, 0.4);
		assertEquals(0.2, p.getRed(), 0.01);
		assertEquals(0.3,  p.getGreen(), 0.01);
		assertEquals(0.4, p.getBlue(), 0.01);
	}
	@Test
	public void testColorPixelInt() {
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
	public void testGrayPixel() {
		Pixel p = new GrayPixel(0.2);
		assertEquals(0.2, p.getRed(), 0.01);
		assertEquals(0.2,  p.getGreen(), 0.01);
		assertEquals(0.2, p.getBlue(), 0.01);
	}
	@Test
	public void testGrayPixelRGB() {
		Pixel p = new GrayPixel(0.34);
		assertEquals(0.34, p.getIntensity(), 0.001);
		assertEquals(0.34, p.getRed(), 0.001);
		assertEquals(0.34, p.getGreen(), 0.001);
		assertEquals(0.34, p.getBlue(), 0.001);
	}
	@Test
	public void testBlend() {
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
	public void testLight() {
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
}
