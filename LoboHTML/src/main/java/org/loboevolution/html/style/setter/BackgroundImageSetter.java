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

import org.htmlunit.cssparser.dom.AbstractCSSRuleImpl;
import org.htmlunit.cssparser.dom.CSSStyleSheetImpl;
import org.loboevolution.common.Urls;
import org.loboevolution.html.js.css.CSSStyleDeclarationImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.HtmlValues;

import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>BackgroundImageSetter class.</p>
 */
public class BackgroundImageSetter implements SubPropertySetter {
	
	private static final Logger logger = Logger.getLogger(BackgroundImageSetter.class.getName());

	private static final Pattern URL_PATTERN = Pattern.compile("url\\(\\s*[\"']?(.*?)[\"']?\\s*\\)");

	/** {@inheritDoc} */
	@Override
	public void changeValue(CSSStyleDeclaration declaration, String newValue) {
		String baseHref = null;
		String finalValue;
		CSSStyleDeclarationImpl props = (CSSStyleDeclarationImpl) declaration;
		final AbstractCSSRuleImpl rule = (AbstractCSSRuleImpl) props.getParentRule();
		if (rule != null) {
			final CSSStyleSheetImpl ssheet = rule.getParentStyleSheet();
			baseHref = ssheet.getHref();
		}

		if (baseHref == null) {
			baseHref = props.getContext() != null ? props.getContext().getDocumentBaseURI() : null;
		}

		if (baseHref != null) {
			final Matcher m = URL_PATTERN.matcher(newValue.toLowerCase());
			if (m.find()) {
				final String tentativeUri = HtmlValues.unquoteAndUnescape(m.group(1));
				try {
					if (tentativeUri.contains("data:image")) {
						finalValue = tentativeUri;
					} else {
						final URL resourcesUrl = Urls.createURL(new URL(baseHref), "../resources");
						if (resourcesUrl != null) {
							finalValue = "url("
									+ HtmlValues.quoteAndEscape(resourcesUrl.toExternalForm() + "/" + tentativeUri)
									+ ")";
						} else {
							final URL styleUrl = Urls.createURL(null, baseHref);
							finalValue = "url("
									+ HtmlValues.quoteAndEscape(Urls.createURL(styleUrl, tentativeUri).toExternalForm())
									+ ")";
						}
					}

				} catch (final Exception mfu) {
					logger.log(Level.WARNING,
							"Unable to create URL for URI=[" + tentativeUri + "], with base=[" + baseHref + "].",
							mfu);
					finalValue = newValue;
				}
			} else {
				finalValue = newValue;
			}

		} else {
			finalValue = newValue;
		}
		declaration.setProperty(BACKGROUND_IMAGE, finalValue);
	}
}