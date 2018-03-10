package control_panel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import compile_time_settings.DebugToggles;
import graph.*;

public class CenterPanelView extends JPanelWithKeyListener {

	private static final long serialVersionUID = -3387525849502002817L;

	private final CenterPanelModel model_;

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

	private final static JComboBox< String > panes_ = new JComboBox< String >( new String[] { "1", "2", "3" } );

	public CenterPanelView( CenterPanelModel model ) {
		model_ = model;

		NodeType[] all_nodes = model.getGraph().getNodes();
		circles_ = new NodeCircle[ all_nodes.length ];
		for( int i = 0; i < all_nodes.length; ++i ) {
			circles_[ i ] = createNodeCircle( i, all_nodes.length, all_nodes[ i ].name() );
		}

		EdgeType[] all_edges = model.getGraph().getEdges();
		lines_ = new EdgeLine[ all_edges.length ];
		for( int i = 0; i < all_edges.length; ++i ) {
			int index0 = all_edges[ i ].outgoingNodeIndex();
			int index1 = all_edges[ i ].incomingNodeIndex();
			lines_[ i ] = new EdgeLine( circles_[ index0 ], circles_[ index1 ], all_edges[ i ].name() );
		}

		this.setLayout( new BorderLayout() );
		JPanel grid = new JPanel( new GridLayout( 10, 1 ) );
		grid.add( panes_ );
		this.add( grid, BorderLayout.WEST );
	}

	public void recolorAllObjects() {
		final int current_node_index = model_.currentNode().index();

		int selected_node_index = -1;
		if( model_.selectedNode() != null ) {
			selected_node_index = model_.selectedNode().index();
		}

		final int next_edge_index = model_.nextEdge().index();
		int selected_edge_index = -1;
		if( model_.selectedEdge() != null ) {
			selected_edge_index = model_.selectedEdge().index();
		}

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
			EdgeType[] all_edges = model_.getGraph().getEdges();
			/*
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

	private NodeCircle createNodeCircle( int index, int num_points, String name ) {
		double radians = Math.PI * 2 * index / ( (double) num_points );// uses radians
		double dx = Math.cos( radians );
		double dy = Math.sin( radians );
		return new NodeCircle( model_.getGraph().getNode( index ), dx, dy, name );
	}

	@Override
	protected void paintComponent( Graphics g ) {
		recolorAllObjects();

		super.paintComponent( g );
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke( new BasicStroke( 8F ) ); // set stroke width

		final int width = this.getWidth();
		final int height = this.getHeight();

		final int min_dim = ( width < height ? width : height );
		final int big_circle_R = min_dim / 2 - diameter_ - 30; // 30 is just a buffer for good measure

		for( NodeCircle circle : circles_ ) {
			circle.draw( g2D, diameter_, width, height, big_circle_R, true );
		}

		for( EdgeLine line : lines_ ) {
			line.draw( g2D, radius_ );
		}

		// quickly draw circles again so they are on top
		for( NodeCircle circle : circles_ ) {
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
		private final NodeType node_;

		final public double dx;
		final public double dy;

		public int most_recent_x = 0;
		public int most_recent_y = 0;
		private final String name_;

		private Color color_;

		public NodeCircle( NodeType node, double dx, double dy, String name ) {
			node_ = node;
			this.dx = dx;
			this.dy = dy;
			name_ = name;
		}

		public int ID() {
			return node_.index();
		}

		public NodeType node() {
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
	}

}
