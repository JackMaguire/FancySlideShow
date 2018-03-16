package xml_parsing;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Util {

	public static Node readFromFile( String filename ) throws IOException, ParserConfigurationException, SAXException {
		final File inputFile = new File( filename );
		final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		final Document doc = dBuilder.parse( inputFile );
		doc.getDocumentElement().normalize();

		final NodeList elements = doc.getChildNodes();
		for( int i = 0; i < elements.getLength(); ++i ) {
			final Node element = elements.item( i );
			final String element_name = element.getNodeName();

			if( !element_name.startsWith( "#" ) ) {
				return element;
			}

		}
		return null;
	}

}
