package applications.one_time;

import graph.Edge;
import graph.Graph;
import graph.Node;
import slide_show.SlideShow;

public class March2018LabMeeting {

	private static String path_to_top_dir_ = "/Users/jackmaguire/Dropbox/March2018LabMeetingSlides";

	// subgraphs
	private static int TITLE_SUBGRAPH = 0;
	private static int MCHBNET_SUBGRAPH = 1;
	private static int MRS_SUBGRAPH = 2;

	private final static int num_nodes_for_title_slide = 2;
	private final static int num_nodes_for_MRS_slide = 11;
	private final static int num_nodes_for_MCHBNet_slide = 29;
	private final static int num_nodes = num_nodes_for_title_slide + num_nodes_for_MRS_slide
			+ num_nodes_for_MCHBNet_slide;

	public static void main( String[] args ) throws Exception {

		if( args.length != 0 ) {
			path_to_top_dir_ = args[ 0 ];
		}

		Graph my_graph = new Graph( num_nodes, 3 );
		my_graph.setSubgraphName( 0, "TITLE" );
		my_graph.setSubgraphName( 1, "MCHBNet" );
		my_graph.setSubgraphName( 2, "MRS" );

		int current_offset = 0;

		setTitleSlideNodesAndEdges( my_graph, current_offset );
		current_offset += num_nodes_for_title_slide;

		setMCHBNetNodesAndEdges( my_graph, current_offset );
		// add edge from title slide to mrs
		my_graph.addEdge( new Edge( "", current_offset - 1, current_offset ) );
		current_offset += num_nodes_for_MCHBNet_slide;

		setMRSNodesAndEdges( my_graph, current_offset );
		// add edge from title slide to mrs
		my_graph.addEdge( new Edge( "", current_offset - 1, current_offset ) );
		current_offset += num_nodes_for_MRS_slide;

		for( int i = 0; i < num_nodes - 1; ++i ) {
			if( my_graph.getNode( i ).getDownstreamEdges().length == 0 ) {
				System.err.println( "Node " + i + " \"" + my_graph.getNode( i ).name() + "\" does not have a downstream edge" );
				// System.exit( 1 );
			}
		}

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
		final String dirname = "MCHBNetTitle";

		graph.setNode( new Node( "Title", false, getFilename( dirname, 1 ), "Good Luck!" ), node_id++, TITLE_SUBGRAPH );
		graph.setNode( new Node( "Title_end", true, getFilename( dirname, 340 ), "" ), node_id++, TITLE_SUBGRAPH );

		int num_frames = 2;

		{// 0 - 0
			String[] filenames = new String[ 249 - 1 ];
			for( int i = 0; i < filenames.length; ++i ) {
				filenames[ i ] = getFilename( dirname, 2 + i );
				++num_frames;
			}
			graph.addEdge( new Edge( "", 0, 0, filenames ) );
		}

		{// 0 - 1
			String[] filenames = new String[ 339 - 250 ];// 250 is duplicate of 1
			for( int i = 0; i < filenames.length; ++i ) {
				filenames[ i ] = getFilename( dirname, 251 + i );
				++num_frames;
			}
			graph.addEdge( new Edge( "", 0, 1, filenames ) );

			// sanity check
			// System.out.println("num_frames for title slide: " + num_frames );
		}
	}

	/*private static void setTitleSlideNodesAndEdges( Graph graph, int offset ) {
	
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
	
	}*/

	private static void setMRSNodesAndEdges( Graph graph, int offset ) throws Exception {

		int node_id = offset;

		final String dir1 = "MRS";

		// 0-2
		graph.setNode( new Node( "Title", true, getFilename( dir1, 0 ), "Good Luck!" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Protocol", true, getFilename( dir1, 30 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "StageProtocol", false, getFilename( dir1, 56 ), "" ), node_id++, MRS_SUBGRAPH );

		// 3-5
		graph.setNode( new Node( "StageProtocol_I", false, getFilename( dir1, 57 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "StageProtocol_II", false, getFilename( dir1, 58 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "StageProtocol_III", true, getFilename( dir1, 59 ), "" ), node_id++, MRS_SUBGRAPH );

		// 6-9
		// graph.setNode( new Node( "StageProtocol", false, getFilename( dir1, 60 ), ""
		// ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Split", true, getFilename( dir1, 100 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Filtered1", true, getFilename( dir1, 101 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Fork", true, getFilename( dir1, 130 ), "" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Filtered2", true, getFilename( dir1, 131 ), "" ), node_id++, MRS_SUBGRAPH );

		// 10
		graph.setNode( new Node( "AltStart", true, getFilename( "MRS/title", 0 ) ), node_id++, MRS_SUBGRAPH );

		if( node_id - offset != num_nodes_for_MRS_slide ) {
			throw new Exception( "node_id - offset != num_nodes_for_MRS_slide" );
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
			// TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 2, offset + 3 ) );
		}

		{// 3 - 4
			// TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 3, offset + 4 ) );
		}

		{// 4 - 5
			// TODO Dissolve Transition
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
			// TODO Dissolve Transition
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
			// TODO Dissolve Transition
			graph.addEdge( new Edge( "", offset + 8, offset + 9 ) );
		}

		{// 10 - 1
			String[] filenames = new String[ 57 ];
			for( int i = 0; i < filenames.length; ++i ) {
				filenames[ i ] = getFilename( "MRS/title", 1 + i );
			}
			graph.addEdge( new Edge( "Wrecking Ball", offset + 10, offset + 1, filenames ) );
		}
	}

	private final static void setMCHBNetNodesAndEdges( Graph graph, int offset ) {

		String[] slide_names = new String[ num_nodes_for_MCHBNet_slide ];
		for( int i = 0; i < num_nodes_for_MCHBNet_slide; ++i ) {
			slide_names[ i ] = "";
		}
		slide_names[ 0 ] = "Title";
		slide_names[ 1 ] = "Boxes";
		// slide_names[ 2 ] = "Boxes (2)";
		// slide_names[ 3 ] = "Boxes (3)";
		slide_names[ 4 ] = "Figures A-E";
		slide_names[ 7 ] = "Back To The Boxes";
		slide_names[ 11 ] = "EXN Title Slide";
		slide_names[ 15 ] = "No Flags";
		slide_names[ 17 ] = "1 Flag";
		slide_names[ 18 ] = "2 Flags";
		slide_names[ 19 ] = "3 Flags";
		slide_names[ 20 ] = "4 Flags";
		slide_names[ 24 ] = "Pt of Max Diff";
		slide_names[ 25 ] = "MC HBNet 1";
		slide_names[ 27 ] = "MC HBNet 2";
		// Fill in individual values if wanted

		// Nodes
		for( int i = 0; i < num_nodes_for_MCHBNet_slide; ++i ) {
			final String filename = path_to_top_dir_ + "/MCHBNet/Slide" + String.format( "%02d", i + 1 ) + ".png";
			graph.setNode( new Node( slide_names[ i ], true, filename, "" ), offset + i, MCHBNET_SUBGRAPH );
		}

		// Edges
		for( int i = 1; i < num_nodes_for_MCHBNet_slide; ++i ) {
			graph.addEdge( new Edge( "", offset + i - 1, offset + i ) );
		}
	}

}
