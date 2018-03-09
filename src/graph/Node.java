package graph;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
//import java.util.Arrays;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import compile_time_settings.ControlPanelMonitorSettings;
import compile_time_settings.DebugToggles;
import frame_graph.FrameGraph;
import frame_graph.FrameNode;

public class Node implements NodeType {

	final private String name_;
	private int index_ = 0;

	private boolean hard_;

	private EdgeType[] upstream_edges_ = new EdgeType[ 0 ];
	private EdgeType[] downstream_edges_ = new EdgeType[ 0 ];

	private String image_filename_;
	private BufferedImage thumbnail_image_;

	private FrameNode corresponding_fnode_ = null;

	public Node( String name ) {
		name_ = name;
		hard_ = true;
	}

	public Node( String name, boolean is_hard ) {
		name_ = name;
		hard_ = is_hard;
	}

	public Node( String name, boolean is_hard, String image_filename ) {
		name_ = name;
		hard_ = is_hard;
		setImageFilename( image_filename );
	}

	public FrameNode getFrameNode() {
		return corresponding_fnode_;
	}

	public void setImageFilename( String filename ) {
		image_filename_ = filename;

		File img = new File( image_filename_ );
		try {
			thumbnail_image_ = ImageIO.read( img );

			final int native_width = thumbnail_image_.getWidth();
			if( native_width > ControlPanelMonitorSettings.EAST_WIDTH ) {
				final double scale = ( (double) ControlPanelMonitorSettings.EAST_WIDTH ) / native_width;
				final int new_height = (int) ( scale * thumbnail_image_.getHeight() );

				// thumbnail_image_ = (BufferedImage) thumbnail_image_.getScaledInstance(
				// ControlPanelMonitorSettings.EAST_WIDTH, new_height,
				// java.awt.Image.SCALE_DEFAULT );

				BufferedImage smaller_version = new BufferedImage( ControlPanelMonitorSettings.EAST_WIDTH, new_height,
						thumbnail_image_.getType() );
				Graphics2D g2d = (Graphics2D) smaller_version.getGraphics();
				g2d.drawImage( thumbnail_image_, 0, 0, ControlPanelMonitorSettings.EAST_WIDTH, new_height, null );
				thumbnail_image_ = smaller_version;
			}

			if( DebugToggles.DEBUG_FRAME_GRAPH ) {
				DataBuffer dataBuffer = thumbnail_image_.getData().getDataBuffer();
				long sizeBytes = ( (long) dataBuffer.getSize() ) * 4l;
				usage_statistics.MemoryCounter.getInstance().addBytesForToken( "PrimaryFGNodes", sizeBytes );
			}
		}
		catch( IOException e ) {
			e.printStackTrace();
		}
	}

	@Override
	public String name() {
		return name_;
	}

	@Override
	public EdgeType[] getUpstreamEdges() {
		return upstream_edges_;
	}

	@Override
	public EdgeType[] getDownstreamEdges() {
		return downstream_edges_;
	}

	@Override
	public BufferedImage getThumbnailImage() {
		return thumbnail_image_;
	}

	@Override
	public void addUpstreamEdge( EdgeType E ) {
		EdgeType[] upstream_edges_new = new EdgeType[ upstream_edges_.length + 1 ];
		for( int i = 0; i < upstream_edges_.length; ++i ) {
			upstream_edges_new[ i ] = upstream_edges_[ i ];
		}
		upstream_edges_new[ upstream_edges_.length ] = E;
		upstream_edges_ = upstream_edges_new;
		// Arrays.sort(upstream_edges_);
	}

	@Override
	public void addDownstreamEdge( EdgeType E ) {
		EdgeType[] downstream_edges_new = new EdgeType[ downstream_edges_.length + 1 ];
		for( int i = 0; i < downstream_edges_.length; ++i ) {
			downstream_edges_new[ i ] = downstream_edges_[ i ];
		}
		downstream_edges_new[ downstream_edges_.length ] = E;
		downstream_edges_ = downstream_edges_new;
		// Arrays.sort(downstream_edges_);
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
	public void toggle_hardness() {
		hard_ = !hard_;
		corresponding_fnode_.setStop( hard_ );
	}

	@Override
	public boolean is_hard() {
		return hard_;
	}

	@Override
	public void applyToFrameGraph( FrameGraph fg ) {
		corresponding_fnode_ = fg.getPrimaryNode( index_ );
		fg.getPrimaryNode( index_ ).setStop( hard_ );
		fg.getPrimaryNode( index_ ).setImageFilename( image_filename_ );
	}

}
