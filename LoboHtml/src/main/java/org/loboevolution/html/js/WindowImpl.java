/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
/*
 * Created on Nov 12, 2005
 */
package org.loboevolution.html.js;

import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.nodeimpl.TextImpl;
import org.loboevolution.html.dom.xpath.XPathResultImpl;
import org.loboevolution.html.js.css.MediaQueryListImpl;
import org.loboevolution.html.js.events.EventImpl;
import org.loboevolution.html.js.events.MouseEventImpl;
import org.loboevolution.html.js.events.UIEventImpl;
import org.loboevolution.html.js.storage.LocalStorage;
import org.loboevolution.html.js.storage.SessionStorage;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.w3c.dom.events.EventException;

import com.gargoylesoftware.css.dom.CSSRuleListImpl;
import org.loboevolution.html.node.BarProp;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.Selection;
import org.loboevolution.html.node.css.CSS3Properties;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.history.History;
import org.loboevolution.html.node.js.Location;
import org.loboevolution.html.node.js.Navigator;
import org.loboevolution.html.node.js.Screen;
import org.loboevolution.html.node.js.Window;
import org.loboevolution.html.node.js.console.Console;
import org.loboevolution.html.node.js.webstorage.Storage;
import org.loboevolution.html.node.views.DocumentView;

import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;

/**
 * <p>WindowImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class WindowImpl extends AbstractScriptableDelegate implements Window {

	private static final Logger logger = Logger.getLogger(WindowImpl.class.getName());
	
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
	
	/** The Constant STORAGE_WRAPPER. */
	private static final JavaClassWrapper STORAGE_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(Storage.class);
	
	/** The Constant ELEMPARSER_WRAPPER. */
	private static final JavaClassWrapper ELEMARSER_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(ElementImpl.class);
	
	private static final Map<HtmlRendererContext, WeakReference<WindowImpl>> CONTEXT_WINDOWS = new WeakHashMap<>();
	
	private static int timerIdCounter = 0;
	
	private static int generateTimerID() {
		synchronized (logger) {
			return timerIdCounter++;
		}
	}

	/**
	 * <p>getWindow.</p>
	 *
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public static WindowImpl getWindow(HtmlRendererContext rcontext) {
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
			final WindowImpl windowImpl = new WindowImpl(rcontext, rcontext.getUserAgentContext());
			CONTEXT_WINDOWS.put(rcontext, new WeakReference<>(windowImpl));
			return windowImpl;
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

	private ScreenImpl screen;

	private Map<Integer, TaskWrapper> taskMap;

	private final UserAgentContext uaContext;
	
    private Scriptable windowScope;

	private List<String> msg;
    
	/**
	 * <p>Constructor for WindowImpl.</p>
	 *
	 * @param rcontext a {@link org.loboevolution.http.HtmlRendererContext} object.
	 * @param uaContext a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public WindowImpl(HtmlRendererContext rcontext, UserAgentContext uaContext) {
		this.rcontext = rcontext;
		this.uaContext = uaContext;
	}

	/**
	 * <p>alert.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void alert(String message) {
		if (this.rcontext != null) {
			if(this.rcontext.isTest()){
				if(msg == null) msg = new ArrayList<>();
				msg.add(message);
			} else {
				this.rcontext.alert(message);
			}
		}
	}

	/**
	 * <p>back.</p>
	 */
	public void back() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.back();
		}
	}

	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.blur();
		}
	}

	/**
	 * <p>clearInterval.</p>
	 *
	 * @param aTimerID Timer ID to stop.
	 */
	public void clearInterval(int aTimerID) {
		final Integer key = aTimerID;
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
						s.delete((Integer) id);
					}
				}
			}
		} finally {
			Context.exit();
		}
	}

	/**
	 * <p>clearTimeout.</p>
	 *
	 * @param timeoutID a int.
	 */
	public void clearTimeout(int timeoutID) {
		final Integer key = timeoutID;
		forgetTask(key, true);
	}

	/**
	 * <p>close.</p>
	 */
	public void close() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.close();
		}
	}

	/**
	 * <p>confirm.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean confirm(String message) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.confirm(message);
		} else {
			return false;
		}
	}

	private void defineElementClass(Scriptable scope, final Document document, final String jsClassName, final String elementName, Class<?> javaClass) {
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
	
	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.focus();
		}
	}

	/**
	 * <p>forceGC.</p>
	 */
	public void forceGC() {
		System.gc();
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

	void forgetTask(Integer timeoutID, boolean cancel) {
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

	/**
	 * <p>getComputedStyle.</p>
	 *
	 * @param element a {@link orrg.loboevolution.html.node.Element} object.
	 * @param pseudoElement a {@link java.lang.String} object.
	 * @return a {@link CSS3Properties} object.
	 */
	public CSS3Properties getComputedStyle(Element element, String pseudoElement) {
		if (element instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) element).getComputedStyle(pseudoElement);
		} else {
			throw new IllegalArgumentException("Element implementation unknown: " + element);
		}
	}
	
    /**
     * <p>getComputedStyle.</p>
     *
      * @param element a {@link orrg.loboevolution.html.node.Element} object.
     * @return a {@link CSS3Properties} object.
     */
    public CSS3Properties getComputedStyle(Element elt) {
        return getComputedStyle(elt, null);
    }

	/**
	 * <p>getDefaultStatus.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
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

	/**
	 * <p>getDocumentNode.</p>
	 *
	 * @return a {@link org.w3c.dom.Document} object.
	 */
	public Document getDocumentNode() {
		return this.document;
	}

	/**
	 * <p>Getter for the field history.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.History} object.
	 */
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
	
	/**
	 * <p>Getter for the field local storage.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.webstorage.Storage} object.
	 */
    public Storage getLocalStorage() {
        return new LocalStorage();
    }

    /**
	 * <p>Getter for the field session storage.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.webstorage.Storage} object.
	 */
    public Storage getSessionStorage() {
    	 return new SessionStorage();
    }

	/**
	 * <p>getHtmlRendererContext.</p>
	 *
	 * @return a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public HtmlRendererContext getHtmlRendererContext() {
		return this.rcontext;
	}

	/**
	 * Gets the number of frames.
	 *
	 * @return a int.
	 */
	public int getLength() {
		if (this.lengthSet) {
			return this.length;
		}
		return 0;
	}

	/**
	 * <p>Getter for the field location.</p>
	 *
	 * @return a {@link oorg.loboevolution.html.node.js.Location} object.
	 */
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

	/**
	 * <p>getName.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getName() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getName();
		} else {
			return null;
		}
	}

	/**
	 * <p>Getter for the field navigator.</p>
	 *
	 * @return a {@link org.org.loboevolution.html.node.js.Navigator} object.
	 */
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

	/**
	 * <p>getOnload.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnload() {
		final Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getOnloadHandler();
		} else {
			return null;
		}
	}

	/**
	 * <p>Getter for the field onunload.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnunload() {
		return this.onunload;
	}

	/**
	 * <p>getOpener.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl getOpener() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null && rcontext.getOpener() != null) {
			return WindowImpl.getWindow(rcontext.getOpener());
		} else {
			return null;
		}
	}

	/**
	 * <p>getParent.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl getParent() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null && rcontext.getParent() != null) {
			return WindowImpl.getWindow(rcontext.getParent());
		} else {
			return this;
		}
	}

	/**
	 * <p>Getter for the field screen.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.js.Screen} object.
	 */
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

	/**
	 * <p>getSelf.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl getSelf() {
		return this;
	}

	/**
	 * <p>getStatus.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getStatus() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getStatus();
		} else {
			return null;
		}
	}

	/**
	 * <p>getTop.</p>
	 *
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl getTop() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null && rcontext.getTop() != null) {
			return WindowImpl.getWindow(rcontext.getTop());
		} else {
			return null;
		}
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
			final Context ctx = Context.enter();
			try {
                windowScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(this, null);
                windowScope = ctx.initSafeStandardObjects((ScriptableObject)windowScope);
                
                Object xpathresult = JavaScript.getInstance().getJavascriptObject(new XPathResultImpl(), windowScope);
				ScriptableObject.putProperty(windowScope, "XPathResult", xpathresult);
				
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
			return new XMLHttpRequest(getUaContext(), hd.getDocumentURL(), ws);
		};

		JavaInstantiator jiXMLSerializer = XMLSerializer::new;
		
		JavaInstantiator text = TextImpl::new;
		
		JavaInstantiator event = EventImpl::new;
		
		JavaInstantiator jiDomParser = DOMParser::new;
		
		JavaInstantiator localParser = LocalStorage::new;

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
		
		Function elmParser = JavaObjectWrapper.getConstructor("Element", ELEMARSER_WRAPPER, jiDomParser);
		ScriptableObject.defineProperty(ws, "Element", elmParser, ScriptableObject.READONLY);
		
		Function store = JavaObjectWrapper.getConstructor("Storage", STORAGE_WRAPPER, localParser);
		ScriptableObject.defineProperty(ws, "Storage", store, ScriptableObject.READONLY);

		defineElementClass(ws, doc, "Comment", "comment", CommentImpl.class);
        defineElementClass(ws, doc, "Image", "img", HTMLImageElementImpl.class);
        defineElementClass(ws, doc, "Script", "script", HTMLScriptElementImpl.class);
        defineElementClass(ws, doc, "IFrame", "iframe", HTMLIFrameElementImpl.class);
        defineElementClass(ws, doc, "Option", "option", HTMLOptionElementImpl.class);
        defineElementClass(ws, doc, "Select", "select", HTMLSelectElementImpl.class);
        defineElementClass(ws, doc, "Console", "console", ConsoleImpl.class);
        defineElementClass(ws, doc, "HTMLDivElement", "div", HTMLDivElementImpl.class);
        defineElementClass(ws, doc, "HTMLElement", "html", HTMLElementImpl.class);
        defineElementClass(ws, doc, "HTMLDocument", "document", HTMLDocumentImpl.class);
        
    }

	/**
	 * <p>isClosed.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isClosed() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.isClosed();
		} else {
			return false;
		}
	}

	/**
	 * <p>namedItem.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Node} object.
	 */
	public Node namedItem(String name) {
		final HTMLDocumentImpl doc = this.document;
		if (doc == null) {
			return null;
		}
		final Node node = doc.getElementById(name);
		return node;
	}

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl open(String url) {
		return this.open(url, "windows");
	}

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param windowName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl open(String url, String windowName) {
		return this.open(url, windowName, "", false);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param url a {@link java.lang.String} object.
	 * @param windowName a {@link java.lang.String} object.
	 * @param windowFeatures a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl open(String url, String windowName, String windowFeatures) {
		return this.open(url, windowName, windowFeatures, false);
	}

	/**
	 * <p>open.</p>
	 *
	 * @param relativeUrl a {@link java.lang.String} object.
	 * @param windowName a {@link java.lang.String} object.
	 * @param windowFeatures a {@link java.lang.String} object.
	 * @param replace a boolean.
	 * @return a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public WindowImpl open(String relativeUrl, String windowName, String windowFeatures, boolean replace) {
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

	/**
	 * <p>prompt.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	public String prompt(String message) {
		return this.prompt(message, "");
	}

	/**
	 * <p>prompt.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param inputDefault a int.
	 * @return a {@link java.lang.String} object.
	 */
	public String prompt(String message, int inputDefault) {
		return this.prompt(message, String.valueOf(inputDefault));
	}

	/**
	 * <p>prompt.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 * @param inputDefault a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
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

	/**
	 * <p>resizeBy.</p>
	 *
	 * @param byWidth a int.
	 * @param byHeight a int.
	 */
	public void resizeBy(int byWidth, int byHeight) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeBy(byWidth, byHeight);
		}
	}

	/**
	 * <p>resizeTo.</p>
	 *
	 * @param width a int.
	 * @param height a int.
	 */
	public void resizeTo(int width, int height) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.resizeTo(width, height);
		}
	}

	/**
	 * <p>scrollBy.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public void scrollBy(int x, int y) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scrollBy(x, y);
		}
	}

	/**
	 * <p>scrollTo.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public void scrollTo(int x, int y) {
		scroll(x, y);
	}
	
	/**
	 * <p>scroll.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 */
	public void scroll(int x, int y) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.scroll(x, y);
		}
	}

	/**
	 * <p>Setter for the field document.</p>
	 *
	 * @param document a {@link org.loboevolution.html.dom.domimpl.HTMLDocumentImpl} object.
	 */
	public void setDocument(final HTMLDocumentImpl document) {
		final Document prevDocument = this.document;
		if (prevDocument != document) {
			final Function onunload = this.onunload;
			if (onunload != null) {
                final HTMLDocumentImpl oldDoc = (HTMLDocumentImpl) prevDocument;
                Executor.executeFunction(this.getWindowScope(), onunload, oldDoc.getDocumentURL(), this.getUaContext());
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
	 * <p>setInterval.</p>
	 *
	 * @param aFunction Javascript function to invoke on each loop.
	 * @param aTimeInMs Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 *       
	 */
	public int setInterval(final Function aFunction, final double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = timeID;
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
	 * <p>setInterval.</p>
	 *
	 * @param aExpression Javascript expression to invoke on each loop.
	 * @param aTimeInMs   Time in millisecund between each loop.
	 * @return Return the timer ID to use as reference
	 */
	public int setInterval(final String aExpression, double aTimeInMs) {
		if (aTimeInMs > Integer.MAX_VALUE || aTimeInMs < 0) {
			throw new IllegalArgumentException("Timeout value " + aTimeInMs + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = timeID;
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

	/**
	 * <p>Setter for the field length.</p>
	 *
	 * @param length a int.
	 */
	public void setLength(int length) {
		this.lengthSet = true;
		this.length = length;
	}

	/**
	 * <p>Setter for the field location.</p>
	 *
	 * @param location a {@link java.lang.String} object.
	 */
	public void setLocation(String location) {
		getLocation().setHref(location);
	}

	/**
	 * <p>setOnload.</p>
	 *
	 * @param onload a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnload(Function onload) {
		// Note that body.onload overrides
		// window.onload.
		final Document doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			((HTMLDocumentImpl) doc).setOnloadHandler(onload);
		}
	}

	/**
	 * <p>Setter for the field onunload.</p>
	 *
	 * @param onunload a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnunload(Function onunload) {
		this.onunload = onunload;
	}

	/**
	 * <p>setOpener.</p>
	 *
	 * @param opener a {@link org.loboevolution.html.js.WindowImpl} object.
	 */
	public void setOpener(WindowImpl opener) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			if (opener == null) {
				rcontext.setOpener(null);
			} else {
				rcontext.setOpener(opener.rcontext);
			}
		}
	}

	/**
	 * <p>setStatus.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void setStatus(String message) {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			rcontext.setStatus(message);
		}
	}

	/**
	 * <p>setTimeout.</p>
	 *
	 * @param function a {@link org.mozilla.javascript.Function} object.
	 * @return a int.
	 */
	public int setTimeout(final Function function) {
		return setTimeout(function, 0);
	}

	/**
	 * <p>setTimeout.</p>
	 *
	 * @param function a {@link org.mozilla.javascript.Function} object.
	 * @param millis a double.
	 * @return a int.
	 */
	public int setTimeout(final Function function, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = timeID;
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

	/**
	 * <p>setTimeout.</p>
	 *
	 * @param expr a {@link java.lang.String} object.
	 * @return a int.
	 */
	public int setTimeout(final String expr) {
		return setTimeout(expr, 0);
	}

	/**
	 * <p>setTimeout.</p>
	 *
	 * @param expr a {@link java.lang.String} object.
	 * @param millis a double.
	 * @return a int.
	 */
	public int setTimeout(final String expr, double millis) {
		if (millis > Integer.MAX_VALUE || millis < 0) {
			throw new IllegalArgumentException("Timeout value " + millis + " is not supported.");
		}
		final int timeID = generateTimerID();
		final Integer timeIDInt = timeID;
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
	
	/**
	 * <p>addEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	public void addEventListener(String type, Function listener) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null && doc instanceof HTMLDocumentImpl) {
			doc.addEventListener(type, listener);
		}
	}

	/**
	 * <p>addEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param useCapture a boolean.
	 */
	public void addEventListener(String type, Function listener, boolean useCapture) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.addEventListener(type, listener, useCapture);
		}
	}

	/**
	 * <p>dispatchEvent.</p>
	 *
	 * @param evt a {@link org.w3c.dom.events.Event} object.
	 * @return a boolean.
	 * @throws org.w3c.dom.events.EventException if any.
	 */
	public boolean dispatchEvent(Event evt) throws EventException {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			return doc.dispatchEvent(doc, evt);
		}
		return false;
	}

	/**
	 * <p>removeEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	public void removeEventListener(String type, Function listener) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.removeEventListener(type, listener);
		}
	}

	/**
	 * <p>removeEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param useCapture a boolean.
	 */
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.getDocument();
		if (doc != null) {
			doc.removeEventListener(type, listener, useCapture);
		}
	}
	
	/**
	 * <p>getInnerHeight.</p>
	 *
	 * @return a int.
	 */
	public double getInnerHeight() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getInnerHeight();
		}
        return -1;
    }
	
	/**
	 * <p>getInnerWidth.</p>
	 *
	 * @return a int.
	 */
	public double getInnerWidth() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getInnerWidth();
		}
        return -1;
    }
	
	
	/**
	 * <p>getOuterHeight.</p>
	 *
	 * @return a int.
	 */
	public double getOuterHeight() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getOuterHeight();
		}
        return -1;
    }
	
	/**
	 * <p>getOuterWidth.</p>
	 *
	 * @return a int.
	 */
	public double getOuterWidth() {
		final HtmlRendererContext rcontext = this.rcontext;
		if (rcontext != null) {
			return rcontext.getOuterWidth();
		}
        return -1;
    }

	public MediaQueryListImpl matchMedia(final String mediaQueryString) {
		return new MediaQueryListImpl(this, mediaQueryString);
	}

	/**
	 * @return the uaContext
	 */
	public UserAgentContext getUaContext() {
		return uaContext;
	}

	public List<String> getMsg() {
		return msg;
	}

	public void setMsg(List<String> msg) {
		this.msg = msg;
	}

	@Override
	public boolean dispatchEvent(Node element, Event evt) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Function getOnabort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnabort(Function onabort) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnanimationcancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnanimationcancel(Function onanimationcancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnanimationend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnanimationend(Function onanimationend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnanimationiteration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnanimationiteration(Function onanimationiteration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnanimationstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnanimationstart(Function onanimationstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnauxclick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnauxclick(Function onauxclick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnblur() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnblur(Function onblur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncancel(Function oncancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncanplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncanplay(Function oncanplay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncanplaythrough() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncanplaythrough(Function oncanplaythrough) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnchange(Function onchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnclick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnclick(Function onclick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnclose() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnclose(Function onclose) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncontextmenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncontextmenu(Function oncontextmenu) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOncuechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOncuechange(Function oncuechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndblclick() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndblclick(Function ondblclick) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndrag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndrag(Function ondrag) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragend(Function ondragend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragenter(Function ondragenter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragexit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragexit(Function ondragexit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragleave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragleave(Function ondragleave) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragover() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragover(Function ondragover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndragstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndragstart(Function ondragstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndrop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndrop(Function ondrop) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOndurationchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOndurationchange(Function ondurationchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnemptied() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnemptied(Function onemptied) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnended() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnended(Function onended) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnerror() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnerror(Function onerror) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnfocus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnfocus(Function onfocus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnfocusin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnfocusin(Function onfocusin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnfocusout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnfocusout(Function onfocusout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOngotpointercapture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOngotpointercapture(Function ongotpointercapture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOninput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOninput(Function oninput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOninvalid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOninvalid(Function oninvalid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnkeydown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnkeydown(Function onkeydown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnkeypress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnkeypress(Function onkeypress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnkeyup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnkeyup(Function onkeyup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnloadeddata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnloadeddata(Function onloadeddata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnloadedmetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnloadedmetadata(Function onloadedmetadata) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnloadend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnloadend(Function onloadend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnloadstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnloadstart(Function onloadstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnlostpointercapture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnlostpointercapture(Function onlostpointercapture) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmousedown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmousedown(Function onmousedown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseenter(Function onmouseenter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseleave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseleave(Function onmouseleave) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmousemove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmousemove(Function onmousemove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseout(Function onmouseout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseover() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseover(Function onmouseover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnmouseup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnmouseup(Function onmouseup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpause(Function onpause) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnplay() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnplay(Function onplay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnplaying() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnplaying(Function onplaying) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointercancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointercancel(Function onpointercancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointerdown() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointerdown(Function onpointerdown) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointerenter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointerenter(Function onpointerenter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointerleave() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointerleave(Function onpointerleave) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointermove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointermove(Function onpointermove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointerout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointerout(Function onpointerout) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointerover() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointerover(Function onpointerover) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnpointerup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnpointerup(Function onpointerup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnprogress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnprogress(Function onprogress) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnratechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnratechange(Function onratechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnreset() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnreset(Function onreset) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnresize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnresize(Function onresize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnscroll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnscroll(Function onscroll) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnsecuritypolicyviolation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnsecuritypolicyviolation(Function onsecuritypolicyviolation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnseeked() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnseeked(Function onseeked) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnseeking() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnseeking(Function onseeking) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnselect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnselect(Function onselect) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnselectionchange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnselectionchange(Function onselectionchange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnselectstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnselectstart(Function onselectstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnstalled() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnstalled(Function onstalled) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnsubmit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnsubmit(Function onsubmit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnsuspend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnsuspend(Function onsuspend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntimeupdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntimeupdate(Function ontimeupdate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntoggle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntoggle(Function ontoggle) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntouchcancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntouchcancel(Function ontouchcancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntouchend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntouchend(Function ontouchend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntouchmove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntouchmove(Function ontouchmove) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntouchstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntouchstart(Function ontouchstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntransitioncancel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntransitioncancel(Function ontransitioncancel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntransitionend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntransitionend(Function ontransitionend) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntransitionrun() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntransitionrun(Function ontransitionrun) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOntransitionstart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOntransitionstart(Function ontransitionstart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnvolumechange() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnvolumechange(Function onvolumechange) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnwaiting() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnwaiting(Function onwaiting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Function getOnwheel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnwheel(Function onwheel) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String atob(String encodedString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String btoa(String rawString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Console getConsole() {
		return new ConsoleImpl();
	}

	@Override
	public Navigator getClientInformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDefaultStatus(String defaultStatus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getDevicePixelRatio() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDoNotTrack() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIsSecureContext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLocation(Location location) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BarProp getLocationbar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarProp getMenubar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOffscreenBuffering(String offscreenBuffering) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOffscreenBuffering(boolean offscreenBuffering) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOpener(String opener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getPageXOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPageYOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BarProp getPersonalbar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getScreenLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getScreenTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getScreenX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getScreenY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getScrollX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getScrollY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BarProp getScrollbars() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarProp getStatusbar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BarProp getToolbar() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean confirm() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CSSRuleListImpl getMatchedCSSRules(Element elt, String pseudoElt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CSSRuleListImpl getMatchedCSSRules(Element elt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Selection getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveBy(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveTo(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Window open() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void postMessage(String message, String targetOrigin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void print() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String prompt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resizeBy(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resizeTo(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scroll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scroll(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollBy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollBy(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollTo() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void scrollTo(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Window get(int index) {
		// TODO Auto-generated method stub
		return null;
	}
}
