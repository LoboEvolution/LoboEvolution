/*
    GNU GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General Public License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.primary.gui;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractAction;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

public class ItemListControl<T> extends JComponent {

	private static final long serialVersionUID = 1L;
	private final JComboBox<T> comboBox;
	private final ItemEditorFactory<T> itemEditorFactory;

	public ItemListControl(ItemEditorFactory<T> ief) {
		this.itemEditorFactory = ief;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.comboBox = new JComboBox<T>();
		this.comboBox.setPreferredSize(new Dimension(100, 24));
		this.comboBox.setEditable(false);
		JButton editButton = new JButton();
		editButton.setAction(new EditAction(false));
		editButton.setText("Edit");
		JButton addButton = new JButton();
		addButton.setAction(new EditAction(true));
		addButton.setText("Add");
		JButton removeButton = new JButton();
		removeButton.setAction(new RemoveAction());
		removeButton.setText("Remove");
		this.add(this.comboBox);
		this.add(editButton);
		this.add(addButton);
		this.add(removeButton);
	}

	public void setItems(Collection<T> items) {
		JComboBox<T> comboBox = this.comboBox;
		comboBox.removeAllItems();
		for (T item : items) {
			comboBox.addItem(item);
		}
	}

	@SuppressWarnings("unchecked")
	private T getSelectedItem() {
		return (T) this.comboBox.getSelectedItem();
	}

	private void addItem(T item) {
		this.comboBox.addItem(item);
		this.comboBox.setSelectedItem(item);
	}

	private void replaceSelectedItem(T item) {
		int index = this.comboBox.getSelectedIndex();
		if (index != -1) {
			this.comboBox.removeItemAt(index);
		}
		this.comboBox.addItem(item);
		this.comboBox.setSelectedItem(item);
	}

	private void removeSelectedItem() {
		int index = this.comboBox.getSelectedIndex();
		if (index != -1) {
			this.comboBox.removeItemAt(index);
		}
	}

	public Collection<T> getItems() {
		Collection<T> items = new ArrayList<T>();
		int count = this.comboBox.getItemCount();
		for (int i = 0; i < count; i++) {
			items.add((T) this.comboBox.getItemAt(i));
		}
		return items;
	}

	private String editListCaption;

	public void setEditorCaption(String caption) {
		this.editListCaption = caption;
	}

	private class RemoveAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.showConfirmDialog(ItemListControl.this,
					"Are you sure you want to remove the selected item?",
					"Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				removeSelectedItem();
			}
		}
	}

	private class EditAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final boolean add;

		public EditAction(boolean add) {
			this.add = add;
		}

		public void actionPerformed(ActionEvent e) {
			Frame parentFrame = SwingTasks.getFrame(ItemListControl.this);
			ItemEditorDialog<T> dialog;
			if (parentFrame != null) {
				dialog = new ItemEditorDialog<T>(parentFrame, itemEditorFactory);
			} else {
				Dialog parentDialog = SwingTasks
						.getDialog(ItemListControl.this);
				dialog = new ItemEditorDialog<T>(parentDialog,
						itemEditorFactory);
			}
			dialog.setModal(true);
			dialog.setTitle(this.add ? "Add Item" : "Edit Item");
			dialog.setCaption(editListCaption);
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
	}
}
