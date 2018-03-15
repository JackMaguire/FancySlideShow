package compile_time_settings;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class FrameCacheSettings {

	public final static String XML_Name = "FrameCache";

	// Frame Caching
	public final static double PRIMARY_NODE_CACHE_RATIO = 0;// 0 means use following:
	public final static int PRI_MAX_WIDTH = 1024;
	public final static int PRI_MAX_HEIGHT = 768;

	public final static double SECONDARY_NODE_CACHE_RATIO = 0;// 0 means use following:
	public final static int SEC_MAX_WIDTH = 1024;
	public final static int SEC_MAX_HEIGHT = 768;

	public final static boolean DELETE_CACHES = false;
	public final static boolean LOAD_CACHES = false;

	public static void parseXMLNode( Node xml_node ) {

		// Attributes
		final NamedNodeMap attribute_nodes = xml_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final int value = Integer.parseInt( attribute.getNodeValue() );

			/*if( attribute_name.equalsIgnoreCase( "monitor" ) ) {
				MONITOR = value;
			} else if( attribute_name.equalsIgnoreCase( "width" ) ) {
				CP_WIDTH = value;
			} else if( attribute_name.equalsIgnoreCase( "height" ) ) {
				CP_HEIGHT = value;
			} else if( attribute_name.equalsIgnoreCase( "east_width" ) ) {
				EAST_WIDTH = value;
			} else if( attribute_name.equalsIgnoreCase( "west_width" ) ) {
				WEST_WIDTH = value;
			}*/
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
