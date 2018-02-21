package control_panel;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import graph.*;


public class CenterPanelView extends JPanel {

	private final CenterPanelModel model_;
	
	private EdgeLine[] lines;
	private NodeCircle[] circles;
	
	private final static Color default_ = Color.BLACK;
	private final static Color forward_ = Color.GREEN;
	private final static Color backward_ = Color.RED;
	private final static Color selected_ = Color.BLUE;
	
	private final static int radius_ = 10;
	private final static int diameter_ = 2 * radius_;
	
	public CenterPanelView( CenterPanelModel model ) {
		model_ = model;

		final int width = this.getWidth();
		final int height = this.getHeight();
		
		final int min_dim = ( width < height ? width : height );
		final int big_circle_R = min_dim / 2 - diameter_ - 30; //30 is just a buffer for good measure
		
		NodeType[] all_nodes = model.getGraph().getNodes();
		circles = new NodeCircle[ all_nodes.length ];
		for( int i=0; i<all_nodes.length; ++i ) {
			circles[i] = createNodeCircle( i, all_nodes.length, big_circle_R, width, height );
		}
		
		EdgeType[] all_edges = model.getGraph().getEdges();
		lines = new EdgeLine[ all_edges.length ];
		for( int i=0; i<all_edges.length; ++i ) {
			int index0 = all_edges[i].outgoingNodeIndex();
			int index1 = all_edges[i].incomingNodeIndex();
			lines[i] = createEdgeLine( circles[ index0 ], circles[ index1 ], radius_ );
		}
	}
	
	public void recolorAllObjects() {
		final int node_index = model_.currentNode().index();
		final int edge_index = model_.selectedEdge().index();
		
		for( int i=0; i<circles.length; ++i ) {
			if ( i == node_index ) {
				circles[i].color_ = this.selected_;
			} else {
				circles[i].color_ = this.default_;
			}
		}
		
		for( int i=0; i<lines.length; ++i ) {
			EdgeType[] all_edges = model_.getGraph().getEdges();
			if ( all_edges[ i ].index() == edge_index ) {
				lines[i].color_ = this.selected_;
			} else if ( all_edges[ i ].outgoingNodeIndex() == node_index ) {
				lines[i].color_ = this.forward_;
			} else if ( all_edges[ i ].incomingNodeIndex() == node_index ) {
				lines[i].color_ = this.backward_;
			} else {
				lines[i].color_ = this.default_;
			}
		}
	}
	
	private static NodeCircle createNodeCircle( int index, int num_points, int big_circle_R, int width, int height) {
		
		double radians = Math.PI * 2 * index / ((double) num_points);//uses radians
		double dx = Math.cos(radians);
		double dy = Math.sin(radians);
		
		int x = (width / 2) + (int) ( dx * big_circle_R );
		int y = (height / 2) + (int) ( dy * big_circle_R );
		
		return new NodeCircle(x,y);
	}
	
	private static EdgeLine createEdgeLine( NodeCircle nc1, NodeCircle nc2, int r ) {
		return new EdgeLine( nc1.x + r, nc1.y + r, nc2.x + r, nc2.y + r );
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	
	private static class NodeCircle {
		final public int x;
		final public int y;
		//final public int r;//defined outside
		private Color color_;
		
		public NodeCircle( int x, int y ) {
			this.x = x;
			this.y = y;
		}
		
		public void setColor( Color color ) {
			color_ = color;
		}
		
		public void draw( Graphics g, int diameter ) {
			g.setColor( color_ );
			g.fillOval(x, y, diameter, diameter);
		}
	}
	
	private static class EdgeLine {
		final public int x1, y1, x2, y2;

		private Color color_;
		
		public EdgeLine( int x1, int y1, int x2, int y2 ) {
			this.x1 = x1;
			this.y1 = y1;
			this.x2 = x2;
			this.y2 = y2;
		}
		
		public void setColor( Color color ) {
			color_ = color;
		}
		
		public void draw( Graphics g ) {
			g.setColor( color_ );
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
}
