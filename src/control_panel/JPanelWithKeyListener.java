package control_panel;

import java.util.ArrayList;

import javax.swing.JPanel;

public class JPanelWithKeyListener extends JPanel {

	private static final long serialVersionUID = 3750551160877487912L;

	private final static ArrayList< JPanelWithKeyListener > all_instances_ = new ArrayList< JPanelWithKeyListener >();
	public static ArrayList< JPanelWithKeyListener > allInstances() {
		return all_instances_;
	}
	
	public JPanelWithKeyListener() {
		all_instances_.add( this );
		setFocusable( true );
	}
	
}
