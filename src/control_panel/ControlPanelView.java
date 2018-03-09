package control_panel;

import java.awt.*;
import javax.swing.*;

import slide_show.SlideShowPanel;

public class ControlPanelView extends JPanel {

	private static final long serialVersionUID = 8381231028726842942L;

	private final ControlPanelModel model_;

	private final BorderLayout layout_ = new BorderLayout();
	private final WestPanelView west_view_;
	private final SouthPanelView south_view_ = new SouthPanelView();
	private final EastPanelView east_view_;	
	
	private final CenterPanelView center_view_;
	private final CenterPanelController center_controller_;

	public ControlPanelView( ControlPanelModel model ) {
		model_ = model;
		setLayout( layout_ );
		// layout_.addLayoutComponent( west_view_, BorderLayout.WEST );

		center_view_ = new CenterPanelView( model_.getCenterPanelModel() );
		add( center_view_, BorderLayout.CENTER );

		center_controller_ = new CenterPanelController( this, model_.getCenterPanelModel(), center_view_ );
		center_view_.addMouseListener( center_controller_ );

		add( south_view_, BorderLayout.SOUTH );

		east_view_ = new EastPanelView( model_.getEastPanelModel() );
		add( east_view_, BorderLayout.EAST );
		
		west_view_ = new WestPanelView();
		add( west_view_, BorderLayout.WEST );
		
		setVisible( true );
	}

	// public void paint(Graphics g) {}
}
