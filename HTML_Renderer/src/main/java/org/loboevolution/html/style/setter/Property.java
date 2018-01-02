package org.loboevolution.html.style.setter;

public class Property {

	/** The value. */
	public final String value;

	/** The important. */
	public final boolean important;

	/**
	 * Instantiates a new property.
	 *
	 * @param value
	 *            the value
	 * @param important
	 *            the important
	 */
	public Property(final String value, final boolean important) {
		this.value = value;
		this.important = important;
	}
}