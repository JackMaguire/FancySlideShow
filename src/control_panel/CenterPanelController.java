package control_panel;

import java.awt.event.*;

import control_panel.CenterPanelView.*;
import graph.Node;
import graph.NodeType;
import compile_time_settings.CompileTimeSettings;

public class CenterPanelController implements MouseListener {

	final private CenterPanelModel model_;
	final private CenterPanelView view_;
	
	public CenterPanelController( CenterPanelModel model, CenterPanelView view ) {
		model_ = model;
		view_ = view;
	}
	
	private void handle_click_on_node( MouseEvent e, NodeCircle node_circle ) {
		if( e.getButton() == 1 ) {
			//select node
			model_.select( model_.getGraph().getNode( node_circle.ID ) );
			view_.repaint();
			if( CompileTimeSettings.DEBUG ) {
				System.out.println( "selected node " + node_circle.ID );
			}
			//TODO
		} else {
			//change hardness of node
			NodeType node_type = model_.getGraph().getNode( node_circle.ID );
			if( node_type instanceof Node ) {
				Node node = (Node) node_type;
				
			}
			//TODO
		}
	}
	
	@Override
	public void mouseClicked( MouseEvent e ) {
		//#1 check for node
		NodeCircle node_circle = view_.get_circle( e.getX(), e.getY() );
		if( node_circle != null ) {
			handle_click_on_node( e, node_circle );
			return;
		}

		//#2 check for edge
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
