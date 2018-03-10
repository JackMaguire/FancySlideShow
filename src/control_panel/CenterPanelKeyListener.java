package control_panel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import compile_time_settings.DebugToggles;
import engine.Engine;
import graph.NodeType;

public class CenterPanelKeyListener implements KeyListener {

	private final Engine engine_;
	private final CenterPanelModel model_;

	public CenterPanelKeyListener( Engine engine, CenterPanelModel model ) {
		engine_ = engine;
		model_ = model;
	}

	private void leftButton( boolean shift ) {
		if( engine_.getTimer().isRunning() ) {
			engine_.setReverse( true );
			engine_.goAtNextTick();
		} else {
			engine_.goBackOneImage();
		}
	}

	private void rightButton( boolean shift ) {
		if( engine_.getTimer().isRunning() ) {
			engine_.setReverse( false );
			engine_.goAtNextTick();
		} else {
			engine_.advanceOneImage();
		}
	}
	
	private void spaceBar() {
		if( engine_.getTimer().isRunning() ) {
			engine_.getTimer().stop();
		} else {
			engine_.getTimer().start();
		}
	}

	private void enter() {
		final NodeType selected_primary_node = model_.selectedNode();
		if( selected_primary_node != null ) {
			engine_.setCurrentNode( selected_primary_node.index() );
		}
	}
	
	@Override
	public void keyPressed( KeyEvent e ) {

		if( DebugToggles.DEBUG_KEYS ) {
			System.out.println( KeyEvent.getKeyText( e.getKeyCode() ) + " was pushed." );
		}

		switch ( e.getKeyCode() ) {
			case ( KeyEvent.VK_LEFT ):
				leftButton( e.isShiftDown() );
				break;
			case ( KeyEvent.VK_RIGHT ):
				rightButton( e.isShiftDown() );
				break;
			case ( KeyEvent.VK_SPACE ):
				spaceBar();
				break;
			case ( KeyEvent.VK_ENTER ):
				enter();
				break;	
		}
	}

	@Override
	public void keyTyped( KeyEvent e ) {

	}

	@Override
	public void keyReleased( KeyEvent e ) {

	}

}
