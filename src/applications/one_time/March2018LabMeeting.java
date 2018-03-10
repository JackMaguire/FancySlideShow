package applications.one_time;

import graph.Edge;
import graph.Graph;
import graph.Node;
import slide_show.SlideShow;

public class March2018LabMeeting {

	private static String path_to_top_dir_ = "/Users/jackmaguire/Dropbox/March2018LabMeetingSlides";

	//subgraphs
	private static int TITLE_SUBGRAPH = 0;
	private static int MRS_SUBGRAPH = 1;
	
	public static void main( String[] args ) {

		if( args.length != 0 ) {
			path_to_top_dir_ = args[ 0 ];
		}
		
		final int num_nodes_for_title_slide = 4;
		final int num_nodes_for_MRS_slide = 2;
		final int num_nodes = num_nodes_for_title_slide + num_nodes_for_MRS_slide;

		Graph my_graph = new Graph( num_nodes, 2 );
		my_graph.setSubgraphName( 0, "TITLE" );
		my_graph.setSubgraphName( 1, "MRS" );
		
		int current_offset = 0;
		
		setTitleSlideNodesAndEdges( my_graph, current_offset );
		current_offset += num_nodes_for_title_slide;
		
		setMRSNodesAndEdges( my_graph, current_offset );
		//add edge from title slide to mrs
		my_graph.addEdge( new Edge( "", current_offset - 1, current_offset ) );
		current_offset += num_nodes_for_MRS_slide;
		
		SlideShow ss = new SlideShow( my_graph );
		ss.run();
	}

	private static String getFilename( String subdir, int framenum ) {
		final String result = path_to_top_dir_ + "/" + subdir + "/" + String.format( "%04d", framenum ) + ".png";
		//System.out.println( result );
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
	
	private static void setMRSNodesAndEdges( Graph graph, int offset ) {

		int node_id = offset;
		
		graph.setNode( new Node( "Title", true, getFilename( "MRS", 60 ), "Good Luck!" ), node_id++, MRS_SUBGRAPH );
		graph.setNode( new Node( "Title_end", false, getFilename( "MRS", 180 ), "" ), node_id++, MRS_SUBGRAPH );
		
		{// 0 - 1
			String[] filenames_for_0_to_1 = new String[ 180 - 61 ];
			for( int i = 0; i < filenames_for_0_to_1.length; ++i ) {
				filenames_for_0_to_1[ i ] = getFilename( "MRS", 61 + i );
			}
			graph.addEdge( new Edge( "Wrecking Ball!", offset, offset + 1, filenames_for_0_to_1 ) );
		}
	}

}
