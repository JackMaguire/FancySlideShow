package graph;

public class Edge implements EdgeType {

	final int outgoing_node_index_;
	final int incoming_node_index_;
	
	public Edge( int outgoing_node_index, int incoming_node_index ) {
		outgoing_node_index_ = outgoing_node_index;
		incoming_node_index_ = incoming_node_index;
	}
	
	@Override
	public boolean hasImages() {
		return false;
	}
	
	@Override
	public int outgoingNodeIndex() {
		return outgoing_node_index_;
	}
	
	@Override
	public int incomingNodeIndex() {
		return incoming_node_index_;
	}

	@Override
	public int compareTo(EdgeType other) {
		//first compare by outgoing node, then incoming node.
		final int first_comparison =  outgoing_node_index_ - other.outgoingNodeIndex();
		if( first_comparison == 0 ) {
			return incoming_node_index_ - other.incomingNodeIndex();
		} else {
			return first_comparison;
		}
	}
	
}
