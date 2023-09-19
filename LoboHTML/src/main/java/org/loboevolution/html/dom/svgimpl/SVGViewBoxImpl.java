/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.svgimpl;


import org.loboevolution.html.dom.svg.SVGSVGElement;

/**
 * <p>SVGViewBoxImpl class.</p>
 *
 *
 *
 */
public class SVGViewBoxImpl {

	private float x;
	private float y;
	private float r;
	private float width;
	private float height;

	/**
	 * <p>Constructor for SVGViewBoxImpl.</p>
	 *
	 * @param svgElem a {@link org.loboevolution.html.dom.svg.SVGSVGElement} object.
	 * @param x a float.
	 * @param y a float.
	 * @param width a float.
	 * @param height a float.
	 * @param r a float.
	 */
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

	/**
	 * <p>Getter for the field x.</p>
	 *
	 * @return a float.
	 */
	public float getX() {
		return x;
	}

	/**
	 * <p>Setter for the field x.</p>
	 *
	 * @param x a float.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * <p>Getter for the field y.</p>
	 *
	 * @return a float.
	 */
	public float getY() {
		return y;
	}

	/**
	 * <p>Setter for the field y.</p>
	 *
	 * @param y a float.
	 */
	public void setY(final float y) {
		this.y = y;
	}

	/**
	 * <p>Getter for the field width.</p>
	 *
	 * @return a float.
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * <p>Setter for the field width.</p>
	 *
	 * @param width a float.
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * <p>Getter for the field height.</p>
	 *
	 * @return a float.
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * <p>Setter for the field height.</p>
	 *
	 * @param height a float.
	 */
	public void setHeight(float height) {
		this.height = height;
	}

	/**
	 * <p>Getter for the field r.</p>
	 *
	 * @return a float.
	 */
	public float getR() {
		return r;
	}

	/**
	 * <p>Setter for the field r.</p>
	 *
	 * @param r a float.
	 */
	public void setR(float r) {
		this.r = r;
	}
}
