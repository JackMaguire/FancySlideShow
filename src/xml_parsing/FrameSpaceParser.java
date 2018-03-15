package xml_parsing;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import conceptual_graph.*;

public class FrameSpaceParser {

	public final static String XML_Name = "FrameSpace";

	private static String frame_space_name_ = "Untitled";
	private static String filename_prefix_ = "";

	private final ArrayList< ConceptualNode > nodes_ = new ArrayList< ConceptualNode >();
	private final HashMap< String, Integer > local_index_for_node_title_ = new HashMap< String, Integer >();

	public FrameSpaceParser( Node frame_space_node ) throws XMLParsingException {

		// Attributes
		final NamedNodeMap attribute_nodes = frame_space_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final String value = attribute.getNodeValue();

			if( attribute_name.equalsIgnoreCase( "name" ) ) {
				frame_space_name_ = value;
			} else if( attribute_name.equalsIgnoreCase( "filename_prefix" ) ) {
				filename_prefix_ = value;
			} else if( !attribute_name.startsWith( "#" ) ) {
				System.err.println( XML_Name + " has no match for " + attribute_name );
				throw new XMLParsingException( XML_Name + " has no match for " + attribute_name );
			}
		}

		final NodeList elements = frame_space_node.getChildNodes();
		final int n = elements.getLength();
		Node nodes_node = null;
		Node edges_node = null;

		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();

			if( element_name.equalsIgnoreCase( "Nodes" ) ) {
				if( nodes_node != null ) {
					throw new XMLParsingException( "Multiple Nodes elements in framespace: " + frame_space_name_ );
				}
				nodes_node = element;
			} else if( element_name.equalsIgnoreCase( "Edges" ) ) {
				edges_node = element;
			}
		}

		// final int num_nodes = countNumElementsWithName( nodes_node, "Node", false );
	}

	public void applyToGraph( ConceptualGraph graph, int node_offset ) {

	}

	public int numNodes() {
		return nodes_.size();
	}

	private ConceptualNode[] nodes( Node nodes_node, ConceptualGraph graph ) {
		final int num_nodes = countNumElementsWithName( nodes_node, "Node", false );
		ConceptualNode[] nodes = new ConceptualNode[ num_nodes ];
		int current_node = 0;

		final NodeList elements = nodes_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();
			
			if( element_name.equalsIgnoreCase( "Node" ) ) {
				
			}
			
		}
		
		return nodes;
	}

	private final ConceptualNode createConceptualNode( Node node_node, int local_node_index ) throws XMLParsingException {
		String title="";
		boolean hard = true;
		String filename="";
		String notes="";
		
		//Attributes
		final NamedNodeMap attribute_nodes = node_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final String value = attribute.getNodeValue();

			if( attribute_name.equalsIgnoreCase( "title" ) ) {
				title = value;
			} else if( attribute_name.equalsIgnoreCase( "filename" ) ) {
				filename = filename_prefix_ + value;
			} else if( attribute_name.equalsIgnoreCase( "notes" ) ) {
				notes = value;
			} else if( attribute_name.equalsIgnoreCase( "hard" ) ) {
				hard = Boolean.parseBoolean( value );
			} else if( !attribute_name.startsWith( "#" ) ) {
				System.err.println( XML_Name + " has no match for " + attribute_name );
				throw new XMLParsingException( XML_Name + " has no match for " + attribute_name );
			}
		}
		
		if( filename.length() == 0 ) {
			throw new XMLParsingException( "Node " + title + " in framespace " + frame_space_name_ + " has no filename." );
		}
		
		final NodeList elements = node_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			if( element.getNodeName().equalsIgnoreCase( "Notes" ) ) {
				notes += "\n" + element.getNodeValue();
			}
		}
		
		String concept_node_title = title;
		if( concept_node_title.startsWith( "_" ) ) {
			concept_node_title = "";
		}
		
		final ConceptualNode con_node = new ConceptualNode( concept_node_title, hard, filename, notes );
		local_index_for_node_title_.put( title, local_node_index );
		
		return con_node;
	}
	
	public final static int countNumElementsWithName( Node node, String name, boolean case_sensitive ) {
		int count = 0;

		final NodeList elements = node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();
			if( case_sensitive ) {
				if( element_name.equals( name ) ) {
					++count;
				}
			} else {
				if( element_name.equalsIgnoreCase( name ) ) {
					++count;
				}
			}
		}

		return count;
	}

}
