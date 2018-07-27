/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 27, 2005
 */
package org.loboevolution.html.domimpl;




import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.w3c.html.HTMLStyleElement;
import com.gargoylesoftware.css.parser.InputSource;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;
import com.gargoylesoftware.css.parser.CSSOMParser;

/**
 * The Class HTMLStyleElementImpl.
 */
public class HTMLStyleElementImpl extends HTMLElementImpl implements HTMLStyleElement {

	/** The style sheet. */
	private CSSStyleSheet styleSheet;

	/** The disabled. */
	private boolean disabled;

	/**
	 * Instantiates a new HTML style element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLStyleElementImpl(String name) {
		super(name);
	}

	@Override
	public boolean getDisabled() {
		return this.disabled;
	}

	@Override
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		CSSStyleSheet sheet = this.styleSheet;
		if (sheet != null) {
			sheet.setDisabled(disabled);
		}
	}

	@Override
	public String getMedia() {
		return this.getAttribute(MEDIA);
	}

	@Override
	public void setMedia(String media) {
		this.setAttribute(MEDIA, media);
	}

	@Override
	public String getType() {
		return this.getAttribute(TYPE);
	}

	@Override
	public void setType(String type) {
		this.setAttribute(TYPE, type);
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (HtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			this.processStyle();
		} else if ("styleSheet.disabled.changed".equals(key)) {
			this.informDocumentInvalid();
		}
		return super.setUserData(key, data, handler);
	}

	/**
	 * Process style.
	 */
	protected void processStyle() {
		this.styleSheet = null;
		UserAgentContext uacontext = this.getUserAgentContext();
		if (uacontext.isInternalCSSEnabled() && CSSUtilities.matchesMedia(this.getMedia(), this.getUserAgentContext())) {
			String text = this.getRawInnerText(true);
			if (text != null && !"".equals(text)) {
				HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getOwnerDocument();
				try {
					CSSOMParser parser = new CSSOMParser();
					InputSource is = CSSUtilities.getCssInputSourceForStyleSheet(text, doc.getBaseURI());
					CSSStyleSheet sheet = parser.parseStyleSheet(is, null);
					if (sheet != null) {
						doc.addStyleSheet(sheet);
						this.styleSheet = sheet;
						if (sheet instanceof CSSStyleSheetImpl) {
							CSSStyleSheetImpl sheetImpl = (CSSStyleSheetImpl) sheet;
							sheetImpl.setDisabled(disabled);
						} else {
							sheet.setDisabled(this.disabled);
						}
					}
				} catch (Throwable err) {
					logger.error("Unable to parse style sheet", err);
				}
			}
		}
	}

	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		// Method not implemented
	}

	@Override
	public boolean getScoped() {
		String scoped = this.getAttribute(SCOPED);
		return SCOPED.equalsIgnoreCase(scoped);
	}

	@Override
	public void setScoped(boolean scoped) {
		this.setAttribute(SCOPED, scoped ? SCOPED : null);
	}

	/**
	 * Gets the sheet.
	 *
	 * @return the sheet
	 */
	public CSSStyleSheet getSheet() {
		return this.styleSheet;
	}

}
