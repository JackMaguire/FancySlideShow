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

		//final int num_frame_spaces_ = countFrameSpaces( graph_node );
		final ArrayList< FrameSpaceParser > frame_spaces = extractFrameSpaces( graph_node );
		if( frame_spaces.size() == 0 ) {
			throw new XMLParsingException( "No Frame Spaces Declared" );
		}
		
		int total_num_nodes = 0;
		int[] offset_for_node = new int[ frame_spaces.size() ];
		offset_for_node[ 0 ] = 0;
		
		return null;
	}

	private final static ArrayList< FrameSpaceParser > extractFrameSpaces( Node graph_node ){
		ArrayList< FrameSpaceParser > list = new ArrayList< FrameSpaceParser >();
		
		final NodeList elements = graph_node.getChildNodes();
		final int n = elements.getLength();
		for( int i = 0; i < n; ++i ) {
			final Node element = elements.item( i );
			if( element.getNodeName().equalsIgnoreCase( FrameSpaceParser.XML_Name ) ) {
				list.add( new FrameSpaceParser( element ) );
			}
		}
		
		return list;
	}
	
	private final static int countFrameSpaces( Node graph_node ) {
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
	}

}
