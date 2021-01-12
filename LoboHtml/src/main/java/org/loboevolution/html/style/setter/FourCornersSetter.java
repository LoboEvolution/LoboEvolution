/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */

package org.loboevolution.html.style.setter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

public class FourCornersSetter implements SubPropertySetter {
	private final String prefix;
	private final String property;
	private final String suffix;

	public FourCornersSetter(String property, String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.property = property;
	}

	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}

	@Override
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration, boolean important) {
		
		if (Strings.isNotBlank(newValue)) {
			properties.setPropertyValueLCAlt(this.property, newValue, important);
			
			final String[] array = HtmlValues.splitCssValue(newValue);
			switch (array.length) {
			case 1:
				final String value = array[0];
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, value, important);
				break;
			case 2:
				properties.setPropertyValueLCAlt(prefix + "top" + suffix,  array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix,  array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix,  array[0], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
				break;
			case 3:
				properties.setPropertyValueLCAlt(prefix + "top" + suffix,  array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix,  array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
				break;
			case 4:
				properties.setPropertyValueLCAlt(prefix + "top" + suffix,  array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix,  array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[3], important);
			default:
				break;
			}
		}
	}
}