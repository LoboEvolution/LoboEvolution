package org.lobo.menu.tools.pref;

import javax.swing.JPanel;

public abstract class AbstractSettingsUI extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Restore defaults.
	 */
	public abstract void restoreDefaults();

	/**
	 * Save.
	 *
	 * @throws ValidationException the validation exception
	 */
	public abstract void save();
}
