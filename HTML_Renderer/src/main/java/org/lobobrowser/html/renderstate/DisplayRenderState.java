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
package org.lobobrowser.html.renderstate;

import org.lobobrowser.html.domimpl.HTMLElementImpl;

/**
 * The Class DisplayRenderState.
 */
public class DisplayRenderState extends StyleSheetRenderState {

	/** The default display. */
	private final int defaultDisplay;

	/**
	 * Instantiates a new display render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param element
	 *            the element
	 * @param defaultDisplay
	 *            the default display
	 */
	public DisplayRenderState(RenderState prevRenderState, HTMLElementImpl element, final int defaultDisplay) {
		super(prevRenderState, element);
		this.defaultDisplay = defaultDisplay;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.renderstate.StyleSheetRenderState#getDefaultDisplay(
	 * )
	 */
	@Override
	public int getDefaultDisplay() {
		return this.defaultDisplay;
	}
}
