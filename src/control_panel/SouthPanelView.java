package control_panel;

import java.awt.Dimension;

import javax.swing.*;

public class SouthPanelView extends JPanel {

	private JTextArea notes_ = new JTextArea("Notes");
	
	public SouthPanelView() {
		notes_.setPreferredSize( new Dimension(700, 200 ) );
		add(notes_);
		Runtime.getRuntime().addShutdownHook( new shutdown_hook(notes_) );
	}
	
	@Override
	public void finalize() {
		System.out.println("Notes:");
		System.out.println(notes_.getText());
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
