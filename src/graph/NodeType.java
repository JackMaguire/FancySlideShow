package graph;

import java.awt.image.BufferedImage;

public interface NodeType {

	EdgeType[] get_upstream_edges();
	EdgeType[] get_downstream_edges();
	
	BufferedImage get_next_image();
	
}
