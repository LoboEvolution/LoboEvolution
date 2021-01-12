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

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * <p>EditAction class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class EditAction<T> extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The add. */
	private final boolean add;

	private final transient ItemListControl<T> item;

	/**
	 * Instantiates a new edits the action.
	 *
	 * @param add the add
	 * @param item a {@link org.loboevolution.menu.tools.pref.search.ItemListControl} object.
	 */
	public EditAction(boolean add, ItemListControl<T> item) {
		this.add = add;
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
		ItemEditor<T> dialog = new ItemEditor<>(this.item.getItemEditorFactory());
		dialog.setTitle(this.add ? "Add Item" : "Edit Item");
		dialog.setCaption(this.item.getEditListCaption());
		dialog.pack();
		final Dimension size = dialog.getSize();
		if (size.width > 400) {
			dialog.setSize(new Dimension(400, size.height));
		}
		dialog.setLocationByPlatform(true);
		if (!this.add) {
			dialog.setItem(getSelectedItem());
		}
		dialog.setVisible(true);
		final T item = dialog.getResultingItem();
		if (item != null) {
			if (this.add) {
				addItem(item);
			} else {
				replaceSelectedItem(item);
			}
		}
	}

	/**
	 * Adds the item.
	 *
	 * @param item the item
	 */
	private void addItem(T item) {
		this.item.getComboBox().addItem(item);
		this.item.getComboBox().setSelectedItem(item);
	}

	/**
	 * Gets the selected item.
	 *
	 * @return the selected item
	 */
	@SuppressWarnings("unchecked")
	private T getSelectedItem() {
		return (T) this.item.getComboBox().getSelectedItem();
	}

	/**
	 * Replace selected item.
	 *
	 * @param item
	 */
	private void replaceSelectedItem(T item) {
		final int index = this.item.getComboBox().getSelectedIndex();
		if (index != -1) {
			this.item.getComboBox().removeItemAt(index);
		}
		this.item.getComboBox().addItem(item);
		this.item.getComboBox().setSelectedItem(item);
	}
}
