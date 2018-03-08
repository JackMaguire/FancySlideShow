package applications;

import slide_show.*;
import graph.*;

public class Demo {

	public static void main( String[] args ) {
		final int num_nodes = 4;
		Graph my_graph = new Graph( num_nodes );

		Node start_node = new Node( "start" );
		my_graph.setNode( start_node, 0 );
		
		my_graph.setNode( new Node( "branch" ), 1 );
		my_graph.setNode( new Node( "end1" ), 2 );
		my_graph.setNode( new Node( "end2" ), 3 );
		
		//my_graph.addEdge( new Edge( "special", 3, 9 ) );

		// my_graph.addEdge( new Edge( "0_1", 0, 1 ) );
		// my_graph.addEdge( new Edge( "1_2", 1, 2 ) );
		// my_graph.addEdge( new Edge( "2_3", 2, 3 ) );
		// my_graph.addEdge( new Edge( "1_3", 1, 3 ) );
		// my_graph.addEdge( new Edge( "0_2", 0, 2 ) );

		SlideShow ss = new SlideShow( my_graph );
		ss.run();
	}

}
