package conceptual_graph;

import java.awt.image.BufferedImage;

import frame_graph.FrameGraph;
import frame_graph.FrameNode;

public interface ConceptualNodeType {

	String name();

	String getNotes();

	int index();

	int subgraph();

	void setIndex( int index, int subgraph );

	// Graph Logic
	ConceptualEdgeType[] getUpstreamEdges();

	ConceptualEdgeType[] getDownstreamEdges();

	void addUpstreamEdge( ConceptualEdgeType E );

	void addDownstreamEdge( ConceptualEdgeType E );

	void toggle_hardness();

	boolean is_hard();

	void applyToFrameGraph( FrameGraph fg );

	FrameNode getFrameNode();

	// Image Logic
	BufferedImage getThumbnailImage();
	// void loadImagesIntoMemory();
	// void eraseImagesFromMemory();
	// int numBytesRequiredForImages();//You can assume/enforce that the images are
	// loaded into memory when this is called.
}
