package a5Tests;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import a5.ColorPixel;
import a5.ImmutablePixelArrayPicture;
import a5.MutablePixelArrayPicture;
import a5.Picture;
import a5.Pixel;
import a5.SubPicture;

public class Test5 {
	
	Pixel red = new ColorPixel(1,0,0);
	Pixel green = new ColorPixel (0,1,0);
	Pixel blue = new ColorPixel (0,0,1);
	Pixel[] firstRow = new Pixel [] { blue, blue, red, red};
	Pixel[] secondRow = new Pixel [] {blue, blue, red, red};
	Pixel[] thirdRow = new Pixel [] {green, green, red, red};
	Pixel[] fourthRow = new Pixel [] {green, green, red, red};
	Pixel[][] multicoloredPixels = new Pixel[][] {firstRow, secondRow, thirdRow, fourthRow};
	Picture multicoloredPicture = new MutablePixelArrayPicture(multicoloredPixels, "Hello World");
	SubPicture test = multicoloredPicture.extract(1, 1, 3, 3);
	
	Pixel[][] multicoloredPixelsImmutable = new Pixel[][] {new Pixel [] {new ColorPixel(0,0,1), new ColorPixel(0,0,1), new ColorPixel(1,0,0) , new ColorPixel(1,0,0)}
	, new Pixel [] {new ColorPixel(0,0,1), new ColorPixel(0,0,1), new ColorPixel(1,0,0) , new ColorPixel(1,0,0)}, 
	new Pixel [] {new ColorPixel(0,1,0), new ColorPixel (0,1,0), new ColorPixel(1,0,0), new ColorPixel(1,0,0)}, 
	new Pixel [] {new ColorPixel (0,1,0), new ColorPixel(0,1,0), new ColorPixel(1,0,0), new ColorPixel(1,0,0)}};
	Picture multicoloredPictureImmutable = new ImmutablePixelArrayPicture(multicoloredPixelsImmutable, "Hello World");
	SubPicture testImmutable = multicoloredPictureImmutable.extract(1, 1, 3, 3);
	
	Pixel[][] sampleIteratorPixels = new Pixel[][] {new Pixel [] {new ColorPixel(0,0,1), new ColorPixel(0,0,1), new ColorPixel(1,0,0) , new ColorPixel(1,0,0)}
	, new Pixel [] {new ColorPixel(0,0,1), new ColorPixel(0,0,1), new ColorPixel(1,0,0) , new ColorPixel(1,0,0)}, 
	new Pixel [] {new ColorPixel(0,1,0), new ColorPixel (0,1,0), new ColorPixel(1,0,0), new ColorPixel(1,0,0)}, 
	new Pixel [] {new ColorPixel (0,1,0), new ColorPixel(0,1,0), new ColorPixel(1,0,0), new ColorPixel(1,0,0)}};
	Picture sampleIteratorPicture = new ImmutablePixelArrayPicture(sampleIteratorPixels, "Hello");
	SubPicture sampleIteratorTest = sampleIteratorPicture.extract(1, 1, 3, 3);
	
	Pixel[] firstRow1 = new Pixel[] {new ColorPixel(0,0,1), new ColorPixel(1,0,0), new ColorPixel(1,0,0)};
	Pixel[] secondRow1 = new Pixel[] {new ColorPixel(0,1,0), new ColorPixel(1,0,0), new ColorPixel(1,0,0)};
	Pixel[] thirdRow1 = new Pixel[] {new ColorPixel(0,1,0), new ColorPixel(1,0,0), new ColorPixel(1,0,0)};
	Pixel[][] multicoloredPixels1 = new Pixel[][] {firstRow1, secondRow1, thirdRow1};
	Picture multicoloredPicture1 = new MutablePixelArrayPicture(multicoloredPixels1, "Hello World");
	SubPicture test2 = multicoloredPicture1.extract(0,0,3,3);

	// Checks Novice methods
	@Test
	public void checkFields() {
		assertTrue(test.getXOffset() == 1);
		assertTrue(test.getYOffset() == 1);
		assertTrue(test.getWidth() == 3);
		assertTrue(test.getWidth() == 3);
		assertTrue(equals(test, test2));
		assertTrue(equals(test, test2.getSource()));
		assertTrue(test.getPixel(1, 0).equals(new ColorPixel(0,1,0)));
		try { 
				test.setCaption(null); }
		
		catch (IllegalArgumentException e) {
			
		};
	}
	
	@Test
	public void checkPaintMethod() {
		test.paint(2, 0, new ColorPixel(1,0,0));
		assertTrue(test.getPixel(0, 1).equals(new ColorPixel(1,0,0)));
		assertFalse(test.getPixel(0, 2).equals(new ColorPixel(0,1,0)));
		assertFalse(equals(test,test2));
		
		test2.paint(1, 0, 2, 1, new ColorPixel(0,1,0));
		Picture testAgain = testImmutable.paint(1, 0, 2, 1, new ColorPixel(0,1,0));	
		assertTrue(test2.getPixel(0, 1).equals(new ColorPixel(1,0,0)));
		assertTrue(test2.getPixel(1, 0).equals(new ColorPixel(0,1,0)));
		assertFalse(equals(test,test2));
		assertTrue(equals(test2,testAgain));
		test.paint(1, 0, testImmutable);
		assertTrue(test.getPixel(1, 0).equals(new ColorPixel(0,0,1)));
		assertFalse(equals(test, testImmutable));
	}
	
	@Test
	public void sampleIteratorTest() {
		Iterator<Pixel> testSampleIterator = sampleIteratorTest.sample(0, 0, 2, 2);
		assertTrue(testSampleIterator.hasNext());
		assertTrue(testSampleIterator.next().equals(new ColorPixel(0,0,1)));
		Pixel nextPixel = testSampleIterator.next();
		assertTrue(nextPixel.equals(new ColorPixel(0,1,0)));
		assertTrue(testSampleIterator.next().equals(new ColorPixel(1,0,0)));
		assertTrue(testSampleIterator.next().equals(new ColorPixel(1,0,0)));
		assertFalse(testSampleIterator.hasNext());
		
	}
	
	@Test
	public void windowIteratorTest() {
		Iterator<SubPicture> testWindowIterator = sampleIteratorTest.window(2, 2);
		assertTrue(testWindowIterator.hasNext());
		Pixel[][] firstWindowSub = new Pixel[][] { new Pixel [] {new ColorPixel(0,0,1), new ColorPixel(1,0,0)} ,
			new Pixel[] {new ColorPixel(0,1,0), new ColorPixel(1,0,0)}
			
		};
		Picture firstWindowSubPicture = new MutablePixelArrayPicture(firstWindowSub, "tragic");
		Pixel[][] secondWindowSub = new Pixel[][] { new Pixel [] {new ColorPixel (0,1,0) , new ColorPixel(1,0,0)},
			new Pixel[] { new ColorPixel(0,1,0), new ColorPixel(1,0,0)} };
		
		Picture secondWindowSubPicture = new MutablePixelArrayPicture(secondWindowSub, "why");
		
		Pixel[][] thirdWindowSub = new Pixel[][] {new Pixel[] {new ColorPixel (1,0,0), new ColorPixel(1,0,0)},
			new Pixel[] {new ColorPixel (1,0,0), new ColorPixel(1,0,0)} };
			
		Picture thirdWindowSubPicture = new MutablePixelArrayPicture(thirdWindowSub, "why");
		assertTrue(equals(testWindowIterator.next(), firstWindowSubPicture ));
		assertTrue(testWindowIterator.hasNext());
		assertTrue(equals (testWindowIterator.next(), secondWindowSubPicture));
		assertTrue(testWindowIterator.hasNext());
		assertTrue(equals (testWindowIterator.next(), thirdWindowSubPicture));
		assertTrue(testWindowIterator.hasNext());
		assertTrue(equals (testWindowIterator.next(), thirdWindowSubPicture));
		assertFalse(testWindowIterator.hasNext());
	}
	
	@Test
	public void tileIteratorTest() {
		Iterator<SubPicture> testTileIterator = sampleIteratorTest.tile(1, 2);
		Pixel[][] firstRow = new Pixel[][] {new Pixel[] {new ColorPixel(0,0,1), new ColorPixel (1,0,0)}};
		Pixel[][] secondRow = new Pixel[][] {new Pixel[] {new ColorPixel(0,1,0), new ColorPixel(1,0,0)}};
		Pixel[][] thirdRow = new Pixel[][] {new Pixel[] {new ColorPixel(0,1,0), new ColorPixel(1,0,0)}};
		Picture firstTilePicture = new MutablePixelArrayPicture(firstRow, "howdy");
		Picture secondTilePicture = new MutablePixelArrayPicture(secondRow, "I'm");
		Picture thirdTilePicture = new MutablePixelArrayPicture(thirdRow, "Flowey");
		assertTrue(testTileIterator.hasNext());
		Picture temp = testTileIterator.next();
		assertTrue(equals(temp, firstTilePicture));
		assertTrue(testTileIterator.hasNext());
		assertTrue(equals(testTileIterator.next(), secondTilePicture));
		assertTrue(testTileIterator.hasNext());
		assertTrue(equals(testTileIterator.next(), thirdTilePicture));
		assertFalse(testTileIterator.hasNext());
		try {
			testTileIterator.next();
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
		
		
	}
	
	@Test
	public void zigZagIterator () {
		Iterator<Pixel> testZigZagIterator = sampleIteratorTest.zigzag();
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(0,0,1)));
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(1,0,0)));
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(0,1,0)));
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(0,1,0)));
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(1,0,0)));
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(1,0,0)));
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(1,0,0)));
		assertTrue(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(1,0,0)));
		assertFalse(testZigZagIterator.hasNext());
		assertTrue(testZigZagIterator.next().equals(new ColorPixel(1,0,0)));
		assertFalse(testZigZagIterator.hasNext());
	
		try {
			testZigZagIterator.next(); 
			assertFalse(true);
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	
	/*Compares two pictures to make sure they're equal
	 * Input: two pictures
	 * Output: Boolean values
	 * Also checks if both the dimensions are the same 
	 */
	
	private boolean equals (Picture test1 , Picture test2) {
		int width1 = test1.getWidth();
		int height1 = test1.getHeight();
		int width2 = test2.getWidth();
		int height2 = test2.getHeight();
		if (width1 != width2 || height1 != height2) {
			return false;
		}
		Pixel[][] pixelarray1 = new Pixel[width1][height1];
		Pixel[][] pixelarray2 = new Pixel[width2][height2];
		 
		for (int width = 0 ; width < width1; width ++) {
			for (int height = 0; height < height1; height++) {
				pixelarray1[width][height] = test1.getPixel(width, height);
				pixelarray2[width][height] = test2.getPixel(width, height);
				
				if (!pixelarray1[width][height].equals(pixelarray2[width][height])) {
					return false;
				}
			}
		}
		return true;
	}
	
	 // This is a handy print method to check the pixels in your class after  you paint it !! 
	//for (int width = 0 ; width < test2.getWidth()  ; width++) {
	//for (int height = 0 ; height < test2.getHeight(); height++) {
		//System.out.println(test2.getPixel(width, height).getRed() + " R " + test2.getPixel(width, height).getGreen() + 
			//	" G " + test2.getPixel(width, height).getBlue() + " B ");
	//}
	//}

}