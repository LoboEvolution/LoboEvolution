/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
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
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.renderstate.TextDecorationRenderState;

/**
 * Element used for U.
 */
public class HTMLUnderlineElementImpl extends HTMLAbstractUIElement {

	/**
	 * Instantiates a new HTML underline element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLUnderlineElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		prevRenderState = new TextDecorationRenderState(prevRenderState, RenderState.MASK_TEXTDECORATION_UNDERLINE);
		return super.createRenderState(prevRenderState);
	}
}
