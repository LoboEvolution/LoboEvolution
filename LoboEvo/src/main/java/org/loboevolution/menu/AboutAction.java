package org.loboevolution.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * <p>AboutAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class AboutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		final String text = "This is Lobo Evolution 1.0, a pure Java web browser.\r\n"
				+ "Copyright (c) 2020 The Lobo Evolution.\r\n https://github.com/LoboEvolution/LoboEvolution/";
		JOptionPane.showMessageDialog(null, text, "About", JOptionPane.INFORMATION_MESSAGE);

	}
}
