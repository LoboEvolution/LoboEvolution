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
package org.loboevolution.html.dom.domimpl;

import java.awt.Color;
import java.awt.Cursor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.HTMLLinkElement;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.ColorRenderState;
import org.loboevolution.html.renderstate.CursorRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TextDecorationRenderState;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.laf.ColorFactory;
import org.w3c.dom.UserDataHandler;

import com.gargoylesoftware.css.dom.CSSStyleSheetImpl;

/**
 * <p>HTMLLinkElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLLinkElementImpl extends HTMLAbstractUIElement implements HTMLLinkElement {
	
	private String COLOR_VISITED = "#551A8B";
	
	private String DEFAULT_COLOR = "Blue";
	
	private boolean disabled;

	private CSSStyleSheetImpl styleSheet;

	/**
	 * <p>Constructor for HTMLLinkElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLLinkElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		RenderState tmpRenderState = prevRenderState;
		if (hasAttribute("href")) {
			tmpRenderState = new TextDecorationRenderState(tmpRenderState, RenderState.MASK_TEXTDECORATION_UNDERLINE);
			tmpRenderState = new ColorRenderState(tmpRenderState, this.linkColor());
			tmpRenderState = new CursorRenderState(tmpRenderState, Optional.of(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));
		}
		return super.createRenderState(tmpRenderState);
	}
	
	private Color linkColor() {
		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		if (rcontext != null) {
			boolean visited = rcontext.isVisitedLink(this);
			String vlink = null;
			String link = null;
			HTMLDocument doc = (HTMLDocument) this.document;
			if (doc != null) {
				HTMLBodyElement body = (HTMLBodyElement) doc.getBody();
				if (body != null) {
					vlink = body.getVLink();
					link = body.getLink();
				}
			}
			vlink = (vlink == null) ? COLOR_VISITED : vlink;
			link = (link == null) ? DEFAULT_COLOR : link;
			String colorText = visited ? vlink : link;
			if (colorText != null) {
				return ColorFactory.getInstance().getColor(colorText);
			}
		}
		return Color.BLUE;
	}

	/**
	 * <p>getAbsoluteHref.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAbsoluteHref() {
		final HtmlRendererContext rcontext = getHtmlRendererContext();
		if (rcontext != null) {
			final String href = getHref();
			if (href != null && href.length() > 0) {
				getTarget();
				try {
					final URL url = getFullURL(href);
					return url == null ? null : url.toExternalForm();
				} catch (final MalformedURLException mfu) {
					this.warn("Malformed URI: [" + href + "].", mfu);
				}
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean getDisabled() {
		return this.disabled;
	}

	/** {@inheritDoc} */
	@Override
	public String getHref() {
		final String href = getAttribute("href");
		return href == null ? "" : href;
	}

	/** {@inheritDoc} */
	@Override
	public String getHreflang() {
		return getAttribute("hreflang");
	}

	/** {@inheritDoc} */
	@Override
	public String getMedia() {
		return getAttribute("media");
	}

	/** {@inheritDoc} */
	@Override
	public String getRel() {
		return getAttribute("rel");
	}

	/** {@inheritDoc} */
	@Override
	public String getRev() {
		return getAttribute("rev");
	}

	/** {@inheritDoc} */
	@Override
	public String getTarget() {
		final String target = getAttribute("target");
		if (target != null) {
			return target;
		}
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.getDefaultTarget();
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return getAttribute("type");
	}

	/**
	 * <p>navigate.</p>
	 */
	public void navigate() {
		if (this.disabled) {
			return;
		}
		final HtmlRendererContext rcontext = getHtmlRendererContext();
		if (rcontext != null) {
			final String href = getHref();
			if (Strings.isNotBlank(href)) {
				try {
					final URL url = getFullURL(href);
					if (url == null) {
						this.warn("Unable to resolve URI: [" + href + "].");
					} else {
						rcontext.linkClicked(url, false);
					}
				} catch (final MalformedURLException mfu) {
					this.warn("Malformed URI: [" + href + "].", mfu);
				}
			}
		}
	}

	/**
	 * If the LINK refers to a stylesheet document, this method loads and parses it.
	 */
	protected void processLink() {
		this.styleSheet = null;
		final String rel = getAttribute("rel");
		if (rel != null) {
			final String cleanRel = rel.trim().toLowerCase();
			final boolean isStyleSheet = cleanRel.equals("stylesheet");
			final boolean isAltStyleSheet = cleanRel.equals("alternate stylesheet");
			if (isStyleSheet || isAltStyleSheet) {
				final UserAgentContext uacontext = getUserAgentContext();
				if (uacontext.isExternalCSSEnabled()) {
					final String media = getMedia();
					if (CSSUtilities.matchesMedia(media, uacontext)) {
						final HTMLDocumentImpl doc = (HTMLDocumentImpl) getOwnerDocument();
						try {
							final CSSStyleSheetImpl sheet = CSSUtilities.parseCssExternal(getHref(), doc);
							if (sheet != null) {
								this.styleSheet = sheet;
								if (sheet instanceof CSSStyleSheetImpl) {
									final CSSStyleSheetImpl sheetImpl = (CSSStyleSheetImpl) sheet;
									sheetImpl.setDisabled(this.disabled);
								} else {
									if (isAltStyleSheet) {
										sheet.setDisabled(true);
									} else {
										sheet.setDisabled(this.disabled);
									}
								}
								doc.addStyleSheet(sheet);
							}

						} catch (final MalformedURLException mfe) {
							this.warn("Will not parse CSS. URI=[" + getHref() + "] with BaseURI=[" + doc.getBaseURI()
									+ "] does not appear to be a valid URI.");
						} catch (final Throwable err) {
							this.warn("Unable to parse CSS. URI=[" + getHref() + "].", err);
						}
					}
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
	public void setHref(String href) {
		setAttribute("href", href);
	}

	/** {@inheritDoc} */
	@Override
	public void setHreflang(String hreflang) {
		setAttribute("hreflang", hreflang);
	}

	/** {@inheritDoc} */
	@Override
	public void setMedia(String media) {
		setAttribute("media", media);
	}

	/** {@inheritDoc} */
	@Override
	public void setRel(String rel) {
		setAttribute("rel", rel);
	}

	/** {@inheritDoc} */
	@Override
	public void setRev(String rev) {
		setAttribute("rev", rev);
	}

	/** {@inheritDoc} */
	@Override
	public void setTarget(String target) {
		setAttribute("target", target);
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
			processLink();
		}
		return super.setUserData(key, data, handler);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		// Javascript code often depends on this being exactly href. See js9.html.
		// To change, perhaps add method to AbstractScriptableDelegate.
		return getHref();
	}
}
