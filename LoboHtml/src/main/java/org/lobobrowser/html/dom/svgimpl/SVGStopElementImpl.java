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
package org.lobobrowser.html.dom.svgimpl;

import java.awt.Color;

import org.lobo.laf.ColorFactory;
import org.lobobrowser.html.dom.svg.SVGAnimatedNumber;
import org.lobobrowser.html.dom.svg.SVGStopElement;
import org.lobobrowser.html.style.AbstractCSSProperties;


public class SVGStopElementImpl extends SVGSVGElementImpl implements SVGStopElement {

	public SVGStopElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedNumber getOffset() {
		String value = this.getAttribute("offset");
		return new SVGAnimatedNumberImpl(new SVGNumberImpl(value));
	}

	public Color getStopColor() {
		String stopcolor = this.getAttribute(STOP_COLOR);
		Color color = Color.BLACK;
		if (stopcolor != null) {
			color = ColorFactory.getInstance().getColor(stopcolor);
		} else {
			AbstractCSSProperties style = this.getStyle();
			color = ColorFactory.getInstance().getColor(style.getStopColor());
		}
		return new Color(color.getRed(), color.getGreen(), color.getBlue(),
				Math.round(255 * Float.parseFloat(getStopOpacity())));
	}

	public String getStopOpacity() {
		String opacity = this.getAttribute(STOP_OPACITY);
		if (opacity == null) {
			AbstractCSSProperties style = this.getStyle();
			opacity = style.getStopOpacity();
			if (opacity == null) {
				opacity = "1";
			}
		}
		return opacity;
	}
}