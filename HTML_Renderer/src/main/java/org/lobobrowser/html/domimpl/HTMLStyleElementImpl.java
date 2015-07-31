/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Nov 27, 2005
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.style.CSSUtilities;
import org.lobobrowser.html.w3c.HTMLStyleElement;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.dom.CSSStyleSheetImpl;
import com.steadystate.css.parser.CSSOMParser;

/**
 * The Class HTMLStyleElementImpl.
 */
public class HTMLStyleElementImpl extends HTMLElementImpl implements
HTMLStyleElement {

    /** The style sheet. */
    private CSSStyleSheet styleSheet;
    
    /** The disabled. */
    private boolean disabled;

    /**
     * Instantiates a new HTML style element impl.
     */
    public HTMLStyleElementImpl() {
        super(HtmlProperties.STYLE, true);
    }

    /**
     * Instantiates a new HTML style element impl.
     *
     * @param name
     *            the name
     */
    public HTMLStyleElementImpl(String name) {
        super(name, true);
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
        return this.getAttribute(HtmlAttributeProperties.MEDIA);
    }

    @Override
    public void setMedia(String media) {
        this.setAttribute(HtmlAttributeProperties.MEDIA, media);
    }

    @Override
    public String getType() {
        return this.getAttribute(HtmlAttributeProperties.TYPE);
    }

    @Override
    public void setType(String type) {
        this.setAttribute(HtmlAttributeProperties.TYPE, type);
    }

    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        if (HtmlParser.MODIFYING_KEY.equals(key) && (data != Boolean.TRUE)) {
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
				if ((text != null) && !"".equals(text)) {

					HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getOwnerDocument();

					try {

						CSSOMParser parser = new CSSOMParser();

						InputSource is = CSSUtilities.getCssInputSourceForStyleSheet(text, doc.getBaseURI());
						CSSStyleSheet sheet = parser.parseStyleSheet(is, null, null);

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
						this.warn("Unable to parse style sheet", err);
					}
				}
            }
        }
    }

    @Override
    protected void appendInnerTextImpl(StringBuffer buffer) {
        // nop
    }

    @Override
    public boolean getScoped() {
        String scoped = this.getAttribute(HtmlAttributeProperties.SCOPED);
        return HtmlAttributeProperties.SCOPED.equalsIgnoreCase(scoped);
    }

    @Override
    public void setScoped(boolean scoped) {
        this.setAttribute(HtmlAttributeProperties.SCOPED,
                scoped ? HtmlAttributeProperties.SCOPED : null);
    }
    
    
    /**
     * Gets the style sheets.
     *
     * @return the style sheets
     */
    public CSSStyleSheet getSheet() {
        return this.styleSheet;
    }

}
