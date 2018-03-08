package frame_graph;

import java.awt.image.BufferedImage;

public class FrameNode {

	private boolean stop_;
	private BufferedImage image_;
	
	
	
	public FrameNode() {
		
	}

	public void setImage( BufferedImage image ) {
		image_ = image;
	}
	
	public BufferedImage image() {
		return image_;
	}
	
	public void setStop( boolean setting ) {
		stop_ = setting;
	}
	
	public boolean stop() {
		return stop_;
	}
	
}
