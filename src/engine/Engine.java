package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import control_panel.CenterPanelView;
import control_panel.ControlPanelView;
import frame_graph.FrameGraph;
import frame_graph.FrameNode;
import slide_show.SlideShowPanel;

public class Engine implements ActionListener {

	// FPS Delay
	// -----------
	// 24 41
	// 30 33.33
	// 60 16.67
	private final int delay_ = 41;

	private final Timer timer_;
	private final SlideShowPanel slide_show_panel_;

	private final FrameGraph frame_graph_;
	private FrameNode current_node_;

	private boolean reverse_ = false;
	private boolean waiting_at_hard_node_;

	private boolean go_at_next_tick_ = false;
	private boolean take_next_secondary_option_ = false;

	private final ControlPanelView control_panel_view_;

	public Engine( SlideShowPanel panel, FrameGraph frame_graph, ControlPanelView control_panel_view ) {
		slide_show_panel_ = panel;
		frame_graph_ = frame_graph;
		current_node_ = frame_graph_.getPrimaryNode( 0 );
		control_panel_view_ = control_panel_view;
		// center_panel_view_.addKeyListener( new CenterPanelKeyListener( this ) );
		waiting_at_hard_node_ = current_node_.stop();

		timer_ = new Timer( delay_, this );
	}

	public boolean isWaitingAtHardNode() {
		return waiting_at_hard_node_;
	}

	public void start() {
		slide_show_panel_.setImage( current_node_.image() );
		timer_.start();
	}

	public void takeNextSecondaryOption( boolean setting ) {
		take_next_secondary_option_ = setting;
	}

	public void takeNextSecondaryOption() {
		take_next_secondary_option_ = true;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {

		if( current_node_.stop() ) {
			if( go_at_next_tick_ ) {
				go_at_next_tick_ = false;
			} else {
				if( !waiting_at_hard_node_ ) {
					slide_show_panel_.setFast( false );
					slide_show_panel_.repaint();
				}
				waiting_at_hard_node_ = true;
				return;
			}
		}

		final FrameNode next_node = take_next_secondary_option_ ? current_node_.getSecondaryForwardNode()
				: current_node_.forwardNode();
		final boolean next_node_is_hard = next_node == null ? true : next_node.stop();

		if( waiting_at_hard_node_ && !next_node_is_hard ) {
			slide_show_panel_.setFast( true );
		}

		waiting_at_hard_node_ = false;
		if( reverse_ ) {
			goBackOneImage();
		} else {
			advanceOneImage();
		}
	}

	public void advanceOneImage() {
		if( current_node_.forwardNode() != null ) {
			if( take_next_secondary_option_ && current_node_.numForwardOptions() > 1 ) {
				take_next_secondary_option_ = false;
				current_node_ = current_node_.getSecondaryForwardNode();
			} else {
				current_node_ = current_node_.forwardNode();
			}
		}
		repaintImage();
	}

	public void goBackOneImage() {
		if( current_node_.reverseNode() != null ) {
			if( take_next_secondary_option_ && current_node_.numReverseOptions() > 1 ) {
				take_next_secondary_option_ = false;
				current_node_ = current_node_.getSecondaryReverseNode();
			} else {
				current_node_ = current_node_.reverseNode();
			}
		}
		repaintImage();
	}

	public void setReverse( boolean setting ) {
		reverse_ = setting;
	}

	public void toggleReverse() {
		reverse_ = !reverse_;
	}

	public boolean reverse() {
		return reverse_;
	}

	public FrameNode currentNode() {
		return current_node_;
	}

	public void repaintImage() {
		slide_show_panel_.setImage( current_node_.image() );
		slide_show_panel_.repaint();
		if( current_node_.IS_PRIMARY ) {
			control_panel_view_.getCenterPanelView().model().setCurrentNode( current_node_.UPSTREAM_PRIMARY_ID );
			control_panel_view_.getCenterPanelView().repaint();

			control_panel_view_.getWestPanelView().updateNotesForCurrentSlide( current_node_.node().getNotes() );
		}
	}

	public void setCurrentNode( int index ) {
		current_node_ = frame_graph_.getPrimaryNode( index );
		repaintImage();
	}

	public void goAtNextTick() {
		go_at_next_tick_ = true;
	}

	public void stopAtNextTick() {
		go_at_next_tick_ = false;
	}

	public Timer getTimer() {
		return timer_;
	}
}
