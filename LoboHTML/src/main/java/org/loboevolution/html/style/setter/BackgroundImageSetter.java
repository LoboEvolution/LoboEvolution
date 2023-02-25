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

import com.gargoylesoftware.css.dom.AbstractCSSRuleImpl;
import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import org.loboevolution.common.Urls;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.HtmlValues;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>BackgroundImageSetter class.</p>
 */
public class BackgroundImageSetter implements SubPropertySetter {
	
	private static final Logger logger = Logger.getLogger(BackgroundImageSetter.class.getName());

	/** {@inheritDoc} */
	@Override
	public void changeValue(CSSStyleDeclaration declaration, String newValue) {
		String baseHref = null;
		String finalValue;
		CSSStyleDeclarationImpl props = (CSSStyleDeclarationImpl) declaration;

		final AbstractCSSRuleImpl rule = (AbstractCSSRuleImpl) props.getParentRule();
		if (rule != null) {
			final CSSStyleSheetImpl sheet = rule.getParentStyleSheet();
			final CSSStyleSheetImpl ssheet = sheet;
			baseHref = ssheet.getHref();
		}

		if (baseHref == null) {
			baseHref = props.getContext() != null ? props.getContext().getDocumentBaseURI() : null;
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
		declaration.setProperty(BACKGROUND_IMAGE, finalValue);
	}
}
