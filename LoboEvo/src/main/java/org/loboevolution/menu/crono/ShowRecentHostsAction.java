package org.loboevolution.menu.crono;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

public class ShowRecentHostsAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public ShowRecentHostsAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final ShowRecentWindow window = new ShowRecentWindow(this.frame);
		window.setLocationByPlatform(true);
		window.setResizable(false);
		window.setSize(new Dimension(600, 400));
		window.setVisible(true);

	}

}
