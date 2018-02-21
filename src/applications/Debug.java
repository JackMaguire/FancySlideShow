package applications;

import slide_show.*;
import graph.*;

public class Debug {

	public static void main(String[] args) {
		Graph my_graph = new Graph( 2 );
		
		SlideShow ss = new SlideShow( my_graph );
		ss.run();
	}
	
}
