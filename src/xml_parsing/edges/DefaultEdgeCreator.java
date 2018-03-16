package xml_parsing.edges;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import conceptual_graph.ConceptualEdge;
import xml_parsing.FrameSpaceParser;
import xml_parsing.XMLParsingException;

public class DefaultEdgeCreator {

	public static ConceptualEdge makeGlobalEdge( Node edge_node, ArrayList< FrameSpaceParser > parsed_frame_spaces,
			int[] offset_for_frame_space ) throws XMLParsingException {

		String title = "";
		String origin_frame_space = "";
		String origin_node = "";
		String destination_frame_space = "";
		String destination_node = "";

		// Attributes
		final NamedNodeMap attribute_nodes = edge_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final String value = attribute.getNodeValue();

			if( attribute_name.equalsIgnoreCase( "title" ) ) {
				title = value;
			} else if( attribute_name.equalsIgnoreCase( "origin_node_frame_space" ) ) {
				origin_frame_space = value;
			} else if( attribute_name.equalsIgnoreCase( "origin_node" ) ) {
				origin_node = value;
			} else if( attribute_name.equalsIgnoreCase( "destination_frame_space" ) ) {
				destination_frame_space = value;
			} else if( attribute_name.equalsIgnoreCase( "destination_node" ) ) {
				destination_node = value;
			} else if( !attribute_name.startsWith( "#" ) ) {
				System.err.println( "Edge has no match for " + attribute_name );
				throw new XMLParsingException( "Edge has no match for " + attribute_name );
			}
		}

		if( origin_frame_space.length() == 0 ) {
			throw new XMLParsingException( "Global edges require origin_frame_space attribute." );
		}
		if( origin_node.length() == 0 ) {
			throw new XMLParsingException( "Global edges require origin_node attribute." );
		}
		if( destination_frame_space.length() == 0 ) {
			throw new XMLParsingException( "Global edges require destination_frame_space attribute." );
		}
		if( destination_node.length() == 0 ) {
			throw new XMLParsingException( "Global edges require destination_node attribute." );
		}

		FrameSpaceParser origin_parser = null;
		FrameSpaceParser destination_parser = null;

		for( FrameSpaceParser fsp : parsed_frame_spaces ) {
			if( fsp.getFrameSpaceName().equals( origin_frame_space ) ) {
				origin_parser = fsp;
			}
			if( fsp.getFrameSpaceName().equals( destination_frame_space ) ) {
				destination_parser = fsp;
			}
		}

		final int start = offset_for_frame_space[ origin_parser.getFrameSpaceID() ]
				+ origin_parser.localIndexForNodeTitle( origin_node );
		final int end = offset_for_frame_space[ destination_parser.getFrameSpaceID() ]
				+ destination_parser.localIndexForNodeTitle( destination_node );

		// Frames!
		final ArrayList< String > frame_filenames = new ArrayList< String >();

		// Elements
		final NodeList elements = edge_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();

			if( element_name.equalsIgnoreCase( "Filename" ) ) {
				appendFilename( element, frame_filenames, "" );
			} else if( element_name.equalsIgnoreCase( "FilenameRange" ) ) {
				appendFilenameRange( element, frame_filenames, "" );
			} else if( !element_name.startsWith( "#" ) ) {
				throw new XMLParsingException( "Edge has no match for " + element_name );
			}
		}

		String[] filenames = new String[ frame_filenames.size() ];
		for( int i = 0; i < filenames.length; ++i ) {
			filenames[ i ] = frame_filenames.get( i );
		}

		return new ConceptualEdge( title, start, end, filenames );
	}

	public static ConceptualEdge makeEdge( Node edge_node, HashMap< String, Integer > local_index_for_node_title,
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

		// Elements
		final NodeList elements = edge_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();

			if( element_name.equalsIgnoreCase( "Filename" ) ) {
				appendFilename( element, frame_filenames, filename_prefix );
			} else if( element_name.equalsIgnoreCase( "FilenameRange" ) ) {
				appendFilenameRange( element, frame_filenames, filename_prefix );
			} else if( !element_name.startsWith( "#" ) ) {
				throw new XMLParsingException( "Edge has no match for " + element_name );
			}
		}

		String[] filenames = new String[ frame_filenames.size() ];
		for( int i = 0; i < filenames.length; ++i ) {
			filenames[ i ] = frame_filenames.get( i );
		}

		return new ConceptualEdge( title, origin_node, destination_node, filenames );
	}

	private static void appendFilename( Node filename_element, ArrayList< String > frame_filenames,
			String filename_prefix ) throws XMLParsingException {
		final NamedNodeMap attribute_nodes = filename_element.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final String value = attribute.getNodeValue();

			if( attribute_name.equalsIgnoreCase( "filename" ) ) {
				frame_filenames.add( filename_prefix + value );
				return;
			} else if( !attribute_name.startsWith( "#" ) ) {
				System.err.println( "Filename has no match for " + attribute_name );
				throw new XMLParsingException( "Filename has no match for " + attribute_name );
			}
		}

		throw new XMLParsingException( "Filename element is missing \'filename\' attribute." );
	}

	private static void appendFilenameRange( Node filename_element, ArrayList< String > frame_filenames,
			String filename_prefix ) throws XMLParsingException {

		final NamedNodeMap attribute_nodes = filename_element.getAttributes();
		final int n_attributes = attribute_nodes.getLength();

		int begin = -1;
		int end = -1;
		String base_filename = "";
		String replace_char = "";

		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final String value = attribute.getNodeValue();

			if( attribute_name.equalsIgnoreCase( "first_num" ) ) {
				begin = Integer.parseInt( value );
			} else if( attribute_name.equalsIgnoreCase( "last_num" ) ) {
				end = Integer.parseInt( value );
			} else if( attribute_name.equalsIgnoreCase( "filename" ) ) {
				base_filename = value;
			} else if( attribute_name.equalsIgnoreCase( "replace_char" ) ) {
				replace_char = value;
			} else if( !attribute_name.startsWith( "#" ) ) {
				throw new XMLParsingException( "Edge has no match for " + attribute_name );
			}
		}

		if( begin == -1 ) {
			throw new XMLParsingException( "FilenameRange requires the \'begin\' attribute." );
		} else if( end == -1 ) {
			throw new XMLParsingException( "FilenameRange requires the \'end\' attribute." );
		} else if( base_filename.length() == 0 ) {
			throw new XMLParsingException( "FilenameRange requires the \'base_filename\' attribute." );
		} else if( replace_char.length() == 0 ) {
			throw new XMLParsingException( "FilenameRange requires the \'replace_char\' attribute." );
		} else if( replace_char.length() > 1 ) {
			throw new XMLParsingException(
					"FilenameRange requires the \'replace_char\' attribute to only have 1 character." );
		}

		final String[] temp_split = base_filename.split( replace_char );
		if( temp_split.length < 2 ) {
			throw new XMLParsingException(
					"FilenameRange: " + base_filename + " does not seem to contain the char " + replace_char );
		}
		final String first_part_of_name = filename_prefix + temp_split[ 0 ];
		final String second_part_of_name = temp_split[ temp_split.length - 1 ];

		int num_size = 0;
		for( int i = 0; i < base_filename.length(); ++i ) {
			if( base_filename.charAt( i ) == replace_char.charAt( 0 ) ) {
				++num_size;
			}
		}

		if( begin > end ) {// reverse
			for( int i = begin; i >= end; --i ) {
				final String next_filename = first_part_of_name + String.format( "%0" + num_size + "d", i )
						+ second_part_of_name;
				frame_filenames.add( next_filename );
			}
		} else {
			for( int i = begin; i <= end; ++i ) {
				final String next_filename = first_part_of_name + String.format( "%0" + num_size + "d", i )
						+ second_part_of_name;
				frame_filenames.add( next_filename );
			}
		}

	}

}
