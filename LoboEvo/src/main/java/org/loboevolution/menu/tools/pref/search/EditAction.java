package org.loboevolution.menu.tools.pref.search;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.gui.SwingTasks;

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

	private transient ItemListControl<T> item;

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
		final Frame parentFrame = SwingTasks.getFrame(this.item);
		ItemEditorDialog<T> dialog;
		if (parentFrame != null) {
			dialog = new ItemEditorDialog<T>(parentFrame, this.item.getItemEditorFactory());
		} else {
			final Dialog parentDialog = SwingTasks.getDialog(this.item);
			dialog = new ItemEditorDialog<T>(parentDialog, this.item.getItemEditorFactory());
		}
		dialog.setModal(true);
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
	private void addItem(T itm) {
		this.item.getComboBox().addItem(itm);
		this.item.getComboBox().setSelectedItem(itm);
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
	 * @param item the item
	 */
	private void replaceSelectedItem(T itm) {
		final int index = this.item.getComboBox().getSelectedIndex();
		if (index != -1) {
			this.item.getComboBox().removeItemAt(index);
		}
		this.item.getComboBox().addItem(itm);
		this.item.getComboBox().setSelectedItem(itm);
	}
}
