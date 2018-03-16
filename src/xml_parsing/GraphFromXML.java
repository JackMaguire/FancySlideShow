package xml_parsing;

import java.util.ArrayList;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import conceptual_graph.*;

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

		ConceptualGraph graph = new ConceptualGraph( total_num_nodes, num_frame_spaces );
		for( int i = 0; i < num_frame_spaces; ++i )
			frame_spaces.get( i ).applyToGraph( graph, offset_for_frame_space[ i ] );

		// TODO InterFrameSpace Edges

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
			if( element_name.startsWith( "#" ) )
				continue;

			// System.out.println( element.getAttributes().toString() );
			if( element_name.equalsIgnoreCase( FrameSpaceParser.XML_Name ) ) {
				list.add( new FrameSpaceParser( element, frame_space_num++ ) );
			}
		}

		return list;
	}

	/*private final static int countFrameSpaces( Node graph_node ) {
		int count = 0;
	
		final NodeList elements = graph_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			if( element.getNodeName().equalsIgnoreCase( FrameSpaceParser.XML_Name ) ) {
				++count;
			}
		}
	
		return count;
	}*/

}
