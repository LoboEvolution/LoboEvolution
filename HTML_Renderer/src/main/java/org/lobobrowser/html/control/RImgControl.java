/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import org.lobobrowser.html.dombl.ModelNode;
import org.lobobrowser.html.renderer.FrameContext;
import org.lobobrowser.html.renderer.RenderableContainer;
import org.lobobrowser.html.renderer.UIControl;
import org.lobobrowser.http.UserAgentContext;

/**
 * The Class RImgControl.
 */
public class RImgControl extends RUIControl {

	/**
	 * Instantiates a new r img control.
	 *
	 * @param me
	 *            the me
	 * @param widget
	 *            the widget
	 * @param container
	 *            the container
	 * @param frameContext
	 *            the frame context
	 * @param ucontext
	 *            the ucontext
	 */
	public RImgControl(ModelNode me, UIControl widget, RenderableContainer container, FrameContext frameContext,
			UserAgentContext ucontext) {
		super(me, widget, container, frameContext, ucontext);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.renderer.BaseElementRenderable#applyStyle(int,
	 * int)
	 */
	@Override
	protected void applyStyle(int availWidth, int availHeight) {
		super.applyStyle(availWidth, availHeight);
	}
}
