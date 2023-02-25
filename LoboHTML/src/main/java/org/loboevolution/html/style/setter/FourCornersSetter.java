/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.html.style.setter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.HtmlValues;

/**
 * <p>FourCornersSetter class.</p>
 */
public class FourCornersSetter implements SubPropertySetter {
	
	private final String prefix;
	private final String property;
	private final String suffix;

	/**
	 * <p>Constructor for FourCornersSetter.</p>
	 *
	 * @param property a {@link java.lang.String} object.
	 * @param prefix a {@link java.lang.String} object.
	 * @param suffix a {@link java.lang.String} object.
	 */
	public FourCornersSetter(String property, String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.property = property;
	}

	/** {@inheritDoc} */
	@Override
	public void changeValue(CSSStyleDeclaration declaration, String newValue) {
		CSSStyleDeclarationImpl properties = (CSSStyleDeclarationImpl) declaration;
		
		if (Strings.isNotBlank(newValue)) {
			properties.setProperty(this.property, newValue);
			
			final String[] array = HtmlValues.splitCssValue(newValue);
			switch (array.length) {
			case 1:
				final String value = array[0];
				properties.setProperty(prefix + "top" + suffix, value);
				properties.setProperty(prefix + "right" + suffix, value);
				properties.setProperty(prefix + "bottom" + suffix, value);
				properties.setProperty(prefix + "left" + suffix, value);
				break;
			case 2:
				properties.setProperty(prefix + "top" + suffix,  array[0]);
				properties.setProperty(prefix + "right" + suffix,  array[1]);
				properties.setProperty(prefix + "bottom" + suffix,  array[0]);
				properties.setProperty(prefix + "left" + suffix, array[1]);
				break;
			case 3:
				properties.setProperty(prefix + "top" + suffix,  array[0]);
				properties.setProperty(prefix + "right" + suffix,  array[1]);
				properties.setProperty(prefix + "bottom" + suffix, array[2]);
				properties.setProperty(prefix + "left" + suffix, array[1]);
				break;
			case 4:
				properties.setProperty(prefix + "top" + suffix,  array[0]);
				properties.setProperty(prefix + "right" + suffix,  array[1]);
				properties.setProperty(prefix + "bottom" + suffix, array[2]);
				properties.setProperty(prefix + "left" + suffix, array[3]);
			default:
				break;
			}
		}
	}
}
