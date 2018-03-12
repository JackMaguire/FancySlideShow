package frame_graph;

import java.awt.image.BufferedImage;

import compile_time_settings.DebugToggles;
import compile_time_settings.PerformanceSettings;
import frame_cache.FrameCacher;
import graph.Node;
import graph.NodeType;

public class FrameNode {

	private boolean stop_;
	private String image_filename_;

	private FrameNode forward_node_ = null;
	private FrameNode[] possible_forward_nodes_ = new FrameNode[ 0 ];

	private FrameNode reverse_node_ = null;
	private FrameNode[] possible_reverse_nodes_ = new FrameNode[ 0 ];

	public final boolean IS_PRIMARY;
	public final int UPSTREAM_PRIMARY_ID;
	public final int DOWNSTREAM_PRIMARY_ID;// ==UPSTREAM_PRIMARY_ID if primary

	private final NodeType corresponding_node_;

	public FrameNode( boolean is_primary, int upstream_id, int downstream_id, NodeType corresponding_node ) {
		IS_PRIMARY = is_primary;
		UPSTREAM_PRIMARY_ID = upstream_id;
		DOWNSTREAM_PRIMARY_ID = downstream_id;
		corresponding_node_ = corresponding_node;
	}

	public int numForwardOptions() {
		return possible_forward_nodes_.length;
	}

	public int numReverseOptions() {
		return possible_reverse_nodes_.length;
	}

	public NodeType node() {
		return corresponding_node_;
	}

	public void setImageFilename( String filename ) {
		image_filename_ = filename;
		if( PerformanceSettings.SECONDARY_NODE_CACHE_RATIO != 1.0 ) {
			image_filename_ = FrameCacher.getInstance().createSmallerVersionPlease( image(),
					PerformanceSettings.SECONDARY_NODE_CACHE_RATIO );
		}
	}

	public BufferedImage image() {
		return util.ImageFromFile.imageFromFile( image_filename_ );
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

	public FrameNode getSecondaryForwardNode() {
		if( numForwardOptions() == 1 )
			return forward_node_;
		else {
			for( FrameNode fnode : possible_forward_nodes_ ) {
				if( fnode != forward_node_ ) {
					return fnode;
				}
			}
		}
		return null;
	}

	public FrameNode reverseNode() {
		return reverse_node_;
	}

	public FrameNode getSecondaryReverseNode() {
		if( numReverseOptions() == 1 )
			return reverse_node_;
		else {
			for( FrameNode rnode : possible_reverse_nodes_ ) {
				if( rnode != reverse_node_ ) {
					return rnode;
				}
			}
		}
		return null;
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
		FrameNode[] new_array = new FrameNode[ in.length + 1 ];
		for( int i = 0; i < in.length; ++i ) {
			new_array[ i ] = in[ i ];
		}
		new_array[ in.length ] = new_element;
		return new_array;
	}

	public void setForwardNode( int primary_node_index ) {

		if( DebugToggles.DEBUG_FRAME_GRAPH ) {
			assert ( IS_PRIMARY );
		}

		for( FrameNode node : possible_forward_nodes_ ) {
			if( node.UPSTREAM_PRIMARY_ID == primary_node_index ) {
				forward_node_ = node;
				return;
			}
		}

		System.err.println( "Node " + UPSTREAM_PRIMARY_ID + " has no forward connection to node " + primary_node_index );
		Exception e = new Exception( "" );
		e.printStackTrace();
	}

	public void setReverseNode( int primary_node_index ) {

		if( DebugToggles.DEBUG_FRAME_GRAPH ) {
			assert ( IS_PRIMARY );
		}

		for( FrameNode node : possible_reverse_nodes_ ) {
			if( node.DOWNSTREAM_PRIMARY_ID == primary_node_index ) {
				reverse_node_ = node;
				return;
			}
		}

		System.err.println( "Node " + UPSTREAM_PRIMARY_ID + " has no reverse connection to node " + primary_node_index );
		Exception e = new Exception( "" );
		e.printStackTrace();
	}

	public int[] upstreamIDs() {
		int[] allIDs = new int[ possible_forward_nodes_.length ];
		for( int i = 0; i < possible_forward_nodes_.length; ++i ) {
			allIDs[ i ] = possible_forward_nodes_[ i ].UPSTREAM_PRIMARY_ID;
		}
		return allIDs;
	}

	public int[] downstreamIDs() {
		int[] allIDs = new int[ possible_reverse_nodes_.length ];
		for( int i = 0; i < possible_reverse_nodes_.length; ++i ) {
			allIDs[ i ] = possible_reverse_nodes_[ i ].DOWNSTREAM_PRIMARY_ID;
		}
		return allIDs;
	}
}
