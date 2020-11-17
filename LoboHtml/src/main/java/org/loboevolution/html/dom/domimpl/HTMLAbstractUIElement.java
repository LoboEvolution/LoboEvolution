package org.loboevolution.html.dom.domimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.loboevolution.html.js.Executor;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.JavaScript;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.RhinoException;
import org.mozilla.javascript.Scriptable;
import org.w3c.dom.Document;

/**
 * Implements common functionality of most elements.
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLAbstractUIElement extends HTMLElementImpl {
	private Map<String, Function> functionByAttribute = null;

	private Function onfocus, onblur, onclick, ondblclick, onmousedown, onmouseup, onmouseover, onmousemove, onmouseout,
			onkeypress, onkeydown, onkeyup, oncontextmenu, onchange;

	/**
	 * <p>Constructor for HTMLAbstractUIElement.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLAbstractUIElement(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected void assignAttributeField(String normalName, String value) {
		super.assignAttributeField(normalName, value);
		if (normalName.startsWith("on")) {
			synchronized (this) {
				final Map<String, Function> fba = this.functionByAttribute;
				if (fba != null) {
					fba.remove(normalName);
				}
			}
		}
	}

	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		final UINode node = getUINode();
		if (node != null) {
			node.blur();
		}
	}

	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		final UINode node = getUINode();
		if (node != null) {
			node.focus();
		}
	}

	/**
	 * <p>getEventFunction.</p>
	 *
	 * @param varValue a {@link org.mozilla.javascript.Function} object.
	 * @param attributeName a {@link java.lang.String} object.
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	protected Function getEventFunction(Function varValue, String attributeName) {
		if (varValue != null) {
			return varValue;
		}
		final String normalAttributeName = normalizeAttributeName(attributeName);
		synchronized (this) {
			Map<String, Function> fba = this.functionByAttribute;
			Function f = fba == null ? null : fba.get(normalAttributeName);
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
                            ctx.setLanguageVersion(Context.VERSION_1_8);
							f = ctx.compileFunction(thisScope, functionCode, getTagName() + "[" + getId() + "]." + attributeName, 1, null);
                        } catch (final RhinoException ecmaError) {
                            logger.log(Level.WARNING, "Javascript error at " + ecmaError.sourceName() + ":" + ecmaError.lineNumber() + ": " + ecmaError.getMessage(), ecmaError.getMessage());
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
					fba = new HashMap<>(1);
					this.functionByAttribute = fba;
				}
				fba.put(normalAttributeName, f);
			}
			return f;
		}
	}

	/**
	 * <p>Getter for the field onblur.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnblur() {
		return getEventFunction(this.onblur, "onblur");
	}

	/**
	 * <p>Getter for the field onclick.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnclick() {
		return getEventFunction(this.onclick, "onclick");
	}

	/**
	 * <p>Getter for the field oncontextmenu.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOncontextmenu() {
		return getEventFunction(this.oncontextmenu, "oncontextmenu");
	}

	/**
	 * <p>Getter for the field ondblclick.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOndblclick() {
		return getEventFunction(this.ondblclick, "ondblclick");
	}

	/**
	 * <p>Getter for the field onfocus.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnfocus() {
		return getEventFunction(this.onfocus, "onfocus");
	}

	/**
	 * <p>Getter for the field onkeydown.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnkeydown() {
		return getEventFunction(this.onkeydown, "onkeydown");
	}

	/**
	 * <p>Getter for the field onkeypress.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnkeypress() {
		return getEventFunction(this.onkeypress, "onkeypress");
	}

	/**
	 * <p>Getter for the field onkeyup.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnkeyup() {
		return getEventFunction(this.onkeyup, "onkeyup");
	}

	/**
	 * <p>Getter for the field onmousedown.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnmousedown() {
		return getEventFunction(this.onmousedown, "onmousedown");
	}

	/**
	 * <p>Getter for the field onmousemove.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnmousemove() {
		return getEventFunction(this.onmousemove, "onmousemove");
	}

	/**
	 * <p>Getter for the field onmouseout.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnmouseout() {
		return getEventFunction(this.onmouseout, "onmouseout");
	}

	/**
	 * <p>Getter for the field onmouseover.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnmouseover() {
		return getEventFunction(this.onmouseover, "onmouseover");
	}

	/**
	 * <p>Getter for the field onmouseup.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnmouseup() {
		return getEventFunction(this.onmouseup, "onmouseup");
	}
	
	/**
	 * <p>Getter for the field onchange.</p>
	 *
	 * @return a {@link org.mozilla.javascript.Function} object.
	 */
	public Function getOnchange() {
		return getEventFunction(this.onchange, "onchange");
	}

	/**
	 * <p>Setter for the field onblur.</p>
	 *
	 * @param onblur a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnblur(Function onblur) {
		this.onblur = onblur;
	}

	/**
	 * <p>Setter for the field onclick.</p>
	 *
	 * @param onclick a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnclick(Function onclick) {
		this.onclick = onclick;
	}

	/**
	 * <p>Setter for the field oncontextmenu.</p>
	 *
	 * @param oncontextmenu a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOncontextmenu(Function oncontextmenu) {
		this.oncontextmenu = oncontextmenu;
	}

	/**
	 * <p>Setter for the field ondblclick.</p>
	 *
	 * @param ondblclick a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOndblclick(Function ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * <p>Setter for the field onfocus.</p>
	 *
	 * @param onfocus a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnfocus(Function onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * <p>Setter for the field onkeydown.</p>
	 *
	 * @param onkeydown a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnkeydown(Function onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * <p>Setter for the field onkeypress.</p>
	 *
	 * @param onkeypress a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnkeypress(Function onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * <p>Setter for the field onkeyup.</p>
	 *
	 * @param onkeyup a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnkeyup(Function onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * <p>Setter for the field onmousedown.</p>
	 *
	 * @param onmousedown a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnmousedown(Function onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * <p>Setter for the field onmousemove.</p>
	 *
	 * @param onmousemove a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnmousemove(Function onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * <p>Setter for the field onmouseout.</p>
	 *
	 * @param onmouseout a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnmouseout(Function onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * <p>Setter for the field onmouseover.</p>
	 *
	 * @param onmouseover a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnmouseover(Function onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * <p>Setter for the field onmouseup.</p>
	 *
	 * @param onmouseup a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnmouseup(Function onmouseup) {
		this.onmouseup = onmouseup;
	}
	
	/**
	 * <p>Setter for the field onchange.</p>
	 *
	 * @param onchange a {@link org.mozilla.javascript.Function} object.
	 */
	public void setOnchange(Function onchange) {
		this.onchange = onchange;
	}
}
