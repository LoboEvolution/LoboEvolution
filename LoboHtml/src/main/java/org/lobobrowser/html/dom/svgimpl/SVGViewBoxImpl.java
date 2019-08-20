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

import org.lobobrowser.html.dom.svg.SVGSVGElement;

public class SVGViewBoxImpl {

	private float x;
	private float y;
	private float r;
	private float width;
	private float height;

	public SVGViewBoxImpl(SVGSVGElement svgElem, float x, float y, float width, float height, float r) {

		float svgWidth = svgElem.getWidth().getBaseVal().getValue();
		float svgHeigth = svgElem.getHeight().getBaseVal().getValue();
		float vbx = svgElem.getViewBox().getBaseVal().getX();
		float vby = svgElem.getViewBox().getBaseVal().getY();
		float vbw = svgElem.getViewBox().getBaseVal().getWidth();
		float vbh = svgElem.getViewBox().getBaseVal().getHeight();
		float xUnit = svgWidth / vbx;
		float yUnit = svgHeigth / vby;
		float widthUnit = svgWidth / vbw;
		float heigthUnit = svgHeigth / vbh;

		if (xUnit != svgWidth) {
			x = x * widthUnit;
		}

		if (yUnit != vbh) {
			y = y * heigthUnit;
		}

		if (widthUnit != svgWidth) {
			width = width * widthUnit;
		}

		if (heigthUnit != vbh) {
			height = height * heigthUnit;
			r = r * heigthUnit;
		}

		this.x = x;
		this.y = y;
		this.r = r;
		this.width = width;
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getR() {
		return r;
	}

	public void setR(float r) {
		this.r = r;
	}
}
