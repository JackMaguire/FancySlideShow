package conceptual_graph;

import frame_graph.FrameGraph;

public interface ConceptualGraphType {

	ConceptualNodeType getNode( int node_index );

	void setNode( ConceptualNodeType node, int node_index, int subgraph );

	ConceptualNodeType[] getNodes();

	ConceptualEdgeType getEdge( int outgoing_node_index, int incoming_node_index );

	void addEdge( ConceptualEdgeType edge );

	ConceptualEdgeType[] getEdges();

	FrameGraph createFrameGraph();

	int numNodes();

	int numSubgraphs();

	int numNodesInSubgraph( int subgraph );

	String getNamesforSubgraph( int subgraph );
}
