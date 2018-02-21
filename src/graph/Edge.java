package graph;

public class Edge implements EdgeType {

	final int outgoing_node_index_;
	final int incoming_node_index_;
	
	public Edge( int outgoing_node_index, int incoming_node_index ) {
		outgoing_node_index_ = outgoing_node_index;
		incoming_node_index_ = incoming_node_index;
	}
	
	public boolean hasImages() {
		return false;
	}
	
	public int outgoingNodeIndex() {
		return outgoing_node_index_;
	}
	
	public int incomingNodeIndex() {
		return incoming_node_index_;
	}
	
}
