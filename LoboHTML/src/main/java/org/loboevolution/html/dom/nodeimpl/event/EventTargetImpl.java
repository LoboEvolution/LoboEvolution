/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

package org.loboevolution.html.dom.nodeimpl.event;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.nodeimpl.ElementImpl;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.events.Event;
import org.loboevolution.html.node.events.EventTarget;
import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventException;

import java.util.*;

/**
 * <p>EventTargetImpl class.</p>
 */
public class EventTargetImpl extends NodeImpl implements EventTarget {

	private final Map<NodeImpl, Map<String, List<Function>>> onEventHandlers = new HashMap<>();
	
	private final List<Node> clicked = new ArrayList<>();

	/** {@inheritDoc} */
	@Override
	public void addEventListener(final String type, final Function listener) {
	    addEventListener(type, listener, false);
	}

	/** {@inheritDoc} */
	@Override
	public void addEventListener(String type, Function listener, boolean useCapture) {
		if ("load".equals(type) || "DOMContentLoaded".equals(type)) {
			onloadEvent(listener);
		} else {
			List<Function> handlerList = new ArrayList<>();
			handlerList.add(listener);
			final Map<String, List<Function>> onEventListeners = new HashMap<>();
			onEventListeners.put(type, handlerList);
			this.onEventHandlers.put(this, onEventListeners);
		}
	}
	
	private void onloadEvent(Function onloadHandler) {
		Executor.executeFunction(this, onloadHandler, null, new Object[0]);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(String script, Function function) {
		removeEventListener(script, function, true);
	}

	/** {@inheritDoc} */
	@Override
	public void removeEventListener(String type, Function listener, boolean useCapture) {
		Set<NodeImpl> keySet = onEventHandlers.keySet();
		for (NodeImpl htmlElementImpl : keySet) {
			Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
			if (map != null) {
				List<Function> list = map.get(type);
				if (list != null) {
					list.remove(listener);
				}
				if (htmlElementImpl instanceof Element) {
					((ElementImpl) htmlElementImpl).removeAttributeField(type);
				}
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean dispatchEvent(Node htmlElementImpl, Event evt) {
		Function f = getFunction(htmlElementImpl, evt);
		if (f != null) {
			if (!clicked.contains(htmlElementImpl)) {
				Executor.executeFunction(this, f, evt, new Object[0]);
				clicked.add(htmlElementImpl);
			} else {
				clicked.clear();
			}
		}
		return false;
	}

	public Function getFunction(Node htmlElementImpl, Event evt) {
		Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
		if (map != null) {
			String evType = evt.getType();
			List<Function> handlers = map.get(evType.startsWith("on") ? evType.substring(2) : evType);
			if (handlers != null && handlers.size() > 0) {
				Optional<Function> optional = handlers.stream().findFirst();
				return optional.get();
			}
		}
		return null;
	}

	@Override
	public boolean dispatchEvent(Event evt) throws EventException {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "[object HTMLDocument]";
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.DOCUMENT_NODE;
	}

	@Override
	public boolean equalAttributes(Node arg) {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String getLocalName() {
		return getNodeName();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeValue() {
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public void setNodeValue(String nodeValue) throws DOMException {
		throw new DOMException(DOMException.INVALID_MODIFICATION_ERR, "Cannot set node value of document");
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	public Map<NodeImpl, Map<String, List<Function>>> getOnEventHandlers() {
		return onEventHandlers;
	}
}
