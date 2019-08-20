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

import org.lobobrowser.html.dom.svg.SVGNumber;
import org.w3c.dom.DOMException;

public class SVGNumberImpl implements SVGNumber {

	private float number = 0;

	public SVGNumberImpl(String value) {
		int index = value.indexOf('%');
		if (index != -1) {
			number = Float.parseFloat(value.substring(0, index)) / 100;
		} else {
			number = Float.parseFloat(value);
		}

		if (number < 0) {
			number = 0;
		} else if (number > 1) {
			number = 1;
		}
	}

	@Override
	public float getValue() {
		return number;
	}

	@Override
	public void setValue(float value) throws DOMException {
		number = value;
	}
}
