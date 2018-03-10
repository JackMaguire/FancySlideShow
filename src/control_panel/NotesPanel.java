package control_panel;

import java.awt.Dimension;

import javax.swing.*;

public class NotesPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8837535643735827491L;
	private JTextArea notes_ = new JTextArea( "Notes" );

	public NotesPanel( int width, int height ) {
		notes_.setPreferredSize( new Dimension( width, height ) );
		add( notes_ );
		Runtime.getRuntime().addShutdownHook( new shutdown_hook( notes_ ) );
	}

	@Override
	public void finalize() {
		System.out.println( "Notes:" );
		System.out.println( notes_.getText() );
	}

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
