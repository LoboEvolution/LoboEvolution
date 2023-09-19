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
/*
 * Created on Nov 27, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.parser.CSSOMParser;
import org.htmlunit.cssparser.parser.InputSource;
import org.htmlunit.cssparser.parser.javacc.CSS3Parser;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLStyleElement;
import org.loboevolution.html.dom.svg.SVGSVGElement;
import org.loboevolution.html.js.css.CSSStyleSheetImpl;
import org.loboevolution.html.node.css.CSSStyleSheet;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.dom.UserDataHandler;

/**
 * <p>HTMLStyleElementImpl class.</p>
 */
public class HTMLStyleElementImpl extends HTMLElementImpl implements HTMLStyleElement {

	private boolean disabled;

	private CSSStyleSheetImpl styleSheet;

	/**
	 * <p>Constructor for HTMLStyleElementImpl.</p>
	 */
	public HTMLStyleElementImpl() {
		super("STYLE");
	}

	/**
	 * <p>Constructor for HTMLStyleElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLStyleElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		// nop
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDisabled() {
		return this.disabled;
	}

	/** {@inheritDoc} */
	@Override
	public CSSStyleSheet getStyleSheet() {
		return this.styleSheet;
	}

	/** {@inheritDoc} */
	@Override
	public String getMedia() {
		return getAttribute("media");
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return getAttribute("type");
	}

	/** {@inheritDoc} */
	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		final CSSStyleSheetImpl sheet = this.styleSheet;
		if (sheet != null) {
			sheet.setDisabled(disabled);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setMedia(String media) {
		setAttribute("media", media);
	}

	/** {@inheritDoc} */
	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (XHtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			processStyle();
		}
		return super.setUserData(key, data, handler);
	}

	/**
	 * <p>processStyle.</p>
	 */
	private void processStyle() {
		this.styleSheet = null;
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) getOwnerDocument();
		if (CSSUtilities.matchesMedia(getMedia(), doc.getDefaultView())) {
			final String text = getRawInnerText(true);
			if (Strings.isNotBlank(text)) {
				final String processedText = CSSUtilities.preProcessCss(text);
				final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
				final String baseURI = doc.getBaseURI();
				final InputSource is = CSSUtilities.getCssInputSourceForStyleSheet(processedText, baseURI);
				try {
					final org.htmlunit.cssparser.dom.CSSStyleSheetImpl sheet = parser.parseStyleSheet(is, null);
					sheet.setHref(baseURI);
					sheet.setDisabled(this.disabled);
					CSSStyleSheetImpl cssStyleSheet = new CSSStyleSheetImpl(sheet);
					cssStyleSheet.setOwnerNode(this);

					if(! (this.getParentNode() instanceof SVGSVGElement))
						doc.addStyleSheet(cssStyleSheet);

					this.styleSheet = cssStyleSheet;
				} catch (final Throwable err) {
					this.warn("Unable to parse style sheet", err);
				}
			}
		}
	}

	@Override
	public int getClientHeight() {
		return 0;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLStyleElement]";
	}
}
