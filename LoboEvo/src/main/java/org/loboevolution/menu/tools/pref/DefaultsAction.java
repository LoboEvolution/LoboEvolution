package org.loboevolution.menu.tools.pref;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * <p>DefaultsAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DefaultsAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private transient PreferenceWindow prefer;

	/**
	 * <p>Constructor for DefaultsAction.</p>
	 *
	 * @param prefer a {@link org.loboevolution.menu.tools.pref.PreferenceWindow} object.
	 */
	public DefaultsAction(PreferenceWindow prefer) {
		this.prefer = prefer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (JOptionPane.showConfirmDialog(this.prefer, "Are you sure you want to restore defaults?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			this.prefer.getPreferencesPanel().restoreDefaults();
		}
	}
}
