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

package org.loboevolution.menu;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 * <p>AboutAction class.</p>
 *
 *
 *
 */
public class AboutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final String text = "This is Lobo Evolution 2.0, a pure Java web browser.\r\n"
				+ "Copyright (c) 2021 The Lobo Evolution.\r\n https://github.com/LoboEvolution/LoboEvolution/";
		JOptionPane.showMessageDialog(null, text, "About", JOptionPane.INFORMATION_MESSAGE);

	}
}
