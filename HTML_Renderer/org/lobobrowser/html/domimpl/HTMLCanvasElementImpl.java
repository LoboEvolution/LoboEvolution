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

package org.lobobrowser.html.domimpl;

import org.lobobrowser.html.style.BlockRenderState;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.w3c.HTMLCanvasElement;

public class HTMLCanvasElementImpl extends HTMLAbstractUIElement implements HTMLCanvasElement {

	public HTMLCanvasElementImpl(String name) {
		super(name);
	}
	
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new BlockRenderState(prevRenderState, this);
	}

	@Override
	public int getWidth() {
		String widthText = this.getAttribute("width");
		if (widthText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(widthText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	@Override
	public void setWidth(int width) {
		this.setAttribute("width", String.valueOf(width));
	}

	@Override
	public int getHeight() {
		String heightText = this.getAttribute("height");
		if (heightText == null) {
			return 0;
		}
		try {
			return Integer.parseInt(heightText);
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	@Override
	public void setHeight(int height) {
		this.setAttribute("height", String.valueOf(height));
	}

	@Override
	public String toDataURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toDataURL(String type, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getContext(String contextId) {	
		return null;
	}
}
