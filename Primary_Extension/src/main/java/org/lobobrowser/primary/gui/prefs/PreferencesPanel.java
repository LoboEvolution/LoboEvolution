/*
 * GNU GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project. Copyright (C)
 * 2014 - 2015 Lobo Evolution This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either verion 2 of the
 * License, or (at your option) any later version. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details. You should have received
 * a copy of the GNU General Public License along with this library; if not,
 * write to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net;
 * ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui.prefs;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;

import org.lobobrowser.primary.gui.ValidationException;
import org.lobobrowser.util.gui.WrapperLayout;

/**
 * The Class PreferencesPanel.
 */
public class PreferencesPanel extends JPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ui. */
	private AbstractSettingsUI ui;

	/**
	 * Instantiates a new preferences panel.
	 */
	public PreferencesPanel() {
		this.setLayout(WrapperLayout.getInstance());
		this.setBorder(new CompoundBorder(new EtchedBorder(), new javax.swing.border.EmptyBorder(8, 8, 8, 8)));
	}

	/**
	 * Save.
	 *
	 * @return true, if successful
	 */
	public boolean save() {
		AbstractSettingsUI ui = this.ui;
		if (ui != null) {
			try {
				ui.save();
			} catch (ValidationException ve) {
				JOptionPane.showMessageDialog(this, ve.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * Restore defaults.
	 */
	public void restoreDefaults() {
		AbstractSettingsUI ui = this.ui;
		if (ui != null) {
			ui.restoreDefaults();
		}
	}

	/**
	 * Sets the settings ui.
	 *
	 * @param ui
	 *            the new settings ui
	 */
	public void setSettingsUI(AbstractSettingsUI ui) {
		this.ui = ui;
		this.removeAll();
		if (ui != null) {
			this.add(ui);
		}
		this.revalidate();
	}
}
