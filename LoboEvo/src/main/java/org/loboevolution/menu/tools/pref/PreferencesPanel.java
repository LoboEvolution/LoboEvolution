package org.loboevolution.menu.tools.pref;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.loboevolution.common.WrapperLayout;
import org.loboevolution.gui.LoboPanel;

/**
 * The Class PreferencesPanel.
 *
 * @author utente
 * @version $Id: $Id
 */
public class PreferencesPanel extends LoboPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ui. */
	private transient AbstractSettingsUI setting;

	/**
	 * Instantiates a new preferences panel.
	 */
	public PreferencesPanel() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setLayout(WrapperLayout.getInstance());
		setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(8, 8, 8, 8)));
	}

	/**
	 * Restore defaults.
	 */
	public void restoreDefaults() {
		final AbstractSettingsUI ui = this.setting;
		if (ui != null) {
			ui.restoreDefaults();
		}
	}

	/**
	 * Save.
	 *
	 * @return true, if successful
	 */
	public boolean save() {
		final AbstractSettingsUI ui = this.setting;
		if (ui != null) {
			ui.save();
		}
		return true;
	}

	/**
	 * Sets the settings ui.
	 *
	 * @param ui the new settings ui
	 */
	public void setSettingsUI(AbstractSettingsUI ui) {
		this.setting = ui;
		removeAll();
		if (ui != null) {
			this.add(ui);
		}
		revalidate();
	}
}
