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

import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;

/**
 * <p>BorderSetter1 class.</p>
 */
public class BorderSetter1 implements SubPropertySetter {

	/** {@inheritDoc} */
	@Override
	public void changeValue(CSSStyleDeclaration declaration, String newValue) {
		CSSStyleDeclarationImpl properties = (CSSStyleDeclarationImpl) declaration;
		properties.setProperty(BORDER, newValue);
		properties.setPropertyValueProcessed(BORDER_TOP, newValue, false, true);
		properties.setPropertyValueProcessed(BORDER_LEFT, newValue, false, true);
		properties.setPropertyValueProcessed(BORDER_BOTTOM, newValue, false, true);
		properties.setPropertyValueProcessed(BORDER_RIGHT, newValue, false, true);
	}
}
