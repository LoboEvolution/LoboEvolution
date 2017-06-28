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
/*
 * Created on Feb 12, 2006
 */
package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.renderstate.ListRenderState;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.w3c.html.HTMLUListElement;

/**
 * The Class HTMLUListElementImpl.
 */
public class HTMLUListElementImpl extends HTMLAbstractUIElement implements HTMLUListElement {

	/**
	 * Instantiates a new HTMLU list element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLUListElementImpl(String name) {
		super(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLUListElement#getCompact()
	 */
	@Override
	public boolean getCompact() {
		String compactText = this.getAttribute(HtmlAttributeProperties.COMPACT);
		return HtmlAttributeProperties.COMPACT.equalsIgnoreCase(compactText);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLUListElement#setCompact(boolean)
	 */
	@Override
	public void setCompact(boolean compact) {
		this.setAttribute(HtmlAttributeProperties.COMPACT, compact ? HtmlAttributeProperties.COMPACT : null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLUListElement#getType()
	 */
	@Override
	public String getType() {
		return this.getAttribute(HtmlAttributeProperties.TYPE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLUListElement#setType(java.lang.String)
	 */
	@Override
	public void setType(String type) {
		this.setAttribute(HtmlAttributeProperties.TYPE, type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.
	 * lobobrowser .html.renderstate.RenderState)
	 */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new ListRenderState(prevRenderState, this);
	}
}
