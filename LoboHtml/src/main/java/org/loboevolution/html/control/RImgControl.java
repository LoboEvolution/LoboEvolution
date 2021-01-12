/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.control;

import java.awt.Dimension;
import java.awt.Insets;

import org.loboevolution.html.dom.domimpl.ModelNode;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.http.UserAgentContext;

/**
 * <p>RImgControl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class RImgControl extends RUIControl {
	/**
	 * <p>Constructor for RImgControl.</p>
	 *
	 * @param me a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
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
				this.width = (prefSize.width * this.height) / prefSize.height;
			}
		} else if (!heightConstrained && widthConstrained) {
			final Dimension prefSize = widget.getPreferredSize();
			if (prefSize.width != 0) {
				this.height = (prefSize.height * this.width) / prefSize.width;
			}
		}
	}
}
