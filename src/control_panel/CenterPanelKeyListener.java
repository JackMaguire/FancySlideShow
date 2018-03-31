package control_panel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import conceptual_graph.ConceptualNodeType;
import control_panel.CenterPanelView.ViewType;
import engine.Engine;
import settings.DebugToggles;

public class CenterPanelKeyListener implements KeyListener {

	private final Engine engine_;
	private final CenterPanelModel model_;
	private final CenterPanelView view_;

	public CenterPanelKeyListener( Engine engine, CenterPanelModel model, CenterPanelView view ) {
		engine_ = engine;
		model_ = model;
		view_ = view;
	}

	private void leftButton( boolean shift ) {
		final boolean was_reversed = engine_.reverse();
		engine_.setReverse( true );

		if( !engine_.isWaitingAtHardNode() && !was_reversed ) {
			return;
		}

		if( engine_.getTimer().isRunning() ) {
			if( engine_.isWaitingAtHardNode() ) {
				engine_.goAtNextTick();
			}
			engine_.takeNextSecondaryOption( shift );
		} else {
			if( shift ) {
				engine_.goBackNFrames( 5, true );
			} else {
				engine_.goBackOneFrame();
			}
		}
	}

	private void downButton() {
		engine_.stopAtNextTick();
	}

	private void rightButton( boolean shift ) {
		final boolean was_forward = !engine_.reverse();
		engine_.setReverse( false );

		if( !engine_.isWaitingAtHardNode() && !was_forward ) {
			return;
		}

		if( engine_.getTimer().isRunning() ) {
			if( engine_.isWaitingAtHardNode() ) {
				engine_.goAtNextTick();
			}
			engine_.takeNextSecondaryOption( shift );
		} else {
			if( shift ) {
				// advance 5 frames or until you hit a hard node, whichever comes first
				engine_.advanceNFrames( 5, true );
			} else {
				engine_.advanceOneFrame();
			}
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
		engine_.stopAtNextTick();
		final ConceptualNodeType selected_primary_node = model_.selectedNode();
		if( selected_primary_node != null ) {
			engine_.setCurrentNode( selected_primary_node.index() );
		}
	}

	@Override
	public void keyPressed( KeyEvent e ) {

		if( DebugToggles.DEBUG_KEYS ) {
			System.out.println( KeyEvent.getKeyText( e.getKeyCode() ) + " was pushed. " + e.isShiftDown() );
		}

		switch ( e.getKeyCode() ) {
			case ( KeyEvent.VK_LEFT ):
				leftButton( e.isShiftDown() );
				break;
			case ( KeyEvent.VK_RIGHT ):
				rightButton( e.isShiftDown() );
				break;
			case ( KeyEvent.VK_DOWN ):
				downButton();
				break;
			case ( KeyEvent.VK_SPACE ):
				spaceBar();
				break;
			case ( KeyEvent.VK_ENTER ):
				enter();
				break;
			case ( KeyEvent.VK_1 ):
				view_.setViewType( ViewType.GRAPH );
				break;
			case ( KeyEvent.VK_2 ):
				view_.setViewType( ViewType.COMPOSITE );
				break;
			case ( KeyEvent.VK_3 ):
				view_.setViewType( ViewType.CURRENT );
				break;
			case ( KeyEvent.VK_4 ):
				view_.setViewType( ViewType.NEXT );
				break;
			case ( KeyEvent.VK_5 ):
				view_.setViewType( ViewType.SELECTED );
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
