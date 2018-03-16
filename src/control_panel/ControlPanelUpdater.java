package control_panel;

import conceptual_graph.ConceptualNodeType;

//The goal of this class is to manage the information flow to the individual components of the control panel when a state is changed

public class ControlPanelUpdater {

	private final ControlPanelModel control_panel_model_;
	private final ControlPanelView control_panel_view_;

	public ControlPanelUpdater( ControlPanelModel model, ControlPanelView view ) {
		control_panel_model_ = model;
		control_panel_view_ = view;
	}

	void setCurrentNode( ConceptualNodeType node ) {
		control_panel_model_.getCenterPanelModel().setCurrentNode( node );
	}

}
