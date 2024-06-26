/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.events.EventImpl;
import org.loboevolution.html.js.events.MouseEventImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.mozilla.javascript.Function;

import java.awt.*;
import java.awt.event.InputEvent;
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
				if (!Executor.executeFunction(uiElement, f, evt, new Object[0])) {
					return false;
				}
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
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("contextmenu", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				if (!Executor.executeFunction(uiElement, f, evt, new Object[0])) {
					return false;
				}
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
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("dblclick", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				if (!Executor.executeFunction(uiElement, f, evt, new Object[0])) {
					return false;
				}
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
			final MouseEventImpl evt = new MouseEventImpl();
			evt.initMouseEvent("click", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
			evt.setIe(event);
			uiElement.dispatchEvent(uiElement, evt);
			if (f != null) {
				if (!Executor.executeFunction(uiElement, f, evt, new Object[0])) {
					return false;
				}
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
		boolean pass = true;
		
		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOnmousedown();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("mousedown", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				pass = Executor.executeFunction(uiElement, f, evt, new Object[0]);
			}
		}
		
		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor("#9090FF80");
			return false;
		}
		
		if (!pass) {
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
					final MouseEventImpl evt = new MouseEventImpl();
					evt.initMouseEvent("mouseout", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
					evt.setIe(event);
					Executor.executeFunction(uiElement, f, evt, new Object[0]);
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
					final MouseEventImpl evt = new MouseEventImpl();
					evt.initMouseEvent("mousemove", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
					evt.setIe(event);
					Executor.executeFunction(uiElement, f, evt, new Object[0]);
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
					final MouseEventImpl evt = new MouseEventImpl();
					evt.initMouseEvent("mouseover", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
					evt.setIe(event);
					Executor.executeFunction(uiElement, f, evt, new Object[0]);
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
			evt.initMouseEvent("scroll", false, false, null, 0, 0, 0, 0, 0, true, true, true, true, (short) 0, null);
			uiElement.dispatchEvent(uiElement, evt);
			if (f != null) {
				Executor.executeFunction(uiElement, f, evt, new Object[0]);
			}
		}
	}



	private static void setMouseOnMouseOver(final BaseBoundableRenderable renderable, final ModelNode nodeStart, final ModelNode limit) {
		ModelNode node = nodeStart;
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof NodeImpl uiElement) {
                final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
				final RenderState rs = uiElement.getRenderState();
				final Cursor cursorOpt = rs.getCursor();
				if (cursorOpt != null) {
					rcontext.setCursor(cursorOpt);
					break;
				} else {
					if (node.getParentModelNode() == limit) {
						if (renderable instanceof RWord || renderable instanceof RBlank) {
							rcontext.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
						}
					}
				}
			}
			node = node.getParentModelNode();
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
		boolean pass = true;
				
		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOnmouseup();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("mouseup", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				pass = Executor.executeFunction(uiElement, f, evt, new Object[0]);
			}
		}
		
		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor(null);
			return false;
		}

		if (!pass) {
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
	 * @param event a {@link java.awt.event.InputEvent} object.
	 */
	public boolean onPressed(final ModelNode node, final InputEvent event, final int x, final int y) {
		
		if (node instanceof HTMLElementImpl uiElement) {
            final Function f = uiElement.getOnclick();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("click", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				uiElement.dispatchEvent(uiElement, evt);
				if (!Executor.executeFunction(uiElement, f, evt, new Object[0])) {
					return false;
				}
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
}
