package control_panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JTextArea;

import compile_time_settings.ControlPanelMonitorSettings;
import graph.ConceptualNodeType;

public class WestPanelView extends JPanelWithKeyListener {

	private static final long serialVersionUID = 267743378873832055L;

	// private NotesPanel notes_panel_;
	private JTextArea notes_for_current_ = new JTextArea( "Notes For Current" );
	private JTextArea personal_notes_ = new JTextArea( "Notes" );

	public WestPanelView( ConceptualNodeType starting_node ) {
		// super( ControlPanelMonitorSettings.WEST_WIDTH, 100 );
		this.setMinimumSize( new Dimension( ControlPanelMonitorSettings.WEST_WIDTH, 100 ) );
		this.setPreferredSize( new Dimension( ControlPanelMonitorSettings.WEST_WIDTH, 100 ) );

		Runtime.getRuntime().addShutdownHook( new shutdown_hook( personal_notes_ ) );
		notes_for_current_.setEditable( false );

		notes_for_current_.setText( starting_node.getNotes() );

		this.setLayout( new GridLayout( 2, 1, 10, 10 ) );
		add( notes_for_current_ );
		add( personal_notes_ );
	}

	public void updateNotesForCurrentSlide( String notes ) {
		notes_for_current_.setText( notes );
	}

	/*
	 * private final static class NorthWestView extends JPanel {
	 * 
	 * }
	 */

	private static class shutdown_hook extends Thread {

		private JTextArea notes_ptr_;

		public shutdown_hook( JTextArea notes ) {
			notes_ptr_ = notes;
		}

		public void run() {
			System.out.println( "Notes:" );
			System.out.println( notes_ptr_.getText() );
		}
	}

}
