package applications;

import java.io.File;

import org.w3c.dom.NodeList;

import conceptual_graph.ConceptualGraph;
import slide_show.SlideShow;
import xml_parsing.GraphFromXML;
import xml_parsing.ParseSettings;
import xml_parsing.TopLevelElementNames;
import xml_parsing.Util;
import xml_parsing.XMLParsingException;

import org.w3c.dom.Node;

public class FrameScript {

	/*
	 https://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
	 */

	// Static
	public final static String top_level_element_name = "FrameScript";

	// Instance
	private Node graph_node_;

	public static void main( String[] args ) throws Exception {
		new FrameScript( args );
	}

	public FrameScript( String[] args ) throws Exception {
		parseArgs( args );
		if( graph_node_ == null ) {
			System.err.println( "No <Graph> element provided" );
		}

		ConceptualGraph graph = GraphFromXML.parse( graph_node_ );
		SlideShow ss = new SlideShow( graph );
		ss.run();
	}

	public void parseArgs( String[] args ) throws Exception {
		for( int i = 0; i < args.length; ++i ) {
			final String script_name = args[ i ];
			parse( script_name );
		}
	}

	public void parse( String script_name ) throws Exception {
		System.out.println( "Parsing " + script_name );
		final Node node = Util.readFromFile( script_name );
		if( node == null ) {
			File f = new File( script_name );
			if( !f.exists() ) {
				throw new Exception( "No file named" + script_name );
			} else {
				throw new Exception( script_name + " exists but we can not parse it" );
			}

		}
		parse( node );
	}

	public void parse( Node node ) throws XMLParsingException {
		final String name = node.getNodeName();
		if( name.equals( TopLevelElementNames.TOP_LEVEL_FRAME_SCRIPT ) ) {
			final NodeList elements = node.getChildNodes();
			final int num_elements = elements.getLength();
			for( int i = 0; i < num_elements; ++i ) {
				final Node element = elements.item( i );
				parse( element );
			}
		} else if( name.equals( TopLevelElementNames.TOP_LEVEL_SETTINGS ) ) {
			ParseSettings.parseSettingsNode( node );
		} else if( name.equals( TopLevelElementNames.TOP_LEVEL_GRAPH ) ) {
			graph_node_ = node;
		}
	}

}
