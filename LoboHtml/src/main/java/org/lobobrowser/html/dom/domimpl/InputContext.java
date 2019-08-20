/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
package org.lobobrowser.html.dom.domimpl;

public interface InputContext {
	void blur();

	void click();

	void focus();

	boolean getChecked();

	int getCols();

	int getControlSize();

	boolean getDisabled();

	java.io.File getFileValue();

	int getMaxLength();

	String getName();

	boolean getReadOnly();

	int getRows();

	int getSelectedIndex();

	int getTabIndex();

	String getValue();

	String[] getValues();

	int getVisibleSize();

	void resetInput();

	void select();

	void setChecked(boolean checked);

	void setCols(int cols);

	void setControlSize(int size);

	void setDisabled(boolean disabled);

	void setMaxLength(int maxLength);

	void setName(String name);

	void setReadOnly(boolean readOnly);

	void setRows(int rows);

	void setSelectedIndex(int value);

	void setTabIndex(int tabIndex);

	void setValue(String value);

	void setVisibleSize(int value);
}
