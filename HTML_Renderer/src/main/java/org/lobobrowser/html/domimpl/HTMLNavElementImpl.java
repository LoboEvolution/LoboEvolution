/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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

import org.lobobrowser.html.renderstate.NavRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.w3c.html.HTMLNavElement;

/**
 * The Class HTMLNavElementImpl.
 */
public class HTMLNavElementImpl extends HTMLAbstractUIElement implements HTMLNavElement {

	/**
	 * Instantiates a new HTML nav element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLNavElementImpl(String name) {
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
		return new NavRenderState(prevRenderState, this);
	}

}
