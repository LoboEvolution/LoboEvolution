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

import java.awt.Color;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.w3c.svg.SVGAnimatedNumber;
import org.lobobrowser.w3c.svg.SVGStopElement;

public class SVGStopElementImpl extends SVGSVGElementImpl implements SVGStopElement {

	public SVGStopElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedNumber getOffset() {
		String value = this.getAttribute(HtmlAttributeProperties.OFFSET);
		return new SVGAnimatedNumberImpl(new SVGNumberImpl(value));
	}
	
	public Color getStopColor() {
		String stopcolor = this.getAttribute(HtmlAttributeProperties.STOP_COLOR);
		Color color = ColorFactory.getInstance().getColor(stopcolor);
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(255 * Integer.parseInt(getStopOpacity())));
	}
	
	public String getStopOpacity() {
		String opacity = this.getAttribute(HtmlAttributeProperties.STOP_OPACITY);
		if(opacity == null){
			return "1";
		}
		return opacity;
	}
}