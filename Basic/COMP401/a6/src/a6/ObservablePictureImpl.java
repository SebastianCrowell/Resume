package a6;

import java.util.List;
import java.util.ArrayList;

	public class ObservablePictureImpl implements ObservablePicture {

		//New picture so that when we paint a region we paint the correct one
		Picture _picture;
		//Array list for regions and observers
		List <ROIObserverDecorator> olist;
		boolean suspend;
		protected Region region;

		public ObservablePictureImpl(Picture p) {
			if(p.equals(null)) {
				throw new IllegalArgumentException("Picture is null");
			}
			this._picture = p;
			olist = new ArrayList <ROIObserverDecorator>();
			region = null;
		}

		@Override
		public int getWidth() {
			// Get and return picture width
			return this._picture.getWidth();
		}

		@Override
		public int getHeight() {
			// Get and return picture height
			return this._picture.getHeight();
		}

		@Override
		public Pixel getPixel(int x, int y) {
			// Get and return pixel
			return this._picture.getPixel(x, y);
		}
		
		@Override
		public Picture paint(int x, int y, Pixel p) {
			// Get and return normal paint
			return this._picture.paint(x, y, p);
		}

		@Override
		public Picture paint(int x, int y, Pixel p, double factor) {
			// Get and return gradient paint
			return this._picture.paint(x, y, p, factor);
		}

		@Override
		public Picture paint(int ax, int ay, int bx, int by, Pixel p) {
			// Get and return normal rect paint
			return this._picture.paint(ax, ay, bx, by, p);
		}

		@Override
		public Picture paint(int ax, int ay, int bx, int by, Pixel p, double factor) {
			// Get and return gradient rect paint
			return this._picture.paint(ax, ay, bx, by, p, factor);
		}

		@Override
		public Picture paint(int cx, int cy, double radius, Pixel p) {
			// Get and return normal circle paint
			return this._picture.paint(cx, cy, radius, p);
		}

		@Override
		public Picture paint(int cx, int cy, double radius, Pixel p, double factor) {
			// Get and return gradient circle paint
			return this._picture.paint(cx, cy, radius, p, factor);
		}

		@Override
		public Picture paint(int x, int y, Picture p) {
			// Picture paint
			return this._picture.paint(x, y, p);
		}

		@Override
		public Picture paint(int x, int y, Picture p, double factor) {
			// Gradient picture paint
			return this._picture.paint(x, y, p, factor);
		}

		@Override
		public String getCaption() {
			// Caption get
			return this._picture.getCaption();
		}

		@Override
		public void setCaption(String caption) {
			// Caption set
			caption = new String();
		}

		@Override
		public SubPicture extract(int x, int y, int width, int height) {
			// Extract sub picture
			return this._picture.extract(x, y, width, height);
		}

		@Override
		public void registerROIObserver(ROIObserver observer, Region r) {
			// Adding the different pieces to Arraylists
			if( observer == null || r == null) {
				throw new IllegalArgumentException("Observer or Region are null");
			}
			olist.add(new ROIObserverDecoratorImpl(observer, r));
		}

		@Override
		public void unregisterROIObservers(Region r) {
			// Remove region 
			if( r.equals(null)) {
				throw new IllegalArgumentException("Region is null");
			}
			List <ROIObserverDecorator> orlist = new ArrayList<ROIObserverDecorator>();

			for(ROIObserverDecorator o : this.olist) {
				try {
					o.getRegion().intersect(r);
				} catch(NoIntersectionException e) {
					orlist.add(o);
				}
			}
			olist = orlist;
		}

		@Override
		public void unregisterROIObserver(ROIObserver observer) {
			// Remove observer
			if(observer.equals(null)) {
				throw new IllegalArgumentException("Observer is null");
			}
			List <ROIObserverDecorator> orlist = new ArrayList<ROIObserverDecorator>();

			for(ROIObserverDecorator o : this.olist) {
				if(o.getROIObserver() != observer) {
					orlist.add(o);
				}
			}
			olist = orlist; 
		}

		@Override
		public ROIObserver[] findROIObservers(Region r) {
			// Find observer
			if(r.equals(null)) {
				throw new IllegalArgumentException("Region is null");
			}
			List <ROIObserver> temporaryobserverlist = new ArrayList<ROIObserver>();

			for (ROIObserverDecorator o : this.olist) {
				try {
					o.getRegion().intersect(r);
					temporaryobserverlist.add(o.getROIObserver());
				} catch (NoIntersectionException e) {

				} 
			} 
			ROIObserver[] arrayFound = new ROIObserver[temporaryobserverlist.size()];
			arrayFound = temporaryobserverlist.toArray(arrayFound);
			return arrayFound;
		}

		@Override
		public void suspendObservable() {
			// Stop actions
			suspend = true;
		}

		@Override
		public void resumeObservable() {
			// Resume actions
			suspend = false;
			for(ROIObserverDecorator o : this.olist) {
				try {
					Region intersection = o.getRegion().intersect(this.region);
					o.notify(this, intersection);
				} catch (NoIntersectionException e) {
				}
			}
			region = null;
		}
}