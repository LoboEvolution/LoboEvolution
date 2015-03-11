package org.lobobrowser.html.domimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.lobobrowser.html.UserAgentContext;
import org.lobobrowser.html.dombl.UINode;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.EcmaError;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;


/**
 * Implements common functionality of most elements.
 */
public class HTMLAbstractUIElement extends HTMLElementImpl {
	
	/** The onload. */
	private Function onfocus, onblur, onclick, ondblclick, onmousedown,
			onmouseup, onmouseover, onmousemove, onmouseout, onkeypress,
			onkeydown, onkeyup, oncontextmenu, onabort, onplay, onplaying,
			onprogress, onreadystatechange, onscroll, onseeked, onseeking,
			onselect, onshow, onstalled, onsubmit, onsuspend, ontimeupdate,
			onwaiting, onvolumechange,onfinish,onstart,onbounce,onload;

	/**
	 * Instantiates a new HTML abstract ui element.
	 *
	 * @param name the name
	 */
	public HTMLAbstractUIElement(String name) {
		super(name);
	}

	/**
	 * Gets the onfinish.
	 *
	 * @return the onfinish
	 */
	public Function getOnfinish() {
		return this.getEventFunction(onfinish, "onfinish");
	}

	/**
	 * Sets the onfinish.
	 *
	 * @param onfinish the new onfinish
	 */
	public void setOnfinish(Function onfinish) {
		this.onfinish = onfinish;
	}

	/**
	 * Gets the onstart.
	 *
	 * @return the onstart
	 */
	public Function getOnstart() {
		return this.getEventFunction(onstart, "onstart");
	}

	/**
	 * Sets the onstart.
	 *
	 * @param onstart the new onstart
	 */
	public void setOnstart(Function onstart) {
		this.onstart = onstart;
	}

	/**
	 * Gets the onbounce.
	 *
	 * @return the onbounce
	 */
	public Function getOnbounce() {
		return this.getEventFunction(onbounce, "onbounce");
	}

	/**
	 * Sets the onbounce.
	 *
	 * @param onbounce the new onbounce
	 */
	public void setOnbounce(Function onbounce) {
		this.onbounce = onbounce;
	}

	/**
	 * Gets the onblur.
	 *
	 * @return the onblur
	 */
	public Function getOnblur() {
		return this.getEventFunction(onblur, "onblur");
	}

	/**
	 * Sets the onblur.
	 *
	 * @param onblur the new onblur
	 */
	public void setOnblur(Function onblur) {
		this.onblur = onblur;
	}

	/**
	 * Gets the onclick.
	 *
	 * @return the onclick
	 */
	public Function getOnclick() {
		return this.getEventFunction(onclick, "onclick");
	}

	/**
	 * Sets the onclick.
	 *
	 * @param onclick the new onclick
	 */
	public void setOnclick(Function onclick) {
		this.onclick = onclick;
	}

	/**
	 * Gets the ondblclick.
	 *
	 * @return the ondblclick
	 */
	public Function getOndblclick() {
		return this.getEventFunction(ondblclick, "ondblclick");
	}

	/**
	 * Sets the ondblclick.
	 *
	 * @param ondblclick the new ondblclick
	 */
	public void setOndblclick(Function ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * Gets the onfocus.
	 *
	 * @return the onfocus
	 */
	public Function getOnfocus() {
		return this.getEventFunction(onfocus, "onfocus");
	}

	/**
	 * Sets the onfocus.
	 *
	 * @param onfocus the new onfocus
	 */
	public void setOnfocus(Function onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * Gets the onkeydown.
	 *
	 * @return the onkeydown
	 */
	public Function getOnkeydown() {
		System.out.println("1");
		return this.getEventFunction(onkeydown, "onkeydown");
	}

	/**
	 * Sets the onkeydown.
	 *
	 * @param onkeydown the new onkeydown
	 */
	public void setOnkeydown(Function onkeydown) {
		System.out.println("2");
		this.onkeydown = onkeydown;
	}

	/**
	 * Gets the onkeypress.
	 *
	 * @return the onkeypress
	 */
	public Function getOnkeypress() {
		return this.getEventFunction(onkeypress, "onkeypress");
	}

	/**
	 * Sets the onkeypress.
	 *
	 * @param onkeypress the new onkeypress
	 */
	public void setOnkeypress(Function onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * Gets the onkeyup.
	 *
	 * @return the onkeyup
	 */
	public Function getOnkeyup() {
		return this.getEventFunction(onkeyup, "onkeyup");
	}

	/**
	 * Sets the onkeyup.
	 *
	 * @param onkeyup the new onkeyup
	 */
	public void setOnkeyup(Function onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * Gets the onmousedown.
	 *
	 * @return the onmousedown
	 */
	public Function getOnmousedown() {
		return this.getEventFunction(onmousedown, "onmousedown");
	}

	/**
	 * Sets the onmousedown.
	 *
	 * @param onmousedown the new onmousedown
	 */
	public void setOnmousedown(Function onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * Gets the onmousemove.
	 *
	 * @return the onmousemove
	 */
	public Function getOnmousemove() {
		return this.getEventFunction(onmousemove, "onmousemove");
	}

	/**
	 * Sets the onmousemove.
	 *
	 * @param onmousemove the new onmousemove
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
		return this.getEventFunction(onmouseout, "onmouseout");
	}

	/**
	 * Sets the onmouseout.
	 *
	 * @param onmouseout the new onmouseout
	 */
	public void setOnmouseout(Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * Gets the onmouseover.
	 *
	 * @return the onmouseover
	 */
	public Function getOnmouseover() {
		return this.getEventFunction(onmouseover, "onmouseover");
	}

	/**
	 * Sets the onmouseover.
	 *
	 * @param onmouseover the new onmouseover
	 */
	public void setOnmouseover(Function onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * Gets the onmouseup.
	 *
	 * @return the onmouseup
	 */
	public Function getOnmouseup() {
		return this.getEventFunction(onmouseup, "onmouseup");
	}

	/**
	 * Sets the onmouseup.
	 *
	 * @param onmouseup the new onmouseup
	 */
	public void setOnmouseup(Function onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * Gets the oncontextmenu.
	 *
	 * @return the oncontextmenu
	 */
	public Function getOncontextmenu() {
		return this.getEventFunction(oncontextmenu, "oncontextmenu");
	}

	/**
	 * Sets the oncontextmenu.
	 *
	 * @param oncontextmenu the new oncontextmenu
	 */
	public void setOncontextmenu(Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	/**
	 * Gets the onabort.
	 *
	 * @return the onabort
	 */
	public Function getOnabort() {
		return this.getEventFunction(onabort, "onabort");
	}

	/**
	 * Sets the onabort.
	 *
	 * @param onabort the new onabort
	 */
	public void setOnabort(Function onabort) {
		this.onabort = onabort;
	}

	/**
	 * Gets the onplay.
	 *
	 * @return the onplay
	 */
	public Function getOnplay() {
		return this.getEventFunction(onplay, "onplay");
	}

	/**
	 * Sets the onplay.
	 *
	 * @param onplay the new onplay
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
		return this.getEventFunction(onplaying, "onplaying");
	}

	/**
	 * Sets the onplaying.
	 *
	 * @param onplaying the new onplaying
	 */
	public void setOnplaying(Function onplaying) {
		this.onplaying = onplaying;
	}

	/**
	 * Gets the onprogress.
	 *
	 * @return the onprogress
	 */
	public Function getOnprogress() {
		return this.getEventFunction(onprogress, "onprogress");
	}

	/**
	 * Sets the onprogress.
	 *
	 * @param onprogress the new onprogress
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
		return this.getEventFunction(onprogress, "onprogress");
	}

	/**
	 * Sets the onratechange.
	 *
	 * @param onratechange the new onratechange
	 */
	public void setOnratechange(Function onratechange) {
	}

	/**
	 * Gets the onreadystatechange.
	 *
	 * @return the onreadystatechange
	 */
	public Function getOnreadystatechange() {
		return this.getEventFunction(onreadystatechange, "onreadystatechange");
	}

	/**
	 * Sets the onreadystatechange.
	 *
	 * @param onreadystatechange the new onreadystatechange
	 */
	public void setOnreadystatechange(Function onreadystatechange) {
		this.onreadystatechange = onreadystatechange;
	}

	/**
	 * Gets the onscroll.
	 *
	 * @return the onscroll
	 */
	public Function getOnscroll() {
		return this.getEventFunction(onscroll, "onscroll");
	}

	/**
	 * Sets the onscroll.
	 *
	 * @param onscroll the new onscroll
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
		return this.getEventFunction(onseeked, "onseeked");
	}

	/**
	 * Sets the onseeked.
	 *
	 * @param onseeked the new onseeked
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
		return this.getEventFunction(onseeking, "onseeking");
	}

	/**
	 * Sets the onseeking.
	 *
	 * @param onseeking the new onseeking
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
		return this.getEventFunction(onselect, "onselect");
	}

	/**
	 * Sets the onselect.
	 *
	 * @param onselect the new onselect
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
		return this.getEventFunction(onshow, "onshow");
	}

	/**
	 * Sets the onshow.
	 *
	 * @param onshow the new onshow
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
		return this.getEventFunction(onstalled, "onstalled");
	}

	/**
	 * Sets the onstalled.
	 *
	 * @param onstalled the new onstalled
	 */
	public void setOnstalled(Function onstalled) {
		this.onstalled = onstalled;
	}

	/**
	 * Gets the onsubmit.
	 *
	 * @return the onsubmit
	 */
	public Function getOnsubmit() {
		return this.getEventFunction(onsubmit, "onsubmit");
	}

	/**
	 * Sets the onsubmit.
	 *
	 * @param onsubmit the new onsubmit
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
		return this.getEventFunction(onsuspend, "onsuspend");
	}

	/**
	 * Sets the onsuspend.
	 *
	 * @param onsuspend the new onsuspend
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
		return this.getEventFunction(ontimeupdate, "ontimeupdate");
	}

	/**
	 * Sets the ontimeupdate.
	 *
	 * @param ontimeupdate the new ontimeupdate
	 */
	public void setOntimeupdate(Function ontimeupdate) {
		this.ontimeupdate = ontimeupdate;
	}

	/**
	 * Gets the onvolumechange.
	 *
	 * @return the onvolumechange
	 */
	public Function getOnvolumechange() {
		return this.getEventFunction(onvolumechange, "onvolumechange");
	}

	/**
	 * Sets the onvolumechange.
	 *
	 * @param onvolumechange the new onvolumechange
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
		return this.getEventFunction(onwaiting, "onwaiting");
	}

	/**
	 * Sets the onwaiting.
	 *
	 * @param onwaiting the new onwaiting
	 */
	public void setOnwaiting(Function onwaiting) {
		this.onwaiting = onwaiting;
	}
	
	/**
	 * Gets the onload.
	 *
	 * @return the onload
	 */
	public Function getOnload() {
		return this.getEventFunction(onload, "onload");
	}

	/**
	 * Sets the onload.
	 *
	 * @param onload the new onload
	 */
	public void setOnload(Function onload) {
		this.onload = onload;
	}
	
	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#focus()
	 */
	public void focus() {
		UINode node = this.getUINode();
		if (node != null) {
			node.focus();
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#blur()
	 */
	public void blur() {
		UINode node = this.getUINode();
		if (node != null) {
			node.blur();
		}
	}

	/** The function by attribute. */
	private Map<String, Function> functionByAttribute = null;

	/**
	 * Gets the event function.
	 *
	 * @param varValue the var value
	 * @param attributeName the attribute name
	 * @return the event function
	 */
	public Function getEventFunction(Function varValue, String attributeName) {
		
		if (varValue != null) {
			return varValue;
		}
		
		String normalAttributeName = this.normalizeAttributeName(attributeName);
		synchronized (this) {
			Map<String, Function> fba = this.functionByAttribute;
			Function f = fba == null ? null : (Function) fba.get(normalAttributeName);
			if (f != null) {
				return f;
			}
			UserAgentContext uac = this.getUserAgentContext();
			if (uac == null) {
				throw new IllegalStateException("No user agent context.");
			}
			if (uac.isScriptingEnabled()) {
				String attributeValue = this.getAttribute(attributeName);
				if (attributeValue == null || attributeValue.length() == 0) {
					f = null;
				} else {
					String functionCode = "function " + normalAttributeName
							+ "_" + System.identityHashCode(this) + "() { "
							+ attributeValue + " }";
					Document doc = this.document;
					if (doc == null) {
						throw new IllegalStateException(
								"Element does not belong to a document.");
					}
					Context ctx = Executor.createContext(this.getDocumentURL(),uac);
					try {
						Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);
						if (scope == null) {
							throw new IllegalStateException(
									"Scriptable (scope) instance was expected to be keyed as UserData to document using "
											+ Executor.SCOPE_KEY);
						}
						Scriptable thisScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(this, scope);
						try {
							// TODO: Get right line number for script. //TODO:
							// Optimize this in case it's called multiple times?
							// Is that done?
							f = ctx.compileFunction(thisScope, functionCode,
									this.getTagName() + "[" + this.getId()
											+ "]." + attributeName, 1, null);
						} catch (EcmaError ecmaError) {
							logger.log(
									Level.WARNING,
									"Javascript error at "
											+ ecmaError.sourceName() + ":"
											+ ecmaError.lineNumber() + ": "
											+ ecmaError.getMessage(), ecmaError);
							f = null;
						} catch (Throwable err) {
							logger.log(Level.WARNING,
									"Unable to evaluate Javascript code", err);
							f = null;
						}
					} finally {
						Context.exit();
					}
				}
				if (fba == null) {
					fba = new HashMap<String, Function>(1);
					this.functionByAttribute = fba;
				}
				fba.put(normalAttributeName, f);
			}
			return f;
		}
	}

	/* (non-Javadoc)
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#assignAttributeField(java.lang.String, java.lang.String)
	 */
	protected void assignAttributeField(String normalName, String value) {
		super.assignAttributeField(normalName, value);
		if (normalName.startsWith("on")) {
			synchronized (this) {
				Map<String, Function> fba = this.functionByAttribute;
				if (fba != null) {
					fba.remove(normalName);
				}
			}
		}
	}
}
