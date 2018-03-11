package applications.one_time;

import graph.Edge;
import graph.Graph;
import graph.Node;
import slide_show.SlideShow;

public class March2018LabMeeting {

	private static String path_to_top_dir_ = "/Users/jackmaguire/Dropbox/March2018LabMeetingSlides";

	// subgraphs
	private static int TITLE_SUBGRAPH = 0;
	private static int MRS_SUBGRAPH = 1;

	private final static int num_nodes_for_title_slide = 4;
	private final static int num_nodes_for_MRS_slide = 10;
	private final static int num_nodes = num_nodes_for_title_slide + num_nodes_for_MRS_slide;
	
	public static void main( String[] args ) throws Exception {

		if( args.length != 0 ) {
			path_to_top_dir_ = args[ 0 ];
		}

		Graph my_graph = new Graph( num_nodes, 2 );
		my_graph.setSubgraphName( 0, "TITLE" );
		my_graph.setSubgraphName( 1, "MRS" );

		int current_offset = 0;

		setTitleSlideNodesAndEdges( my_graph, current_offset );
		current_offset += num_nodes_for_title_slide;

		setMRSNodesAndEdges( my_graph, current_offset );
		// add edge from title slide to mrs
		my_graph.addEdge( new Edge( "", current_offset - 1, current_offset ) );
		current_offset += num_nodes_for_MRS_slide;

		SlideShow ss = new SlideShow( my_graph );
		ss.run();
	}

	private static String getFilename( String subdir, int framenum ) {
		final String result = path_to_top_dir_ + "/" + subdir + "/" + String.format( "%04d", framenum ) + ".png";
		// System.out.println( result );
		return result;
	}

	private static void setTitleSlideNodesAndEdges( Graph graph, int offset ) {

		int node_id = offset;

		graph.setNode( new Node( "Title", false, getFilename( "Title", 0 ), "Good Luck!" ), node_id++, TITLE_SUBGRAPH );
		graph.setNode( new Node( "Title_mid", false, getFilename( "Title", 250 ), "" ), node_id++, TITLE_SUBGRAPH );
		graph.setNode( new Node( "Title_final_start", false, getFilename( "Title", 501 ), "" ), node_id++, TITLE_SUBGRAPH );
		graph.setNode( new Node( "Title_final_end", true, getFilename( "Title", 550 ), "" ), node_id++, TITLE_SUBGRAPH );

		{// 0 - 1
			String[] filenames_for_0_to_1 = new String[ 249 ];
			for( int i = 0; i < filenames_for_0_to_1.length; ++i ) {
				filenames_for_0_to_1[ i ] = getFilename( "Title", 1 + i );
			}
			graph.addEdge( new Edge( "", 0, 1, filenames_for_0_to_1 ) );
		}

		{// 1 - 0
			String[] filenames_for_1_to_0 = new String[ 500 - 250 ];
			for( int i = 0; i < filenames_for_1_to_0.length; ++i ) {
				filenames_for_1_to_0[ i ] = getFilename( "Title", 251 + i );
			}
			graph.addEdge( new Edge( "", 1, 0, filenames_for_1_to_0 ) );
		}

		// 0 - 2
		graph.addEdge( new Edge( "", 0, 2 ) );

		// 1 - 2
		graph.addEdge( new Edge( "", 1, 2 ) );

		{// 2 - 3
			String[] filenames_for_2_to_3 = new String[ 550 - 502 ];
			for( int i = 0; i < filenames_for_2_to_3.length; ++i ) {
				filenames_for_2_to_3[ i ] = getFilename( "Title", 502 + i );
			}
			graph.addEdge( new Edge( "", 2, 3, filenames_for_2_to_3 ) );
		}
	}

	private static void setMRSNodesAndEdges( Graph graph, int offset ) throws Exception {

		int node_id = offset;

		final String dir1 = "MRS";
		
		graph.setNode( new Node( "Title", true, getFilename( dir1, 0 ), "Good Luck!" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Protocol", true, getFilename( dir1, 30 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "StageProtocol", false, getFilename( dir1, 56 ), "" ), node_id++, MRS_SUBGRAPH );
		
		graph.setNode( new Node( "StageProtocol_I", false, getFilename( dir1, 57 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "StageProtocol_II", false, getFilename( dir1, 58 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "StageProtocol_III", true, getFilename( dir1, 59 ), "" ), node_id++, MRS_SUBGRAPH );
		
		//graph.setNode( new Node( "StageProtocol", false, getFilename( dir1, 60 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Split", true, getFilename( dir1, 100 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Filtered1", true, getFilename( dir1, 101 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Fork", true, getFilename( dir1, 130 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Filtered2", true, getFilename( dir1, 131 ), "" ), node_id++, MRS_SUBGRAPH );
		
		if( node_id - offset != num_nodes_for_MRS_slide ){
			throw new Exception("node_id - offset != num_nodes_for_MRS_slide");
		}
		
		{// 0 - 1
			String[] filenames = new String[ 29 ];
			for( int i = 0; i < filenames.length; ++i ) {
				filenames[ i ] = getFilename( dir1, 1 + i );
			}
			graph.addEdge( new Edge( "Wreck It", offset, offset + 1, filenames ) );
		}
		
		{// 1 - 2
			String[] filenames = new String[ 55 - 30 ];
			for( int i = 0; i < filenames.length; ++i ) {
				filenames[ i ] = getFilename( dir1, 31 + i );
			}
			graph.addEdge( new Edge( "", offset + 1, offset + 2, filenames ) );
		}
		
		{// 2 - 3
			//TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 2, offset + 3 ) );
		}
		
		{// 3 - 4
			//TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 3, offset + 4 ) );
		}
		
		{// 4 - 5
			//TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 4, offset + 5 ) );
		}
		
		{// 5 - 6
			String[] filenames = new String[ 99 - 59 ];
			for( int i = 0; i < filenames.length; ++i ) {
				filenames[ i ] = getFilename( dir1, 60 + i );
			}
			graph.addEdge( new Edge( "", offset + 5, offset + 6, filenames ) );
		}
		
		{// 6 - 7
			//TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 6, offset + 7 ) );
		}
		
		{// 7 - 8
			String[] filenames = new String[ 130 - 102 ];
			for( int i = 0; i < filenames.length; ++i ) {
				filenames[ i ] = getFilename( dir1, 102 + i );
			}
			graph.addEdge( new Edge( "Fork", offset + 7, offset + 8, filenames ) );
		}
		
		{// 8 - 9
			//TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 8, offset + 9 ) );
		}
	}

}
