/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Jan 28, 2006
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFrameElement;
import org.w3c.dom.Document;

public class HTMLFrameElementImpl extends HTMLElementImpl implements HTMLFrameElement {
	
	private boolean noResize;

	public HTMLFrameElementImpl(String name) {
		super(name);
	}
	
	@Override
	public Document getContentDocument() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFrameBorder() {
		return getAttribute("frameBorder");
	}

	@Override
	public String getLongDesc() {
		return getAttribute("longdesc");
	}

	@Override
	public String getMarginHeight() {
		return getAttribute("marginHeight");
	}

	@Override
	public String getMarginWidth() {
		return getAttribute("marginWidth");
	}

	@Override
	public String getName() {
		return getAttribute("name");
	}

	@Override
	public boolean getNoResize() {
		return this.noResize;
	}

	@Override
	public String getScrolling() {
		return getAttribute("scrolling");
	}

	@Override
	public String getSrc() {
		return getAttribute("src");
	}

	@Override
	public void setFrameBorder(String frameBorder) {
		setAttribute("frameBorder", frameBorder);
	}

	@Override
	public void setLongDesc(String longDesc) {
		setAttribute("longdesc", longDesc);
	}

	@Override
	public void setMarginHeight(String marginHeight) {
		setAttribute("marginHeight", marginHeight);
	}

	@Override
	public void setMarginWidth(String marginWidth) {
		setAttribute("marginWidth", marginWidth);
	}

	@Override
	public void setName(String name) {
		setAttribute("name", name);
	}

	@Override
	public void setNoResize(boolean noResize) {
		this.noResize = noResize;
	}

	@Override
	public void setScrolling(String scrolling) {
		setAttribute("scrolling", scrolling);
	}

	@Override
	public void setSrc(String src) {
		setAttribute("src", src);
	}
}