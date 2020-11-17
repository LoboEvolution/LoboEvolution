package org.loboevolution.menu.tools.pref.startup;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * <p>TextEditCancelAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class TextEditCancelAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The search. */
	private final transient TextEdit search;

	/**
	 * <p>Constructor for TextEditCancelAction.</p>
	 *
	 * @param search a {@link org.loboevolution.menu.tools.pref.startup.TextEdit} object.
	 */
	public TextEditCancelAction(TextEdit search) {
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
		this.search.setResultingText(null);
		this.search.dispose();
	}
}
