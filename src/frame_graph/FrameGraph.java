package frame_graph;

import graph.ConceptualNodeType;

public class FrameGraph {

	private FrameNode[] primary_nodes_;

	public FrameGraph( ConceptualNodeType[] nodes ) {
		primary_nodes_ = new FrameNode[ nodes.length ];
		for( int i = 0; i < primary_nodes_.length; ++i ) {
			primary_nodes_[ i ] = new FrameNode( true, i, i, nodes[ i ] );
		}
	}

	public FrameNode getPrimaryNode( int id ) {
		return primary_nodes_[ id ];
	}

}
