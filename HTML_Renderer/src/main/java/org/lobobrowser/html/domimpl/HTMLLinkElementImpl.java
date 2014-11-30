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
package org.lobobrowser.html.domimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.style.CSSUtilities;
import org.lobobrowser.html.style.ColorRenderState;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.TextDecorationRenderState;
import org.lobobrowser.html.w3c.DOMSettableTokenList;
import org.lobobrowser.html.w3c.DOMTokenList;
import org.lobobrowser.html.w3c.HTMLBodyElement;
import org.lobobrowser.html.w3c.HTMLDocument;
import org.lobobrowser.html.w3c.HTMLLinkElement;
import org.lobobrowser.util.Urls;
import org.lobobrowser.util.gui.ColorFactory;
import org.w3c.dom.UserDataHandler;
import org.w3c.dom.css.CSSStyleSheet;

import com.steadystate.css.dom.CSSStyleSheetImpl;

public class HTMLLinkElementImpl extends HTMLAbstractUIElement implements
		HTMLLinkElement {
	private static final Logger logger = Logger
			.getLogger(HTMLLinkElementImpl.class.getName());
	private static final boolean loggableInfo = logger.isLoggable(Level.INFO);
	private CSSStyleSheet styleSheet;

	public HTMLLinkElementImpl(String name) {
		super(name);
	}

	private boolean disabled;

	public boolean getDisabled() {
		return this.disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
		CSSStyleSheet sheet = this.styleSheet;
		if (sheet != null) {
			sheet.setDisabled(disabled);
		}
	}

	public String getHref() {
		String href = this.getAttribute(HtmlAttributeProperties.HREF);
		return href == null ? "" : Urls.removeControlCharacters(href);
	}

	public void setHref(String href) {
		this.setAttribute(HtmlAttributeProperties.HREF, href);
	}

	public String getHreflang() {
		return this.getAttribute(HtmlAttributeProperties.HREFLANG);
	}

	public void setHreflang(String hreflang) {
		this.setAttribute(HtmlAttributeProperties.HREFLANG, hreflang);
	}

	public String getMedia() {
		return this.getAttribute(HtmlAttributeProperties.MEDIA);
	}

	public void setMedia(String media) {
		this.setAttribute(HtmlAttributeProperties.MEDIA, media);
	}

	public String getRel() {
		return this.getAttribute(HtmlAttributeProperties.REL);
	}

	public void setRel(String rel) {
		this.setAttribute(HtmlAttributeProperties.REL, rel);
	}

	public String getRev() {
		return this.getAttribute(HtmlAttributeProperties.REV);
	}

	public void setRev(String rev) {
		this.setAttribute(HtmlAttributeProperties.REV, rev);
	}

	public String getTarget() {
		String target = this.getAttribute(HtmlAttributeProperties.TARGET);
		if (target != null) {
			return target;
		}
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.getDefaultTarget();
	}

	public void setTarget(String target) {
		this.setAttribute(HtmlAttributeProperties.TARGET, target);
	}

	public String getType() {
		return this.getAttribute(HtmlAttributeProperties.TYPE);
	}

	public void setType(String type) {
		this.setAttribute(HtmlAttributeProperties.TYPE, type);
	}

	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (org.lobobrowser.html.parser.HtmlParser.MODIFYING_KEY.equals(key)
				&& data != Boolean.TRUE) {
			this.processLink();
		} else if (com.steadystate.css.dom.CSSStyleSheetImpl.KEY_DISABLED_CHANGED.equals(key)) {
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
			boolean isAppendixStyleSheet = cleanRel.equals("appendix stylesheet");
			if (isStyleSheet || isAltStyleSheet || isAppendixStyleSheet) {
				UserAgentContext uacontext = this.getUserAgentContext();
				if (uacontext.isExternalCSSEnabled()) {
					String media = this.getMedia();
					if (CSSUtilities.matchesMedia(media, uacontext)) {
						HTMLDocumentImpl doc = (HTMLDocumentImpl) this
								.getOwnerDocument();
						try {
							boolean liflag = loggableInfo;
							long time1 = liflag ? System.currentTimeMillis() : 0;
							try {

								ArrayList<String> arrText = CSSUtilities.cssText(this.getHref(), doc,
										doc.getBaseURI());

								String text = "";
								
								for (int i = 0; i < arrText.size(); i++) {
									
									text = arrText.get(i);
									
									if (text.length() > 0 && !text.startsWith("/*")) {
										
										CSSStyleSheet sheet = null;

										try {	
										sheet = CSSUtilities
												.parse(this, this.getHref(),
														text, doc.getBaseURI(),
														false);
										}catch(Throwable e){
											logger.log(Level.WARNING, "Unable to parse CSS: " + text);
										}
										
										if (sheet != null) {
											doc.addStyleSheet(sheet);
											this.styleSheet = sheet;
											if (sheet instanceof CSSStyleSheetImpl) {
												CSSStyleSheetImpl sheetImpl = (CSSStyleSheetImpl) sheet;
												if (isAltStyleSheet) {
													sheetImpl.setDisabled(true);
												} else {
													sheetImpl
															.setDisabled(disabled);
												}
											} else {
												if (isAltStyleSheet) {
													sheet.setDisabled(true);
												} else {
													sheet.setDisabled(this.disabled);
												}
											}
											doc.addStyleSheet(sheet);
										}
									}
								}
							} finally {
								if (liflag) {
									long time2 = System.currentTimeMillis();
									logger.info("processLink(): Loaded and parsed CSS (or attempted to) at URI=["
											+ this.getHref()
											+ "] in "
											+ (time2 - time1) + " ms.");
								}
							}

						} catch (MalformedURLException mfe) {
							this.warn("Will not parse CSS. URI=["
									+ this.getHref() + "] with BaseURI=["
									+ doc.getBaseURI()
									+ "] does not appear to be a valid URI.");
						} catch (Throwable err) {
							this.warn(
									"Unable to parse CSS. URI=["
											+ this.getHref() + "].", err);
						}
					}
				}
			}
		}
	}
	
	public String getAbsoluteHref() {
		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		if (rcontext != null) {
			String href = this.getHref();
			if (href != null && href.length() > 0) {
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

	public void navigate() {
		if (this.disabled) {
			return;
		}
		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		if (rcontext != null) {
			String href = this.getHref();
			if (href != null && href.length() > 0) {
				String target = this.getTarget();
				try {
					URL url = this.getFullURL(href);
					if (url == null) {
						this.warn("Unable to resolve URI: [" + href + "].");
					} else {
						rcontext.linkClicked(this, url, target);
					}
				} catch (MalformedURLException mfu) {
					this.warn("Malformed URI: [" + href + "].", mfu);
				}
			}
		}
	}

	private java.awt.Color getLinkColor() {
		HTMLDocument doc = (HTMLDocument) this.document;
		if (doc != null) {
			HTMLBodyElement body = (HTMLBodyElement) doc.getBody();
			if (body != null) {
				String vlink = body.getVLink();
				String link = body.getLink();
				if (vlink != null || link != null) {
					HtmlRendererContext rcontext = this
							.getHtmlRendererContext();
					if (rcontext != null) {
						boolean visited = rcontext.isVisitedLink(this);
						String colorText = visited ? vlink : link;
						if (colorText != null) {
							return ColorFactory.getInstance().getColor(
									colorText);
						}
					}
				}
			}
		}
		return java.awt.Color.BLUE;
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		if (this.hasAttribute(HtmlAttributeProperties.HREF)) {
			prevRenderState = new TextDecorationRenderState(prevRenderState,
					RenderState.MASK_TEXTDECORATION_UNDERLINE);
			prevRenderState = new ColorRenderState(prevRenderState,
					this.getLinkColor());
		}
		return super.createRenderState(prevRenderState);
	}

	public String toString() {
		// Javascript code often depends on this being exactly href. See
		// js9.html.
		// To change, perhaps add method to AbstractScriptableDelegate.
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
}
