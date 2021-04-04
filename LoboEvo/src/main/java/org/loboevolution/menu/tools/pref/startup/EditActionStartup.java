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

package org.loboevolution.menu.tools.pref.startup;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * <p>EditActionStartup class.</p>
 *
 *
 *
 */
public class EditActionStartup extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final StartupListControl control;

	/**
	 * <p>Constructor for EditActionStartup.</p>
	 *
	 * @param control a {@link org.loboevolution.menu.tools.pref.startup.StartupListControl} object.
	 */
	public EditActionStartup(StartupListControl control) {
		this.control = control;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		TextEdit dialog = new TextEdit();
		dialog.setCaption(this.control.getEditListCaption());
		dialog.setSize(new Dimension(400, 300));
		dialog.setLocationByPlatform(true);
		dialog.setText(this.control.getStringsAsText());
		dialog.setVisible(true);
		final String text = dialog.getResultingText();
		if (text != null) {
			this.control.setStringsFromText(text);
		}
	}
}
