package test.xml;

import java.awt.Color;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import compile_time_settings.SlideShowPanelSettings;
import test.SingleTest;
import xml_parsing.ParseSettings;

public class XMLSettingsTest implements SingleTest {

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
			p( "Top level element name should be " + ParseSettings.TOP_LEVEL_NAME + ", not "
					+ doc.getDocumentElement().getNodeName() );
			return false;
		}
		ParseSettings.parseSettingsNode( doc.getDocumentElement() );
		return validate();
	}

	private boolean validate() {
		boolean is_valid = true;

		Color bg = SlideShowPanelSettings.BACKGROUND;
		is_valid |= diff( "BACKGROUND.getRed()", bg.getRed(), 1 );
		is_valid |= diff( "BACKGROUND.getGreen()", bg.getGreen(), 0 );
		is_valid |= diff( "BACKGROUND.getBlue()", bg.getBlue(), 255 );

		return is_valid;
	}

	private void p( String s ) {
		System.err.println( "test.XMLTest: " + s );
	}

	private boolean diff( String name, int value, int intended_value ) {
		if( value != intended_value ) {
			p( name + " is " + value + " instead of " + intended_value );
			return false;
		}
		return true;
	}

	@Override
	public String name() {
		return "XMLTest";
	}

}
