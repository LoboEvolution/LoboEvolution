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
import org.loboevolution.laf.ColorFactory;

/**
 * <p>BorderSetter2 class.</p>
 */
public class BorderSetter2 implements SubPropertySetter {

	private final String name;

	/**
	 * <p>Constructor for BorderSetter2.</p>
	 *
	 * @param baseName a {@link java.lang.String} object.
	 */
	public BorderSetter2(String baseName) {
		this.name = baseName;
	}

	/** {@inheritDoc} */
	@Override
	public void changeValue(CSSStyleDeclaration declaration, String value) {
		CSSStyleDeclarationImpl properties = (CSSStyleDeclarationImpl) declaration;
		properties.setProperty(this.name, value);
		if (Strings.isNotBlank(value)) {
			final String[] array = HtmlValues.splitCssValue(value);
			String color = null;
			String style = null;
			String width = null;
			for (final String token : array) {
				if (HtmlValues.isBorderStyle(token)) {
					style = token;
				} else if (ColorFactory.getInstance().isColor(token)) {
					color = token;
				} else {
					width = token;
				}
			}
			final String name = this.name;
			if (style != null) {
				properties.setProperty(name + "-style", style);
				if (color == null) {
					color = "black";
				}
				
				if (width == null) {
					width = "2px";
				}
			}
			
			if (color != null) {
				properties.setProperty(name + "-color", color);
			}
			if (width != null) {
				properties.setProperty(name + "-width", width);
			}				
		}
	}
}
