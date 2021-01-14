package a5Tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import a5.*;

public class a5Test {
		
	static public String[] getTestNames() {
		String[] test_names = new String[10];
		
		test_names[0] = "pictureCaptionTest";
		test_names[1] = "newPaintMethodsTest";
		test_names[2] = "extractMethodTest";
		test_names[3] = "subPictureInterfaceTest";
		test_names[4] = "sampleIteratorTest";
		test_names[5] = "windowIteratorTest";
		test_names[6] = "tileIteratorTest";
		test_names[7] = "zigzagIteratorTest";
		test_names[8] = "iteratorTestWithChangesInMiddle";
		test_names[9] = "iteratorIndependenceTest";
		
		return test_names;
	}
	
	// Initialize different pixel amounts.
	Pixel red = new ColorPixel(1, 0, 0);
	Pixel green = new ColorPixel(0, 1, 0);
	Pixel blue = new ColorPixel(0, 0, 1);
	Pixel purple = new ColorPixel(1, 0, 1);
	Pixel cyan = new ColorPixel(0, 1, 1);
	Pixel yellow = new ColorPixel(1, 1, 0);
	Pixel redGreenBlend = new ColorPixel(0.5, 0.5, 0);
	Pixel redBlueBlend = new ColorPixel (0.5, 0, 0.5);
	Pixel greenBlueBlend = new ColorPixel(0, 0.5, 0.5);
	Pixel grayscale = new GrayPixel(0.5);
	
	// Valid 2D Pixel Arrays
	Pixel[][] allRed4x6Picture = { {red, red, red, red, red, red}, 
			{red, red, red, red, red, red}, 
			{red, red, red, red, red, red},
			{red, red, red, red, red, red} };
	
	Pixel[][] allBlue4x6Picture = { {blue, blue, blue, blue, blue, blue}, 
			{blue, blue, blue, blue, blue, blue}, 
			{blue, blue, blue, blue, blue, blue}, 
			{blue, blue, blue, blue, blue, blue} };
	
	Pixel[][] allBlue5x5Picture = { {blue, blue, blue, blue, blue},
			{blue, blue, blue, blue, blue},
			{blue, blue, blue, blue, blue},
			{blue, blue, blue, blue, blue},
			{blue, blue, blue, blue, blue} };
	
	Pixel[][] allBlue2x2Picture = { {blue, blue}, {blue, blue} };
	
	Pixel[][] rainbow6x6Picture = { {red, green, blue, purple, cyan, yellow}, 
			{green, blue, purple, cyan, yellow, red}, 
			{blue, purple, cyan, yellow, red, green}, 
			{purple, cyan, yellow, red, green, blue}, 
			{cyan, yellow, red, green, blue, purple}, 
			{yellow, red, green, blue, purple, cyan} };
	
	// 11x11 Pixel Array with red, green, blue alternating for each pixel
	Pixel[][] rgb11x11Picture = { {red, blue, green, red, blue, green, red, blue, green, red, blue},
			{blue, green, red, blue, green, red, blue, green, red, blue, green}, 
			{green, red, blue, green, red, blue, green, red, blue, green, red}, 
			{red, blue, green, red, blue, green, red, blue, green, red, blue}, 
			{blue, green, red, blue, green, red, blue, green, red, blue, green}, 
			{green, red, blue, green, red, blue, green, red, blue, green, red}, 
			{red, blue, green, red, blue, green, red, blue, green, red, blue}, 
			{blue, green, red, blue, green, red, blue, green, red, blue, green}, 
			{green, red, blue, green, red, blue, green, red, blue, green, red}, 
			{red, blue, green, red, blue, green, red, blue, green, red, blue}, 
			{blue, green, red, blue, green, red, blue, green, red, blue, green} };
	
	Pixel[][] rainbow1x6Picture = { {red, green, blue, purple, cyan, yellow} };
	
	Pixel[][] rainbow6x1Picture = { {red}, {green}, {blue}, {purple}, {cyan}, {yellow} };
	
	Pixel[][] rainbow6x2Picture = { {red, green, blue, purple, cyan, yellow}, 
			{green, blue, purple, cyan, yellow, red} };
	
	
	/* Tests the new String caption of the Picture interface, to see if it can
	 * be set and gotten correctly.
	 */
	
	@Test
	public void pictureCaptionTest() {
		Picture testPicture1 = new MutablePixelArrayPicture(allRed4x6Picture, "test 1");
		
		assertEquals("test 1", testPicture1.getCaption());
		testPicture1.setCaption("My caption is changed!");
		assertEquals("My caption is changed!", testPicture1.getCaption());

		// Test Immutable Pixel Array Picture caption's mutability
		Picture testPicture2 = new ImmutablePixelArrayPicture(allBlue5x5Picture, "test 2");
		
		assertEquals("test 2", testPicture2.getCaption());
		testPicture2.setCaption("I'm different!");
		assertEquals("I'm different!", testPicture2.getCaption());
	}
	
	
	/* Tests the new paint method of the Picture interface, by which one 
	 * Picture's pixel information is painted onto another.
	 * 
	 * Parts tested include: 
	 * simple testing of painting an area, 
	 * out-of-bounds parameters,
	 * factor parameter in the paint method, 
	 * differences in how Mutable and Immutable Pictures react to painting.
	 */
	
	@Test
	public void newPaintMethodsTest() {
		// Initialize test Pictures; cloning of Pixel Arrays used to protect
		// them for future tests and in this very test, except in case of 
		// immutable Pictures.
		Picture toBePainted = new MutablePixelArrayPicture(allRed4x6Picture.clone(), "toBePainted");
		Picture immutableToBePainted = new ImmutablePixelArrayPicture(allRed4x6Picture, "immutableToBePainted");
		Picture toPaintWith = new MutablePixelArrayPicture(allBlue2x2Picture.clone(), "toPaintWith");
		Picture toPaintWith2 = new MutablePixelArrayPicture(allBlue5x5Picture.clone(), "toPaintWith2");
		
		
		// Check that the original Mutable Picture itself changes by painting
		
		toBePainted.paint(0, 0, toPaintWith);
		
		// For loop must use assertTrue with getPixel.equals as blend method 
		// will generate new Pixel object to paint over the picture.
		// So assertEquals will not work here.
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				if (i < 2 && j < 2) {
					assertTrue(toBePainted.getPixel(i, j).equals(blue));
				} else {
					assertTrue(toBePainted.getPixel(i, j).equals(red));
				}
			}
		}
		
		// Check that the factor works
		toBePainted.paint(2, 4, toPaintWith, 0.5);
		for (int i = 2; i < 4; i++) {
			for (int j = 4; j < 6; j++) {
				assertTrue(toBePainted.getPixel(i, j).equals(redBlueBlend));
			}
		}
		
		// Check that the original Immutable Picture is not changed by painting
		Picture immutableAfterPainting = immutableToBePainted.paint(0, 0, toPaintWith);
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 6; j++) {
				assertEquals(red, immutableToBePainted.getPixel(i, j));
				if (i < 2 && j < 2) {
					assertTrue(immutableAfterPainting.getPixel(i, j).equals(blue));
				} else {
					assertTrue(immutableAfterPainting.getPixel(i, j).equals(red));
				}
			}
		}
		
		// Check that paint treats the parameters being out of bounds correctly
		try {
			toBePainted.paint(-1, 4, toPaintWith);
			fail("Picture paint method should throw IllegalArgumentException" + 
					"when parameters are out of bounds!");
		} catch (Exception e) {
		}

		// Check that paint only paints the part of the picture that's
		// in-bounds when the toBePainted overlaps with an out-of-bounds area.
		try {
			Picture immutableAfterPainting2 = immutableToBePainted.paint(0, 2, toPaintWith2);
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 2; j++) {
					assertTrue(immutableAfterPainting2.getPixel(i, j).equals(red));
				}
				for (int j = 2; j < 6; j++) {
					assertTrue(immutableAfterPainting2.getPixel(i, j).equals(blue));
				}
			}
		} catch (Exception e) {
			fail("Picture paint method should not throw " + 
					"IllegalArgumentException when parameters are > 0!");
		}
		
	}
	

	/* Tests the extract method of the Picture interface, by which a new 
	 * SubPicture is created from the old picture.
	 * 
	 * Parts tested are:
	 * the area extracted by the method is correct, 
	 * Immutable and Mutable Pictures act... the same... with extraction, 
	 * the captions are correct for extracted pictures.
	 */
	
	@Test
	public void extractMethodTest() {
		// Initialize pictures to extract from.
		Picture toExtract = new MutablePixelArrayPicture(rainbow6x6Picture.clone(), "toExtract");
		Picture immutableToExtract = new ImmutablePixelArrayPicture(rainbow6x6Picture, "immutableToExtract");
		
		// Check that extraction works on the entire picture
		Picture extracted1 = toExtract.extract(0, 0, 6, 6);
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				assertEquals(toExtract.getPixel(i, j), extracted1.getPixel(i, j));
			}
		}
		
		// Check that extraction will throw an exception if the parameters are 
		// incorrect
		try {
			Picture tooLargeToExtract = toExtract.extract(0, 0, 7, 7);
			fail("Method should throw exception when parameters of picture" + 
					"to be extracted are out of bounds!");
		} catch (RuntimeException e) {
		}
		
		try {
			Picture extractionOutOfBounds = toExtract.extract(5, 5, 2, 2);
			fail("Method should throw exception when parameters of picture" + 
					"to be extracted are out of bounds!");
		} catch (RuntimeException e) {
		}
		
		try {
			Picture extractedWidthOrLengthIsNegative = toExtract.extract(3, 3, -1, 1);
			fail("Method should throw exception when parameters of picture" + 
					"to be extracted are out of bounds!");
		} catch (RuntimeException e) {
		}
		
		try {
			Picture extractedWidthOrLengthIs0 = toExtract.extract(3, 3, 0, 2);
			fail("Method should throw exception when parameters of picture" + 
					"to be extracted are out of bounds!");
		} catch (RuntimeException e) {
		}
		
		// Check that extraction works on a small part of the picture
		Picture extracted2 = toExtract.extract(3, 3, 2, 1);
		assertEquals(toExtract.getPixel(3, 3), extracted2.getPixel(0, 0));
		assertEquals(toExtract.getPixel(4, 3), extracted2.getPixel(1, 0));
		
		Picture extracted3 = toExtract.extract(2, 2, 2, 2);
		assertEquals(toExtract.getPixel(2, 2), extracted3.getPixel(0, 0));
		assertEquals(toExtract.getPixel(3, 2), extracted3.getPixel(1, 0));
		assertEquals(toExtract.getPixel(2, 3), extracted3.getPixel(0, 1));
		assertEquals(toExtract.getPixel(3, 3), extracted3.getPixel(1, 1));
		
		// Check that extraction, inexplicably, DOESN'T clone pixels in the 
		// case of Immutable Picture.
		Picture extractedFromImmutable = immutableToExtract.extract(1, 1, 4, 4);
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 5; j++) {
				assertEquals(immutableToExtract.getPixel(i, j), extractedFromImmutable.getPixel(i - 1, j - 1));
			}
		}
		
		// Check that the caption information of extracted pictures matches 
		// that of the parent.
		assertEquals(toExtract.getCaption(), extracted1.getCaption());
		assertEquals(immutableToExtract.getCaption(), extractedFromImmutable.getCaption());
		
	}
	
	
	/* Tests the new SubPicture interface to ensure that it works correctly. 
	 * 
	 * Parts tested include:
	 * getXOffset and getYOffset methods,
	 * getSource method, 
	 * independent captions from its parent Picture,
	 * the SubPicture's paint methods returning itself when Mutable and
	 *     a new SubPicture when Immutable.
	 */
	
	@Test
	public void subPictureInterfaceTest() {
		// Initialize Pictures from which SubPictures will be extracted.
		Picture mutableToExtract = new MutablePixelArrayPicture(rainbow6x6Picture.clone(), "mutableToExtract");
		Picture immutableToExtract = new ImmutablePixelArrayPicture(rainbow6x6Picture, "immutableToExtract");
		
		// Check that the getOffset methods in SubPicture interface work.
		SubPicture offset2x1 = mutableToExtract.extract(2, 1, 2, 2);
		assertEquals(2, offset2x1.getXOffset());
		assertEquals(1, offset2x1.getYOffset());
		
		SubPicture immutableOffset2x1 = immutableToExtract.extract(2, 1, 2, 2);
		assertEquals(2, immutableOffset2x1.getXOffset());
		assertEquals(1, immutableOffset2x1.getYOffset());
		
		// Check that getSource method works.
		assertEquals(mutableToExtract, offset2x1.getSource());
		assertEquals(immutableToExtract, immutableOffset2x1.getSource());
		
		// Check that the SubPicture's paint methods return itself painted 
		// when the parent is Mutable, and a new SubPicture object when the 
		// parent is Immutable.
		
		offset2x1.paint(0, 0, 1, 1, green);
		assertTrue(offset2x1.getPixel(0, 0).equals(green));
		assertTrue(offset2x1.getPixel(1, 0).equals(green));
		assertTrue(offset2x1.getPixel(0, 1).equals(green));
		assertTrue(offset2x1.getPixel(1, 1).equals(green));
		
		immutableOffset2x1.paint(0, 0, 1, 1, green);
		assertFalse(immutableOffset2x1.getPixel(0, 0).equals(green));
		assertFalse(immutableOffset2x1.getPixel(1, 0).equals(green));
		assertFalse(immutableOffset2x1.getPixel(0, 1).equals(green));
		assertFalse(immutableOffset2x1.getPixel(1, 1).equals(green));
		
		offset2x1.setCaption("Now I'm different!");
		immutableOffset2x1.setCaption("I'm different now too!");
		assertNotEquals(mutableToExtract.getCaption(), offset2x1.getCaption());
		assertNotEquals(immutableToExtract.getCaption(), immutableOffset2x1.getCaption());
		assertEquals("Now I'm different!", offset2x1.getCaption());
		assertEquals("I'm different now too!", immutableOffset2x1.getCaption());
	}
	
	
	
	/* Tests the sample method of a Picture object.
	 * 
	 * Parts tested include:
	 * iterating through every single pixel of the picture, 
	 * iterating through evenly spaced pixels of the picture without going
	 *     out of bounds,
	 * iterating when starting at a random coordinate of the picture, 
	 * throwing an exception when the starting coordinate is out of bounds, 
	 * skipping an out-of-bounds pixel when the iterator gets there.
	 */
	@Test
	public void sampleIteratorTest() {
		// Initialize Pictures to be sampled and sample Iterators.
		Picture pictureToSample = new MutablePixelArrayPicture(rainbow6x6Picture.clone(), "pictureToSample");
		Picture unevenPictureToSample = new MutablePixelArrayPicture(rgb11x11Picture.clone(), "unevenPictureToSample");
		Iterator<Pixel> allPixelSampler = pictureToSample.sample(0, 0, 1, 1);
		Iterator<Pixel> noOutOfBoundsSampler = pictureToSample.sample(0, 0, 2, 2);
		Iterator<Pixel> notStartingFrom0Sampler = unevenPictureToSample.sample(1, 1, 3, 4);
		
		// Check that sample Iterator works for each pixel in the picture.
		for (int j = 0; j < 6; j++) {
			for (int i = 0; i < 6; i++) {
				assertTrue(allPixelSampler.hasNext());
				assertEquals(pictureToSample.getPixel(i, j), allPixelSampler.next());
			}
		}
		assertFalse(allPixelSampler.hasNext());
		
		// Check that sample Iterator works for evenly spaced pixel samples, 
		// with no pixel to be sampled going out of bounds.
		for (int j = 0; j < 6; j += 2) {
			for (int i = 0; i < 6; i += 2) {
				assertTrue(noOutOfBoundsSampler.hasNext());
				assertEquals(pictureToSample.getPixel(i, j), noOutOfBoundsSampler.next());
			}
		}
		assertFalse(noOutOfBoundsSampler.hasNext());
		
		// Check that exception is thrown when sampler is created out-of-bounds
		try {
			Iterator<Pixel> invalidSampler = pictureToSample.sample(10, 10, 1, 1);
			fail("Sampler should throw exception when starting parameters " + 
					"are out-of-bounds!");
		} catch (RuntimeException e) {
		}
		
		// Check that the iterator proceeds correctly when not starting from 
		// (0, 0), and that it works when it samples outside of the picture's 
		// boundaries by skipping to the next row.
		assertEquals(unevenPictureToSample.getPixel(1, 1), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(4, 1), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(7, 1), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(10, 1), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(1, 5), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(4, 5), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(7, 5), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(10, 5), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(1, 9), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(4, 9), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(7, 9), notStartingFrom0Sampler.next());
		assertEquals(unevenPictureToSample.getPixel(10, 9), notStartingFrom0Sampler.next());
		assertFalse(notStartingFrom0Sampler.hasNext());
		
	}
	
	
	/* Tests the window method of a Picture object.
	 * 
	 * Parts tested include:
	 * producing correct windows when looking at every single pixel, 
	 * producing correct windows when looking at larger windows, 
	 * throwing an exception when window size is larger than the Picture, 
	 * correct captions on all SubPicture objects.
	 */
	
	@Test
	public void windowIteratorTest() {
		// Initialize Pictures and Window Iterators for testing.
		Picture toBeWindowed = new MutablePixelArrayPicture(rgb11x11Picture.clone(), "toBeWindowed");
		Iterator<SubPicture> window1x1 = toBeWindowed.window(1, 1);
		Iterator<SubPicture> window4x4 = toBeWindowed.window(4, 4);
		Iterator<SubPicture> window10x10ForCaptions = toBeWindowed.window(10, 10);
		
		// Check that correct windows are produced when going pixel by pixel.
		for (int j = 0; j < 11; j++) {
			for (int i = 0; i < 11; i++) {
				assertTrue(toBeWindowed.extract(i, j, 1, 1).getPixel(0, 0).equals(window1x1.next().getPixel(0, 0)));
			}
		}
		assertFalse(window1x1.hasNext());
		
		// Check that correct windows are produced with a square, larger window
		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 8; i++) {
				SubPicture tempExtract = toBeWindowed.extract(i, j, 4, 4);
				SubPicture windowCompare = window4x4.next();
				for (int k = 0; k < 4; k++) {
					for (int l = 0; l < 4; l++) {
						assertTrue(tempExtract.getPixel(k, l).equals(windowCompare.getPixel(k, l)));
					}
				}
			}
		}
		
		// Check that window throws an exception when the window size 
		// parameters are greater than the Picture's own width and height.
		try {
			Iterator<SubPicture> window12x12 = toBeWindowed.window(12, 12);
			fail("Window method should throw an exception if the window" + 
					"width and height are greater than the Picture's own!");
		} catch (RuntimeException e) {
		}
		
		// Check that captions of SubPictures produced by the window method 
		// are the same as those of the parent.
		assertEquals(toBeWindowed.getCaption(), window10x10ForCaptions.next().getCaption());
		assertEquals(toBeWindowed.getCaption(), window10x10ForCaptions.next().getCaption());
		assertEquals(toBeWindowed.getCaption(), window10x10ForCaptions.next().getCaption());
		assertEquals(toBeWindowed.getCaption(), window10x10ForCaptions.next().getCaption());
	}
	
	
	/* Tests the tile method of a Picture object.
	 * 
	 * Parts tested include:
	 * producing tiles correctly when tiles fit originally picture perfectly, 
	 * not producing partial tiles when tile fit is not perfect, 
	 * throwing an exception when tile size is larger than the Picture,
	 * correct captions on Tile objects.
	 */
	
	@Test
	public void tileIteratorTest() {
		// Initialize Pictures and tile iterators.
		Picture toBeTiled = new MutablePixelArrayPicture(rgb11x11Picture.clone(), "toBeTiled");
		Iterator<SubPicture> tile1x1 = toBeTiled.tile(1, 1);
		Iterator<SubPicture> tile4x4 = toBeTiled.tile(4, 4);
		Iterator<SubPicture> tile5x3 = toBeTiled.tile(5, 3);
		Iterator<SubPicture> tile10x5CaptionTest = toBeTiled.tile(10, 5);
		
		// Check that tiling every single pixel in the Picture works.
		for (int i = 0; i < 11; i++) {
			for (int j = 0; j < 11; j++) {
				assertTrue(toBeTiled.getPixel(i, j).equals(tile1x1.next().getPixel(0, 0)));
			}
		}
		assertFalse(tile1x1.hasNext());
		
		// Check that uneven square tilings of the Picture work, and partial 
		// tiles are not produced.
		for (int j = 0; j < 2; j++) {
			for (int i = 0; i < 2; i++) {
				SubPicture tempExtract = toBeTiled.extract(i * 4, j * 4, 4, 4);
				SubPicture tileCompare = tile4x4.next();
				for (int k = 0; k < 4; k++) {
					for (int l = 0; l < 4; l++) {
						assertTrue(tempExtract.getPixel(k, l).equals(tileCompare.getPixel(k, l)));
					}
				}
			}
		}
		assertFalse(tile4x4.hasNext());
		
		// Check that uneven rectangular tilings of the Picture work.
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 2; i++) {
				SubPicture tempExtract = toBeTiled.extract(i * 5, j * 3, 5, 3);
				SubPicture tileCompare = tile5x3.next();
				for (int k = 0; k < 5; k++) {
					for (int l = 0; l < 3; l++) {
						assertTrue(tempExtract.getPixel(k, l).equals(tileCompare.getPixel(k, l)));
					}
				}
			}
		}
		assertFalse(tile5x3.hasNext());
		
		// Check that exception is thrown when tile size is larger than Picture
		try {
			Iterator<SubPicture> invalidTiling = toBeTiled.tile(12, 12);
			fail("Exception should be thrown when tile size is larger" + 
					"than the Picture itself!");
		} catch (Exception e) {
		}
		
		// Check that captions of SubPictures produced by Tile match those 
		// of the parent.
		assertEquals("toBeTiled", tile10x5CaptionTest.next().getCaption());
		assertEquals("toBeTiled", tile10x5CaptionTest.next().getCaption());
		
	}
	
	
	/* Tests the zigzag method of a Picture object.
	 * 
	 * Parts tested include: 
	 * square pictures, 
	 * non-square rectangular pictures,
	 * single-pixel pictures.
	 */
	@Test
	public void zigzagIteratorTest() {
		// Initialize pictures and zigzag iterators to test.
		Picture rainbowToZigzag = new MutablePixelArrayPicture(rainbow6x6Picture.clone(), "rainbowToZigzag");
		Picture rainbowRectangleToZigzag = new MutablePixelArrayPicture(rainbow6x2Picture.clone(), "rainbowRectangleToZigzag");
		Picture horizontalLineToZigzag = new MutablePixelArrayPicture(rainbow6x1Picture.clone(), "horizontalLineToZigzag");
		Picture verticalLineToZigzag = new MutablePixelArrayPicture(rainbow1x6Picture.clone(), "verticalLineToZigzag");
		Iterator<Pixel> rainbowZigzagged = rainbowToZigzag.zigzag();
		Iterator<Pixel> rainbowRectangleZigzagged = rainbowRectangleToZigzag.zigzag();
		Iterator<Pixel> horizontalLineZigzagged = horizontalLineToZigzag.zigzag();
		Iterator<Pixel> verticalLineZigzagged = verticalLineToZigzag.zigzag();
		
		// Check that zigzag works on square pictures.
		assertEquals(rainbowToZigzag.getPixel(0, 0), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(1, 0), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(0, 1), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(0, 2), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(1, 1), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(2, 0), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(3, 0), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(2, 1), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(1, 2), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(0, 3), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(0, 4), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(1, 3), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(2, 2), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(3, 1), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(4, 0), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(5, 0), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(4, 1), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(3, 2), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(2, 3), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(1, 4), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(0, 5), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(1, 5), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(2, 4), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(3, 3), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(4, 2), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(5, 1), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(5, 2), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(4, 3), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(3, 4), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(2, 5), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(3, 5), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(4, 4), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(5, 3), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(5, 4), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(4, 5), rainbowZigzagged.next());
		assertEquals(rainbowToZigzag.getPixel(5, 5), rainbowZigzagged.next());
		assertFalse(rainbowZigzagged.hasNext());
		
		// Check that zigzag works on rectangular pictures.
		assertEquals(rainbowRectangleToZigzag.getPixel(0, 0), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(1, 0), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(0, 1), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(0, 2), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(1, 1), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(1, 2), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(0, 3), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(0, 4), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(1, 3), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(1, 4), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(0, 5), rainbowRectangleZigzagged.next());
		assertEquals(rainbowRectangleToZigzag.getPixel(1, 5), rainbowRectangleZigzagged.next());
		assertFalse(rainbowRectangleZigzagged.hasNext());
		
		// Check that zigzag works on single pixel pictures.
		assertEquals(horizontalLineToZigzag.getPixel(0, 0), horizontalLineZigzagged.next());
		assertEquals(horizontalLineToZigzag.getPixel(1, 0), horizontalLineZigzagged.next());
		assertEquals(horizontalLineToZigzag.getPixel(2, 0), horizontalLineZigzagged.next());
		assertEquals(horizontalLineToZigzag.getPixel(3, 0), horizontalLineZigzagged.next());
		assertEquals(horizontalLineToZigzag.getPixel(4, 0), horizontalLineZigzagged.next());
		assertEquals(horizontalLineToZigzag.getPixel(5, 0), horizontalLineZigzagged.next());
		assertFalse(horizontalLineZigzagged.hasNext());
		
		assertEquals(verticalLineToZigzag.getPixel(0, 0), verticalLineZigzagged.next());
		assertEquals(verticalLineToZigzag.getPixel(0, 1), verticalLineZigzagged.next());
		assertEquals(verticalLineToZigzag.getPixel(0, 2), verticalLineZigzagged.next());
		assertEquals(verticalLineToZigzag.getPixel(0, 3), verticalLineZigzagged.next());
		assertEquals(verticalLineToZigzag.getPixel(0, 4), verticalLineZigzagged.next());
		assertEquals(verticalLineToZigzag.getPixel(0, 5), verticalLineZigzagged.next());
		assertFalse(verticalLineZigzagged.hasNext());
		
	}
	
	
	/* Tests that iterators will detect when the source Picture object has 
	 * changed and react appropriately, as "appropriately" is defined in 
	 * this assignment by KMP.
	 * 
	 * sample, window, tile, and zigzag iterators tested.
	 */
	@Test
	public void iteratorTestWithChangesInMiddle() {
		// Initialize Pictures and Iterators.
		Picture thisChangesInMiddle = new MutablePixelArrayPicture(allRed4x6Picture.clone(), "thisChangesInMiddle");
		Picture changeToThis = new MutablePixelArrayPicture(allBlue4x6Picture.clone(), "changeToThis");
		Iterator<Pixel> sampler = thisChangesInMiddle.sample(0, 0, 1, 1);
		Iterator<Pixel> zigzagger = thisChangesInMiddle.zigzag();
		Iterator<SubPicture> windower = thisChangesInMiddle.window(1, 1);
		Iterator<SubPicture> tiler = thisChangesInMiddle.tile(1, 1);
		
		// Run each iterator once.
		sampler.next();
		zigzagger.next();
		windower.next();
		tiler.next();
		
		// Change picture.
		thisChangesInMiddle.paint(0, 0, changeToThis);
		
		// Check that iterators react appropriately; in this case, by now 
		// iterating from the new Picture's pixel arrays.
		assertTrue(sampler.next().equals(blue));
		assertTrue(zigzagger.next().equals(blue));
		assertEquals(thisChangesInMiddle.extract(1, 0, 1, 1).getPixel(0, 0), windower.next().getPixel(0, 0));
		assertEquals(thisChangesInMiddle.extract(1, 0, 1, 1).getPixel(0, 0), tiler.next().getPixel(0, 0));
		
	}
	
	
	/* Tests that multiple versions of each iterator can run at once, as 
	 * good design would dictate.
	 * 
	 * sample, window, tile, and zigzag iterators tested.
	 */
	
	@Test
	public void iteratorIndependenceTest() {
		// Initialize Pictures and different iterator instances to test.
		// Each sampler can be called 6 times,
		// each zigzagger 36 times,
		// each windower 4 times,
		// each tiler 4 times.
		Picture iteratedPicture = new MutablePixelArrayPicture(rainbow6x6Picture, "iteratedPicture");
		Iterator<Pixel> sampler1 = iteratedPicture.sample(0, 0, 2, 3);
		Iterator<Pixel> sampler2 = iteratedPicture.sample(0, 0, 2, 3);
		Iterator<Pixel> zigzagger1 = iteratedPicture.zigzag();
		Iterator<Pixel> zigzagger2 = iteratedPicture.zigzag();
		Iterator<SubPicture> windower1 = iteratedPicture.window(5, 5);
		Iterator<SubPicture> windower2 = iteratedPicture.window(5, 5);
		Iterator<SubPicture> tiler1 = iteratedPicture.tile(3, 3);
		Iterator<SubPicture> tiler2 = iteratedPicture.tile(3, 3);
		
		// Set up variables used for comparing iterators vs. extracted
		SubPicture tempExtract;
		SubPicture compare;
		
		// Check that different iterators can run at the same time.
		// sample iterator
		// Each sampler called 4 times here. 2 calls left each.
		assertEquals(iteratedPicture.getPixel(0, 0), sampler1.next());
		assertEquals(iteratedPicture.getPixel(2, 0), sampler1.next());
		assertEquals(iteratedPicture.getPixel(0, 0), sampler2.next());
		assertEquals(iteratedPicture.getPixel(2, 0), sampler2.next());
		assertEquals(iteratedPicture.getPixel(4, 0), sampler1.next());
		assertEquals(iteratedPicture.getPixel(4, 0), sampler2.next());
		assertEquals(iteratedPicture.getPixel(0, 3), sampler2.next());
		assertEquals(iteratedPicture.getPixel(0, 3), sampler1.next());
		
		
		// zigzag iterator
		// Each zigzagger called 8 times here. 28 calls left each.
		assertEquals(iteratedPicture.getPixel(0, 0), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(1, 0), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(0, 0), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(1, 0), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(0, 1), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(0, 2), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(1, 1), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(2, 0), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(0, 1), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(0, 2), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(1, 1), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(2, 0), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(3, 0), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(3, 0), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(2, 1), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(2, 1), zigzagger2.next());
		
		
		// window iterator
		// Each windower called twice here. 2 calls left each.
		tempExtract = iteratedPicture.extract(0, 0, 5, 5);
		compare = windower1.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		compare = windower2.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		tempExtract = iteratedPicture.extract(1, 0, 5, 5);
		compare = windower2.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		compare = windower1.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		
		// tile iterator
		// Each tiler called twice here. 2 calls left each.
		tempExtract = iteratedPicture.extract(0, 0, 3, 3);
		compare = tiler2.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		compare = tiler1.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		tempExtract = iteratedPicture.extract(3, 0, 3, 3);
		compare = tiler1.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		compare = tiler2.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		
		// all four at once
		// each sampler called once
		// each zigzagger called twice
		// each windower called once
		// each tiler called once
		
		// Call tiler1 first. 1 call left.
		tempExtract = iteratedPicture.extract(0, 3, 3, 3);
		compare = tiler1.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		// Call sampler2 once. 1 call left.
		assertEquals(iteratedPicture.getPixel(2, 3), sampler2.next());
		
		// Call windower2 first. 1 call left.
		tempExtract = iteratedPicture.extract(0, 1, 5, 5);
		compare = windower2.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		// Call zigzagger1 first. 27 calls left.
		assertEquals(iteratedPicture.getPixel(1, 2), zigzagger1.next());
		
		// Call windower1. 1 call left.
		compare = windower1.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		// Call zigzagger2. 27 calls left.
		assertEquals(iteratedPicture.getPixel(1, 2), zigzagger2.next());
		
		// Call sampler1 once. 1 call left.
		assertEquals(iteratedPicture.getPixel(2, 3), sampler1.next());
		
		// Call tiler2. 1 call left.
		tempExtract = iteratedPicture.extract(0, 3, 3, 3);
		compare = tiler2.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		// Call last call of sampler1.
		assertEquals(iteratedPicture.getPixel(4, 3), sampler1.next());
		
		assertFalse(sampler1.hasNext());
		assertTrue(sampler2.hasNext());
		
		// Call last call of sampler2.
		assertEquals(iteratedPicture.getPixel(4, 3), sampler2.next());
		
		assertFalse(sampler2.hasNext());
		
		// zigzagger1 called remaining 27 times 
		assertEquals(iteratedPicture.getPixel(0, 3), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(0, 4), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(1, 3), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(2, 2), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(3, 1), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(4, 0), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(5, 0), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(4, 1), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(3, 2), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(2, 3), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(1, 4), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(0, 5), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(1, 5), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(2, 4), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(3, 3), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(4, 2), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(5, 1), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(5, 2), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(4, 3), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(3, 4), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(2, 5), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(3, 5), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(4, 4), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(5, 3), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(5, 4), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(4, 5), zigzagger1.next());
		assertEquals(iteratedPicture.getPixel(5, 5), zigzagger1.next());
		
		assertFalse(zigzagger1.hasNext());
		assertTrue(zigzagger2.hasNext());
		
		// zigzagger2 called remaining 27 times
		assertEquals(iteratedPicture.getPixel(0, 3), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(0, 4), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(1, 3), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(2, 2), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(3, 1), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(4, 0), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(5, 0), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(4, 1), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(3, 2), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(2, 3), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(1, 4), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(0, 5), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(1, 5), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(2, 4), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(3, 3), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(4, 2), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(5, 1), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(5, 2), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(4, 3), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(3, 4), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(2, 5), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(3, 5), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(4, 4), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(5, 3), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(5, 4), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(4, 5), zigzagger2.next());
		assertEquals(iteratedPicture.getPixel(5, 5), zigzagger2.next());
		
		assertFalse(zigzagger2.hasNext());
		
		// Call last call of windower1.
		tempExtract = iteratedPicture.extract(1, 1, 5, 5);
		compare = windower1.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		assertFalse(windower1.hasNext());
		assertTrue(windower2.hasNext());
		
		// Call last call of windower2.
		compare = windower2.next();
		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		assertFalse(windower2.hasNext());
		
		// Call last call of tiler1.
		tempExtract = iteratedPicture.extract(3, 3, 3, 3);
		compare = tiler1.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		assertFalse(tiler1.hasNext());
		assertTrue(tiler2.hasNext());
		
		// Call last call of tiler2.
		compare = tiler2.next();
		for (int j = 0; j < 3; j++) {
			for (int i = 0; i < 3; i++) {
				assertTrue(tempExtract.getPixel(i, j).equals(compare.getPixel(i, j)));
			}
		}
		
		assertFalse(tiler2.hasNext());
		
	}
}