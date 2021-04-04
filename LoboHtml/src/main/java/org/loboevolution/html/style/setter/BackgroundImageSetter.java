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

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Urls;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;

import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;

/**
 * <p>BackgroundImageSetter class.</p>
 *
 *
 *
 */
public class BackgroundImageSetter implements SubPropertySetter {
	
	private static final Logger logger = Logger.getLogger(BackgroundImageSetter.class.getName());

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
		String baseHref = null;
		String finalValue;
		if (declaration != null) {
			final AbstractCSSRuleImpl rule = declaration.getParentRule();
			if (rule != null) {
				final CSSStyleSheetImpl sheet = rule.getParentStyleSheet();
				final CSSStyleSheetImpl ssheet = sheet;
				baseHref = ssheet.getHref();
			}
		}
		if (baseHref == null) {
			baseHref = properties.getContext().getDocumentBaseURI();
		}
		final String start = "url(";
		if (newValue == null || !newValue.toLowerCase().startsWith(start)) {
			finalValue = newValue;
		} else {
			final int startIdx = start.length();
			final int closingIdx = newValue.lastIndexOf(')');
			if (closingIdx == -1) {
				finalValue = newValue;
			} else {
				final String quotedUri = newValue.substring(startIdx, closingIdx);
				final String tentativeUri = HtmlValues.unquoteAndUnescape(quotedUri);
				if (baseHref == null) {
					finalValue = newValue;
				} else {
					try {
						final URL styleUrl = Urls.createURL(null, baseHref);
						if (tentativeUri.contains("data:image")) {
							finalValue = tentativeUri;
						} else {
							finalValue = "url("
									+ HtmlValues.quoteAndEscape(Urls.createURL(styleUrl, tentativeUri).toExternalForm())
									+ ")";
						}

					} catch (final Exception mfu) {
						logger.log(Level.WARNING,
								"Unable to create URL for URI=[" + tentativeUri + "], with base=[" + baseHref + "].",
								mfu);
						finalValue = newValue;
					}
				}
			}
		}
		properties.setPropertyValueLCAlt(BACKGROUND_IMAGE, finalValue, important);
	}
}
