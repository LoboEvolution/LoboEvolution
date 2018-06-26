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
/*
 * Created on Oct 8, 2005
 */
package org.loboevolution.html.domimpl;

import java.net.URL;
import java.util.MissingResourceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.dombl.ExternalResourcesCache;
import org.loboevolution.html.js.Executor;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.w3c.html.HTMLScriptElement;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.UserDataHandler;

/**
 * The Class HTMLScriptElementImpl.
 */
public class HTMLScriptElementImpl extends HTMLElementImpl implements HTMLScriptElement {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HTMLScriptElementImpl.class);
	
	/** The text. */
	private String text;

	/** The defer. */
	private boolean defer;


	/**
	 * Instantiates a new HTML script element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLScriptElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#getText()
	 */
	@Override
	public String getText() {
		String t = this.text;
		if (t == null) {
			return this.getRawInnerText(true);
		} else {
			return t;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#setText(java.lang.String)
	 */
	@Override
	public void setText(String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#getHtmlFor()
	 */
	@Override
	public String getHtmlFor() {
		return this.getAttribute(HTMLFOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLScriptElement#setHtmlFor(java.lang.String)
	 */
	@Override
	public void setHtmlFor(String htmlFor) {
		this.setAttribute(HTMLFOR, htmlFor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#getEvent()
	 */
	@Override
	public String getEvent() {
		return this.getAttribute(EVENT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.w3c.html.HTMLScriptElement#setEvent(java.lang.String)
	 */
	@Override
	public void setEvent(String event) {
		this.setAttribute(EVENT, event);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#getDefer()
	 */
	@Override
	public boolean getDefer() {
		return this.defer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#setDefer(boolean)
	 */
	@Override
	public void setDefer(boolean defer) {
		this.defer = defer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#getSrc()
	 */
	@Override
	public String getSrc() {
		return this.getAttribute(SRC);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#setSrc(java.lang.String)
	 */
	@Override
	public void setSrc(String src) {
		this.setAttribute(SRC, src);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#getType()
	 */
	@Override
	public String getType() {
		return this.getAttribute(TYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.w3c.html.HTMLScriptElement#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.setAttribute(TYPE, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.domimpl.DOMNodeImpl#setUserData(java.lang.String,
	 * java.lang.Object, org.w3c.dom.UserDataHandler)
	 */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (org.loboevolution.html.parser.HtmlParser.MODIFYING_KEY.equals(key) && data != Boolean.TRUE) {
			this.processScript();
		}
		return super.setUserData(key, data, handler);
	}

	/**
	 * Process script.
	 */
	protected final void processScript() {
		UserAgentContext bcontext = this.getUserAgentContext();
		if (bcontext != null && bcontext.isScriptingEnabled()) {
			String text;
			final String scriptURI;
			int baseLineNumber;
			String src = this.getSrc();
			Document doc = this.document;
			if (doc instanceof HTMLDocumentImpl) {
				if (src == null) {
					text = this.getText();
					scriptURI = doc.getBaseURI();
					baseLineNumber = 1;
				} else {
					this.informExternalScriptLoading();
					URL scriptURL = ((HTMLDocumentImpl) doc).getFullURL(src);
					scriptURI = scriptURL == null ? src : scriptURL.toExternalForm();
					text = ExternalResourcesCache.getSourceCache(scriptURI, "JS");
					baseLineNumber = 1;
				}
				Context ctx = Executor.createContext(this.getDocumentURL(), bcontext);
				try {
					Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);
					if (scope != null) {
						if (text != null) {
							ctx.evaluateString(scope, text, scriptURI, baseLineNumber, null);
						} else {
							logger.error("No Script at uri " + scriptURI);
						}
					} else {
						logger.error("No Scope");
					}

				} catch (EcmaError ecmaError) {
					logger.error("Javascript error at " + ecmaError.sourceName() + ":" + ecmaError.columnNumber() + ": " + ecmaError);
				} catch (EvaluatorException e) {
					logger.error(e);
				} catch (MissingResourceException err) {
					logger.error("Missing Resource");
				} catch (Exception err) {
					logger.error("Unable to evaluate Javascript code", err);
				} finally {
					Context.exit();
				}
			}
		} else {
			logger.error("No user agent context");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.domimpl.DOMNodeImpl#appendInnerTextImpl(java.lang.
	 * StringBuilder )
	 */
	@Override
	protected void appendInnerTextImpl(StringBuilder buffer) {
		// Method not implemented
	}

	@Override
	public boolean getAsync() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAsync(boolean async) {
		// TODO Auto-generated method stub

	}
}
