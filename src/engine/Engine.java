package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import control_panel.ControlPanelUpdater;
import frame_graph.FrameGraph;
import frame_graph.FrameNode;
import settings.SlideShowPanelSettings;
import slide_show.SlideShowPanel;

public class Engine implements ActionListener {

	// FPS Delay
	// -----------
	// 24 41
	// 30 33.33
	// 60 16.67
	private final int delay_;

	private final Timer timer_;
	private final SlideShowPanel slide_show_panel_;

	private final FrameGraph frame_graph_;
	private FrameNode current_node_;

	private boolean reverse_ = false;
	private boolean waiting_at_hard_node_;

	private boolean go_at_next_tick_ = false;// if true, overrides the hardness of hard nodes
	private boolean take_next_secondary_option_ = false;

	private int current_subgraph_ = 1;

	private final ControlPanelUpdater cp_updater_;

	public Engine( SlideShowPanel panel, FrameGraph frame_graph, ControlPanelUpdater cp_updater ) {
		slide_show_panel_ = panel;
		frame_graph_ = frame_graph;
		current_node_ = frame_graph_.getPrimaryNode( 0 );
		cp_updater_ = cp_updater;
		// center_panel_view_.addKeyListener( new CenterPanelKeyListener( this ) );
		waiting_at_hard_node_ = current_node_.stop();

		delay_ = (int) ( 1000.0 / (double) SlideShowPanelSettings.FPS );
		// System.out.println( "FPS: " + SlideShowPanelSettings.FPS );

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
			advanceOneFrame();
		}
	}

	public void advanceOneFrame() {
		if( current_node_.forwardNode() != null ) {
			if( take_next_secondary_option_ && current_node_.numForwardOptions() > 1 ) {
				take_next_secondary_option_ = false;
				current_node_ = current_node_.getSecondaryForwardNode();
			} else {
				current_node_ = current_node_.forwardNode();
			}
		}
		repaintImage();

		if( current_node_.IS_PRIMARY ) {
			final int subgraph = current_node_.getConceptualNode().subgraph();
			if( subgraph != current_subgraph_ ) {
				current_subgraph_ = subgraph;
				cp_updater_.setCurrentSubgraph( subgraph );
			}
		}
	}

	public void advanceNFrames( int num_frames, boolean stop_at_soft_primary_node ) {
		for( int i = 0; i < num_frames; ++i ) {
			if( current_node_.forwardNode() != null ) {
				if( take_next_secondary_option_ && current_node_.numForwardOptions() > 1 ) {
					take_next_secondary_option_ = false;
					current_node_ = current_node_.getSecondaryForwardNode();
				} else {
					current_node_ = current_node_.forwardNode();
				}
			}

			if( current_node_.IS_PRIMARY && ( current_node_.stop() || stop_at_soft_primary_node ) ) {
				break;
			}
		}

		repaintImage();

		if( current_node_.IS_PRIMARY ) {
			final int subgraph = current_node_.getConceptualNode().subgraph();
			if( subgraph != current_subgraph_ ) {
				current_subgraph_ = subgraph;
				cp_updater_.setCurrentSubgraph( subgraph );
			}
		}
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

		if( current_node_.IS_PRIMARY ) {
			final int subgraph = current_node_.getConceptualNode().subgraph();
			if( subgraph != current_subgraph_ ) {
				current_subgraph_ = subgraph;
				cp_updater_.setCurrentSubgraph( subgraph );
			}
		}
	}

	public void goBackNFrames( int num_frames, boolean stop_at_soft_primary_node ) {
		for( int i = 0; i < num_frames; ++i ) {
			if( current_node_.reverseNode() != null ) {
				if( take_next_secondary_option_ && current_node_.numReverseOptions() > 1 ) {
					take_next_secondary_option_ = false;
					current_node_ = current_node_.getSecondaryReverseNode();
				} else {
					current_node_ = current_node_.reverseNode();
				}
			}

			if( current_node_.IS_PRIMARY && ( current_node_.stop() || stop_at_soft_primary_node ) ) {
				break;
			}
		}
		repaintImage();

		if( current_node_.IS_PRIMARY ) {
			final int subgraph = current_node_.getConceptualNode().subgraph();
			if( subgraph != current_subgraph_ ) {
				current_subgraph_ = subgraph;
				cp_updater_.setCurrentSubgraph( subgraph );
			}
		}
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
		Thread T = new RepaintThread( this );
		T.start();
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

	protected void RepaintThreadExec() {
		slide_show_panel_.setImage( current_node_.image() );
		slide_show_panel_.repaint();
		if( current_node_.IS_PRIMARY ) {
			cp_updater_.setCurrentNode( current_node_.getConceptualNode() );
		}
	}

	private static class RepaintThread extends Thread {

		final private Engine engine_;

		public RepaintThread( Engine e ) {
			engine_ = e;
		}

		public void run() {
			engine_.RepaintThreadExec();
		}
	}
}
