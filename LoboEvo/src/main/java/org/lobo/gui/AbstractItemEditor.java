package org.lobo.gui;

import javax.swing.JComponent;

public abstract class AbstractItemEditor<T> extends JComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public abstract T getItem();

	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public abstract void setItem(T item);

	/**
	 * Validate item.
	 *
	 * @throws ValidationException the validation exception
	 */
	public abstract void validateItem();
}
