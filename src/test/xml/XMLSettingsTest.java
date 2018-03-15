package test.xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

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
		return true;
	}

	private void p( String s ) {
		System.out.println( "test.XMLTest: " + s );
	}

	@Override
	public String name() {
		return "XMLTest";
	}

}
