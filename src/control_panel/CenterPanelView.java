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

	private EdgeLine[] lines_;
	private NodeCircle[] circles_;

	private final static Color text_ = Color.BLACK;

	private final static Color default_ = Color.GRAY;
	private final static Color forward_ = Color.GREEN;
	private final static Color backward_ = Color.RED;
	private final static Color selected_ = Color.BLUE;

	private final static int radius_ = 10;
	private final static int diameter_ = 2 * radius_;

	public CenterPanelView(CenterPanelModel model) {
		model_ = model;

		NodeType[] all_nodes = model.getGraph().getNodes();
		circles_ = new NodeCircle[all_nodes.length];
		for (int i = 0; i < all_nodes.length; ++i) {
			circles_[i] = createNodeCircle(i, all_nodes.length, all_nodes[i].name());
		}

		EdgeType[] all_edges = model.getGraph().getEdges();
		lines_ = new EdgeLine[all_edges.length];
		for (int i = 0; i < all_edges.length; ++i) {
			int index0 = all_edges[i].outgoingNodeIndex();
			int index1 = all_edges[i].incomingNodeIndex();
			lines_[i] = new EdgeLine(circles_[index0], circles_[index1], all_edges[i].name());
		}
	}

	public void recolorAllObjects() {
		final int node_index = model_.currentNode().index();
		final int edge_index = model_.selectedEdge().index();

		for (int i = 0; i < circles_.length; ++i) {
			if (i == node_index) {
				circles_[i].setColor(selected_);
			} else {
				circles_[i].setColor(default_);
			}
		}

		for (int i = 0; i < lines_.length; ++i) {
			EdgeType[] all_edges = model_.getGraph().getEdges();
			if (all_edges[i].index() == edge_index) {
				lines_[i].setColor(selected_);
			} else if (all_edges[i].outgoingNodeIndex() == node_index) {
				lines_[i].setColor(forward_);
			} else if (all_edges[i].incomingNodeIndex() == node_index) {
				lines_[i].setColor(backward_);
			} else {
				lines_[i].setColor(default_);
			}
		}
	}

	private static NodeCircle createNodeCircle(int index, int num_points, String name) {
		double radians = Math.PI * 2 * index / ((double) num_points);// uses radians
		double dx = Math.cos(radians);
		double dy = Math.sin(radians);
		return new NodeCircle(dx, dy, name);
	}

	@Override
	protected void paintComponent(Graphics g) {
		recolorAllObjects();

		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		g2D.setStroke(new BasicStroke(8F)); // set stroke width

		final int width = this.getWidth();
		final int height = this.getHeight();

		final int min_dim = (width < height ? width : height);
		final int big_circle_R = min_dim / 2 - diameter_ - 30; // 30 is just a buffer for good measure

		for (NodeCircle circle : circles_) {
			circle.draw(g2D, diameter_, width, height, big_circle_R, true);
		}

		for (EdgeLine line : lines_) {
			line.draw(g2D, radius_);
		}

		// quickly draw circles again so they are on top
		for (NodeCircle circle : circles_) {
			circle.draw(g2D, diameter_, width, height, big_circle_R, false);
		}
	}

	public NodeCircle get_circle(int x, int y, int max_distance_1D) {
		for (NodeCircle circle : circles_) {
			if (circle.most_recent_x - x < max_distance_1D && circle.most_recent_y - y < max_distance_1D) {
				return circle;
			}
		}
		return null;
	}

	private static class NodeCircle {

		final public double dx;
		final public double dy;

		public int most_recent_x = 0;
		public int most_recent_y = 0;
		private final String name_;

		private Color color_;

		public NodeCircle(double dx, double dy, String name) {
			this.dx = dx;
			this.dy = dy;
			name_ = name;
		}

		public void setColor(Color color) {
			color_ = color;
		}

		public void draw(Graphics g, int diameter, int width, int height, int big_circle_R, boolean recalculate) {
			g.setColor(color_);

			if (recalculate) {
				most_recent_x = (width / 2) + (int) (dx * big_circle_R);
				most_recent_y = (height / 2) + (int) (dy * big_circle_R);
			}

			g.fillOval(most_recent_x, most_recent_y, diameter, diameter);

			if (name_.length() > 0) {
				g.setColor(text_);
				g.drawString(name_, most_recent_x, most_recent_y);
			}
		}
	}

	private static class EdgeLine {
		// public int x1, y1, x2, y2;

		private final NodeCircle circle1, circle2;
		private final String name_;

		private Color color_;

		public EdgeLine(NodeCircle circle1, NodeCircle circle2, String name) {
			this.circle1 = circle1;
			this.circle2 = circle2;
			name_ = name;
		}

		public void setColor(Color color) {
			color_ = color;
		}

		public void draw(Graphics g, int radius) {
			g.setColor(color_);
			g.drawLine(circle1.most_recent_x + radius, circle1.most_recent_y + radius, circle2.most_recent_x + radius,
					circle2.most_recent_y + radius);

			if (name_.length() > 0) {
				g.setColor(text_);
				final int string_x = (circle1.most_recent_x + circle2.most_recent_x) / 2;
				final int string_y = (circle1.most_recent_y + circle2.most_recent_y) / 2;
				g.drawString(name_, string_x, string_y);
			}
		}
	}

}
