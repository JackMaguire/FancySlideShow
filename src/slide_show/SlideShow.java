package slide_show;

import java.awt.*;

import javax.swing.*;

import compile_time_settings.ControlPanelMonitorSettings;
import control_panel.*;
import graph.*;

public class SlideShow implements SlideShowType {

	// Starts at Node 0
	private Graph graph_;
	// private Node current_node_;

	private ControlPanelModel control_panel_;
	private final SlideShowPanel slide_show_panel_;

	public SlideShow( Graph graph ) {
		graph_ = graph;
		control_panel_ = new ControlPanelModel( graph_ );
		slide_show_panel_ = new SlideShowPanel( control_panel_.getCenterPanelModel().currentNode().getThumbnailImage() );
	}

	// Getters and setters
	protected Graph graph() {
		return graph_;
	}

	public void setGraph( Graph g ) {
		graph_ = g;
	}

	// run
	public void run() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// p( ge.toString() );

		GraphicsDevice[] devices = ge.getScreenDevices();
		// Window w = devices[ 0 ].getFullScreenWindow();

		JFrame slideshow_frame = createSlideshowJFrame();
		devices[ 1 ].setFullScreenWindow( slideshow_frame );

		JFrame control_panel = createControlPanelJFrame();
	}

	private JFrame createSlideshowJFrame() {
		JFrame F = new JFrame( "SlideShow" );
		F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		F.add( slide_show_panel_ );
		F.setExtendedState( JFrame.MAXIMIZED_BOTH );
		F.setUndecorated( true );
		F.setVisible( true );
		return F;
	}

	private JFrame createControlPanelJFrame() {
		JFrame F = new JFrame( "Control Panel" );
		F.setSize( ControlPanelMonitorSettings.CP_WIDTH, ControlPanelMonitorSettings.CP_HEIGHT );
		F.add( new ControlPanelView( control_panel_ ) );
		F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		F.setExtendedState( JFrame.NORMAL );
		F.setUndecorated( false );
		F.setVisible( true );
		return F;
	}

	// util
	void p( String s ) {
		System.out.println( s );
	}

	/*
	 * void p( Object...objects ) { System.out.println(objects); }
	 */
}
