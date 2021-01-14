package a7;

import java.awt.BorderLayout;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FramePuzzle {

		public static void main(String[] args) throws IOException {

			//Creates widget 
			Picture p = A7Helper.readFromURL("http://www.cs.unc.edu/~kmp/kmp-in-namibia.jpg");
			FramePuzzleViewWidget simple_widget = new FramePuzzleViewWidget(p);

			//Make JFrame
			JFrame main_frame = new JFrame();
			main_frame.setTitle("Assignment 7 Frame Puzzle");
			main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			//JPanel
			JPanel top_panel = new JPanel();
			top_panel.setLayout(new BorderLayout());
			top_panel.add(simple_widget, BorderLayout.CENTER);


			//JFrame
			main_frame.setContentPane(top_panel);
			main_frame.pack();
			main_frame.setVisible(true);
		}
	}
