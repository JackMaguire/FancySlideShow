package control_panel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import compile_time_settings.DebugToggles;
import engine.Engine;

public class CenterPanelKeyListener implements KeyListener {

	private final Engine engine_;

	public CenterPanelKeyListener( Engine engine ) {
		engine_ = engine;
	}

	private void leftButton() {
		engine_.setReverse( true );
		engine_.goAtNextTick();
	}

	private void rightButton() {
		engine_.setReverse( false );
		engine_.goAtNextTick();
	}

	@Override
	public void keyPressed( KeyEvent e ) {

		if( DebugToggles.DEBUG_KEYS ) {
			System.out.println( KeyEvent.getKeyText( e.getKeyCode() ) + " was pushed." );
		}

		switch ( e.getKeyCode() ) {
			case ( KeyEvent.VK_LEFT ):
				leftButton();
				break;
			case ( KeyEvent.VK_RIGHT ):
				rightButton();
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
