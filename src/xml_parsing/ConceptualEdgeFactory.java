package xml_parsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
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
			int node_offset, String filename_prefix ) throws XMLParsingException {

		String title = "";
		int origin_node = -1;
		int destination_node = -1;

		// Attributes
		final NamedNodeMap attribute_nodes = edge_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final String value = attribute.getNodeValue();

			if( attribute_name.equalsIgnoreCase( "title" ) ) {
				title = value;
			} else if( attribute_name.equalsIgnoreCase( "origin_node" ) ) {
				if( !local_index_for_node_title.containsKey( value ) ) {
					throw new XMLParsingException( "Edge can not find Node with name: " + value );
				}
				origin_node = node_offset + local_index_for_node_title.get( value );
			} else if( attribute_name.equalsIgnoreCase( "destination_node" ) ) {
				if( !local_index_for_node_title.containsKey( value ) ) {
					throw new XMLParsingException( "Edge can not find Node with name: " + value );
				}
				destination_node = node_offset + local_index_for_node_title.get( value );
			} else if( !attribute_name.startsWith( "#" ) ) {
				System.err.println( "Edge has no match for " + attribute_name );
				throw new XMLParsingException( "Edge has no match for " + attribute_name );
			}
		}

		if( origin_node == -1 ) {
			throw new XMLParsingException( "Edge has no origin node: " + title );
		}
		
		if( destination_node == -1 ) {
			throw new XMLParsingException( "Edge has no destination node: " + title );
		}
		
		final ArrayList< String > frame_filenames = new ArrayList< String >();
		
		return null;// TODO
	}

	private static ConceptualEdge makeZoomInTransition( Node edge_node,
			HashMap< String, Integer > local_index_for_node_title, int node_offset ) {
		return null;// TODO
	}

}
