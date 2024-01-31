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

package org.loboevolution.css;

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
	 * <p> getFillOpacity. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getFillOpacity();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getJustifyContent();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getOpacity();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStroke();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStopColor();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStopOpacity();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStrokeOpacity();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStrokeLineJoin();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStrokeMiterLimit();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStrokeDashArray();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStrokeLineCap();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getStrokeWidth();

	/**
	 * <p> getJustifyContent. </p>
	 * @return a {@link java.lang.String} object.
	 */
	String getTransform();

	/**
	 * <p> setBoxSizing. </p>
	 * @param boxSizing a {@link java.lang.String} object.
	 */
	void setBoxSizing(String boxSizing);

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

	/**
	 * <p> setFill. </p>
	 * @param fill a {@link java.lang.String} object.
	 */
	void setFill(String fill);

	/**
	 * <p> setFillOpacity. </p>
	 * @param fillOpacity a {@link java.lang.String} object.
	 */
	void setFillOpacity(String fillOpacity);

	/**
	 * <p> setOpacity. </p>
	 * @param opacity a {@link java.lang.String} object.
	 */
	void setOpacity(String opacity);

	/**
	 * <p> setTransform. </p>
	 * @param transform a {@link java.lang.String} object.
	 */
	void setTransform(String transform);

	/**
	 * <p> setStrokeWidth. </p>
	 * @param strokeWidth a {@link java.lang.String} object.
	 */
	void setStrokeWidth(String strokeWidth);

	/**
	 * <p> setStrokeOpacity. </p>
	 * @param strokeOpacity a {@link java.lang.String} object.
	 */
	void setStrokeOpacity(String strokeOpacity);

	/**
	 * <p> setStrokeMiterLimit. </p>
	 * @param strokeMiterLimit a {@link java.lang.String} object.
	 */
	void setStrokeMiterLimit(String strokeMiterLimit);

	/**
	 * <p> setStrokeLineJoin. </p>
	 * @param strokeLineJoin a {@link java.lang.String} object.
	 */
	void setStrokeLineJoin(String strokeLineJoin);

	/**
	 * <p> setStrokeDashArray. </p>
	 * @param strokeDashArray a {@link java.lang.String} object.
	 */
	void setStrokeDashArray(String strokeDashArray);

	/**
	 * <p> setStroke. </p>
	 * @param stroke a {@link java.lang.String} object.
	 */
	void setStroke(String stroke);

	/**
	 * <p> setStrokeLineCap. </p>
	 * @param strokeLineCap a {@link java.lang.String} object.
	 */
	void setStrokeLineCap(String strokeLineCap);
}