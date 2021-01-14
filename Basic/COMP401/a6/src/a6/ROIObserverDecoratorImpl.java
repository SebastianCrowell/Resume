package a6;

public class ROIObserverDecoratorImpl implements ROIObserverDecorator{

	protected ROIObserver observer;
	protected Region r;

	public ROIObserverDecoratorImpl(ROIObserver observer, Region r) {
			this.observer = observer;
				this.r = r;
	}

	public void notify(ObservablePicture p, Region r) {

	}

	public Region getRegion() {
		return r;
	}

	public ROIObserver getROIObserver() {
		return observer;
	}
}