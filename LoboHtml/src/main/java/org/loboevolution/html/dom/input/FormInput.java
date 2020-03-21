package org.loboevolution.html.dom.input;

import java.io.File;

/**
 * The <code>FormInput</code> class contains the state of an HTML form input
 * item.
 */
public class FormInput {

	/** The Constant EMPTY_ARRAY. */
	public static final FormInput[] EMPTY_ARRAY = new FormInput[0];

	/** The name. */
	private final String name;

	/** The text value. */
	private final String textValue;

	/** The file value. */
	private final File[] fileValue;

	/**
	 * Constructs a <code>FormInput</code> with a text value.
	 *
	 * @param name
	 *            The name of the input.
	 * @param value
	 *            The value of the input.
	 */
	public FormInput(String name, String value) {
		super();
		this.name = name;
		this.textValue = value;
		this.fileValue = null;
	}

	/**
	 * Constructs a <code>FormInput</code> with a file value.
	 *
	 * @param name
	 *            The name of the input.
	 * @param value
	 *            The value of the input.
	 */
	public FormInput(String name, File[] value) {
		this.name = name;
		this.textValue = null;
		this.fileValue = value;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Checks if is text.
	 *
	 * @return true, if is text
	 */
	public boolean isText() {
		return this.textValue != null;
	}

	/**
	 * Checks if is file.
	 *
	 * @return true, if is file
	 */
	public boolean isFile() {
		return this.fileValue != null;
	}

	/**
	 * Gets the text value.
	 *
	 * @return the text value
	 */
	public String getTextValue() {
		return this.textValue;
	}

	/**
	 * Gets the file value.
	 *
	 * @return the file value
	 */
	public File[] getFileValue() {
		return this.fileValue;
	}

	/**
	 * Shows a string representation of the <code>FormInput</code> that may be
	 * useful in debugging.
	 *
	 * @return the string
	 * @see #getTextValue()
	 */
	@Override
	public String toString() {
		return "FormInput[name=" + this.name + ",textValue=" + this.textValue + "]";
	}
}

