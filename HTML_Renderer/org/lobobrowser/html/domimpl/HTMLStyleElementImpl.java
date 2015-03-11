/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

import java.util.ArrayList;
import java.util.logging.Level;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.style.CSSUtilities;
import org.lobobrowser.html.w3c.HTMLStyleElement;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.dom.CSSStyleSheetImpl;


/**
 * The Class HTMLStyleElementImpl.
 */
public class HTMLStyleElementImpl extends HTMLElementImpl implements
		HTMLStyleElement {
	
	/** The style sheet. */
	private CSSStyleSheet styleSheet;

	/**
	 * Instantiates a new HTML style element impl.
	 */
	public HTMLStyleElementImpl() {
		super(HtmlProperties.STYLE, true);
	}

	/**
	 * Instantiates a new HTML style element impl.
	 *
	 * @param name the name
	 */
	public HTMLStyleElementImpl(String name) {
		super(name, true);
	}

	/** The disabled. */
	private boolean disabled;

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMElementImpl#getDisabled()
	 */
	public boolean getDisabled() {
		return this.disabled;
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLStyleElement#setDisabled(boolean)
	 */
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		CSSStyleSheet sheet = this.styleSheet;
		if (sheet != null) {
			sheet.setDisabled(disabled);
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLStyleElement#getMedia()
	 */
	public String getMedia() {
		return this.getAttribute(HtmlAttributeProperties.MEDIA);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLStyleElement#setMedia(java.lang.String)
	 */
	public void setMedia(String media) {
		this.setAttribute(HtmlAttributeProperties.MEDIA, media);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLStyleElement#getType()
	 */
	public String getType() {
		return this.getAttribute(HtmlAttributeProperties.TYPE);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLStyleElement#setType(java.lang.String)
	 */
	public void setType(String type) {
		this.setAttribute(HtmlAttributeProperties.TYPE, type);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#setUserData(java.lang.String, java.lang.Object, org.w3c.dom.UserDataHandler)
	 */
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
		if (uacontext.isInternalCSSEnabled()) {
			if (CSSUtilities.matchesMedia(this.getMedia(),
					this.getUserAgentContext())) {
				String text = this.getRawInnerText(true);
				if (text != null && !"".equals(text)) {

					HTMLDocumentImpl doc = (HTMLDocumentImpl) this
							.getOwnerDocument();
					
					try {

						ArrayList<String> arrText = CSSUtilities.cssText(text);

						for (int i = 0; i < arrText.size(); i++) {

							text = arrText.get(i);

							if (text.length() > 0 && !text.startsWith("/*")) {

								CSSStyleSheet sheet = null;

								try {
									sheet = CSSUtilities.parse(this, text,doc.getBaseURI(), false);
								} catch (Throwable e) {
									logger.log(Level.WARNING,
											"Unable to parse CSS: " + text);
								}
								
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
							}
						}
					} catch (Throwable err) {
						this.warn("Unable to parse style sheet", err);
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.DOMNodeImpl#appendInnerTextImpl(java.lang.StringBuffer)
	 */
	protected void appendInnerTextImpl(StringBuffer buffer) {
		// nop
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLStyleElement#getScoped()
	 */
	@Override
	public boolean getScoped() {
		String scoped = this.getAttribute(HtmlAttributeProperties.SCOPED);
		return HtmlAttributeProperties.SCOPED.equalsIgnoreCase(scoped);
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.w3c.HTMLStyleElement#setScoped(boolean)
	 */
	@Override
	public void setScoped(boolean scoped) {
		this.setAttribute(HtmlAttributeProperties.SCOPED, scoped ? HtmlAttributeProperties.SCOPED : null);
	}
	
}
