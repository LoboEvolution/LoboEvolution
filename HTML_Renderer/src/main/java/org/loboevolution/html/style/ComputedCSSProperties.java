/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.style;

/**
 * The Class ComputedCSSProperties.
 */
public class ComputedCSSProperties extends AbstractCSSProperties {

	/**
	 * Instantiates a new computed cs s2 properties.
	 *
	 * @param context
	 *            the context
	 */
	public ComputedCSSProperties(CSSPropertiesContext context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.style.AbstractCSSProperties#setPropertyValueLC(java
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
