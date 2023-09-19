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

package org.loboevolution.gui;

import org.loboevolution.html.dom.nodeimpl.NodeImpl;

class DocumentNotification {
	/** Constant GENERIC=3 */
	public static final int GENERIC = 3;
	/** Constant LOOK=0 */
	public static final int LOOK = 0;
	/** Constant POSITION=1 */
	public static final int POSITION = 1;
	/** Constant SIZE=2 */
	public static final int SIZE = 2;

	public final NodeImpl node;
	public final int type;

	/**
	 * <p>Constructor for DocumentNotification.</p>
	 *
	 * @param type a int.
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	public DocumentNotification(int type, NodeImpl node) {
		this.type = type;
		this.node = node;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "DocumentNotification[type=" + this.type + ",node=" + this.node + "]";
	}
}
