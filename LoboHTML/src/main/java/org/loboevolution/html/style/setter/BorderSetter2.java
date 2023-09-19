/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
