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
package org.loboevolution.html.svgimpl;

import java.awt.Color;

import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.style.AbstractCSS2Properties;
import org.loboevolution.util.gui.ColorFactory;
import org.loboevolution.w3c.svg.SVGAnimatedNumber;
import org.loboevolution.w3c.svg.SVGStopElement;

public class SVGStopElementImpl extends SVGSVGElementImpl implements SVGStopElement {

	public SVGStopElementImpl(String name) {
		super(name);
	}

	@Override
	public SVGAnimatedNumber getOffset() {
		String value = this.getAttribute(OFFSET);
		return new SVGAnimatedNumberImpl(new SVGNumberImpl(value));
	}

	public Color getStopColor() {
		String stopcolor = this.getAttribute(HtmlAttributeProperties.STOP_COLOR);
		Color color = Color.BLACK;
		if (stopcolor != null) {
			color = ColorFactory.getInstance().getColor(stopcolor);
		} else {
			AbstractCSS2Properties style = this.getStyle();
			color = ColorFactory.getInstance().getColor(style.getStopColor());
		}
		return new Color(color.getRed(), color.getGreen(), color.getBlue(),
				Math.round(255 * Float.parseFloat(getStopOpacity())));
	}

	public String getStopOpacity() {
		String opacity = this.getAttribute(HtmlAttributeProperties.STOP_OPACITY);
		if (opacity == null) {
			AbstractCSS2Properties style = this.getStyle();
			opacity = style.getStopOpacity();
			if (opacity == null) {
				opacity = "1";
			}
		}
		return opacity;
	}
}