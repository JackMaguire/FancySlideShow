package control_panel;

import java.awt.Color;
import java.awt.Graphics;


public class CenterPanelView {

	CenterPanelModel model_;
	
	private EdgeLine[] lines;
	private NodeCircle[] circles;
	
	private Color default_;
	private Color forward_;
	private Color backward_;
	private Color selected_;
	
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
