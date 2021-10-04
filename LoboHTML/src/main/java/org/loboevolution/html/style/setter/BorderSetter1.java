/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

import org.loboevolution.html.style.AbstractCSSProperties;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

/**
 * <p>BorderSetter1 class.</p>
 *
 *
 *
 */
public class BorderSetter1 implements SubPropertySetter {

	/**
	 * <p>changeValue.</p>
	 *
	 * @param properties a {@link org.loboevolution.html.style.AbstractCSSProperties} object.
	 * @param newValue a {@link java.lang.String} object.
	 * @param declaration a {@link com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl} object.
	 */
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}

	/** {@inheritDoc} */
	@Override
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration, boolean important) {
		properties.setPropertyValueLCAlt(BORDER, newValue, important);
		properties.setPropertyValueProcessed(BORDER_TOP, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_LEFT, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_BOTTOM, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_RIGHT, newValue, declaration, important);
	}
}
