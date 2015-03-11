package org.lobobrowser.html.w3c;


/**
 * The Interface DOMStringMap.
 */
public interface DOMStringMap {
	// DOMStringMap
	/**
	 * Gets the element.
	 *
	 * @param name the name
	 * @return the element
	 */
	public String getElement(String name);

	/**
	 * Sets the element.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void setElement(String name, String value);

	/**
	 * Creates the element.
	 *
	 * @param name the name
	 * @param value the value
	 */
	public void createElement(String name, String value);

	/**
	 * Delete element.
	 *
	 * @param name the name
	 */
	public void deleteElement(String name);
}
