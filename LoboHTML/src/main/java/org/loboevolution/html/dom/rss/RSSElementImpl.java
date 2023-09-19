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

package org.loboevolution.html.dom.rss;

import org.loboevolution.html.dom.nodeimpl.NodeListImpl;

import java.awt.*;

/**
 * <p>RSSElementImpl class.</p>
 *
 *
 *
 */
public class RSSElementImpl extends RSSElement {

	/**
	 * <p>Constructor for RSSElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSElementImpl(final String name) {
		super(name);
	}

	/**
	 * <p>draw.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 */
	public void draw(final Graphics2D graphics) {
		if (hasChildNodes()) {
			NodeListImpl children = (NodeListImpl)getChildNodes();
			children.forEach(child -> {
				if (child instanceof RSSChanelElementImpl) {
					RSSChanelElementImpl channel = (RSSChanelElementImpl) child;
					channel.draw(graphics);
				}
			});
		}
	}
}
