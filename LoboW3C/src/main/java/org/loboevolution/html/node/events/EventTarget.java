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
	 * @param evt a {@link org.loboevolution.html.node.events.Event} object.
	 * @return a boolean.
	 * @throws org.w3c.dom.events.EventException if any.
	 */
	boolean dispatchEvent(Event evt) throws EventException;

}
