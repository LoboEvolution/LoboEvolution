package org.lobobrowser.html.domimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.lobobrowser.html.js.Executor;
import org.lobobrowser.http.UserAgentContext;
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
	private Map functionByAttribute = null;

	private Function onfocus, onblur, onclick, ondblclick, onmousedown, onmouseup, onmouseover, onmousemove, onmouseout,
			onkeypress, onkeydown, onkeyup, oncontextmenu;

	public HTMLAbstractUIElement(String name) {
		super(name);
	}

	@Override
	protected void assignAttributeField(String normalName, String value) {
		super.assignAttributeField(normalName, value);
		if (normalName.startsWith("on")) {
			synchronized (this) {
				final Map fba = this.functionByAttribute;
				if (fba != null) {
					fba.remove(normalName);
				}
			}
		}
	}

	public void blur() {
		final UINode node = getUINode();
		if (node != null) {
			node.blur();
		}
	}

	public void focus() {
		final UINode node = getUINode();
		if (node != null) {
			node.focus();
		}
	}

	protected Function getEventFunction(Function varValue, String attributeName) {
		if (varValue != null) {
			return varValue;
		}
		final String normalAttributeName = normalizeAttributeName(attributeName);
		synchronized (this) {
			Map fba = this.functionByAttribute;
			Function f = fba == null ? null : (Function) fba.get(normalAttributeName);
			if (f != null) {
				return f;
			}
			final UserAgentContext uac = getUserAgentContext();
			if (uac == null) {
				throw new IllegalStateException("No user agent context.");
			}
			if (uac.isScriptingEnabled()) {
				final String attributeValue = getAttribute(attributeName);
				if (attributeValue == null || attributeValue.length() == 0) {
					f = null;
				} else {
					final String functionCode = "function " + normalAttributeName + "_" + System.identityHashCode(this)
							+ "() { " + attributeValue + " }";
					final Document doc = this.document;
					if (doc == null) {
						throw new IllegalStateException("Element does not belong to a document.");
					}
					final Context ctx = Executor.createContext(getDocumentURL(), uac);
					try {
						final Scriptable scope = (Scriptable) doc.getUserData(Executor.SCOPE_KEY);
						if (scope == null) {
							throw new IllegalStateException(
									"Scriptable (scope) instance was expected to be keyed as UserData to document using "
											+ Executor.SCOPE_KEY);
						}
						final Scriptable thisScope = (Scriptable) JavaScript.getInstance().getJavascriptObject(this,
								scope);
						try {
							// TODO: Get right line number for script. //TODO: Optimize this in case it's
							// called multiple times? Is that done?
							f = ctx.compileFunction(thisScope, functionCode,
									getTagName() + "[" + getId() + "]." + attributeName, 1, null);
						} catch (final EcmaError ecmaError) {
							logger.log(Level.WARNING, "Javascript error at " + ecmaError.getSourceName() + ":"
									+ ecmaError.getLineNumber() + ": " + ecmaError.getMessage(), ecmaError);
							f = null;
						} catch (final Throwable err) {
							logger.log(Level.WARNING, "Unable to evaluate Javascript code", err);
							f = null;
						}
					} finally {
						Context.exit();
					}
				}
				if (fba == null) {
					fba = new HashMap(1);
					this.functionByAttribute = fba;
				}
				fba.put(normalAttributeName, f);
			}
			return f;
		}
	}

	public Function getOnblur() {
		return getEventFunction(this.onblur, "onblur");
	}

	public Function getOnclick() {
		return getEventFunction(this.onclick, "onclick");
	}

	public Function getOncontextmenu() {
		return getEventFunction(this.oncontextmenu, "oncontextmenu");
	}

	public Function getOndblclick() {
		return getEventFunction(this.ondblclick, "ondblclick");
	}

	public Function getOnfocus() {
		return getEventFunction(this.onfocus, "onfocus");
	}

	public Function getOnkeydown() {
		return getEventFunction(this.onkeydown, "onkeydown");
	}

	public Function getOnkeypress() {
		return getEventFunction(this.onkeypress, "onkeypress");
	}

	public Function getOnkeyup() {
		return getEventFunction(this.onkeyup, "onkeyup");
	}

	public Function getOnmousedown() {
		return getEventFunction(this.onmousedown, "onmousedown");
	}

	public Function getOnmousemove() {
		return getEventFunction(this.onmousemove, "onmousemove");
	}

	public Function getOnmouseout() {
		return getEventFunction(this.onmouseout, "onmouseout");
	}

	public Function getOnmouseover() {
		return getEventFunction(this.onmouseover, "onmouseover");
	}

	public Function getOnmouseup() {
		return getEventFunction(this.onmouseup, "onmouseup");
	}

	public void setOnblur(Function onblur) {
		this.onblur = onblur;
	}

	public void setOnclick(Function onclick) {
		this.onclick = onclick;
	}

	public void setOncontextmenu(Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	public void setOndblclick(Function ondblclick) {
		this.ondblclick = ondblclick;
	}

	public void setOnfocus(Function onfocus) {
		this.onfocus = onfocus;
	}

	public void setOnkeydown(Function onkeydown) {
		this.onkeydown = onkeydown;
	}

	public void setOnkeypress(Function onkeypress) {
		this.onkeypress = onkeypress;
	}

	public void setOnkeyup(Function onkeyup) {
		this.onkeyup = onkeyup;
	}

	public void setOnmousedown(Function onmousedown) {
		this.onmousedown = onmousedown;
	}

	public void setOnmousemove(Function onmousemove) {
		this.onmousemove = onmousemove;
	}

	public void setOnmouseout(Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	public void setOnmouseover(Function onmouseover) {
		this.onmouseover = onmouseover;
	}

	public void setOnmouseup(Function onmouseup) {
		this.onmouseup = onmouseup;
	}
}
