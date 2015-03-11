package org.lobobrowser.html.w3c;


/**
 * The Interface HTMLOptionElement_Constructor.
 */
public interface HTMLOptionElement_Constructor {
	// Constructor
	/**
	 * Creates the instance.
	 *
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance();

	/**
	 * Creates the instance.
	 *
	 * @param text the text
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text);

	/**
	 * Creates the instance.
	 *
	 * @param text the text
	 * @param value the value
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text, String value);

	/**
	 * Creates the instance.
	 *
	 * @param text the text
	 * @param value the value
	 * @param defaultSelected the default selected
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text, String value,
			boolean defaultSelected);

	/**
	 * Creates the instance.
	 *
	 * @param text the text
	 * @param value the value
	 * @param defaultSelected the default selected
	 * @param selected the selected
	 * @return the HTML option element
	 */
	public HTMLOptionElement createInstance(String text, String value,
			boolean defaultSelected, boolean selected);
}
