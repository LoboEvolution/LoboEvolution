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

package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.renderstate.FontStyleRenderState;
import org.loboevolution.html.renderstate.RenderState;

/**
 * Element used for SUB
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLSuperscriptElementImpl extends HTMLAbstractUIElement {
	private final int superscript;

	/**
	 * <p>Constructor for HTMLSuperscriptElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @param superscript a int.
	 */
	public HTMLSuperscriptElementImpl(String name, int superscript) {
		super(name);
		this.superscript = superscript;
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		prevRenderState = FontStyleRenderState.createSuperscriptFontStyleRenderState(prevRenderState,
                this.superscript);
		return super.createRenderState(prevRenderState);
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLSuperscriptElement]";
	}
}
