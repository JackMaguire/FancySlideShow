package control_panel;

import java.awt.*;
import javax.swing.*;

public class ControlPanelView extends JPanel{

	private static final long serialVersionUID = 8381231028726842942L;
	
	private final ControlPanelModel model_;
	
	BorderLayout layout_ = new BorderLayout();
	WestPanelView west_view_ = new WestPanelView();
	JTextField notes_ = new JTextField("Notes");
	
	public ControlPanelView( ControlPanelModel model ) {
		model_ = model;
		setLayout( layout_ );
		//layout_.addLayoutComponent( west_view_, BorderLayout.WEST );
		
		JPanel south = new JPanel();
		south.add(notes_);
		add( south, BorderLayout.SOUTH );

		setVisible(true);
	}
	
	//public void paint(Graphics g) {}
}
