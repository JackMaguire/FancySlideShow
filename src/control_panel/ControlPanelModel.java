package control_panel;

import conceptual_graph.ConceptualGraph;

public class ControlPanelModel {

	private final WestPanelModel west_;
	private final CenterPanelModel center_;
	private final EastPanelModel east_;

	private final ConceptualGraph graph_;

	public ControlPanelModel( ConceptualGraph graph ) {
		graph_ = graph;
		center_ = new CenterPanelModel( graph );
		west_ = new WestPanelModel();
		east_ = new EastPanelModel( center_, graph );
	}

	public WestPanelModel getWestPanelModel() {
		return west_;
	}

	public CenterPanelModel getCenterPanelModel() {
		return center_;
	}

	public EastPanelModel getEastPanelModel() {
		return east_;
	}

	public ConceptualGraph getGraph() {
		return graph_;
	}

}
