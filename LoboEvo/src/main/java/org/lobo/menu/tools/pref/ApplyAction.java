package org.lobo.menu.tools.pref;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * The Class ApplyAction.
 */
public class ApplyAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private transient PreferenceWindow prefer;

	public ApplyAction(PreferenceWindow prefer) {
		this.prefer = prefer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.prefer.getPreferencesPanel().save();
	}
}
