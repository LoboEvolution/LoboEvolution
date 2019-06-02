package org.lobo.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.lobo.component.BrowserFrame;

public class ExitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final BrowserFrame frame;

	public ExitAction(BrowserFrame frame) {
		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.frame.dispose();

	}

}
