package applications;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import xml_parsing.ParseSettings;
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
	private String full_script_filename_ = "";
	private String graph_script_filename_ = "";
	private String settings_script_filename_ = "";

	public static void main( String[] args ) {
		new FrameScript( args );
	}

	public FrameScript( String[] args ) {
		parseArgs( args );
	}

	public void parseArgs( String[] args ) {
		for( int i = 0; i < args.length; ++i ) {
			// options with 0 args

			// options with 1 arg
			if( i == args.length - 1 )
				break;

			if( args[ i ].replaceAll( "-", "" ).equalsIgnoreCase( "script" ) ) {
				full_script_filename_ = args[ i + 1 ];
			}
		}
	}

	public void createSchema() {

	}

	public void parseAll() throws ParserConfigurationException, SAXException, IOException, XMLParsingException {
		final Node frame_script_node = Util.readFromFile( full_script_filename_ );
		
		if( !frame_script_node.getNodeName().equals( top_level_element_name ) ) {
			throw new XMLParsingException(
					"Top level element name should be " + top_level_element_name + ", not " + frame_script_node.getNodeName() );
		}

		final NodeList elements = frame_script_node.getChildNodes();
		for( int i = 0; i < elements.getLength(); ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();

			if( element_name.equalsIgnoreCase( ParseSettings.TOP_LEVEL_NAME ) ) {
				ParseSettings.parseSettingsNode( element );
			} else if( !element_name.startsWith( "#" ) ) {
				System.err.println( "No top-level match for " + element_name );
				System.exit( 1 );
			}
		}
	}

	private void parseFrameSpaceNode( Node frame_space_node ) {

	}

}
