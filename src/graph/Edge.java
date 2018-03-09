package graph;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import compile_time_settings.DebugToggles;
import frame_graph.FrameGraph;
import frame_graph.FrameNode;

public class Edge implements EdgeType {

	final String name_;
	final int outgoing_node_index_;
	final int incoming_node_index_;
	int index_ = 0;

	private String[] image_filenames_;

	public Edge( String name, int outgoing_node_index, int incoming_node_index ) {
		name_ = name;
		outgoing_node_index_ = outgoing_node_index;
		incoming_node_index_ = incoming_node_index;
	}

	public Edge( String name, int outgoing_node_index, int incoming_node_index, String[] image_filenames ) {
		name_ = name;
		outgoing_node_index_ = outgoing_node_index;
		incoming_node_index_ = incoming_node_index;
		image_filenames_ = image_filenames;
	}

	public void setImageFilenames( String[] image_filenames ) {
		image_filenames_ = image_filenames;
	}

	@Override
	public String name() {
		return name_;
	}

	@Override
	public boolean hasImages() {
		return false;
	}

	@Override
	public int outgoingNodeIndex() {
		return outgoing_node_index_;
	}

	@Override
	public int incomingNodeIndex() {
		return incoming_node_index_;
	}

	@Override
	public int compareTo( EdgeType other ) {
		// first compare by outgoing node, then incoming node.
		final int first_comparison = outgoing_node_index_ - other.outgoingNodeIndex();
		if( first_comparison == 0 ) {
			return incoming_node_index_ - other.incomingNodeIndex();
		} else {
			return first_comparison;
		}
	}

	@Override
	public int index() {
		return index_;
	}

	@Override
	public void setIndex( int index ) {
		index_ = index;
	}

	@Override
	public void applyToFrameGraph( FrameGraph fg ) {
		FrameNode begin_primary = fg.getPrimaryNode( outgoing_node_index_ );
		FrameNode final_primary = fg.getPrimaryNode( incoming_node_index_ );

		if( image_filenames_.length == 0 ) {
			begin_primary.addForwardNode( final_primary );
			final_primary.addReverseNode( begin_primary );
			return;
		}

		FrameNode[] my_edge_nodes = new FrameNode[ image_filenames_.length ];
		for( int i = 0; i < my_edge_nodes.length; ++i ) {
			my_edge_nodes[ i ] = new FrameNode( false, incoming_node_index_, outgoing_node_index_ );
			my_edge_nodes[ i ].setImageFilename( image_filenames_[ i ] );
		}

		begin_primary.addForwardNode( my_edge_nodes[ 0 ] );
		my_edge_nodes[ 0 ].addReverseNode( begin_primary );

		for( int i = 0; i < my_edge_nodes.length; ++i ) {
			if( i != 0 ) {
				my_edge_nodes[ i ].addReverseNode( my_edge_nodes[ i - 1 ] );
			}
			if( i != image_filenames_.length - 1 ) {
				my_edge_nodes[ i ].addForwardNode( my_edge_nodes[ i + 1 ] );
			}
		}

		my_edge_nodes[ image_filenames_.length - 1 ].addForwardNode( final_primary );
		final_primary.addReverseNode( my_edge_nodes[ image_filenames_.length - 1 ] );

	}

}
