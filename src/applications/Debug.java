package applications;

import conceptual_graph.*;
import slide_show.*;

public class Debug {

	public static void main( String[] args ) {
		final int num_nodes = 20;
		ConceptualGraph my_graph = new ConceptualGraph( num_nodes );
		for( int i = 1; i <= num_nodes; ++i ) {
			my_graph.setNode( new ConceptualNode( "" + i ), i - 1 );
			if( i != 1 && i != 5 ) {
				my_graph.addEdge( new ConceptualEdge( "normal", i - 2, i - 1 ) );
			}
		}

		my_graph.addEdge( new ConceptualEdge( "special", 3, 9 ) );

		// my_graph.addEdge( new Edge( "0_1", 0, 1 ) );
		// my_graph.addEdge( new Edge( "1_2", 1, 2 ) );
		// my_graph.addEdge( new Edge( "2_3", 2, 3 ) );
		// my_graph.addEdge( new Edge( "1_3", 1, 3 ) );
		// my_graph.addEdge( new Edge( "0_2", 0, 2 ) );

		SlideShow ss = new SlideShow( my_graph );
		ss.run();
	}

}
