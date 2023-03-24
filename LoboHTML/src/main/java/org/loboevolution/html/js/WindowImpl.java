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
/*
 * Created on Nov 12, 2005
 */
package org.loboevolution.html.js;

import com.gargoylesoftware.css.dom.CSSRuleListImpl;
import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.filter.BodyFilter;
import org.loboevolution.html.dom.nodeimpl.CommentImpl;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.TextImpl;
import org.loboevolution.html.dom.nodeimpl.traversal.NodeFilterImpl;
import org.loboevolution.html.dom.xpath.XPathResultImpl;
import org.loboevolution.html.js.css.MediaQueryListImpl;
import org.loboevolution.html.js.events.EventImpl;
import org.loboevolution.html.js.events.MouseEventImpl;
import org.loboevolution.html.js.events.UIEventImpl;
import org.loboevolution.html.js.storage.LocalStorage;
import org.loboevolution.html.js.storage.SessionStorage;
import org.loboevolution.html.js.xml.XMLHttpRequest;
import org.loboevolution.html.js.xml.XMLSerializerImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.node.css.ComputedCSSStyleDeclaration;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.history.History;
import org.loboevolution.html.node.js.Location;
import org.loboevolution.html.node.js.Navigator;
import org.loboevolution.html.node.js.Screen;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.node.js.console.Console;
import org.loboevolution.html.node.js.webstorage.Storage;
import org.loboevolution.html.node.traversal.NodeFilter;
import org.loboevolution.html.node.views.DocumentView;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.JavaInstantiator;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.events.EventException;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;

/**
 * <p>WindowImpl class.</p>
 */
public class WindowImpl extends WindowEventHandlersImpl implements Window {

	private static final Logger logger = Logger.getLogger(WindowImpl.class.getName());

	private static final Map<HtmlRendererContext, WeakReference<WindowImpl>> CONTEXT_WINDOWS = new WeakHashMap<>();

	private Map<Integer, TaskWrapper> taskMap;

	private List<String> msg;

	private static int timerIdCounter = 0;

	private int length;

	private int outerHeight;

	private int outerWidth;

	private int innerHeight;

	private int innerWidth;

	private boolean lengthSet = false;

	private String name = "";

	private volatile HTMLDocumentImpl document;

	private History history;

	private Location location;

	private Navigator navigator;

	private final HtmlRendererContext rcontext;

	private ScreenImpl screen;

	private Scriptable windowScope;

	private final UserAgentContext uaContext;

	private final HtmlRendererConfig config;

    
	/**
	 * <p>Constructor for WindowImpl.</p>
	 *
	 * @param rcontext  a {@link HtmlRendererContext} object.
	 * @param uaContext a {@link UserAgentContext} object.
	 * @param config a {@link HtmlRendererConfig} object.
	 */
	public WindowImpl(final HtmlRendererContext rcontext, final UserAgentContext uaContext, final HtmlRendererConfig config) {
		this.rcontext = rcontext;
		this.uaContext = uaContext;
		this.config = config;
	}

	/**
	 * <p>getWindow.</p>
	 *
	 * @param rcontext a {@link HtmlRendererContext} object.
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public static WindowImpl getWindow(HtmlRendererContext rcontext, final HtmlRendererConfig config) {
		if (rcontext == null) {
			return null;
		}
		synchronized (CONTEXT_WINDOWS) {
			final Reference<WindowImpl> wref = CONTEXT_WINDOWS.get(rcontext);
			if (wref != null) {
				final WindowImpl windowImpl = wref.get();
				if (windowImpl != null) {
					return windowImpl;
				}
			}
			final WindowImpl windowImpl = new WindowImpl(rcontext, rcontext.getUserAgentContext(), config);
			CONTEXT_WINDOWS.put(rcontext, new WeakReference<>(windowImpl));
			return windowImpl;
		}
	}

	/**
	 * <p>Setter for the field document.</p>
	 *
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	public void setDocument(final HTMLDocumentImpl document) {
		final HTMLDocumentImpl prevDocument = this.document;
		if (prevDocument != document) {
			final Function onunload = getOnunload();
			if (onunload != null) {
				Executor.executeFunction(this.getWindowScope(), onunload, prevDocument.getDocumentURL(), this.getUaContext());
				setOnunload(null);
			}

			if (prevDocument != null) {
				this.clearState();
			}
			this.forgetAllTasks();
			this.initWindowScope(document);
			document.setUserData(Executor.SCOPE_KEY, getWindowScope(), null);
			this.document = document;
		}
	}

	/**
	 * <p>getHtmlRendererContext.</p>
	 *
	 * @return a {@link HtmlRendererContext} object.
	 */
	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/**
	 * <p>getUserAgentContext.</p>
	 *
	 * @return a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public UserAgentContext getUserAgentContext() {
		return this.getUaContext();
	}

	/**
	 * <p>getWindow.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl getWindow() {
		return this;
	}

	/**
	 * <p>namedItem.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	public Node namedItem(final String name) {
		final HTMLDocumentImpl doc = this.document;
		if (doc == null) {
			return null;
		}
		return doc.getElementById(name);
	}

	/**
	 * <p>Getter for the field windowScope.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Scriptable} object.
	 */
	public Scriptable getWindowScope() {
		synchronized (this) {
			Scriptable windowScope = this.windowScope;
			if (windowScope != null) {
				return windowScope;
			}

			try(Context ctx = Context.enter()) {
				windowScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(this, null);
				windowScope = ctx.initSafeStandardObjects((ScriptableObject)windowScope);
				this.windowScope = windowScope;
				return windowScope;
			}
		}
	}

	/**
	 * <p>Getter for the field <code>uaContext</code>.</p>
	 *
	 * @return the uaContext
	 */
	public UserAgentContext getUaContext() {
		return uaContext;
	}

	@Override
	public HtmlRendererConfig getConfig() { return this.config; }

	/**
	 * <p>Getter for the field <code>msg</code>.</p>
	 *
	 * @return a {@link java.util.List} object.
	 */
	public List<String> getMsg() {
		return msg;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void alert(final String message) {
		if (this.rcontext != null) {
			if (this.rcontext.isTestEnabled()) {
				if (msg == null) msg = new ArrayList<>();
				msg.add(message);
			} else {
				this.rcontext.alert(message);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void back() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.back();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void blur() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.blur();
		}
	}

	/** {@inheritDoc} */
	@Override
	public void clearInterval(final int aTimerID) {
		final Integer key = aTimerID;
		forgetTask(key, true);
	}

	/** {@inheritDoc} */
	@Override
	public void clearTimeout(final int timeoutID) {
		final Integer key = timeoutID;
		forgetTask(key, true);
	}

	/** {@inheritDoc} */
	@Override
	public void close() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.close();
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean confirm(final String message) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.confirm(message);
		} else {
			return false;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void focus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.focus();
		}
	}

	/** {@inheritDoc} */
	@Override
	public ComputedCSSStyleDeclaration getComputedStyle(final Element element, final String pseudoElement) {
		if (element instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) element).getComputedStyle(pseudoElement);
		} else {
			throw new IllegalArgumentException("Element implementation unknown: " + element);
		}
	}

	/** {@inheritDoc} */
	@Override
    public ComputedCSSStyleDeclaration getComputedStyle(final Element elt) {
        return getComputedStyle(elt, null);
    }

	/** {@inheritDoc} */
	@Override
	public String getDefaultStatus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getDefaultStatus();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public DocumentView getDocument() {
		return this.document;
	}

	/** {@inheritDoc} */
	@Override
	public Document getDocumentNode() {
		return this.document;
	}

	/** {@inheritDoc} */
	@Override
	public History getHistory() {
		synchronized (this) {
			History history = this.history;
			if (history == null) {
				history = new HistoryImpl(this);
				this.history = history;
			}
			return history;
		}
	}

	/** {@inheritDoc} */
	@Override
    public Storage getLocalStorage() {
        return new LocalStorage(this);
    }

	/** {@inheritDoc} */
	@Override
    public Storage getSessionStorage() {
    	 return new SessionStorage(this.getHtmlRendererConfig());
    }

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		if (this.lengthSet) {
			return this.length;
		} else {
			final HTMLDocumentImpl doc = this.document;
			final List<Node> list = new LinkedList<>(Arrays.asList(doc.getNodeList(new BodyFilter()).toArray()));
			HTMLCollection collection = new HTMLCollectionImpl(doc, list);
			return collection.getLength();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Location getLocation() {
		synchronized (this) {
			Location location = this.location;
			if (location == null) {
				location = new LocationImpl(this);
				this.location = location;
			}
			return location;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getName() {
		return this.name;
	}

	/** {@inheritDoc} */
	@Override
	public Navigator getNavigator() {
		synchronized (this) {
			Navigator nav = this.navigator;
			if (nav == null) {
				nav = new NavigatorImpl(this);
				this.navigator = nav;
			}
			return nav;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Function getOnload() {
		final HTMLDocumentImpl doc = this.document;
		if (doc != null) {
			return doc.getOnloadHandler();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl getOpener() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null && rcontext.getOpener() != null) {
			return WindowImpl.getWindow(rcontext.getOpener(), this.config);
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl getParent() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null && rcontext.getParent() != null) {
			return WindowImpl.getWindow(rcontext.getParent(), this.config);
		} else {
			return this;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Screen getScreen() {
		synchronized (this) {
			ScreenImpl nav = this.screen;
			if (nav == null) {
				nav = new ScreenImpl();
				this.screen = nav;
			}
			return nav;
		}
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl getSelf() {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public String getStatus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getStatus();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl getTop() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null && rcontext.getTop() != null) {
			return WindowImpl.getWindow(rcontext.getTop(), this.config);
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean isClosed() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.isClosed();
		} else {
			return false;
		}
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl open(final String url) {
		return this.open(url, "windows");
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl open(final String url, final String windowName) {
		return this.open(url, windowName, "", false);
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl open(final String url, final String windowName, final String windowFeatures) {
		return this.open(url, windowName, windowFeatures, false);
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl open(final String relativeUrl, final String windowName, final String windowFeatures, final boolean replace) {
		final HtmlRendererContext rcontext = this.rcontext;
		URL url;
		final HTMLDocumentImpl document = this.document;
		if (document != null) {
			url = document.getFullURL(relativeUrl);
		} else {
			try {
				url = new URL(relativeUrl);
			} catch (final MalformedURLException mfu) {
				throw new IllegalArgumentException("Malformed URI: " + relativeUrl);
			}
		}

		if (url != null && rcontext != null) {
			final HtmlRendererContext newContext = rcontext.open(url, windowName, windowFeatures, replace);
			return getWindow(newContext, this.config);
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String prompt(final String message) {
		return this.prompt(message, "");
	}

	/** {@inheritDoc} */
	@Override
	public String prompt(final String message, final Object inputDefault) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.prompt(message, inputDefault.toString());
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void resizeBy(double byWidth, final double byHeight) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeBy(byWidth, byHeight);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void resizeTo(final double width, final double height) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeTo(width, height);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy(final double x, final double y) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scrollBy(x, y);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void scrollTo(final double x, final double y) {
		scroll(x, y);
	}

	/** {@inheritDoc} */
	@Override
	public void scroll(final double x, final double y) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scroll(x, y);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int setInterval(final Object aFunction, final double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = timeID;
		ActionListener task = null;

		if(aFunction instanceof Function){
			Function function = (Function) aFunction;
			task = new FunctionTimerTask(this, timeIDInt, function, false);
		}

		if(aFunction instanceof String){
			String aExpression = (String) aFunction;
			task = new ExpressionTimerTask(this, timeIDInt, aExpression, false);
		}

		int t = (int) aTimeInMs;
		if (t < 1) {
			t = 1;
		}
		final Timer timer = new Timer(t, task);
		timer.setRepeats(true); // The only difference with setTimeout
		putAndStartTask(timeIDInt, timer, aFunction);
		return timeID;
	}

	private static int generateTimerID() {
		synchronized (logger) {
			return timerIdCounter++;
		}
	}

	/**
	 * <p>Setter for the field length.</p>
	 *
	 * @param length a int.
	 */
	public void setLength(final int length) {
		this.lengthSet = true;
		this.length = length;
	}

	/** {@inheritDoc} */
	@Override
	public void setLocation(final String location) {
		getLocation().setHref(location);
	}

	/** {@inheritDoc} */
	@Override
	public void setLocation(final Location location) {
		this.location = location;
	}

	/** {@inheritDoc} */
	@Override
	public void setOnload(final Function onload) {
		final HTMLDocumentImpl doc = this.document;
		if (doc != null) {
			doc.setOnloadHandler(onload);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setOpener(final Window opener) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			if (opener == null) {
				rcontext.setOpener(null);
			} else {
				final WindowImpl win = (WindowImpl)opener;
				rcontext.setOpener(win.rcontext);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setStatus(final String message) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.setStatus(message);
		}
	}

	/** {@inheritDoc} */
	@Override
	public int setTimeout(final Object function) {
		return setTimeout(function, 0);
	}

	/** {@inheritDoc} */
	@Override
	public int setTimeout(final Object function, final double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = timeID;
		ActionListener task = null;

		if(function instanceof Function) {
			Function fun = (Function) function;
			task = new FunctionTimerTask(this, timeIDInt, fun, true);
		}

		if(function instanceof String){
			String expr = (String)function;
			task = new ExpressionTimerTask(this, timeIDInt, expr, true);
		}

		int t = (int) millis;
		if (t < 1) {
			t = 1;
		}
		final Timer timer = new Timer(t, task);
		timer.setRepeats(false);
		putAndStartTask(timeIDInt, timer, function);
		return timeID;
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(final String type, final Function listener) {
		addEventListener(type, listener, false);
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(final String type, final Function listener, final boolean useCapture) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.addEventListener(type, listener, useCapture);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean dispatchEvent(final Event evt) throws EventException {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			return doc.dispatchEvent(doc, evt);
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(final String type, final Function listener) {
		removeEventListener(type, listener, false);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(final String type, final Function listener, final boolean useCapture) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.removeEventListener(type, listener, useCapture);
		}
	}

	/** {@inheritDoc} */
	@Override
	public double getInnerHeight() {
		if(innerHeight > 0) {
			return innerHeight;
		}
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getInnerHeight();
		}
        return -1;
    }

	/**
	 * <p>setInnerHeight.</p>
	 *
	 * @param innerHeight a boolean.
	 */
	@Override
	public void setInnerHeight(double innerHeight) {
		this.innerHeight = Double.valueOf(innerHeight).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public double getInnerWidth() {
		if(innerWidth > 0) {
			return innerWidth;
		}

		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getInnerWidth();
		}
        return -1;
    }

	/**
	 * <p>setInnerWidth.</p>
	 *
	 * @param innerWidth a boolean.
	 */
	@Override
	public void setInnerWidth(double innerWidth) {
		this.innerWidth = Double.valueOf(innerWidth).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public double getOuterHeight() {
		if(outerHeight > 0) {
			return outerHeight;
		}

		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getOuterHeight();
		}
        return -1;
    }

	/**
	 * <p>setOuterHeight.</p>
	 *
	 * @param outerHeight a boolean.
	 */
	@Override
	public void setOuterHeight(double outerHeight) {
		this.outerHeight = Double.valueOf(outerHeight).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public double getOuterWidth() {
		if(outerWidth > 0) {
			return outerWidth;
		}
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getOuterWidth();
		}
        return -1;
    }

	/**
	 * <p>setOuterWidth.</p>
	 *
	 * @param outerWidth a boolean.
	 */
	@Override
	public void setOuterWidth(double outerWidth) {
		this.outerWidth = Double.valueOf(outerWidth).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public MediaQueryListImpl matchMedia(final String mediaQueryString) {
		return new MediaQueryListImpl(this, mediaQueryString);
	}

	/** {@inheritDoc} */
	@Override
	public String atob(String encodedString) throws DOMException {
		final int l = encodedString.length();
		for (int i = 0; i < l; i++) {
			if (encodedString.charAt(i) > 255) {
				throw new DOMException(DOMException.INVALID_CHARACTER_ERR, "The string to be decoded is not correctly encoded.");
			}
		}

		final byte[] bytes = encodedString.getBytes(StandardCharsets.UTF_8);
		return new String(Base64.getDecoder().decode(bytes), StandardCharsets.UTF_8);
	}

	/** {@inheritDoc} */
	@Override
	public String btoa(String rawString) {
		if(rawString == null) {
			rawString = "null";
		}

		final byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
		return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
	}

	/** {@inheritDoc} */
	@Override
	public Console getConsole() {
		return new ConsoleImpl(this.getHtmlRendererConfig());
	}

	/** {@inheritDoc} */
	@Override
	public Navigator getClientInformation() {
		return getNavigator();
	}

	/** {@inheritDoc} */
	@Override
	public void setDefaultStatus(final String defaultStatus) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public double getDevicePixelRatio() {
		return 1;
	}

	/** {@inheritDoc} */
	@Override
	public String getDoNotTrack() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isIsSecureContext() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public BarProp getLocationbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public BarProp getMenubar() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void setName(final String name) {
		this.name = name;
		
	}

	/** {@inheritDoc} */
	@Override
	public double getPageXOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getPageYOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public BarProp getPersonalbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public double getScreenLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getScreenTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getScreenX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getScreenY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public double getScrollY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public BarProp getScrollbars() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public BarProp getStatusbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public BarProp getToolbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean confirm() {
		// TODO Auto-generated method stub
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public CSSRuleListImpl getMatchedCSSRules(final Element elt, final String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public CSSRuleListImpl getMatchedCSSRules(final Element elt) {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void moveBy(final double x, final double y) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void moveTo(final double x, final double y) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public Window open() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void postMessage(final String message, final String targetOrigin) {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public String prompt() {
		// TODO Auto-generated method stub
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void scroll() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollBy() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void scrollTo() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl get(final int index) {
		return this;
	}

	/** {@inheritDoc} */
	@Override
	public WindowImpl getFrames(){
		return this;
	}

	private void initWindowScope(final Document doc) {
		final Scriptable ws = this.getWindowScope();
		final JavaScript js = JavaScript.getInstance();
		JavaInstantiator jiXhttp = () -> {
			HTMLDocumentImpl hd = (HTMLDocumentImpl) doc;
			return new XMLHttpRequest(getUaContext(), hd.getDocumentURL(), ws);
		};

		JavaInstantiator jidomp = () -> new DOMParserImpl(document);
		JavaInstantiator jiloc = () -> new LocalStorage(this);

		js.defineJsObject(ws, "XMLHttpRequest", XMLHttpRequest.class, jiXhttp);
		js.defineJsObject(ws, "DOMParser",  DOMParserImpl.class, jidomp);

		js.defineJsObject(ws, "XMLSerializer",  XMLSerializerImpl.class, XMLSerializerImpl::new);
		js.defineJsObject(ws, "XPathResult", XPathResultImpl.class, XPathResultImpl::new);
		js.defineJsObject(ws, "MouseEvent", MouseEventImpl.class, MouseEventImpl::new);
		js.defineJsObject(ws, "UIEvent",  UIEventImpl.class, MouseEventImpl::new);
		js.defineJsObject(ws, "Element", Element.class, MouseEventImpl::new);
		js.defineJsObject(ws, "Event", EventImpl.class, EventImpl::new);
		js.defineJsObject(ws, "Text", TextImpl.class, TextImpl::new);
		js.defineJsObject(ws, "Storage", LocalStorage.class, jiloc);


		js.defineElementClass(ws, doc, "Comment", "comment", CommentImpl.class);
		js.defineElementClass(ws, doc, "Image", "img", HTMLImageElementImpl.class);
		js.defineElementClass(ws, doc, "Script", "script", HTMLScriptElementImpl.class);
		js.defineElementClass(ws, doc, "IFrame", "iframe", HTMLIFrameElementImpl.class);
		js.defineElementClass(ws, doc, "Option", "option", HTMLOptionElementImpl.class);
		js.defineElementClass(ws, doc, "Select", "select", HTMLSelectElementImpl.class);
		js.defineElementClass(ws, doc, "Console", "console", ConsoleImpl.class);
		js.defineElementClass(ws, doc, "HTMLDivElement", "div", HTMLDivElementImpl.class);
		js.defineElementClass(ws, doc, "HTMLElement", "html", HTMLElementImpl.class);
		js.defineElementClass(ws, doc, "HTMLDocument", "document", HTMLDocumentImpl.class);

		js.defineElementClass(ws, doc, "NodeFilter", "NodeFilter", NodeFilterImpl.class);
		js.defineElementClass(ws, doc, "Node", "Node", NodeImpl.class);

	}

	private void forgetAllTasks() {
		TaskWrapper[] oldTaskWrappers = null;
		synchronized (this) {
			final Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTaskWrappers = taskMap.values().toArray(new TaskWrapper[0]);
				this.taskMap = null;
			}
		}
		if (oldTaskWrappers != null) {
			for (final TaskWrapper taskWrapper : oldTaskWrappers) {
				taskWrapper.timer.stop();
			}
		}
	}

	private void clearState() {

		try(Context cx = Context.enter()) {
			Scriptable s = this.getWindowScope();
			if (s != null) {
				Object[] ids = s.getIds();
				for (Object id : ids) {
					if (id instanceof String) {
						s.delete((String)id);
					} else if (id instanceof Integer) {
						s.delete((Integer) id);
					}
				}
			}
		}
	}

	private void putAndStartTask(final Integer timeoutID, final Timer timer, final Object retained) {
		TaskWrapper oldTaskWrapper = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap == null) {
				taskMap = new HashMap<>(4);
				this.taskMap = taskMap;
			} else {
				oldTaskWrapper = taskMap.get(timeoutID);
			}
			taskMap.put(timeoutID, new TaskWrapper(timer, retained));
		}
		// Do this outside synchronized block, just in case.
		if (oldTaskWrapper != null) {
			oldTaskWrapper.timer.stop();
		}
		timer.start();
	}

	void forgetTask(final Integer timeoutID, final boolean cancel) {
		TaskWrapper oldTimer = null;
		synchronized (this) {
			final Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTimer = taskMap.remove(timeoutID);
			}
		}
		if (oldTimer != null && cancel) {
			oldTimer.timer.stop();
		}
	}

	public NodeFilter getNodeFilter() {
		return new NodeFilterImpl();
	}

	public Node getNode() {
		return this;
	}


	@Override
	public String toString() {
		return "[object Window]";
	}
}
