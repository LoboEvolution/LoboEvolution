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
/*
 * Created on Jan 14, 2006
 */
package org.loboevolution.html.node;

import org.loboevolution.js.IgnoreJs;

import java.awt.*;

/**
 * A UI node abstraction that is used to send notifications back to the UI and
 * to obtain information the DOM needs from the UI (such as image dimensions).
 */
public interface UINode {
	
	/**
	 * <p>getBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	@IgnoreJs
	Rectangle getBounds();

	/**
	 * Called
	 *
	 * @param modelNode a {@link ModelNode} object.
	 */

	@IgnoreJs
	void repaint(ModelNode modelNode);
}
