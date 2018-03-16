package conceptual_graph;

import frame_graph.FrameGraph;

public interface ConceptualEdgeType {

	String name();

	int outgoingNodeIndex();

	int incomingNodeIndex();

	boolean hasImages();

	int index();

	void setIndex( int index );

	void applyToFrameGraph( FrameGraph fg );

	String[] getImageFilenames();

}
