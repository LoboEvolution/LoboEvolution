/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.node.events;

import org.loboevolution.html.node.Node;
import org.mozilla.javascript.Function;
import org.w3c.dom.events.EventException;

/**
 * <p>EventTarget interface.</p>
 */
public interface EventTarget {

	/**
	 * <p>addEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 */
	void addEventListener(String type, final Function listener);
	
	/**
	 * <p>addEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param useCapture a boolean.
	 */
	void addEventListener(String type, Function listener, boolean useCapture);
	
	/**
	 * <p>removeEventListener.</p>
	 *
	 * @param script a {@link java.lang.String} object.
	 * @param function a {@link org.mozilla.javascript.Function} object.
	 */
	void removeEventListener(String script, Function function);
	
	/**
	 * <p>removeEventListener.</p>
	 *
	 * @param type a {@link java.lang.String} object.
	 * @param listener a {@link org.mozilla.javascript.Function} object.
	 * @param useCapture a boolean.
	 */
	void removeEventListener(String type, Function listener, boolean useCapture);
	
	/**
	 * <p>dispatchEvent.</p>
	 *
	 * @param element a {@link org.loboevolution.html.node.Node} object.
	 * @param evt a {@link org.loboevolution.html.node.events.Event} object.
	 * @return a boolean.
	 */
	boolean dispatchEvent(Node element, Event evt);

	/**
	 * <p>dispatchEvent.</p>
	 *
	 * @param evt a {@link org.w3c.dom.events.Event} object.
	 * @return a boolean.
	 * @throws org.w3c.dom.events.EventException if any.
	 */
	boolean dispatchEvent(Event evt) throws EventException;

}
