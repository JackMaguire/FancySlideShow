package control_panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import graph.*;


public class CenterPanelView extends JPanel {

	private static final long serialVersionUID = -3387525849502002817L;

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
		
		NodeType[] all_nodes = model.getGraph().getNodes();
		circles = new NodeCircle[ all_nodes.length ];
		for( int i=0; i<all_nodes.length; ++i ) {
			circles[i] = createNodeCircle( i, all_nodes.length );
		}
		
		EdgeType[] all_edges = model.getGraph().getEdges();
		lines = new EdgeLine[ all_edges.length ];
		for( int i=0; i<all_edges.length; ++i ) {
			int index0 = all_edges[i].outgoingNodeIndex();
			int index1 = all_edges[i].incomingNodeIndex();
			lines[i] = createEdgeLine( circles[ index0 ], circles[ index1 ] );
		}
	}
	
	public void recolorAllObjects() {
		final int node_index = model_.currentNode().index();
		final int edge_index = model_.selectedEdge().index();
		
		for( int i=0; i<circles.length; ++i ) {
			if ( i == node_index ) {
				circles[i].setColor( selected_ );
			} else {
				circles[i].setColor( default_ );
			}
		}
		
		for( int i=0; i<lines.length; ++i ) {
			EdgeType[] all_edges = model_.getGraph().getEdges();
			if ( all_edges[ i ].index() == edge_index ) {
				lines[i].setColor( selected_ );
			} else if ( all_edges[ i ].outgoingNodeIndex() == node_index ) {
				lines[i].setColor( forward_ );
			} else if ( all_edges[ i ].incomingNodeIndex() == node_index ) {
				lines[i].setColor( backward_ );
			} else {
				lines[i].setColor( default_ );
			}
		}
	}
	
	private static NodeCircle createNodeCircle( int index, int num_points ) {
		double radians = Math.PI * 2 * index / ((double) num_points);//uses radians
		double dx = Math.cos(radians);
		double dy = Math.sin(radians);
		return new NodeCircle(dx,dy);
	}
	
	private static EdgeLine createEdgeLine( NodeCircle nc1, NodeCircle nc2 ) {
		return new EdgeLine( nc1, nc2 );
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;      
	    g2D.setStroke(new BasicStroke(10F));  // set stroke width of 10
	    
		final int width = this.getWidth();
		final int height = this.getHeight();
		
		final int min_dim = ( width < height ? width : height );
		final int big_circle_R = min_dim / 2 - diameter_ - 30; //30 is just a buffer for good measure

	    for( NodeCircle circle : circles ) {
    			circle.draw(g2D, diameter_, width, height, big_circle_R );
	    }
	   
	    for( EdgeLine line : lines ) {
	    		line.draw(g2D, radius_ );
	    }
	    
	}
	
	private static class NodeCircle {

		final public double dx;
		final public double dy;
		
		public int most_recent_x = 0;
		public int most_recent_y = 0;
		
		private Color color_;
		
		public NodeCircle( double dx, double dy ) {
			this.dx = dx;
			this.dy = dy;
		}
		
		public void setColor( Color color ) {
			color_ = color;
		}
		
		public void draw( Graphics g, int diameter, int width, int height, int big_circle_R ) {
			g.setColor( color_ );
			
			most_recent_x = (width / 2) + (int) ( dx * big_circle_R );
			most_recent_y = (height / 2) + (int) ( dy * big_circle_R );
			
			g.fillOval(most_recent_x, most_recent_y, diameter, diameter);
		}
	}
	
	private static class EdgeLine {
		//public int x1, y1, x2, y2;
		
		private final NodeCircle circle1, circle2;
	
		private Color color_;
		
		public EdgeLine( NodeCircle circle1, NodeCircle circle2 ) {
			this.circle1 = circle1;
			this.circle2 = circle2;
		}
		
		public void setColor( Color color ) {
			color_ = color;
		}
		
		public void draw( Graphics g, int radius ) {
			g.setColor( color_ );
			g.drawLine( circle1.most_recent_x + radius, circle1.most_recent_y + radius,
					circle2.most_recent_x + radius, circle2.most_recent_y + radius );
		}
	}
	
}
