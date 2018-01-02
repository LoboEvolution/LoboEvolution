/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.w3c.html;

/**
 * The Interface HTMLOptionElement.
 */
public interface HTMLOptionElement extends HTMLElement {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLElement#getDisabled()
	 */
	// HTMLOptionElement
	@Override
	public boolean getDisabled();

	/**
	 * Sets the disabled.
	 *
	 * @param disabled
	 *            the new disabled
	 */
	public void setDisabled(boolean disabled);

	/**
	 * Gets the form.
	 *
	 * @return the form
	 */
	public HTMLFormElement getForm();

	/*
	 * (non-Javadoc)
	 * 
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

	/**
	 * Gets the default selected.
	 *
	 * @return the default selected
	 */
	public boolean getDefaultSelected();

	/**
	 * Sets the default selected.
	 *
	 * @param defaultSelected
	 *            the new default selected
	 */
	public void setDefaultSelected(boolean defaultSelected);

	/**
	 * Gets the selected.
	 *
	 * @return the selected
	 */
	public boolean getSelected();

	/**
	 * Sets the selected.
	 *
	 * @param selected
	 *            the new selected
	 */
	public void setSelected(boolean selected);

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue();

	/**
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(String value);

	/**
	 * Gets the text.
	 *
	 * @return the text
	 */
	public String getText();

	/**
	 * Sets the text.
	 *
	 * @param text
	 *            the new text
	 */
	public void setText(String text);

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex();
}
