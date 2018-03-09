package graph;

import frame_graph.FrameGraph;

public interface GraphType {

	NodeType getNode( int node_index );

	void setNode( NodeType node, int node_index );

	NodeType[] getNodes();

	EdgeType getEdge( int outgoing_node_index, int incoming_node_index );

	void addEdge( EdgeType edge );

	EdgeType[] getEdges();

	FrameGraph createFrameGraph();
	
	int numNodes();
}
