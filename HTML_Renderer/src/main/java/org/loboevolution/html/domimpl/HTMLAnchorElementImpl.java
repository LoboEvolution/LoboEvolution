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
package org.loboevolution.html.domimpl;

import java.awt.Color;
import java.awt.Cursor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.loboevolution.color.ColorFactory;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.renderstate.ColorRenderState;
import org.loboevolution.html.renderstate.CursorRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TextDecorationRenderState;
import org.loboevolution.http.Urls;
import org.loboevolution.util.Strings;
import org.loboevolution.w3c.html.DOMTokenList;
import org.loboevolution.w3c.html.HTMLAnchorElement;
import org.loboevolution.w3c.html.HTMLBodyElement;
import org.loboevolution.w3c.html.HTMLDocument;

/**
 * The Class HTMLAnchorElementImpl.
 */
public class HTMLAnchorElementImpl extends HTMLAbstractUIElement implements HTMLAnchorElement {

	/**
	 * Instantiates a new HTML link element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLAnchorElementImpl(String name) {
		super(name);
	}

	@Override
	public String getHref() {
		String href = this.getAttribute(HREF);
		return href == null ? "" : Urls.removeControlCharacters(href);
	}

	@Override
	public void setHref(String href) {
		this.setAttribute(HREF, href);
	}

	@Override
	public String getHreflang() {
		return this.getAttribute(HREFLANG);
	}

	@Override
	public void setHreflang(String hreflang) {
		this.setAttribute(HREFLANG, hreflang);
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
	public String getRel() {
		return this.getAttribute(REL);
	}

	@Override
	public void setRel(String rel) {
		this.setAttribute(REL, rel);
	}

	@Override
	public String getRev() {
		return this.getAttribute(REV);
	}

	@Override
	public void setRev(String rev) {
		this.setAttribute(REV, rev);
	}

	@Override
	public String getTarget() {
		String target = this.getAttribute(TARGET);
		if (target != null) {
			return target;
		}
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		return doc == null ? null : doc.getDefaultTarget();
	}

	@Override
	public void setTarget(String target) {
		this.setAttribute(TARGET, target);
	}

	@Override
	public String getType() {
		return this.getAttribute(TYPE);
	}

	@Override
	public void setType(String type) {
		this.setAttribute(TYPE, type);
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
			if (!Strings.isBlank(href)) {
				try {
					URL url = this.getFullURL(href);
					return url == null ? null : url.toExternalForm();
				} catch (MalformedURLException mfu) {
					logger.error("Malformed URI: [" + href + "].", mfu);
				}
			}
		}
		return null;
	}

	/**
	 * Navigate.
	 */
	public void navigate() {

		HtmlRendererContext rcontext = this.getHtmlRendererContext();
		if (rcontext != null) {
			String href = this.getHref();
			if (!Strings.isBlank(href)) {
				String target = this.getTarget();
				try {
					URL url = this.getFullURL(href);
					if (url != null) {
						rcontext.linkClicked(this, url, target);
					}
				} catch (MalformedURLException mfu) {
					logger.error("Malformed URI: [" + href + "].", mfu);
				}
			}
		}
	}

	/**
	 * Gets the link color.
	 *
	 * @return the link color
	 */
	private Color linkColor() {
		HTMLDocument doc = (HTMLDocument) this.document;
		if (doc != null) {
			HTMLBodyElement body = (HTMLBodyElement) doc.getBody();
			if (body != null) {
				String vlink = body.getVLink();
				String link = body.getLink();
				if (vlink != null || link != null) {
					HtmlRendererContext rcontext = this.getHtmlRendererContext();
					if (rcontext != null) {
						boolean visited = rcontext.isVisitedLink(this);
						String colorText = visited ? vlink : link;
						if (colorText != null) {
							return ColorFactory.getInstance().getColor(colorText);
						}
					}
				}
			}
		}
		return Color.BLUE;
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		RenderState tmpRenderState = prevRenderState;
		if (this.hasAttribute(HREF)) {
			tmpRenderState = new TextDecorationRenderState(tmpRenderState, RenderState.MASK_TEXTDECORATION_UNDERLINE);
			tmpRenderState = new ColorRenderState(tmpRenderState, this.linkColor());
			tmpRenderState = new CursorRenderState(tmpRenderState, Optional.of(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)));
		}
		return super.createRenderState(tmpRenderState);
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
	public String getCoords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCoords(String coords) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getShape() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShape(String shape) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPing(String ping) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProtocol(String protocol) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHost(String host) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHostname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHostname(String hostname) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPort(String port) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPathname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPathname(String pathname) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSearch() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSearch(String search) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getHash() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHash(String hash) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPing() {
		// TODO Auto-generated method stub
		return null;
	}
}
