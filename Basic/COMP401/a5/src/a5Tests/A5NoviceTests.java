package a5Tests;
import static org.junit.Assert.*;
import org.junit.Test;
import a5.*;
public class A5NoviceTests {

	

	private static Pixel RED = new ColorPixel(1.0, 0, 0);

	private static Pixel GREEN = new ColorPixel(0, 1.0, 0);

	private static Pixel BLUE = new ColorPixel(0, 0, 1.0);



	@Test

	public void basicSubPictureTest() {		

		String capstr = "my_caption";

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7, 5, RED), capstr);

		p.paint(1, 1, GREEN);

		p.paint(2, 2, BLUE);

		

		Picture sp = p.extract(0,0,7,5);

		assertEquals(7, sp.getWidth());

		assertEquals(5, sp.getHeight());

		colorCompare(RED, sp.getPixel(0, 0));

		colorCompare(GREEN, sp.getPixel(1, 1));

		colorCompare(BLUE, sp.getPixel(2, 2));

		assertEquals(capstr, sp.getCaption());

		

		sp = p.extract(0,0,1,1);

		assertEquals(1, sp.getWidth());

		assertEquals(1, sp.getHeight());

		colorCompare(RED, sp.getPixel(0, 0));



		sp = p.extract(1,1,1,1);

		assertEquals(1, sp.getWidth());

		assertEquals(1, sp.getHeight());

		colorCompare(GREEN, sp.getPixel(0, 0));



		sp = p.extract(2,2,1,1);

		assertEquals(1, sp.getWidth());

		assertEquals(1, sp.getHeight());

		colorCompare(BLUE, sp.getPixel(0, 0));		

	}

	

	@Test

	public void badSubPictureConstruction() {

		String capstr = "my_caption";

		Picture p = new ImmutablePixelArrayPicture(makeSolidPixelArray(7, 5, RED), capstr);

		

		try {

			Picture sp = p.extract(0, 0, 8, 1);

			fail("Attempt to extract subpicture that is too wide should have caused exception");

		}

		catch (RuntimeException e) {

		}

		

		try {

			Picture sp = p.extract(0, 0, 1, 6);

			fail("Attempt to extract subpicture that is too tall should have caused exception");

		}

		catch (RuntimeException e) {

		}

		

		try {

			Picture sp = p.extract(7, 0, 1, 1);

			fail("Attempt to extract subpicture with xoffset out of bounds should have caused exception");

		}

		catch (RuntimeException e) {

		}



		try {

			Picture sp = p.extract(0, 5, 1, 1);

			fail("Attempt to extract subpicture with xoffset out of bounds should have caused exception");

		}

		catch (RuntimeException e) {

		}



	}

	

	@Test

	public void mutableSourcePictureSubPicturePaintTest() {

		String capstr = "my_caption";

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7, 5, RED), capstr);



		Picture sp = p.extract(2, 2, 3, 2);

		

		Picture result = sp.paint(0, 0, GREEN);

		

		assertEquals(sp, result);

		colorCompare(GREEN, sp.getPixel(0, 0));

		colorCompare(GREEN, p.getPixel(2, 2));

		

		p.paint(3, 3, BLUE);

		colorCompare(BLUE, sp.getPixel(1, 1));

	}





	@Test

	public void immutableSourcePictureSubPicturePaintTest() {

		String capstr = "my_caption";

		Picture p = new ImmutablePixelArrayPicture(makeSolidPixelArray(7, 5, RED), capstr);



		Picture sp = p.extract(2, 2, 3, 2);

		sp.setCaption("sub caption");

		

		Picture result = sp.paint(0, 0, GREEN);

		

		assertNotEquals(sp, result);

		colorCompare(GREEN, result.getPixel(0, 0));

		colorCompare(RED, sp.getPixel(0, 0));

		colorCompare(RED, p.getPixel(2, 2));

		

		p.paint(3, 3, BLUE);

		colorCompare(RED, result.getPixel(1, 1));

		

		assertEquals("sub caption", result.getCaption());

	}

	

	@Test

	public void doubleSubPicturePaintTest() {

		String capstr = "source picture";

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(8,8, RED), capstr);

		

		Picture sp_level1 = p.extract(1, 1, 6, 6);

		Picture sp_level2 = sp_level1.extract(1, 1, 4, 4);

		Picture result = sp_level2.paint(0, 0, GREEN);

		

		

		assertEquals(result, sp_level2);

		assertEquals(sp_level1, ((SubPicture) sp_level2).getSource());

		assertEquals(p, ((SubPicture) sp_level1).getSource());

		colorCompare(GREEN, sp_level2.getPixel(0, 0));

		colorCompare(GREEN, sp_level1.getPixel(1, 1));

		colorCompare(GREEN, p.getPixel(2, 2));

		

		p = new ImmutablePixelArrayPicture(makeSolidPixelArray(8,8,RED), capstr);

		sp_level1 = p.extract(1, 1, 6, 6);

		sp_level2 = sp_level1.extract(1, 1, 4, 4);

		result = sp_level2.paint(0, 0, GREEN);

		

		assertNotEquals(result, sp_level2);

		assertNotEquals(sp_level1, ((SubPicture) result).getSource());

		assertNotEquals(p, ((SubPicture) ((SubPicture) result).getSource()).getSource());

		assertEquals(sp_level1, ((SubPicture) sp_level2).getSource());

		assertEquals(p, ((SubPicture) sp_level1).getSource());

		colorCompare(RED, sp_level2.getPixel(0, 0));

		colorCompare(RED, sp_level1.getPixel(1, 1));

		colorCompare(RED, p.getPixel(2, 2));

		colorCompare(GREEN, result.getPixel(0, 0));

		colorCompare(GREEN, ((SubPicture) result).getSource().getPixel(1, 1));

		colorCompare(GREEN, ((SubPicture) ((SubPicture) result).getSource()).getSource().getPixel(2, 2));

	}



	private void colorCompare(Pixel a, Pixel b) {

		assertEquals(a.getRed(), b.getRed(), 0.001);

		assertEquals(a.getGreen(), b.getGreen(), 0.001);

		assertEquals(a.getBlue(), b.getBlue(), 0.001);

	}

	

	private static Pixel[][] makeSolidPixelArray(int width, int height, Pixel p) {

		Pixel[][] parray = new Pixel[width][height];

		for (int x=0; x<width; x++) {

			for (int y=0; y<height; y++) {

				parray[x][y] = p;

			}

		}

		return parray;

	}

}