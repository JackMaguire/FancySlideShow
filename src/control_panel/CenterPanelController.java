package control_panel;

import java.awt.event.*;

import control_panel.CenterPanelView.*;

public class CenterPanelController implements MouseListener {

	final private CenterPanelModel model_;
	final private CenterPanelView view_;
	
	public CenterPanelController( CenterPanelModel model, CenterPanelView view ) {
		model_ = model;
		view_ = view;
	}
	
	@Override
	public void mouseClicked( MouseEvent e ) {
		NodeCircle node_circle = view_.get_circle( e.getX(), e.getY() );
		if( e.getButton() == 1 ) {
			//select node
			model_.select( model_.getGraph().getNode( node_circle.ID ) );
			view_.repaint();
			//TODO
		} else {
			//change hardness of node
			//TODO
		}
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
