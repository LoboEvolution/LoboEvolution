/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.renderstate.FontStyleRenderState;
import org.lobobrowser.html.renderstate.RenderState;

/**
 * Element used for SUB.
 */

public class HTMLSuperscriptElementImpl extends HTMLAbstractUIElement {

	/** The superscript. */
	private int superscript;

	/**
	 * Instantiates a new HTML superscript element impl.
	 *
	 * @param name
	 *            the name
	 * @param superscript
	 *            the superscript
	 */
	public HTMLSuperscriptElementImpl(String name, int superscript) {
		super(name);
		this.superscript = superscript;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		prevRenderState = FontStyleRenderState.createSuperscriptFontStyleRenderState(prevRenderState,
				Integer.valueOf(this.superscript));
		return super.createRenderState(prevRenderState);
	}
}
