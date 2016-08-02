/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 8, 2005
 */
package org.lobobrowser.html.domimpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.http.HttpRequest;
import org.lobobrowser.http.Method;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.w3c.html.HTMLScriptElement;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.EvaluatorException;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.UserDataHandler;

/**
 * The Class HTMLScriptElementImpl.
 */
public class HTMLScriptElementImpl extends HTMLElementImpl implements
HTMLScriptElement {

    /** The Constant logger. */
    private static final Logger logger = Logger
            .getLogger(HTMLScriptElementImpl.class.getName());

    /** The Constant loggableInfo. */
    private static final boolean loggableInfo = logger.isLoggable(Level.INFO);

    /**
     * Instantiates a new HTML script element impl.
     */
    public HTMLScriptElementImpl() {
        super(HtmlProperties.SCRIPT, true);
    }

    /**
     * Instantiates a new HTML script element impl.
     *
     * @param name
     *            the name
     */
    public HTMLScriptElementImpl(String name) {
        super(name, true);
    }

    /** The text. */
    private String text;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#getText()
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
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#setText(java.lang.String)
     */
    @Override
    public void setText(String text) {
        this.text = text;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#getHtmlFor()
     */
    @Override
    public String getHtmlFor() {
        return this.getAttribute(HtmlAttributeProperties.HTMLFOR);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#setHtmlFor(java.lang.String)
     */
    @Override
    public void setHtmlFor(String htmlFor) {
        this.setAttribute(HtmlAttributeProperties.HTMLFOR, htmlFor);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#getEvent()
     */
    @Override
    public String getEvent() {
        return this.getAttribute(HtmlAttributeProperties.EVENT);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#setEvent(java.lang.String)
     */
    @Override
    public void setEvent(String event) {
        this.setAttribute(HtmlAttributeProperties.EVENT, event);
    }

    /** The defer. */
    private boolean defer;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#getDefer()
     */
    @Override
    public boolean getDefer() {
        return this.defer;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#setDefer(boolean)
     */
    @Override
    public void setDefer(boolean defer) {
        this.defer = defer;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#getSrc()
     */
    @Override
    public String getSrc() {
        return this.getAttribute(HtmlAttributeProperties.SRC);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#setSrc(java.lang.String)
     */
    @Override
    public void setSrc(String src) {
        this.setAttribute(HtmlAttributeProperties.SRC, src);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#getType()
     */
    @Override
    public String getType() {
        return this.getAttribute(HtmlAttributeProperties.TYPE);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLScriptElement#setType(java.lang.String)
     */
    @Override
    public void setType(String type) {
        this.setAttribute(HtmlAttributeProperties.TYPE, type);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#setUserData(java.lang.String,
     * java.lang.Object, org.w3c.dom.UserDataHandler)
     */
    @Override
    public Object setUserData(String key, Object data, UserDataHandler handler) {
        if (org.lobobrowser.html.parser.HtmlParser.MODIFYING_KEY.equals(key)
                && (data != Boolean.TRUE)) {
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

				boolean liflag = loggableInfo;
				if (src == null) {
					text = this.getText();
					scriptURI = doc.getBaseURI();
					baseLineNumber = 1; // TODO: Line number of inner text??
				} else {
					this.informExternalScriptLoading();
					URL scriptURL = ((HTMLDocumentImpl) doc).getFullURL(src);
					scriptURI = scriptURL == null ? src : scriptURL
							.toExternalForm();
					long time1 = liflag ? System.currentTimeMillis() : 0;
					try {
						final HttpRequest request = bcontext
								.createHttpRequest();
						// Perform a synchronous request
						SecurityManager sm = System.getSecurityManager();
						if (sm == null) {
							try {
								request.open(Method.GET, getFullURL(scriptURI), false);
								request.send();
							} catch (IOException thrown) {
								logger.log(Level.WARNING, "processScript()",
										thrown);
							}
						} else {
							AccessController
									.doPrivileged(new PrivilegedAction<Object>() {
										@Override
										public Object run() {
											// Code might have restrictions on
											// accessing
											// items from elsewhere.
											try {
												request.open(Method.GET, getFullURL(scriptURI), false);
												request.send();
											} catch (IOException thrown) {
												logger.log(Level.WARNING,
														"processScript()",
														thrown);
											}
											return null;
										}
									});
						}
						int status = request.getStatus();
						if ((status != 200) && (status != 0)) {
							text = httpURLConnection(scriptURI);
						} else {
							text = request.getResponseText();
						}

					} finally {
						if (liflag) {
							long time2 = System.currentTimeMillis();
							logger.info("processScript(): Loaded external Javascript from URI=["
									+ scriptURI
									+ "] in "
									+ (time2 - time1)
									+ " ms.");
						}
					}
					baseLineNumber = 1;
				}
				Context ctx = Executor.createContext(this.getDocumentURL(),
						bcontext);
				try {
					Scriptable scope = (Scriptable) doc
							.getUserData(Executor.SCOPE_KEY);
					if (scope != null) {

						long time1 = liflag ? System.currentTimeMillis() : 0;
						if (text != null) {
							ctx.evaluateString(scope, text, scriptURI,
									baseLineNumber, null);
							if (liflag) {
								long time2 = System.currentTimeMillis();
								logger.info("addNotify(): Evaluated (or attempted to evaluate) Javascript in "
										+ (time2 - time1) + " ms.");
							}
						} else {
							logger.severe("No Script at uri "  + scriptURI);
						}
					} else {
						logger.severe("No Scope");
					}

				} catch (EcmaError ecmaError) {
					logger.log(Level.WARNING,
							"Javascript error at " + ecmaError.sourceName()
									+ ":" + ecmaError.columnNumber() + ": "
									+ ecmaError.getMessage());
				} catch (EvaluatorException e){
					logger.log(Level.WARNING, e.getMessage());
				} catch (MissingResourceException err) {
					logger.log(Level.WARNING, "Missing Resource");
				} catch (Exception err) {
					logger.severe("scriptURI: " + scriptURI);
					logger.log(Level.WARNING,
							"Unable to evaluate Javascript code", err);
				} finally {
					Context.exit();
				}
			}
		} else {
			logger.severe("No user agent context");
		}
	}
    
    /**
	 * Http url connection.
	 *
	 * @param url the url
	 * @return the string
	 */
	private static String httpURLConnection(String srtUrl) {

		StringBuffer response = new StringBuffer();
		int responseCode = -1;
		try {

			URL url = new URL(srtUrl);
			URI uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
			URL obj = uri.toURL();
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestProperty("User-Agent", UserAgentContext.DEFAULT_USER_AGENT);
			con.setRequestMethod("GET");
			responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

		} catch (Exception e) {
			logger.warning("Unable to parse script. URI=[" + srtUrl + "]. Response status was " + responseCode + ".");
			return "";
		}
		return response.toString();
	}

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.domimpl.DOMNodeImpl#appendInnerTextImpl(java.lang.
     * StringBuffer)
     */
    @Override
    protected void appendInnerTextImpl(StringBuffer buffer) {
        // nop
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
