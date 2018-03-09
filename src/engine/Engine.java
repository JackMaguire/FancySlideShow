package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

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

	private boolean go_at_next_tick_ = false;

	public Engine( SlideShowPanel panel, FrameGraph frame_graph ) {
		slide_show_panel_ = panel;
		frame_graph_ = frame_graph;
		current_node_ = frame_graph_.getPrimaryNode( 0 );
		timer_ = new Timer( delay_, this );
	}

	public void start() {
		slide_show_panel_.setImage( current_node_.image() );
		timer_.start();
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		if( current_node_.stop() ) {
			if( go_at_next_tick_ ) {
				go_at_next_tick_ = false;
			} else {
				return;
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

}
