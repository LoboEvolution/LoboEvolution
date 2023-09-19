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

package org.loboevolution.html;

import java.awt.*;

/**
 * This interface should be implemented to provide OBJECT, EMBED or APPLET
 * functionality.
 *
 *
 *
 */
public interface HtmlObject {
	/**
	 * <p>destroy.</p>
	 */
	void destroy();

	/**
	 * <p>getComponent.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	Component getComponent();

	/**
	 * Called as the object is layed out, either the first time it's layed out or
	 * whenever the DOM changes. This is where the object should reset its state
	 * based on element children or attributes and possibly change its preferred
	 * size if appropriate.
	 *
	 * @param availableWidth a int.
	 * @param availableHeight a int.
	 */
	void reset(int availableWidth, int availableHeight);

	/**
	 * <p>resume.</p>
	 */
	void resume();

	/**
	 * <p>suspend.</p>
	 */
	void suspend();
}
