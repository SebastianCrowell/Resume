package a6test;

import static org.junit.Assert.*;

import a6.*;

import org.junit.Test;





public class A6Tests {

	

	@Test

	public void noIntersectionRegionTest() {

		Region a = new RegionImpl(0, 0, 1, 1);

		Region b = new RegionImpl(2, 2, 3, 3);

		Region c = null;

		

		try {

			a.intersect(b);

			fail("expected NoIntersectionException to be thrown when regions do not intersect");

		} catch(NoIntersectionException e) {}

		

		try {

			a.intersect(c);

			fail("expected NoIntersectionException to be thrown when called region is null");

		} catch(NoIntersectionException e ) {}

	}

	

	@Test

	public void basicRegionIntersectTest() {

		Region wide_region = new RegionImpl(0, 0, 10, 0);

		Region skinny_region = new RegionImpl(5, 0, 6, 10);

		Region large_square = new RegionImpl(0, 0, 9, 9);

		Region small_square = new RegionImpl(4, 4, 6, 6);

		

		try {

			Region r1 = wide_region.intersect(skinny_region);

			Region r2 = large_square.intersect(small_square);

			

			assertEquals(5, r1.getLeft());

			assertEquals(0, r1.getTop());

			assertEquals(6, r1.getRight());

			assertEquals(0, r1.getBottom());



			assertEquals(4, r2.getLeft());

			assertEquals(4, r2.getTop());

			assertEquals(6, r2.getRight());

			assertEquals(6, r2.getBottom());

		} catch(NoIntersectionException e) {

			fail("expected intersections to be found, but none were");

		}

	}

	

	@Test

	public void regionConstructorTest() {

		try {

			new RegionImpl(1, 0, 0, 0);

			fail("left of region is greater than right, IllegalArgumentException should have been thrown");

		} catch (IllegalArgumentException e) {}

		try {

			new RegionImpl(0, 1, 0, 0);

			fail("top of region is greater than bottom, IllegalArgumentException should have been thrown");

		} catch (IllegalArgumentException e) {}

	}

	

}



