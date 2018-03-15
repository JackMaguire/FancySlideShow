package test.xml;

import java.awt.Color;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import compile_time_settings.ControlPanelMonitorSettings;
import compile_time_settings.SlideShowPanelSettings;
import test.SingleTest;
import xml_parsing.ParseSettings;

public class XMLSettingsTest extends SingleTest {

	private final static String path_to_test_dir = "src/test/xml/";

	@Override
	public boolean run() {
		final File inputFile = new File( path_to_test_dir + "XMLSettingsTest.xml" );
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		Document doc = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse( inputFile );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		doc.getDocumentElement().normalize();

		if( !doc.getDocumentElement().getNodeName().equals( ParseSettings.TOP_LEVEL_NAME ) ) {
			err( "Top level element name should be " + ParseSettings.TOP_LEVEL_NAME + ", not "
					+ doc.getDocumentElement().getNodeName() );
			return false;
		}
		ParseSettings.parseSettingsNode( doc.getDocumentElement() );
		return validate();
	}

	private boolean validate() {
		boolean is_valid = true;

		//SlideShowPanelSettings
		Color bg = SlideShowPanelSettings.BACKGROUND;
		is_valid |= diff( "BACKGROUND.getRed()", bg.getRed(), 1 );
		is_valid |= diff( "BACKGROUND.getGreen()", bg.getGreen(), 0 );
		is_valid |= diff( "BACKGROUND.getBlue()", bg.getBlue(), 255 );
		
		is_valid |= diff( "FPS", SlideShowPanelSettings.FPS, 1 );
		is_valid |= diff( "SS MONITOR", SlideShowPanelSettings.MONITOR, 7 );
		
		//ControlPanelMonitorSettings
		is_valid |= diff( "CP MONITOR", ControlPanelMonitorSettings.MONITOR, 10);
		is_valid |= diff( "CP WIDTH", ControlPanelMonitorSettings.CP_WIDTH, 14);
		is_valid |= diff( "CP HEIGHT", ControlPanelMonitorSettings.CP_HEIGHT, 8);
		is_valid |= diff( "CP EAST WIDTH", ControlPanelMonitorSettings.EAST_WIDTH, 2);
		is_valid |= diff( "CP WEST WIDTH", ControlPanelMonitorSettings.WEST_WIDTH, 5);
		
		
		return is_valid;
	}

	@Override
	public String name() {
		return "XMLSettingsTest";
	}

}
