package applications;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

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

	public void parseAll() throws ParserConfigurationException, SAXException, IOException {
		final File inputFile = new File( full_script_filename_ );
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse( inputFile );
		doc.getDocumentElement().normalize();

		if( !doc.getDocumentElement().getNodeName().equals( top_level_element_name ) ) {
			System.out.println( "Top level element name should be " + top_level_element_name + ", not "
					+ doc.getDocumentElement().getNodeName() );
			System.exit( 1 );
		}

		final NodeList sections = doc.getChildNodes();
		for( int i = 0; i < sections.getLength(); ++i ) {
			final Node section = sections.item( i );
			if( section.getNodeName().equalsIgnoreCase( "FrameSpace" ) ) {
				
			}
		}
	}
	
	private void parseFrameSpaceNode( Node frame_space_node ) {
		
	}

}
