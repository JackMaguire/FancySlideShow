package test.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import applications.FrameScript;
import conceptual_graph.ConceptualEdgeType;
import conceptual_graph.ConceptualGraph;
import conceptual_graph.ConceptualNodeType;
import settings.FrameCacheSettings;
import test.SingleTest;
import xml_parsing.GraphFromXML;
import xml_parsing.Util;

public class DemoFrameGraphTest extends SingleTest {

	@Override
	public boolean run() {

		FrameCacheSettings.LOAD_CACHES = false;
		FrameCacheSettings.PRIMARY_NODE_CACHE_RATIO = 1.0;
		FrameCacheSettings.SECONDARY_NODE_CACHE_RATIO = 1.0;

		try {
			Node frame_script_node = Util.readFromFile( "demo/script.xml" );
			if( !equals_string( "top level name", frame_script_node.getNodeName(), FrameScript.top_level_element_name ) ) {
				return false;
			}

			Node graph_node = null;
			final NodeList elements = frame_script_node.getChildNodes();
			final int n = elements.getLength();
			for( int i = 0; i < n; ++i ) {
				final Node element = elements.item( i );
				if( element.getNodeName().equalsIgnoreCase( GraphFromXML.XML_Name ) ) {
					graph_node = element;
					break;
				}
			}

			if( graph_node == null ) {
				err( "Could node find <Graph> section" );
				return false;
			}

			ConceptualGraph graph = GraphFromXML.parse( graph_node );
			if( graph == null ) {
				System.err.println( "graph == null" );
				return false;
			}
			return validateGraph( graph );
		}
		catch( Exception e ) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
			return false;
		}

	}

	private boolean validateGraph( ConceptualGraph graph ) {

		boolean is_valid = true;

		// Graph
		is_valid |= equals_int( "num nodes", graph.numNodes(), 4 );
		is_valid |= equals_int( "num nodes in subgraph 0", graph.numNodesInSubgraph( 0 ), 4 );
		is_valid |= equals_int( "num subgraphs", graph.numSubgraphs(), 1 );

		// Nodes
		{// adding scopes to protect myself from copy/paste errors
			final ConceptualNodeType node0 = graph.getNode( 0 );
			is_valid |= equals_string( "node 0 notes", node0.getNotes(), "Good luck with your presentation, handsome!" );
			is_valid |= equals_bool( "node 0 is hard", node0.is_hard(), true );
			is_valid |= equals_int( "node 0 subgraph", node0.subgraph(), 0 );
			is_valid |= equals_string( "node 0 filename", node0.getImageFilename(), "demo/PicsForDemo/Track1/0001.png" );
			is_valid |= equals_int( "num node 0 upstream edges", node0.getDownstreamEdges().length, 1 );
			is_valid |= equals_int( "num node 0 upstream edges", node0.getUpstreamEdges().length, 0 );
		}

		{
			final ConceptualNodeType node1 = graph.getNode( 1 );
			is_valid |= equals_string( "node 1 notes", node1.getNotes(),
					"this is the branch point\n\nYou Can Have\n\nMultiple Lines\n\nOf Notes!" );
			is_valid |= equals_bool( "node 1 is hard", node1.is_hard(), false );
			is_valid |= equals_int( "node 1 subgraph", node1.subgraph(), 0 );
			is_valid |= equals_string( "node 1 filename", node1.getImageFilename(), "demo/PicsForDemo/Track1/0130.png" );
			is_valid |= equals_int( "num node 1 upstream edges", node1.getDownstreamEdges().length, 2 );
			is_valid |= equals_int( "num node 1 upstream edges", node1.getUpstreamEdges().length, 1 );
		}

		{
			final ConceptualNodeType node2 = graph.getNode( 2 );
			is_valid |= equals_string( "node 2 notes", node2.getNotes(), "" );
			is_valid |= equals_bool( "node 2 is hard", node2.is_hard(), true );
			is_valid |= equals_int( "node 2 subgraph", node2.subgraph(), 0 );
			is_valid |= equals_string( "node 2 filename", node2.getImageFilename(), "demo/PicsForDemo/Track1/0250.png" );
			is_valid |= equals_int( "num node 2 upstream edges", node2.getDownstreamEdges().length, 0 );
			is_valid |= equals_int( "num node 2 upstream edges", node2.getUpstreamEdges().length, 1 );
		}

		{
			final ConceptualNodeType node3 = graph.getNode( 3 );
			is_valid |= equals_string( "node 3 notes", node3.getNotes(), "only note is out of line" );
			is_valid |= equals_bool( "node 3 is hard", node3.is_hard(), true );
			is_valid |= equals_int( "node 3 subgraph", node3.subgraph(), 0 );
			is_valid |= equals_string( "node 3 filename", node3.getImageFilename(), "demo/PicsForDemo/Track2/0250.png" );
			is_valid |= equals_int( "num node 3 upstream edges", node3.getDownstreamEdges().length, 0 );
			is_valid |= equals_int( "num node 3 upstream edges", node3.getUpstreamEdges().length, 1 );
		}

		if( !is_valid ) {
			// We can not test the edges if the nodes are problematic
			return false;
		}

		// Edges
		{// 0-1
			final ConceptualEdgeType edge_0_to_1 = graph.getNode( 0 ).getDownstreamEdges()[ 0 ];
			is_valid |= equals_object( "edge_0_to_1 vs reverse_edge_1_to_0", edge_0_to_1,
					graph.getNode( 1 ).getUpstreamEdges()[ 0 ] );
			
			is_valid |= equals_int( "edge 0-1 incoming node index", edge_0_to_1.incomingNodeIndex(), 1 );
			is_valid |= equals_int( "edge 0-1 outgoing node index", edge_0_to_1.outgoingNodeIndex(), 0 );

			final String[] filenames = edge_0_to_1.getImageFilenames();
			is_valid |= equals_int( "num edge 0-1 filenames", filenames.length, 128 );
			for( int i = 0; i < filenames.length; ++i ) {
				final boolean is_good = equals_string( "edge 0-1 filename #" + i, filenames[ i ],
						"demo/PicsForDemo/Track1/0" + String.format( "%03d", i + 2 ) + ".png" );
				if( !is_good ) {
					is_valid = false;
					break;// break loop so that we do not end up with hundreds of error messages
				}
			}
		}

		{// 1-2
			final ConceptualEdgeType edge_1_to_2 = graph.getNode( 1 ).getDownstreamEdges()[ 0 ];
			is_valid |= equals_object( "edge_1_to_2 vs reverse_edge_2_to_1", edge_1_to_2,
					graph.getNode( 2 ).getUpstreamEdges()[ 0 ] );
			
			is_valid |= equals_int( "edge 1-2 incoming node index", edge_1_to_2.incomingNodeIndex(), 2 );
			is_valid |= equals_int( "edge 1-2 outgoing node index", edge_1_to_2.outgoingNodeIndex(), 1 );

			final String[] filenames = edge_1_to_2.getImageFilenames();
			is_valid |= equals_int( "num edge 1-2 filenames", filenames.length, 249 - 130 );
			for( int i = 0; i < filenames.length; ++i ) {
				final boolean is_good = equals_string( "edge 1-2 filename #" + i, filenames[ i ],
						"demo/PicsForDemo/Track1/0" + String.format( "%03d", i + 131 ) + ".png" );
				if( !is_good ) {
					is_valid = false;
					break;// break loop so that we do not end up with hundreds of error messages
				}
			}
		}
		
		{// 1-3
			final ConceptualEdgeType edge_1_to_3 = graph.getNode( 1 ).getDownstreamEdges()[ 1 ];
			is_valid |= equals_object( "edge_1_to_3 vs reverse_edge_3_to_1", edge_1_to_3,
					graph.getNode( 3 ).getUpstreamEdges()[ 0 ] );
			
			is_valid |= equals_int( "edge 1-3 incoming node index", edge_1_to_3.incomingNodeIndex(), 3 );
			is_valid |= equals_int( "edge 1-3 outgoing node index", edge_1_to_3.outgoingNodeIndex(), 1 );

			final String[] filenames = edge_1_to_3.getImageFilenames();
			is_valid |= equals_int( "num edge 1-3 filenames", filenames.length, 249 - 130 );
			for( int i = 0; i < filenames.length; ++i ) {
				final boolean is_good = equals_string( "edge 1-3 filename #" + i, filenames[ i ],
						"demo/PicsForDemo/Track2/0" + String.format( "%03d", i + 131 ) + ".png" );
				if( !is_good ) {
					is_valid = false;
					break;// break loop so that we do not end up with hundreds of error messages
				}
			}
		}
		
		return is_valid;
	}

	@Override
	public String name() {
		return "DemoFrameGraphTest";
	}

}
