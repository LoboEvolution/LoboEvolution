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
package org.lobobrowser.html.domimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.style.CSSUtilities;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.Urls;
import org.lobobrowser.w3c.html.DOMSettableTokenList;
import org.lobobrowser.w3c.html.DOMTokenList;
import org.lobobrowser.w3c.html.HTMLLinkElement;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.dom.CSSStyleSheetImpl;

/**
 * The Class HTMLLinkElementImpl.
 */
public class HTMLLinkElementImpl extends HTMLAbstractUIElement implements
HTMLLinkElement {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(HTMLLinkElementImpl.class.getName());

    /** The style sheet. */
    private CSSStyleSheet styleSheet;
    
    /** The disabled. */
    private boolean disabled;

    /**
     * Instantiates a new HTML link element impl.
     *
     * @param name
     *            the name
     */
    public HTMLLinkElementImpl(String name) {
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
    public String getHref() {
        String href = this.getAttribute(HtmlAttributeProperties.HREF);
        return href == null ? "" : Urls.removeControlCharacters(href);
    }

    @Override
    public void setHref(String href) {
        this.setAttribute(HtmlAttributeProperties.HREF, href);
    }

    @Override
    public String getHreflang() {
        return this.getAttribute(HtmlAttributeProperties.HREFLANG);
    }

    @Override
    public void setHreflang(String hreflang) {
        this.setAttribute(HtmlAttributeProperties.HREFLANG, hreflang);
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
    public String getRel() {
        return this.getAttribute(HtmlAttributeProperties.REL);
    }

    @Override
    public void setRel(String rel) {
        this.setAttribute(HtmlAttributeProperties.REL, rel);
    }

    @Override
    public String getRev() {
        return this.getAttribute(HtmlAttributeProperties.REV);
    }

    @Override
    public void setRev(String rev) {
        this.setAttribute(HtmlAttributeProperties.REV, rev);
    }

    @Override
    public String getTarget() {
        String target = this.getAttribute(HtmlAttributeProperties.TARGET);
        if (target != null) {
            return target;
        }
        HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
        return doc == null ? null : doc.getDefaultTarget();
    }

    @Override
    public void setTarget(String target) {
        this.setAttribute(HtmlAttributeProperties.TARGET, target);
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
            this.processLink();
        } else if ("styleSheet.disabled.changed".equals(key)) {
            this.informDocumentInvalid();
        }
        return super.setUserData(key, data, handler);
    }

    /**
     * If the LINK refers to a stylesheet document, this method loads and parses
     * it.
     */
    protected void processLink() {
        this.styleSheet = null;
        String rel = this.getAttribute(HtmlAttributeProperties.REL);
        if (rel != null) {
            String cleanRel = rel.trim().toLowerCase();
            boolean isStyleSheet = cleanRel.equals("stylesheet");
            boolean isAltStyleSheet = cleanRel.equals("alternate stylesheet");
            boolean isAppendixStyleSheet = cleanRel
                    .equals("appendix stylesheet");
            if (isStyleSheet || isAltStyleSheet || isAppendixStyleSheet) {
                UserAgentContext uacontext = this.getUserAgentContext();
                if (uacontext.isExternalCSSEnabled()) {
                    String media = this.getMedia();
					if (CSSUtilities.matchesMedia(media, uacontext)) {
						HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getOwnerDocument();
						try {
							
							CSSStyleSheet sheet = CSSUtilities.parse(this.getHref(), doc);
							CSSStyleSheetImpl sheetImpl = null;
							if (sheet != null) {
								doc.addStyleSheet(sheet);
								this.styleSheet = sheet;
								if (sheet instanceof CSSStyleSheetImpl) {
									sheetImpl = (CSSStyleSheetImpl) sheet;
									if (isAltStyleSheet) {
										sheetImpl.setDisabled(true);
									} else {
										sheetImpl.setDisabled(disabled);
									}
									
									URL baseURL = new URL(doc.getBaseURI());
							        URL scriptURL = Urls.createURL(baseURL, getHref());
							        if(scriptURL!= null)
							        	sheetImpl.setHref(scriptURL.toString());
									doc.addStyleSheet(sheetImpl);
								} else {
									if (isAltStyleSheet) {
										sheet.setDisabled(true);
									} else {
										sheet.setDisabled(this.disabled);
									}
									doc.addStyleSheet(sheet);
								}
								
							}

						} catch (MalformedURLException mfe) {
							this.warn("Will not parse CSS. URI=[" + this.getHref() + "] with BaseURI=["
									+ doc.getBaseURI() + "] does not appear to be a valid URI.");
						} catch (Throwable err) {
							this.warn("Unable to parse CSS. URI=[" + this.getHref() + "].", err);
						}
					}
                }
            }
        }
    }

    /**
     * Gets the absolute href.
     *
     * @return the absolute href
     */
    public String getAbsoluteHref() {
        HtmlRendererContext rcontext = this.getHtmlRendererContext();
        if (rcontext != null) {
            String href = this.getHref();
            if ((href != null) && (href.length() > 0)) {
                try {
                    URL url = this.getFullURL(href);
                    return url == null ? null : url.toExternalForm();
                } catch (MalformedURLException mfu) {
                    this.warn("Malformed URI: [" + href + "].", mfu);
                }
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        return this.getHref();
    }

    @Override
    public DOMTokenList getRelList() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DOMSettableTokenList getSizes() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setSizes(String sizes) {
        this.setAttribute(HtmlAttributeProperties.SIZES, sizes);

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
