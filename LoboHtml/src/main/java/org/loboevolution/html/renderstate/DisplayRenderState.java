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
package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;

/**
 * <p>DisplayRenderState class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class DisplayRenderState extends StyleSheetRenderState {
	private final int defaultDisplay;

	/**
	 * <p>Constructor for DisplayRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
	 * @param defaultDisplay a int.
	 */
	public DisplayRenderState(RenderState prevRenderState, HTMLElementImpl element, final int defaultDisplay) {
		super(prevRenderState, element);
		this.defaultDisplay = defaultDisplay;
	}

	/** {@inheritDoc} */
	@Override
	public int getDefaultDisplay() {
		return this.defaultDisplay;
	}
}
