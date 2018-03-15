package test.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import applications.FrameScript;
import conceptual_graph.ConceptualGraph;
import test.SingleTest;
import xml_parsing.GraphFromXML;
import xml_parsing.ParseSettings;
import xml_parsing.Util;

public class DemoFrameGraphTest extends SingleTest {

	@Override
	public boolean run() {

		try {
			Node frame_script_node = Util.readFromFile( "src/demo/script.xml" );
			if( !diff( "top level name", frame_script_node.getNodeName(), FrameScript.top_level_element_name ) ) {
				return false;
			}

			Node graph_node = null;
			final NodeList elements = frame_script_node.getChildNodes();
			final int n = elements.getLength();
			for( int i = 0; i < n; ++i ) {
				final Node element = elements.item( i );
				if( element.getNodeName().equalsIgnoreCase( GraphFromXML.XML_Name ) ) {
					graph_node = element;
					break;
				}
			}

			if( graph_node == null ) {
				err( "Could node find <Graph> section" );
				return false;
			}

			ConceptualGraph graph = GraphFromXML.parse( graph_node );
			if( graph == null ) {
				System.err.println( "graph == null" );
				return false;
			}
			return validateGraph( graph );
		}
		catch( Exception e ) {
			System.err.println( e.getMessage() );
			e.printStackTrace();
			return false;
		}

	}

	private boolean validateGraph( ConceptualGraph graph ) {
		return true;
	}

	@Override
	public String name() {
		return "DemoFrameGraphTest";
	}

}
