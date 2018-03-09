package frame_graph;

public class FrameGraph {

	private FrameNode[] primary_nodes_;

	public FrameGraph( int num_primary_nodes ) {
		primary_nodes_ = new FrameNode[ num_primary_nodes ];
		for( int i = 0; i < primary_nodes_.length; ++i ) {
			primary_nodes_[ i ] = new FrameNode( true, i, i );
		}
	}

	public FrameNode getPrimaryNode( int id ) {
		return primary_nodes_[ id ];
	}

}
