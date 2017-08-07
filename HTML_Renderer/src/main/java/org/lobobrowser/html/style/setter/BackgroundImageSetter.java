package org.lobobrowser.html.style.setter;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.Urls;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.util.CSSProperties;

public class BackgroundImageSetter implements SubPropertySetter, CSSProperties{
	
	/** The Constant logger. */
	protected static final Logger logger = LogManager.getLogger(BackgroundImageSetter.class.getName());

	@Override
	public void changeValue(AbstractCSS2Properties properties, String newValue, CSSStyleDeclaration declaration,
			boolean important) {
		String baseHref = null;
		String finalValue;
		if (declaration != null) {
			CSSRule rule = declaration.getParentRule();
			if (rule != null) {
				CSSStyleSheet sheet = rule.getParentStyleSheet();
				if (sheet instanceof com.steadystate.css.dom.CSSStyleSheetImpl) {
					com.steadystate.css.dom.CSSStyleSheetImpl ssheet = (com.steadystate.css.dom.CSSStyleSheetImpl) sheet;
					baseHref = ssheet.getHref();
				}
			}
		}
		if (baseHref == null) {
			baseHref = properties.context.getDocumentBaseURI();
		}
		String start = "url(";
		if (newValue == null || !newValue.toLowerCase().startsWith(start)) {
			finalValue = newValue;
		} else {
			int startIdx = start.length();
			int closingIdx = newValue.lastIndexOf(')');
			if (closingIdx == -1) {
				finalValue = newValue;
			} else {
				String quotedUri = newValue.substring(startIdx, closingIdx);
				String tentativeUri = HtmlValues.unquoteAndUnescape(quotedUri);
				if (baseHref == null) {
					finalValue = newValue;
				} else {
					try {
						URL styleUrl = Urls.createURL(null, baseHref);
						finalValue = "url("
								+ HtmlValues.quoteAndEscape(Urls.createURL(styleUrl, tentativeUri).toExternalForm())
								+ ")";
					} catch (MalformedURLException | UnsupportedEncodingException mfu) {
						logger.error("Unable to create URL for URI=[" + tentativeUri + "], with base=[" + baseHref
								+ "].", mfu);
						finalValue = newValue;
					}
				}
			}
		}
		properties.setPropertyValueLCAlt(BACKGROUND_IMAGE, finalValue, important);
	}
	
	/**
	 * Change value.
	 *
	 * @param properties
	 *            the properties
	 * @param newValue
	 *            the new value
	 * @param declaration
	 *            the declaration
	 */
	public void changeValue(AbstractCSS2Properties properties, String newValue, CSSStyleDeclaration declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}
}