/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.domimpl;

import java.awt.Font;

import org.loboevolution.html.renderstate.FontStyleRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * Element used for B and STRONG.
 */
public class HTMLStrongElementImpl extends HTMLAbstractUIElement {

	/**
	 * Instantiates a new HTML strong element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLStrongElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * loboevolution .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		RenderState tmpRenderState = prevRenderState;
		tmpRenderState = new FontStyleRenderState(tmpRenderState, Font.BOLD);
		return super.createRenderState(tmpRenderState);
	}
}
