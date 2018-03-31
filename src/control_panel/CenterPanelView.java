package control_panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.JComboBox;

import conceptual_graph.*;
import settings.DebugToggles;

public class CenterPanelView extends JPanelWithKeyListener {

	public enum ViewType {
		GRAPH, CURRENT, NEXT, SELECTED, COMPOSITE
	}

	private static final long serialVersionUID = -3387525849502002817L;

	private final CenterPanelModel model_;

	private ViewType current_view_type_ = ViewType.GRAPH;

	private EdgeLine[] lines_;
	private NodeCircle[] circles_;

	private final static Color text_ = Color.BLACK;

	private final static Color default_ = Color.GRAY;
	private final static Color forward_ = Color.BLUE;
	private final static Color backward_ = Color.RED;
	private final static Color current_ = Color.BLUE;
	private final static Color selected_ = Color.GREEN;

	private final static int radius_ = 10;
	private final static int diameter_ = 2 * radius_;

	private final static JComboBox< String > panes_ = new JComboBox< String >();
	private static int current_subgraph_ = 0;

	public CenterPanelView( CenterPanelModel model ) {
		model_ = model;

		final ConceptualGraph graph = model.getGraph();
		final int nsubgraphs = graph.numSubgraphs();
		for( int i = 0; i < nsubgraphs; ++i ) {
			panes_.addItem( graph.getNamesforSubgraph( i ) );
		}

		final int[] nodes_added_per_subgraph = new int[ nsubgraphs ];
		for( int i = 0; i < nsubgraphs; ++i ) {
			nodes_added_per_subgraph[ i ] = 0;
		}

		ConceptualNodeType[] all_nodes = graph.getNodes();
		circles_ = new NodeCircle[ all_nodes.length ];
		for( int i = 0; i < all_nodes.length; ++i ) {
			final int subgraph = all_nodes[ i ].subgraph();
			circles_[ i ] = createNodeCircle( i, nodes_added_per_subgraph[ subgraph ]++, graph.numNodesInSubgraph( subgraph ),
					all_nodes[ i ].name() );
		}

		ConceptualEdgeType[] all_edges = graph.getEdges();
		lines_ = new EdgeLine[ all_edges.length ];
		for( int i = 0; i < all_edges.length; ++i ) {
			int index0 = all_edges[ i ].outgoingNodeIndex();
			int index1 = all_edges[ i ].incomingNodeIndex();
			lines_[ i ] = new EdgeLine( circles_[ index0 ], circles_[ index1 ], all_edges[ i ].name() );
		}

		// this.setLayout( new BorderLayout() );
		// JPanel grid = new JPanel( new GridLayout( 10, 1 ) );
		// grid.add( panes_ );
		// this.add( grid, BorderLayout.WEST );
		panes_.addItemListener( new SubgraphListener( this ) );
	}

	public JComboBox< String > subgraphs() {
		return panes_;
	}

	public void updateCurrentSubgraph() {
		setCurrentSubgraph( panes_.getSelectedIndex() );
	}

	public void setCurrentSubgraph( int subgraph ) {
		if( subgraph != current_subgraph_ ) {
			current_subgraph_ = subgraph;
			panes_.setSelectedIndex( subgraph );
			repaint();
			// revalidate();
		}
	}

	public void setViewType( ViewType setting ) {
		current_view_type_ = setting;
		repaint();
	}

	public void recolorAllObjects() {
		final int current_node_index = model_.currentNode().index();

		int selected_node_index = -1;
		if( model_.selectedNode() != null ) {
			selected_node_index = model_.selectedNode().index();
		}

		/*
		final int next_edge_index = model_.nextEdge().index();
		int selected_edge_index = -1;
		if( model_.selectedEdge() != null ) {
			selected_edge_index = model_.selectedEdge().index();
		}*/

		for( int i = 0; i < circles_.length; ++i ) {
			if( i == current_node_index ) {
				circles_[ i ].setColor( current_ );
			} else if( i == selected_node_index ) {
				circles_[ i ].setColor( selected_ );
			} else {
				circles_[ i ].setColor( default_ );
			}
		}

		for( int i = 0; i < lines_.length; ++i ) {
			ConceptualEdgeType[] all_edges = model_.getGraph().getEdges();
			/*Grrr Eclipse reformatted this
			 * if( all_edges[ i ].index() == next_edge_index ) { lines_[ i ].setColor(
			 * current_ ); } else if( i == selected_edge_index ) { lines_[ i ].setColor(
			 * selected_ ); } else
			 */if( all_edges[ i ].outgoingNodeIndex() == current_node_index ) {
				lines_[ i ].setColor( forward_ );
			} else if( all_edges[ i ].incomingNodeIndex() == current_node_index ) {
				lines_[ i ].setColor( backward_ );
			} else {
				lines_[ i ].setColor( default_ );
			}
		}
	}

	private NodeCircle createNodeCircle( int index, int index_within_subgraph, int num_points, String name ) {
		// TODO change this to -= pi/2
		double radians = Math.PI * 2 * index_within_subgraph / ( (double) num_points ) - ( Math.PI / 2.0 );// uses radians
		double dx = Math.cos( radians );
		double dy = Math.sin( radians );
		return new NodeCircle( model_.getGraph().getNode( index ), dx, dy, name );
	}

	@Override
	protected void paintComponent( Graphics g ) {
		super.paintComponent( g );
		Graphics2D g2D = (Graphics2D) g;

		switch ( current_view_type_ ) {
			case GRAPH:
				paintGraph( g2D );
				break;
			case CURRENT:
				paintImage( g2D, model_.currentNode().getThumbnailImage() );
				break;
			case NEXT:
				final ConceptualEdgeType[] edges = model_.currentNode().getDownstreamEdges();
				if( edges.length == 0 ) {
					paintBlack( g2D );
				} else {
					final ConceptualNodeType next_node = model_.getGraph().getNode( edges[ 0 ].incomingNodeIndex() );
					paintImage( g2D, next_node.getThumbnailImage() );
				}
			case SELECTED:
				if( model_.selectedNode() != null ) {
					paintImage( g2D, model_.selectedNode().getThumbnailImage() );
				}
				break;
			case COMPOSITE:
				final BufferedImage current_image = model_.currentNode().getThumbnailImage();
				final ConceptualEdgeType[] edges2 = model_.currentNode().getDownstreamEdges();
				final ConceptualGraph conceptual_graph = model_.getGraph();

				if( edges2.length == 0 ) {
					paintImagesOverUnder( g2D, current_image, null );

				} else if( edges2.length == 1 ) {
					final int next_index = edges2[ 0 ].incomingNodeIndex();
					final ConceptualNodeType next_node2 = conceptual_graph.getNode( next_index );
					paintImagesOverUnder( g2D, current_image, next_node2.getThumbnailImage() );

				} else {
					final int next_index_active = edges2[ 0 ].incomingNodeIndex();
					final ConceptualNodeType next_node_active = conceptual_graph.getNode( next_index_active );
					final BufferedImage next_image_active = next_node_active.getThumbnailImage();

					final int next_index_option = edges2[ 1 ].incomingNodeIndex();
					final ConceptualNodeType next_node_option = conceptual_graph.getNode( next_index_option );
					final BufferedImage next_image_option = next_node_option.getThumbnailImage();

					paintImagesOverLeftRight( g2D, current_image, next_image_active, next_image_option );
				}
				break;
			default:
				paintGraph( g2D );
				break;
		}
	}

	private void paintBlack( Graphics2D g2D ) {
		final int panel_width = this.getWidth();
		final int panel_height = this.getHeight();
		g2D.setColor( Color.BLACK );
		g2D.fillRect( 0, 0, panel_width, panel_height );
	}

	private void paintImage( Graphics2D g2D, BufferedImage image ) {
		final int panel_width = this.getWidth();
		final int panel_height = this.getHeight();

		final int image_width = image.getWidth();
		final int image_height = image.getHeight();

		final double scale = util.ImageScale.getScale( panel_width, panel_height, image_width, image_height );
		final int scaled_image_width = (int) ( image_width * scale );
		final int scaled_image_height = (int) ( image_height * scale );

		final int side_buffersize = ( panel_width - scaled_image_width ) / 2;
		final int top_buffersize = ( panel_height - scaled_image_height ) / 2;

		g2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
		g2D.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );
		g2D.drawImage( image, side_buffersize, top_buffersize, scaled_image_width, scaled_image_height, null );
		// g2D.drawImage( image, 0, 0, image_width, image_height, null );
	}

	private void paintImagesOverUnder( Graphics2D g2D, BufferedImage over, BufferedImage under ) {
		final int sub_panel_width = this.getWidth();
		final int sub_panel_height = this.getHeight() / 2;

		g2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
		g2D.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

		if( over != null ) {
			final int image_width = over.getWidth();
			final int image_height = over.getHeight();

			final double scale = util.ImageScale.getScale( sub_panel_width, sub_panel_height, image_width, image_height );

			final int scaled_image_width = (int) ( image_width * scale );
			final int scaled_image_height = (int) ( image_height * scale );

			final int side_buffersize = ( sub_panel_width - scaled_image_width ) / 2;
			final int top_buffersize = ( sub_panel_height - scaled_image_height ) / 2;

			g2D.drawImage( over, side_buffersize, top_buffersize, scaled_image_width, scaled_image_height, null );
		} else {
			g2D.setColor( Color.BLACK );
			g2D.fillRect( 0, 0, sub_panel_width, sub_panel_height );
		}

		if( under != null ) {

			final int image_width = under.getWidth();
			final int image_height = under.getHeight();

			final double under_scale = util.ImageScale.getScale( sub_panel_width, sub_panel_height, image_width,
					image_height );

			final int scaled_image_width = (int) ( image_width * under_scale );
			final int scaled_image_height = (int) ( image_height * under_scale );
			final int side_buffersize = ( sub_panel_width - scaled_image_width ) / 2;
			final int top_buffersize = ( sub_panel_height - scaled_image_height ) / 2;

			g2D.drawImage( under, side_buffersize, sub_panel_height + top_buffersize, scaled_image_width, scaled_image_height,
					null );

		} else {
			g2D.setColor( Color.BLACK );
			g2D.fillRect( 0, sub_panel_height, sub_panel_width, sub_panel_height );
		}
	}

	private void paintImagesOverLeftRight( Graphics2D g2D, BufferedImage over, BufferedImage left, BufferedImage right ) {
		int sub_panel_width = this.getWidth();
		final int sub_panel_height = this.getHeight() / 2;

		g2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC );
		g2D.setRenderingHint( RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY );

		if( over != null ) {
			final int image_width = over.getWidth();
			final int image_height = over.getHeight();

			final double over_scale = util.ImageScale.getScale( sub_panel_width, sub_panel_height, image_width,
					image_height );

			final int scaled_image_width = (int) ( image_width * over_scale );
			final int scaled_image_height = (int) ( image_height * over_scale );

			final int side_buffersize = ( sub_panel_width - scaled_image_width ) / 2;
			final int top_buffersize = ( sub_panel_height - scaled_image_height ) / 2;

			g2D.drawImage( over, side_buffersize, top_buffersize, scaled_image_width, scaled_image_height, null );
		} else {
			g2D.setColor( Color.BLACK );
			g2D.fillRect( 0, 0, sub_panel_width, sub_panel_height );
		}

		// Bottom two panels have to share the bottom row
		sub_panel_width /= 2;

		if( left != null ) {

			final int image_width = left.getWidth();
			final int image_height = left.getHeight();

			final double under_scale = util.ImageScale.getScale( sub_panel_width, sub_panel_height, image_width,
					image_height );

			final int scaled_image_width = (int) ( image_width * under_scale );
			final int scaled_image_height = (int) ( image_height * under_scale );
			final int side_buffersize = ( sub_panel_width - scaled_image_width ) / 2;
			final int top_buffersize = ( sub_panel_height - scaled_image_height ) / 2;

			g2D.drawImage( left, side_buffersize, sub_panel_height + top_buffersize, scaled_image_width, scaled_image_height,
					null );

		} else {
			g2D.setColor( Color.BLACK );
			g2D.fillRect( 0, sub_panel_height, sub_panel_width, sub_panel_height );
		}

		if( right != null ) {

			final int image_width = ( right == null ? 1 : right.getWidth() );
			final int image_height = ( right == null ? 1 : right.getHeight() );

			final double under_scale = util.ImageScale.getScale( sub_panel_width, sub_panel_height, image_width,
					image_height );

			final int scaled_image_width = (int) ( image_width * under_scale );
			final int scaled_image_height = (int) ( image_height * under_scale );
			final int side_buffersize = ( sub_panel_width - scaled_image_width ) / 2;
			final int top_buffersize = ( sub_panel_height - scaled_image_height ) / 2;

			g2D.drawImage( right, sub_panel_width + side_buffersize, sub_panel_height + top_buffersize, scaled_image_width,
					scaled_image_height, null );

		} else {
			g2D.setColor( Color.BLACK );
			g2D.fillRect( sub_panel_width, sub_panel_height, sub_panel_width, sub_panel_height );
		}
	}

	private void paintGraph( Graphics2D g2D ) {
		recolorAllObjects();

		g2D.setStroke( new BasicStroke( 8F ) ); // set stroke width

		final int width = this.getWidth();
		final int height = this.getHeight();

		final int min_dim = ( width < height ? width : height );
		final int big_circle_R = min_dim / 2 - diameter_ - 30; // 30 is just a buffer for good measure

		for( NodeCircle circle : circles_ ) {
			if( circle.node().subgraph() == current_subgraph_ )
				circle.draw( g2D, diameter_, width, height, big_circle_R, true );
		}

		for( EdgeLine line : lines_ ) {
			if( line.circle1.node().subgraph() == current_subgraph_ ) {
				if( line.circle2.node().subgraph() == current_subgraph_ ) {
					line.draw( g2D, radius_ );
				} else {
					// line.draw( g2D, radius_, getWidth() / 2, getHeight() / 2 );
				}
			}
		}

		// quickly draw circles again so they are on top
		for( NodeCircle circle : circles_ ) {
			if( circle.node().subgraph() == current_subgraph_ )
				circle.draw( g2D, diameter_, width, height, big_circle_R, false );
		}
	}

	public NodeCircle get_circle( int x, int y ) {
		return get_circle( x, y, diameter_ );
	}

	public NodeCircle get_circle( int x, int y, int max_distance_1D ) {
		if( DebugToggles.DEBUG_CONTROL_PANEL ) {
			for( int i = 0; i < circles_.length; ++i ) {
				NodeCircle circle = circles_[ i ];
				System.out.println( i + "\t" + ( circle.most_recent_x - x ) + "\t" + ( circle.most_recent_y - y ) + "\t"
						+ ( Math.abs( circle.most_recent_x - x ) < max_distance_1D
								&& Math.abs( circle.most_recent_y - y ) < max_distance_1D ) );
			}
		}

		for( NodeCircle circle : circles_ ) {
			if( circle.node().subgraph() != current_subgraph_ )
				continue;
			if( Math.abs( circle.most_recent_x - x ) < max_distance_1D
					&& Math.abs( circle.most_recent_y - y ) < max_distance_1D ) {
				return circle;
			}
		}
		return null;
	}

	public CenterPanelModel model() {
		return model_;
	}

	static class NodeCircle {

		// final public int ID;
		private final ConceptualNodeType node_;

		final public double dx;
		final public double dy;

		public int most_recent_x = 0;
		public int most_recent_y = 0;
		private final String name_;

		private Color color_;

		public NodeCircle( ConceptualNodeType node, double dx, double dy, String name ) {
			node_ = node;
			this.dx = dx;
			this.dy = dy;
			name_ = name;
		}

		public int ID() {
			return node_.index();
		}

		public ConceptualNodeType node() {
			return node_;
		}

		public void setColor( Color color ) {
			color_ = color;
		}

		public void draw( Graphics g, int diameter, int width, int height, int big_circle_R, boolean recalculate ) {

			if( recalculate ) {
				most_recent_x = ( width / 2 ) + (int) ( dx * big_circle_R );
				most_recent_y = ( height / 2 ) + (int) ( dy * big_circle_R );
			}

			g.setColor( color_ );
			if( node_.is_hard() ) {
				g.fillRect( most_recent_x, most_recent_y, diameter, diameter );
				// g.fillOval( most_recent_x, most_recent_y, diameter, diameter );
			} else {
				g.fillOval( most_recent_x, most_recent_y, diameter, diameter );
			}

			if( name_.length() > 0 ) {
				g.setColor( text_ );
				g.drawString( name_, most_recent_x, most_recent_y );
			}
		}
	}

	static class EdgeLine {
		// public int x1, y1, x2, y2;

		private final NodeCircle circle1, circle2;
		private final String name_;

		private Color color_;

		public EdgeLine( NodeCircle circle1, NodeCircle circle2, String name ) {
			this.circle1 = circle1;
			this.circle2 = circle2;
			name_ = name;
		}

		public void setColor( Color color ) {
			color_ = color;
		}

		public void draw( Graphics g, int radius ) {
			g.setColor( color_ );
			g.drawLine( circle1.most_recent_x + radius, circle1.most_recent_y + radius, circle2.most_recent_x + radius,
					circle2.most_recent_y + radius );

			if( name_.length() > 0 ) {
				g.setColor( text_ );
				final int string_x = ( circle1.most_recent_x + circle2.most_recent_x ) / 2;
				final int string_y = ( circle1.most_recent_y + circle2.most_recent_y ) / 2;
				g.drawString( name_, string_x, string_y );
			}
		}

		public void draw( Graphics g, int radius, int x, int y ) {
			g.setColor( color_ );
			g.drawLine( circle1.most_recent_x + radius, circle1.most_recent_y + radius, x, y );

			if( name_.length() > 0 ) {
				g.setColor( text_ );
				final int string_x = ( circle1.most_recent_x + circle2.most_recent_x ) / 2;
				final int string_y = ( circle1.most_recent_y + circle2.most_recent_y ) / 2;
				g.drawString( name_, string_x, string_y );
			}
		}
	}

	private final static class SubgraphListener extends RemoveFocusListener implements ItemListener {

		private final CenterPanelView parent_;

		public SubgraphListener( CenterPanelView parent ) {
			super( parent );
			parent_ = parent;
		}

		public void itemStateChanged( ItemEvent e ) {
			if( e.getStateChange() == ItemEvent.SELECTED ) {
				parent_.updateCurrentSubgraph();
			}
			this.actionPerformed( null );
		}

	}

}
