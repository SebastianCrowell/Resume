package a6test;

import static org.junit.Assert.*;

import org.junit.Test;

import a6.*;



public class A6Test {

	

	Pixel red = new ColorPixel(1, 0, 0);

	Pixel green = new ColorPixel(0, 1, 0);

	Pixel blue = new ColorPixel(0, 0, 1);

	

	@Test

	public void testRegionConstructor() {

		Region r;

		//Valid constructions

		r = new RegionImpl(1, 1, 2, 2);

		r = new RegionImpl(5, 7, 6, 11);

		r = new RegionImpl(4, 2, 9, 10);

		r = new RegionImpl(1, 1, 5, 1);

		

		//Invalid constructions

		try {

			r = new RegionImpl(1, 1, 0, 0);

			fail("Can't use these left/right/top/bottom bounds");

		}catch (IllegalArgumentException e) {

			

		}

		

		try {

			r = new RegionImpl(4, 4, 8, 3);

			fail("Can't use these left/right/top/bottom bounds");

		}catch (IllegalArgumentException e) {

			

		}

		

		try {

			r = new RegionImpl(2, 2, 1, 9);

			fail("Can't use these left/right/top/bottom bounds");

		}catch (IllegalArgumentException e) {

			

		}

	}

	

	@Test

	public void testRegionGetters(){

		

		Region r1 = new RegionImpl(0, 0, 4, 4);

		assertEquals(0, r1.getLeft());

		assertEquals(0, r1.getTop());

		assertEquals(4, r1.getRight());

		assertEquals(4, r1.getBottom());

		

		Region r2 = new RegionImpl (2, 4, 6, 5);

		assertEquals(2, r2.getLeft());

		assertEquals(4, r2.getTop());

		assertEquals(6, r2.getRight());

		assertEquals(5, r2.getBottom());

	}

	

	@Test

	public void testRegionIntersect() {

		

		Region r1;

		Region r2;

		Region i;

		

		//Regions that intersect

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(2, 2, 6, 6);

		i = new RegionImpl(2, 2, 5, 5);

		try {

			assertTrue(regionCompare(i, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(2, 2, 4, 4);

		i = new RegionImpl(2, 2, 4, 4);

		try {

			assertTrue(regionCompare(i, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(1, 1, 10, 2);

		r2 = new RegionImpl(5, 0, 6, 10);

		i = new RegionImpl(5, 1, 6, 2);

		try {

			assertTrue(regionCompare(i, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(2, 3, 4, 7);

		i = new RegionImpl(2, 3, 4, 5);

		try {

			assertTrue(regionCompare(i, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(1, 1, 5, 5);

		try {

			assertTrue(regionCompare(r1, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(1, 2, 4, 5);

		r2 = new RegionImpl(2, 1, 8, 4);

		i = new RegionImpl(2, 2, 4, 4);

		try {

			assertTrue(regionCompare(i, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(5, 1, 7, 7);

		i = new RegionImpl(5, 1, 5, 5);

		try {

			assertTrue(regionCompare(i, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(5, 1, 5, 8);

		i = new RegionImpl(5, 1, 5, 5);

		

		try {

			assertTrue(regionCompare(i, r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should have been thrown");

		}

		

		r1 = new RegionImpl(0, 0, 5, 5);

		r2 = new RegionImpl(2, 2, 6, 6);

		

		try {

			assertTrue(regionCompare(new RegionImpl(2, 2, 5 ,5), r1.intersect(r2)));

		} catch (NoIntersectionException e) {

			fail("No exception should be thrown");

		}

		

		//Regions that don't intersect

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(1, 6, 7, 7);

		try {

			r1.intersect(r2);

			fail("Exception should be thrown");

		} catch (NoIntersectionException e) {

			

		}

		

		r1 = new RegionImpl (1, 1, 5, 5);

		r2 = new RegionImpl (6, 1, 7, 7);

		try {

			r1.intersect(r2);

			fail("Exception should be thrown");

		} catch (NoIntersectionException e) {

			

		}	

		

		//Null intersection

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = null;

		try {

			r1.intersect(r2);

			fail("Exception should be thrown");

		} catch (NoIntersectionException e) {

			

		}

		



	}

	

	@Test

	public void testRegionUnion() {

		Region r1;

		Region r2;

		Region u;

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(2, 2, 6, 6);

		u = new RegionImpl(1, 1, 6, 6);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(2, 2, 4, 4);

		u = new RegionImpl(1, 1, 5, 5);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 1, 10, 2);

		r2 = new RegionImpl(5, 0, 6, 10);

		u = new RegionImpl(1, 0, 10, 10);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(2, 3, 4, 7);

		u = new RegionImpl(1, 1, 5, 7);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(1, 1, 5, 5);

		u = new RegionImpl(1, 1, 5, 5);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 2, 4, 5);

		r2 = new RegionImpl(2, 1, 8, 4);

		u = new RegionImpl(1, 1, 8, 5);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(5, 1, 7, 7);

		u = new RegionImpl(1, 1, 7, 7);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = new RegionImpl(5, 1, 5, 8);

		u = new RegionImpl(1, 1, 5, 8);

		assertTrue(regionCompare(u, r1.union(r2)));

		

		r1 = new RegionImpl(1, 1, 5, 5);

		r2 = null;

		assertTrue(regionCompare(r1, r1.union(r2)));

		assertEquals(r1, r1.union(r2));

		

	}

	

	@Test

	public void testObservablePicturePaint() {

		//Mutable underlying picture

		Picture mut = new MutablePixelArrayPicture(makePixelArray(5, 5), "5x5 half black half white");

		Picture o = new ObservablePictureImpl(mut);

		

		mut.paint(0, 0, red);

		assertTrue(colorCompare(o.getPixel(0, 0), red));

		o.paint(1, 0, blue);

		assertTrue(colorCompare(mut.getPixel(1, 0), blue));

		assertTrue(colorCompare(o.getPixel(1, 0), blue));	

		

		Picture p = o.paint(2, 0, green);

		assertEquals(p, o);

		assertNotEquals(o, mut);

		

		//Immutable underlying picture

		Picture imm = new ImmutablePixelArrayPicture(makePixelArray(5, 5), "5x5 half black half white");

		Picture o2 = new ObservablePictureImpl(imm);

		

		imm.paint(0, 0, red);

		assertFalse(colorCompare(o2.getPixel(0, 0), red));

		Picture i = imm.paint(0, 0, red);

		assertFalse(colorCompare(o2.getPixel(0, 0), red));

		

		o2.paint(1, 0, blue);

		assertTrue(colorCompare(o2.getPixel(1, 0), blue));

		Picture po = o2.paint(2, 0, green);

		assertTrue(colorCompare(o2.getPixel(2, 0), green));

		

		Picture p2 = o.paint(3, 0, red);

		assertEquals(p2, o);

		assertNotEquals(p2, imm);

	}

	

	@Test

	public void testObserverRegistrationAndFinding() {

		Picture p = new MutablePixelArrayPicture(makePixelArray(5, 5), "5x5 half black half white");

		ObservablePictureImpl o = new ObservablePictureImpl(p);

		

		Region region1 = new RegionImpl(2, 2, 3, 3);

		Region region2 = new RegionImpl(4, 4, 5, 5);

		Region region3 = new RegionImpl(0, 0, 1, 5);

		Region region4 = new RegionImpl(3, 3, 4, 4);

		

		ROIObserver o1 = new ROIObserverImpl();

		ROIObserver o2 = new ROIObserverImpl();

		ROIObserver o3 = new ROIObserverImpl();

		

		o.registerROIObserver(o1, region1);

		o.registerROIObserver(o2, region2);

		o.registerROIObserver(o3, region3);

		

		assertEquals(o1, o.findROIObservers(region1)[0]);

		assertEquals(o2, o.findROIObservers(region2)[0]);

		assertEquals(o3, o.findROIObservers(region3)[0]);

		assertNotEquals(o1, o.findROIObservers(region2)[0]);

		assertNotEquals(o1, o.findROIObservers(region3)[0]);

		assertEquals(o1, o.findROIObservers(region4)[0]);

		assertEquals(o2, o.findROIObservers(region4)[1]);

		

		o.unregisterROIObserver(o1);

		o.unregisterROIObservers(region3);

		

		try {

			ROIObserver test = o.findROIObservers(region1)[0];

			fail("No observers here, array should be out of bounds!");

		} catch (ArrayIndexOutOfBoundsException e) {

			

		}

		

		try {

			ROIObserver test = o.findROIObservers(region3)[0];

			fail("No observers here, array should be out of bounds!");

		} catch (ArrayIndexOutOfBoundsException e) {

			

		}

		

		o.registerROIObserver(o2, region3);

		assertEquals(o2, o.findROIObservers(region2)[0]);

		assertEquals(o2, o.findROIObservers(region4)[0]);

		assertEquals(o2, o.findROIObservers(region3)[0]);

	}

	

	@Test

	public void testNotify() {

		//No suspend or resume

		Picture p = new MutablePixelArrayPicture(makePixelArray(10, 10), "10x10 half black half white");

		ObservablePicture o = new ObservablePictureImpl(p);

		

		Region r1 = new RegionImpl(1, 1, 5, 5);

		Region r2 = new RegionImpl(0, 0, 3, 3);

		Region r3 = new RegionImpl(2, 2, 7, 7);

		

		ROIObserverImpl o1 = new ROIObserverImpl();

		ROIObserverImpl o2 = new ROIObserverImpl();

		

		o.registerROIObserver(o1, r1);

		o.registerROIObserver(o2, r2);

		o.registerROIObserver(o2, r3);

		

		o.paint(1, 1, red);

		assertEquals(1, o1.getNotifyCount());

		assertEquals(1, o2.getNotifyCount());

		o1.clearNotifications();

		o2.clearNotifications();

		

		o.paint(1, 1, 4, 4, red);

		assertEquals(1, o1.getNotifyCount());

		assertEquals(2, o2.getNotifyCount());

		o1.clearNotifications();

		o2.clearNotifications();

		

		o.paint(8, 8, red);

		assertEquals(0, o1.getNotifyCount());

		assertEquals(0, o2.getNotifyCount());

		

		//Suspend and resume

		o.suspendObservable();

		o.paint(2, 1, red);

		o.paint(3, 2, red);

		o.paint(4, 4, red);

		o.resumeObservable();

		assertEquals(1, o1.getNotifyCount());

		assertEquals(2, o2.getNotifyCount());

		o1.clearNotifications();

		o2.clearNotifications();

		

	}

	

	private boolean regionCompare(Region r1, Region r2) {

		if (r1.getLeft() == r2.getLeft() && r1.getRight() == r2.getRight()

				&& r1.getTop() == r2.getTop() && r1.getBottom() == r2.getBottom()){

					return true;

		}

		return false;

	}

	

	private boolean colorCompare(Pixel c1, Pixel c2) {

		if (c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen()

				&& c1.getBlue() == c2.getBlue()) {

			return true;

		}

		return false;

	}

	

	private Pixel[][] makePixelArray(int x, int y) {

		Pixel[][] pixels = new Pixel[x][y];

		for (int r = 0; r < pixels.length; r++) {

			for (int h = 0; h < pixels[0].length; h++) {

				if (h < y/2) {

					pixels[r][h] = Pixel.BLACK;

				}else {

					pixels[r][h] = Pixel.WHITE;

				}

			}

		}

		return pixels;

	}

}