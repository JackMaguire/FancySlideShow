package slide_show;

import java.awt.*;

import javax.swing.*;

import graph.*;
import util.*;

public class SlideShow implements SlideShowType{

	//Starts at Node 0
	private Node[] nodes_;
	
	public SlideShow( Node[] nodes ) {
		nodes_ = nodes;
	}
	
	//Getters and setters
	protected Node[] nodes() {
		return nodes_;
	}
	
	
	//run
	public void run() {
		GraphicsEnvironment ge =
				GraphicsEnvironment.getLocalGraphicsEnvironment();
		p( ge.toString() );
	}
	
	private JFrame create_slideshow_jframe() {
		JFrame F = new JFrame("SlideShow");
		
		return F;
	}
	
	//util
	void p( String s ) {
		System.out.println(s);
	}
	
	void p( Object...objects ) {
		System.out.println(objects);
	}
}
