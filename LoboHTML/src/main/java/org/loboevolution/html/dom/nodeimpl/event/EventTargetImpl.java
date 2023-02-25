/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.nodeimpl.event;

import com.gargoylesoftware.css.dom.DOMException;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.js.Executor;
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
				map.get(type).remove(listener);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean dispatchEvent(Node htmlElementImpl, Event evt) {
		Map<String, List<Function>> map = this.onEventHandlers.get(htmlElementImpl);
		if (map != null) {
			List<Function> handlers = map.get(evt.getType());
			if (handlers != null) {
				for (final Function h : handlers) {
					if (!clicked.contains(htmlElementImpl)) {
						Executor.executeFunction(this, h, evt, new Object[0]);
						clicked.add(htmlElementImpl);
					} else {
						clicked.clear();
					}
				}
			}
		}
		return false;
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
}
