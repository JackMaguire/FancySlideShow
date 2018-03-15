package control_panel;

import conceptual_graph.*;

public class CenterPanelModel {

	private ConceptualGraph graph_;

	private ConceptualNodeType current_node_;
	private ConceptualEdgeType next_edge_;

	private ConceptualNodeType selected_node_;
	private ConceptualEdgeType selected_edge_;
	private boolean node_was_selected_more_recently_ = true;

	public CenterPanelModel( ConceptualGraph graph ) {
		graph_ = graph;
		current_node_ = graph.getNode( 0 );
		selected_node_ = null;
		selected_edge_ = null;
		next_edge_ = current_node_.getDownstreamEdges()[ 0 ];
	}

	public ConceptualGraph getGraph() {
		return graph_;
	}

	public ConceptualEdgeType nextEdge() {
		return next_edge_;
	}

	public void setNextEdge( ConceptualEdgeType edge ) {
		next_edge_ = edge;
	}

	public ConceptualNodeType currentNode() {
		return current_node_;
	}

	public void setCurrentNode( ConceptualNodeType node ) {
		current_node_ = node;
	}

	public void setCurrentNode( int index ) {
		current_node_ = graph_.getNode( index );
	}

	public ConceptualNodeType selectedNode() {
		return selected_node_;
	}

	public void setSelectedNode( ConceptualNodeType node ) {
		node_was_selected_more_recently_ = true;
		selected_node_ = node;
	}

	public ConceptualEdgeType selectedEdge() {
		return selected_edge_;
	}

	public void setSelectedEdge( ConceptualEdgeType edge ) {
		node_was_selected_more_recently_ = false;
		selected_edge_ = edge;
	}

	public void select( ConceptualNodeType node ) {
		setSelectedNode( node );
	}

	public void select( ConceptualEdgeType edge ) {
		setSelectedEdge( edge );
	}

	public boolean node_was_selected_more_recently() {
		return node_was_selected_more_recently_;
	}
}
