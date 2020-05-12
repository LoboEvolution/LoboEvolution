package org.loboevolution.menu.tools.pref;

import javax.swing.JPanel;

/**
 * <p>Abstract AbstractSettingsUI class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class AbstractSettingsUI extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Restore defaults.
	 */
	public abstract void restoreDefaults();

	/**
	 * Save.
	 */
	public abstract void save();
}
