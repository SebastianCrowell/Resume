package a7;

import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class FramePuzzleViewWidget extends JPanel implements MouseListener, KeyListener {

	//Instance variables
	private PictureView picture_view;
	private int x = 4; 
	private int y = 4;
	private PictureView [][] subPicArray = new PictureView[5][5]; 
	
	//Frame to hold puzzle
	public FramePuzzleViewWidget(Picture p) {
		picture_view = new PictureView(p.createObservable());
		this.setLayout(new GridLayout(5,5,1,1));
		int width = picture_view.getWidth();
		int height = picture_view.getHeight();
		int subWidth = width/5;
		int subHeight = height/5;
		Picture colorCube = new PictureImpl(width, height);
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				if (i==4 && j==4) {
					subPicArray[i][j] = new PictureView
							(colorCube.extract(i*subWidth, j*subHeight, subWidth, subHeight).createObservable());
				}
				else {
				subPicArray[i][j] = new PictureView 
						(p.extract(i*subWidth, j*subHeight, subWidth, subHeight).createObservable());
				}
			}
		}
		for (int i=0; i<5; i++) {
			for (int j=0; j<5; j++) {
				subPicArray[j][i].addMouseListener(this);
				subPicArray[j][i].addKeyListener(this);
				subPicArray[j][i].revalidate();
				subPicArray[j][i].repaint();
				add(subPicArray[j][i]);
				subPicArray[j][i].setX(j);
				subPicArray[j][i].setY(i);
			}
		}
		revalidate();
		repaint();
	}
	
	//Move tiles around
	public void swapTiles (int swapX, int swapY) {
		if (swapY < 0 ||
			swapX < 0 ||
			swapY > 4 ||
			swapX > 4) {
			return;
		}
		Picture swapPicture = subPicArray[swapX][swapY].getPicture();
		Picture swapColorPicture = subPicArray[x][y].getPicture();
		subPicArray[x][y].setPicture(swapPicture.createObservable());
		subPicArray[swapX][swapY].setPicture(swapColorPicture.createObservable());
		x = swapX;
		y = swapY;
	}

	//Handle if click
	@Override
	public void mouseClicked(MouseEvent e) {
		PictureView source = (PictureView) e.getSource();
		int clickedX = source.getX();
		int clickedY = source.getY();

		//Swapperino cappucino
		if (clickedX == x) {
			if (clickedY > y) { //clicked square below color
				for (int i = y+1; i<=clickedY; i++){
					swapTiles(x ,i);
				}
			}
			else if (clickedY < y) { //clicked square above color
				for (int i = y-1; i>=clickedY; i--){
					swapTiles(x ,i);
				}
			}
		}
		//Swapperino riperino
		else if (clickedY == y) {
			if (clickedX > x) { //clicked square right color
				for (int i = x+1; i<=clickedX; i++){
					swapTiles(i ,y);
				}
			}
			else if (clickedX < x) { //clicked square left color
				for (int i = x-1; i>=clickedX; i--){
					swapTiles(i ,y);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {	
	}

	@Override
	public void mouseEntered(MouseEvent e) {	
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	
	//DO NOT USE
	@Override
	public void keyTyped(KeyEvent e) {	
	}
	
	//Key events go here
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP){
			swapTiles(x, y - 1);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN){
			swapTiles(x, y + 1);
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT){
			swapTiles(x - 1 ,y);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			swapTiles(x + 1, y);
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}
}