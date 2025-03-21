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
/*
 * Created on Nov 12, 2005
 */
package org.loboevolution.html.js;

import lombok.Getter;
import org.htmlunit.cssparser.dom.CSSRuleListImpl;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.canvas.ImageDataImpl;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.filter.BodyFilter;
import org.loboevolution.html.dom.nodeimpl.*;
import org.loboevolution.html.dom.nodeimpl.RangeImpl;
import org.loboevolution.html.dom.nodeimpl.traversal.NodeFilterImpl;
import org.loboevolution.html.dom.xpath.XPathResultImpl;
import org.loboevolution.html.js.audio.AudioContextImpl;
import org.loboevolution.html.js.css.MediaQueryListImpl;
import org.loboevolution.html.js.events.*;
import org.loboevolution.html.js.storage.LocalStorage;
import org.loboevolution.html.js.storage.SessionStorage;
import org.loboevolution.html.js.xml.*;
import org.loboevolution.html.node.*;
import org.loboevolution.css.ComputedCSSStyleDeclaration;
import org.loboevolution.events.Event;
import org.loboevolution.html.dom.History;
import org.loboevolution.html.node.Node;
import org.loboevolution.js.*;
import org.loboevolution.js.console.Console;
import org.loboevolution.js.webstorage.Storage;
import org.loboevolution.traversal.NodeFilter;
import org.loboevolution.views.DocumentView;
import org.loboevolution.http.UserAgentContext;
import org.mozilla.javascript.*;
import org.w3c.dom.events.EventException;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>WindowImpl class.</p>
 */
public class WindowImpl extends WindowEventHandlersImpl implements Window {

	private static final Map<HtmlRendererContext, WeakReference<WindowImpl>> CONTEXT_WINDOWS = new WeakHashMap<>();

	private Map<Integer, TaskWrapper> taskMap;

	@Getter
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

	@Getter
	private final UserAgentContext uaContext;

	@Getter
	private final HtmlRendererConfig config;

	@Getter
	private LoboContextFactory contextFactory;

    
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
		this.contextFactory = new LoboContextFactory();
	}

	/**
	 * <p>getWindow.</p>
	 *
	 * @param rcontext a {@link HtmlRendererContext} object.
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public static WindowImpl getWindow(final HtmlRendererContext rcontext, final HtmlRendererConfig config) {
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
			try (Context ctx = contextFactory.enterContext()) {
				final Function onunload = getOnunload();
				if (onunload != null) {
					Executor.executeFunction(this.getWindowScope(ctx), onunload, contextFactory);
					setOnunload(null);
				}

				if (prevDocument != null) {
					this.clearState();
				}
				this.forgetAllTasks();
				this.initWindowScope(document);
				document.setUserData(Executor.SCOPE_KEY, getWindowScope(ctx), null);
				this.document = document;
			}
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
	 * {@inheritDoc}
	 */
	@Override
	public Node namedItem(final String name) {
		final HTMLDocumentImpl doc = this.document;
		if (doc == null) {
			return null;
		}

		final HTMLAllCollectionImpl all = (HTMLAllCollectionImpl) doc.getall();
		final AtomicReference<Node> find = new AtomicReference<>();
		all.forEach(node -> {
			if (node.hasAttributes()) {
				final NamedNodeMap attributes = node.getAttributes();
				if (attributes != null) {
					for (final Node attribute : Nodes.iterable(attributes)) {
						if (name.equals(attribute.getNodeValue())) {
							find.set(node);
						}
					}
				}
			}

			if (node.hasChildNodes()) {
				findChild(node, find);
			}
		});
		return find.get();
	}

	private void findChild(final Node node, final AtomicReference<Node> find) {
		final NodeListImpl childNodes = (NodeListImpl) node.getChildNodes();
		childNodes.forEach(nde -> {
			if (nde.hasAttributes()) {
				final NamedNodeMap attributes = nde.getAttributes();
				for (final Node attribute : Nodes.iterable(attributes)) {
					if (name.equals(attribute.getNodeValue())) {
						find.set(nde);
					}
				}
			}

			if (nde.hasChildNodes()) {
				findChild(nde, find);
			}
		});
	}

	/**
	 * <p>Getter for the field windowScope.</p>
	 *
	 * @param context a {@link Context} object.
	 * @return a {@link org.mozilla.javascript.Scriptable} object.
	 */
	public Scriptable getWindowScope(Context context) {
		synchronized (this) {
			Scriptable windowScope = this.windowScope;
			if (windowScope != null) {
				return windowScope;
			}
			windowScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(this, null);
			windowScope = context.initSafeStandardObjects((ScriptableObject) windowScope);
			this.windowScope = windowScope;
			return windowScope;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void alert(final String message) {
		if (this.rcontext != null) {
			if (this.rcontext.isTestEnabled()) {
				if (msg == null) msg = new ArrayList<>();
				msg.add(message == null ? "null" : message);
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
			return ((HTMLElementImpl) element).getComputedStyle(Strings.isNotBlank(pseudoElement) ? pseudoElement : element.getNodeName());
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
		final HTMLDocumentImpl doc = this.document;
		return new SessionStorage(doc.getHtmlRendererConfig());
	}

	/** {@inheritDoc} */
	@Override
	public int getLength() {
		if (this.lengthSet) {
			return this.length;
		} else {
			final HTMLDocumentImpl doc = this.document;
			final HTMLCollection collection = new HTMLCollectionImpl(doc, new BodyFilter());
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
		final URL url;
		final HTMLDocumentImpl document = this.document;
		if (document != null) {
			url = document.getFullURL(relativeUrl);
		} else {
			try {
				url = new URI(relativeUrl).toURL();
			} catch (Exception mfu) {
				throw new IllegalArgumentException("Error URI: " + relativeUrl);
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
	public void resizeBy(final double byWidth, final double byHeight) {
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

		if(aFunction instanceof Function function){
            task = new FunctionTimerTask(this, timeIDInt, function, false);
		}

		if(aFunction instanceof String aExpression){
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
		return timerIdCounter++;
	}

	/**
	 * <p>Setter for the field length.</p>
	 *
	 * @param length a {@link java.lang.Integer} object.
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

		if(function instanceof Function fun) {
            task = new FunctionTimerTask(this, timeIDInt, fun, true);
		}

		if(function instanceof String expr){
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

	@Override
	public boolean dispatchEvent(final Node element, final Event evt) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			return doc.dispatchEvent(element, evt);
		}
		return false;
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
	public void setInnerHeight(final double innerHeight) {
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
	public void setInnerWidth(final double innerWidth) {
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
	public void setOuterHeight(final double outerHeight) {
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
	public void setOuterWidth(final double outerWidth) {
		this.outerWidth = Double.valueOf(outerWidth).intValue();
	}

	/** {@inheritDoc} */
	@Override
	public MediaQueryListImpl matchMedia(final String mediaQueryString) {
		return new MediaQueryListImpl(this, mediaQueryString);
	}

	/** {@inheritDoc} */
	@Override
	public String atob(final String encodedString) throws DOMException {
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
	public String btoa(final String rString) {
		String rawString = rString;
		if(rawString == null) {
			rawString = "null";
		}

		final byte[] bytes = rawString.getBytes(StandardCharsets.UTF_8);
		return new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
	}

	/** {@inheritDoc} */
	@Override
	public Console getConsole() {
		final HTMLDocumentImpl doc = this.document;
		return new ConsoleImpl(doc.getHtmlRendererConfig());
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
		return new SelectionImpl();
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
		try (Context cx = contextFactory.enterContext()) {
			final Scriptable ws = this.getWindowScope(cx);
			setScope(doc, ws);
		}
	}

	private void setScope(final Document doc, Scriptable ws) {
		final JavaScript js = JavaScript.getInstance();
		final JavaInstantiator jiInputEvent = InputEventImpl::new;
		final JavaInstantiator jiUIEvent = UIEventImpl::new;
		final JavaInstantiator jiEvent = EventImpl::new;
		final JavaInstantiator jiSubmitEvent = SubmitEventImpl::new;
		final JavaInstantiator jiMouseEvent = MouseEventImpl::new;
		final JavaInstantiator jiWheelEvent = WheelEventImpl::new;
		final JavaInstantiator jiKeyboardEvent = KeyboardEventImpl::new;
		final JavaInstantiator jiAnimationEvent = AnimationEventImpl::new;
		final JavaInstantiator jiMessageEvent = MessageEventImpl::new;
		final JavaInstantiator jiMutationEvent = MutationEventImpl::new;
		final JavaInstantiator jiCustomEvent = CustomEventImpl::new;
		final JavaInstantiator jiCloseEvent = CloseEventImpl::new;
		final JavaInstantiator jiCompositionEvent = CompositionEventImpl::new;
		final JavaInstantiator jiPointerEvent = PointerEventImpl::new;
		final JavaInstantiator jiPopStateEvent = PopStateEventImpl::new;
		final JavaInstantiator jiProgressEvent = ProgressEventImpl::new;
		final JavaInstantiator jiFocusEvent = FocusEventImpl::new;
		final JavaInstantiator jiBeforeInstallPromptEvent = BeforeInstallPromptEventImpl::new;
		final JavaInstantiator jiBeforeUnloadEvent = BeforeUnloadEventImpl::new;
		final JavaInstantiator jiBlobEvent = BlobEventImpl::new;
		final JavaInstantiator jiTransitionEvent = TransitionEventImpl::new;
		final JavaInstantiator jiDragEvent = DragEventImpl::new;
		final JavaInstantiator jiGamepadEvent = GamepadEventimpl::new;
		final JavaInstantiator jiAudioProcessingEvent = AudioProcessingEventImpl::new;
		final JavaInstantiator jiTouchEvent = TouchEventImpl::new;
		final JavaInstantiator jiDeviceMotionEvent = DeviceMotionEventImpl::new;
		final JavaInstantiator jiDeviceOrientationEvent = DeviceOrientationEventImpl::new;
		final JavaInstantiator jiHashChangeEvent = HashChangeEventImpl::new;
		final JavaInstantiator jiPageTransitionEvent = PageTransitionEventImpl::new;
		final JavaInstantiator jiStorageEvent = StorageEventImpl::new;
		final JavaInstantiator jiTrackEvent = TrackEventImpl::new;
		final JavaInstantiator jiErrorEvent = ErrorEventImpl::new;

		final JavaInstantiator jiXTarget = (args) -> new XMLHttpRequestEventTargetImpl(document);
		final JavaInstantiator jiXUpload = (args) -> new XMLHttpRequestUploadImpl(document);
		final JavaInstantiator jiXhttp = (args) -> new XMLHttpRequestImpl(document, ws, this);
		final JavaInstantiator jiDomp = (args) -> new DOMParserImpl(document);
		final JavaInstantiator jiform = (args) -> new FormDataImpl(document);
		final JavaInstantiator jiloc = (args) -> new LocalStorage(this);
		final JavaInstantiator jiElement = (args) -> new ElementImpl("");
		final JavaInstantiator jiXSeralizer = (args) -> new XMLSerializerImpl();
		final JavaInstantiator jiXPath = (args) -> new XPathResultImpl();
		final JavaInstantiator jiXMLDocument = (args) -> new XMLDocument();
		final JavaInstantiator jiText = (args) -> new TextImpl();
		final JavaInstantiator jiAudioContext = (args) -> new AudioContextImpl();
		final JavaInstantiator jiBlob = (args) -> new BlobImpl();
		final JavaInstantiator jiImageData = (args) -> new ImageDataImpl();


		js.defineJsObject(ws, "Event", EventImpl.class, jiEvent);
		js.defineJsObject(ws, "UIEvent", UIEventImpl.class, jiUIEvent);
		js.defineJsObject(ws, "InputEvent", InputEventImpl.class, jiInputEvent);
		js.defineJsObject(ws, "MouseEvent", MouseEventImpl.class, jiMouseEvent);
		js.defineJsObject(ws, "WheelEvent", WheelEventImpl.class, jiWheelEvent);
		js.defineJsObject(ws, "SubmitEvent", SubmitEventImpl.class, jiSubmitEvent);
		js.defineJsObject(ws, "KeyboardEvent", KeyboardEventImpl.class, jiKeyboardEvent);
		js.defineJsObject(ws, "AnimationEvent", AnimationEventImpl.class, jiAnimationEvent);
		js.defineJsObject(ws, "MessageEvent", MessageEventImpl.class, jiMessageEvent);
		js.defineJsObject(ws, "MutationEvent", MutationEventImpl.class, jiMutationEvent);
		js.defineJsObject(ws, "CustomEvent", CustomEventImpl.class, jiCustomEvent);
		js.defineJsObject(ws, "CloseEvent", CloseEventImpl.class, jiCloseEvent);
		js.defineJsObject(ws, "CompositionEvent", CompositionEventImpl.class, jiCompositionEvent);
		js.defineJsObject(ws, "PointerEvent", PointerEventImpl.class, jiPointerEvent);
		js.defineJsObject(ws, "PopStateEvent", PopStateEventImpl.class, jiPopStateEvent);
		js.defineJsObject(ws, "ProgressEvent", ProgressEventImpl.class, jiProgressEvent);
		js.defineJsObject(ws, "FocusEvent", FocusEventImpl.class, jiFocusEvent);
		js.defineJsObject(ws, "BeforeInstallPromptEvent", BeforeInstallPromptEventImpl.class, jiBeforeInstallPromptEvent);
		js.defineJsObject(ws, "BeforeUnloadEvent", BeforeUnloadEventImpl.class, jiBeforeUnloadEvent);
		js.defineJsObject(ws, "BlobEvent", BlobEventImpl.class, jiBlobEvent);
		js.defineJsObject(ws, "TransitionEvent", TransitionEventImpl.class, jiTransitionEvent);
		js.defineJsObject(ws, "DragEvent", DragEventImpl.class, jiDragEvent);
		js.defineJsObject(ws, "GamepadEvent", GamepadEventimpl.class, jiGamepadEvent);
		js.defineJsObject(ws, "AudioProcessingEvent", AudioProcessingEventImpl.class, jiAudioProcessingEvent);
		js.defineJsObject(ws, "TouchEvent", TouchEventImpl.class, jiTouchEvent);
		js.defineJsObject(ws, "DeviceMotionEvent", DeviceMotionEventImpl.class, jiDeviceMotionEvent);
		js.defineJsObject(ws, "DeviceOrientationEvent", DeviceOrientationEventImpl.class, jiDeviceOrientationEvent);
		js.defineJsObject(ws, "HashChangeEvent", HashChangeEventImpl.class, jiHashChangeEvent);
		js.defineJsObject(ws, "PageTransitionEvent", PageTransitionEventImpl.class, jiPageTransitionEvent);
		js.defineJsObject(ws, "StorageEvent", StorageEventImpl.class, jiStorageEvent);
		js.defineJsObject(ws, "TrackEvent", TrackEventImpl.class, jiTrackEvent);
		js.defineJsObject(ws, "ErrorEvent", ErrorEventImpl.class, jiErrorEvent);

		js.defineJsObject(ws, "XMLHttpRequestEventTarget", XMLHttpRequestEventTargetImpl.class, jiXTarget);
		js.defineJsObject(ws, "XMLHttpRequestUpload", XMLHttpRequestUploadImpl.class, jiXUpload);
		js.defineJsObject(ws, "XMLHttpRequest", XMLHttpRequestImpl.class, jiXhttp);
		js.defineJsObject(ws, "DOMParser", DOMParserImpl.class, jiDomp);
		js.defineJsObject(ws, "InputEvent", InputEventImpl.class, jiInputEvent);
		js.defineJsObject(ws, "FormData", FormDataImpl.class, jiform);
		js.defineJsObject(ws, "LocalStorage", LocalStorage.class, jiloc);
		js.defineJsObject(ws, "XMLSerializer", XMLSerializerImpl.class, jiXSeralizer);
		js.defineJsObject(ws, "XPathResult", XPathResultImpl.class, jiXPath);
		js.defineJsObject(ws, "XMLDocument", XMLDocument.class, jiXMLDocument);
		js.defineJsObject(ws, "Element", ElementImpl.class, jiElement);
		js.defineJsObject(ws, "Text", TextImpl.class, jiText);
		js.defineJsObject(ws, "AudioContext", AudioContextImpl.class, jiAudioContext);
		js.defineJsObject(ws, "Blob", BlobImpl.class, jiBlob);
		js.defineJsObject(ws, "ImageData", ImageDataImpl.class, jiImageData);

		js.defineElementClass(ws, doc, "Comment", "comment", CommentImpl.class);
		js.defineElementClass(ws, doc, "Image", "img", HTMLImageElementImpl.class);
		js.defineElementClass(ws, doc, "Script", "script", HTMLScriptElementImpl.class);
		js.defineElementClass(ws, doc, "IFrame", "iframe", HTMLIFrameElementImpl.class);
		js.defineElementClass(ws, doc, "Option", "option", HTMLOptionElementImpl.class);
		js.defineElementClass(ws, doc, "Select", "select", HTMLSelectElementImpl.class);
		js.defineElementClass(ws, doc, "HTMLDivElement", "div", HTMLDivElementImpl.class);
		js.defineElementClass(ws, doc, "HTMLElement", "html", HTMLElementImpl.class);
		js.defineElementClass(ws, doc, "NodeFilter", "NodeFilter", NodeFilterImpl.class);
		js.defineElementClass(ws, doc, "HTMLDialogElement", "HTMLDialogElement", HTMLDialogElementImpl.class);
		js.defineElementClass(ws, doc, "Node", "Node", NodeImpl.class);
		js.defineElementClass(ws, doc, "Range", "Range", RangeImpl.class);



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
		try (final Context cx = contextFactory.enterContext()) {
			final Scriptable s = this.getWindowScope(cx);
			if (s != null) {
				final Object[] ids = s.getIds();
				for (final Object id : ids) {
					if (id instanceof String) {
						s.delete((String) id);
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

	@Override
	public String toString() {
		return "[object Window]";
	}
}
