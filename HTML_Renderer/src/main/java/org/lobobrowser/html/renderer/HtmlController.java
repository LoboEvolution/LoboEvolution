/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderer;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.lobobrowser.html.FormInput;
import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.HtmlRendererContext;
import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.domimpl.HTMLAbstractUIElement;
import org.lobobrowser.html.domimpl.HTMLAnchorElementImpl;
import org.lobobrowser.html.domimpl.HTMLButtonElementImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.domimpl.HTMLInputElementImpl;
import org.lobobrowser.html.domimpl.HTMLSelectElementImpl;
import org.lobobrowser.html.js.Executor;
import org.lobobrowser.html.jsimpl.KeyboardEventImpl;
import org.lobobrowser.html.jsimpl.MouseEventImpl;
import org.mozilla.javascript.Function;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * The Class HtmlController.
 */
public class HtmlController {

	/** The Constant logger. */
	private static final Logger logger = LogManager.getLogger(HtmlController.class.getName());

	/** The Constant instance. */
	private static final HtmlController instance = new HtmlController();

	/**
	 * Gets the Constant instance.
	 *
	 * @return the Constant instance
	 */
	public static HtmlController getInstance() {
		return instance;
	}

	/**
	 * On enter pressed.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @return True to propagate further and false if the event was consumed.
	 */
	public boolean onEnterPressed(ModelNode node, InputEvent event) {
		if (node instanceof HTMLInputElementImpl) {
			HTMLInputElementImpl hie = (HTMLInputElementImpl) node;
			if (hie.isSubmittableWithEnterKey()) {
				hie.submitForm(null);
				return false;
			}
		}
		// No propagation
		return false;
	}

	/**
	 * On mouse click.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return True to propagate further and false if the event was consumed.
	 */
	public boolean onMouseClick(ModelNode node, MouseEvent event, RBlockViewport bodyLayout, int x, int y) {
		if (logger.isEnabled(Level.INFO)) {
			logger.info("onMouseClick(): node=" + node + ",class=" + node.getClass().getName());
		}

		if (node instanceof HTMLDocumentImpl) {
			HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
			Function f = uiDoc.getOnclick();
			if (f != null && bodyLayout == null) {
				if (!Executor.executeFunction(uiDoc, f, null)) {
					return false;
				}
			} else if (f == null && bodyLayout != null) {
				if (!bodyLayout.onMouseClick(event, x - bodyLayout.x, y - bodyLayout.y)) {
					return false;
				}
			} else if (f != null && bodyLayout != null) {
				boolean result = Executor.executeFunction(uiDoc, f, null);
				result = bodyLayout.onMouseClick(event, x - bodyLayout.x, y - bodyLayout.y);

				if (!result) {
					return result;
				}
			}

		} else if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).navigate();
			return false;
		} else if (node instanceof HTMLButtonElementImpl) {
			HTMLButtonElementImpl button = (HTMLButtonElementImpl) node;
			String rawType = button.getAttribute(HtmlAttributeProperties.TYPE);
			String type;
			if (rawType == null) {
				type = "submit";
			} else {
				type = rawType.trim().toLowerCase();
			}
			if ("submit".equals(type)) {
				FormInput[] formInputs;
				String name = button.getName();
				if (name == null) {
					formInputs = null;
				} else {
					formInputs = new FormInput[] { new FormInput(name, button.getValue()) };
				}
				button.submitForm(formInputs);
			} else if ("reset".equals(type)) {
				button.resetForm();
			} else {
				// NOP for "button"!
			}
			return false;
		} else if (node instanceof HTMLAbstractUIElement) {
			HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
			Function f = uiElement.getOnclick();

			if (f != null) {
				MouseEventImpl jsEvent = new MouseEventImpl("click", uiElement, event, x, y);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
			HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
			if (rcontext != null) {
				if (!rcontext.onMouseClick(uiElement, event)) {
					return false;
				}
			}
		}

		ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return this.onMouseClick(parent, event, null, x, y);
	}

	/**
	 * On context menu.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onContextMenu(ModelNode node, MouseEvent event, int x, int y) {
		if (logger.isEnabled(Level.INFO)) {
			logger.info("onContextMenu(): node=" + node + ",class=" + node.getClass().getName());
		}
		if (node instanceof HTMLAbstractUIElement) {
			HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
			Function f = uiElement.getOncontextmenu();
			if (f != null) {
				MouseEventImpl jsEvent = new MouseEventImpl("contextmenu", uiElement, event, x, y);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
			HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
			if (rcontext != null) {
				// Needs to be done after Javascript, so the script
				// is able to prevent it.
				if (!rcontext.onContextMenu(uiElement, event)) {
					return false;
				}
			}
		}
		ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return this.onContextMenu(parent, event, x, y);
	}

	/**
	 * On mouse over.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param limit
	 *            the limit
	 */
	public void onMouseOver(ModelNode node, MouseEvent event, int x, int y, ModelNode limit) {
		while (node != null) {
			if (node == limit) {
				break;
			}

			if (node instanceof HTMLDocumentImpl) {
				HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
				Function f = uiDoc.getOnmouseover();
				Executor.executeFunction(uiDoc, f, null);
			} else if (node instanceof HTMLAbstractUIElement) {
				HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
				uiElement.setMouseOver(true);
				Function f = uiElement.getOnmouseover();
				if (f != null) {
					MouseEventImpl jsEvent = new MouseEventImpl("mouseover", uiElement, event, x, y);
					Executor.executeFunction(uiElement, f, jsEvent);
				}
				HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
				if (rcontext != null) {
					rcontext.onMouseOver(uiElement, event);
				}
			}
			node = node.getParentModelNode();
		}
	}

	/**
	 * On mouse out.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param limit
	 *            the limit
	 */
	public void onMouseOut(ModelNode node, MouseEvent event, int x, int y, ModelNode limit) {
		while (node != null) {
			if (node == limit) {
				break;
			}
			if (node instanceof HTMLAbstractUIElement) {
				HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
				uiElement.setMouseOver(false);
				Function f = uiElement.getOnmouseout();
				if (f != null) {
					MouseEventImpl jsEvent = new MouseEventImpl("mouseout", uiElement, event, x, y);
					Executor.executeFunction(uiElement, f, jsEvent);
				}
				HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
				if (rcontext != null) {
					rcontext.onMouseOut(uiElement, event);
				}
			}
			node = node.getParentModelNode();
		}
	}

	/**
	 * On double click.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return True to propagate further, false if consumed.
	 */
	public boolean onDoubleClick(ModelNode node, MouseEvent event, RBlockViewport bodyLayout, int x, int y) {
		if (logger.isEnabled(Level.INFO)) {
			logger.info("onDoubleClick(): node=" + node + ",class=" + node.getClass().getName());
		}

		if (node instanceof HTMLDocumentImpl) {
			HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
			Function f = uiDoc.getOndblclick();
			if (f != null && bodyLayout == null) {
				if (!Executor.executeFunction(uiDoc, f, null)) {
					return false;
				}
			} else if (f == null && bodyLayout != null) {
				if (!bodyLayout.onDoubleClick(event, x - bodyLayout.x, y - bodyLayout.y)) {
					return false;
				}
			} else if (f != null && bodyLayout != null) {
				boolean result = Executor.executeFunction(uiDoc, f, null);
				result = bodyLayout.onDoubleClick(event, x - bodyLayout.x, y - bodyLayout.y);

				if (!result) {
					return result;
				}
			}

		} else if (node instanceof HTMLAbstractUIElement) {
			HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
			Function f = uiElement.getOndblclick();
			if (f != null) {
				MouseEventImpl jsEvent = new MouseEventImpl("dblclick", uiElement, event, x, y);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
			HtmlRendererContext rcontext = uiElement.getHtmlRendererContext();
			if (rcontext != null) {
				if (!rcontext.onDoubleClick(uiElement, event)) {
					return false;
				}
			}
		}
		ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return this.onDoubleClick(parent, event, null, x, y);
	}

	/**
	 * On mouse disarmed.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @return True to propagate further, false if consumed.
	 */
	public boolean onMouseDisarmed(ModelNode node, MouseEvent event) {
		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor(null);
			return false;
		}
		ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return this.onMouseDisarmed(parent, event);
	}

	/**
	 * On mouse down.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return True to propagate further, false if consumed.
	 */
	public boolean onMouseDown(ModelNode node, MouseEvent event, RBlock block, int x, int y) {
		boolean pass = true;

		if (node instanceof HTMLDocumentImpl) {
			if (block != null) {
				RBlockViewport bodyLayout = block.bodyLayout;
				int newX = 0;
				int newY = 0;
				HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
				Function f = uiDoc.getOnmousedown();
				if (f != null && bodyLayout == null) {
					block.setArmedRenderable(null);
					if (!Executor.executeFunction(uiDoc, f, null)) {
						return false;
					}
				} else if (f == null && bodyLayout != null) {

					newX = x - bodyLayout.x;
					newY = y - bodyLayout.y;

					if (bodyLayout.contains(newX, newY)) {
						block.setArmedRenderable(bodyLayout);
						if (!bodyLayout.onMousePressed(event, newX, newY)) {
							return false;
						}
					} else {
						block.setArmedRenderable(null);
					}

				} else if (f != null && bodyLayout != null) {
					boolean result = Executor.executeFunction(uiDoc, f, null);
					newX = x - bodyLayout.x;
					newY = y - bodyLayout.y;

					if (bodyLayout.contains(newX, newY)) {
						block.setArmedRenderable(bodyLayout);
						result = bodyLayout.onMousePressed(event, newX, newY);
					} else {
						block.setArmedRenderable(null);
					}
					if (!result) {
						return result;
					}
				}
			}
		} else if (node instanceof HTMLAbstractUIElement) {
			HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
			Function f = uiElement.getOnmousedown();
			if (f != null) {
				MouseEventImpl jsEvent = new MouseEventImpl("mousedown", uiElement, event, x, y);
				pass = Executor.executeFunction(uiElement, f, jsEvent);
			}
		}
		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor("#9090FF80");
			return false;
		}
		if (!pass) {
			return false;
		}
		ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return this.onMouseDown(parent, event, null, x, y);
	}

	/**
	 * On mouse up.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return True to propagate further, false if consumed.
	 */
	public boolean onMouseUp(ModelNode node, MouseEvent event, RBlock block, int x, int y) {
		boolean pass = true;

		if (node instanceof HTMLDocumentImpl) {
			if (block != null) {
				RBlockViewport bodyLayout = block.bodyLayout;
				int newX = 0;
				int newY = 0;
				HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
				Function f = uiDoc.getOnmouseup();
				if (f != null && bodyLayout == null) {
					block.setArmedRenderable(null);
					if (!Executor.executeFunction(uiDoc, f, null)) {
						return false;
					}
				} else if (f == null && bodyLayout != null) {

					newX = x - bodyLayout.x;
					newY = y - bodyLayout.y;

					if (bodyLayout.contains(newX, newY)) {
						block.setArmedRenderable(bodyLayout);
						if (!bodyLayout.onMouseReleased(event, newX, newY)) {
							return false;
						}
					} else {
						block.setArmedRenderable(null);
					}

				} else if (f != null && bodyLayout != null) {
					boolean result = Executor.executeFunction(uiDoc, f, null);
					newX = x - bodyLayout.x;
					newY = y - bodyLayout.y;

					if (bodyLayout.contains(newX, newY)) {
						block.setArmedRenderable(bodyLayout);
						result = bodyLayout.onMouseReleased(event, newX, newY);
					} else {
						block.setArmedRenderable(null);
					}
					if (!result) {
						return result;
					}
				}
			}
		} else if (node instanceof HTMLAbstractUIElement) {
			HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
			Function f = uiElement.getOnmouseup();
			if (f != null) {
				MouseEventImpl jsEvent = new MouseEventImpl("mouseup", uiElement, event, x, y);
				pass = Executor.executeFunction(uiElement, f, jsEvent);
			}
		}
		if (node instanceof HTMLAnchorElementImpl) {
			((HTMLAnchorElementImpl) node).getCurrentStyle().setOverlayColor(null);
			return false;
		}
		if (!pass) {
			return false;
		}
		ModelNode parent = node.getParentModelNode();
		if (parent == null) {
			return true;
		}
		return this.onMouseUp(parent, event, null, x, y);
	}

	/**
	 * On pressed.
	 *
	 * @param node
	 *            The node generating the event.
	 * @param event
	 *            the event
	 * @param x
	 *            For images only, x coordinate of mouse click.
	 * @param y
	 *            For images only, y coordinate of mouse click.
	 * @return True to propagate further, false if consumed.
	 */
	public boolean onPressed(ModelNode node, InputEvent event, int x, int y) {
		if (node instanceof HTMLAbstractUIElement) {
			HTMLAbstractUIElement uiElement = (HTMLAbstractUIElement) node;
			Function f = uiElement.getOnclick();
			if (f != null) {
				MouseEventImpl jsEvent = new MouseEventImpl("click", uiElement, event, x, y);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
		}
		if (node instanceof HTMLInputElementImpl) {
			HTMLInputElementImpl hie = (HTMLInputElementImpl) node;
			HTMLDocumentImpl doc = (HTMLDocumentImpl) hie.getOwnerDocument();
			NodeList list = doc.getElementsByTagName(HtmlProperties.INPUT);

			if (hie.isSubmitInput()) {
				FormInput[] formInputs = new FormInput[list.getLength()];

				for (int i = 0; i < list.getLength(); i++) {
					Node n = (Node) list.item(i);

					if (n instanceof HTMLInputElementImpl) {
						HTMLInputElementImpl input = (HTMLInputElementImpl) n;
						String name = input.getName();
						if (name == null) {
							formInputs[i] = new FormInput("", "");
						} else {
							String value = "";
							if (input != null) {
								value = input.getValue();
							}
							formInputs[i] = new FormInput(name, value);
						}
					}
				}
				hie.submitForm(formInputs);
			} else if (hie.isImageInput()) {
				FormInput[] formInputs = new FormInput[list.getLength() + 2];
				String name = hie.getName();
				String prefix = name == null ? "" : name + ".";
				int count = 0;
				for (int i = 0; i < list.getLength(); i++) {
					Node n = (Node) list.item(i);
					if (n instanceof HTMLInputElementImpl) {
						HTMLInputElementImpl input = (HTMLInputElementImpl) n;
						String inputName = input.getName();
						if (inputName == null) {
							formInputs[i] = new FormInput("", "");
						} else {
							String value = "";
							if (input != null) {
								value = input.getValue();
							}
							formInputs[i] = new FormInput(inputName, value);
						}
					}
					count = i;
				}
				formInputs[count + 1] = new FormInput(prefix + "x", String.valueOf(x));
				formInputs[count + 2] = new FormInput(prefix + "y", String.valueOf(y));
				hie.submitForm(formInputs);
			} else if (hie.isResetInput()) {
				hie.resetForm();
			}
		}
		return false;
	}

	/**
	 * On change.
	 *
	 * @param node
	 *            the node
	 * @return true, if successful
	 */
	public boolean onChange(ModelNode node) {
		if (node instanceof HTMLSelectElementImpl) {
			HTMLSelectElementImpl uiElement = (HTMLSelectElementImpl) node;
			Function f = uiElement.getOnchange();
			if (f != null) {
				MouseEventImpl jsEvent = new MouseEventImpl("change", uiElement);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
		}
		// No propagate
		return false;
	}

	/**
	 * On key down.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean onKeyDown(ModelNode node, KeyEvent event) {

		if (node instanceof HTMLDocumentImpl) {

			HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
			Function f = uiDoc.getOnkeydown();
			if (!Executor.executeFunction(uiDoc, f, null)) {
				return false;
			}

		} else if (node instanceof HTMLInputElementImpl) {
			HTMLInputElementImpl uiElement = (HTMLInputElementImpl) node;
			Function f = uiElement.getOnkeydown();
			if (f != null) {
				KeyboardEventImpl jsEvent = new KeyboardEventImpl("keydown", uiElement, event);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
		}
		// No propagate
		return false;
	}

	/**
	 * On key press.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean onKeyPress(ModelNode node, KeyEvent event) {

		if (node instanceof HTMLDocumentImpl) {

			HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
			Function f = uiDoc.getOnkeypress();
			if (!Executor.executeFunction(uiDoc, f, null)) {
				return false;
			}

		} else if (node instanceof HTMLInputElementImpl) {
			HTMLInputElementImpl uiElement = (HTMLInputElementImpl) node;
			Function f = uiElement.getOnkeypress();
			if (f != null) {
				KeyboardEventImpl jsEvent = new KeyboardEventImpl("keypress", uiElement, event);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * On key up.
	 *
	 * @param node
	 *            the node
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean onKeyUp(ModelNode node, KeyEvent event) {

		if (node instanceof HTMLDocumentImpl) {

			HTMLDocumentImpl uiDoc = (HTMLDocumentImpl) node;
			Function f = uiDoc.getOnkeyup();
			if (!Executor.executeFunction(uiDoc, f, null)) {
				return false;
			}

		} else if (node instanceof HTMLInputElementImpl) {
			HTMLInputElementImpl uiElement = (HTMLInputElementImpl) node;
			Function f = uiElement.getOnkeyup();
			if (f != null) {
				KeyboardEventImpl jsEvent = new KeyboardEventImpl("keyup", uiElement, event);
				if (!Executor.executeFunction(uiElement, f, jsEvent)) {
					return false;
				}
			}
		}
		// No propagate
		return false;
	}
}
