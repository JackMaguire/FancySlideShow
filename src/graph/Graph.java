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
	
}
