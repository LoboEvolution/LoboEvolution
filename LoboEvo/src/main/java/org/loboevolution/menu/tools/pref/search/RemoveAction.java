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
