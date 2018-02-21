package graph;

public class Graph implements GraphType {

	NodeType[] nodes_;
	
	public Graph( int num_nodes ) {
		nodes_ = new NodeType[ num_nodes ];
	}
	
	public void addNode( NodeType node, int node_index ) {
		nodes_[ node_index ] = node;
	}
	
	public NodeType getNode( int node_index ) {
		return nodes_[ node_index ];
	}
	
	public void addEdge( EdgeType edge ) {
		nodes_[ edge.incomingNodeIndex() ].addUpstreamEdge( edge );
		nodes_[ edge.outgoingNodeIndex() ].addDownstreamEdge( edge );
	}
	
	public EdgeType getEdge( int outgoing_node_index, int incoming_node_index ) {
		EdgeType[] edges = nodes_[ outgoing_node_index ].getDownstreamEdges();
		for( EdgeType edge : edges ) {
			if( edge.incomingNodeIndex() == incoming_node_index ) {
				return edge;
			}
		}
		return null;
	}
	
}
