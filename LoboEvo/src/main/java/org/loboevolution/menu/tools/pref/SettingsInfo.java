package org.loboevolution.menu.tools.pref;

public interface SettingsInfo {

	/**
	 * Creates the settings ui.
	 *
	 * @return the abstract settings ui
	 */
	AbstractSettingsUI createSettingsUI();

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	String getDescription();

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();
}
