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
 * Created on Nov 12, 2005
 */
package org.loboevolution.html.js;

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

import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.xpath.XPathResultImpl;
import org.loboevolution.html.dom.domimpl.CommentImpl;
import org.loboevolution.html.dom.domimpl.HTMLDivElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLIFrameElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLOptionElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLScriptElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl;
import org.loboevolution.html.dom.domimpl.TextImpl;
import org.loboevolution.html.js.events.EventImpl;
import org.loboevolution.html.js.events.MouseEventImpl;
import org.loboevolution.html.js.events.UIEventImpl;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.js.JavaClassWrapper;
import org.loboevolution.js.JavaClassWrapperFactory;
import org.loboevolution.js.JavaInstantiator;
import org.loboevolution.js.JavaObjectWrapper;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.css.CSS3Properties;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventException;
import org.w3c.dom.views.AbstractView;
import org.w3c.dom.views.DocumentView;

public class Window extends AbstractScriptableDelegate implements AbstractView {
	private static class ExpressionTimerTask extends WeakWindowTask {
		private final String expression;
		private final boolean removeTask;
		// Implemented as a static WeakWindowTask to allow the Window
		// to get garbage collected, especially in infinite loop
		// scenarios.
		private final Integer timeIDInt;

		public ExpressionTimerTask(Window window, Integer timeIDInt, String expression, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.expression = expression;
			this.removeTask = removeTask;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// This executes in the GUI thread and that's good.
			try {
				final Window window = this.getWindow();
				if (window == null) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("actionPerformed(): Window is no longer available.");
					}
					return;
				}
				if (this.removeTask) {
					window.forgetTask(this.timeIDInt, false);
				}
				final HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
				if (doc == null) {
					throw new IllegalStateException("Cannot perform operation when document is unset.");
				}
				window.evalInScope(this.expression);
			} catch (final Throwable err) {
				logger.log(Level.WARNING, "actionPerformed()", err);
			}
		}
	}

	private static class FunctionTimerTask extends WeakWindowTask {
		private final WeakReference<Function> functionRef;
		private final boolean removeTask;
		// Implemented as a static WeakWindowTask to allow the Window
		// to get garbage collected, especially in infinite loop
		// scenarios.
		private final Integer timeIDInt;

		public FunctionTimerTask(Window window, Integer timeIDInt, Function function, boolean removeTask) {
			super(window);
			this.timeIDInt = timeIDInt;
			this.functionRef = new WeakReference<Function>(function);
			this.removeTask = removeTask;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// This executes in the GUI thread and that's good.
			try {
				final Window window = this.getWindow();
				if (window == null) {
					if (logger.isLoggable(Level.INFO)) {
						logger.info("actionPerformed(): Window is no longer available.");
					}
					return;
				}
				if (this.removeTask) {
					window.forgetTask(this.timeIDInt, false);
				}
				final HTMLDocumentImpl doc = (HTMLDocumentImpl) window.getDocument();
				if (doc == null) {
					throw new IllegalStateException("Cannot perform operation when document is unset.");
				}
				final Function function = (Function) this.functionRef.get();
				if (function == null) {
					throw new IllegalStateException("Cannot perform operation. Function is no longer available.");
				}
				Executor.executeFunction(window.getWindowScope(), function, doc.getDocumentURL(),
						window.getUserAgentContext());
			} catch (final Throwable err) {
				logger.log(Level.WARNING, "actionPerformed()", err);
			}
		}
	}

	private static class TaskWrapper {
		public final Timer timer;

		public TaskWrapper(Timer timer, Object retained) {
			this.timer = timer;
		}
	}

	private static abstract class WeakWindowTask implements ActionListener {
		private final WeakReference<Window> windowRef;

		public WeakWindowTask(Window window) {
			this.windowRef = new WeakReference<Window>(window);
		}

		protected Window getWindow() {
			final WeakReference<Window> ref = this.windowRef;
			return ref == null ? null : (Window) ref.get();
		}
	}

	private static final Logger logger = Logger.getLogger(Window.class.getName());
	
	/** The Constant XMLHTTPREQUEST_WRAPPER. */
	private static final JavaClassWrapper XMLHTTPREQUEST_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLHttpRequest.class);

	/** The Constant XMLSERIALIZER_WRAPPER. */
	private static final JavaClassWrapper XMLSERIALIZER_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLSerializer.class);
	
	/** The Constant TEXT_WRAPPER. */
	private static final JavaClassWrapper TEXT_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(TextImpl.class);
	
	/** The Constant EVENT_WRAPPER. */
	private static final JavaClassWrapper EVENT_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(EventImpl.class);
	
	/** The Constant EVENT_WRAPPER. */
	private static final JavaClassWrapper MOUSEVENT_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(MouseEventImpl.class);
	
	/** The Constant EVENT_WRAPPER. */
	private static final JavaClassWrapper UIEVENT_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(UIEventImpl.class);
	
	/** The Constant DOMPARSER_WRAPPER. */
	private static final JavaClassWrapper DOMPARSER_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(DOMParser.class);
	
	private static final Map<HtmlRendererContext, WeakReference<Window>> CONTEXT_WINDOWS = new WeakHashMap<HtmlRendererContext, WeakReference<Window>>();
	
	private static int timerIdCounter = 0;
	
	private static int generateTimerID() {
		synchronized (logger) {
			return timerIdCounter++;
		}
	}

	public static Window getWindow(HtmlRendererContext rcontext) {
		if (rcontext == null) {
			return null;
		}
		synchronized (CONTEXT_WINDOWS) {
			final Reference<Window> wref = (Reference<Window>) CONTEXT_WINDOWS.get(rcontext);
			if (wref != null) {
				final Window window = (Window) wref.get();
				if (window != null) {
					return window;
				}
			}
			final Window window = new Window(rcontext, rcontext.getUserAgentContext());
			CONTEXT_WINDOWS.put(rcontext, new WeakReference<Window>(window));
			return window;
		}
	}

	private volatile HTMLDocumentImpl document;

	private History history;

	private int length;

	private boolean lengthSet = false;

	private Location location;

	private Navigator navigator;

	private Function onunload;

	private final HtmlRendererContext rcontext;

	private Screen screen;

	private Map<Integer, TaskWrapper> taskMap;

	private final UserAgentContext uaContext;
	
    private Scriptable windowScope;
    
	public Window(HtmlRendererContext rcontext, UserAgentContext uaContext) {
		// TODO: Probably need to create a new Window instance
		// for every document. Sharing of Window state between
		// different documents is not correct.
		this.rcontext = rcontext;
		this.uaContext = uaContext;
	}

	public void alert(String message) {
		if (this.rcontext != null) {
			this.rcontext.alert(message);
		}
	}

	public void back() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.back();
		}
	}

	public void blur() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.blur();
		}
	}

	/**
	 * @param aTimerID Timer ID to stop.
	 * @see <a href=
	 *      "http://developer.mozilla.org/en/docs/DOM:window.clearInterval">Window.clearInterval
	 *      interface Definition</a>
	 */
	public void clearInterval(int aTimerID) {
		final Integer key = new Integer(aTimerID);
		forgetTask(key, true);
	}

	private void clearState() {
		Context.enter();
		try {
			Scriptable s = this.getWindowScope();
			if (s != null) {
				Object[] ids = s.getIds();
				for (Object id : ids) {
					if (id instanceof String) {
						s.delete((String) id);
					} else if (id instanceof Integer) {
						s.delete(((Integer) id).intValue());
					}
				}
			}
		} finally {
			Context.exit();
		}
	}

	public void clearTimeout(int timeoutID) {
		final Integer key = new Integer(timeoutID);
		forgetTask(key, true);
	}

	public void close() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.close();
		}
	}

	public boolean confirm(String message) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.confirm(message);
		} else {
			return false;
		}
	}

	private final void defineElementClass(Scriptable scope, final Document document, final String jsClassName, final String elementName, Class<?> javaClass) {
		JavaInstantiator ji = () -> {
			Document d = document;
			if (d == null) {
				throw new IllegalStateException("Document not set in current context.");
			}
			return d.createElement(elementName);
		};
		JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		Function constructorFunction = JavaObjectWrapper.getConstructor(jsClassName, classWrapper, ji);
		ScriptableObject.defineProperty(scope, jsClassName, constructorFunction, ScriptableObject.READONLY);
	}
	
	private Object evalInScope(final String javascript) {
		final Context ctx = Executor.createContext(document.getDocumentURL(), this.uaContext);
		try {
			final String scriptURI = "window.eval";
			return ctx.evaluateString(getWindowScope(), javascript, scriptURI, 1, null);
		} finally {
			Context.exit();
		}
	}

	public void focus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.focus();
		}
	}

	public void forceGC() {
		System.gc();
	}

	private void forgetAllTasks() {
		TaskWrapper[] oldTaskWrappers = null;
		synchronized (this) {
			final Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTaskWrappers = (TaskWrapper[]) taskMap.values().toArray(new TaskWrapper[0]);
				this.taskMap = null;
			}
		}
		if (oldTaskWrappers != null) {
			for (final TaskWrapper taskWrapper : oldTaskWrappers) {
				taskWrapper.timer.stop();
			}
		}
	}

	private void forgetTask(Integer timeoutID, boolean cancel) {
		TaskWrapper oldTimer = null;
		synchronized (this) {
            final Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTimer = (TaskWrapper) taskMap.remove(timeoutID);
			}
		}
		if (oldTimer != null && cancel) {
			oldTimer.timer.stop();
		}
	}

	public CSS3Properties getComputedStyle(HTMLElement element, String pseudoElement) {
		if (element instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) element).getComputedStyle(pseudoElement);
		} else {
			throw new IllegalArgumentException("Element implementation unknown: " + element);
		}
	}
	
    public CSS3Properties getComputedStyle(HTMLElement elt) {
        return getComputedStyle(elt, null);
    }

	public String getDefaultStatus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getDefaultStatus();
		} else {
			return null;
		}
	}

	@Override
	public DocumentView getDocument() {
		return this.document;
	}

	public Document getDocumentNode() {
		return this.document;
	}

	public HTMLCollection getFrames() {
		final Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getFrames();
		}
		return null;
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

	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/**
	 * Gets the number of frames.
	 */
	public int getLength() {
		if (this.lengthSet) {
			return this.length;
		} else {
			final HTMLCollection frames = getFrames();
			return frames == null ? 0 : frames.getLength();
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

	public String getName() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getName();
		} else {
			return null;
		}
	}

	public Navigator getNavigator() {
		synchronized (this) {
			Navigator nav = this.navigator;
			if (nav == null) {
				nav = new Navigator(this);
				this.navigator = nav;
			}
			return nav;
		}
	}

	public Function getOnload() {
		final Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnloadHandler();
		} else {
			return null;
		}
	}

	public Function getOnunload() {
		return this.onunload;
	}

	public Window getOpener() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getOpener());
		} else {
			return null;
		}
	}

	public Window getParent() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getParent());
		} else {
			return null;
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

	public Window getSelf() {
		return this;
	}

	public String getStatus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getStatus();
		} else {
			return null;
		}
	}

	public Window getTop() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return Window.getWindow(rcontext.getTop());
		} else {
			return null;
		}
	}

	public UserAgentContext getUserAgentContext() {
		return this.uaContext;
	}

	public Window getWindow() {
		return this;
	}

	public Scriptable getWindowScope() {
		synchronized (this) {
            Scriptable windowScope = this.windowScope;
			if (windowScope != null) {
				return windowScope;
			}
			final Context ctx = Context.enter();
			try {
                windowScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(this, null);
                windowScope = ctx.initSafeStandardObjects((ScriptableObject)windowScope);
                
                Object xpathresult = JavaScript.getInstance().getJavascriptObject(new XPathResultImpl(), windowScope);
				ScriptableObject.putProperty(windowScope, "XPathResult", xpathresult);
				
				Object consoleJSObj = JavaScript.getInstance().getJavascriptObject(new Console(), windowScope);
				ScriptableObject.putProperty(windowScope, "console", consoleJSObj);

				this.windowScope = windowScope;
				return windowScope;
			} finally {
				Context.exit();
			}
		}
	}

	private void initWindowScope(final Document doc) {
		final Scriptable ws = this.getWindowScope();
		JavaInstantiator jiXhttp = () -> {
			Document d = doc;
			if (d == null) {
				throw new IllegalStateException("Cannot perform operation when document is unset.");
			}
			HTMLDocumentImpl hd;
			try {
				hd = (HTMLDocumentImpl) d;
			} catch (ClassCastException err) {
				throw new IllegalStateException(
						"Cannot perform operation with documents of type " + d.getClass().getName() + ".");
			}
			return new XMLHttpRequest(uaContext, hd.getDocumentURL(), ws);
		};

		JavaInstantiator jiXMLSerializer = () -> new XMLSerializer();
		
		JavaInstantiator text = () -> new TextImpl();
		
		JavaInstantiator event = () -> new EventImpl();
		
		JavaInstantiator jiDomParser = () -> new DOMParser();

		Function xmlHttpRequestC = JavaObjectWrapper.getConstructor("XMLHttpRequest", XMLHTTPREQUEST_WRAPPER, jiXhttp);
		ScriptableObject.defineProperty(ws, "XMLHttpRequest", xmlHttpRequestC, ScriptableObject.READONLY);

		Function xmlserial = JavaObjectWrapper.getConstructor("XMLSerializer", XMLSERIALIZER_WRAPPER, jiXMLSerializer);
		ScriptableObject.defineProperty(ws, "XMLSerializer", xmlserial, ScriptableObject.READONLY);
		
		Function txt = JavaObjectWrapper.getConstructor("Text", TEXT_WRAPPER, text);
		ScriptableObject.defineProperty(ws, "Text", txt, ScriptableObject.READONLY);
		
		Function evt = JavaObjectWrapper.getConstructor("Event", EVENT_WRAPPER, event);
		ScriptableObject.defineProperty(ws, "Event", evt, ScriptableObject.READONLY);
		
		Function mevt = JavaObjectWrapper.getConstructor("MouseEvent", MOUSEVENT_WRAPPER, event);
		ScriptableObject.defineProperty(ws, "MouseEvent", mevt, ScriptableObject.READONLY);
		
		Function uievt = JavaObjectWrapper.getConstructor("UIEvent", UIEVENT_WRAPPER, event);
		ScriptableObject.defineProperty(ws, "UIEvent", uievt, ScriptableObject.READONLY);
		
		Function domParser = JavaObjectWrapper.getConstructor("DOMParser", DOMPARSER_WRAPPER, jiDomParser);
		ScriptableObject.defineProperty(ws, "DOMParser", domParser, ScriptableObject.READONLY);

		defineElementClass(ws, doc, "Comment", "comment", CommentImpl.class);
        defineElementClass(ws, doc, "Image", "img", HTMLImageElementImpl.class);
        defineElementClass(ws, doc, "Script", "script", HTMLScriptElementImpl.class);
        defineElementClass(ws, doc, "IFrame", "iframe", HTMLIFrameElementImpl.class);
        defineElementClass(ws, doc, "Option", "option", HTMLOptionElementImpl.class);
        defineElementClass(ws, doc, "Select", "select", HTMLSelectElementImpl.class);
        defineElementClass(ws, doc, "HTMLDivElement", "div", HTMLDivElementImpl.class);
	}

	public boolean isClosed() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.isClosed();
		} else {
			return false;
		}
	}

	public Node namedItem(String name) {
		final HTMLDocumentImpl doc = this.document;
		if (doc == null) {
			return null;
		}
		final Node node = doc.getElementById(name);
		if (node != null) {
			return node;
		}
		return null;
	}

	public Window open(String url) {
		return this.open(url, "windows");
	}

	public Window open(String url, String windowName) {
		return this.open(url, windowName, "", false);
	}

	public Window open(String url, String windowName, String windowFeatures) {
		return this.open(url, windowName, windowFeatures, false);
	}

	public Window open(String relativeUrl, String windowName, String windowFeatures, boolean replace) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			URL url;
			final Object document = this.document;
			if (document instanceof HTMLDocumentImpl) {
				url = ((HTMLDocumentImpl) document).getFullURL(relativeUrl);
			} else {
				try {
					url = new URL(relativeUrl);
				} catch (final MalformedURLException mfu) {
					throw new IllegalArgumentException("Malformed URI: " + relativeUrl);
				}
			}
			final HtmlRendererContext newContext = rcontext.open(url, windowName, windowFeatures, replace);
			return getWindow(newContext);
		} else {
			return null;
		}
	}

	public String prompt(String message) {
		return this.prompt(message, "");
	}

	public String prompt(String message, int inputDefault) {
		return this.prompt(message, String.valueOf(inputDefault));
	}

	public String prompt(String message, String inputDefault) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.prompt(message, inputDefault);
		} else {
			return null;
		}
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

	public void resizeBy(int byWidth, int byHeight) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeBy(byWidth, byHeight);
		}
	}

	public void resizeTo(int width, int height) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeTo(width, height);
		}
	}

	public void scrollBy(int x, int y) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scrollBy(x, y);
		}
	}

	public void scrollTo(int x, int y) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scroll(x, y);
		}
	}

	public void setDocument(final HTMLDocumentImpl document) {
		final Document prevDocument = this.document;
		if (prevDocument != document) {
			final Function onunload = this.onunload;
			if (onunload != null) {
                final HTMLDocumentImpl oldDoc = (HTMLDocumentImpl) prevDocument;
                Executor.executeFunction(this.getWindowScope(), onunload, oldDoc.getDocumentURL(), this.uaContext);
				this.onunload = null;
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
	 * @param aFunction Javascript function to invoke on each loop.
	 * @param aTimeInMs Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a href=
	 *      "http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
	 *      interface definition</a>
	 * @todo Make proper and refactore with
	 *       {@link Window#setTimeout(Function, double)}.
	 */
	public int setInterval(final Function aFunction, final double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		final ActionListener task = new FunctionTimerTask(this, timeIDInt, aFunction, false);
		int t = (int) aTimeInMs;
		if (t < 1) {
			t = 1;
		}
		final Timer timer = new Timer(t, task);
		timer.setRepeats(true); // The only difference with setTimeout
		putAndStartTask(timeIDInt, timer, aFunction);
		return timeID;
	}

	/**
	 * @param aExpression Javascript expression to invoke on each loop.
	 * @param aTimeInMs   Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 * @see <a href=
	 *      "http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
	 *      interface definition</a>
	 * @todo Make proper and refactore with
	 *       {@link Window#setTimeout(String, double)}.
	 */
	public int setInterval(final String aExpression, double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		final ActionListener task = new ExpressionTimerTask(this, timeIDInt, aExpression, false);
		int t = (int) aTimeInMs;
		if (t < 1) {
			t = 1;
		}
		final Timer timer = new Timer(t, task);
		timer.setRepeats(false); // The only difference with setTimeout
		putAndStartTask(timeIDInt, timer, null);
		return timeID;
	}

	public void setLength(int length) {
		this.lengthSet = true;
		this.length = length;
	}

	public void setLocation(String location) {
		getLocation().setHref(location);
	}

	public void setOnload(Function onload) {
		// Note that body.onload overrides
		// window.onload.
		final Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnloadHandler(onload);
		}
	}

	public void setOnunload(Function onunload) {
		this.onunload = onunload;
	}

	public void setOpener(Window opener) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			if (opener == null) {
				rcontext.setOpener(null);
			} else {
				rcontext.setOpener(opener.rcontext);
			}
		}
	}

	public void setStatus(String message) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.setStatus(message);
		}
	}

	public int setTimeout(final Function function) {
		return setTimeout(function, 0);
	}

	public int setTimeout(final Function function, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		final ActionListener task = new FunctionTimerTask(this, timeIDInt, function, true);
		int t = (int) millis;
		if (t < 1) {
			t = 1;
		}
		final Timer timer = new Timer(t, task);
		timer.setRepeats(false);
		putAndStartTask(timeIDInt, timer, function);
		return timeID;
	}

	public int setTimeout(final String expr) {
		return setTimeout(expr, 0);
	}

	public int setTimeout(final String expr, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = new Integer(timeID);
		final ActionListener task = new ExpressionTimerTask(this, timeIDInt, expr, true);
		int t = (int) millis;
		if (t < 1) {
			t = 1;
		}
		final Timer timer = new Timer(t, task);
		timer.setRepeats(false);
		putAndStartTask(timeIDInt, timer, null);
		return timeID;
	}
	
	public void addEventListener(String type, Function listener) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null && doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).addEventListener(type, listener);
		}
	}

	public void addEventListener(String type, Function listener, boolean useCapture) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.addEventListener(type, listener, useCapture);
		}
	}

	public boolean dispatchEvent(Event evt) throws EventException {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			return doc.dispatchEvent(doc, evt);
		}
		return false;
	}

	public void removeEventListener(String type, Function listener) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.removeEventListener(type, listener);
		}
	}

	public void removeEventListener(String type, Function listener, boolean useCapture) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.removeEventListener(type, listener, useCapture);
		}
	}

}
