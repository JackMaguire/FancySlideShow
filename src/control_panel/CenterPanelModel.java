package control_panel;

import graph.*;

public class CenterPanelModel {

	private Graph graph_;

	private NodeType current_node_;
	private EdgeType next_edge_;

	private NodeType selected_node_;
	private EdgeType selected_edge_;
	private boolean node_was_selected_more_recently_ = true;

	public CenterPanelModel( Graph graph ) {
		graph_ = graph;
		current_node_ = graph.getNode( 0 );
		selected_node_ = null;
		selected_edge_ = null;
		next_edge_ = current_node_.getDownstreamEdges()[ 0 ];
	}

	public Graph getGraph() {
		return graph_;
	}

	public EdgeType nextEdge() {
		return next_edge_;
	}

	public void setNextEdge( EdgeType edge ) {
		next_edge_ = edge;
	}

	public NodeType currentNode() {
		return current_node_;
	}

	public void setCurrentNode( NodeType node ) {
		current_node_ = node;
	}

	public void setCurrentNode( int index ) {
		current_node_ = graph_.getNode( index );
	}

	public NodeType selectedNode() {
		return selected_node_;
	}

	public void setSelectedNode( NodeType node ) {
		node_was_selected_more_recently_ = true;
		selected_node_ = node;
	}

	public EdgeType selectedEdge() {
		return selected_edge_;
	}

	public void setSelectedEdge( EdgeType edge ) {
		node_was_selected_more_recently_ = false;
		selected_edge_ = edge;
	}

	public void select( NodeType node ) {
		setSelectedNode( node );
	}

	public void select( EdgeType edge ) {
		setSelectedEdge( edge );
	}

	public boolean node_was_selected_more_recently() {
		return node_was_selected_more_recently_;
	}
}
