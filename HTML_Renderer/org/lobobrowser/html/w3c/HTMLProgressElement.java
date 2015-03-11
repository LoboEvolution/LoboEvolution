package org.lobobrowser.html.w3c;

import org.w3c.dom.NodeList;


/**
 * The Interface HTMLProgressElement.
 */
public interface HTMLProgressElement extends HTMLElement {
	// HTMLProgressElement
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public float getValue();

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(float value);

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public float getMax();

	/**
	 * Sets the max.
	 *
	 * @param max the new max
	 */
	public void setMax(float max);

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public float getPosition();

	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/**
	 * Gets the labels.
	 *
	 * @return the labels
	 */
	public NodeList getLabels();
}
