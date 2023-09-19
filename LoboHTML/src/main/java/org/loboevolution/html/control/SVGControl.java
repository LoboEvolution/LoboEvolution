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

import org.loboevolution.html.dom.svgimpl.SVGSVGElementImpl;

import java.awt.*;
import java.util.logging.Logger;

/**
 * <p>SVGControl class.</p>
 *
 *
 *
 */
public class SVGControl extends BaseControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Constant logger */
	protected static final Logger logger = Logger.getLogger(SVGControl.class.getName());

	private final SVGSVGElementImpl modelNode;

	/**
	 * <p>Constructor for SVGControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.svgimpl.SVGSVGElementImpl} object.
	 */
	public SVGControl(SVGSVGElementImpl modelNode) {
		super(modelNode);
		this.modelNode = modelNode;
		modelNode.setPainted(false);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		/*Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		modelNode.draw(g2d);
		modelNode.setPainted(true);*/
	}
}
