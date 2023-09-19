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

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;

import java.awt.*;
import java.util.Iterator;

/**
 * <p>RSSItemElementImpl class.</p>
 *
 *
 *
 */
public class RSSItemElementImpl extends HTMLElementImpl {
	
	/**
	 * <p>Constructor for RSSItemElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public RSSItemElementImpl(final String name) {
		super(name);
	}
	
	/**
	 * <p>draw.</p>
	 *
	 * @param graphics a {@link java.awt.Graphics2D} object.
	 * @param y a int.
	 */
	public void draw(Graphics2D graphics, int y) {
		if (hasChildNodes()) {
			NodeListImpl children = (NodeListImpl)getChildNodes();
			for (Iterator<Node> i= children.iterator(); i.hasNext();) {
				Node child = i.next();
				if (child instanceof RSSDrawable) {
					RSSDrawable channel = (RSSDrawable) child;
					channel.draw(graphics, y);
					y = y + 20;
				}
			}
		}
	}
}
