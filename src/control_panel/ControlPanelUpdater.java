package control_panel;

import conceptual_graph.ConceptualNodeType;
import engine.Engine;

//The goal of this class is to manage the information flow to the individual components of the control panel when a state is changed

public class ControlPanelUpdater {

	private final ControlPanelModel control_panel_model_;
	private final ControlPanelView control_panel_view_;

	public ControlPanelUpdater( ControlPanelModel model, ControlPanelView view ) {
		control_panel_model_ = model;
		control_panel_view_ = view;
	}

	public void setCurrentNode( ConceptualNodeType node ) {
		control_panel_model_.getCenterPanelModel().setCurrentNode( node );
		control_panel_view_.getEastPanelView().setCurrentNode( node );
		control_panel_view_.getWestPanelView().updateNotesForCurrentSlide( node.getNotes() );
		
		Thread T = new RepaintThread( control_panel_view_ );
		T.start();
	}
	
	public void setCurrentSubgraph( int subgraph ) {
		control_panel_view_.getCenterPanelView().setCurrentSubgraph( subgraph );
		
		Thread T = new RepaintThread( control_panel_view_ );
		T.start();
	}

	private static class RepaintThread extends Thread {

		final private ControlPanelView control_panel_view_;

		public RepaintThread( ControlPanelView view ) {
			control_panel_view_ = view;
		}

		public void run() {
			control_panel_view_.repaint();
		}
	}
	
}
