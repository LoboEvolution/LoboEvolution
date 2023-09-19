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

		if (baseHref != null && newValue != null) {
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