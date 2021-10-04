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

import org.loboevolution.html.CSSValues;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.ColorFactory;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

/**
 * <p>BackgroundSetter class.</p>
 *
 *
 *
 */
public class BackgroundSetter implements SubPropertySetter {
	
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
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration,
			boolean important) {
		properties.setPropertyValueLCAlt(BACKGROUND, newValue, important);
		if (newValue != null && newValue.length() > 0) {
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
				properties.setPropertyValueLCAlt(BACKGROUND_COLOR, color, important);
			}
			if (image != null) {
				properties.setPropertyValueProcessed(BACKGROUND_IMAGE, image, declaration, important);
			}
			if (backgroundRepeat != null) {
				properties.setPropertyValueLCAlt(BACKGROUND_REPEAT, backgroundRepeat, important);
			}
			if (position != null) {
				properties.setPropertyValueLCAlt(BACKGROUND_POSITION, position, important);
			}
		}
	}

}
