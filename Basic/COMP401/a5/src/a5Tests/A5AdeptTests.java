package a5Tests;
import static org.junit.Assert.*;
import java.util.Iterator;
import org.junit.Test;
import a5.*;
public class A5AdeptTests {



	private static Pixel RED = new ColorPixel(1.0, 0, 0);

	private static Pixel GREEN = new ColorPixel(0, 1.0, 0);

	private static Pixel BLUE = new ColorPixel(0, 0, 1.0);



	@Test 

	public void testBasicSampleIterator() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}

		

		Iterator<Pixel> si = p.sample(0, 0, 1, 1);

		for (int y=0; y<p.getHeight(); y++) {

			for (int x=0; x<p.getWidth(); x++) {

				assertTrue(si.hasNext());

				assertEquals(p.getPixel(x, y), si.next());

			}

		}	

		assertFalse(si.hasNext());

	}	

	

	@Test

	public void testSkipSampleIterator() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}

		

		Iterator<Pixel> si = p.sample(1, 0, 3, 2);

		for (int y=0; y<p.getHeight(); y+=2) {

			for (int x=1; x<p.getWidth(); x+=3) {

				assertTrue(si.hasNext());

				assertEquals(p.getPixel(x, y), si.next());

			}

		}	

		assertFalse(si.hasNext());

	}

	

	@Test

	public void testDegenerateGeometrySampleIterator() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(1,1, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}

		

		Iterator<Pixel> si = p.sample(0, 0, 1, 1);

		for (int y=0; y<p.getHeight(); y++) {

			for (int x=0; x<p.getWidth(); x++) {

				assertTrue(si.hasNext());

				assertEquals(p.getPixel(x, y), si.next());

			}

		}	

		assertFalse(si.hasNext());

		

		p = new MutablePixelArrayPicture(makeSolidPixelArray(1,5, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}

		

		si = p.sample(0, 0, 1, 1);

		for (int y=0; y<p.getHeight(); y++) {

			for (int x=0; x<p.getWidth(); x++) {

				assertTrue(si.hasNext());

				assertEquals(p.getPixel(x, y), si.next());

			}

		}	

		assertFalse(si.hasNext());



		p = new MutablePixelArrayPicture(makeSolidPixelArray(5,1, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}

		

		si = p.sample(0, 0, 1, 1);

		for (int y=0; y<p.getHeight(); y++) {

			for (int x=0; x<p.getWidth(); x++) {

				assertTrue(si.hasNext());

				assertEquals(p.getPixel(x, y), si.next());

			}

		}	

		assertFalse(si.hasNext());



	}

	

	@Test

	public void basicWindowTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");



		Iterator<SubPicture> wi = p.window(3, 2);

		

		for (int yoff=0; yoff <= p.getHeight()-2; yoff++) {

			for (int xoff = 0; xoff <= p.getWidth()-3; xoff++) {

				assertTrue(wi.hasNext());

				SubPicture sp = wi.next();

				assertEquals(p, sp.getSource());

				assertEquals(xoff, sp.getXOffset());

				assertEquals(yoff, sp.getYOffset());

				assertEquals(3, sp.getWidth());

				assertEquals(2, sp.getHeight());

			}

		}

		assertFalse(wi.hasNext());

	}

	

	@Test

	public void oneByOneWindowTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");



		Iterator<SubPicture> wi = p.window(1, 1);

		

		for (int yoff=0; yoff <= p.getHeight()-1; yoff++) {

			for (int xoff = 0; xoff <= p.getWidth()-1; xoff++) {

				assertTrue(wi.hasNext());

				SubPicture sp = wi.next();

				assertEquals(p, sp.getSource());

				assertEquals(xoff, sp.getXOffset());

				assertEquals(yoff, sp.getYOffset());

				assertEquals(1, sp.getWidth());

				assertEquals(1, sp.getHeight());

			}

		}

		assertFalse(wi.hasNext());

	}

	

	@Test

	public void fullWidthAndHeightWindowTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");



		Iterator<SubPicture> wi = p.window(7, 5);

		

		assertTrue(wi.hasNext());

		SubPicture sp = wi.next();

		assertEquals(p, sp.getSource());

		assertEquals(0, sp.getXOffset());

		assertEquals(0, sp.getYOffset());

		assertEquals(7, sp.getWidth());

		assertEquals(5, sp.getHeight());

		assertFalse(wi.hasNext());

	}



	

	@Test

	public void basicTileTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(8,4, RED), "caption");



		Iterator<SubPicture> ti = p.tile(2, 2);

		for (int yoff=0; yoff <= p.getHeight()-1; yoff+=2) {

			for (int xoff = 0; xoff <= p.getWidth()-1; xoff+=2) {

				assertTrue(ti.hasNext());

				SubPicture sp = ti.next();

				assertEquals(p, sp.getSource());

				assertEquals(xoff, sp.getXOffset());

				assertEquals(yoff, sp.getYOffset());

				assertEquals(2, sp.getWidth());

				assertEquals(2, sp.getHeight());

			}

		}

		assertFalse(ti.hasNext());		

	}

	

	@Test

	public void noPartialTilesTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");



		Iterator<SubPicture> ti = p.tile(2, 2);

		for (int yoff=0; yoff < 4; yoff+=2) {

			for (int xoff = 0; xoff < 6; xoff+=2) {

				assertTrue(ti.hasNext());

				SubPicture sp = ti.next();

				assertEquals(p, sp.getSource());

				assertEquals(xoff, sp.getXOffset());

				assertEquals(yoff, sp.getYOffset());

				assertEquals(2, sp.getWidth());

				assertEquals(2, sp.getHeight());

			}

		}

		assertFalse(ti.hasNext());		

	}





	@Test

	public void oneByOneTileTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");



		Iterator<SubPicture> ti = p.tile(1, 1);

		

		for (int yoff=0; yoff <= p.getHeight()-1; yoff++) {

			for (int xoff = 0; xoff <= p.getWidth()-1; xoff++) {

				assertTrue(ti.hasNext());

				SubPicture sp = ti.next();

				assertEquals(p, sp.getSource());

				assertEquals(xoff, sp.getXOffset());

				assertEquals(yoff, sp.getYOffset());

				assertEquals(1, sp.getWidth());

				assertEquals(1, sp.getHeight());

			}

		}

		assertFalse(ti.hasNext());

	}

	

	@Test

	public void fullWidthAndHeightTileTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(7,5, RED), "caption");



		Iterator<SubPicture> ti = p.tile(7, 5);

		

		assertTrue(ti.hasNext());

		SubPicture sp = ti.next();

		assertEquals(p, sp.getSource());

		assertEquals(0, sp.getXOffset());

		assertEquals(0, sp.getYOffset());

		assertEquals(7, sp.getWidth());

		assertEquals(5, sp.getHeight());

		assertFalse(ti.hasNext());

	}

	

	@Test

	public void squareZigZagEvenDiagonalUpperRightCornerTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(3,3, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}



		Iterator<Pixel> pi = p.zigzag();



		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 0), pi.next());		

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(1, 0), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 1), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 2), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(1, 1), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(2, 0), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(2, 1), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(1, 2), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(2, 2), pi.next());

		assertFalse(pi.hasNext());

	}



	@Test

	public void squareZigZagOddDiagonalUpperRightCornerTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(4,4, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}



		Iterator<Pixel> pi = p.zigzag();

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 0), pi.next());		

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(1, 0), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 1), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 2), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(1, 1), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(2, 0), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(3, 0), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(2, 1), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(1, 2), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 3), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(1, 3), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(2, 2), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(3, 1), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(3, 2), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(2, 3), pi.next());

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(3, 3), pi.next());

		assertFalse(pi.hasNext());

	}



	

	@Test

	public void oneByOneZigZagTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(1,1, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}



		Iterator<Pixel> pi = p.zigzag();



		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 0), pi.next());		

		assertFalse(pi.hasNext());

	}



	@Test

	public void oneByNZigZagTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(1,5, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}



		Iterator<Pixel> pi = p.zigzag();

		

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 0), pi.next());		

		assertEquals(p.getPixel(0, 1), pi.next());		

		assertEquals(p.getPixel(0, 2), pi.next());		

		assertEquals(p.getPixel(0, 3), pi.next());		

		assertEquals(p.getPixel(0, 4), pi.next());		

		assertFalse(pi.hasNext());

	}





	@Test

	public void nByOneZigZagTest() {

		Picture p = new MutablePixelArrayPicture(makeSolidPixelArray(5,1, RED), "caption");

		

		for (int x=0; x<p.getWidth(); x++) {

			for (int y=0; y<p.getHeight(); y++) {

				double fx = ((double) x) / ((double) (p.getWidth()-1));

				double fy = ((double) y) / ((double) (p.getHeight()-1));

				p.paint(x, y, RED.blend(BLUE, fx).blend(GREEN, fy));

			}

		}



		Iterator<Pixel> pi = p.zigzag();

		

		assertTrue(pi.hasNext());

		assertEquals(p.getPixel(0, 0), pi.next());		

		assertEquals(p.getPixel(1, 0), pi.next());		

		assertEquals(p.getPixel(2, 0), pi.next());		

		assertEquals(p.getPixel(3, 0), pi.next());		

		assertEquals(p.getPixel(4, 0), pi.next());		

		assertFalse(pi.hasNext());

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