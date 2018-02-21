package slide_show;

import graph.*;

public class SlideShow {

	//Starts at Node 0
	private Node[] nodes_;
	
	public SlideShow( Node[] nodes ) {
		nodes_ = nodes;
	}
	
	//Getters and setters
	protected Node[] nodes() {
		return nodes_;
	}
	
}
