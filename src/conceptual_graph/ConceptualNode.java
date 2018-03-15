package conceptual_graph;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
//import java.util.Arrays;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import frame_graph.FrameGraph;
import frame_graph.FrameNode;
import settings.ControlPanelMonitorSettings;
import settings.DebugToggles;

public class ConceptualNode implements ConceptualNodeType {

	final private String name_;
	private String notes_ = "";

	private int index_ = 0;
	private int subgraph_;

	private boolean hard_;

	private ConceptualEdgeType[] upstream_edges_ = new ConceptualEdgeType[ 0 ];
	private ConceptualEdgeType[] downstream_edges_ = new ConceptualEdgeType[ 0 ];

	private String image_filename_;
	private BufferedImage thumbnail_image_;

	private FrameNode corresponding_fnode_ = null;

	public ConceptualNode( String name ) {
		name_ = name;
		hard_ = true;
	}

	public ConceptualNode( String name, boolean is_hard ) {
		name_ = name;
		hard_ = is_hard;
	}

	public ConceptualNode( String name, boolean is_hard, String image_filename ) {
		name_ = name;
		hard_ = is_hard;
		setImageFilename( image_filename );
	}

	public ConceptualNode( String name, boolean is_hard, String image_filename, String notes ) {
		name_ = name;
		hard_ = is_hard;
		notes_ = notes;
		setImageFilename( image_filename );
	}

	@Override
	public String getNotes() {
		return notes_;
	}

	public void setNotes( String setting ) {
		notes_ = setting;
	}

	public void appendNotes( String setting ) {
		notes_ += "\n" + setting;
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
	public ConceptualEdgeType[] getUpstreamEdges() {
		return upstream_edges_;
	}

	@Override
	public ConceptualEdgeType[] getDownstreamEdges() {
		return downstream_edges_;
	}

	@Override
	public BufferedImage getThumbnailImage() {
		return thumbnail_image_;
	}

	@Override
	public void addUpstreamEdge( ConceptualEdgeType E ) {
		ConceptualEdgeType[] upstream_edges_new = new ConceptualEdgeType[ upstream_edges_.length + 1 ];
		for( int i = 0; i < upstream_edges_.length; ++i ) {
			upstream_edges_new[ i ] = upstream_edges_[ i ];
		}
		upstream_edges_new[ upstream_edges_.length ] = E;
		upstream_edges_ = upstream_edges_new;
		// Arrays.sort(upstream_edges_);
	}

	@Override
	public void addDownstreamEdge( ConceptualEdgeType E ) {
		ConceptualEdgeType[] downstream_edges_new = new ConceptualEdgeType[ downstream_edges_.length + 1 ];
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
	public void setIndex( int index, int subgraph ) {
		index_ = index;
		subgraph_ = subgraph;
	}

	@Override
	public int subgraph() {
		return subgraph_;
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
