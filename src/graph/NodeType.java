package graph;

import java.awt.image.BufferedImage;

public interface NodeType {

	EdgeType[] getUpstreamEdges();
	EdgeType[] getDownstreamEdges();
	
	BufferedImage getNextImage();
	
}
