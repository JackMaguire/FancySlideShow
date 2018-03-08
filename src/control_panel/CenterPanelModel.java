package control_panel;

import graph.*;

public class CenterPanelModel {

	private Graph graph_;

	private EdgeType selected_edge_;
	private NodeType current_node_;

	public CenterPanelModel( Graph graph ) {
		graph_ = graph;
		current_node_ = graph.getNode( 0 );
		selected_edge_ = current_node_.getDownstreamEdges()[ 0 ];
	}

	public Graph getGraph() {
		return graph_;
	}

	public EdgeType selectedEdge() {
		return selected_edge_;
	}

	public NodeType currentNode() {
		return current_node_;
	}

}
