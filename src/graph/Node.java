package graph;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
//import java.util.Arrays;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import compile_time_settings.CompileTimeSettings;
import frame_graph.FrameGraph;

public class Node implements NodeType {

	final private String name_;
	private int index_ = 0;

	private boolean hard_;

	private EdgeType[] upstream_edges_ = new EdgeType[ 0 ];
	private EdgeType[] downstream_edges_ = new EdgeType[ 0 ];

	private String image_filename_;
	private BufferedImage image_;

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

	public void setImageFilename( String filename ) {
		image_filename_ = filename;

		File img = new File( image_filename_ );
		try {
			image_ = ImageIO.read( img );

			if( CompileTimeSettings.DEBUG_FRAME_GRAPH ) {
				DataBuffer dataBuffer = image_.getData().getDataBuffer();
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
		return image_;
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
	}

	@Override
	public boolean is_hard() {
		return hard_;
	}

	@Override
	public void applyToFrameGraph( FrameGraph fg ) {
		fg.getPrimaryNode( index_ ).setStop( hard_ );
		fg.getPrimaryNode( index_ ).setImage( image_ );
	}

}
