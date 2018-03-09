package engine;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Engine implements ActionListener {

	private final int delay_ = 41;
	private final Timer timer_;
	private final JPanel panel_;

	// FPS Delay
	// -----------
	// 24 41
	// 30 33.33
	// 60 16.67

	public Engine( JPanel panel ) {
		panel_ = panel;
		timer_ = new Timer( delay_, this );
	}

	public void start() {

	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		// TODO Auto-generated method stub

	}

}
