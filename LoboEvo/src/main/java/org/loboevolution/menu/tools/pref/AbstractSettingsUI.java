package org.loboevolution.menu.tools.pref;

import org.loboevolution.gui.LoboPanel;

/**
 * <p>Abstract AbstractSettingsUI class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract class AbstractSettingsUI extends LoboPanel {

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
