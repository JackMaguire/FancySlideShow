package graph;

public class Graph implements GraphType {

	NodeType[] nodes_;
	EdgeType[] edges_ = new EdgeType[0];

	public Graph( int num_nodes ) {
		nodes_ = new NodeType[num_nodes];
	}

	@Override
	public void addNode( NodeType node, int node_index ) {
		nodes_[ node_index ] = node;
	}

	@Override
	public NodeType getNode( int node_index ) {
		return nodes_[ node_index ];
	}

	@Override
	public void addEdge( EdgeType edge ) {
		nodes_[ edge.incomingNodeIndex() ].addUpstreamEdge( edge );
		nodes_[ edge.outgoingNodeIndex() ].addDownstreamEdge( edge );
		registerEdge( edge );
	}

	@Override
	public EdgeType getEdge( int outgoing_node_index, int incoming_node_index ) {
		EdgeType[] edges = nodes_[ outgoing_node_index ].getDownstreamEdges();
		for( EdgeType edge : edges ) {
			if( edge.incomingNodeIndex() == incoming_node_index ) {
				return edge;
			}
		}
		return null;
	}

	public NodeType[] getNodes() {
		return nodes_;
	}

	@Override
	public EdgeType[] getEdges() {
		return edges_;
	}

	private void registerEdge( EdgeType E ) {
		EdgeType[] edges_new = new EdgeType[edges_.length + 1];
		for( int i = 0; i < edges_.length; ++i ) {
			edges_new[ i ] = edges_[ i ];
		}
		edges_new[ edges_.length ] = E;
		E.setIndex( edges_.length );
		edges_ = edges_new;
	}
}
