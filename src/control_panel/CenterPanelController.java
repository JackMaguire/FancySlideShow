package control_panel;

import java.awt.event.*;

import control_panel.CenterPanelView.*;
import graph.NodeType;
import compile_time_settings.CompileTimeSettings;

public class CenterPanelController implements MouseListener {

	final private CenterPanelModel model_;
	final private CenterPanelView view_;

	final private ControlPanelView parent_view_;

	public CenterPanelController( ControlPanelView parent_view, CenterPanelModel model, CenterPanelView view ) {
		parent_view_ = parent_view;
		model_ = model;
		view_ = view;
	}

	private void handle_click_on_node( MouseEvent e, NodeCircle node_circle ) {
		NodeType node = node_circle.node();
		if( e.getButton() == 1 ) {
			// select node
			model_.select( node );
			view_.repaint();
			if( CompileTimeSettings.DEBUG_CONTROL_PANEL ) {
				System.out.println( "selected node " + node.name() );
			}
		}
		else {
			// change hardness of node
			node.toggle_hardness();
			view_.repaint();
			if( CompileTimeSettings.DEBUG_CONTROL_PANEL ) {
				System.out.println( "toggled node " + node.name() );
			}
		}

		parent_view_.repaint();
		parent_view_.revalidate();
	}

	@Override
	public void mouseClicked( MouseEvent e ) {
		// #1 check for node
		NodeCircle node_circle = view_.get_circle( e.getX(), e.getY() );
		if( node_circle != null ) {
			handle_click_on_node( e, node_circle );
			return;
		}

		// #2 check for edge
	}

	@Override
	public void mousePressed( MouseEvent e ) {

	}

	@Override
	public void mouseReleased( MouseEvent e ) {

	}

	@Override
	public void mouseEntered( MouseEvent e ) {

	}

	@Override
	public void mouseExited( MouseEvent e ) {

	}

}
