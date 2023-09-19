/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.input;

import java.io.File;

/**
 * The FormInput class contains the state of an HTML form input item.
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
	 * Constructs a FormInput with a text value.
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
	 * Constructs a FormInput with a file value.
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
	 * {@inheritDoc}
	 *
	 * Shows a string representation of the FormInput that may be
	 * useful in debugging.
	 * @see #getTextValue()
	 */
	@Override
	public String toString() {
		return "FormInput[name=" + this.name + ",textValue=" + this.textValue + "]";
	}
}

