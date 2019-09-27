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
/*
 * Created on Oct 8, 2005
 */
package org.lobobrowser.html.dom.domimpl;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobo.store.ExternalResourcesStore;
import org.lobobrowser.html.dom.HTMLScriptElement;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.UserAgentContext;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.UserDataHandler;

public class HTMLScriptElementImpl extends HTMLElementImpl implements HTMLScriptElement {
	private static final Logger logger = Logger.getLogger(HTMLScriptElementImpl.class.getName());

	private boolean defer;

	private String text;

	public HTMLScriptElementImpl() {
		super("SCRIPT", true);
	}

	public HTMLScriptElementImpl(String name) {
		super(name, true);
	}

	@Override
	protected void appendInnerTextImpl(StringBuffer buffer) {
		// nop
	}

	@Override
	public boolean getDefer() {
		return this.defer;
	}

	@Override
	public String getEvent() {
		return getAttribute("event");
	}

	@Override
	public String getHtmlFor() {
		return getAttribute("htmlFor");
	}

	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	@Override
	public String getText() {
		final String t = this.text;
		if (t == null) {
			return getRawInnerText(true);
		} else {
			return t;
		}
	}

	@Override
	public String getType() {
		return getAttribute("type");
	}

	protected final void processScript() {
		final UserAgentContext bcontext = getUserAgentContext();
		if (bcontext == null) {
			throw new IllegalStateException("No user agent context.");
		}
		if (bcontext.isScriptingEnabled()) {
			String text = "";
			final String scriptURI;
			int baseLineNumber;
			final String src = getSrc();
			final Document doc = this.document;
			if (!(doc instanceof HTMLDocumentImpl)) {
				throw new IllegalStateException("no valid document");
			}
			if (src == null) {
				text = getText();
				scriptURI = doc.getBaseURI();
				baseLineNumber = 1;
			} else {
				this.informExternalScriptLoading();
				URL scriptURL = ((HTMLDocumentImpl) doc).getFullURL(src);
				scriptURI = scriptURL == null ? src : scriptURL.toExternalForm();
				text = ExternalResourcesStore.getSourceCache(scriptURI, "JS");
				baseLineNumber = 1;
			}
			final Context ctx = Executor.createContext(getDocumentURL(), bcontext);
			try {
				final Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);
				if (scope == null) {
					throw new IllegalStateException(
							"Scriptable (scope) instance was expected to be keyed as UserData to document using "
									+ Executor.SCOPE_KEY);
				}
				try {
					if (text == null) {
						throw new java.lang.IllegalStateException("Script source is null: " + this + ".");
					}
					ctx.setLanguageVersion(Context.VERSION_1_8);
					ctx.evaluateString(scope, text, scriptURI, baseLineNumber, null);
					
                } catch (final RhinoException ecmaError) {
                    logger.log(Level.WARNING, "Javascript error at " + ecmaError.sourceName() + ":" + ecmaError.lineNumber() + ": " + ecmaError.getMessage(), ecmaError.getMessage());
				} catch (final Throwable err) {
					logger.log(Level.WARNING, "Unable to evaluate Javascript code", err);
				}
			} finally {
				Context.exit();
			}
		}
	}

	@Override
	public void setDefer(boolean defer) {
		this.defer = defer;
	}

	@Override
	public void setEvent(String event) {
		setAttribute("event", event);
	}

	@Override
	public void setHtmlFor(String htmlFor) {
		setAttribute("htmlFor", htmlFor);
	}

	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void setType(String type) {
		setAttribute("type", type);
	}

	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (org.lobobrowser.html.parser.HtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			processScript();
		}
		return super.setUserData(key, data, handler);
	}
}
