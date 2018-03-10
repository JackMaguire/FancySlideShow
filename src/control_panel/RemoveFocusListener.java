package control_panel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class RemoveFocusListener implements ActionListener {

	private final JPanelWithKeyListener owner_;

	public RemoveFocusListener( JPanelWithKeyListener owner ) {
		owner_ = owner;
	}

	@Override
	public void actionPerformed( ActionEvent e ) {
		owner_.requestFocus();
	}

}