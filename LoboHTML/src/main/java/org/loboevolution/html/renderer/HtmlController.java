/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderer;

import lombok.Getter;
import org.loboevolution.events.Event;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.html.js.events.EventImpl;
import org.loboevolution.html.js.events.MouseEventImpl;
import org.loboevolution.js.JavaScript;
import org.loboevolution.js.LoboContextFactory;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import java.awt.event.MouseEvent;

/**
 * <p>HtmlController class.</p>
 */
public class HtmlController {

	@Getter
	private static final HtmlController instance = new HtmlController();

	/**
	 * <p>onChange.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @return a boolean.
	 */
	public boolean onChange(final ModelNode node) {
		if (node instanceof HTMLSelectElementImpl uiElement) {
            final Function f = uiElement.getOnchange();
			if (f != null) {
				final EventImpl evt = new EventImpl();
				evt.initEvent("change", false, false);
				evt.setTarget(uiElement);
				boolean isEx = execute(uiElement, f, evt);
				if (!isEx) return false;
			}
		}
		return false;
	}

	/**
	 * <p>onContextMenu.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	public boolean onContextMenu(final ModelNode node, final MouseEvent event, final int x, final int y) {
		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOncontextmenu();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl(event);
				evt.initMouseEvent("contextmenu", false, false, null, 0, 0, 0,
						x, y, true, true, true, true, 0,  uiElement);
				boolean isEx = execute(uiElement, f, evt);
				if (!isEx) return false;
			}
			final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
			if (rcontext != null) {
				if (!rcontext.onContextMenu(uiElement, event)) {
					return false;
				}
			}
		}
		final ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return onContextMenu(parent, event, 0, 0);
	}

	/**
	 * <p>onDoubleClick.</p>
	 *
	 * @return True to propagate further, false if consumed.
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 */
	public boolean onDoubleClick(final ModelNode node, final MouseEvent event, final int x, final int y) {
		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOndblclick();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl(event);
				evt.initMouseEvent("dblclick", false, false, null, 0, 0,
						0, x, y, true, true, true, true, 0, uiElement);
				boolean isEx = execute(uiElement, f, evt);
				if (!isEx) return false;
			}
		}
		final ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return onDoubleClick(parent, event, 0, 0);
	}

	/**
	 * <p>onEnterPressed.</p>
	 *
	 * @return True to propagate further and false if the event was consumed.
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public boolean onEnterPressed(final ModelNode node) {
		final HTMLInputElementImpl hie = (HTMLInputElementImpl) node;
		hie.submitForm(null);
		return false;
	}

	/**
	 * <p>onMouseClick.</p>
	 *
	 * @return True to propagate further and false if the event was consumed.
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 */
	public boolean onMouseClick(final ModelNode node, final MouseEvent event, final int x, final int y) {
		if (node instanceof HTMLElementImpl uiElement) {
			final Function f = uiElement.getOnclick();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl(event);
				evt.initMouseEvent("click", true, true, null, 0, 0, 0,
						x, y, true, true, true, true, 0, uiElement);
				boolean isEx = execute(uiElement, f, evt);
				if (!isEx) return false;
			}
		}

		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).navigate();
			return false;
		} else if (node instanceof HTMLButtonElementImpl btn) {
            switch (btn.getType()) {
			case "submit":
				btn.submit();
				break;
			case "reset":
				btn.reset();
				break;
			case "button":
			default:
				break;
			}
			return false;
		}

		final ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return onMouseClick(parent, event, 0, 0);
	}

	/**
	 * <p>onMouseDisarmed.</p>
	 *
	 * @return True to propagate further, false if consumed.
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 */
	public boolean onMouseDisarmed(final ModelNode node, final MouseEvent event) {
		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor(null);
			return false;
		}
		final ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return onMouseDisarmed(parent, event);
	}

	/**
	 * <p>onMouseDown.</p>
	 *
	 * @return True to propagate further, false if consumed.
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 */
	public boolean onMouseDown(final ModelNode node, final MouseEvent event, final int x, final int y) {

		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOnmousedown();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl(event);
				evt.initMouseEvent("mousedown", false, false, null, 0, 0, 0,
						x, y, true, true, true, true, 0, uiElement);
				boolean isEx = execute(uiElement, f, evt);
				if (!isEx) return false;
			}
		}

		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor("#9090FF80");
			return false;
		}

		final ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return onMouseDown(parent, event, 0, 0);
	}

	/**
	 * <p>onMouseOut.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public void onMouseOut(final ModelNode modelNode, final MouseEvent event, final int x, final int y, final ModelNode limit) {
		ModelNode node = modelNode;
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof HTMLElementImpl uiElement) {
                uiElement.setMouseOver(false);
				final Function f = uiElement.getOnmouseout();
				if (f != null) {
					final MouseEventImpl evt = new MouseEventImpl(event);
					evt.initMouseEvent("mouseout", false, false, null, 0, 0,
							0, x, y, true, true, true, true, 0, uiElement);
					execute(uiElement, f, evt);
				}
			}
			node = node.getParentModelNode();
		}
	}

	/**
	 * <p>onMouseMoved.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public void onMouseMoved(final ModelNode modelNode, final MouseEvent event, final int x, final int y, final ModelNode limit) {
		ModelNode node = modelNode;
		while (node != null) {
			if (node instanceof HTMLElementImpl uiElement) {
                uiElement.setMouseOver(true);
				final Function f = uiElement.getOnmousemove();
				if (f != null) {
					final MouseEventImpl evt = new MouseEventImpl(event);
					evt.initMouseEvent("mousemove", false, false, null, 0,
							0, 0, x, y, true, true, true, true, 0, uiElement);
					execute(uiElement, f, evt);
				}
			}
			node = node.getParentModelNode();
		}
	}

	/**
	 * <p>onMouseOver.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public void onMouseOver(final ModelNode modelNode, final MouseEvent event, final int x, final int y, final ModelNode limit) {
		ModelNode node = modelNode;
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof HTMLElementImpl uiElement) {
                uiElement.setMouseOver(true);
				final Function f = uiElement.getOnmouseover();
				if (f != null) {
					final MouseEventImpl evt = new MouseEventImpl(event);
					evt.initMouseEvent("mouseover", false, false, null, 0,
							0, 0, x, y, true, true, true, true, 0, uiElement);
					execute(uiElement, f, evt);
				}
			}
			node = node.getParentModelNode();
		}
	}

	/**
	 * <p>onMouseScroll.</p>
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public void onMouseScroll(final ModelNode node) {
		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOnscroll();
			final MouseEventImpl evt = new MouseEventImpl();
			evt.initMouseEvent("scroll", false, false, null, 0, 0, 0,
					0, 0, true, true, true, true, 0, uiElement);
			execute(uiElement, f, evt);
		}
	}

	/**
	 * <p>onMouseUp.</p>
	 *
	 * @return True to propagate further, false if consumed.
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a {@link java.lang.Integer} object.
	 * @param y a {@link java.lang.Integer} object.
	 */
	public boolean onMouseUp(final ModelNode node, final MouseEvent event, final int x, final int y) {
		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOnmouseup();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl(event);
				evt.initMouseEvent("mouseup", false, false, null, 0, 0, 0,
						x, y, true, true, true, true, 0, uiElement);
				execute(uiElement, f, evt);
			}
		}

		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor(null);
			return false;
		}

		final ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return onMouseUp(parent, event, 0, 0);
	}

	/**
	 * <p>onPressed.</p>
	 *
	 * @param node The node generating the event.
	 * @param x    For images only, x coordinate of mouse click.
	 * @param y    For images only, y coordinate of mouse click.
	 * @return True to propagate further, false if consumed.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 */
	public boolean onPressed(final ModelNode node, final MouseEvent event, final int x, final int y) {

		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOnclick();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl(event);
				evt.initMouseEvent("click", false, false, null, 0, 0, 0,
						x, y, true, true, true, true, 0, uiElement);
				execute(uiElement, f, evt);
			}
		}
		
		if (node instanceof HTMLInputElementImpl hie) {
            switch (hie.getType()) {
			case "submit":
				hie.submit();
				break;
			case "reset":
				hie.reset();
				break;
			case "image":
				hie.submitImage(x, y);
				break;
			case "button":
			default:
				break;
			}
		}

		return false;
	}

	public boolean execute(final NodeImpl node, final Function f, Event evt) {
		WindowImpl win = getWindow(node);
		LoboContextFactory contextFactory = win.getContextFactory();
		try (Context ctx = contextFactory.enterContext()) {
			Scriptable windowScope = win.getWindowScope(ctx);
			final Object eventJSObj = JavaScript.getInstance().getJavascriptObject(evt, windowScope);
			ScriptableObject.putProperty(windowScope, "event", eventJSObj);
			if (!Executor.executeFunction(node, f, new Object[0], contextFactory)) {
				return false;
			}
		}
		return true;
	}

	private WindowImpl getWindow(final NodeImpl e) {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) e.getOwnerDocument();
		return (WindowImpl) doc.getDefaultView();
	}
}
