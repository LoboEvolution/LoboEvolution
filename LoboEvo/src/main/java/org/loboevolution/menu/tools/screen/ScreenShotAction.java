package org.loboevolution.menu.tools.screen;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.component.BrowserFrame;

public class ScreenShotAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public ScreenShotAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		new ScreenShootWindow(this.frame);
	}

}
