package org.loboevolution.menu.tools.pref.search;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import org.loboevolution.gui.ItemEditorFactory;

import com.jtattoo.plaf.lobo.LoboButton;

/**
 * The Class ItemListControl.
 *
 * @param <T> the generic type
 * @author utente
 * @version $Id: $Id
 */
public class ItemListControl<T> extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The combo box. */
	private final JComboBox<T> comboBox;

	/** The edit list caption. */
	private String editListCaption;

	/** The item editor factory. */
	private final transient ItemEditorFactory<T> itemEditorFactory;

	/**
	 * Instantiates a new item list control.
	 *
	 * @param ief the ief
	 */
	public ItemListControl(ItemEditorFactory<T> ief) {
		this.itemEditorFactory = ief;
		this.comboBox = new JComboBox<T>();
		createAndShowGUI();
	}

	private void createAndShowGUI() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.getComboBox().setEditable(false);
		final LoboButton setDefaultButton = new LoboButton();
		setDefaultButton.setAction(new SetAsDefaultAction<T>(this));
		setDefaultButton.setText("Set Default");
		final LoboButton editButton = new LoboButton();
		editButton.setAction(new EditAction<T>(false, this));
		editButton.setText("Edit");
		final LoboButton addButton = new LoboButton();
		addButton.setAction(new EditAction<T>(true, this));
		addButton.setText("Add");
		final LoboButton removeButton = new LoboButton();
		removeButton.setAction(new RemoveAction<T>(this));
		removeButton.setText("Remove");
		this.add(getComboBox());
		this.add(setDefaultButton);
		this.add(editButton);
		this.add(addButton);
		this.add(removeButton);
	}

	/**
	 * <p>Getter for the field comboBox.</p>
	 *
	 * @return the comboBox
	 */
	public JComboBox<T> getComboBox() {
		return this.comboBox;
	}

	/**
	 * <p>Getter for the field editListCaption.</p>
	 *
	 * @return the editListCaption
	 */
	public String getEditListCaption() {
		return this.editListCaption;
	}

	/**
	 * <p>Getter for the field itemEditorFactory.</p>
	 *
	 * @return the itemEditorFactory
	 */
	public ItemEditorFactory<T> getItemEditorFactory() {
		return this.itemEditorFactory;
	}

	/**
	 * Gets the items.
	 *
	 * @return the items
	 */
	public Collection<T> getItems() {
		final Collection<T> items = new ArrayList<T>();
		final int count = this.getComboBox().getItemCount();
		for (int i = 0; i < count; i++) {
			items.add(this.getComboBox().getItemAt(i));
		}
		return items;
	}

	/**
	 * <p>Setter for the field editListCaption.</p>
	 *
	 * @param editListCaption the editListCaption to set
	 */
	public void setEditListCaption(String editListCaption) {
		this.editListCaption = editListCaption;
	}

	/**
	 * Sets the editor caption.
	 *
	 * @param caption the new editor caption
	 */
	public void setEditorCaption(String caption) {
		this.setEditListCaption(caption);
	}

	/**
	 * Sets the items.
	 *
	 * @param items the new items
	 */
	public void setItems(Collection<T> items) {
		final JComboBox<T> comboBox = this.getComboBox();
		comboBox.removeAllItems();
		for (final T item : items) {
			comboBox.addItem(item);
		}
	}
}
