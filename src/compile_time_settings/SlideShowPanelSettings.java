package compile_time_settings;

import java.awt.Color;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SlideShowPanelSettings {

	public final static String XML_Name = "SlideShowPanel";

	public static int MONITOR = 0;
	// public final static int WIDTH = 1024;
	// public final static int HEIGHT = 1024;

	public static Color BACKGROUND = Color.BLACK;
	// public final static int SCALE_PIC = true;

	public static int FPS = 24;
	
	public static void parseXMLNode( Node xml_node ) {
		
		//Attributes
		final NamedNodeMap nodes = xml_node.getAttributes();

		final int n_elements = nodes.getLength();
		for( int i = 0; i < n_elements; ++i ) {
			final Node element = nodes.item( i );
			System.out.println( element.getNodeName() + " " + element.getNodeValue() );
			if( element.getNodeName().equalsIgnoreCase( "fps" ) ) {
				FPS = Integer.parseInt( element.getNodeValue() );
			} else if( element.getNodeName().equalsIgnoreCase( "monitor" ) ) {
				MONITOR = Integer.parseInt( element.getNodeValue() );
			}
		}
	}
	
	private static void parseColor( Node color_node ) {
		// TODO pick up from here please!
	}
}
