/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html;

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
     * Gets the name of the input.
     *
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns true if the form input holds a text value.
     *
     * @return true, if is text
     */
    public boolean isText() {
        return this.textValue != null;
    }

    /**
     * Returns true if the form input holds a file value.
     *
     * @return true, if is file
     */
    public boolean isFile() {
        return this.fileValue != null;
    }

    /**
     * Gets the text value of the form input. If the form input does not hold a
     * text value, this method should not be called.
     *
     * @return the text value
     * @see #isText()
     */
    public String getTextValue() {
        return this.textValue;
    }

    /**
     * Gets the file value of the form input. If the form input does not hold a
     * file value, this method should not be called.
     *
     * @return the file value
     * @see #isFile()
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
        return "FormInput[name=" + this.name + ",textValue=" + this.textValue
                + "]";
    }
}
