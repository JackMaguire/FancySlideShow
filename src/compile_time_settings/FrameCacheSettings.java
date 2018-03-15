package compile_time_settings;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FrameCacheSettings {

	public final static String XML_Name = "FrameCache";

	// Frame Caching
	public static double PRIMARY_NODE_CACHE_RATIO = 0;// 0 means use following:
	public static int PRI_MAX_WIDTH = 1024;
	public static int PRI_MAX_HEIGHT = 768;

	public static double SECONDARY_NODE_CACHE_RATIO = 0;// 0 means use following:
	public static int SEC_MAX_WIDTH = 1024;
	public static int SEC_MAX_HEIGHT = 768;

	public static boolean DELETE_CACHES = false;
	public static boolean LOAD_CACHES = false;

	public static void parseXMLNode( Node xml_node ) {

		// Attributes
		final NamedNodeMap attribute_nodes = xml_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final boolean value = Boolean.parseBoolean( attribute.getNodeValue() );

			if( attribute_name.equalsIgnoreCase( "load_caches" ) ) {
				LOAD_CACHES = value;
			} else if( attribute_name.equalsIgnoreCase( "delete_caches" ) ) {
				DELETE_CACHES = value;
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

			if( element_name.startsWith( "Primary" ) ) {
				parseElement( element, true );
			} else if( element_name.startsWith( "Secondary" ) ) {
				parseElement( element, false );
			} else if( !element_name.startsWith( "#" ) ) {
				System.err.println( XML_Name + " has no match for " + element_name );
				System.exit( 1 );
			}
		}

	}

	private static void parseElement( Node xml_node, boolean is_primary ) {
		final NamedNodeMap attribute_nodes = xml_node.getAttributes();
		final int n_attributes = attribute_nodes.getLength();
		for( int i = 0; i < n_attributes; ++i ) {
			final Node attribute = attribute_nodes.item( i );
			final String attribute_name = attribute.getNodeName();
			final String value = attribute.getNodeValue();

			if( attribute_name.equalsIgnoreCase( "ratio" ) ) {
				final double ratio = Double.parseDouble( value );
				if( is_primary )
					PRIMARY_NODE_CACHE_RATIO = ratio;
				else
					SECONDARY_NODE_CACHE_RATIO = ratio;
			} else if( attribute_name.equalsIgnoreCase( "max_width" ) ) {
				final int width = Integer.parseInt( value );
				if( is_primary )
					PRI_MAX_WIDTH = width;
				else
					SEC_MAX_WIDTH = width;
			} else if( attribute_name.equalsIgnoreCase( "max_height" ) ) {
				final int height = Integer.parseInt( value );
				if( is_primary )
					PRI_MAX_HEIGHT = height;
				else
					SEC_MAX_HEIGHT = height;
			} else if( !attribute_name.startsWith( "#" ) ) {
				System.err.println( XML_Name + ":" + xml_node.getNodeName() + " has no match for " + attribute_name );
				System.exit( 1 );
			}
		}
	}
}
