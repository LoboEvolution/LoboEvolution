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
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.dom.nodeimpl.ModelNode;

import java.awt.*;
import java.util.Iterator;

/**
 * Represents a renderer (view) node.
 */
public interface Renderable {
	/** Constant EMPTY_ARRAY */
	Renderable[] EMPTY_ARRAY = new Renderable[0];

	/**
	 * <p>getModelNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	ModelNode getModelNode();

	/**
	 * <p>paint.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	void paint(Graphics g);

	/**
	 * <p>isFixed.</p>
	 *
	 * @return a boolean.
	 */
	default boolean isFixed() {
		return false;
	}
	
	/**
	 * <p>findHtmlRenderable.</p>
	 *
	 * @param root a {@link org.loboevolution.html.renderer.RCollection} object.
	 * @return a {@link org.loboevolution.html.renderer.Renderable} object.
	 */
	default Renderable findHtmlRenderable(RCollection root) {
		final Iterator<? extends Renderable> rs = root.getRenderables();
		if (rs != null) {
			while (rs.hasNext()) {
				final Renderable r = rs.next();
				if (r.getModelNode() instanceof HTMLHtmlElement) {
					return r;
				}
			}
		}
		return null;
	}
}
