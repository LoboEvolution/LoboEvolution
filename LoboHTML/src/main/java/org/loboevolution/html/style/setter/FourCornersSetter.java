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
