/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.input;

import java.io.File;

/**
 * The FormInput class contains the state of an HTML form input
 * item.
 *
 *
 *
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

