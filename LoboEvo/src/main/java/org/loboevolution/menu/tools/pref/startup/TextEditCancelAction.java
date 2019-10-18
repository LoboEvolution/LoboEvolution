package org.loboevolution.menu.tools.pref.startup;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class TextEditCancelAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The search. */
	private transient SimpleTextEditDialog search;

	public TextEditCancelAction(SimpleTextEditDialog search) {
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
		this.search.setResultingText(null);
		this.search.dispose();
	}
}