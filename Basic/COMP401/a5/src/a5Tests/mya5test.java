package a5Tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import a5.*;

public class mya5test {
	// Initialize different pixel amounts.
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel orange = new ColorPixel(0.9, 0.6, 0.1);
	Pixel yellow = new ColorPixel(0.9, 1, 0.1);
	Pixel randomColor = new ColorPixel(0.545, 0.65, 0.332);
	Pixel unit = new ColorPixel(0, 0, 0);
	// Initialize Pixel 2d Arrays
	Pixel[][] parray = { { unit, unit, unit }, { unit, unit, unit }, { unit, unit, unit } };
	Pixel[][] paintedAry = { { red, red, red }, { red, red, red }, { red, red, red } };
	Pixel[][] paintAry = { { yellow, green}, { blue, orange } };
	
	Picture picture = new MutablePixelArrayPicture(parray, "My caption");
	Picture paintedPic = new MutablePixelArrayPicture(paintedAry, "Painted caption");
	Picture paintedPic1 = new ImmutablePixelArrayPicture(paintedAry, "Painted caption");
	Picture paintedPicFac = new MutablePixelArrayPicture(paintedAry, "Painted caption");
	Picture paintedPicFac1 = new ImmutablePixelArrayPicture(paintedAry, "Painted caption");
	Picture paintPic = new MutablePixelArrayPicture(paintAry, "Paint caption");
	
	
		
	static public String[] getTestNames() {
		
		
		String[] test_names = new String[10];
		
		test_names[0] = "paintMutablePicture";
		test_names[1] = "paintImmutablePicture";
		test_names[2] = "captionGetterSetter";
		test_names[3] = "extractSubpicture";
		test_names[4] = "testSampleIter";
		test_names[5] = "testWindow";	
		test_names[6] = "testTile";
		test_names[7] = "testZigZag";
		test_names[8] = "factorPaintMutable";
		test_names[9] = "factorPaintImmutable";

		
		
		return test_names;
	}
	
	
	@Test
	public void paintMutablePicture() {
		Picture nullPic = null;
		try {
			paintedPic.paint(4, 0, paintPic);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPic.paint(0, 4, paintPic);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPic.paint(0, 0, nullPic);
			fail("picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
		paintedPic.paint(0, 0, paintPic);
		check_for_component_equality(paintedPic.getPixel(0, 0), paintPic.getPixel(0, 0));
		check_for_component_equality(paintedPic.getPixel(0, 1), paintPic.getPixel(0, 1));
		check_for_component_equality(paintedPic.getPixel(1, 0), paintPic.getPixel(1, 0));
		check_for_component_equality(paintedPic.getPixel(1, 1), paintPic.getPixel(1, 1));
	}
	
	@Test
	public void paintImmutablePicture() {
		Picture nullPic = null;
		try {
			paintedPic1.paint(4, 0, paintPic);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPic1.paint(0, 4, paintPic);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPic1.paint(0, 0, nullPic);
			fail("picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
		Picture paintImmuPic = paintedPic1.paint(0, 0, paintPic);
		check_for_component_equality(paintImmuPic.getPixel(0, 0), paintPic.getPixel(0, 0));
		check_for_component_equality(paintImmuPic.getPixel(0, 1), paintPic.getPixel(0, 1));
		check_for_component_equality(paintImmuPic.getPixel(1, 0), paintPic.getPixel(1, 0));
		check_for_component_equality(paintImmuPic.getPixel(1, 1), paintPic.getPixel(1, 1));
		assertFalse(paintedPic1.getPixel(0, 0) == paintPic.getPixel(0, 0));
		assertFalse(paintedPic1.getPixel(0, 1) == paintPic.getPixel(0, 1));
		assertFalse(paintedPic1.getPixel(1, 0) == paintPic.getPixel(1, 0));
		assertFalse(paintedPic1.getPixel(1, 1) == paintPic.getPixel(1, 1));
	}
	
	@Test
	public void captionGetterSetter() {
		try {
			picture.setCaption(null);
			fail("Caption cannot be null.");
		} catch (IllegalArgumentException e) {
		}
		paintPic.setCaption("new caption");
		assertEquals(picture.getCaption(), "My caption");
		assertEquals(paintPic.getCaption(), "new caption");
	}
	
	
	@Test
	public void extractSubpicture() {
		try {
			SubPicture sub1 = picture.extract(5, 0, 2, 2);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			SubPicture sub2 = picture.extract(0, 5, 2, 2);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			SubPicture sub3 = picture.extract(0, 0, -2, 2);
			fail("Dx is illegal.");
		} catch (IllegalArgumentException e) {
		}
		try {
			SubPicture sub4 = picture.extract(0, 0, 2, -2);
			fail("Dy is illegal.");
		} catch (IllegalArgumentException e) {
		}
		SubPicture sub = picture.extract(0, 0, 2, 2);
		assertEquals(sub.getXOffset(), 0);
		assertEquals(sub.getYOffset(), 0);
		assertEquals(sub.getSource(), picture);
		assertEquals(sub.getPixel(0, 1), unit);
		assertEquals(sub.getPixel(0, 0), unit);
		assertEquals(sub.getPixel(0, 1), unit);
		assertEquals(sub.getPixel(1, 0), unit);
		assertEquals(sub.getPixel(1, 1), unit);
		assertEquals(sub.getHeight(), 2);
		assertEquals(sub.getWidth(), 2);
	}
	
	
	@Test
	public void testSampleIter() {
		try {
			Iterator<Pixel> iter1 = picture.sample(0, 4, 2, 2);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<Pixel> iter2 = picture.sample(4, 0, 1, 1);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<Pixel> iter3 = picture.sample(0, 0, -1, 2);
			fail("Dx is illegal.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<Pixel> iter4 = picture.sample(0, 0, 1, -1);
			fail("Dy is illegal.");
		} catch (IllegalArgumentException e) {
		}
		Iterator<Pixel> iter = picture.sample(0, 0, 2, 2);
		assertTrue(iter.hasNext());
		assertEquals(parray[0][0], iter.next());
		assertTrue(iter.hasNext());
		assertEquals(parray[2][0], iter.next());
		assertTrue(iter.hasNext());
		assertEquals(parray[0][2], iter.next());
		assertTrue(iter.hasNext());
		assertEquals(parray[2][2], iter.next());
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void testWindow() {
		try {
			Iterator<SubPicture> window1 = picture.window(0, 0);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<SubPicture> window2 = picture.window(-1, -2);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<SubPicture> window3 = picture.window(1, 4);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<SubPicture> window4 = picture.window(4, 1);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		Iterator<SubPicture> iterWd = picture.window(2, 2);
		assertTrue(iterWd.hasNext());
		check_for_picture_equality(picture.extract(0, 0, 2, 2), iterWd.next());
		assertTrue(iterWd.hasNext());
		check_for_picture_equality(picture.extract(1, 0, 2, 2), iterWd.next());
		assertTrue(iterWd.hasNext());
		check_for_picture_equality(picture.extract(0, 1, 2, 2), iterWd.next());
		assertTrue(iterWd.hasNext());
		check_for_picture_equality(picture.extract(1, 1, 2, 2), iterWd.next());
		assertFalse(iterWd.hasNext());
	}
	
	@Test
	public void testTile() {
		try {
			Iterator<SubPicture> tile1 = picture.tile(0, 0);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<SubPicture> tile1 = picture.tile(-1, -2);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<SubPicture> tile1 = picture.tile(1, 4);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			Iterator<SubPicture> tile1 = picture.tile(4, 1);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		Iterator<SubPicture> iterTl = picture.tile(2, 2);
		assertTrue(iterTl.hasNext());
		check_for_picture_equality(picture.extract(0, 0, 2, 2), iterTl.next());
		assertFalse(iterTl.hasNext());
	}
	
	@Test
	public void testZigZag() {
		Iterator<Pixel> iterZz = picture.zigzag();
		for(int i=0;i<picture.getWidth();i++) {
			for(int j=0; j<picture.getHeight(); j++) {
				Pixel a1 = picture.getPixel(i, j);
				check_for_component_equality(a1, iterZz.next());
			}
			
		}
	}
	
	@Test
	public void factorPaintMutable() {
		Picture nullPic = null;
		try {
			paintedPicFac.paint(4, 0, paintPic, 0.5);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac.paint(0, 4, paintPic, 0.5);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac.paint(0, 0, nullPic, 0.5);
			fail("picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac.paint(0, 0, nullPic, -1);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac.paint(0, 0, nullPic, 2);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		paintedPicFac.paint(0, 0, paintPic,0.5);
		Pixel pf1 = paintedPic.getPixel(0, 0).blend(paintPic.getPixel(0, 0), 0.5);
		Pixel pf2 = paintedPic.getPixel(0, 0).blend(paintPic.getPixel(0, 1), 0.5);
		Pixel pf3 = paintedPic.getPixel(0, 0).blend(paintPic.getPixel(1, 0), 0.5);
		Pixel pf4 = paintedPic.getPixel(0, 0).blend(paintPic.getPixel(1, 1), 0.5);
		check_for_component_equality(paintedPicFac.getPixel(0, 0), pf1);
		check_for_component_equality(paintedPicFac.getPixel(0, 1), pf2);
		check_for_component_equality(paintedPicFac.getPixel(1, 0), pf3);
		check_for_component_equality(paintedPicFac.getPixel(1, 1), pf4);
	}
	
	@Test
	public void factorPaintImmutable() {
		Picture nullPic = null;
		try {
			paintedPicFac1.paint(4, 0, paintPic, 0.5);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac1.paint(0, 4, paintPic, 0.5);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac1.paint(0, 0, nullPic, 0.5);
			fail("picture cannot be null.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac1.paint(0, 0, nullPic, -1);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		try {
			paintedPicFac1.paint(0, 0, nullPic, 2);
			fail("Out of range.");
		} catch (IllegalArgumentException e) {
		}
		Picture facImmuPic = paintedPicFac1.paint(0, 0, paintPic,0.5);
		Pixel pf1 = paintedPic1.getPixel(0, 0).blend(paintPic.getPixel(0, 0), 0.5);
		Pixel pf2 = paintedPic1.getPixel(0, 0).blend(paintPic.getPixel(0, 1), 0.5);
		Pixel pf3 = paintedPic1.getPixel(0, 0).blend(paintPic.getPixel(1, 0), 0.5);
		Pixel pf4 = paintedPic1.getPixel(0, 0).blend(paintPic.getPixel(1, 1), 0.5);
		check_for_component_equality(facImmuPic.getPixel(0, 0), pf1);
		check_for_component_equality(facImmuPic.getPixel(0, 1), pf2);
		check_for_component_equality(facImmuPic.getPixel(1, 0), pf3);
		check_for_component_equality(facImmuPic.getPixel(1, 1), pf4);
		assertFalse(paintedPic1.getPixel(0, 0) == facImmuPic.getPixel(0, 0));
		assertFalse(paintedPic1.getPixel(0, 1) == facImmuPic.getPixel(0, 1));
		assertFalse(paintedPic1.getPixel(1, 0) == facImmuPic.getPixel(1, 0));
		assertFalse(paintedPic1.getPixel(1, 1) == facImmuPic.getPixel(1, 1));
		
	}
	
	
	private static boolean check_for_component_equality(Pixel a, Pixel b) {
		assertEquals(a.getRed(), b.getRed(), 0.001);
		assertEquals(a.getGreen(), b.getGreen(), 0.001);
		assertEquals(a.getBlue(), b.getBlue(), 0.001);

		return true;
	}
	
	private static boolean check_for_picture_equality(Picture a, Picture b) {
		if(a.getWidth() != b.getWidth() || a.getHeight() != b.getHeight()) {
			throw new IllegalArgumentException("cannot compare");
		}
		for(int i=0;i<a.getWidth();i++) {
			for(int j=0; j<a.getHeight(); j++) {
				Pixel a1 = a.getPixel(i, j);
				Pixel b1 = b.getPixel(i, j);
				check_for_component_equality(a1, b1);
			}
			
		}

		return true;
	}
	

}
