/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLLinkElement;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.js.css.CSSStyleSheetImpl;
import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.css.StyleSheet;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.renderstate.LinkRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.net.MimeType;
import org.loboevolution.html.dom.UserDataHandler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>HTMLLinkElementImpl class.</p>
 */
public class HTMLLinkElementImpl extends HTMLElementImpl implements HTMLLinkElement {

	private boolean disabled;

	private CSSStyleSheetImpl styleSheet;

	/**
	 * <p>Constructor for HTMLLinkElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLLinkElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		RenderState tmpRenderState = prevRenderState;
		if (hasAttribute("href")) {
			return new LinkRenderState(prevRenderState, getHtmlRendererContext(), this);
		}
		return super.createRenderState(tmpRenderState);
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

	/**
	 * <p>navigate.</p>
	 */
	public void navigate() {
		final HtmlRendererContext rcontext = getHtmlRendererContext();
		if (!this.disabled && rcontext != null) {
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
			final HTMLDocumentImpl doc = (HTMLDocumentImpl) getOwnerDocument();
			final String baseURI = doc.getBaseURI();

			try {
				final HtmlRendererContext rcontext = this.getHtmlRendererContext();
				final HtmlRendererConfig config = this.getHtmlRendererConfig();

				final String href = getHref();
				final String cleanRel = rel.trim().toLowerCase();
				final boolean isStyleSheet = cleanRel.equals("stylesheet");
				final boolean isAltStyleSheet = cleanRel.equals("alternate stylesheet");
				final String currentUrl = rcontext.getCurrentURL();

				if ((isStyleSheet || isAltStyleSheet)) {
					String title = getAttribute("title");
					URL baseURL = new URL(baseURI);
					URL scriptURL = Urls.createURL(baseURL, href);
					if (Strings.isBlank(title)) {
						URI uri = scriptURL.toURI();
						if (Urls.isLocalFile(scriptURL)) {
							title = Paths.get(uri).getFileName().toString();
						} else {
							title = new File(uri.getPath()).getName();
						}
					}

					String styleEnabled = "";
					List<String> styles = new ArrayList<>();
					if (!rcontext.isTestEnabled()) {
						styles = config.getStyles(href, currentUrl);
						if (styles.size() == 0) {
							config.insertStyle(title, href, currentUrl, isStyleSheet ? 1 : 0);
						} else {
							styleEnabled = styles.get(0);
						}
					}

					if (styleEnabled.equals(title) || styles.size() == 0) {
						final UserAgentContext uacontext = getUserAgentContext();
						if (uacontext.isExternalCSSEnabled()) {
							final String media = getMedia();
							if (CSSUtilities.matchesMedia(media, doc.getDefaultView())) {
								Instant start = Instant.now();
								TimingInfo info = new TimingInfo();
								final com.gargoylesoftware.css.dom.CSSStyleSheetImpl sheet = CSSUtilities.parseCssExternal(getHtmlRendererConfig(), href, scriptURL, baseURI, rcontext.isTestEnabled());
								sheet.setHref(baseURI);
								sheet.setDisabled(this.disabled);
								CSSStyleSheetImpl cssStyleSheet = new CSSStyleSheetImpl(sheet);
								cssStyleSheet.setOwnerNode(this);
								doc.addStyleSheet(cssStyleSheet);
								this.styleSheet = cssStyleSheet;
								Instant finish = Instant.now();
								long timeElapsed = Duration.between(start, finish).toMillis();
								info.setName(title);
								info.setTimeElapsed(timeElapsed);
								info.setPath(scriptURL.toExternalForm());
								info.setType(MimeType.CSS.getValue());
								info.setHttpResponse(200);
								final HtmlRendererContext htmlRendererContext = this.getHtmlRendererContext();
								final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
								htmlPanel.getBrowserPanel().getTimingList.add(info);
							}
						}
					}
				}

			} catch (final MalformedURLException mfe) {
				this.warn("Will not parse CSS. URI=[" + getHref() + "] with BaseURI=[" + baseURI + "] does not appear to be a valid URI.");
			} catch (final Throwable err) {
				this.warn("Unable to parse CSS. URI=[" + getHref() + "].", err);
			}
		}
	}

	@Override
	public String getAs() {
		return null;
	}

	@Override
	public void setAs(String as) {

	}

	@Override
	public String getCrossOrigin() {
		return null;
	}

	@Override
	public void setCrossOrigin(String crossOrigin) {

	}

	/** {@inheritDoc} */
	@Override
	public boolean isDisabled() {
		return this.disabled;
	}

	/** {@inheritDoc} */
	@Override
	public String getHref() {
		final String href = getAttribute("href");
		return href == null ? "" : href.trim();
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

	@Override
	public String getImageSizes() {
		return null;
	}

	@Override
	public void setImageSizes(String imageSizes) {

	}

	@Override
	public String getImageSrcset() {
		return null;
	}

	@Override
	public void setImageSrcset(String imageSrcset) {

	}

	@Override
	public String getIntegrity() {
		return null;
	}

	@Override
	public void setIntegrity(String integrity) {

	}

	/** {@inheritDoc} */
	@Override
	public void setMedia(String media) {
		setAttribute("media", media);
	}

	@Override
	public String getReferrerPolicy() {
		return null;
	}

	@Override
	public void setReferrerPolicy(String referrerPolicy) {

	}

	/** {@inheritDoc} */
	@Override
	public void setRel(String rel) {
		setAttribute("rel", rel);
	}

	@Override
	public DOMTokenList getRelList() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setRev(String rev) {
		setAttribute("rev", rev);
	}

	@Override
	public DOMTokenList getSizes() {
		return null;
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


	@Override
	public StyleSheet getSheet() {
		if ("stylesheet".equals(getRel())) {
			com.gargoylesoftware.css.dom.CSSStyleSheetImpl sheet = new com.gargoylesoftware.css.dom.CSSStyleSheetImpl();
			styleSheet = new CSSStyleSheetImpl(sheet);
		}

		return styleSheet;
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (XHtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			processLink();
		}
		return super.setUserData(key, data, handler);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getHref();
	}
}
