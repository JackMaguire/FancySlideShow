package xml_parsing;

import org.w3c.dom.Node;

import compile_time_settings.ControlPanelMonitorSettings;
import compile_time_settings.SlideShowPanelSettings;

public class ParseSettings {

	public final static String TOP_LEVEL_NAME = "Settings";

	public static void parseSettingsNode( Node settings_node ) {
		Node ptr = settings_node.getFirstChild();
		Node last = settings_node.getLastChild();

		while ( ptr != last ) {
			parseNode( ptr );
			ptr = ptr.getNextSibling();
		}
		parseNode( last );
	}

	private static void parseNode( Node individual_node ) {
		// System.out.println( individual_node.getNodeName() );
		final String name = individual_node.getNodeName();
		if( name.equalsIgnoreCase( SlideShowPanelSettings.XML_Name ) ) {
			SlideShowPanelSettings.parseXMLNode( individual_node );
		} else if( name.equalsIgnoreCase( ControlPanelMonitorSettings.XML_Name ) ) {
			ControlPanelMonitorSettings.parseXMLNode( individual_node );
		}
	}

}
