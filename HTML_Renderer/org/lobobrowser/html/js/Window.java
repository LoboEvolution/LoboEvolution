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

public class Window extends AbstractScriptableDelegate implements AbstractView {
	private static final Logger logger = Logger.getLogger(Window.class.getName());
	private static final Map<HtmlRendererContext, WeakReference<Window>> CONTEXT_WINDOWS = new WeakHashMap<HtmlRendererContext, WeakReference<Window>>();
	private static final JavaClassWrapper XMLHTTPREQUEST_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLHttpRequest.class);
	private static int timerIdCounter = 0;
	private final HtmlRendererContext rcontext;
	private final UserAgentContext uaContext;
	private Navigator navigator;
	private Screen screen;
	private Location location;
	private ScriptableObject windowScope;
	private History history;
	private Function onunload;
	private Map<Integer, TaskWrapper> taskMap;
	private volatile HTMLDocumentImpl document;
	private int length;
	private String name;
	private boolean lengthSet = false;
	private boolean nameSet = false;

	public Window(HtmlRendererContext rcontext, UserAgentContext uaContext) {
		// TODO: Probably need to create a new Window instance
		// for every document. Sharing of Window state between
		// different documents is not correct.
		this.rcontext = rcontext;
		this.uaContext = uaContext;
	}

	private static int generateTimerID() {
		synchronized (logger) {
			return timerIdCounter++;
		}
	}

	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	public UserAgentContext getUserAgentContext() {
		return this.uaContext;
	}

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

	public DocumentView getDocument() {
		return this.document;
	}

	public Document getDocumentNode() {
		return this.document;
	}

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
	 * @param aFunction
	 *            Javascript function to invoke on each loop.
	 * @param aTimeInMs
	 *            Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a
	 *      href="http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
	 *      interface definition</a>
	 * @todo Make proper and refactore with
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
	 * @param aExpression
	 *            Javascript expression to invoke on each loop.
	 * @param aTimeInMs
	 *            Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a
	 *      href="http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
	 *      interface definition</a>
	 * @todo Make proper and refactore with
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
	 * @param aTimerID
	 *            Timer ID to stop.
	 * @see <a
	 *      href="http://developer.mozilla.org/en/docs/DOM:window.clearInterval">Window.clearInterval
	 *      interface Definition</a>
	 */
	public void clearInterval(int aTimerID) {
		Integer key = new Integer(aTimerID);
		this.forgetTask(key, true);
	}

	public void alert(String message) {
		if (this.rcontext != null) {
			this.rcontext.alert(message);
		}
	}

	public void back() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.back();
		}
	}

	public void blur() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.blur();
		}
	}

	public void clearTimeout(int timeoutID) {
		Integer key = new Integer(timeoutID);
		this.forgetTask(key, true);
	}

	public void close() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.close();
		}
	}

	public boolean confirm(String message) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.confirm(message);
		} else {
			return false;
		}
	}

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

	public void focus() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.focus();
		}
	}

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
	
	public Window open() {
		return this.open("", "window:" + String.valueOf(ID.generateLong()));
	}

	public Window open(String url) {
		return this.open(url, "window:" + String.valueOf(ID.generateLong()));
	}

	public Window open(String url, String windowName) {
		return this.open(url, windowName, "", false);
	}

	public Window open(String url, String windowName, String windowFeatures) {
		return this.open(url, windowName, windowFeatures, false);
	}

	public String prompt(String message) {
		return this.prompt(message, "");
	}

	public String prompt(String message, int inputDefault) {
		return this.prompt(message, String.valueOf(inputDefault));
	}

	public String prompt(String message, String inputDefault) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.prompt(message, inputDefault);
		} else {
			return null;
		}
	}

	public void scrollTo(int x, int y) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scroll(x, y);
		}
	}

	public void scrollBy(int x, int y) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scrollBy(x, y);
		}
	}

	public void resizeTo(int width, int height) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeTo(width, height);
		}
	}

	public void resizeBy(int byWidth, int byHeight) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeBy(byWidth, byHeight);
		}
	}

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

	public boolean isClosed() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.isClosed();
		} else {
			return false;
		}
	}

	public String getDefaultStatus() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getDefaultStatus();
		} else {
			return null;
		}
	}

	public HTMLCollection getFrames() {
		Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getFrames();
		}
		return null;
	}

	/**
	 * Gets the number of frames.
	 */
	public int getLength() {
		if (this.lengthSet) {
			return this.length;
		} else {
			HTMLCollection frames = this.getFrames();
			return frames == null ? 0 : frames.getLength();
		}
	}

	public void setLength(int length) {
		this.lengthSet = true;
		this.length = length;
	}

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
	
	
	public void setName(String name) {
		this.nameSet = true;
		this.name = name;
	}

	public Window getParent() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getParent());
		} else {
			return null;
		}
	}

	public Window getOpener() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getOpener());
		} else {
			return null;
		}
	}

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

	public Window getSelf() {
		return this;
	}

	public String getStatus() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getStatus();
		} else {
			return null;
		}
	}

	public void setStatus(String message) {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.setStatus(message);
		}
	}

	public Window getTop() {
		HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getTop());
		} else {
			return null;
		}
	}

	public Window getWindow() {
		return this;
	}

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

	public void setLocation(String location) {
		this.getLocation().setHref(location);
	}


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

	public CSS2Properties getComputedStyle(HTMLElement element, String pseudoElement) {
		if (element instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) element).getComputedStyle(pseudoElement);
		} else {
			throw new IllegalArgumentException(
					"Element implementation unknown: " + element);
		}
	}
	
	public CSS2Properties getComputedStyle(HTMLElement elt) {
		return getComputedStyle(elt, null);
	}

	public Function getOnload() {
		Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnloadHandler();
		} else {
			return null;
		}
	}

	public void setOnload(Function onload) {
		// Note that body.onload overrides
		// window.onload.
		Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnloadHandler(onload);
		}
	}

	public Function getOnunload() {
		return onunload;
	}

	public void setOnunload(Function onunload) {
		this.onunload = onunload;
	}

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

	public void forceGC() {
		System.gc();
	}

	private static abstract class WeakWindowTask implements ActionListener {
		private final WeakReference<Window> windowRef;

		public WeakWindowTask(Window window) {
			this.windowRef = new WeakReference<Window>(window);
		}

		protected Window getWindow() {
			WeakReference<Window> ref = this.windowRef;
			return ref == null ? null : (Window) ref.get();
		}
	}

	private static class FunctionTimerTask extends WeakWindowTask {
		// Implemented as a static WeakWindowTask to allow the Window
		// to get garbage collected, especially in infinite loop
		// scenarios.
		private final Integer timeIDInt;
		private final WeakReference<Function> functionRef;
		private final boolean removeTask;

		public FunctionTimerTask(Window window, Integer timeIDInt,
				Function function, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.functionRef = new WeakReference<Function>(function);
			this.removeTask = removeTask;
		}

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

	private static class ExpressionTimerTask extends WeakWindowTask {
		// Implemented as a static WeakWindowTask to allow the Window
		// to get garbage collected, especially in infinite loop
		// scenarios.
		private final Integer timeIDInt;
		private final String expression;
		private final boolean removeTask;

		public ExpressionTimerTask(Window window, Integer timeIDInt,
				String expression, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.expression = expression;
			this.removeTask = removeTask;
		}

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

	private static class TaskWrapper {
		public final Timer timer;
		private final Object retained;

		public TaskWrapper(Timer timer, Object retained) {
			super();
			this.timer = timer;
			this.retained = retained;
		}
	}
	
	public void addEventListener(String script, String function) {
		// TODO Auto-generated method stub
	}

	
	public UndoManager getUndoManager() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object getLocationbar() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setLocationbar(Object locationbar) {
		// TODO Auto-generated method stub
		
	}

	
	public BarProp getMenubar() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setMenubar(BarProp menubar) {
		// TODO Auto-generated method stub
		
	}

	
	public BarProp getPersonalbar() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setPersonalbar(BarProp personalbar) {
		// TODO Auto-generated method stub
		
	}

	
	public BarProp getScrollbars() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setScrollbars(BarProp scrollbars) {
		// TODO Auto-generated method stub
		
	}

	
	public BarProp getStatusbar() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setStatusbar(BarProp statusbar) {
		// TODO Auto-generated method stub
		
	}

	
	public BarProp getToolbar() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setToolbar(BarProp toolbar) {
		// TODO Auto-generated method stub
		
	}

	
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	
	public void setFrames(Object frames) {
		// TODO Auto-generated method stub
		
	}

	
	public Element getFrameElement() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public ApplicationCache getApplicationCache() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void print() {
		// TODO Auto-generated method stub
		
	}

	
	public Object showModalDialog(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public Object showModalDialog(String url, Object argument) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void postMessage(Object message, String targetOrigin) {
		// TODO Auto-generated method stub
		
	}

	
	public void postMessage(Object message, String targetOrigin,
			MessagePort[] ports) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnabort() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnabort(Function onabort) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnafterprint() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnafterprint(Function onafterprint) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnbeforeprint() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnbeforeprint(Function onbeforeprint) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnbeforeunload() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnbeforeunload(Function onbeforeunload) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnblur() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnblur(Function onblur) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOncanplay() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOncanplay(Function oncanplay) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOncanplaythrough() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOncanplaythrough(Function oncanplaythrough) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnchange(Function onchange) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnclick() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnclick(Function onclick) {
		System.out.println("onclick: " + onclick);
		// TODO Auto-generated method stub
		
	}

	
	public Function getOncontextmenu() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOncontextmenu(Function oncontextmenu) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndblclick() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndblclick(Function ondblclick) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndrag() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndrag(Function ondrag) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndragend() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndragend(Function ondragend) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndragenter() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndragenter(Function ondragenter) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndragleave() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndragleave(Function ondragleave) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndragover() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndragover(Function ondragover) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndragstart() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndragstart(Function ondragstart) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndrop() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndrop(Function ondrop) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOndurationchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOndurationchange(Function ondurationchange) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnemptied() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnemptied(Function onemptied) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnended() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnended(Function onended) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnerror() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnerror(Function onerror) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnfocus() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnfocus(Function onfocus) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnformchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnformchange(Function onformchange) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnforminput() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnforminput(Function onforminput) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnhashchange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnhashchange(Function onhashchange) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOninput() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOninput(Function oninput) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOninvalid() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOninvalid(Function oninvalid) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnkeydown() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnkeydown(Function onkeydown) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnkeypress() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnkeypress(Function onkeypress) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnkeyup() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnkeyup(Function onkeyup) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnloadeddata() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnloadeddata(Function onloadeddata) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnloadedmetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnloadedmetadata(Function onloadedmetadata) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnloadstart() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnloadstart(Function onloadstart) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnmessage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnmessage(Function onmessage) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnmousedown() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnmousedown(Function onmousedown) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnmousemove() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnmousemove(Function onmousemove) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnmouseout() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnmouseout(Function onmouseout) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnmouseover() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnmouseover(Function onmouseover) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnmouseup() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnmouseup(Function onmouseup) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnmousewheel() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnmousewheel(Function onmousewheel) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnoffline() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnoffline(Function onoffline) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnonline() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnonline(Function ononline) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnpause() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnpause(Function onpause) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnplay() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnplay(Function onplay) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnplaying() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnplaying(Function onplaying) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnpagehide() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnpagehide(Function onpagehide) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnpageshow() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnpageshow(Function onpageshow) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnpopstate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnpopstate(Function onpopstate) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnprogress() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnprogress(Function onprogress) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnratechange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnratechange(Function onratechange) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnreadystatechange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnreadystatechange(Function onreadystatechange) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnredo() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnredo(Function onredo) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnresize() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnresize(Function onresize) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnscroll() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnscroll(Function onscroll) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnseeked() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnseeked(Function onseeked) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnseeking() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnseeking(Function onseeking) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnselect() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnselect(Function onselect) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnshow() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnshow(Function onshow) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnstalled() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnstalled(Function onstalled) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnstorage() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnstorage(Function onstorage) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnsubmit() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnsubmit(Function onsubmit) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnsuspend() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnsuspend(Function onsuspend) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOntimeupdate() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOntimeupdate(Function ontimeupdate) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnundo() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnundo(Function onundo) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnvolumechange() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnvolumechange(Function onvolumechange) {
		// TODO Auto-generated method stub
		
	}

	
	public Function getOnwaiting() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void setOnwaiting(Function onwaiting) {
		// TODO Auto-generated method stub
		
	}
	
	public StyleMedia getStyleMedia() {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getInnerWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getInnerHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getPageXOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getPageYOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void scroll(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	
	public int getScreenX() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getScreenY() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getOuterWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public int getOuterHeight() {
		// TODO Auto-generated method stub
		return 0;
	}
}
