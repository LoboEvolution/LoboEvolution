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

package org.loboevolution.html.js;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import org.loboevolution.html.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.domimpl.HTMLIFrameElementImpl;
import org.loboevolution.html.domimpl.HTMLImageElementImpl;
import org.loboevolution.html.domimpl.HTMLOptionElementImpl;
import org.loboevolution.html.domimpl.HTMLScriptElementImpl;
import org.loboevolution.html.domimpl.HTMLSelectElementImpl;
import org.loboevolution.html.js.xml.XMLHttpRequest;
import org.loboevolution.html.js.xml.XMLSerializer;
import org.loboevolution.html.jsimpl.ConsoleImpl;
import org.loboevolution.html.xpath.XPathResultImpl;
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

public class JSFunction extends AbstractScriptableDelegate{
	
	/** The Constant XMLHTTPREQUEST_WRAPPER. */
	private static final JavaClassWrapper XMLHTTPREQUEST_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLHttpRequest.class);

	/** The Constant DOMPARSER_WRAPPER. */
	private static final JavaClassWrapper DOMPARSER_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(DOMParser.class);

	/** The Constant XMLSERIALIZER_WRAPPER. */
	private static final JavaClassWrapper XMLSERIALIZER_WRAPPER = JavaClassWrapperFactory.getInstance().getClassWrapper(XMLSerializer.class);

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

	/** The oncontextmenu. */
	private Function oncontextmenu;

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

	/** The onloadeddata. */
	private Function onloadeddata;

	/** The onloadedmetadata. */
	private Function onloadedmetadata;

	/** The onloadstart. */
	private Function onloadstart;

	/** The onmessage. */
	private Function onmessage;

	/** The onmousemove. */
	private Function onmousemove;

	/** The onmouseout. */
	private Function onmouseout;

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
	
	/** The onunload. */
	private Function onunload;
		
	/** The ua context. */
	private final UserAgentContext uaContext;
	
	/** The window scope. */
	private ScriptableObject windowScope;
	
	/** The document. */
	private volatile HTMLDocumentImpl document;
	
	/** The task map. */
	private Map<Integer, TaskWrapper> taskMap;
	
	public JSFunction(UserAgentContext uaContext) {
		this.uaContext = uaContext;
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
			Function onunload = this.getOnbeforeunload();
			if (onunload != null) {
				HTMLDocumentImpl oldDoc = this.document;
				Executor.executeFunction(this.getWindowScope(), onunload, oldDoc.getDocumentURL(), this.uaContext);
				this.setOnbeforeunload(null);
			}
			this.document = document;
		}
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
			try {
				Context ctx = Context.enter();
				// Window scope needs to be top-most scope.
				windowScope = (ScriptableObject) JavaScript.getInstance().getJavascriptObject(this, null);
				ctx.initStandardObjects(windowScope);
				Object consoleJSObj = JavaScript.getInstance().getJavascriptObject(new ConsoleImpl(), windowScope);
				ScriptableObject.putProperty(windowScope, "console", consoleJSObj);
				Object xpathresult = JavaScript.getInstance().getJavascriptObject(new XPathResultImpl(), windowScope);
				ScriptableObject.putProperty(windowScope, "XPathResult", xpathresult);
				this.windowScope = windowScope;
				return windowScope;
			} finally {
				Context.exit();
			}
		}
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
	public void putAndStartTask(Integer timeoutID, Timer timer) {
		TaskWrapper oldTaskWrapper = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap == null) {
				taskMap = new HashMap<Integer, TaskWrapper>(4);
				this.taskMap = taskMap;
			} else {
				oldTaskWrapper = taskMap.get(timeoutID);
			}
			taskMap.put(timeoutID, new TaskWrapper(timer));
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
	public void forgetTask(Integer timeoutID, boolean cancel) {
		TaskWrapper oldTimer = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTimer = taskMap.remove(timeoutID);
			}
		}
		if (oldTimer != null && cancel) {
			oldTimer.timer.stop();
		}
	}

	/**
	 * Forget all tasks.
	 */
	public void forgetAllTasks() {
		TaskWrapper[] oldTaskWrappers = null;
		synchronized (this) {
			Map<Integer, TaskWrapper> taskMap = this.taskMap;
			if (taskMap != null) {
				oldTaskWrappers = taskMap.values().toArray(new TaskWrapper[0]);
				this.taskMap = null;
			}
		}
		if (oldTaskWrappers != null) {
			for (TaskWrapper taskWrapper : oldTaskWrappers) {
				taskWrapper.timer.stop();
			}
		}
	}
	
	/**
	 * Inits the window scope.
	 *
	 * @param doc
	 *            the doc
	 */
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

		JavaInstantiator jiDomParser = () -> new DOMParser();

		JavaInstantiator jiXMLSerializer = () -> new XMLSerializer();

		Function xmlHttpRequestC = JavaObjectWrapper.getConstructor("XMLHttpRequest", XMLHTTPREQUEST_WRAPPER, ws, jiXhttp);
		ScriptableObject.defineProperty(ws, "XMLHttpRequest", xmlHttpRequestC, ScriptableObject.READONLY);

		Function domParser = JavaObjectWrapper.getConstructor("DOMParser", DOMPARSER_WRAPPER, ws, jiDomParser);
		ScriptableObject.defineProperty(ws, "DOMParser", domParser, ScriptableObject.READONLY);

		Function xmlserial = JavaObjectWrapper.getConstructor("XMLSerializer", XMLSERIALIZER_WRAPPER, ws, jiXMLSerializer);
		ScriptableObject.defineProperty(ws, "XMLSerializer", xmlserial, ScriptableObject.READONLY);

		// HTML element classes
		this.defineElementClass(ws, doc, "Image", "img", HTMLImageElementImpl.class);
		this.defineElementClass(ws, doc, "Script", "script", HTMLScriptElementImpl.class);
		this.defineElementClass(ws, doc, "IFrame", "iframe", HTMLIFrameElementImpl.class);
		this.defineElementClass(ws, doc, "Option", "option", HTMLOptionElementImpl.class);
		this.defineElementClass(ws, doc, "Select", "select", HTMLSelectElementImpl.class);
	}
	
	private final void defineElementClass(Scriptable scope, final Document document, final String jsClassName,  final String elementName, Class<?> javaClass) {
		JavaInstantiator ji = () -> {
			Document d = document;
			if (d == null) {
				throw new IllegalStateException("Document not set in current context.");
			}
			return d.createElement(elementName);
		};
		JavaClassWrapper classWrapper = JavaClassWrapperFactory.getInstance().getClassWrapper(javaClass);
		Function constructorFunction = JavaObjectWrapper.getConstructor(jsClassName, classWrapper, scope, ji);
		ScriptableObject.defineProperty(scope, jsClassName, constructorFunction, ScriptableObject.READONLY);
	}
	
	/**
	 * Clear state.
	 */
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

	/**
	 * Gets the onabort.
	 *
	 * @return the onabort
	 */
	public Function getOnabort() {
		return onabort;
	}

	/**
	 * Sets the onabort.
	 *
	 * @param onabort
	 *            the new onabort
	 */
	public void setOnabort(Function onabort) {
		this.onabort = onabort;
	}

	/**
	 * Gets the onafterprint.
	 *
	 * @return the onafterprint
	 */
	public Function getOnafterprint() {
		return onafterprint;
	}

	/**
	 * Sets the onafterprint.
	 *
	 * @param onafterprint
	 *            the new onafterprint
	 */
	public void setOnafterprint(Function onafterprint) {
		this.onafterprint = onafterprint;
	}

	/**
	 * Gets the onbeforeprint.
	 *
	 * @return the onbeforeprint
	 */
	public Function getOnbeforeprint() {
		return onbeforeprint;
	}

	/**
	 * Sets the onbeforeprint.
	 *
	 * @param onbeforeprint
	 *            the new onbeforeprint
	 */
	public void setOnbeforeprint(Function onbeforeprint) {
		this.onbeforeprint = onbeforeprint;
	}

	/**
	 * Gets the onbeforeunload.
	 *
	 * @return the onbeforeunload
	 */
	public Function getOnbeforeunload() {
		return onbeforeunload;
	}

	/**
	 * Sets the onbeforeunload.
	 *
	 * @param onbeforeunload
	 *            the new onbeforeunload
	 */
	public void setOnbeforeunload(Function onbeforeunload) {
		this.onbeforeunload = onbeforeunload;
	}

	/**
	 * Gets the onblur.
	 *
	 * @return the onblur
	 */
	public Function getOnblur() {
		return onblur;
	}

	/**
	 * Sets the onblur.
	 *
	 * @param onblur
	 *            the new onblur
	 */
	public void setOnblur(Function onblur) {
		this.onblur = onblur;
	}

	/**
	 * Gets the oncanplay.
	 *
	 * @return the oncanplay
	 */
	public Function getOncanplay() {
		return oncanplay;
	}

	/**
	 * Sets the oncanplay.
	 *
	 * @param oncanplay
	 *            the new oncanplay
	 */
	public void setOncanplay(Function oncanplay) {
		this.oncanplay = oncanplay;
	}

	/**
	 * Gets the oncanplaythrough.
	 *
	 * @return the oncanplaythrough
	 */
	public Function getOncanplaythrough() {
		return oncanplaythrough;
	}

	/**
	 * Sets the oncanplaythrough.
	 *
	 * @param oncanplaythrough
	 *            the new oncanplaythrough
	 */
	public void setOncanplaythrough(Function oncanplaythrough) {
		this.oncanplaythrough = oncanplaythrough;
	}

	/**
	 * Gets the onchange.
	 *
	 * @return the onchange
	 */
	public Function getOnchange() {
		return onchange;
	}

	/**
	 * Sets the onchange.
	 *
	 * @param onchange
	 *            the new onchange
	 */
	public void setOnchange(Function onchange) {
		this.onchange = onchange;
	}

	/**
	 * Gets the oncontextmenu.
	 *
	 * @return the oncontextmenu
	 */
	public Function getOncontextmenu() {
		return oncontextmenu;
	}

	/**
	 * Sets the oncontextmenu.
	 *
	 * @param oncontextmenu
	 *            the new oncontextmenu
	 */
	public void setOncontextmenu(Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	/**
	 * Gets the ondrag.
	 *
	 * @return the ondrag
	 */
	public Function getOndrag() {
		return ondrag;
	}

	/**
	 * Sets the ondrag.
	 *
	 * @param ondrag
	 *            the new ondrag
	 */
	public void setOndrag(Function ondrag) {
		this.ondrag = ondrag;
	}

	/**
	 * Gets the ondragend.
	 *
	 * @return the ondragend
	 */
	public Function getOndragend() {
		return ondragend;
	}

	/**
	 * Sets the ondragend.
	 *
	 * @param ondragend
	 *            the new ondragend
	 */
	public void setOndragend(Function ondragend) {
		this.ondragend = ondragend;
	}

	/**
	 * Gets the ondragenter.
	 *
	 * @return the ondragenter
	 */
	public Function getOndragenter() {
		return ondragenter;
	}

	/**
	 * Sets the ondragenter.
	 *
	 * @param ondragenter
	 *            the new ondragenter
	 */
	public void setOndragenter(Function ondragenter) {
		this.ondragenter = ondragenter;
	}

	/**
	 * Gets the ondragleave.
	 *
	 * @return the ondragleave
	 */
	public Function getOndragleave() {
		return ondragleave;
	}

	/**
	 * Sets the ondragleave.
	 *
	 * @param ondragleave
	 *            the new ondragleave
	 */
	public void setOndragleave(Function ondragleave) {
		this.ondragleave = ondragleave;
	}

	/**
	 * Gets the ondragover.
	 *
	 * @return the ondragover
	 */
	public Function getOndragover() {
		return ondragover;
	}

	/**
	 * Sets the ondragover.
	 *
	 * @param ondragover
	 *            the new ondragover
	 */
	public void setOndragover(Function ondragover) {
		this.ondragover = ondragover;
	}

	/**
	 * Gets the ondragstart.
	 *
	 * @return the ondragstart
	 */
	public Function getOndragstart() {
		return ondragstart;
	}

	/**
	 * Sets the ondragstart.
	 *
	 * @param ondragstart
	 *            the new ondragstart
	 */
	public void setOndragstart(Function ondragstart) {
		this.ondragstart = ondragstart;
	}

	/**
	 * Gets the ondrop.
	 *
	 * @return the ondrop
	 */
	public Function getOndrop() {
		return ondrop;
	}

	/**
	 * Sets the ondrop.
	 *
	 * @param ondrop
	 *            the new ondrop
	 */
	public void setOndrop(Function ondrop) {
		this.ondrop = ondrop;
	}

	/**
	 * Gets the ondurationchange.
	 *
	 * @return the ondurationchange
	 */
	public Function getOndurationchange() {
		return ondurationchange;
	}

	/**
	 * Sets the ondurationchange.
	 *
	 * @param ondurationchange
	 *            the new ondurationchange
	 */
	public void setOndurationchange(Function ondurationchange) {
		this.ondurationchange = ondurationchange;
	}

	/**
	 * Gets the onemptied.
	 *
	 * @return the onemptied
	 */
	public Function getOnemptied() {
		return onemptied;
	}

	/**
	 * Sets the onemptied.
	 *
	 * @param onemptied
	 *            the new onemptied
	 */
	public void setOnemptied(Function onemptied) {
		this.onemptied = onemptied;
	}

	/**
	 * Gets the onended.
	 *
	 * @return the onended
	 */
	public Function getOnended() {
		return onended;
	}

	/**
	 * Sets the onended.
	 *
	 * @param onended
	 *            the new onended
	 */
	public void setOnended(Function onended) {
		this.onended = onended;
	}

	/**
	 * Gets the onerror.
	 *
	 * @return the onerror
	 */
	public Function getOnerror() {
		return onerror;
	}

	/**
	 * Sets the onerror.
	 *
	 * @param onerror
	 *            the new onerror
	 */
	public void setOnerror(Function onerror) {
		this.onerror = onerror;
	}

	/**
	 * Gets the onfocus.
	 *
	 * @return the onfocus
	 */
	public Function getOnfocus() {
		return onfocus;
	}

	/**
	 * Sets the onfocus.
	 *
	 * @param onfocus
	 *            the new onfocus
	 */
	public void setOnfocus(Function onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * Gets the onformchange.
	 *
	 * @return the onformchange
	 */
	public Function getOnformchange() {
		return onformchange;
	}

	/**
	 * Sets the onformchange.
	 *
	 * @param onformchange
	 *            the new onformchange
	 */
	public void setOnformchange(Function onformchange) {
		this.onformchange = onformchange;
	}

	/**
	 * Gets the onforminput.
	 *
	 * @return the onforminput
	 */
	public Function getOnforminput() {
		return onforminput;
	}

	/**
	 * Sets the onforminput.
	 *
	 * @param onforminput
	 *            the new onforminput
	 */
	public void setOnforminput(Function onforminput) {
		this.onforminput = onforminput;
	}

	/**
	 * Gets the onhashchange.
	 *
	 * @return the onhashchange
	 */
	public Function getOnhashchange() {
		return onhashchange;
	}

	/**
	 * Sets the onhashchange.
	 *
	 * @param onhashchange
	 *            the new onhashchange
	 */
	public void setOnhashchange(Function onhashchange) {
		this.onhashchange = onhashchange;
	}

	/**
	 * Gets the oninput.
	 *
	 * @return the oninput
	 */
	public Function getOninput() {
		return oninput;
	}

	/**
	 * Sets the oninput.
	 *
	 * @param oninput
	 *            the new oninput
	 */
	public void setOninput(Function oninput) {
		this.oninput = oninput;
	}

	/**
	 * Gets the oninvalid.
	 *
	 * @return the oninvalid
	 */
	public Function getOninvalid() {
		return oninvalid;
	}

	/**
	 * Sets the oninvalid.
	 *
	 * @param oninvalid
	 *            the new oninvalid
	 */
	public void setOninvalid(Function oninvalid) {
		this.oninvalid = oninvalid;
	}

	/**
	 * Gets the onloadeddata.
	 *
	 * @return the onloadeddata
	 */
	public Function getOnloadeddata() {
		return onloadeddata;
	}

	/**
	 * Sets the onloadeddata.
	 *
	 * @param onloadeddata
	 *            the new onloadeddata
	 */
	public void setOnloadeddata(Function onloadeddata) {
		this.onloadeddata = onloadeddata;
	}

	/**
	 * Gets the onloadedmetadata.
	 *
	 * @return the onloadedmetadata
	 */
	public Function getOnloadedmetadata() {
		return onloadedmetadata;
	}

	/**
	 * Sets the onloadedmetadata.
	 *
	 * @param onloadedmetadata
	 *            the new onloadedmetadata
	 */
	public void setOnloadedmetadata(Function onloadedmetadata) {
		this.onloadedmetadata = onloadedmetadata;
	}

	/**
	 * Gets the onloadstart.
	 *
	 * @return the onloadstart
	 */
	public Function getOnloadstart() {
		return onloadstart;
	}

	/**
	 * Sets the onloadstart.
	 *
	 * @param onloadstart
	 *            the new onloadstart
	 */
	public void setOnloadstart(Function onloadstart) {
		this.onloadstart = onloadstart;
	}

	/**
	 * Gets the onmessage.
	 *
	 * @return the onmessage
	 */
	public Function getOnmessage() {
		return onmessage;
	}

	/**
	 * Sets the onmessage.
	 *
	 * @param onmessage
	 *            the new onmessage
	 */
	public void setOnmessage(Function onmessage) {
		this.onmessage = onmessage;
	}

	/**
	 * Gets the onmousemove.
	 *
	 * @return the onmousemove
	 */
	public Function getOnmousemove() {
		return onmousemove;
	}

	/**
	 * Sets the onmousemove.
	 *
	 * @param onmousemove
	 *            the new onmousemove
	 */
	public void setOnmousemove(Function onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * Gets the onmouseout.
	 *
	 * @return the onmouseout
	 */
	public Function getOnmouseout() {
		return onmouseout;
	}

	/**
	 * Sets the onmouseout.
	 *
	 * @param onmouseout
	 *            the new onmouseout
	 */
	public void setOnmouseout(Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * Gets the onmousewheel.
	 *
	 * @return the onmousewheel
	 */
	public Function getOnmousewheel() {
		return onmousewheel;
	}

	/**
	 * Sets the onmousewheel.
	 *
	 * @param onmousewheel
	 *            the new onmousewheel
	 */
	public void setOnmousewheel(Function onmousewheel) {
		this.onmousewheel = onmousewheel;
	}

	/**
	 * Gets the onoffline.
	 *
	 * @return the onoffline
	 */
	public Function getOnoffline() {
		return onoffline;
	}

	/**
	 * Sets the onoffline.
	 *
	 * @param onoffline
	 *            the new onoffline
	 */
	public void setOnoffline(Function onoffline) {
		this.onoffline = onoffline;
	}

	/**
	 * Gets the ononline.
	 *
	 * @return the ononline
	 */
	public Function getOnonline() {
		return ononline;
	}

	/**
	 * Sets the ononline.
	 *
	 * @param ononline
	 *            the new ononline
	 */
	public void setOnonline(Function ononline) {
		this.ononline = ononline;
	}

	/**
	 * Gets the onpause.
	 *
	 * @return the onpause
	 */
	public Function getOnpause() {
		return onpause;
	}

	/**
	 * Sets the onpause.
	 *
	 * @param onpause
	 *            the new onpause
	 */
	public void setOnpause(Function onpause) {
		this.onpause = onpause;
	}

	/**
	 * Gets the onplay.
	 *
	 * @return the onplay
	 */
	public Function getOnplay() {
		return onplay;
	}

	/**
	 * Sets the onplay.
	 *
	 * @param onplay
	 *            the new onplay
	 */
	public void setOnplay(Function onplay) {
		this.onplay = onplay;
	}

	/**
	 * Gets the onplaying.
	 *
	 * @return the onplaying
	 */
	public Function getOnplaying() {
		return onplaying;
	}

	/**
	 * Sets the onplaying.
	 *
	 * @param onplaying
	 *            the new onplaying
	 */
	public void setOnplaying(Function onplaying) {
		this.onplaying = onplaying;
	}

	/**
	 * Gets the onpagehide.
	 *
	 * @return the onpagehide
	 */
	public Function getOnpagehide() {
		return onpagehide;
	}

	/**
	 * Sets the onpagehide.
	 *
	 * @param onpagehide
	 *            the new onpagehide
	 */
	public void setOnpagehide(Function onpagehide) {
		this.onpagehide = onpagehide;
	}

	/**
	 * Gets the onpageshow.
	 *
	 * @return the onpageshow
	 */
	public Function getOnpageshow() {
		return onpageshow;
	}

	/**
	 * Sets the onpageshow.
	 *
	 * @param onpageshow
	 *            the new onpageshow
	 */
	public void setOnpageshow(Function onpageshow) {
		this.onpageshow = onpageshow;
	}

	/**
	 * Gets the onpopstate.
	 *
	 * @return the onpopstate
	 */
	public Function getOnpopstate() {
		return onpopstate;
	}

	/**
	 * Sets the onpopstate.
	 *
	 * @param onpopstate
	 *            the new onpopstate
	 */
	public void setOnpopstate(Function onpopstate) {
		this.onpopstate = onpopstate;
	}

	/**
	 * Gets the onprogress.
	 *
	 * @return the onprogress
	 */
	public Function getOnprogress() {
		return onprogress;
	}

	/**
	 * Sets the onprogress.
	 *
	 * @param onprogress
	 *            the new onprogress
	 */
	public void setOnprogress(Function onprogress) {
		this.onprogress = onprogress;
	}

	/**
	 * Gets the onratechange.
	 *
	 * @return the onratechange
	 */
	public Function getOnratechange() {
		return onratechange;
	}

	/**
	 * Sets the onratechange.
	 *
	 * @param onratechange
	 *            the new onratechange
	 */
	public void setOnratechange(Function onratechange) {
		this.onratechange = onratechange;
	}

	/**
	 * Gets the onreadystatechange.
	 *
	 * @return the onreadystatechange
	 */
	public Function getOnreadystatechange() {
		return onreadystatechange;
	}

	/**
	 * Sets the onreadystatechange.
	 *
	 * @param onreadystatechange
	 *            the new onreadystatechange
	 */
	public void setOnreadystatechange(Function onreadystatechange) {
		this.onreadystatechange = onreadystatechange;
	}

	/**
	 * Gets the onredo.
	 *
	 * @return the onredo
	 */
	public Function getOnredo() {
		return onredo;
	}

	/**
	 * Sets the onredo.
	 *
	 * @param onredo
	 *            the new onredo
	 */
	public void setOnredo(Function onredo) {
		this.onredo = onredo;
	}

	/**
	 * Gets the onresize.
	 *
	 * @return the onresize
	 */
	public Function getOnresize() {
		return onresize;
	}

	/**
	 * Sets the onresize.
	 *
	 * @param onresize
	 *            the new onresize
	 */
	public void setOnresize(Function onresize) {
		this.onresize = onresize;
	}

	/**
	 * Gets the onscroll.
	 *
	 * @return the onscroll
	 */
	public Function getOnscroll() {
		return onscroll;
	}

	/**
	 * Sets the onscroll.
	 *
	 * @param onscroll
	 *            the new onscroll
	 */
	public void setOnscroll(Function onscroll) {
		this.onscroll = onscroll;
	}

	/**
	 * Gets the onseeked.
	 *
	 * @return the onseeked
	 */
	public Function getOnseeked() {
		return onseeked;
	}

	/**
	 * Sets the onseeked.
	 *
	 * @param onseeked
	 *            the new onseeked
	 */
	public void setOnseeked(Function onseeked) {
		this.onseeked = onseeked;
	}

	/**
	 * Gets the onseeking.
	 *
	 * @return the onseeking
	 */
	public Function getOnseeking() {
		return onseeking;
	}

	/**
	 * Sets the onseeking.
	 *
	 * @param onseeking
	 *            the new onseeking
	 */
	public void setOnseeking(Function onseeking) {
		this.onseeking = onseeking;
	}

	/**
	 * Gets the onselect.
	 *
	 * @return the onselect
	 */
	public Function getOnselect() {
		return onselect;
	}

	/**
	 * Sets the onselect.
	 *
	 * @param onselect
	 *            the new onselect
	 */
	public void setOnselect(Function onselect) {
		this.onselect = onselect;
	}

	/**
	 * Gets the onshow.
	 *
	 * @return the onshow
	 */
	public Function getOnshow() {
		return onshow;
	}

	/**
	 * Sets the onshow.
	 *
	 * @param onshow
	 *            the new onshow
	 */
	public void setOnshow(Function onshow) {
		this.onshow = onshow;
	}

	/**
	 * Gets the onstalled.
	 *
	 * @return the onstalled
	 */
	public Function getOnstalled() {
		return onstalled;
	}

	/**
	 * Sets the onstalled.
	 *
	 * @param onstalled
	 *            the new onstalled
	 */
	public void setOnstalled(Function onstalled) {
		this.onstalled = onstalled;
	}

	/**
	 * Gets the onstorage.
	 *
	 * @return the onstorage
	 */
	public Function getOnstorage() {
		return onstorage;
	}

	/**
	 * Sets the onstorage.
	 *
	 * @param onstorage
	 *            the new onstorage
	 */
	public void setOnstorage(Function onstorage) {
		this.onstorage = onstorage;
	}

	/**
	 * Gets the onsubmit.
	 *
	 * @return the onsubmit
	 */
	public Function getOnsubmit() {
		return onsubmit;
	}

	/**
	 * Sets the onsubmit.
	 *
	 * @param onsubmit
	 *            the new onsubmit
	 */
	public void setOnsubmit(Function onsubmit) {
		this.onsubmit = onsubmit;
	}

	/**
	 * Gets the onsuspend.
	 *
	 * @return the onsuspend
	 */
	public Function getOnsuspend() {
		return onsuspend;
	}

	/**
	 * Sets the onsuspend.
	 *
	 * @param onsuspend
	 *            the new onsuspend
	 */
	public void setOnsuspend(Function onsuspend) {
		this.onsuspend = onsuspend;
	}

	/**
	 * Gets the ontimeupdate.
	 *
	 * @return the ontimeupdate
	 */
	public Function getOntimeupdate() {
		return ontimeupdate;
	}

	/**
	 * Sets the ontimeupdate.
	 *
	 * @param ontimeupdate
	 *            the new ontimeupdate
	 */
	public void setOntimeupdate(Function ontimeupdate) {
		this.ontimeupdate = ontimeupdate;
	}

	/**
	 * Gets the onundo.
	 *
	 * @return the onundo
	 */
	public Function getOnundo() {
		return onundo;
	}

	/**
	 * Sets the onundo.
	 *
	 * @param onundo
	 *            the new onundo
	 */
	public void setOnundo(Function onundo) {
		this.onundo = onundo;
	}

	/**
	 * Gets the onvolumechange.
	 *
	 * @return the onvolumechange
	 */
	public Function getOnvolumechange() {
		return onvolumechange;
	}

	/**
	 * Sets the onvolumechange.
	 *
	 * @param onvolumechange
	 *            the new onvolumechange
	 */
	public void setOnvolumechange(Function onvolumechange) {
		this.onvolumechange = onvolumechange;
	}

	/**
	 * Gets the onwaiting.
	 *
	 * @return the onwaiting
	 */
	public Function getOnwaiting() {
		return onwaiting;
	}

	/**
	 * Sets the onwaiting.
	 *
	 * @param onwaiting
	 *            the new onwaiting
	 */
	public void setOnwaiting(Function onwaiting) {
		this.onwaiting = onwaiting;
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
	 * @return the document
	 */
	public HTMLDocumentImpl getWindowDocument() {
		return document;
	}

}
