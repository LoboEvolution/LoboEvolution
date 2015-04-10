/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
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

// TODO: Auto-generated Javadoc
/**
 * The Class Window.
 */
public class Window extends AbstractScriptableDelegate implements AbstractView {

    /** The Constant logger. */
    private static final Logger logger = Logger.getLogger(Window.class
            .getName());

    /** The Constant CONTEXT_WINDOWS. */
    private static final Map<HtmlRendererContext, WeakReference<Window>> CONTEXT_WINDOWS = new WeakHashMap<HtmlRendererContext, WeakReference<Window>>();

    /** The Constant XMLHTTPREQUEST_WRAPPER. */
    private static final JavaClassWrapper XMLHTTPREQUEST_WRAPPER = JavaClassWrapperFactory
            .getInstance().getClassWrapper(XMLHttpRequest.class);

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

    /** The onabort. */
    private Function onabort;

    /** The onafterprint. */
    private Function onafterprint;

    /** The onbeforeprint. */
    private Function onbeforeprint;

    /** The onbeforeunload. */
    private Function onbeforeunload;

    /** The onblur. */
    private Function onblur;

    /** The oncanplay. */
    private Function oncanplay;

    /** The oncanplaythrough. */
    private Function oncanplaythrough;

    /** The onchange. */
    private Function onchange;

    /** The onclick. */
    private Function onclick;

    /** The oncontextmenu. */
    private Function oncontextmenu;

    /** The ondblclick. */
    private Function ondblclick;

    /** The ondrag. */
    private Function ondrag;

    /** The ondragend. */
    private Function ondragend;

    /** The ondragenter. */
    private Function ondragenter;

    /** The ondragleave. */
    private Function ondragleave;

    /** The ondragover. */
    private Function ondragover;

    /** The ondragstart. */
    private Function ondragstart;

    /** The ondrop. */
    private Function ondrop;

    /** The ondurationchange. */
    private Function ondurationchange;

    /** The onemptied. */
    private Function onemptied;

    /** The onended. */
    private Function onended;

    /** The onerror. */
    private Function onerror;

    /** The onfocus. */
    private Function onfocus;

    /** The onformchange. */
    private Function onformchange;

    /** The onforminput. */
    private Function onforminput;

    /** The onhashchange. */
    private Function onhashchange;

    /** The oninput. */
    private Function oninput;

    /** The oninvalid. */
    private Function oninvalid;

    /** The onkeydown. */
    private Function onkeydown;

    /** The onkeypress. */
    private Function onkeypress;

    /** The onkeyup. */
    private Function onkeyup;

    /** The onloadeddata. */
    private Function onloadeddata;

    /** The onloadedmetadata. */
    private Function onloadedmetadata;

    /** The onloadstart. */
    private Function onloadstart;

    /** The onmessage. */
    private Function onmessage;

    /** The onmousedown. */
    private Function onmousedown;

    /** The onmousemove. */
    private Function onmousemove;

    /** The onmouseout. */
    private Function onmouseout;

    /** The onmouseover. */
    private Function onmouseover;

    /** The onmouseup. */
    private Function onmouseup;

    /** The onmousewheel. */
    private Function onmousewheel;

    /** The onoffline. */
    private Function onoffline;

    /** The ononline. */
    private Function ononline;

    /** The onpause. */
    private Function onpause;

    /** The onplay. */
    private Function onplay;

    /** The onplaying. */
    private Function onplaying;

    /** The onpagehide. */
    private Function onpagehide;

    /** The onpageshow. */
    private Function onpageshow;

    /** The onpopstate. */
    private Function onpopstate;

    /** The onprogress. */
    private Function onprogress;

    /** The onratechange. */
    private Function onratechange;

    /** The onreadystatechange. */
    private Function onreadystatechange;

    /** The onredo. */
    private Function onredo;

    /** The onresize. */
    private Function onresize;

    /** The onscroll. */
    private Function onscroll;

    /** The onseeked. */
    private Function onseeked;

    /** The onseeking. */
    private Function onseeking;

    /** The onselect. */
    private Function onselect;

    /** The onshow. */
    private Function onshow;

    /** The onstalled. */
    private Function onstalled;

    /** The onstorage. */
    private Function onstorage;

    /** The onsubmit. */
    private Function onsubmit;

    /** The onsuspend. */
    private Function onsuspend;

    /** The ontimeupdate. */
    private Function ontimeupdate;

    /** The onundo. */
    private Function onundo;

    /** The onvolumechange. */
    private Function onvolumechange;

    /** The onwaiting. */
    private Function onwaiting;

    /**
     * Instantiates a new window.
     *
     * @param rcontext
     *            the rcontext
     * @param uaContext
     *            the ua context
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
     * @param document
     *            the new document
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
                HTMLDocumentImpl oldDoc = this.document;
                Executor.executeFunction(this.getWindowScope(), onunload,
                        oldDoc.getDocumentURL(), this.uaContext);
                this.onunload = null;
            }
            this.document = document;
        }
    }

    /*
 * (non-Javadoc)
 * @see org.w3c.dom.views.AbstractView#getDocument()
 */
    @Override
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
     * @param timeoutID
     *            the timeout id
     * @param timer
     *            the timer
     * @param retained
     *            the retained
     */
    private void putAndStartTask(Integer timeoutID, Timer timer, Object retained) {
        TaskWrapper oldTaskWrapper = null;
        synchronized (this) {
            Map<Integer, TaskWrapper> taskMap = this.taskMap;
            if (taskMap == null) {
                taskMap = new HashMap<Integer, TaskWrapper>(4);
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
     * Forget task.
     *
     * @param timeoutID
     *            the timeout id
     * @param cancel
     *            the cancel
     */
    private void forgetTask(Integer timeoutID, boolean cancel) {
        TaskWrapper oldTimer = null;
        synchronized (this) {
            Map<Integer, TaskWrapper> taskMap = this.taskMap;
            if (taskMap != null) {
                oldTimer = taskMap.remove(timeoutID);
            }
        }
        if ((oldTimer != null) && cancel) {
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
                oldTaskWrappers = taskMap.values().toArray(new TaskWrapper[0]);
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
     * @param aFunction
     *            Javascript function to invoke on each loop.
     * @param aTimeInMs
     *            Time in millisecund between each loop.
     * @return Return the timer ID to use as reference
     * @see <a
     *      href="http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
     *      interface definition</a> TODO Make proper and refactore with
     *      {@link Window#setTimeout(Function, double)}.
     */
    public int setInterval(final Function aFunction, final double aTimeInMs) {
        if ((aTimeInMs > Integer.MAX_VALUE) || (aTimeInMs < 0)) {
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
     * @param aExpression
     *            Javascript expression to invoke on each loop.
     * @param aTimeInMs
     *            Time in millisecund between each loop.
     * @return Return the timer ID to use as reference
     * @see <a
     *      href="http://developer.mozilla.org/en/docs/DOM:window.setInterval">Window.setInterval
     *      interface definition</a> TODO Make proper and refactore with
     *      {@link Window#setTimeout(String, double)}.
     */
    public int setInterval(final String aExpression, double aTimeInMs) {
        if ((aTimeInMs > Integer.MAX_VALUE) || (aTimeInMs < 0)) {
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
        HTMLDocumentImpl document = this.document;
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
     * @param doc
     *            the doc
     */
    private void initWindowScope(final Document doc) {
        // Special Javascript class: XMLHttpRequest
        final Scriptable ws = this.getWindowScope();
        JavaInstantiator xi = new JavaInstantiator() {
            @Override
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
        Function xmlHttpRequestC = JavaObjectWrapper.getConstructor(
                "XMLHttpRequest", XMLHTTPREQUEST_WRAPPER, ws, xi);
        ScriptableObject.defineProperty(ws, "XMLHttpRequest", xmlHttpRequestC,
                ScriptableObject.READONLY);

        // HTML element classes
        this.defineElementClass(ws, doc, "Image", "img",
                HTMLImageElementImpl.class);
        this.defineElementClass(ws, doc, "Script", "script",
                HTMLScriptElementImpl.class);
        this.defineElementClass(ws, doc, "IFrame", "iframe",
                HTMLIFrameElementImpl.class);
        this.defineElementClass(ws, doc, "Option", "option",
                HTMLOptionElementImpl.class);
        this.defineElementClass(ws, doc, "Select", "select",
                HTMLSelectElementImpl.class);
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
     * @param scope
     *            the scope
     * @param document
     *            the document
     * @param jsClassName
     *            the js class name
     * @param elementName
     *            the element name
     * @param javaClass
     *            the java class
     */
    private final void defineElementClass(Scriptable scope,
            final Document document, final String jsClassName,
            final String elementName, Class<?> javaClass) {
        JavaInstantiator ji = new JavaInstantiator() {
            @Override
            public Object newInstance() {
                Document d = document;
                if (d == null) {
                    throw new IllegalStateException(
                            "Document not set in current context.");
                }
                return d.createElement(elementName);
            }
        };
        JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance()
                .getClassWrapper(javaClass);
        Function constructorFunction = JavaObjectWrapper.getConstructor(
                jsClassName, classWrapper, scope, ji);
        ScriptableObject.defineProperty(scope, jsClassName,
                constructorFunction, ScriptableObject.READONLY);
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
    public Window open(String relativeUrl, String windowName,
            String windowFeatures, boolean replace) {
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
                    throw new IllegalArgumentException("Malformed URI: "
                            + relativeUrl);
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
     * @param url
     *            the url
     * @return the window
     */
    public Window open(String url) {
        return this.open(url, "window:" + String.valueOf(ID.generateLong()));
    }

    /**
     * Open.
     *
     * @param url
     *            the url
     * @param windowName
     *            the window name
     * @return the window
     */
    public Window open(String url, String windowName) {
        return this.open(url, windowName, "", false);
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
     * @return the window
     */
    public Window open(String url, String windowName, String windowFeatures) {
        return this.open(url, windowName, windowFeatures, false);
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
        if ((millis > Integer.MAX_VALUE) || (millis < 0)) {
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
     * @param function
     *            the function
     * @param millis
     *            the millis
     * @return the int
     */
    public int setTimeout(final Function function, double millis) {
        if ((millis > Integer.MAX_VALUE) || (millis < 0)) {
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
    public CSS2Properties getComputedStyle(HTMLElement element,
            String pseudoElement) {
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
     * @param onload
     *            the new onload
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
     * @param onunload
     *            the new onunload
     */
    public void setOnunload(Function onunload) {
        this.onunload = onunload;
    }

    /**
     * Named item.
     *
     * @param name
     *            the name
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
        public FunctionTimerTask(Window window, Integer timeIDInt,
                Function function, boolean removeTask) {
            super(window);
            this.timeIDInt = timeIDInt;
            this.functionRef = new WeakReference<Function>(function);
            this.removeTask = removeTask;
        }

        /*
 * (non-Javadoc)
 * @see
 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
        @Override
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
                Function function = this.functionRef.get();
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
         * @param window
         *            the window
         * @param timeIDInt
         *            the time id int
         * @param expression
         *            the expression
         * @param removeTask
         *            the remove task
         */
        public ExpressionTimerTask(Window window, Integer timeIDInt,
                String expression, boolean removeTask) {
            super(window);
            this.timeIDInt = timeIDInt;
            this.expression = expression;
            this.removeTask = removeTask;
        }

        /*
 * (non-Javadoc)
 * @see
 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
        @Override
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
         * @param timer
         *            the timer
         * @param retained
         *            the retained
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
     * @param script
     *            the script
     * @param function
     *            the function
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
    public void postMessage(Object message, String targetOrigin,
            MessagePort[] ports) {
        // TODO Auto-generated method stub

    }

    /**
     * @return the onabort
     */
    public Function getOnabort() {
        return onabort;
    }

    /**
     * @param onabort
     *            the onabort to set
     */
    public void setOnabort(Function onabort) {
        this.onabort = onabort;
    }

    /**
     * @return the onafterprint
     */
    public Function getOnafterprint() {
        return onafterprint;
    }

    /**
     * @param onafterprint
     *            the onafterprint to set
     */
    public void setOnafterprint(Function onafterprint) {
        this.onafterprint = onafterprint;
    }

    /**
     * @return the onbeforeprint
     */
    public Function getOnbeforeprint() {
        return onbeforeprint;
    }

    /**
     * @param onbeforeprint
     *            the onbeforeprint to set
     */
    public void setOnbeforeprint(Function onbeforeprint) {
        this.onbeforeprint = onbeforeprint;
    }

    /**
     * @return the onbeforeunload
     */
    public Function getOnbeforeunload() {
        return onbeforeunload;
    }

    /**
     * @param onbeforeunload
     *            the onbeforeunload to set
     */
    public void setOnbeforeunload(Function onbeforeunload) {
        this.onbeforeunload = onbeforeunload;
    }

    /**
     * @return the onblur
     */
    public Function getOnblur() {
        return onblur;
    }

    /**
     * @param onblur
     *            the onblur to set
     */
    public void setOnblur(Function onblur) {
        this.onblur = onblur;
    }

    /**
     * @return the oncanplay
     */
    public Function getOncanplay() {
        return oncanplay;
    }

    /**
     * @param oncanplay
     *            the oncanplay to set
     */
    public void setOncanplay(Function oncanplay) {
        this.oncanplay = oncanplay;
    }

    /**
     * @return the oncanplaythrough
     */
    public Function getOncanplaythrough() {
        return oncanplaythrough;
    }

    /**
     * @param oncanplaythrough
     *            the oncanplaythrough to set
     */
    public void setOncanplaythrough(Function oncanplaythrough) {
        this.oncanplaythrough = oncanplaythrough;
    }

    /**
     * @return the onchange
     */
    public Function getOnchange() {
        return onchange;
    }

    /**
     * @param onchange
     *            the onchange to set
     */
    public void setOnchange(Function onchange) {
        this.onchange = onchange;
    }

    /**
     * @return the onclick
     */
    public Function getOnclick() {
        return onclick;
    }

    /**
     * @param onclick
     *            the onclick to set
     */
    public void setOnclick(Function onclick) {
        this.onclick = onclick;
    }

    /**
     * @return the oncontextmenu
     */
    public Function getOncontextmenu() {
        return oncontextmenu;
    }

    /**
     * @param oncontextmenu
     *            the oncontextmenu to set
     */
    public void setOncontextmenu(Function oncontextmenu) {
        this.oncontextmenu = oncontextmenu;
    }

    /**
     * @return the ondblclick
     */
    public Function getOndblclick() {
        return ondblclick;
    }

    /**
     * @param ondblclick
     *            the ondblclick to set
     */
    public void setOndblclick(Function ondblclick) {
        this.ondblclick = ondblclick;
    }

    /**
     * @return the ondrag
     */
    public Function getOndrag() {
        return ondrag;
    }

    /**
     * @param ondrag
     *            the ondrag to set
     */
    public void setOndrag(Function ondrag) {
        this.ondrag = ondrag;
    }

    /**
     * @return the ondragend
     */
    public Function getOndragend() {
        return ondragend;
    }

    /**
     * @param ondragend
     *            the ondragend to set
     */
    public void setOndragend(Function ondragend) {
        this.ondragend = ondragend;
    }

    /**
     * @return the ondragenter
     */
    public Function getOndragenter() {
        return ondragenter;
    }

    /**
     * @param ondragenter
     *            the ondragenter to set
     */
    public void setOndragenter(Function ondragenter) {
        this.ondragenter = ondragenter;
    }

    /**
     * @return the ondragleave
     */
    public Function getOndragleave() {
        return ondragleave;
    }

    /**
     * @param ondragleave
     *            the ondragleave to set
     */
    public void setOndragleave(Function ondragleave) {
        this.ondragleave = ondragleave;
    }

    /**
     * @return the ondragover
     */
    public Function getOndragover() {
        return ondragover;
    }

    /**
     * @param ondragover
     *            the ondragover to set
     */
    public void setOndragover(Function ondragover) {
        this.ondragover = ondragover;
    }

    /**
     * @return the ondragstart
     */
    public Function getOndragstart() {
        return ondragstart;
    }

    /**
     * @param ondragstart
     *            the ondragstart to set
     */
    public void setOndragstart(Function ondragstart) {
        this.ondragstart = ondragstart;
    }

    /**
     * @return the ondrop
     */
    public Function getOndrop() {
        return ondrop;
    }

    /**
     * @param ondrop
     *            the ondrop to set
     */
    public void setOndrop(Function ondrop) {
        this.ondrop = ondrop;
    }

    /**
     * @return the ondurationchange
     */
    public Function getOndurationchange() {
        return ondurationchange;
    }

    /**
     * @param ondurationchange
     *            the ondurationchange to set
     */
    public void setOndurationchange(Function ondurationchange) {
        this.ondurationchange = ondurationchange;
    }

    /**
     * @return the onemptied
     */
    public Function getOnemptied() {
        return onemptied;
    }

    /**
     * @param onemptied
     *            the onemptied to set
     */
    public void setOnemptied(Function onemptied) {
        this.onemptied = onemptied;
    }

    /**
     * @return the onended
     */
    public Function getOnended() {
        return onended;
    }

    /**
     * @param onended
     *            the onended to set
     */
    public void setOnended(Function onended) {
        this.onended = onended;
    }

    /**
     * @return the onerror
     */
    public Function getOnerror() {
        return onerror;
    }

    /**
     * @param onerror
     *            the onerror to set
     */
    public void setOnerror(Function onerror) {
        this.onerror = onerror;
    }

    /**
     * @return the onfocus
     */
    public Function getOnfocus() {
        return onfocus;
    }

    /**
     * @param onfocus
     *            the onfocus to set
     */
    public void setOnfocus(Function onfocus) {
        this.onfocus = onfocus;
    }

    /**
     * @return the onformchange
     */
    public Function getOnformchange() {
        return onformchange;
    }

    /**
     * @param onformchange
     *            the onformchange to set
     */
    public void setOnformchange(Function onformchange) {
        this.onformchange = onformchange;
    }

    /**
     * @return the onforminput
     */
    public Function getOnforminput() {
        return onforminput;
    }

    /**
     * @param onforminput
     *            the onforminput to set
     */
    public void setOnforminput(Function onforminput) {
        this.onforminput = onforminput;
    }

    /**
     * @return the onhashchange
     */
    public Function getOnhashchange() {
        return onhashchange;
    }

    /**
     * @param onhashchange
     *            the onhashchange to set
     */
    public void setOnhashchange(Function onhashchange) {
        this.onhashchange = onhashchange;
    }

    /**
     * @return the oninput
     */
    public Function getOninput() {
        return oninput;
    }

    /**
     * @param oninput
     *            the oninput to set
     */
    public void setOninput(Function oninput) {
        this.oninput = oninput;
    }

    /**
     * @return the oninvalid
     */
    public Function getOninvalid() {
        return oninvalid;
    }

    /**
     * @param oninvalid
     *            the oninvalid to set
     */
    public void setOninvalid(Function oninvalid) {
        this.oninvalid = oninvalid;
    }

    /**
     * @return the onkeydown
     */
    public Function getOnkeydown() {
        return onkeydown;
    }

    /**
     * @param onkeydown
     *            the onkeydown to set
     */
    public void setOnkeydown(Function onkeydown) {
        this.onkeydown = onkeydown;
    }

    /**
     * @return the onkeypress
     */
    public Function getOnkeypress() {
        return onkeypress;
    }

    /**
     * @param onkeypress
     *            the onkeypress to set
     */
    public void setOnkeypress(Function onkeypress) {
        this.onkeypress = onkeypress;
    }

    /**
     * @return the onkeyup
     */
    public Function getOnkeyup() {
        return onkeyup;
    }

    /**
     * @param onkeyup
     *            the onkeyup to set
     */
    public void setOnkeyup(Function onkeyup) {
        this.onkeyup = onkeyup;
    }

    /**
     * @return the onloadeddata
     */
    public Function getOnloadeddata() {
        return onloadeddata;
    }

    /**
     * @param onloadeddata
     *            the onloadeddata to set
     */
    public void setOnloadeddata(Function onloadeddata) {
        this.onloadeddata = onloadeddata;
    }

    /**
     * @return the onloadedmetadata
     */
    public Function getOnloadedmetadata() {
        return onloadedmetadata;
    }

    /**
     * @param onloadedmetadata
     *            the onloadedmetadata to set
     */
    public void setOnloadedmetadata(Function onloadedmetadata) {
        this.onloadedmetadata = onloadedmetadata;
    }

    /**
     * @return the onloadstart
     */
    public Function getOnloadstart() {
        return onloadstart;
    }

    /**
     * @param onloadstart
     *            the onloadstart to set
     */
    public void setOnloadstart(Function onloadstart) {
        this.onloadstart = onloadstart;
    }

    /**
     * @return the onmessage
     */
    public Function getOnmessage() {
        return onmessage;
    }

    /**
     * @param onmessage
     *            the onmessage to set
     */
    public void setOnmessage(Function onmessage) {
        this.onmessage = onmessage;
    }

    /**
     * @return the onmousedown
     */
    public Function getOnmousedown() {
        return onmousedown;
    }

    /**
     * @param onmousedown
     *            the onmousedown to set
     */
    public void setOnmousedown(Function onmousedown) {
        this.onmousedown = onmousedown;
    }

    /**
     * @return the onmousemove
     */
    public Function getOnmousemove() {
        return onmousemove;
    }

    /**
     * @param onmousemove
     *            the onmousemove to set
     */
    public void setOnmousemove(Function onmousemove) {
        this.onmousemove = onmousemove;
    }

    /**
     * @return the onmouseout
     */
    public Function getOnmouseout() {
        return onmouseout;
    }

    /**
     * @param onmouseout
     *            the onmouseout to set
     */
    public void setOnmouseout(Function onmouseout) {
        this.onmouseout = onmouseout;
    }

    /**
     * @return the onmouseover
     */
    public Function getOnmouseover() {
        return onmouseover;
    }

    /**
     * @param onmouseover
     *            the onmouseover to set
     */
    public void setOnmouseover(Function onmouseover) {
        this.onmouseover = onmouseover;
    }

    /**
     * @return the onmouseup
     */
    public Function getOnmouseup() {
        return onmouseup;
    }

    /**
     * @param onmouseup
     *            the onmouseup to set
     */
    public void setOnmouseup(Function onmouseup) {
        this.onmouseup = onmouseup;
    }

    /**
     * @return the onmousewheel
     */
    public Function getOnmousewheel() {
        return onmousewheel;
    }

    /**
     * @param onmousewheel
     *            the onmousewheel to set
     */
    public void setOnmousewheel(Function onmousewheel) {
        this.onmousewheel = onmousewheel;
    }

    /**
     * @return the onoffline
     */
    public Function getOnoffline() {
        return onoffline;
    }

    /**
     * @param onoffline
     *            the onoffline to set
     */
    public void setOnoffline(Function onoffline) {
        this.onoffline = onoffline;
    }

    /**
     * @return the ononline
     */
    public Function getOnonline() {
        return ononline;
    }

    /**
     * @param ononline
     *            the ononline to set
     */
    public void setOnonline(Function ononline) {
        this.ononline = ononline;
    }

    /**
     * @return the onpause
     */
    public Function getOnpause() {
        return onpause;
    }

    /**
     * @param onpause
     *            the onpause to set
     */
    public void setOnpause(Function onpause) {
        this.onpause = onpause;
    }

    /**
     * @return the onplay
     */
    public Function getOnplay() {
        return onplay;
    }

    /**
     * @param onplay
     *            the onplay to set
     */
    public void setOnplay(Function onplay) {
        this.onplay = onplay;
    }

    /**
     * @return the onplaying
     */
    public Function getOnplaying() {
        return onplaying;
    }

    /**
     * @param onplaying
     *            the onplaying to set
     */
    public void setOnplaying(Function onplaying) {
        this.onplaying = onplaying;
    }

    /**
     * @return the onpagehide
     */
    public Function getOnpagehide() {
        return onpagehide;
    }

    /**
     * @param onpagehide
     *            the onpagehide to set
     */
    public void setOnpagehide(Function onpagehide) {
        this.onpagehide = onpagehide;
    }

    /**
     * @return the onpageshow
     */
    public Function getOnpageshow() {
        return onpageshow;
    }

    /**
     * @param onpageshow
     *            the onpageshow to set
     */
    public void setOnpageshow(Function onpageshow) {
        this.onpageshow = onpageshow;
    }

    /**
     * @return the onpopstate
     */
    public Function getOnpopstate() {
        return onpopstate;
    }

    /**
     * @param onpopstate
     *            the onpopstate to set
     */
    public void setOnpopstate(Function onpopstate) {
        this.onpopstate = onpopstate;
    }

    /**
     * @return the onprogress
     */
    public Function getOnprogress() {
        return onprogress;
    }

    /**
     * @param onprogress
     *            the onprogress to set
     */
    public void setOnprogress(Function onprogress) {
        this.onprogress = onprogress;
    }

    /**
     * @return the onratechange
     */
    public Function getOnratechange() {
        return onratechange;
    }

    /**
     * @param onratechange
     *            the onratechange to set
     */
    public void setOnratechange(Function onratechange) {
        this.onratechange = onratechange;
    }

    /**
     * @return the onreadystatechange
     */
    public Function getOnreadystatechange() {
        return onreadystatechange;
    }

    /**
     * @param onreadystatechange
     *            the onreadystatechange to set
     */
    public void setOnreadystatechange(Function onreadystatechange) {
        this.onreadystatechange = onreadystatechange;
    }

    /**
     * @return the onredo
     */
    public Function getOnredo() {
        return onredo;
    }

    /**
     * @param onredo
     *            the onredo to set
     */
    public void setOnredo(Function onredo) {
        this.onredo = onredo;
    }

    /**
     * @return the onresize
     */
    public Function getOnresize() {
        return onresize;
    }

    /**
     * @param onresize
     *            the onresize to set
     */
    public void setOnresize(Function onresize) {
        this.onresize = onresize;
    }

    /**
     * @return the onscroll
     */
    public Function getOnscroll() {
        return onscroll;
    }

    /**
     * @param onscroll
     *            the onscroll to set
     */
    public void setOnscroll(Function onscroll) {
        this.onscroll = onscroll;
    }

    /**
     * @return the onseeked
     */
    public Function getOnseeked() {
        return onseeked;
    }

    /**
     * @param onseeked
     *            the onseeked to set
     */
    public void setOnseeked(Function onseeked) {
        this.onseeked = onseeked;
    }

    /**
     * @return the onseeking
     */
    public Function getOnseeking() {
        return onseeking;
    }

    /**
     * @param onseeking
     *            the onseeking to set
     */
    public void setOnseeking(Function onseeking) {
        this.onseeking = onseeking;
    }

    /**
     * @return the onselect
     */
    public Function getOnselect() {
        return onselect;
    }

    /**
     * @param onselect
     *            the onselect to set
     */
    public void setOnselect(Function onselect) {
        this.onselect = onselect;
    }

    /**
     * @return the onshow
     */
    public Function getOnshow() {
        return onshow;
    }

    /**
     * @param onshow
     *            the onshow to set
     */
    public void setOnshow(Function onshow) {
        this.onshow = onshow;
    }

    /**
     * @return the onstalled
     */
    public Function getOnstalled() {
        return onstalled;
    }

    /**
     * @param onstalled
     *            the onstalled to set
     */
    public void setOnstalled(Function onstalled) {
        this.onstalled = onstalled;
    }

    /**
     * @return the onstorage
     */
    public Function getOnstorage() {
        return onstorage;
    }

    /**
     * @param onstorage
     *            the onstorage to set
     */
    public void setOnstorage(Function onstorage) {
        this.onstorage = onstorage;
    }

    /**
     * @return the onsubmit
     */
    public Function getOnsubmit() {
        return onsubmit;
    }

    /**
     * @param onsubmit
     *            the onsubmit to set
     */
    public void setOnsubmit(Function onsubmit) {
        this.onsubmit = onsubmit;
    }

    /**
     * @return the onsuspend
     */
    public Function getOnsuspend() {
        return onsuspend;
    }

    /**
     * @param onsuspend
     *            the onsuspend to set
     */
    public void setOnsuspend(Function onsuspend) {
        this.onsuspend = onsuspend;
    }

    /**
     * @return the ontimeupdate
     */
    public Function getOntimeupdate() {
        return ontimeupdate;
    }

    /**
     * @param ontimeupdate
     *            the ontimeupdate to set
     */
    public void setOntimeupdate(Function ontimeupdate) {
        this.ontimeupdate = ontimeupdate;
    }

    /**
     * @return the onundo
     */
    public Function getOnundo() {
        return onundo;
    }

    /**
     * @param onundo
     *            the onundo to set
     */
    public void setOnundo(Function onundo) {
        this.onundo = onundo;
    }

    /**
     * @return the onvolumechange
     */
    public Function getOnvolumechange() {
        return onvolumechange;
    }

    /**
     * @param onvolumechange
     *            the onvolumechange to set
     */
    public void setOnvolumechange(Function onvolumechange) {
        this.onvolumechange = onvolumechange;
    }

    /**
     * @return the onwaiting
     */
    public Function getOnwaiting() {
        return onwaiting;
    }

    /**
     * @param onwaiting
     *            the onwaiting to set
     */
    public void setOnwaiting(Function onwaiting) {
        this.onwaiting = onwaiting;
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
