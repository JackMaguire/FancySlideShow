package graph;

import java.util.ArrayList;

import frame_graph.FrameGraph;

public interface ConceptualGraphType {

	NodeType getNode( int node_index );

	void setNode( NodeType node, int node_index, int subgraph );

	NodeType[] getNodes();

	EdgeType getEdge( int outgoing_node_index, int incoming_node_index );

	void addEdge( EdgeType edge );

	EdgeType[] getEdges();

	FrameGraph createFrameGraph();

	int numNodes();

	int numSubgraphs();

	int numNodesInSubgraph( int subgraph );

	String getNamesforSubgraph( int subgraph );
}