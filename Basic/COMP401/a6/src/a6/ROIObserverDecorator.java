package a6;

public interface ROIObserverDecorator extends ROIObserver {

	Region getRegion();
	ROIObserver getROIObserver();

}