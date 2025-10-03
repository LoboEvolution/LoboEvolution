/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
import org.loboevolution.html.dom.DOMTokenList;
import org.loboevolution.css.StyleSheet;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.net.MimeType;
import org.loboevolution.html.dom.UserDataHandler;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
	 * @param name a {@link String} object.
	 */
	public HTMLLinkElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return super.createRenderState(prevRenderState);
	}

	/**
	 * If the LINK refers to a stylesheet document, this method loads and parses it.
	 */
	private void processLink() {
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
					final URI scriptURI = Urls.createURI(baseURI, href);
					final URL scriptURL = scriptURI.toURL();
					if (Strings.isBlank(title)) {
						if (Urls.isLocalFile(scriptURL)) {
							title = Paths.get(scriptURI).getFileName().toString();
						} else {
							title = new File(scriptURI.getPath()).getName();
						}
					}

					String styleEnabled = "";
					List<String> styles = new ArrayList<>();
					if (!rcontext.isTestEnabled()) {
						styles = config.getStyles(href, currentUrl);
						if (styles.isEmpty()) {
							config.insertStyle(title, href, currentUrl, isStyleSheet ? 1 : 0);
						} else {
							styleEnabled = styles.getFirst();
						}
					}

					if (styleEnabled.equals(title) || styles.isEmpty()) {
						final UserAgentContext uacontext = getUserAgentContext();
						if (uacontext.isExternalCSSEnabled()) {
							final String media = getMedia();
							if (CSSUtilities.matchesMedia(media, doc.getDefaultView())) {
								final Instant start = Instant.now();
								final TimingInfo info = new TimingInfo();
								final org.htmlunit.cssparser.dom.CSSStyleSheetImpl sheet = CSSUtilities.parseCssExternal(getHtmlRendererConfig(), scriptURI, baseURI, getIntegrity(), rcontext.isTestEnabled());
								String fileName = scriptURL.getFile().substring(scriptURL.getFile().lastIndexOf('/') + 1);
								boolean exist = Urls.exists(scriptURL);
								sheet.setHref(exist ? fileName : null);
								sheet.setDisabled(this.disabled);

								final CSSStyleSheetImpl cssStyleSheet = new CSSStyleSheetImpl(sheet);
								cssStyleSheet.setOwnerNode(this);
								doc.addStyleSheet(cssStyleSheet);
								this.styleSheet = cssStyleSheet;
								final Instant finish = Instant.now();
								final long timeElapsed = Duration.between(start, finish).toMillis();
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

			} catch (Exception ex) {
				this.warn("Will not parse CSS. URI=[" + getHref() + "] with BaseURI=[" + baseURI + "] does not appear to be a valid URI.", ex);
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
	public void setAs(final String as) {

	}

	@Override
	public String getCrossOrigin() {
		return null;
	}

	@Override
	public void setCrossOrigin(final String crossOrigin) {

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
	public void setDisabled(final boolean disabled) {
		this.disabled = disabled;
		final CSSStyleSheetImpl sheet = this.styleSheet;
		if (sheet != null) {
			sheet.setDisabled(disabled);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setHref(final String href) {
		setAttribute("href", href);
	}

	/** {@inheritDoc} */
	@Override
	public void setHreflang(final String hreflang) {
		setAttribute("hreflang", hreflang);
	}

	@Override
	public String getImageSizes() {
		return null;
	}

	@Override
	public void setImageSizes(final String imageSizes) {

	}

	@Override
	public String getImageSrcset() {
		return null;
	}

	@Override
	public void setImageSrcset(final String imageSrcset) {

	}

	@Override
	public String getIntegrity() {
		return getAttribute("integrity");
	}

	@Override
	public void setIntegrity(final String integrity) {

	}

	/** {@inheritDoc} */
	@Override
	public void setMedia(final String media) {
		setAttribute("media", media);
	}

	@Override
	public String getReferrerPolicy() {
		return null;
	}

	@Override
	public void setReferrerPolicy(final String referrerPolicy) {

	}

	/** {@inheritDoc} */
	@Override
	public void setRel(final String rel) {
		setAttribute("rel", rel);
	}

	@Override
	public DOMTokenList getRelList() {
		final DOMTokenListImpl tokList = new DOMTokenListImpl(this);
		final String rel = getRel();
		if(Strings.isNotBlank(rel)){
			final String[] listString = rel.split(" ");
			final List<String> names = Arrays.asList(listString);
			names.forEach(tokList::populate);
		}
		return tokList;
	}

	/** {@inheritDoc} */
	@Override
	public void setRev(final String rev) {
		setAttribute("rev", rev);
	}

	@Override
	public DOMTokenList getSizes() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setTarget(final String target)  {
		setAttribute("target", target);
	}

	/** {@inheritDoc} */
	@Override
	public void setType(final String type) {
		setAttribute("type", type);
	}


	@Override
	public StyleSheet getSheet() {
		if ("stylesheet".equals(getRel())) {
			final org.htmlunit.cssparser.dom.CSSStyleSheetImpl sheet = new org.htmlunit.cssparser.dom.CSSStyleSheetImpl();
			styleSheet = new CSSStyleSheetImpl(sheet);
		}

		return styleSheet;
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		if (XHtmlParser.MODIFYING_KEY.equals(key) && !Boolean.TRUE.equals(data)) {
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
