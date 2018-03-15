package compile_time_settings;

import java.awt.Color;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SlideShowPanelSettings {

	public final static String XML_Name = "SlideShowPanel";

	public static int MONITOR = 0;
	// public final static int WIDTH = 1024;
	// public final static int HEIGHT = 1024;

	public static Color BACKGROUND = Color.BLACK;
	// public final static int SCALE_PIC = true;

	public static int FPS = 24;

	public static void parseXMLNode( Node xml_node ) {

		// Attributes
		final NamedNodeMap attribute_nodes = xml_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final int value = Integer.parseInt( attribute.getNodeValue() );
			// System.out.println( attribute.getNodeName() + " " + attribute.getNodeValue()
			// );
			if( attribute_name.equalsIgnoreCase( "fps" ) ) {
				FPS = value;
			} else if( attribute_name.equalsIgnoreCase( "monitor" ) ) {
				MONITOR = value;
			} else if( !attribute_name.startsWith( "#" ) ) {
				System.err.println( XML_Name + " has no match for " + attribute_name );
				System.exit( 1 );
			}
		}

		final NodeList element_nodes = xml_node.getChildNodes();
		final int n_elements = element_nodes.getLength();
		for( int i = 0; i < n_elements; ++i ) {
			final Node element = element_nodes.item( i );
			final String element_name = element.getNodeName();
			if( element_name.equalsIgnoreCase( "Background" ) ) {
				parseColor( element );
			} else if( !element_name.startsWith( "#" ) ) {
				System.err.println( XML_Name + " has no match for " + element_name );
				System.exit( 1 );
			}
		}
	}

	private static void parseColor( Node color_node ) {
		int r = 0;
		int g = 0;
		int b = 0;

		final NamedNodeMap nodes = color_node.getAttributes();
		final int n_elements = nodes.getLength();
		for( int i = 0; i < n_elements; ++i ) {
			final Node element = nodes.item( i );
			// System.out.println( element.getNodeName() + " " + element.getNodeValue() );
			final String name = element.getNodeName();
			final int value = Integer.parseInt( element.getNodeValue() );

			if( name.equalsIgnoreCase( "r" ) || name.equalsIgnoreCase( "red" ) ) {
				r = value;
			} else if( name.equalsIgnoreCase( "g" ) || name.equalsIgnoreCase( "green" ) ) {
				g = value;
			} else if( name.equalsIgnoreCase( "b" ) || name.equalsIgnoreCase( "blue" ) ) {
				b = value;
			}
		}

		BACKGROUND = new Color( r, g, b );
	}
}
