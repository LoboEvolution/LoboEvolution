/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
 * Created on Nov 12, 2005
 */
package org.lobobrowser.html.js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.Timer;

import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLIFrameElementImpl;
import org.lobobrowser.html.domimpl.HTMLImageElementImpl;
import org.lobobrowser.html.domimpl.HTMLOptionElementImpl;
import org.lobobrowser.html.domimpl.HTMLScriptElementImpl;
import org.lobobrowser.html.domimpl.HTMLSelectElementImpl;
import org.lobobrowser.html.w3c.ApplicationCache;
import org.lobobrowser.html.w3c.BarProp;
import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.MessagePort;
import org.lobobrowser.html.w3c.Selection;
import org.lobobrowser.html.w3c.StyleMedia;
import org.lobobrowser.html.w3c.UndoManager;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.lobobrowser.js.JavaClassWrapper;
import org.lobobrowser.js.JavaClassWrapperFactory;
import org.lobobrowser.js.JavaInstantiator;
import org.lobobrowser.js.JavaObjectWrapper;
import org.lobobrowser.js.JavaScript;
import org.lobobrowser.util.ID;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSS2Properties;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;


/**
 * The Class Window.
 */
public class Window extends AbstractScriptableDelegate implements AbstractView {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(Window.class.getName());
	
	/** The Constant CONTEXT_WINDOWS. */
	private static final Map<HtmlRendererContext, WeakReference<Window>> CONTEXT_WINDOWS = new WeakHashMap<HtmlRendererContext, WeakReference<Window>>();
	
	/** The Constant XMLHTTPREQUEST_WRAPPER. */
	private static final JavaClassWrapper XMLHTTPREQUEST_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLHttpRequest.class);
	
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
	
	/** The window scope. */
	private ScriptableObject windowScope;
	
	/** The history. */
	private History history;
	
	/** The onunload. */
	private Function onunload;
	
	/** The task map. */
	private Map<Integer, TaskWrapper> taskMap;
	
	/** The document. */
	private volatile HTMLDocumentImpl document;
	
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
	 * @param rcontext the rcontext
	 * @param uaContext the ua context
	 */
	public Window(HtmlRendererContext rcontext, UserAgentContext uaContext) {
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

	/**
	 * Clear state.
	 */
	private void clearState() {
		Scriptable s = this.getWindowScope();
		if (s != null) {
			Object[] ids = s.getIds();
			for (int i = 0; i < ids.length; i++) {
				Object id = ids[i];
				if (id instanceof String) {
					s.delete((String) id);
				} else if (id instanceof Integer) {
					s.delete(((Integer) id).intValue());
				}
			}
		}
	}

	/**
	 * Sets the document.
	 *
	 * @param document the new document
	 */
	public void setDocument(HTMLDocumentImpl document) {
		Document prevDocument = this.document;
		if (prevDocument != document) {
			// Should clearing of the state be done
			// when window "unloads"?
			if (prevDocument != null) {
				// Only clearing when the previous document was not null
				// because state might have been set on the window before
				// the very first document is added.
				this.clearState();
			}
			this.initWindowScope(document);
			this.forgetAllTasks();
			Function onunload = this.onunload;
			if (onunload != null) {
				HTMLDocumentImpl oldDoc = (HTMLDocumentImpl) this.document;
				Executor.executeFunction(this.getWindowScope(), onunload,
						oldDoc.getDocumentURL(), this.uaContext);
				this.onunload = null;
			}
			this.document = document;
		}
	}

	/* (non-Javadoc)
	 * @see org.w3c.dom.views.AbstractView#getDocument()
	 */
	public DocumentView getDocument() {
		return this.document;
	}

	/**
	 * Gets the document node.
	 *
	 * @return the document node
	 */
	public Document getDocumentNode() {
		return this.document;
	}

	/**
	 * Put and start task.
	 *
	 * @param timeoutID the timeout id
	 * @param timer the timer
	 * @param retained the retained
	 */
	private void putAndStartTask(Integer timeoutID, Timer timer, Object retained) {
		TaskWrapper oldTaskWrapper = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap == null) {
				taskMap = new HashMap<Integer, TaskWrapper>(4);
				this.taskMap = taskMap;
			} else {
				oldTaskWrapper = (TaskWrapper) taskMap.get(timeoutID);
			}
			taskMap.put(timeoutID, new TaskWrapper(timer, retained));
		}
		// Do this outside synchronized block, just in case.
		if (oldTaskWrapper != null) {
			oldTaskWrapper.timer.stop();
		}
		timer.start();
	}

	/**
	 * Forget task.
	 *
	 * @param timeoutID the timeout id
	 * @param cancel the cancel
	 */
	private void forgetTask(Integer timeoutID, boolean cancel) {
		TaskWrapper oldTimer = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTimer = (TaskWrapper) taskMap.remove(timeoutID);
			}
		}
		if (oldTimer != null && cancel) {
			oldTimer.timer.stop();
		}
	}

	/**
	 * Forget all tasks.
	 */
	private void forgetAllTasks() {
		TaskWrapper[] oldTaskWrappers = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTaskWrappers = (TaskWrapper[]) taskMap.values().toArray(
						new TaskWrapper[0]);
				this.taskMap = null;
			}
		}
		if (oldTaskWrappers != null) {
			for (int i = 0; i < oldTaskWrappers.length; i++) {
				TaskWrapper taskWrapper = oldTaskWrappers[i];
				taskWrapper.timer.stop();
			}
		}
	}

	/**
	 * Sets the interval.
	 *
	 * @param aFunction            Javascript function to invoke on each loop.
	 * @param aTimeInMs            Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a
	 *      href="http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
	 *      interface definition</a>
	 * TODO Make proper and refactore with
	 *       {@link Window#setTimeout(Function, double)}.
	 */
	public int setInterval(final Function aFunction, final double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs
					+ " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new FunctionTimerTask(this, timeIDInt, aFunction,
				false);
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
	 * @param aExpression            Javascript expression to invoke on each loop.
	 * @param aTimeInMs            Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a
	 *      href="http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
	 *      interface definition</a>
	 * TODO Make proper and refactore with
	 *       {@link Window#setTimeout(String, double)}.
	 */
	public int setInterval(final String aExpression, double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs
					+ " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new ExpressionTimerTask(this, timeIDInt,
				aExpression, false);
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
	 * @param aTimerID            Timer ID to stop.
	 * @see <a
	 *      href="http://developer.mozilla.org/en/docs/DOM:window.clearInterval">Window.clearInterval
	 *      interface Definition</a>
	 */
	public void clearInterval(int aTimerID) {
		Integer key = new Integer(aTimerID);
		this.forgetTask(key, true);
	}

	/**
	 * Alert.
	 *
	 * @param message the message
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
	 * @param timeoutID the timeout id
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
	 * @param message the message
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
	 * @param javascript the javascript
	 * @return the object
	 */
	public Object eval(String javascript) {
		HTMLDocumentImpl document = (HTMLDocumentImpl) this.document;
		if (document == null) {
			throw new IllegalStateException(
					"Cannot evaluate if document is not set.");
		}
		Context ctx = Executor.createContext(document.getDocumentURL(),
				this.uaContext);
		try {
			Scriptable scope = this.getWindowScope();
			if (scope == null) {
				throw new IllegalStateException(
						"Scriptable (scope) instance was expected to be keyed as UserData to document using "
								+ Executor.SCOPE_KEY);
			}
			String scriptURI = "window.eval";
			if (logger.isLoggable(Level.INFO)) {
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
	 * Inits the window scope.
	 *
	 * @param doc the doc
	 */
	private void initWindowScope(final Document doc) {
		// Special Javascript class: XMLHttpRequest
		final Scriptable ws = this.getWindowScope();
		JavaInstantiator xi = new JavaInstantiator() {
			public Object newInstance() {
				Document d = doc;
				if (d == null) {
					throw new IllegalStateException(
							"Cannot perform operation when document is unset.");
				}
				HTMLDocumentImpl hd;
				try {
					hd = (HTMLDocumentImpl) d;
				} catch (ClassCastException err) {
					throw new IllegalStateException(
							"Cannot perform operation with documents of type "
									+ d.getClass().getName() + ".");
				}
				return new XMLHttpRequest(uaContext, hd.getDocumentURL(), ws);
			}
		};
		Function xmlHttpRequestC = JavaObjectWrapper.getConstructor("XMLHttpRequest", XMLHTTPREQUEST_WRAPPER, ws, xi);
		ScriptableObject.defineProperty(ws, "XMLHttpRequest", xmlHttpRequestC,ScriptableObject.READONLY);

		// HTML element classes
		this.defineElementClass(ws, doc, "Image", "img", HTMLImageElementImpl.class);
		this.defineElementClass(ws, doc, "Script", "script", HTMLScriptElementImpl.class);
		this.defineElementClass(ws, doc, "IFrame", "iframe", HTMLIFrameElementImpl.class);
		this.defineElementClass(ws, doc, "Option", "option", HTMLOptionElementImpl.class);
		this.defineElementClass(ws, doc, "Select", "select", HTMLSelectElementImpl.class);
	}

	/**
	 * Gets the window scope.
	 *
	 * @return the window scope
	 */
	public Scriptable getWindowScope() {
		synchronized (this) {
			ScriptableObject windowScope = this.windowScope;
			if (windowScope != null) {
				return windowScope;
			}
			// Context.enter() OK in this particular case.
			Context ctx = Context.enter();
			try {
				// Window scope needs to be top-most scope.
				windowScope = (ScriptableObject) JavaScript.getInstance()
						.getJavascriptObject(this, null);
				ctx.initStandardObjects(windowScope);
				this.windowScope = windowScope;
				return windowScope;
			} finally {
				Context.exit();
			}
		}
	}

	/**
	 * Define element class.
	 *
	 * @param scope the scope
	 * @param document the document
	 * @param jsClassName the js class name
	 * @param elementName the element name
	 * @param javaClass the java class
	 */
	private final void defineElementClass(Scriptable scope,
			final Document document, final String jsClassName,
			final String elementName, Class<?> javaClass) {
		JavaInstantiator ji = new JavaInstantiator() {
			public Object newInstance() {
				Document d = document;
				if (d == null) {
					throw new IllegalStateException(
							"Document not set in current context.");
				}
				return d.createElement(elementName);
			}
		};
		JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		Function constructorFunction = JavaObjectWrapper.getConstructor(jsClassName, classWrapper, scope, ji);
		ScriptableObject.defineProperty(scope, jsClassName,	constructorFunction, ScriptableObject.READONLY);
	}

	/**
	 * Gets the window.
	 *
	 * @param rcontext the rcontext
	 * @return the window
	 */
	public static Window getWindow(HtmlRendererContext rcontext) {
		if (rcontext == null) {
			return null;
		}
		synchronized (CONTEXT_WINDOWS) {
			Reference<?> wref = (Reference<?>) CONTEXT_WINDOWS.get(rcontext);
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
	 * @param relativeUrl the relative url
	 * @param windowName the window name
	 * @param windowFeatures the window features
	 * @param replace the replace
	 * @return the window
	 */
	public Window open(String relativeUrl, String windowName, String windowFeatures, boolean replace) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			URL url;
			Object document = this.document;
			if (document instanceof HTMLDocumentImpl) {
				url = ((HTMLDocumentImpl) document).getFullURL(relativeUrl);
			} else {
				try {
					url = new URL(relativeUrl);
				} catch (MalformedURLException mfu) {
					throw new IllegalArgumentException("Malformed URI: " + relativeUrl);
				}
			}
			HtmlRendererContext newContext = rcontext.open(url, windowName,
					windowFeatures, replace);
			return getWindow(newContext);
		} else {
			return null;
		}
	}
	
	/**
	 * Open.
	 *
	 * @return the window
	 */
	public Window open() {
		return this.open("", "window:" + String.valueOf(ID.generateLong()));
	}

	/**
	 * Open.
	 *
	 * @param url the url
	 * @return the window
	 */
	public Window open(String url) {
		return this.open(url, "window:" + String.valueOf(ID.generateLong()));
	}

	/**
	 * Open.
	 *
	 * @param url the url
	 * @param windowName the window name
	 * @return the window
	 */
	public Window open(String url, String windowName) {
		return this.open(url, windowName, "", false);
	}

	/**
	 * Open.
	 *
	 * @param url the url
	 * @param windowName the window name
	 * @param windowFeatures the window features
	 * @return the window
	 */
	public Window open(String url, String windowName, String windowFeatures) {
		return this.open(url, windowName, windowFeatures, false);
	}

	/**
	 * Prompt.
	 *
	 * @param message the message
	 * @return the string
	 */
	public String prompt(String message) {
		return this.prompt(message, "");
	}

	/**
	 * Prompt.
	 *
	 * @param message the message
	 * @param inputDefault the input default
	 * @return the string
	 */
	public String prompt(String message, int inputDefault) {
		return this.prompt(message, String.valueOf(inputDefault));
	}

	/**
	 * Prompt.
	 *
	 * @param message the message
	 * @param inputDefault the input default
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
	 * @param x the x
	 * @param y the y
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
	 * @param x the x
	 * @param y the y
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
	 * @param width the width
	 * @param height the height
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
	 * @param byWidth the by width
	 * @param byHeight the by height
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
	 * @param expr the expr
	 * @param millis the millis
	 * @return the int
	 */
	public int setTimeout(final String expr, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis
					+ " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new ExpressionTimerTask(this, timeIDInt, expr,
				true);
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
	 * @param function the function
	 * @param millis the millis
	 * @return the int
	 */
	public int setTimeout(final Function function, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis
					+ " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		ActionListener task = new FunctionTimerTask(this, timeIDInt, function,
				true);
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
		Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getFrames();
		}
		return null;
	}

	/**
	 * Gets the number of frames.
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
	 * @param length the new length
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
	 * @param name the new name
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
	 * @param opener the new opener
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
	 * @param message the new status
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
	 * @param location the new location
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
	 * @param element the element
	 * @param pseudoElement the pseudo element
	 * @return the computed style
	 */
	public CSS2Properties getComputedStyle(HTMLElement element, String pseudoElement) {
		if (element instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) element).getComputedStyle(pseudoElement);
		} else {
			throw new IllegalArgumentException(
					"Element implementation unknown: " + element);
		}
	}
	
	/**
	 * Gets the computed style.
	 *
	 * @param elt the elt
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
		Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnloadHandler();
		} else {
			return null;
		}
	}

	/**
	 * Sets the onload.
	 *
	 * @param onload the new onload
	 */
	public void setOnload(Function onload) {
		// Note that body.onload overrides
		// window.onload.
		Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnloadHandler(onload);
		}
	}

	/**
	 * Gets the onunload.
	 *
	 * @return the onunload
	 */
	public Function getOnunload() {
		return onunload;
	}

	/**
	 * Sets the onunload.
	 *
	 * @param onunload the new onunload
	 */
	public void setOnunload(Function onunload) {
		this.onunload = onunload;
	}

	/**
	 * Named item.
	 *
	 * @param name the name
	 * @return the node
	 */
	public Node namedItem(String name) {
		HTMLDocumentImpl doc = this.document;
		if (doc == null) {
			return null;
		}
		org.w3c.dom.Node node = doc.getElementById(name);
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
		 * @param window the window
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
		 * @param window the window
		 * @param timeIDInt the time id int
		 * @param function the function
		 * @param removeTask the remove task
		 */
		public FunctionTimerTask(Window window, Integer timeIDInt,
				Function function, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.functionRef = new WeakReference<Function>(function);
			this.removeTask = removeTask;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			// This executes in the GUI thread and that's good.
			try {
				Window window = this.getWindow();
				if (window == null) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("actionPerformed(): Window is no longer available.");
					}
					return;
				}
				if (this.removeTask) {
					window.forgetTask(this.timeIDInt, false);
				}
				HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
				if (doc == null) {
					throw new IllegalStateException(
							"Cannot perform operation when document is unset.");
				}
				Function function = (Function) this.functionRef.get();
				if (function == null) {
					throw new IllegalStateException(
							"Cannot perform operation. Function is no longer available.");
				}
				Executor.executeFunction(window.getWindowScope(), function,
						doc.getDocumentURL(), window.getUserAgentContext());
			} catch (Throwable err) {
				logger.log(Level.WARNING, "actionPerformed()", err);
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
		 * @param window the window
		 * @param timeIDInt the time id int
		 * @param expression the expression
		 * @param removeTask the remove task
		 */
		public ExpressionTimerTask(Window window, Integer timeIDInt,
				String expression, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.expression = expression;
			this.removeTask = removeTask;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			// This executes in the GUI thread and that's good.
			try {
				Window window = this.getWindow();
				if (window == null) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("actionPerformed(): Window is no longer available.");
					}
					return;
				}
				if (this.removeTask) {
					window.forgetTask(this.timeIDInt, false);
				}
				HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
				if (doc == null) {
					throw new IllegalStateException(
							"Cannot perform operation when document is unset.");
				}
				window.eval(this.expression);
			} catch (Throwable err) {
				logger.log(Level.WARNING, "actionPerformed()", err);
			}
		}
	}

	/**
	 * The Class TaskWrapper.
	 */
	private static class TaskWrapper {
		
		/** The timer. */
		public final Timer timer;
		
		/** The retained. */
		private final Object retained;

		/**
		 * Instantiates a new task wrapper.
		 *
		 * @param timer the timer
		 * @param retained the retained
		 */
		public TaskWrapper(Timer timer, Object retained) {
			super();
			this.timer = timer;
			this.retained = retained;
		}
	}
	
	/**
	 * Adds the event listener.
	 *
	 * @param script the script
	 * @param function the function
	 */
	public void addEventListener(String script, String function) {
		// TODO Auto-generated method stub
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
	 * @param locationbar the new locationbar
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
	 * @param menubar the new menubar
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
	 * @param personalbar the new personalbar
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
	 * @param scrollbars the new scrollbars
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
	 * @param statusbar the new statusbar
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
	 * @param toolbar the new toolbar
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
	 * @param frames the new frames
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
	 * @param url the url
	 * @return the object
	 */
	public Object showModalDialog(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Show modal dialog.
	 *
	 * @param url the url
	 * @param argument the argument
	 * @return the object
	 */
	public Object showModalDialog(String url, Object argument) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Post message.
	 *
	 * @param message the message
	 * @param targetOrigin the target origin
	 */
	public void postMessage(Object message, String targetOrigin) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Post message.
	 *
	 * @param message the message
	 * @param targetOrigin the target origin
	 * @param ports the ports
	 */
	public void postMessage(Object message, String targetOrigin,
			MessagePort[] ports) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onabort.
	 *
	 * @return the onabort
	 */
	public Function getOnabort() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onabort.
	 *
	 * @param onabort the new onabort
	 */
	public void setOnabort(Function onabort) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onafterprint.
	 *
	 * @return the onafterprint
	 */
	public Function getOnafterprint() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onafterprint.
	 *
	 * @param onafterprint the new onafterprint
	 */
	public void setOnafterprint(Function onafterprint) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onbeforeprint.
	 *
	 * @return the onbeforeprint
	 */
	public Function getOnbeforeprint() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onbeforeprint.
	 *
	 * @param onbeforeprint the new onbeforeprint
	 */
	public void setOnbeforeprint(Function onbeforeprint) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onbeforeunload.
	 *
	 * @return the onbeforeunload
	 */
	public Function getOnbeforeunload() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onbeforeunload.
	 *
	 * @param onbeforeunload the new onbeforeunload
	 */
	public void setOnbeforeunload(Function onbeforeunload) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onblur.
	 *
	 * @return the onblur
	 */
	public Function getOnblur() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onblur.
	 *
	 * @param onblur the new onblur
	 */
	public void setOnblur(Function onblur) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the oncanplay.
	 *
	 * @return the oncanplay
	 */
	public Function getOncanplay() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the oncanplay.
	 *
	 * @param oncanplay the new oncanplay
	 */
	public void setOncanplay(Function oncanplay) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the oncanplaythrough.
	 *
	 * @return the oncanplaythrough
	 */
	public Function getOncanplaythrough() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the oncanplaythrough.
	 *
	 * @param oncanplaythrough the new oncanplaythrough
	 */
	public void setOncanplaythrough(Function oncanplaythrough) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onchange.
	 *
	 * @return the onchange
	 */
	public Function getOnchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onchange.
	 *
	 * @param onchange the new onchange
	 */
	public void setOnchange(Function onchange) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onclick.
	 *
	 * @return the onclick
	 */
	public Function getOnclick() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onclick.
	 *
	 * @param onclick the new onclick
	 */
	public void setOnclick(Function onclick) {
		System.out.println("onclick: " + onclick);
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the oncontextmenu.
	 *
	 * @return the oncontextmenu
	 */
	public Function getOncontextmenu() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the oncontextmenu.
	 *
	 * @param oncontextmenu the new oncontextmenu
	 */
	public void setOncontextmenu(Function oncontextmenu) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondblclick.
	 *
	 * @return the ondblclick
	 */
	public Function getOndblclick() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondblclick.
	 *
	 * @param ondblclick the new ondblclick
	 */
	public void setOndblclick(Function ondblclick) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondrag.
	 *
	 * @return the ondrag
	 */
	public Function getOndrag() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondrag.
	 *
	 * @param ondrag the new ondrag
	 */
	public void setOndrag(Function ondrag) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondragend.
	 *
	 * @return the ondragend
	 */
	public Function getOndragend() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondragend.
	 *
	 * @param ondragend the new ondragend
	 */
	public void setOndragend(Function ondragend) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondragenter.
	 *
	 * @return the ondragenter
	 */
	public Function getOndragenter() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondragenter.
	 *
	 * @param ondragenter the new ondragenter
	 */
	public void setOndragenter(Function ondragenter) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondragleave.
	 *
	 * @return the ondragleave
	 */
	public Function getOndragleave() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondragleave.
	 *
	 * @param ondragleave the new ondragleave
	 */
	public void setOndragleave(Function ondragleave) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondragover.
	 *
	 * @return the ondragover
	 */
	public Function getOndragover() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondragover.
	 *
	 * @param ondragover the new ondragover
	 */
	public void setOndragover(Function ondragover) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondragstart.
	 *
	 * @return the ondragstart
	 */
	public Function getOndragstart() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondragstart.
	 *
	 * @param ondragstart the new ondragstart
	 */
	public void setOndragstart(Function ondragstart) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondrop.
	 *
	 * @return the ondrop
	 */
	public Function getOndrop() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondrop.
	 *
	 * @param ondrop the new ondrop
	 */
	public void setOndrop(Function ondrop) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ondurationchange.
	 *
	 * @return the ondurationchange
	 */
	public Function getOndurationchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ondurationchange.
	 *
	 * @param ondurationchange the new ondurationchange
	 */
	public void setOndurationchange(Function ondurationchange) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onemptied.
	 *
	 * @return the onemptied
	 */
	public Function getOnemptied() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onemptied.
	 *
	 * @param onemptied the new onemptied
	 */
	public void setOnemptied(Function onemptied) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onended.
	 *
	 * @return the onended
	 */
	public Function getOnended() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onended.
	 *
	 * @param onended the new onended
	 */
	public void setOnended(Function onended) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onerror.
	 *
	 * @return the onerror
	 */
	public Function getOnerror() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onerror.
	 *
	 * @param onerror the new onerror
	 */
	public void setOnerror(Function onerror) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onfocus.
	 *
	 * @return the onfocus
	 */
	public Function getOnfocus() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onfocus.
	 *
	 * @param onfocus the new onfocus
	 */
	public void setOnfocus(Function onfocus) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onformchange.
	 *
	 * @return the onformchange
	 */
	public Function getOnformchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onformchange.
	 *
	 * @param onformchange the new onformchange
	 */
	public void setOnformchange(Function onformchange) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onforminput.
	 *
	 * @return the onforminput
	 */
	public Function getOnforminput() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onforminput.
	 *
	 * @param onforminput the new onforminput
	 */
	public void setOnforminput(Function onforminput) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onhashchange.
	 *
	 * @return the onhashchange
	 */
	public Function getOnhashchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onhashchange.
	 *
	 * @param onhashchange the new onhashchange
	 */
	public void setOnhashchange(Function onhashchange) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the oninput.
	 *
	 * @return the oninput
	 */
	public Function getOninput() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the oninput.
	 *
	 * @param oninput the new oninput
	 */
	public void setOninput(Function oninput) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the oninvalid.
	 *
	 * @return the oninvalid
	 */
	public Function getOninvalid() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the oninvalid.
	 *
	 * @param oninvalid the new oninvalid
	 */
	public void setOninvalid(Function oninvalid) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onkeydown.
	 *
	 * @return the onkeydown
	 */
	public Function getOnkeydown() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onkeydown.
	 *
	 * @param onkeydown the new onkeydown
	 */
	public void setOnkeydown(Function onkeydown) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onkeypress.
	 *
	 * @return the onkeypress
	 */
	public Function getOnkeypress() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onkeypress.
	 *
	 * @param onkeypress the new onkeypress
	 */
	public void setOnkeypress(Function onkeypress) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onkeyup.
	 *
	 * @return the onkeyup
	 */
	public Function getOnkeyup() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onkeyup.
	 *
	 * @param onkeyup the new onkeyup
	 */
	public void setOnkeyup(Function onkeyup) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onloadeddata.
	 *
	 * @return the onloadeddata
	 */
	public Function getOnloadeddata() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onloadeddata.
	 *
	 * @param onloadeddata the new onloadeddata
	 */
	public void setOnloadeddata(Function onloadeddata) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onloadedmetadata.
	 *
	 * @return the onloadedmetadata
	 */
	public Function getOnloadedmetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onloadedmetadata.
	 *
	 * @param onloadedmetadata the new onloadedmetadata
	 */
	public void setOnloadedmetadata(Function onloadedmetadata) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onloadstart.
	 *
	 * @return the onloadstart
	 */
	public Function getOnloadstart() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onloadstart.
	 *
	 * @param onloadstart the new onloadstart
	 */
	public void setOnloadstart(Function onloadstart) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onmessage.
	 *
	 * @return the onmessage
	 */
	public Function getOnmessage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onmessage.
	 *
	 * @param onmessage the new onmessage
	 */
	public void setOnmessage(Function onmessage) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onmousedown.
	 *
	 * @return the onmousedown
	 */
	public Function getOnmousedown() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onmousedown.
	 *
	 * @param onmousedown the new onmousedown
	 */
	public void setOnmousedown(Function onmousedown) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onmousemove.
	 *
	 * @return the onmousemove
	 */
	public Function getOnmousemove() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onmousemove.
	 *
	 * @param onmousemove the new onmousemove
	 */
	public void setOnmousemove(Function onmousemove) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onmouseout.
	 *
	 * @return the onmouseout
	 */
	public Function getOnmouseout() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onmouseout.
	 *
	 * @param onmouseout the new onmouseout
	 */
	public void setOnmouseout(Function onmouseout) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onmouseover.
	 *
	 * @return the onmouseover
	 */
	public Function getOnmouseover() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onmouseover.
	 *
	 * @param onmouseover the new onmouseover
	 */
	public void setOnmouseover(Function onmouseover) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onmouseup.
	 *
	 * @return the onmouseup
	 */
	public Function getOnmouseup() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onmouseup.
	 *
	 * @param onmouseup the new onmouseup
	 */
	public void setOnmouseup(Function onmouseup) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onmousewheel.
	 *
	 * @return the onmousewheel
	 */
	public Function getOnmousewheel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onmousewheel.
	 *
	 * @param onmousewheel the new onmousewheel
	 */
	public void setOnmousewheel(Function onmousewheel) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onoffline.
	 *
	 * @return the onoffline
	 */
	public Function getOnoffline() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onoffline.
	 *
	 * @param onoffline the new onoffline
	 */
	public void setOnoffline(Function onoffline) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ononline.
	 *
	 * @return the ononline
	 */
	public Function getOnonline() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ononline.
	 *
	 * @param ononline the new ononline
	 */
	public void setOnonline(Function ononline) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onpause.
	 *
	 * @return the onpause
	 */
	public Function getOnpause() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onpause.
	 *
	 * @param onpause the new onpause
	 */
	public void setOnpause(Function onpause) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onplay.
	 *
	 * @return the onplay
	 */
	public Function getOnplay() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onplay.
	 *
	 * @param onplay the new onplay
	 */
	public void setOnplay(Function onplay) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onplaying.
	 *
	 * @return the onplaying
	 */
	public Function getOnplaying() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onplaying.
	 *
	 * @param onplaying the new onplaying
	 */
	public void setOnplaying(Function onplaying) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onpagehide.
	 *
	 * @return the onpagehide
	 */
	public Function getOnpagehide() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onpagehide.
	 *
	 * @param onpagehide the new onpagehide
	 */
	public void setOnpagehide(Function onpagehide) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onpageshow.
	 *
	 * @return the onpageshow
	 */
	public Function getOnpageshow() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onpageshow.
	 *
	 * @param onpageshow the new onpageshow
	 */
	public void setOnpageshow(Function onpageshow) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onpopstate.
	 *
	 * @return the onpopstate
	 */
	public Function getOnpopstate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onpopstate.
	 *
	 * @param onpopstate the new onpopstate
	 */
	public void setOnpopstate(Function onpopstate) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onprogress.
	 *
	 * @return the onprogress
	 */
	public Function getOnprogress() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onprogress.
	 *
	 * @param onprogress the new onprogress
	 */
	public void setOnprogress(Function onprogress) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onratechange.
	 *
	 * @return the onratechange
	 */
	public Function getOnratechange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onratechange.
	 *
	 * @param onratechange the new onratechange
	 */
	public void setOnratechange(Function onratechange) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onreadystatechange.
	 *
	 * @return the onreadystatechange
	 */
	public Function getOnreadystatechange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onreadystatechange.
	 *
	 * @param onreadystatechange the new onreadystatechange
	 */
	public void setOnreadystatechange(Function onreadystatechange) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onredo.
	 *
	 * @return the onredo
	 */
	public Function getOnredo() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onredo.
	 *
	 * @param onredo the new onredo
	 */
	public void setOnredo(Function onredo) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onresize.
	 *
	 * @return the onresize
	 */
	public Function getOnresize() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onresize.
	 *
	 * @param onresize the new onresize
	 */
	public void setOnresize(Function onresize) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onscroll.
	 *
	 * @return the onscroll
	 */
	public Function getOnscroll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onscroll.
	 *
	 * @param onscroll the new onscroll
	 */
	public void setOnscroll(Function onscroll) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onseeked.
	 *
	 * @return the onseeked
	 */
	public Function getOnseeked() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onseeked.
	 *
	 * @param onseeked the new onseeked
	 */
	public void setOnseeked(Function onseeked) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onseeking.
	 *
	 * @return the onseeking
	 */
	public Function getOnseeking() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onseeking.
	 *
	 * @param onseeking the new onseeking
	 */
	public void setOnseeking(Function onseeking) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onselect.
	 *
	 * @return the onselect
	 */
	public Function getOnselect() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onselect.
	 *
	 * @param onselect the new onselect
	 */
	public void setOnselect(Function onselect) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onshow.
	 *
	 * @return the onshow
	 */
	public Function getOnshow() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onshow.
	 *
	 * @param onshow the new onshow
	 */
	public void setOnshow(Function onshow) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onstalled.
	 *
	 * @return the onstalled
	 */
	public Function getOnstalled() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onstalled.
	 *
	 * @param onstalled the new onstalled
	 */
	public void setOnstalled(Function onstalled) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onstorage.
	 *
	 * @return the onstorage
	 */
	public Function getOnstorage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onstorage.
	 *
	 * @param onstorage the new onstorage
	 */
	public void setOnstorage(Function onstorage) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onsubmit.
	 *
	 * @return the onsubmit
	 */
	public Function getOnsubmit() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onsubmit.
	 *
	 * @param onsubmit the new onsubmit
	 */
	public void setOnsubmit(Function onsubmit) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onsuspend.
	 *
	 * @return the onsuspend
	 */
	public Function getOnsuspend() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onsuspend.
	 *
	 * @param onsuspend the new onsuspend
	 */
	public void setOnsuspend(Function onsuspend) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the ontimeupdate.
	 *
	 * @return the ontimeupdate
	 */
	public Function getOntimeupdate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the ontimeupdate.
	 *
	 * @param ontimeupdate the new ontimeupdate
	 */
	public void setOntimeupdate(Function ontimeupdate) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onundo.
	 *
	 * @return the onundo
	 */
	public Function getOnundo() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onundo.
	 *
	 * @param onundo the new onundo
	 */
	public void setOnundo(Function onundo) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onvolumechange.
	 *
	 * @return the onvolumechange
	 */
	public Function getOnvolumechange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onvolumechange.
	 *
	 * @param onvolumechange the new onvolumechange
	 */
	public void setOnvolumechange(Function onvolumechange) {
		// TODO Auto-generated method stub
		
	}

	
	/**
	 * Gets the onwaiting.
	 *
	 * @return the onwaiting
	 */
	public Function getOnwaiting() {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * Sets the onwaiting.
	 *
	 * @param onwaiting the new onwaiting
	 */
	public void setOnwaiting(Function onwaiting) {
		// TODO Auto-generated method stub
		
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
	 * @param x the x
	 * @param y the y
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
