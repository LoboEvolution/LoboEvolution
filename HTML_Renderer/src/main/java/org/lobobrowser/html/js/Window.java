/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.WeakHashMap;

import javax.swing.Timer;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.util.ID;
import org.lobobrowser.w3c.html.ApplicationCache;
import org.lobobrowser.w3c.html.BarProp;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.w3c.html.MessagePort;
import org.lobobrowser.w3c.html.Selection;
import org.lobobrowser.w3c.html.StyleMedia;
import org.lobobrowser.w3c.html.UndoManager;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSS2Properties;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;

/**
 * The Class Window.
 */
public class Window extends JSFunction implements AbstractView {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(Window.class.getName());

	/** The Constant CONTEXT_WINDOWS. */
	private static final Map<HtmlRendererContext, WeakReference<Window>> CONTEXT_WINDOWS = new WeakHashMap<HtmlRendererContext, WeakReference<Window>>();

	/** The timer id counter. */
	private static int timerIdCounter = 0;

	/** The rcontext. */
	private final HtmlRendererContext rcontext;

	/** The ua context. */
	private final UserAgentContext uaContext;

	/** The navigator. */
	private Navigator navigator;

	/** The screen. */
	private Screen screen;

	/** The location. */
	private Location location;

	/** The history. */
	private History history;

	/** The length. */
	private int length;

	/** The name. */
	private String name;

	/** The length set. */
	private boolean lengthSet = false;

	/** The name set. */
	private boolean nameSet = false;
	
	/**
	 * Instantiates a new window.
	 *
	 * @param rcontext
	 *            the rcontext
	 * @param uaContext
	 *            the ua context
	 */
	public Window(HtmlRendererContext rcontext, UserAgentContext uaContext) {
		super(uaContext);
		// TODO: Probably need to create a new Window instance
		// for every document. Sharing of Window state between
		// different documents is not correct.
		this.rcontext = rcontext;
		this.uaContext = uaContext;
	}
	
	/**
	 * Generate timer id.
	 *
	 * @return the int
	 */
	private static int generateTimerID() {
		synchronized (logger) {
			return timerIdCounter++;
		}
	}

	/**
	 * Gets the html renderer context.
	 *
	 * @return the html renderer context
	 */
	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/**
	 * Gets the user agent context.
	 *
	 * @return the user agent context
	 */
	public UserAgentContext getUserAgentContext() {
		return this.uaContext;
	}

	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.views.AbstractView#getDocument()
	 */
	@Override
	public DocumentView getDocument() {
		return this.getWindowDocument();
	}

	/**
	 * Gets the document node.
	 *
	 * @return the document node
	 */
	public Document getDocumentNode() {
		return this.getWindowDocument();
	}

	/**
	 * Sets the interval.
	 *
	 * @param aFunction
	 *            Javascript function to invoke on each loop.
	 * @param aTimeInMs
	 *            Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a href=
	 *      "http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window
	 *      .setInterval interface definition</a> TODO Make proper and refactore
	 *      with {@link Window#setTimeout(Function, double)}.
	 */
	public int setInterval(final Function aFunction, final double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new FunctionTimerTask(this, timeIDInt, aFunction, false);
		int t = (int) aTimeInMs;
		if (t < 1) {
			t = 1;
		}
		Timer timer = new Timer(t, task);
		timer.setRepeats(true); // The only difference with setTimeout
		this.putAndStartTask(timeIDInt, timer, aFunction);
		return timeID;
	}

	/**
	 * Sets the interval.
	 *
	 * @param aExpression
	 *            Javascript expression to invoke on each loop.
	 * @param aTimeInMs
	 *            Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a href=
	 *      "http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window
	 *      .setInterval interface definition</a> TODO Make proper and refactore
	 *      with {@link Window#setTimeout(String, double)}.
	 */
	public int setInterval(final String aExpression, double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new ExpressionTimerTask(this, timeIDInt, aExpression, false);
		int t = (int) aTimeInMs;
		if (t < 1) {
			t = 1;
		}
		Timer timer = new Timer(t, task);
		timer.setRepeats(false); // The only difference with setTimeout
		this.putAndStartTask(timeIDInt, timer, null);
		return timeID;
	}

	/**
	 * Clear interval.
	 *
	 * @param aTimerID
	 *            Timer ID to stop.
	 * @see <a href=
	 *      "http://developer.mozilla.org/en/docs/DOM:window.clearInterval">
	 *      Window.clearInterval interface Definition</a>
	 */
	public void clearInterval(int aTimerID) {
		Integer key = new Integer(aTimerID);
		this.forgetTask(key, true);
	}

	/**
	 * Alert.
	 *
	 * @param message
	 *            the message
	 */
	public void alert(String message) {
		if (this.rcontext != null) {
			this.rcontext.alert(message);
		}
	}

	/**
	 * Back.
	 */
	public void back() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.back();
		}
	}

	/**
	 * Blur.
	 */
	public void blur() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.blur();
		}
	}

	/**
	 * Clear timeout.
	 *
	 * @param timeoutID
	 *            the timeout id
	 */
	public void clearTimeout(int timeoutID) {
		Integer key = new Integer(timeoutID);
		this.forgetTask(key, true);
	}

	/**
	 * Close.
	 */
	public void close() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.close();
		}
	}

	/**
	 * Confirm.
	 *
	 * @param message
	 *            the message
	 * @return true, if successful
	 */
	public boolean confirm(String message) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.confirm(message);
		} else {
			return false;
		}
	}

	/**
	 * Eval.
	 *
	 * @param javascript
	 *            the javascript
	 * @return the object
	 */
	public Object eval(String javascript) {
		HTMLDocumentImpl document = this.getWindowDocument();
		if (document == null) {
			throw new IllegalStateException("Cannot evaluate if document is not set.");
		}
		Context ctx = Executor.createContext(document.getDocumentURL(), this.uaContext);
		try {
			Scriptable scope = this.getWindowScope();
			if (scope == null) {
				throw new IllegalStateException(
						"Scriptable (scope) instance was expected to be keyed as UserData to document using "
								+ Executor.SCOPE_KEY);
			}
			String scriptURI = "window.eval";
			if (logger.isEnabled(Level.INFO)) {
				logger.info("eval(): javascript follows...\r\n" + javascript);
			}
			return ctx.evaluateString(scope, javascript, scriptURI, 1, null);
		} finally {
			Context.exit();
		}
	}

	/**
	 * Focus.
	 */
	public void focus() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.focus();
		}
	}
	
	/**
	 * Gets the window.
	 *
	 * @param rcontext
	 *            the rcontext
	 * @return the window
	 */
	public static Window getWindow(HtmlRendererContext rcontext) {
		if (rcontext == null) {
			return null;
		}
		synchronized (CONTEXT_WINDOWS) {
			Reference<?> wref = CONTEXT_WINDOWS.get(rcontext);
			if (wref != null) {
				Window window = (Window) wref.get();
				if (window != null) {
					return window;
				}
			}
			Window window = new Window(rcontext, rcontext.getUserAgentContext());
			CONTEXT_WINDOWS.put(rcontext, new WeakReference<Window>(window));
			return window;
		}
	}

	/**
	 * Open.
	 *
	 * @param relativeUrl
	 *            the relative url
	 * @param windowName
	 *            the window name
	 * @param windowFeatures
	 *            the window features
	 * @param replace
	 *            the replace
	 * @return the window
	 */
	public void open(String relativeUrl, String windowName, String windowFeatures, boolean replace) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			URL url;
			Object document = this.getWindowDocument();
			if (document instanceof HTMLDocumentImpl) {
				url = ((HTMLDocumentImpl) document).getFullURL(relativeUrl);
			} else {
				try {
					url = new URL(relativeUrl);
				} catch (MalformedURLException mfu) {
					throw new IllegalArgumentException("Malformed URI: " + relativeUrl);
				}
			}
			rcontext.open(url, windowName, windowFeatures, replace);
		}
	}

	/**
	 * Open.
	 */
	public void open() {
		this.open("", "window:" + String.valueOf(ID.generateLong()));
	}

	/**
	 * Open.
	 *
	 * @param url
	 *            the url
	 */
	public void open(String url) {
		this.open(url, "window:" + String.valueOf(ID.generateLong()));
	}

	/**
	 * Open.
	 *
	 * @param url
	 *            the url
	 * @param windowName
	 *            the window name
	 */
	public void open(String url, String windowName) {
		this.open(url, windowName, null, false);
	}

	/**
	 * Open.
	 *
	 * @param url
	 *            the url
	 * @param windowName
	 *            the window name
	 * @param windowFeatures
	 *            the window features
	 */
	public void open(String url, String windowName, String windowFeatures) {
		this.open(url, windowName, windowFeatures, false);
	}

	/**
	 * Prompt.
	 *
	 * @param message
	 *            the message
	 * @return the string
	 */
	public String prompt(String message) {
		return this.prompt(message, "");
	}

	/**
	 * Prompt.
	 *
	 * @param message
	 *            the message
	 * @param inputDefault
	 *            the input default
	 * @return the string
	 */
	public String prompt(String message, int inputDefault) {
		return this.prompt(message, String.valueOf(inputDefault));
	}

	/**
	 * Prompt.
	 *
	 * @param message
	 *            the message
	 * @param inputDefault
	 *            the input default
	 * @return the string
	 */
	public String prompt(String message, String inputDefault) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.prompt(message, inputDefault);
		} else {
			return null;
		}
	}

	/**
	 * Scroll to.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void scrollTo(int x, int y) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scroll(x, y);
		}
	}

	/**
	 * Scroll by.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void scrollBy(int x, int y) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scrollBy(x, y);
		}
	}

	/**
	 * Resize to.
	 *
	 * @param width
	 *            the width
	 * @param height
	 *            the height
	 */
	public void resizeTo(int width, int height) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeTo(width, height);
		}
	}

	/**
	 * Resize by.
	 *
	 * @param byWidth
	 *            the by width
	 * @param byHeight
	 *            the by height
	 */
	public void resizeBy(int byWidth, int byHeight) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeBy(byWidth, byHeight);
		}
	}

	/**
	 * Sets the timeout.
	 *
	 * @param expr
	 *            the expr
	 * @param millis
	 *            the millis
	 * @return the int
	 */
	public int setTimeout(final String expr, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new ExpressionTimerTask(this, timeIDInt, expr, true);
		int t = (int) millis;
		if (t < 1) {
			t = 1;
		}
		Timer timer = new Timer(t, task);
		timer.setRepeats(false);
		this.putAndStartTask(timeIDInt, timer, null);
		return timeID;
	}

	/**
	 * Sets the timeout.
	 *
	 * @param function
	 *            the function
	 * @param millis
	 *            the millis
	 * @return the int
	 */
	public int setTimeout(final Function function, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new FunctionTimerTask(this, timeIDInt, function, true);
		int t = (int) millis;
		if (t < 1) {
			t = 1;
		}
		Timer timer = new Timer(t, task);
		timer.setRepeats(false);
		this.putAndStartTask(timeIDInt, timer, function);
		return timeID;
	}

	/**
	 * Sets the timeout.
	 *
	 * @param expr
	 *            the expr
	 * @return the int
	 */
	public int setTimeout(final String expr) {
		return setTimeout(expr, 0);
	}

	/**
	 * Checks if is closed.
	 *
	 * @return true, if is closed
	 */
	public boolean isClosed() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.isClosed();
		} else {
			return false;
		}
	}

	/**
	 * Gets the default status.
	 *
	 * @return the default status
	 */
	public String getDefaultStatus() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getDefaultStatus();
		} else {
			return null;
		}
	}

	/**
	 * Gets the frames.
	 *
	 * @return the frames
	 */
	public HTMLCollection getFrames() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getFrames();
		}
		return null;
	}

	/**
	 * Gets the length.
	 *
	 * @return the length
	 */
	public int getLength() {
		if (this.lengthSet) {
			return this.length;
		} else {
			HTMLCollection frames = this.getFrames();
			return frames == null ? 0 : frames.getLength();
		}
	}

	/**
	 * Sets the length.
	 *
	 * @param length
	 *            the new length
	 */
	public void setLength(int length) {
		this.lengthSet = true;
		this.length = length;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		if (this.nameSet) {
			return this.name;
		} else {
			HtmlRendererContext rcontext = this.rcontext;
			if (rcontext != null) {
				return rcontext.getName();
			} else {
				return null;
			}
		}
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.nameSet = true;
		this.name = name;
	}

	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Window getParent() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getParent());
		} else {
			return null;
		}
	}

	/**
	 * Gets the opener.
	 *
	 * @return the opener
	 */
	public Window getOpener() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getOpener());
		} else {
			return null;
		}
	}

	/**
	 * Sets the opener.
	 *
	 * @param opener
	 *            the new opener
	 */
	public void setOpener(Window opener) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			if (opener == null) {
				rcontext.setOpener(null);
			} else {
				rcontext.setOpener(opener.rcontext);
			}
		}
	}

	/**
	 * Gets the self.
	 *
	 * @return the self
	 */
	public Window getSelf() {
		return this;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getStatus();
		} else {
			return null;
		}
	}

	/**
	 * Sets the status.
	 *
	 * @param message
	 *            the new status
	 */
	public void setStatus(String message) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.setStatus(message);
		}
	}

	/**
	 * Gets the top.
	 *
	 * @return the top
	 */
	public Window getTop() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getTop());
		} else {
			return null;
		}
	}

	/**
	 * Gets the window.
	 *
	 * @return the window
	 */
	public Window getWindow() {
		return this;
	}

	/**
	 * Gets the navigator.
	 *
	 * @return the navigator
	 */
	public Navigator getNavigator() {
		synchronized (this) {
			Navigator nav = this.navigator;
			if (nav == null) {
				nav = new Navigator(this.uaContext);
				this.navigator = nav;
			}
			return nav;
		}
	}

	/**
	 * Gets the screen.
	 *
	 * @return the screen
	 */
	public Screen getScreen() {
		synchronized (this) {
			Screen nav = this.screen;
			if (nav == null) {
				nav = new Screen();
				this.screen = nav;
			}
			return nav;
		}
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation() {
		synchronized (this) {
			Location location = this.location;
			if (location == null) {
				location = new Location(this);
				this.location = location;
			}
			return location;
		}
	}

	/**
	 * Sets the location.
	 *
	 * @param location
	 *            the new location
	 */
	public void setLocation(String location) {
		this.getLocation().setHref(location);
	}

	/**
	 * Gets the history.
	 *
	 * @return the history
	 */
	public History getHistory() {
		synchronized (this) {
			History history = this.history;
			if (history == null) {
				history = new History(this);
				this.history = history;
			}
			return history;
		}
	}

	/**
	 * Gets the computed style.
	 *
	 * @param element
	 *            the element
	 * @param pseudoElement
	 *            the pseudo element
	 * @return the computed style
	 */
	public CSS2Properties getComputedStyle(HTMLElement element, String pseudoElement) {
		if (element instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) element).getComputedStyle(pseudoElement);
		} else {
			throw new IllegalArgumentException("Element implementation unknown: " + element);
		}
	}

	/**
	 * Gets the computed style.
	 *
	 * @param elt
	 *            the elt
	 * @return the computed style
	 */
	public CSS2Properties getComputedStyle(HTMLElement elt) {
		return getComputedStyle(elt, null);
	}

	/**
	 * Gets the onload.
	 *
	 * @return the onload
	 */
	public Function getOnload() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnloadHandler();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onload.
	 *
	 * @param onload
	 *            the new onload
	 */
	public void setOnload(Function onload) {
		// Note that body.onload overrides
		// window.onload.
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnloadHandler(onload);
		}
	}

	/**
	 * Named item.
	 *
	 * @param name
	 *            the name
	 * @return the node
	 */
	public Node namedItem(String name) {
		HTMLDocumentImpl doc = this.getWindowDocument();
		if (doc == null) {
			return null;
		}
		Node node = doc.getElementById(name);
		if (node != null) {
			return node;
		}
		return null;
	}

	/**
	 * Force gc.
	 */
	public void forceGC() {
		System.gc();
	}

	/**
	 * The Class WeakWindowTask.
	 */
	private static abstract class WeakWindowTask implements ActionListener {

		/** The window ref. */
		private final WeakReference<Window> windowRef;

		/**
		 * Instantiates a new weak window task.
		 *
		 * @param window
		 *            the window
		 */
		public WeakWindowTask(Window window) {
			this.windowRef = new WeakReference<Window>(window);
		}

		/**
		 * Gets the window.
		 *
		 * @return the window
		 */
		protected Window getWindow() {
			WeakReference<Window> ref = this.windowRef;
			return ref == null ? null : (Window) ref.get();
		}
	}

	/**
	 * The Class FunctionTimerTask.
	 */
	private static class FunctionTimerTask extends WeakWindowTask {
		// Implemented as a static WeakWindowTask to allow the Window
		// to get garbage collected, especially in infinite loop
		// scenarios.
		/** The time id int. */
		private final Integer timeIDInt;

		/** The function ref. */
		private final WeakReference<Function> functionRef;

		/** The remove task. */
		private final boolean removeTask;

		/**
		 * Instantiates a new function timer task.
		 *
		 * @param window
		 *            the window
		 * @param timeIDInt
		 *            the time id int
		 * @param function
		 *            the function
		 * @param removeTask
		 *            the remove task
		 */
		public FunctionTimerTask(Window window, Integer timeIDInt, Function function, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.functionRef = new WeakReference<Function>(function);
			this.removeTask = removeTask;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// This executes in the GUI thread and that's good.
			try {
				Window window = this.getWindow();
				if (window == null) {
					if (logger.isEnabled(Level.INFO)) {
						logger.info("actionPerformed(): Window is no longer available.");
					}
					return;
				}
				if (this.removeTask) {
					window.forgetTask(this.timeIDInt, false);
				}
				HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
				if (doc == null) {
					throw new IllegalStateException("Cannot perform operation when document is unset.");
				}
				Function function = this.functionRef.get();
				if (function == null) {
					throw new IllegalStateException("Cannot perform operation. Function is no longer available.");
				}
				Executor.executeFunction(window.getWindowScope(), function, doc.getDocumentURL(),
						window.getUserAgentContext());
			} catch (Throwable err) {
				logger.error("actionPerformed()", err.getCause());
			}
		}
	}

	/**
	 * The Class ExpressionTimerTask.
	 */
	private static class ExpressionTimerTask extends WeakWindowTask {
		// Implemented as a static WeakWindowTask to allow the Window
		// to get garbage collected, especially in infinite loop
		// scenarios.
		/** The time id int. */
		private final Integer timeIDInt;

		/** The expression. */
		private final String expression;

		/** The remove task. */
		private final boolean removeTask;

		/**
		 * Instantiates a new expression timer task.
		 *
		 * @param window
		 *            the window
		 * @param timeIDInt
		 *            the time id int
		 * @param expression
		 *            the expression
		 * @param removeTask
		 *            the remove task
		 */
		public ExpressionTimerTask(Window window, Integer timeIDInt, String expression, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.expression = expression;
			this.removeTask = removeTask;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.
		 * ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// This executes in the GUI thread and that's good.
			try {
				Window window = this.getWindow();
				if (window == null) {
					if (logger.isEnabled(Level.INFO)) {
						logger.info("actionPerformed(): Window is no longer available.");
					}
					return;
				}
				if (this.removeTask) {
					window.forgetTask(this.timeIDInt, false);
				}
				HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
				if (doc == null) {
					throw new IllegalStateException("Cannot perform operation when document is unset.");
				}
				window.eval(this.expression);
			} catch (Throwable err) {
				logger.error("actionPerformed()", err.getCause());
			}
		}
	}

	/**
	 * Adds the event listener.
	 *
	 * @param script
	 *            the script
	 * @param function
	 *            the function
	 */
	public void addEventListener(String script, Function function) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).addEventListener(script, function);
		}
	}

	public void removeEventListener(String script, Function function) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).removeEventListener(script, function);
		}
	}

	/**
	 * Gets the undo manager.
	 *
	 * @return the undo manager
	 */
	public UndoManager getUndoManager() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the selection.
	 *
	 * @return the selection
	 */
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the locationbar.
	 *
	 * @return the locationbar
	 */
	public Object getLocationbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the locationbar.
	 *
	 * @param locationbar
	 *            the new locationbar
	 */
	public void setLocationbar(Object locationbar) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the menubar.
	 *
	 * @return the menubar
	 */
	public BarProp getMenubar() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the menubar.
	 *
	 * @param menubar
	 *            the new menubar
	 */
	public void setMenubar(BarProp menubar) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the personalbar.
	 *
	 * @return the personalbar
	 */
	public BarProp getPersonalbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the personalbar.
	 *
	 * @param personalbar
	 *            the new personalbar
	 */
	public void setPersonalbar(BarProp personalbar) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the scrollbars.
	 *
	 * @return the scrollbars
	 */
	public BarProp getScrollbars() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the scrollbars.
	 *
	 * @param scrollbars
	 *            the new scrollbars
	 */
	public void setScrollbars(BarProp scrollbars) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the statusbar.
	 *
	 * @return the statusbar
	 */
	public BarProp getStatusbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the statusbar.
	 *
	 * @param statusbar
	 *            the new statusbar
	 */
	public void setStatusbar(BarProp statusbar) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the toolbar.
	 *
	 * @return the toolbar
	 */
	public BarProp getToolbar() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Sets the toolbar.
	 *
	 * @param toolbar
	 *            the new toolbar
	 */
	public void setToolbar(BarProp toolbar) {
		// TODO Auto-generated method stub

	}

	/**
	 * Stop.
	 */
	public void stop() {
		// TODO Auto-generated method stub

	}

	/**
	 * Sets the frames.
	 *
	 * @param frames
	 *            the new frames
	 */
	public void setFrames(Object frames) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the frame element.
	 *
	 * @return the frame element
	 */
	public Element getFrameElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the application cache.
	 *
	 * @return the application cache
	 */
	public ApplicationCache getApplicationCache() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Prints the.
	 */
	public void print() {
		// TODO Auto-generated method stub

	}

	/**
	 * Show modal dialog.
	 *
	 * @param url
	 *            the url
	 * @return the object
	 */
	public Object showModalDialog(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Show modal dialog.
	 *
	 * @param url
	 *            the url
	 * @param argument
	 *            the argument
	 * @return the object
	 */
	public Object showModalDialog(String url, Object argument) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Post message.
	 *
	 * @param message
	 *            the message
	 * @param targetOrigin
	 *            the target origin
	 */
	public void postMessage(Object message, String targetOrigin) {
		// TODO Auto-generated method stub

	}

	/**
	 * Post message.
	 *
	 * @param message
	 *            the message
	 * @param targetOrigin
	 *            the target origin
	 * @param ports
	 *            the ports
	 */
	public void postMessage(Object message, String targetOrigin, MessagePort[] ports) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the onclick.
	 *
	 * @return the onclick
	 */
	public Function getOnclick() {

		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnclick();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onclick.
	 *
	 * @param onclick
	 *            the new onclick
	 */
	public void setOnclick(Function onclick) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnclick(onclick);

		}
	}

	/**
	 * Gets the ondblclick.
	 *
	 * @return the ondblclick
	 */
	public Function getOndblclick() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOndblclick();
		} else {
			return null;
		}
	}

	/**
	 * Sets the ondblclick.
	 *
	 * @param ondblclick
	 *            the new ondblclick
	 */
	public void setOndblclick(Function ondblclick) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOndblclick(ondblclick);

		}
	}

	/**
	 * Gets the onkeydown.
	 *
	 * @return the onkeydown
	 */
	public Function getOnkeydown() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnkeydown();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onkeydown.
	 *
	 * @param onkeydown
	 *            the new onkeydown
	 */
	public void setOnkeydown(Function onkeydown) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnkeydown(onkeydown);
		}
	}

	/**
	 * Gets the onkeypress.
	 *
	 * @return the onkeypress
	 */
	public Function getOnkeypress() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnkeypress();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onkeypress.
	 *
	 * @param onkeypress
	 *            the new onkeypress
	 */
	public void setOnkeypress(Function onkeypress) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnkeypress(onkeypress);
		}
	}

	/**
	 * Gets the onkeyup.
	 *
	 * @return the onkeyup
	 */
	public Function getOnkeyup() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnkeyup();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onkeyup.
	 *
	 * @param onkeyup
	 *            the new onkeyup
	 */
	public void setOnkeyup(Function onkeyup) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnkeyup(onkeyup);
		}
	}

	/**
	 * Gets the onmousedown.
	 *
	 * @return the onmousedown
	 */
	public Function getOnmousedown() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnmousedown();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onmousedown.
	 *
	 * @param onmousedown
	 *            the new onmousedown
	 */
	public void setOnmousedown(Function onmousedown) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnmousedown(onmousedown);
		}
	}

	/**
	 * Gets the onmouseover.
	 *
	 * @return the onmouseover
	 */
	public Function getOnmouseover() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnmouseover();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onmouseover.
	 *
	 * @param onmouseover
	 *            the new onmouseover
	 */
	public void setOnmouseover(Function onmouseover) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnmouseover(onmouseover);
		}
	}

	/**
	 * Gets the onmouseup.
	 *
	 * @return the onmouseup
	 */
	public Function getOnmouseup() {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnmouseup();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onmouseup.
	 *
	 * @param onmouseup
	 *            the new onmouseup
	 */
	public void setOnmouseup(Function onmouseup) {
		Document doc = this.getWindowDocument();
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnmouseup(onmouseup);
		}
	}

	/**
	 * Gets the style media.
	 *
	 * @return the style media
	 */
	public StyleMedia getStyleMedia() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gets the inner width.
	 *
	 * @return the inner width
	 */
	public int getInnerWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the inner height.
	 *
	 * @return the inner height
	 */
	public int getInnerHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the page x offset.
	 *
	 * @return the page x offset
	 */
	public int getPageXOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the page y offset.
	 *
	 * @return the page y offset
	 */
	public int getPageYOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Scroll.
	 *
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public void scroll(int x, int y) {
		// TODO Auto-generated method stub

	}

	/**
	 * Gets the screen x.
	 *
	 * @return the screen x
	 */
	public int getScreenX() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the screen y.
	 *
	 * @return the screen y
	 */
	public int getScreenY() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the outer width.
	 *
	 * @return the outer width
	 */
	public int getOuterWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * Gets the outer height.
	 *
	 * @return the outer height
	 */
	public int getOuterHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
