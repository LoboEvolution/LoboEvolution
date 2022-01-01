/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2022 Lobo Evolution
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

package org.loboevolution.menu.tools.developer;

import com.jtattoo.plaf.lobo.LoboPanel;
import org.loboevolution.common.WrapperLayout;
import org.loboevolution.menu.tools.AbstractToolsUI;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * The Class DeveloperToolPanel.
 */
public class DeveloperToolPanel extends LoboPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The ui. */
	private transient AbstractToolsUI setting;

	/**
	 * Instantiates a new preferences panel.
	 */
	public DeveloperToolPanel() {
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setLayout(WrapperLayout.getInstance());
		setBorder(new CompoundBorder(new EtchedBorder(), new EmptyBorder(8, 8, 8, 8)));
	}

	/**
	 * Sets the settings ui.
	 *
	 * @param ui the new settings ui
	 */
	public void setSettingsUI(AbstractToolsUI ui) {
		this.setting = ui;
		removeAll();
		if (ui != null) {
			this.add(ui);
		}
		revalidate();
	}
}
