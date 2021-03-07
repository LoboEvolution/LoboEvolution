/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLScriptElement;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.net.HttpNetwork;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.UserDataHandler;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;

/**
 * <p>HTMLScriptElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLScriptElementImpl extends HTMLElementImpl implements HTMLScriptElement {
	private static final Logger logger = Logger.getLogger(HTMLScriptElementImpl.class.getName());

	private boolean defer;

	private String text;

	/**
	 * <p>Constructor for HTMLScriptElementImpl.</p>
	 */
	public HTMLScriptElementImpl() {
		super("SCRIPT", true);
	}

	/**
	 * <p>Constructor for HTMLScriptElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLScriptElementImpl(String name) {
		super(name, true);
	}

	/** {@inheritDoc} */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		// nop
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
	protected final void processScript() {
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
			final Context ctx = Executor.createContext(getDocumentURL(), bcontext);
			ctx.setLanguageVersion(Context.VERSION_1_8);
			ctx.setOptimizationLevel(-1);
			final String src = getSrc();
			try {
				if (Strings.isNotBlank(src)) {
					final URL scriptURL = ((HTMLDocumentImpl) doc).getFullURL(src);
					final String scriptURI = scriptURL == null ? src : scriptURL.toExternalForm();
					final URL u = new URL(scriptURI);
					final URLConnection connection = u.openConnection();
					connection.setRequestProperty("User-Agent", HttpNetwork.getUserAgentValue());
					try (InputStream in = HttpNetwork.openConnectionCheckRedirects(connection);
							Reader reader = new InputStreamReader(in, "utf-8")) {
						BufferedReader br = new BufferedReader(reader);						
						ctx.evaluateReader(scope, br, scriptURI, 1, null);
					} catch (SocketTimeoutException e) {
						logger.log(Level.SEVERE, "More than " + connection.getConnectTimeout() + " elapsed.");
				    } catch (Exception e) {
						logger.log(Level.SEVERE, e.getMessage(), e);
					}
				} else {
					String scriptURI = doc.getBaseURI();
					text = getText();
					ctx.evaluateString(scope, text, scriptURI, 1, null);
				}
			} catch (final RhinoException ecmaError) {
				final String error = ecmaError.sourceName() + ":" + ecmaError.lineNumber() + ": " + ecmaError.getMessage();
				logger.log(Level.WARNING, "Javascript error at " + error, ecmaError.getMessage());
			} catch (java.util.MissingResourceException mre) {
				logger.log(Level.WARNING, mre.getMessage());
			} catch (final Throwable err) {
				logger.log(Level.WARNING, "Unable to evaluate Javascript code", err);
			} finally {
				Context.exit();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setDefer(boolean defer) {
		this.defer = defer;
	}

	/** {@inheritDoc} */
	@Override
	public void setEvent(String event) {
		setAttribute("event", event);
	}

	/** {@inheritDoc} */
	@Override
	public void setHtmlFor(String htmlFor) {
		setAttribute("htmlFor", htmlFor);
	}

	/** {@inheritDoc} */
	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	/** {@inheritDoc} */
	@Override
	public void setText(String text) {
		this.text = text;
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
			processScript();
		}
		return super.setUserData(key, data, handler);
	}

	@Override
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAsync(boolean async) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getCrossOrigin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCrossOrigin(String crossOrigin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getIntegrity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setIntegrity(String integrity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isNoModule() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNoModule(boolean noModule) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getReferrerPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReferrerPolicy(String referrerPolicy) {
		// TODO Auto-generated method stub
		
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLScriptElement]";
	}
}
