package org.loboevolution.primary.gui.item;

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

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;

/**
 * The Class ItemListControl.
 *
 * @param <T>
 *            the generic type
 */
public class ItemListControl<T> extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The combo box. */
	private final JComboBox<T> comboBox;

	/** The item editor factory. */
	private final transient ItemEditorFactory<T> itemEditorFactory;

	/** The edit list caption. */
	private String editListCaption;

	/**
	 * Instantiates a new item list control.
	 *
	 * @param ief
	 *            the ief
	 */
	public ItemListControl(ItemEditorFactory<T> ief) {
		this.itemEditorFactory = ief;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.comboBox = new JComboBox<T>();
		this.getComboBox().setPreferredSize(new Dimension(100, 24));
		this.getComboBox().setEditable(false);
		JButton editButton = new JButton();
		editButton.setAction(new EditAction<T>(false, this));
		editButton.setText("Edit");
		JButton addButton = new JButton();
		addButton.setAction(new EditAction<T>(true, this));
		addButton.setText("Add");
		JButton removeButton = new JButton();
		removeButton.setAction(new RemoveAction<T>(this));
		removeButton.setText("Remove");
		this.add(this.getComboBox());
		this.add(editButton);
		this.add(addButton);
		this.add(removeButton);
	}

	/**
	 * Sets the items.
	 *
	 * @param items
	 *            the new items
	 */
	public void setItems(Collection<T> items) {
		JComboBox<T> comboBox = this.getComboBox();
		comboBox.removeAllItems();
		for (T item : items) {
			comboBox.addItem(item);
		}
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public Collection<T> getItems() {
		Collection<T> items = new ArrayList<T>();
		int count = this.getComboBox().getItemCount();
		for (int i = 0; i < count; i++) {
			items.add(this.getComboBox().getItemAt(i));
		}
		return items;
	}

	/**
	 * Sets the editor caption.
	 *
	 * @param caption
	 *            the new editor caption
	 */
	public void setEditorCaption(String caption) {
		this.setEditListCaption(caption);
	}

	/**
	 * @return the comboBox
	 */
	public JComboBox<T> getComboBox() {
		return comboBox;
	}

	/**
	 * @return the itemEditorFactory
	 */
	public ItemEditorFactory<T> getItemEditorFactory() {
		return itemEditorFactory;
	}

	/**
	 * @return the editListCaption
	 */
	public String getEditListCaption() {
		return editListCaption;
	}

	/**
	 * @param editListCaption the editListCaption to set
	 */
	public void setEditListCaption(String editListCaption) {
		this.editListCaption = editListCaption;
	}
}
