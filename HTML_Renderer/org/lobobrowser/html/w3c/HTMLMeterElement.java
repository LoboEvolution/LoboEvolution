package org.lobobrowser.html.w3c;

import org.w3c.dom.NodeList;


/**
 * The Interface HTMLMeterElement.
 */
public interface HTMLMeterElement extends HTMLElement {
	// HTMLMeterElement
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
	 * Gets the min.
	 *
	 * @return the min
	 */
	public float getMin();

	/**
	 * Sets the min.
	 *
	 * @param min the new min
	 */
	public void setMin(float min);

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
	 * Gets the low.
	 *
	 * @return the low
	 */
	public float getLow();

	/**
	 * Sets the low.
	 *
	 * @param low the new low
	 */
	public void setLow(float low);

	/**
	 * Gets the high.
	 *
	 * @return the high
	 */
	public float getHigh();

	/**
	 * Sets the high.
	 *
	 * @param high the new high
	 */
	public void setHigh(float high);

	/**
	 * Gets the optimum.
	 *
	 * @return the optimum
	 */
	public float getOptimum();

	/**
	 * Sets the optimum.
	 *
	 * @param optimum the new optimum
	 */
	public void setOptimum(float optimum);

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
