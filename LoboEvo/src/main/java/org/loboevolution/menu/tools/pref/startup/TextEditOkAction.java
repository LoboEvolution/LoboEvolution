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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;

/**
 * <p>TextEditOkAction class.</p>
 *
 *
 *
 */
public class TextEditOkAction extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The search. */
	private final transient TextEdit search;

	/** The text area. */
	private final JTextArea textArea;

	/**
	 * <p>Constructor for TextEditOkAction.</p>
	 *
	 * @param textArea a {@link javax.swing.JTextArea} object.
	 * @param search a {@link org.loboevolution.menu.tools.pref.startup.TextEdit} object.
	 */
	public TextEditOkAction(JTextArea textArea, TextEdit search) {
		this.textArea = textArea;
		this.search = search;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		this.search.setResultingText(this.textArea.getText());
		this.search.dispose();
	}
}
