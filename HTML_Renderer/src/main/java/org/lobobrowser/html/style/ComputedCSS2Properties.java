/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
package org.lobobrowser.html.style;

/**
 * The Class ComputedCSS2Properties.
 */
public class ComputedCSS2Properties extends AbstractCSS2Properties {

	/**
	 * Instantiates a new computed cs s2 properties.
	 *
	 * @param context
	 *            the context
	 */
	public ComputedCSS2Properties(CSS2PropertiesContext context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.style.AbstractCSS2Properties#setPropertyValueLC(java
	 * .lang.String, java.lang.String)
	 */
	@Override
	protected void setPropertyValueLC(String lowerCaseName, String value) {
		throw new IllegalAccessError("Style properties cannot be set in this instance.");
	}

	/**
	 * Internal set lc.
	 *
	 * @param lowerCaseName
	 *            the lower case name
	 * @param value
	 *            the value
	 */
	public void internalSetLC(String lowerCaseName, String value) {
		// Should only be called right after creation of the CSS object.
		// Properties need to be "unimportant" otherwise they won't get
		// overridden.
		super.setPropertyValueLCAlt(lowerCaseName, value, false);
	}
}
