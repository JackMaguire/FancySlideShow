package test.xml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import applications.FrameScript;
import conceptual_graph.ConceptualGraph;
import conceptual_graph.ConceptualNode;
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
			Node frame_script_node = Util.readFromFile( "src/demo/script.xml" );
			if( !diff( "top level name", frame_script_node.getNodeName(), FrameScript.top_level_element_name ) ) {
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
		is_valid |= diff( "num nodes", graph.numNodes(), 4 );
		is_valid |= diff( "num nodes in subgraph 0", graph.numNodesInSubgraph( 0 ), 4 );
		is_valid |= diff( "num subgraphs", graph.numSubgraphs(), 1 );

		// Nodes
		{// adding scopes to protect myself from copy/paste errors
			final ConceptualNodeType node0 = graph.getNode( 0 );
			is_valid |= diff( "node 0 notes", node0.getNotes(), "Good luck with your presentation, handsome!" );
			is_valid |= diff( "node 0 is hard", node0.is_hard(), true );
			is_valid |= diff( "node 0 subgraph", node0.subgraph(), 0 );
			is_valid |= diff( "node 0 filename", node0.getImageFilename(), "src/demo/PicsForDemo/Track1/0001.png" );
			is_valid |= diff( "num node 0 upstream edges", node0.getDownstreamEdges().length, 1 );
			is_valid |= diff( "num node 0 upstream edges", node0.getUpstreamEdges().length, 0 );
		}

		{
			final ConceptualNodeType node1 = graph.getNode( 1 );
			is_valid |= diff( "node 1 notes", node1.getNotes(),
					"this is the branch point\n\nYou Can Have\n\nMultiple Lines\n\nOf Notes!" );
			is_valid |= diff( "node 1 is hard", node1.is_hard(), false );
			is_valid |= diff( "node 1 subgraph", node1.subgraph(), 0 );
			is_valid |= diff( "node 1 filename", node1.getImageFilename(), "src/demo/PicsForDemo/Track1/0130.png" );
			is_valid |= diff( "num node 1 upstream edges", node1.getDownstreamEdges().length, 2 );
			is_valid |= diff( "num node 1 upstream edges", node1.getUpstreamEdges().length, 1 );
		}

		{
			final ConceptualNodeType node2 = graph.getNode( 2 );
			is_valid |= diff( "node 2 notes", node2.getNotes(), "" );
			is_valid |= diff( "node 2 is hard", node2.is_hard(), true );
			is_valid |= diff( "node 2 subgraph", node2.subgraph(), 0 );
			is_valid |= diff( "node 2 filename", node2.getImageFilename(), "src/demo/PicsForDemo/Track1/0250.png" );
			is_valid |= diff( "num node 2 upstream edges", node2.getDownstreamEdges().length, 0 );
			is_valid |= diff( "num node 2 upstream edges", node2.getUpstreamEdges().length, 1 );
		}

		{
			final ConceptualNodeType node3 = graph.getNode( 3 );
			is_valid |= diff( "node 3 notes", node3.getNotes(), "only note is out of line" );
			is_valid |= diff( "node 3 is hard", node3.is_hard(), true );
			is_valid |= diff( "node 3 subgraph", node3.subgraph(), 0 );
			is_valid |= diff( "node 3 filename", node3.getImageFilename(), "src/demo/PicsForDemo/Track2/0250.png" );
			is_valid |= diff( "num node 3 upstream edges", node3.getDownstreamEdges().length, 0 );
			is_valid |= diff( "num node 3 upstream edges", node3.getUpstreamEdges().length, 1 );
		}

		return is_valid;
	}

	@Override
	public String name() {
		return "DemoFrameGraphTest";
	}

}
