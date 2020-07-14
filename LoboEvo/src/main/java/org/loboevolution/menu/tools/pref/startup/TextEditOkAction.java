package org.loboevolution.menu.tools.pref.startup;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

/**
 * <p>TextEditOkAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TextEditOkAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The search. */
	private transient TextEdit search;

	/** The text area. */
	private final JTextArea textArea;

	/**
	 * <p>Constructor for TextEditOkAction.</p>
	 *
	 * @param textArea a {@link javax.swing.JTextArea} object.
	 * @param search a {@link org.loboevolution.menu.tools.pref.startup.TextEdit} object.
	 */
	public TextEditOkAction(JTextArea textArea, TextEdit search) {
		this.textArea = textArea;
		this.search = search;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.search.setResultingText(this.textArea.getText());
		this.search.dispose();
	}
}
