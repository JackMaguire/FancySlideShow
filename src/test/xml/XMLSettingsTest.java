package test.xml;

import java.awt.Color;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import settings.ControlPanelMonitorSettings;
import settings.FrameCacheSettings;
import settings.SlideShowPanelSettings;
import test.SingleTest;
import xml_parsing.ParseSettings;
import xml_parsing.XMLParsingException;

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
		try {
			ParseSettings.parseSettingsNode( doc.getDocumentElement() );
		}
		catch( XMLParsingException e ) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
			return false;
		}
		return validate();
	}

	private boolean validate() {
		boolean is_valid = true;

		// SlideShowPanelSettings
		Color bg = SlideShowPanelSettings.BACKGROUND;
		is_valid &= equals_int( "BACKGROUND.getRed()", bg.getRed(), 1 );
		is_valid &= equals_int( "BACKGROUND.getGreen()", bg.getGreen(), 0 );
		is_valid &= equals_int( "BACKGROUND.getBlue()", bg.getBlue(), 255 );

		is_valid &= equals_int( "FPS", SlideShowPanelSettings.FPS, 1 );
		is_valid &= equals_int( "SS MONITOR", SlideShowPanelSettings.MONITOR, 7 );

		// ControlPanelMonitorSettings
		is_valid &= equals_int( "CP MONITOR", ControlPanelMonitorSettings.MONITOR, 10 );
		is_valid &= equals_int( "CP WIDTH", ControlPanelMonitorSettings.CP_WIDTH, 14 );
		is_valid &= equals_int( "CP HEIGHT", ControlPanelMonitorSettings.CP_HEIGHT, 8 );
		is_valid &= equals_int( "CP EAST WIDTH", ControlPanelMonitorSettings.EAST_WIDTH, 2 );
		is_valid &= equals_int( "CP WEST WIDTH", ControlPanelMonitorSettings.WEST_WIDTH, 5 );

		// FrameCacheSettingsTest
		is_valid &= equals_double( "PRIMARY_NODE_CACHE_RATIO", FrameCacheSettings.PRIMARY_NODE_CACHE_RATIO, 0.5 );
		is_valid &= equals_double( "SECONDARY_NODE_CACHE_RATIO", FrameCacheSettings.SECONDARY_NODE_CACHE_RATIO, 0 );
		is_valid &= equals_int( "SEC_MAX_WIDTH", FrameCacheSettings.SEC_MAX_WIDTH, 1 );
		is_valid &= equals_int( "SEC_MAX_HEIGHT", FrameCacheSettings.SEC_MAX_HEIGHT, 2 );
		is_valid &= equals_bool( "LOAD_CACHES", FrameCacheSettings.LOAD_CACHES, false );
		is_valid &= equals_bool( "DELETE_CACHES", FrameCacheSettings.DELETE_CACHES, true );

		return is_valid;
	}

	@Override
	public String name() {
		return "XMLSettingsTest";
	}

}
