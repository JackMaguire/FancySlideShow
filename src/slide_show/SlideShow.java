package slide_show;

import java.awt.*;

import javax.swing.*;

import conceptual_graph.*;
import control_panel.*;
import engine.Engine;
import frame_graph.FrameGraph;
import settings.ControlPanelMonitorSettings;
import settings.DebugToggles;
import settings.SlideShowPanelSettings;

public class SlideShow implements SlideShowType {

	// Starts at Node 0
	private ConceptualGraph graph_;
	// private Node current_node_;

	private final ControlPanelModel control_panel_model_;
	private final ControlPanelView control_panel_view_;
	private final SlideShowPanel slide_show_panel_;

	public SlideShow( ConceptualGraph graph ) {
		graph_ = graph;
		control_panel_model_ = new ControlPanelModel( graph_ );
		control_panel_view_ = new ControlPanelView( control_panel_model_ );
		slide_show_panel_ = new SlideShowPanel( null );
		// control_panel_model_.getCenterPanelModel().currentNode().getThumbnailImage()
		// );
	}

	// Getters and setters
	protected ConceptualGraph graph() {
		return graph_;
	}

	public void setGraph( ConceptualGraph g ) {
		graph_ = g;
	}

	// run
	public void run() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// p( ge.toString() );

		GraphicsDevice[] devices = ge.getScreenDevices();
		// Window w = devices[ 0 ].getFullScreenWindow();

		JFrame slideshow_frame = createSlideshowJFrame();

		if( devices.length > 1 ) {
			devices[ SlideShowPanelSettings.MONITOR ].setFullScreenWindow( slideshow_frame );
		} else {
			// devices[ 0 ].setFullScreenWindow( slideshow_frame );
		}

		JFrame control_panel = createControlPanelJFrame();

		FrameGraph frame_graph = graph_.createFrameGraph();

		ControlPanelUpdater cp_updater = new ControlPanelUpdater( control_panel_model_, control_panel_view_ );

		Engine engine = new Engine( slide_show_panel_, frame_graph, cp_updater );
		engine.start();

		CenterPanelKeyListener center_panel_key_listener = new CenterPanelKeyListener( engine,
				control_panel_model_.getCenterPanelModel(), control_panel_view_.getCenterPanelView() );
		slideshow_frame.addKeyListener( center_panel_key_listener );
		for( JPanelWithKeyListener jp : JPanelWithKeyListener.allInstances() ) {
			jp.addKeyListener( center_panel_key_listener );
		}
		if( DebugToggles.DEBUG_KEYS ) {
			System.out
					.println( "Key listener added to " + ( JPanelWithKeyListener.allInstances().size() + 1 ) + " jpanels!" );
		}
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
		F.add( control_panel_view_ );
		F.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		F.setExtendedState( JFrame.NORMAL );
		F.setUndecorated( false );
		F.setVisible( true );
		return F;
	}

	/*
	 * void p( Object...objects ) { System.out.println(objects); }
	 */
}
