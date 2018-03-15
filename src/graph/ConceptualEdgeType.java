package graph;

import frame_graph.FrameGraph;

//Comparable methods will only be used for sorting!
public interface ConceptualEdgeType {

	String name();

	int outgoingNodeIndex();

	int incomingNodeIndex();

	boolean hasImages();

	int index();

	void setIndex( int index );

	void applyToFrameGraph( FrameGraph fg );

}
