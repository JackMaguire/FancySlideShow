package compile_time_settings;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class ControlPanelMonitorSettings {

	public final static String XML_Name = "ControlPanel";

	public static int MONITOR = 0;

	// iMac:
	// <ControlPanel monitor="0" width="1920" height="1060" east_width="400"
	// west_width="400"/>

	// Macbook Air:
	// <ControlPanel monitor="1" width="1440" height="880" east_width="250"
	// west_width="250"/>

	// iMac, 1920 x 1080
	// public final static int CP_WIDTH = 1920; public final static int CP_HEIGHT =
	// 1060; public final static int EAST_WIDTH = 400; public final static int
	// WEST_WIDTH = 400;

	// Macbook Air
	public static int CP_WIDTH = 1440;
	public static int CP_HEIGHT = 880;
	public static int EAST_WIDTH = 250;
	public static int WEST_WIDTH = 250;

	public static void parseXMLNode( Node xml_node ) {

		// Attributes
		final NamedNodeMap attribute_nodes = xml_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final int value = Integer.parseInt( attribute.getNodeValue() );

			if( attribute_name.equalsIgnoreCase( "monitor" ) ) {
				MONITOR = value;
			} else if( attribute_name.equalsIgnoreCase( "width" ) ) {
				CP_WIDTH = value;
			} else if( attribute_name.equalsIgnoreCase( "height" ) ) {
				CP_HEIGHT = value;
			} else if( attribute_name.equalsIgnoreCase( "east_width" ) ) {
				EAST_WIDTH = value;
			} else if( attribute_name.equalsIgnoreCase( "west_width" ) ) {
				WEST_WIDTH = value;
			}
		}

		/*
		final NodeList element_nodes = xml_node.getChildNodes();
		final int n_elements = element_nodes.getLength();
		for( int i = 0; i < n_elements; ++i ) {
			final Node element = element_nodes.item( i );
			final String element_name = element.getNodeName();
		
		}
		*/
	}

}
