package org.loboevolution.gui;

import javax.swing.JComponent;

/**
 * <p>Abstract AbstractItemEditor class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
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
	 */
	public abstract void validateItem();
}
