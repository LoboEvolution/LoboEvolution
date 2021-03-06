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

import java.awt.Color;

/**
 * The Class ColorRenderState.
 *
 * @author utente
 * @version $Id: $Id
 */
public class ColorRenderState extends RenderStateDelegator {

	/** The color. */
	private final Color color;

	/**
	 * Instantiates a new color render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @param color
	 *            the color
	 */
	public ColorRenderState(RenderState prevRenderState, Color color) {
		super(prevRenderState);
		this.color = color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.style.RenderStateDelegator#getColor()
	 */
	/** {@inheritDoc} */
	@Override
	public Color getColor() {
		return this.color;
	}
}
