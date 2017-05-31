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
package org.lobobrowser.html.svgimpl;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.jsimpl.EventException;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.w3c.events.Event;
import org.lobobrowser.w3c.svg.SVGAnimatedTransformList;
import org.lobobrowser.w3c.svg.SVGGElement;
import org.w3c.dom.DOMException;

public class SVGGElementImpl extends SVGSVGElementImpl implements SVGGElement {

	public SVGGElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedTransformList getTransform() {
		return new SVGAnimatedTransformListImpl(this.getAttribute(HtmlAttributeProperties.TRANSFORM));
	}

	@Override
	public boolean dispatchEvent(Event evt) throws EventException, DOMException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AbstractCSS2Properties getSVGStyle() {
		
		AbstractCSS2Properties style = this.getStyle();
		
		if(style.getStroke() == null){
			style.setStroke(this.getStroke());
		}
		
		if(style.getStrokeDashArray() == null){
			style.setStrokeDashArray(this.getStrokeDashArray());
		}
		
		if(style.getStrokeLineCap() == null){
			style.setStrokeLineCap(this.getStrokeLineCap());
		}
		
		if(style.getStrokeMiterLimit() == null){
			style.setStrokeMiterLimit(this.getStrokeMiterLimit());
		}
		
		if(style.getStrokeOpacity() == null){
			style.setStrokeOpacity(this.getStrokeOpacity());
		}
		
		if(style.getStrokeWidth() == null){
			style.setStrokeWidth(this.getStrokeWidth());
		}
		
		if(style.getFill() == null){
			style.setFill(this.getFill());
		}
		
		return style;
	}

}
