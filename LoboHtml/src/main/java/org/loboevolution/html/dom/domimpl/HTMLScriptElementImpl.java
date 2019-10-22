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
package org.loboevolution.html.dom.domimpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLScriptElement;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.http.UserAgentContext;
import org.mozilla.javascript.Context;
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
		
		final Document doc = this.document;
		final Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);
		
        if (scope == null) {
            throw new IllegalStateException("Scriptable (scope) instance was expected to be keyed as UserData to document using " + Executor.SCOPE_KEY);
        }

		if (bcontext.isScriptingEnabled()) {
			final Context ctx = Executor.createContext(getDocumentURL(), bcontext);
            ctx.setLanguageVersion(Context.VERSION_1_8);
            ctx.setOptimizationLevel(-1);
			final String src = getSrc();

			if (Strings.isNotBlank(src)) {
				BufferedReader br = null;
				try {
	                this.informExternalScriptLoading();
	                URL scriptURL = ((HTMLDocumentImpl) doc).getFullURL(src);
	                String scriptURI = scriptURL == null ? src : scriptURL.toExternalForm();
					final URL url = new URL(src);
					br = new BufferedReader(new InputStreamReader(url.openStream()));
					ctx.evaluateReader(scope, br, scriptURI, 1, null);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (br != null) {
							br.close();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else {
                String scriptURI = doc.getBaseURI();
				text = getText();
				ctx.evaluateString(scope, text, scriptURI, 1, null);
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
		if (HtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			processScript();
		}
		return super.setUserData(key, data, handler);
	}
}
