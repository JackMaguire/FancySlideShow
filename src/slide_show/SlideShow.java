package slide_show;

import java.awt.*;

import javax.swing.*;

import graph.*;
import util.*;

public class SlideShow implements SlideShowType{

	//Starts at Node 0
	private Graph graph_;
	private Node current_node_;
	
	public SlideShow( Graph graph ) {
		graph_ = graph;
	}
	
	//Getters and setters
	protected Graph graph() {
		return graph_;
	}
	
	public void setGraph( Graph g ) {
		graph_ = g;
	}
	
	//run
	public void run() {
		GraphicsEnvironment ge =
				GraphicsEnvironment.getLocalGraphicsEnvironment();
		p( ge.toString() );

		GraphicsDevice[] devices = ge.getScreenDevices();
		Window w = devices[0].getFullScreenWindow();
		
		
		JFrame slideshow_frame = createSlideshowJFrame();
	}
	
	private JFrame createSlideshowJFrame() {
		JFrame F = new JFrame("SlideShow");
		F.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		F.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		F.setUndecorated(true);
		F.setVisible(true);
		return F;
	}
	
	//util
	void p( String s ) {
		System.out.println(s);
	}
	
	/*void p( Object...objects ) {
		System.out.println(objects);
	}*/
}
