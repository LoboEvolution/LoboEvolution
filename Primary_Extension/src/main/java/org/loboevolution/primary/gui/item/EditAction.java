/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.primary.gui.item;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.primary.gui.SwingTasks;

/**
 * The Class EditAction.
 * 
 * @param <T>
 */
public class EditAction<T> extends AbstractAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The add. */
	private final boolean add;

	private transient ItemListControl<T> item;

	/**
	 * Instantiates a new edits the action.
	 *
	 * @param add
	 *            the add
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
	@Override
	public void actionPerformed(ActionEvent e) {
		Frame parentFrame = SwingTasks.getFrame(item);
		ItemEditorDialog<T> dialog;
		if (parentFrame != null) {
			dialog = new ItemEditorDialog<T>(parentFrame, item.getItemEditorFactory());
		} else {
			Dialog parentDialog = SwingTasks.getDialog(item);
			dialog = new ItemEditorDialog<T>(parentDialog, item.getItemEditorFactory());
		}
		dialog.setModal(true);
		dialog.setTitle(this.add ? "Add Item" : "Edit Item");
		dialog.setCaption(item.getEditListCaption());
		dialog.pack();
		Dimension size = dialog.getSize();
		if (size.width > 400) {
			dialog.setSize(new Dimension(400, size.height));
		}
		dialog.setLocationByPlatform(true);
		if (!this.add) {
			dialog.setItem(getSelectedItem());
		}
		dialog.setVisible(true);
		T item = dialog.getResultingItem();
		if (item != null) {
			if (this.add) {
				addItem(item);
			} else {
				replaceSelectedItem(item);
			}
		}
	}

	/**
	 * Gets the selected item.
	 *
	 * @return the selected item
	 */
	@SuppressWarnings("unchecked")
	private T getSelectedItem() {
		return (T) item.getComboBox().getSelectedItem();
	}

	/**
	 * Adds the item.
	 *
	 * @param item
	 *            the item
	 */
	private void addItem(T itm) {
		item.getComboBox().addItem(itm);
		item.getComboBox().setSelectedItem(itm);
	}

	/**
	 * Replace selected item.
	 *
	 * @param item
	 *            the item
	 */
	private void replaceSelectedItem(T itm) {
		int index = item.getComboBox().getSelectedIndex();
		if (index != -1) {
			item.getComboBox().removeItemAt(index);
		}
		item.getComboBox().addItem(itm);
		item.getComboBox().setSelectedItem(itm);
	}
}
