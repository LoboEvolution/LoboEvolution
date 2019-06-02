package org.lobo.menu.tools.pref.startup;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

public class TextEditOkAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The search. */
	private transient SimpleTextEditDialog search;

	/** The text area. */
	private final JTextArea textArea;

	public TextEditOkAction(JTextArea textArea, SimpleTextEditDialog search) {
		this.textArea = textArea;
		this.search = search;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.search.setResultingText(this.textArea.getText());
		this.search.dispose();
	}
}
