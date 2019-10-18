package org.loboevolution.menu.tools.clear;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

public class ClearHistoryAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public ClearHistoryAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final ClearHistoryWindow clear = new ClearHistoryWindow(this.frame);
		clear.setLocationByPlatform(true);
		clear.setResizable(false);
		clear.setSize(new Dimension(200, 190));
		clear.setVisible(true);
	}
}