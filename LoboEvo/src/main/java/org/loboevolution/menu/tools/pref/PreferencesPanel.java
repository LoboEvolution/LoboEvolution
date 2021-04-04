/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.tools.pref;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.loboevolution.common.WrapperLayout;

import com.jtattoo.plaf.lobo.LoboPanel;

/**
 * The Class PreferencesPanel.
 *
 *
 *
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
