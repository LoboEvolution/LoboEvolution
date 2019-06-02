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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.dom.HTMLStyleElement;
import org.lobobrowser.html.style.CSSUtilities;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;
import com.gargoylesoftware.css.parser.InputSource;

public class HTMLStyleElementImpl extends HTMLElementImpl implements HTMLStyleElement {
	private boolean disabled;

	private CSSStyleSheet styleSheet;

	public HTMLStyleElementImpl() {
		super("STYLE", true);
	}

	public HTMLStyleElementImpl(String name) {
		super(name, true);
	}

	@Override
	protected void appendInnerTextImpl(StringBuffer buffer) {
		// nop
	}

	@Override
	public boolean getDisabled() {
		return this.disabled;
	}

	@Override
	public String getMedia() {
		return getAttribute("media");
	}

	@Override
	public String getType() {
		return getAttribute("type");
	}

	protected void processStyle() {
		this.styleSheet = null;
		if (CSSUtilities.matchesMedia(getMedia(), getUserAgentContext())) {
			final String text = getRawInnerText(true);
			if (text != null && !"".equals(text)) {
				final String processedText = CSSUtilities.preProcessCss(text);
				final HTMLDocumentImpl doc = (HTMLDocumentImpl) getOwnerDocument();
				final CSSOMParser parser = new CSSOMParser();
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

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		final CSSStyleSheet sheet = this.styleSheet;
		if (sheet != null) {
			sheet.setDisabled(disabled);
		}
	}

	@Override
	public void setMedia(String media) {
		setAttribute("media", media);
	}

	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (org.lobobrowser.html.parser.HtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			processStyle();
		}
		return super.setUserData(key, data, handler);
	}
}
