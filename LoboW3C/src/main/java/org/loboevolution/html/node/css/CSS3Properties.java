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

package org.loboevolution.html.node.css;

/**
 * <p> CSS3Properties interface.</p>
 */
public interface CSS3Properties extends CSS2Properties {

	/**
	 * <p> getAlignItems.</p>
	 * @return a {@link java.lang.String} object.
	 */
	String getAlignItems();

	/**
	 * <p> getAlignContent.</p>
	 * @return a {@link java.lang.String} object.
	 */
	String getAlignContent();

	/**
	 * <p> getBoxSizing. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getBoxSizing();

	/**
	 * <p> getClipPath. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getClipPath();

	/**
	 * <p> getClipRule. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getClipRule();

	/**
	 * <p> getFill. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getFill();

	/**
	 * <p> getFloat. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getFloat();

	/**
	 * <p> getFlexDirection. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexDirection();

	/**
	 * <p> flexWrap. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexWrap();

	/**
	 * <p> getFlexFlow. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getFlexFlow();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getJustifyContent();

	/**
	 * <p> setClipPath. </p>
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipPath(String clip);

	/**
	 * <p> setClipRule. </p>
	 * @param clip a {@link java.lang.String} object.
	 */
	void setClipRule(String clip);

	/**
	 * <p> setFloat. </p>
	 * @param flt a {@link java.lang.String} object.
	 */
	void setFloat(String flt);

	void setFill(String value);

	void setFillOpacity(String value);

	void setOpacity(String value);

	void setTransform(String value);

	void setStrokeWidth(String value);

	void setStrokeOpacity(String value);

	void setStrokeMiterLimit(String value);

	void setStrokeLineJoin(String value);

	void setStrokeDashArray(String value);

	void setStroke(String value);

	void setStrokeLineCap(String value);

	String getStroke();

	String getStopColor();

	String getStopOpacity();

	String getStrokeOpacity();

	String getStrokeLineJoin();

	String getStrokeMiterLimit();

	String getStrokeDashArray();

	String getOpacity();

	String getFillOpacity();

	String getStrokeLineCap();

	String getStrokeWidth();

	String getTransform();
}
