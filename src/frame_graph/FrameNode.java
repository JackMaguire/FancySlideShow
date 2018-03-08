package frame_graph;

import java.awt.image.BufferedImage;

public class FrameNode {

	private boolean stop_;
	private BufferedImage image_;

	private FrameNode forward_node_ = null;
	private FrameNode[] possible_forward_nodes_ = new FrameNode[0];

	private FrameNode reverse_node_;
	private FrameNode[] possible_reverse_nodes_ = new FrameNode[0];

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

	public FrameNode forwardNode() {
		return forward_node_;
	}

	public FrameNode reverseNode() {
		return reverse_node_;
	}

	public FrameNode getNextNode( boolean reverse ) {
		if( reverse )
			return reverse_node_;
		else
			return forward_node_;
	}

	public void addForwardNode( FrameNode other ) {
		possible_forward_nodes_ = addFrameToArray( possible_forward_nodes_, other );
		if( forward_node_ == null ) {
			forward_node_ = other;
		}
	}

	public void addReverseNode( FrameNode other ) {
		possible_reverse_nodes_ = addFrameToArray( possible_reverse_nodes_, other );
		if( reverse_node_ == null ) {
			reverse_node_ = other;
		}
	}

	public static FrameNode[] addFrameToArray( FrameNode[] in, FrameNode new_element ) {
		FrameNode[] new_array = new FrameNode[in.length + 1];
		for( int i = 0; i < in.length; ++i ) {
			new_array[ i ] = in[ i ];
		}
		new_array[ in.length ] = new_element;
		return new_array;
	}
}
