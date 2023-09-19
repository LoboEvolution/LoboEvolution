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

package org.loboevolution.html.renderer;

import org.loboevolution.html.AlignValues;
import org.loboevolution.html.dom.domimpl.UINode;

/**
 * A renderer node for elements such as blocks, lists, tables, inputs, images,
 * etc.
 *
 *
 *
 */
public interface RElement extends RCollection, UINode {

	/**
	 * <p>getCollapsibleMarginBottom.</p>
	 *
	 * @return a int.
	 */
	int getCollapsibleMarginBottom();

	/**
	 * <p>getCollapsibleMarginTop.</p>
	 *
	 * @return a int.
	 */
	int getCollapsibleMarginTop();

	/**
	 * <p>getMarginBottom.</p>
	 *
	 * @return a int.
	 */
	int getMarginBottom();

	/**
	 * <p>getMarginLeft.</p>
	 *
	 * @return a int.
	 */
	int getMarginLeft();

	/**
	 * <p>getMarginRight.</p>
	 *
	 * @return a int.
	 */
	int getMarginRight();

	/**
	 * <p>getMarginTop.</p>
	 *
	 * @return a int.
	 */
	int getMarginTop();

	/**
	 * Vertical alignment for elements rendered in a line. Returns one of the
	 * constants defined in this class.
	 *
	 * @return a int.
	 */
	default int getVAlign() {
		return AlignValues.BASELINE.getValue();
	}

	/**
	 * Lays out the subtree below the RElement. The RElement is expected to set its
	 * own dimensions, but not its origin.
	 *
	 * @param availWidth  The available width from the parent's canvas.
	 * @param availHeight The available height from the parent's canvas.
	 * @param sizeOnly    Whether the layout is for sizing determination only.
	 */
	void layout(int availWidth, int availHeight, boolean sizeOnly);

	/**
	 * <p>setupRelativePosition.</p>
	 *
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 */
	void setupRelativePosition(final RenderableContainer container);
}
