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

public class BackgroundImageSetter implements SubPropertySetter {
	
	private static final Logger logger = Logger.getLogger(BackgroundImageSetter.class.getName());

	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}

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