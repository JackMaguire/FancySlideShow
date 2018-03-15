package xml_parsing;

import java.util.ArrayList;

import org.w3c.dom.Node;

import conceptual_graph.*;

public class FrameSpaceParser {

	public final static String XML_Name = "FrameSpace"; 
	
	private final ArrayList< ConceptualNode > nodes_ = new ArrayList< ConceptualNode >();
	
	public FrameSpaceParser( Node frame_space_node ) {
		
	}
	
	public void applyToGraph( ConceptualGraph graph, int node_offset ) {
		
	}
	
	public int numNodes() {
		return nodes_.size();
	}
	
}
