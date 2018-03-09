package control_panel;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import compile_time_settings.ControlPanelMonitorSettings;

public class WestPanelView extends JPanel {

	private static final long serialVersionUID = 267743378873832055L;

	public WestPanelView() {

		this.setMinimumSize( new Dimension( ControlPanelMonitorSettings.WEST_WIDTH, 100 ) );
		this.setPreferredSize( new Dimension( ControlPanelMonitorSettings.WEST_WIDTH, 100 ) );

		this.setLayout( new GridLayout( 2, 1 ) );
	}

	/*
	 * private final static class NorthWestView extends JPanel {
	 * 
	 * }
	 */

}
