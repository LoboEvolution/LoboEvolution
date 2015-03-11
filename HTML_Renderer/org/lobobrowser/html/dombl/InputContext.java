/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
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
	public boolean getChecked();

	/**
	 * Sets the checked.
	 *
	 * @param checked the new checked
	 */
	public void setChecked(boolean checked);

	/**
	 * Gets the disabled.
	 *
	 * @return the disabled
	 */
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled the new disabled
	 */
	public void setDisabled(boolean disabled);

	/**
	 * Gets the max length.
	 *
	 * @return the max length
	 */
	public int getMaxLength();

	/**
	 * Sets the max length.
	 *
	 * @param maxLength the new max length
	 */
	public void setMaxLength(int maxLength);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name);

	/**
	 * Gets the read only.
	 *
	 * @return the read only
	 */
	public boolean getReadOnly();

	/**
	 * Sets the read only.
	 *
	 * @param readOnly the new read only
	 */
	public void setReadOnly(boolean readOnly);

	/**
	 * Gets the control size.
	 *
	 * @return the control size
	 */
	public int getControlSize();

	/**
	 * Sets the control size.
	 *
	 * @param size the new control size
	 */
	public void setControlSize(int size);

	/**
	 * Gets the tab index.
	 *
	 * @return the tab index
	 */
	public int getTabIndex();

	/**
	 * Sets the tab index.
	 *
	 * @param tabIndex the new tab index
	 */
	public void setTabIndex(int tabIndex);

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public String[] getValues();

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value);

	/**
	 * Blur.
	 */
	public void blur();

	/**
	 * Focus.
	 */
	public void focus();

	/**
	 * Select.
	 */
	public void select();

	/**
	 * Click.
	 */
	public void click();

	/**
	 * Gets the rows.
	 *
	 * @return the rows
	 */
	public int getRows();

	/**
	 * Gets the cols.
	 *
	 * @return the cols
	 */
	public int getCols();

	/**
	 * Sets the rows.
	 *
	 * @param rows the new rows
	 */
	public void setRows(int rows);

	/**
	 * Sets the cols.
	 *
	 * @param cols the new cols
	 */
	public void setCols(int cols);

	/**
	 * Gets the selected index.
	 *
	 * @return the selected index
	 */
	public int getSelectedIndex();

	/**
	 * Sets the selected index.
	 *
	 * @param value the new selected index
	 */
	public void setSelectedIndex(int value);

	/**
	 * Gets the visible size.
	 *
	 * @return the visible size
	 */
	public int getVisibleSize();

	/**
	 * Sets the visible size.
	 *
	 * @param value the new visible size
	 */
	public void setVisibleSize(int value);

	/**
	 * Gets the file value.
	 *
	 * @return the file value
	 */
	public File[] getFileValue();

	/**
	 * Reset input.
	 */
	public void resetInput();
}
