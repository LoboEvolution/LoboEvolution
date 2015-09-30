/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

package org.lobobrowser.w3c.html;


/**
 * The Interface HTMLCommandElement.
 */
public interface HTMLCommandElement extends HTMLElement {
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	// HTMLCommandElement
	public String getType();

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the new type
	 */
	public void setType(String type);

	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getLabel()
	 */
	@Override
	public String getLabel();

	/**
	 * Sets the label.
	 *
	 * @param label
	 *            the new label
	 */
	public void setLabel(String label);

	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getIcon()
	 */
	@Override
	public String getIcon();

	/**
	 * Sets the icon.
	 *
	 * @param icon
	 *            the new icon
	 */
	public void setIcon(String icon);

	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getDisabled()
	 */
	@Override
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled);

	/* (non-Javadoc)
	 * @see org.lobobrowser.w3c.html.HTMLElement#getChecked()
	 */
	@Override
	public boolean getChecked();

	/**
	 * Sets the checked.
	 *
	 * @param checked
	 *            the new checked
	 */
	public void setChecked(boolean checked);

	/**
	 * Gets the radiogroup.
	 *
	 * @return the radiogroup
	 */
	public String getRadiogroup();

	/**
	 * Sets the radiogroup.
	 *
	 * @param radiogroup
	 *            the new radiogroup
	 */
	public void setRadiogroup(String radiogroup);
}
