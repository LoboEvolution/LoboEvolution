/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.dom.domimpl;

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLScriptElement;
import org.loboevolution.gui.HtmlPanel;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.renderstate.DisplayRenderState;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.info.TimingInfo;
import org.loboevolution.net.AlgorithmDigest;
import org.loboevolution.net.HttpNetwork;
import org.loboevolution.net.IOUtil;
import org.loboevolution.net.UserAgent;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import org.loboevolution.html.dom.UserDataHandler;

import java.io.*;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

/**
 * <p>HTMLScriptElementImpl class.</p>
 */
@Slf4j
public class HTMLScriptElementImpl extends HTMLElementImpl implements HTMLScriptElement {
	private boolean defer;

	private String text;

	/**
	 * <p>Constructor for HTMLScriptElementImpl.</p>
	 */
	public HTMLScriptElementImpl() {
		super("SCRIPT");
	}

	/**
	 * <p>Constructor for HTMLScriptElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLScriptElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(final StringBuilder buffer) {
		// nop
	}

	@Override
	public Boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAsync(Object async) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getCrossOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCrossOrigin(final String crossOrigin) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefer() {
		return this.defer;
	}

	/** {@inheritDoc} */
	@Override
	public String getEvent() {
		return getAttribute("event");
	}

	/** {@inheritDoc} */
	@Override
	public String getHtmlFor() {
		return getAttribute("htmlFor");
	}

	/** {@inheritDoc} */
	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	/** {@inheritDoc} */
	@Override
	public String getText() {
		final String t = this.text;
		if (t == null) {
			return getRawInnerText(true);
		} else {
			return t;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getType() {
		return getAttribute("type");
	}

	/**
	 * <p>processScript.</p>
	 */
	private void processScript() {
		final UserAgentContext bcontext = getUserAgentContext();
		if (bcontext == null) {
			throw new IllegalStateException("No user agent context.");
		}

		final Document doc = this.document;
		final Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);

		if (scope == null) {
			throw new IllegalStateException(
					"Scriptable (scope) instance was expected to be keyed as UserData to document using "
							+ Executor.SCOPE_KEY);
		}

		if (bcontext.isScriptingEnabled()) {
			try (Context ctx = Executor.createContext()) {
				ctx.setLanguageVersion(Context.VERSION_1_8);
				ctx.setOptimizationLevel(-1);
				final String src = getSrc();
				final Instant start = Instant.now();

				if (Strings.isNotBlank(src)) {
					final TimingInfo info = new TimingInfo();
					final URL scriptURL = ((HTMLDocumentImpl) doc).getFullURL(src);
					final String scriptURI = scriptURL == null ? src : scriptURL.toExternalForm();
					info.setName(scriptURL != null ? scriptURL.getFile() : new URI(scriptURI).toURL().getFile());

					try (InputStream in = getStream(scriptURL, scriptURI, info)) {
						if (AlgorithmDigest.validate(IOUtil.readFully(in), getIntegrity())) {
							try (final Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
								final BufferedReader br = new BufferedReader(reader);
								ctx.evaluateReader(scope, br, scriptURI, 1, null);
							} catch (Exception e) {
								throw new Exception(e);
							}
						}
					} catch (final SocketTimeoutException e) {
						info.setHttpResponse(400);
					} catch (final Exception e) {
						log.error(e.getMessage(), e);
					} finally {
						final Instant finish = Instant.now();
						final long timeElapsed = Duration.between(start, finish).toMillis();
						info.setTimeElapsed(timeElapsed);
						info.setPath(scriptURI);
						info.setHttpResponse(200);

						final HtmlRendererContext htmlRendererContext = this.getHtmlRendererContext();
						final HtmlPanel htmlPanel = htmlRendererContext.getHtmlPanel();
						htmlPanel.getBrowserPanel().getTimingList.add(info);
					}
				} else {
					final String scriptURI = doc.getBaseURI();
					text = getText();
					ctx.evaluateString(scope, text, scriptURI, 1, null);
				}
			} catch (final RhinoException ecmaError) {
				final String error = ecmaError.sourceName() + ":" + ecmaError.lineNumber() + ": " + ecmaError.getMessage();
				log.error("Javascript error at {}", error);
			} catch (final Throwable err) {
				log.error("Unable to evaluate Javascript code", err);
			}
		}
	}

	private InputStream getStream(URL scriptURL, String scriptURI, TimingInfo info) throws Exception {
		if (Urls.isLocalFile(scriptURL)) {
			return Files.newInputStream(Paths.get(scriptURI.replace("file://", "")));
		} else {
			final URLConnection connection = scriptURL.openConnection();
			connection.setRequestProperty("User-Agent", UserAgent.getUserAgent());
			connection.getHeaderField("Set-Cookie");
			info.setType(connection.getContentType());
			return HttpNetwork.openConnectionCheckRedirects(connection);
		}
	}


	/** {@inheritDoc} */
	@Override
	public void setDefer(final boolean defer) {
		this.defer = defer;
	}

	/** {@inheritDoc} */
	@Override
	public void setEvent(final String event) {
		setAttribute("event", event);
	}

	/** {@inheritDoc} */
	@Override
	public void setHtmlFor(final String htmlFor) {
		setAttribute("htmlFor", htmlFor);
	}

	/** {@inheritDoc} */
	@Override
	public void setSrc(final String src) {
		setAttribute("src", src);
	}

	/** {@inheritDoc} */
	@Override
	public void setText(final String text) {
		this.text = text;
	}

	/** {@inheritDoc} */
	@Override
	public void setType(final String type) {
		setAttribute("type", type);
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		if (XHtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			processScript();
		}
		return super.setUserData(key, data, handler);
	}
	@Override
	public String getIntegrity() {
		return getAttribute("integrity");
	}

	@Override
	public void setIntegrity(final String integrity) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isNoModule() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNoModule(final boolean noModule) {
		// TODO Auto-generated method stub
	}

	@Override
	public String getReferrerPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReferrerPolicy(final String referrerPolicy) {
		// TODO Auto-generated method stub
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return new DisplayRenderState(prevRenderState, this, RenderState.DISPLAY_NONE);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLScriptElement]";
	}
}
