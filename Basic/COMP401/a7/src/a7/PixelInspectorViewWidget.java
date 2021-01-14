package a7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PixelInspectorViewWidget extends JPanel implements MouseListener {
	
	//Variables	
	private PictureView picture_view;
	private JPanel PixelInspector;
	private JLabel x;
	private JLabel y;
	private JLabel r;
	private JLabel g;
	private JLabel b;
	private JLabel i;
		
	public PixelInspectorViewWidget(Picture picture) {
		//KMP's setup
		setLayout(new BorderLayout());
		
		picture_view = new PictureView(picture.createObservable());
		picture_view.addMouseListener(this);
		add(picture_view, BorderLayout.EAST);
		
		PixelInspector = newPixelInspector();
	}

	public JPanel newPixelInspector(){

		//PixelInspector JPanel
		JPanel pixelInspector = new JPanel();
		pixelInspector.setLayout(new GridLayout(0,1));

		//Variables
		this.x = new JLabel("getX: ");
		this.y = new JLabel("getY: ");
		this.r = new JLabel("getRed: ");
		this.g = new JLabel("getGreen: ");
		this.b = new JLabel("getBlue: ");
		this.i = new JLabel("getIntensity: ");

		//Labels to the PixelInspector
		pixelInspector.add(x);
		pixelInspector.add(y);
		pixelInspector.add(r);
		pixelInspector.add(g);
		pixelInspector.add(b);
		pixelInspector.add(i);

		//PixelInspector to the widget
		this.add(pixelInspector, BorderLayout.WEST);
		return pixelInspector;
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		//Mouse events for variables
		double X = e.getX();
		double Y = e.getY();
		double red = picture_view.getPicture().getPixel(e.getX(), e.getY()).getRed();
		double green = picture_view.getPicture().getPixel(e.getX(), e.getY()).getGreen();
		double blue = picture_view.getPicture().getPixel(e.getX(), e.getY()).getBlue();
		double intensity = picture_view.getPicture().getPixel(e.getX(), e.getY()).getIntensity();
		
		//Display (unsure how to check values for correctness)
		x.setText("X: " + X);
		y.setText("Y: " + Y);
		r.setText("Red: " + red);
		g.setText("Green: " + green);
		b.setText("Blue: " + blue);
		i.setText("Intensity: " + intensity);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// Needed events
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// Needed events
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Needed events
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Needed events
	}

}
