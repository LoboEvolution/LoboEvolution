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
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.ColorFactory;

/**
 * <p>BackgroundSetter class.</p>
 */
public class BackgroundSetter implements SubPropertySetter {

	/** {@inheritDoc} */
	@Override
	public void changeValue(CSSStyleDeclaration declaration, String newValue) {
		CSSStyleDeclarationImpl properties = (CSSStyleDeclarationImpl) declaration;
		properties.setProperty(BACKGROUND, newValue);
		if (Strings.isNotBlank(newValue)) {
			final String[] tokens = HtmlValues.splitCssValue(newValue);
			boolean hasXPosition = false;
			boolean hasYPosition = false;
			String color = null;
			String image = null;
			String backgroundRepeat = null;
			String position = null;
			for (final String token : tokens) {
				if (ColorFactory.getInstance().isColor(token) || CSSValues.INITIAL.equals(CSSValues.get(token))) {
					color = token;
				} else if (HtmlValues.isUrl(token) || HtmlValues.isGradient(token)) {
					image = token;
				} else if (HtmlValues.isBackgroundRepeat(token)) {
					backgroundRepeat = token;
				} else if (HtmlValues.isBackgroundPosition(token)) {
					if (hasXPosition && !hasYPosition) {
						position += " " + token;
						hasYPosition = true;
					} else {
						hasXPosition = true;
						position = token;
					}
				}
			}
			if (color != null) {
				if (CSSValues.INITIAL.equals(CSSValues.get(color))) {
					color = "white";
				}
				properties.setProperty(BACKGROUND_COLOR, color);
			}
			if (image != null) {
				properties.setPropertyValueProcessed(BACKGROUND_IMAGE, image, false);
			}
			if (backgroundRepeat != null) {
				properties.setProperty(BACKGROUND_REPEAT, backgroundRepeat);
			}
			if (position != null) {
				properties.setProperty(BACKGROUND_POSITION, position);
			}
		}
	}
}
