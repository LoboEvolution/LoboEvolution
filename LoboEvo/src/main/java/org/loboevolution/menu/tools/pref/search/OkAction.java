/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.menu.tools.pref.search;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * The Class OkAction.
 *
 * @param <T> a T object.
 * @author utente
 * @version $Id: $Id
 */
public class OkAction<T> extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private final transient ItemEditor<T> item;

	/**
	 * <p>Constructor for OkAction.</p>
	 *
	 * @param item a {@link org.loboevolution.menu.tools.pref.search.ItemEditor} object.
	 */
	public OkAction(ItemEditor<T> item) {
		this.item = item;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
	 * ActionEvent)
	 */
	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		this.item.getEditor().validateItem();
		this.item.setResultingItem(this.item.getEditor().getItem());
		this.item.dispose();
	}
}
