package org.lobo.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class AboutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	@Override
	public void actionPerformed(ActionEvent e) {
		final String text = "This is Lobo Evolution 0.99.3, a pure Java web browser.\r\n"
				+ "Copyright (c) 2018 The Lobo Evolution.\r\n http://sourceforge.net/projects/loboevolution/";
		JOptionPane.showMessageDialog(null, text, "About", JOptionPane.INFORMATION_MESSAGE);

	}
}