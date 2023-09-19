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

import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.http.UserAgentContext;

import java.awt.*;

/**
 * <p>RImgControl class.</p>
 *
 *
 *
 */
public class RImgControl extends RUIControl {
	/**
	 * <p>Constructor for RImgControl.</p>
	 *
	 * @param me a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param widget a {@link org.loboevolution.html.control.UIControl} object.
	 * @param container a {@link org.loboevolution.html.renderer.RenderableContainer} object.
	 * @param frameContext a {@link org.loboevolution.html.renderer.FrameContext} object.
	 * @param ucontext a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public RImgControl(ModelNode me, UIControl widget, RenderableContainer container, FrameContext frameContext,
			UserAgentContext ucontext) {
		super(me, widget, container, frameContext, ucontext);
	}

	/** {@inheritDoc} */
	@Override
	public Insets getBorderInsets() {
		return getInsets(false, false);
	}

	/** {@inheritDoc} */
	@Override
	public void doLayout(int availWidth, int availHeight, boolean sizeOnly) {
		super.doLayout(availWidth, availHeight, sizeOnly);
		final boolean widthConstrained =  (this.declaredWidth == -1);
		final boolean heightConstrained = (this.declaredHeight == -1);
		if (!widthConstrained && heightConstrained) {
			final Dimension prefSize = widget.getPreferredSize();
			if (prefSize.height != 0) {
				this.setWidth((prefSize.width * this.getHeight()) / prefSize.height);
			}
		} else if (!heightConstrained && widthConstrained) {
			final Dimension prefSize = widget.getPreferredSize();
			if (prefSize.width != 0) {
				this.setHeight((prefSize.height * this.getWidth()) / prefSize.width);
			}
		}
	}
}
