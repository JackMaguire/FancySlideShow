package applications;

import slide_show.*;
import graph.*;

public class Debug {

	public static void main(String[] args) {
		Graph my_graph = new Graph( 4 );
		my_graph.addNode( new Node("0"), 0 );
		my_graph.addNode( new Node("1"), 1 );
		my_graph.addNode( new Node("2"), 2 );
		my_graph.addNode( new Node("3"), 3 );
		
		my_graph.addEdge( new Edge( "0_1", 0, 1 ) );
		my_graph.addEdge( new Edge( "0_1", 1, 2 ) );
		my_graph.addEdge( new Edge( "0_1", 2, 3 ) );
		my_graph.addEdge( new Edge( "0_1", 1, 3 ) );
		my_graph.addEdge( new Edge( "0_1", 0, 2 ) );
		
		SlideShow ss = new SlideShow( my_graph );
		ss.run();
	}
	
}
