
package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ImageAdjusterViewWidget extends JPanel implements ChangeListener {
	
		private Picture _picture;
		private PictureView picture_view;
		private JSlider blurSlider;
		private JSlider satSlider;
		private JSlider intSlider;
		List<ChangeListener> change_listeners;
		private JPanel ImageAdjuster;
		
		public ImageAdjusterViewWidget(Picture picture) {
		//KMP's setup
			setLayout(new BorderLayout());
			_picture = picture;
			
			picture_view = new PictureView(picture.createObservable());
			add(picture_view, BorderLayout.CENTER);
			
			ImageAdjuster = new JPanel();
			ImageAdjuster.setLayout(new GridLayout(0,1));
			add(ImageAdjuster, BorderLayout.SOUTH);
			
			blurSlider();
			satSlider();
			intSlider();
		} 	 	
	
		
		public void blurSlider(){
			//Slider from 0 -> 5, start at 0
			blurSlider = new JSlider(0, 5, 0);
	
			//Properties
			blurSlider.addChangeListener(this);
			blurSlider.setPaintTicks(true);
			blurSlider.setPaintLabels(true);
			blurSlider.setMajorTickSpacing(1);
	
			//Add slider to JPanel
			ImageAdjuster.add(new JLabel("Blur: "));
			ImageAdjuster.add(blurSlider);
		}
		
		public void satSlider(){
			//Slider from -100 -> 100, start at 0
			satSlider = new JSlider(-100, 100, 0);
			
			//Properties
			satSlider.addChangeListener(this);
			satSlider.setPaintTicks(true);
			satSlider.setPaintLabels(true);
			satSlider.setMajorTickSpacing(25);
	
			//Add slider to JPanel
			ImageAdjuster.add(new JLabel("Saturation: "));
			ImageAdjuster.add(satSlider);
		}
	
		public void intSlider(){
			//Slider from -100 -> 100, start at 0
			intSlider = new JSlider(-100, 100, 0);
			
			//Properties
			intSlider.addChangeListener(this);
			intSlider.setPaintTicks(true);
			intSlider.setPaintLabels(true);
			intSlider.setMajorTickSpacing(25);
	
			//Add slider to JPanel
			ImageAdjuster.add(new JLabel("Intensity: "));
			ImageAdjuster.add(intSlider);
		}

	
	
		public Picture blur(Picture p, int blurValue) {
			if (blurValue == 0) {
				return p;
			}
			int width = p.getWidth();
			int height = p.getHeight();
			Picture newBlurPicture = new PictureImpl(width, height);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					int numPixels = 0;
					double red = 0.0;
					double green = 0.0;
					double blue = 0.0;
					for (int x = i - blurValue; x <= i + blurValue; x++) {
						for (int y = j - blurValue; y <= j + blurValue; y++) {
							if (x >= width ||
								x < 0 ||
								y >= height ||
								y < 0 ||
								x == i ||
								y == j) {
								continue;
							}
							Pixel pPixel = p.getPixel(x, y);
							red += pPixel.getRed();
							green += pPixel.getGreen();
							blue += pPixel.getBlue();
							numPixels++;
						}
					}
					red /= numPixels;
					green /= numPixels;
					blue /= numPixels;
					Pixel newPixel = new ColorPixel(red, green, blue);
					newBlurPicture.setPixel(i, j, newPixel);
				}
			}
			return newBlurPicture;
		}
		
		public Picture saturation(Picture p, double saturationValue) {
			int width = p.getWidth();
			int height = p.getHeight();
			double f = saturationValue;
			double red;
			double green;
			double blue;
			Picture newSaturatedPicture = new PictureImpl(width, height);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Pixel pPixel = p.getPixel(i, j);
					double b = p.getPixel(i,j).getIntensity();
					red = pPixel.getRed();
					green = pPixel.getGreen();
					blue = pPixel.getBlue(); 
					if (red == 0.0 && green == 0.0 && blue == 0.0){
						red = 0.0;
						green = 0.0;
						blue = 0.0;
					}
					if (f>= -100 && f<= 0) {
						red = red * (1.0 + (f / 100.0) ) - (b * f / 100.0);
						green = green * (1.0 + (f / 100.0) ) - (b * f / 100.0);
						blue = blue * (1.0 + (f / 100.0) ) - (b * f / 100.0);
					}
					if (f>0 && f<=100) {
						double a = 0.0;
						if ( red >= green && red >= blue ) {a = red;}
					      else if ( green >= red && green >= blue ) { a = green;}
					      	else if ( blue >= red && blue >= green ) {a = blue;}
						red = red * ((a + ((1.0 - a) * (f / 100.0))) / a);
						green = green * ((a + ((1.0 - a) * (f / 100.0))) / a);
						blue = blue * ((a + ((1.0 - a) * (f / 100.0))) / a);
					}
					Pixel newPixel = new ColorPixel(red, green, blue);
					newSaturatedPicture.setPixel(i, j, newPixel);
				}
			}
			return newSaturatedPicture;
		}
	
		public Picture brightness(Picture p, double brightnessValue) {
			int width = p.getWidth();
			int height = p.getHeight();
			
			Picture newBrightnessPicture = new PictureImpl(width, height);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Pixel pPixel = p.getPixel(i, j);
					Pixel blendPixel = null;
					ColorPixel white = new ColorPixel(1.0, 1.0, 1.0);
					ColorPixel black = new ColorPixel(0.0, 0.0, 0.0);
					if (brightnessValue > 0) {
						blendPixel = pPixel.blend(white, 1 - (brightnessValue / 100.0));
					} else {
						blendPixel = pPixel.blend(black, 1 - Math.abs((brightnessValue / 100.0)));
					}
					newBrightnessPicture.setPixel(i, j, blendPixel);
				}
			}
			return newBrightnessPicture;
		}
	
		public void stateChanged(ChangeEvent e) {
			int width = _picture.getWidth();
			int height = _picture.getHeight();
	
			// Copy pic
			Picture changedPicture = new PictureImpl(width, height);
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					Pixel p = _picture.getPixel(i, j);
					changedPicture.setPixel(i, j, p);
				}
			}
			changedPicture = blur(changedPicture, blurSlider.getValue());
			changedPicture = saturation(changedPicture, satSlider.getValue());
			changedPicture = brightness(changedPicture, intSlider.getValue());
			picture_view.setPicture(changedPicture.createObservable());
		}
		public void addChangeListener(ChangeListener l) {
			change_listeners.add(l);
		}
		public void removeChangeListener(ChangeListener l) {
			change_listeners.remove(l);
		}
	}