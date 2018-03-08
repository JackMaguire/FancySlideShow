package graph;

import java.awt.image.BufferedImage;

import frame_graph.FrameGraph;

public interface NodeType {

	String name();

	int index();

	void setIndex( int index );

	// Graph Logic
	EdgeType[] getUpstreamEdges();

	EdgeType[] getDownstreamEdges();

	void addUpstreamEdge( EdgeType E );

	void addDownstreamEdge( EdgeType E );

	void toggle_hardness();

	boolean is_hard();

	void applyToFrameGraph( FrameGraph fg );

	// Image Logic
	BufferedImage getThumbnailImage();
	// void loadImagesIntoMemory();
	// void eraseImagesFromMemory();
	// int numBytesRequiredForImages();//You can assume/enforce that the images are
	// loaded into memory when this is called.
}
