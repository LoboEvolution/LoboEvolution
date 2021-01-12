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
import javax.swing.JOptionPane;

/**
 * <p>RemoveAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RemoveAction<T> extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private final transient ItemListControl<T> item;

	/**
	 * <p>Constructor for RemoveAction.</p>
	 *
	 * @param item a {@link org.loboevolution.menu.tools.pref.search.ItemListControl} object.
	 */
	public RemoveAction(ItemListControl<T> item) {
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
		if (JOptionPane.showConfirmDialog(this.item, "Are you sure you want to remove the selected item?", "Confirm",
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
			removeSelectedItem();
		}
	}

	/**
	 * Removes the selected item.
	 */
	private void removeSelectedItem() {
		final int index = this.item.getComboBox().getSelectedIndex();
		if (index != -1) {
			this.item.getComboBox().removeItemAt(index);
		}
	}
}
