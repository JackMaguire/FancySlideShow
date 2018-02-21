package slide_show;

import graph.*;

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
		
	}
}
