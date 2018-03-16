package xml_parsing;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import conceptual_graph.*;
import xml_parsing.edges.ConceptualEdgeFactory;

public class GraphFromXML {

	public final static String XML_Name = "Graph";

	public static ConceptualGraph parse( Node graph_node ) throws Exception {
		if( !graph_node.getNodeName().equalsIgnoreCase( XML_Name ) ) {
			System.err.println( "Incorrect Node Passed to GraphFromXML" );
		}

		// final int num_frame_spaces_ = countFrameSpaces( graph_node );
		final ArrayList< FrameSpaceParser > frame_spaces = extractFrameSpaces( graph_node );
		final int num_frame_spaces = frame_spaces.size();
		if( num_frame_spaces == 0 ) {
			throw new XMLParsingException( "No Frame Spaces Declared" );
		}

		int total_num_nodes = 0;
		int[] offset_for_frame_space = new int[ num_frame_spaces ];

		for( int i = 0; i < num_frame_spaces; ++i ) {
			offset_for_frame_space[ i ] = total_num_nodes;
			total_num_nodes += frame_spaces.get( i ).numNodes();
		}

		final ConceptualGraph graph = new ConceptualGraph( total_num_nodes, num_frame_spaces );
		for( int i = 0; i < num_frame_spaces; ++i )
			frame_spaces.get( i ).applyToGraph( graph, offset_for_frame_space[ i ] );

		// Global Edges
		final ArrayList< ConceptualEdgeType > global_edges = extractInterFrameSpaceEdges( graph_node, frame_spaces,
				offset_for_frame_space );
		for( ConceptualEdgeType e : global_edges ) {
			graph.addEdge( e );
		}

		return graph;
	}

	private final static ArrayList< FrameSpaceParser > extractFrameSpaces( Node graph_node ) throws XMLParsingException {
		ArrayList< FrameSpaceParser > list = new ArrayList< FrameSpaceParser >();

		int frame_space_num = 0;

		final NodeList elements = graph_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();

			if( element_name.equalsIgnoreCase( FrameSpaceParser.XML_Name ) ) {
				list.add( new FrameSpaceParser( element, frame_space_num++ ) );
			}
		}

		return list;
	}

	private final static ArrayList< ConceptualEdgeType > extractInterFrameSpaceEdges( Node graph_node,
			ArrayList< FrameSpaceParser > parsed_frame_spaces, int[] offset_for_frame_space ) throws XMLParsingException {

		ArrayList< ConceptualEdgeType > edges = new ArrayList< ConceptualEdgeType >();

		final NodeList elements = graph_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();

			if( element_name.equalsIgnoreCase( ConceptualEdgeFactory.global_edge_xml_name ) ) {
				//Look for edge subelements
				final NodeList subelements = element.getChildNodes();
				final int n_subelements = subelements.getLength();
				for( int j = 0; j < n_subelements; ++j ) {
					final Node subelement = subelements.item( j );
					final String subelement_name = subelement.getNodeName();
					if( ConceptualEdgeFactory.XMLNameIsEdgeType( subelement_name ) ) {
						edges.add( ConceptualEdgeFactory.create( subelement, parsed_frame_spaces, offset_for_frame_space ) );
					}
				}
			}
		}

		return edges;
	}

}
