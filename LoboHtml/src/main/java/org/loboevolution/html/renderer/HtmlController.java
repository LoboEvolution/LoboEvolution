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

package org.loboevolution.html.renderer;

import java.awt.Cursor;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.loboevolution.html.dom.domimpl.HTMLButtonElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.html.dom.domimpl.HTMLSelectElementImpl;
import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.events.EventImpl;
import org.loboevolution.html.js.events.MouseEventImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.HtmlRendererContext;
import org.mozilla.javascript.Function;

/**
 * <p>HtmlController class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HtmlController {
	private static final HtmlController instance = new HtmlController();
	private static final Logger logger = Logger.getLogger(HtmlController.class.getName());

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.HtmlController} object.
	 */
	public static HtmlController getInstance() {
		return instance;
	}

	/**
	 * <p>onChange.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @return a boolean.
	 */
	public boolean onChange(ModelNode node) {
		if (node instanceof HTMLSelectElementImpl) {
			final HTMLSelectElementImpl uiElement = (HTMLSelectElementImpl) node;
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
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onContextMenu(ModelNode node, MouseEvent event, int x, int y) {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("onContextMenu(): node=" + node + ",class=" + node.getClass().getName());
		}
		
		if (node instanceof HTMLElementImpl) {
			final HTMLElementImpl uiElement = (HTMLElementImpl) node;
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
	 * @param x a int.
	 * @param y a int.
	 */
	public boolean onDoubleClick(ModelNode node, MouseEvent event, int x, int y) {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("onDoubleClick(): node=" + node + ",class=" + node.getClass().getName());
		}
		if (node instanceof HTMLElementImpl) {
			final HTMLElementImpl uiElement = (HTMLElementImpl) node;
			final Function f = uiElement.getOndblclick();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("dblclick", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				if (!Executor.executeFunction(uiElement, f, evt, new Object[0])) {
					return false;
				}
			}
			final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
			if (rcontext != null) {
				if (!rcontext.onDoubleClick(uiElement, event)) {
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
	public boolean onEnterPressed(ModelNode node) {
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
	 * @param x a int.
	 * @param y a int.
	 */
	public boolean onMouseClick(ModelNode node, MouseEvent event, int x, int y) {
		if (logger.isLoggable(Level.INFO)) {
			logger.info("onMouseClick(): node=" + node + ",class=" + node.getClass().getName());
		}
		
		if (node instanceof HTMLElementImpl) {
			final HTMLElementImpl uiElement = (HTMLElementImpl) node;
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
			final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
			if (rcontext != null) {
				if (!rcontext.onMouseClick(uiElement, event)) {
					return false;
				}
			}
		}
		
		if (node instanceof HTMLLinkElementImpl) {
			((HTMLLinkElementImpl) node).navigate();
			return false;
		} else if (node instanceof HTMLButtonElementImpl) {
			final HTMLButtonElementImpl btn = (HTMLButtonElementImpl) node;
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
	public boolean onMouseDisarmed(ModelNode node, MouseEvent event) {
		if (node instanceof HTMLLinkElementImpl) {
			((HTMLLinkElementImpl) node).getCurrentStyle().setOverlayColor(null);
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
	 * @param x a int.
	 * @param y a int.
	 */
	public boolean onMouseDown(ModelNode node, MouseEvent event, int x, int y) {
		boolean pass = true;
		
		if (node instanceof HTMLElementImpl) {
			final HTMLElementImpl uiElement = (HTMLElementImpl) node;
			final Function f = uiElement.getOnmousedown();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("mousedown", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				pass = Executor.executeFunction(uiElement, f, evt, new Object[0]);
			}
		}
		
		if (node instanceof HTMLLinkElementImpl) {
			((HTMLLinkElementImpl) node).getCurrentStyle().setOverlayColor("#9090FF80");
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
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public void onMouseOut(ModelNode node, MouseEvent event, int x, int y, ModelNode limit) {
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof HTMLElementImpl) {
				final HTMLElementImpl uiElement = (HTMLElementImpl) node;
				uiElement.setMouseOver(false);
				final Function f = uiElement.getOnmouseout();
				if (f != null) {
					final MouseEventImpl evt = new MouseEventImpl();
					evt.initMouseEvent("mouseout", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
					evt.setIe(event);
					Executor.executeFunction(uiElement, f, evt, new Object[0]);
				}
				final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
				if (rcontext != null) {
					rcontext.onMouseOut(uiElement, event);
				}
			}
			node = node.getParentModelNode();
		}
	}

	/**
	 * <p>onMouseOver.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public void onMouseOver(ModelNode node, MouseEvent event, int x, int y, ModelNode limit) {
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof HTMLElementImpl) {
				final HTMLElementImpl uiElement = (HTMLElementImpl) node;
				uiElement.setMouseOver(true);
				final Function f = uiElement.getOnmouseover();
				if (f != null) {
					final MouseEventImpl evt = new MouseEventImpl();
					evt.initMouseEvent("mouseover", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
					evt.setIe(event);
					Executor.executeFunction(uiElement, f, evt, new Object[0]);
				}
				final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
				if (rcontext != null) {
					rcontext.onMouseOver(uiElement, event);
				}
			}
			node = node.getParentModelNode();
		}
	}
	
	
	/**
	 * <p>onMouseOver.</p>
	 *
	 * @param renderable a {@link org.loboevolution.html.renderer.BaseBoundableRenderable} object.
	 * @param nodeStart a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @param limit a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public void onMouseOver(final BaseBoundableRenderable renderable, final ModelNode nodeStart, final MouseEvent event, final int x, final int y, final ModelNode limit) {
		ModelNode node = nodeStart;
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof HTMLElementImpl) {
				final HTMLElementImpl uiElement = (HTMLElementImpl) node;
				uiElement.setMouseOver(true);
				final Function f = uiElement.getOnmouseover();
				if (f != null) {
					final MouseEventImpl evt = new MouseEventImpl();
					evt.initMouseEvent("mouseover", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
					evt.setIe(event);
					Executor.executeFunction(uiElement, f, evt, new Object[0]);
				}
				final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
				if (rcontext != null) {
					rcontext.onMouseOver(uiElement, event);
				}
			}
			node = node.getParentModelNode();
		}

		setMouseOnMouseOver(renderable, nodeStart, limit);
	}

	private static void setMouseOnMouseOver(final BaseBoundableRenderable renderable, final ModelNode nodeStart, final ModelNode limit) {
		ModelNode node = nodeStart;
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof NodeImpl) {
				final NodeImpl uiElement = (NodeImpl) node;
				final HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
				final RenderState rs = uiElement.getRenderState();
				final Optional<Cursor> cursorOpt = rs.getCursor();
				if (cursorOpt.isPresent()) {
					rcontext.setCursor(cursorOpt);
					break;
				} else {
					if (node.getParentModelNode() == limit) {
						if (renderable instanceof RWord || renderable instanceof RBlank) {
							rcontext.setCursor(Optional.of(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)));
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
	 * @param x a int.
	 * @param y a int.
	 */
	public boolean onMouseUp(ModelNode node, MouseEvent event, int x, int y) {
		boolean pass = true;
				
		if (node instanceof HTMLElementImpl) {
			final HTMLElementImpl uiElement = (HTMLElementImpl) node;
			final Function f = uiElement.getOnmouseup();
			if (f != null) {
				final MouseEventImpl evt = new MouseEventImpl();
				evt.initMouseEvent("mouseup", false, false, null, 0, 0, 0, x, y, true, true, true, true, (short) 0, null);
				evt.setIe(event);
				pass = Executor.executeFunction(uiElement, f, evt, new Object[0]);
			}
		}
		
		if (node instanceof HTMLLinkElementImpl) {
			((HTMLLinkElementImpl) node).getCurrentStyle().setOverlayColor(null);
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
	public boolean onPressed(ModelNode node, InputEvent event, int x, int y) {
		
		if (node instanceof HTMLElementImpl) {
			final HTMLElementImpl uiElement = (HTMLElementImpl) node;
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
		
		if (node instanceof HTMLInputElementImpl) {
			final HTMLInputElementImpl hie = (HTMLInputElementImpl) node;
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
