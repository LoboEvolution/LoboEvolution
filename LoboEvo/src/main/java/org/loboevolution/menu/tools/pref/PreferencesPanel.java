/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.tools.pref;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.loboevolution.common.WrapperLayout;

import com.jtattoo.plaf.lobo.LoboPanel;
import org.loboevolution.menu.tools.AbstractToolsUI;

import java.io.Serial;

/**
 * The Class PreferencesPanel.
 */
public class PreferencesPanel extends LoboPanel {

	/** The Constant serialVersionUID. */
	@Serial
	private static final long serialVersionUID = 1L;

	/** The ui. */
	private transient AbstractToolsUI setting;

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
		final AbstractToolsUI ui = this.setting;
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
		final AbstractToolsUI ui = this.setting;
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
	public void setSettingsUI(final AbstractToolsUI ui) {
		this.setting = ui;
		removeAll();
		if (ui != null) {
			this.add(ui);
		}
		revalidate();
	}
}
