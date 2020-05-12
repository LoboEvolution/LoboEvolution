/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Nov 27, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLStyleElement;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.style.CSSUtilities;
import org.w3c.dom.UserDataHandler;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;
import com.gargoylesoftware.css.parser.javacc.CSS3Parser;

/**
 * <p>HTMLStyleElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLStyleElementImpl extends HTMLElementImpl implements HTMLStyleElement {
	private boolean disabled;

	private CSSStyleSheetImpl styleSheet;

	/**
	 * <p>Constructor for HTMLStyleElementImpl.</p>
	 */
	public HTMLStyleElementImpl() {
		super("STYLE", true);
	}

	/**
	 * <p>Constructor for HTMLStyleElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLStyleElementImpl(String name) {
		super(name, true);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		// nop
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDisabled() {
		return this.disabled;
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

	/**
	 * <p>processStyle.</p>
	 */
	protected void processStyle() {
		this.styleSheet = null;
		if (CSSUtilities.matchesMedia(getMedia(), getUserAgentContext())) {
			final String text = getRawInnerText(true);
			if (Strings.isNotBlank(text)) {
				final String processedText = CSSUtilities.preProcessCss(text);
				final HTMLDocumentImpl doc = (HTMLDocumentImpl) getOwnerDocument();
                final CSSOMParser parser = new CSSOMParser(new CSS3Parser());
				final String baseURI = doc.getBaseURI();
				final InputSource is = CSSUtilities.getCssInputSourceForStyleSheet(processedText, baseURI);
				try {
					final CSSStyleSheetImpl sheet = parser.parseStyleSheet(is, null);
					sheet.setOwnerNode(this);
					sheet.setHref(baseURI);
					doc.addStyleSheet(sheet);
					this.styleSheet = sheet;
					sheet.setDisabled(this.disabled);
				} catch (final Throwable err) {
					this.warn("Unable to parse style sheet", err);
				}
			}
		}
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
		if (HtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			processStyle();
		}
		return super.setUserData(key, data, handler);
	}
}
