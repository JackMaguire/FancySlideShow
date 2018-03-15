package xml_parsing;

import java.util.HashMap;

import org.w3c.dom.Node;

import conceptual_graph.ConceptualEdge;
import conceptual_graph.ConceptualGraph;

public class ConceptualEdgeFactory {

	private final static String[] xml_names_ = new String[] { "Edge", "ZoomInTransition" };

	public static boolean XMLNameIsEdgeType( String name ) {
		for( String s : xml_names_ ) {
			if( s.equalsIgnoreCase( name ) )
				return true;
		}
		return false;
	}

	public static ConceptualEdge create( Node edge_node, HashMap< String, Integer > local_index_for_node_title,
			int node_offset, String filename_prefix ) throws XMLParsingException {
		final String node_name = edge_node.getNodeName();

		if( node_name.equalsIgnoreCase( "Edge" ) ) {
			return makeEdge( edge_node, local_index_for_node_title, node_offset, filename_prefix );
		} else if( node_name.equalsIgnoreCase( "ZoomInTransition" ) ) {
			return makeZoomInTransition( edge_node, local_index_for_node_title, node_offset );
		} else {
			if( XMLNameIsEdgeType( node_name ) ) {
				System.err.println( "ConceptualEdgeFactory::xml_names_ is out of date: " + node_name );
				System.exit( 1 );
			} else {
				throw new XMLParsingException( "ConceptualEdgeFactory: " + node_name + " is undefined." );
			}
		}

		return null;
	}

	private static ConceptualEdge makeEdge( Node edge_node, HashMap< String, Integer > local_index_for_node_title,
			int node_offset, String filename_prefix ) {

		// Attributes

		return null;// TODO
	}

	private static ConceptualEdge makeZoomInTransition( Node edge_node,
			HashMap< String, Integer > local_index_for_node_title, int node_offset ) {
		return null;// TODO
	}

}
