package a6;

import a6.ObservablePicture;

import a6.ROIObserver;

import a6.Region;



/*

 * Implementation of ROIObserver exists for the purpose of testing

 */



public class ROIObserverImpl implements ROIObserver{

	

	/*

	 * NotifyCount is not actually useful to make ROIObserver or its implementation work,

	 * but it will keep track of how many times the notify method is called to ensure

	 * that all is working properly

	 */

	

	private int notifyCount;

	

	public ROIObserverImpl() {

		notifyCount = 0;

	}

	

	@Override

	public void notify(ObservablePicture picture, Region changed_region) {

		notifyCount++;

	}

	

	public int getNotifyCount() {

		return notifyCount;

	}

	

	public void clearNotifications() {

		notifyCount = 0;

	}

}