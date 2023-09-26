/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.menu.tools.pref.search;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * <p>EditAction class.</p>
 *
 *
 *
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
	public EditAction(final boolean add, final ItemListControl<T> item) {
		this.add = add;
		this.item = item;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(final ActionEvent e) {
		final ItemEditor<T> dialog = new ItemEditor<>(this.item.getItemEditorFactory());
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
	private void addItem(final T item) {
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
	private void replaceSelectedItem(final T item) {
		final int index = this.item.getComboBox().getSelectedIndex();
		if (index != -1) {
			this.item.getComboBox().removeItemAt(index);
		}
		this.item.getComboBox().addItem(item);
		this.item.getComboBox().setSelectedItem(item);
	}
}
