package graph;

public interface GraphType {

	NodeType getNode( int node_index );
	void addNode( NodeType node, int node_index );
	
	EdgeType getEdge( int outgoing_node_index, int incoming_node_index );
	void addEdge( EdgeType edge );
}
