package applications;

import slide_show.*;

import java.io.File;

import graph.*;

public class Demo {

	public static void main( String[] args ) {
		final int num_nodes = 4;
		ConceptualGraph my_graph = new ConceptualGraph( num_nodes );

		Node start_node = new Node( "start", true );
		start_node.setNotes( "Don't forget to speak into the microphone please!" );
		start_node.setImageFilename( "src/PicsForDemo/Track1/0001.png" );
		// File f = new File("src/PicsForDemo/Track1/0001.png");
		my_graph.setNode( start_node, 0 );

		my_graph.setNode( new Node( "branch", true, "src/PicsForDemo/Track1/0130.png" ), 1 );
		my_graph.setNode( new Node( "end1", true, "src/PicsForDemo/Track1/0250.png", "Don't forget to thank Yoda" ), 2 );
		my_graph.setNode( new Node( "end2", true, "src/PicsForDemo/Track2/0250.png" ), 3 );

		// my_graph.getNode( 0 )

		String[] filenames_for_start_to_branch = new String[ 128 ];
		for( int i = 0; i < 128; ++i ) {
			filenames_for_start_to_branch[ i ] = "src/PicsForDemo/Track1/0" + String.format( "%03d", i + 2 ) + ".png";
		}
		my_graph.addEdge( new Edge( "", 0, 1, filenames_for_start_to_branch ) );

		String[] filenames_for_branch_to_end1 = new String[ 119 ];
		for( int i = 0; i < 119; ++i ) {
			filenames_for_branch_to_end1[ i ] = "src/PicsForDemo/Track1/0" + String.format( "%03d", i + 131 ) + ".png";
		}
		my_graph.addEdge( new Edge( "", 1, 2, filenames_for_branch_to_end1 ) );

		String[] filenames_for_branch_to_end2 = new String[ 119 ];
		for( int i = 0; i < 119; ++i ) {
			filenames_for_branch_to_end2[ i ] = "src/PicsForDemo/Track2/0" + String.format( "%03d", i + 131 ) + ".png";
		}
		my_graph.addEdge( new Edge( "", 1, 3, filenames_for_branch_to_end2 ) );

		SlideShow ss = new SlideShow( my_graph );
		ss.run();
	}

}
