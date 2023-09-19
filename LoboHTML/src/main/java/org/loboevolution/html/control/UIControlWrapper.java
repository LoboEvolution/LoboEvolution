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

package org.loboevolution.html.control;

import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.renderer.BrokenComponent;
import org.loboevolution.html.renderer.RenderableSpot;

import java.awt.*;

/**
 * <p>UIControlWrapper class.</p>
 *
 *
 *
 */
public class UIControlWrapper implements UIControl {
	private final Component component;
	private final HtmlObject htmlObject;

	/**
	 * <p>Constructor for UIControlWrapper.</p>
	 *
	 * @param ho a {@link org.loboevolution.html.HtmlObject} object.
	 */
	public UIControlWrapper(HtmlObject ho) {
		this.htmlObject = ho;
		Component c;
		if (ho == null) {
			c = new BrokenComponent();
		} else {
			c = ho.getComponent();
		}
		this.component = c;
	}

	/** {@inheritDoc} */
	@Override
	public Color getBackgroundColor() {
		return this.component.getBackground();
	}

	/** {@inheritDoc} */
	@Override
	public Component getComponent() {
		return this.component;
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize() {
		return this.component.getPreferredSize();
	}

	/** {@inheritDoc} */
	@Override
	public void invalidate() {
		// Calls its AWT parent's invalidate, but I guess that's OK.
		this.component.invalidate();
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		this.component.paint(g);
	}

	/**
	 * <p>paintSelection.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param inSelection a boolean.
	 * @param startPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @param endPoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 * @return a boolean.
	 */
	public boolean paintSelection(Graphics g, boolean inSelection, RenderableSpot startPoint, RenderableSpot endPoint) {
		// Does not paint selection
		return inSelection;
	}

	/** {@inheritDoc} */
	@Override
	public void reset(final int availWidth, final int availHeight) {
		this.htmlObject.reset(availWidth, availHeight);
	}

	/** {@inheritDoc} */
	@Override
	public void setBounds(int x, int y, int width, int height) {
		this.component.setBounds(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public void setRUIControl(RUIControl ruicontrol) {
		// Not doing anything with this.
	}
}
