/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.dombl;

import java.io.File;

/**
 * The Interface InputContext.
 */
public interface InputContext {

	/**
	 * Gets the checked.
	 *
	 * @return the checked
	 */
	boolean getChecked();

	/**
	 * Sets the checked.
	 *
	 * @param checked
	 *            the new checked
	 */
	void setChecked(boolean checked);

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled
	 *            the new disabled
	 */
	void setDisabled(boolean disabled);

	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	int getMaxLength();

	/**
	 * Sets the max length.
	 *
	 * @param maxLength
	 *            the new max length
	 */
	void setMaxLength(int maxLength);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	void setName(String name);

	/**
	 * Gets the read only.
	 *
	 * @return the read only
	 */
	boolean getReadOnly();

	/**
	 * Sets the read only.
	 *
	 * @param readOnly
	 *            the new read only
	 */
	void setReadOnly(boolean readOnly);

	/**
	 * Gets the control size.
	 *
	 * @return the control size
	 */
	int getControlSize();

	/**
	 * Sets the control size.
	 *
	 * @param size
	 *            the new control size
	 */
	void setControlSize(int size);

	/**
	 * Gets the tab index.
	 *
	 * @return the tab index
	 */
	int getTabIndex();

	/**
	 * Sets the tab index.
	 *
	 * @param tabIndex
	 *            the new tab index
	 */
	void setTabIndex(int tabIndex);

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	String getValue();

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	String[] getValues();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	void setValue(String value);

	/**
	 * Blur.
	 */
	void blur();

	/**
	 * Focus.
	 */
	void focus();

	/**
	 * Select.
	 */
	void select();

	/**
	 * Click.
	 */
	void click();

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	int getRows();

	/**
	 * Gets the cols.
	 *
	 * @return the cols
	 */
	int getCols();

	/**
	 * Sets the rows.
	 *
	 * @param rows
	 *            the new rows
	 */
	void setRows(int rows);

	/**
	 * Sets the cols.
	 *
	 * @param cols
	 *            the new cols
	 */
	void setCols(int cols);

	/**
	 * Gets the selected index.
	 *
	 * @return the selected index
	 */
	int getSelectedIndex();

	/**
	 * Sets the selected index.
	 *
	 * @param value
	 *            the new selected index
	 */
	void setSelectedIndex(int value);

	/**
	 * Gets the visible size.
	 *
	 * @return the visible size
	 */
	int getVisibleSize();

	/**
	 * Sets the visible size.
	 *
	 * @param value
	 *            the new visible size
	 */
	void setVisibleSize(int value);

	/**
	 * Gets the file value.
	 *
	 * @return the file value
	 */
	File[] getFileValue();

	/**
	 * Reset input.
	 */
	void resetInput();

	/**
	 * Sets the min.
	 *
	 * @param value
	 *            the new min
	 */
	void setMin(int value);

	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	int getMin();

	/**
	 * Sets the max.
	 *
	 * @param value
	 *            the new max
	 */
	void setMax(int value);

	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	int getMax();

	/**
	 * Sets the pattern.
	 *
	 * @param value
	 *            the new pattern
	 */
	void setPattern(String value);

	/**
	 * Gets the pattern.
	 *
	 * @return the pattern
	 */
	String getPattern();
}
