/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.style;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;

/**
 * <p>HtmlInsets class.</p>
 */
public class HtmlInsets {
	
	/** Constant TYPE_UNDEFINED=0 */
	public static final int TYPE_UNDEFINED = 0;
	
	/** Constant TYPE_PIXELS=1 */
	public static final int TYPE_PIXELS = 1;
	
	/** Constant TYPE_AUTO=2 */
	public static final int TYPE_AUTO = 2;

	/** Constant TYPE_PERCENT=3 */
	public static final int TYPE_PERCENT = 3;
	
	public int top, bottom, left, right;

	/* Types assumed to be initialized as UNDEFINED */
	public int topType, bottomType, leftType, rightType;

	/**
	 * <p>Constructor for HtmlInsets.</p>
	 */
	public HtmlInsets() {
	}

	/**
	 * <p>getAWTInsets.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param autoX a int.
	 * @param autoY a int.
	 * @return a {@link java.awt.Insets} object.
	 */
	public Insets getAWTInsets(int availWidth, int availHeight, int autoX, int autoY) {
		final int top = getInsetPixels(this.top, this.topType, availHeight, autoY);
		final int left = getInsetPixels(this.left, this.leftType, availWidth, autoX);
		final int bottom = getInsetPixels(this.bottom, this.bottomType, availHeight, autoY);
		final int right = getInsetPixels(this.right, this.rightType, availWidth, autoX);
		return new Insets(top, left, bottom, right);
	}

	protected static HtmlInsets getInsets(String topText, String leftText, String bottomText, String rightText, HTMLElementImpl element, RenderState renderState) {
		BasicInset basicInset;
		HtmlInsets insets = new HtmlInsets();

		basicInset = updateInset(element, topText, renderState);
		insets.top = basicInset.getValue();
		insets.topType = basicInset.getType();

		basicInset = updateInset(element, leftText, renderState);
		insets.left = basicInset.getValue();
		insets.leftType = basicInset.getType();

		basicInset = updateInset(element, bottomText, renderState);
		insets.bottom = basicInset.getValue();
		insets.bottomType = basicInset.getType();

		basicInset = updateInset(element, rightText, renderState);
		insets.right = basicInset.getValue();
		insets.rightType = basicInset.getType();

		return insets;
	}

	private static BasicInset updateInset(HTMLElementImpl element, String sizeText, RenderState renderState) {
		int type = 0;
		int value = 0;
		if (Strings.isNotBlank(sizeText)) {
			if ("auto".equalsIgnoreCase(sizeText)) {
				type = HtmlInsets.TYPE_AUTO;
			} else if (sizeText.endsWith("%")) {
				type = HtmlInsets.TYPE_PERCENT;
				try {
					value = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
				} catch (final Exception nfe) {
					value = 0;
				}
			} else {
				HTMLDocumentImpl doc = (HTMLDocumentImpl)element.getDocumentNode();
				type = HtmlInsets.TYPE_PIXELS;
				value = HtmlValues.getPixelSize(sizeText, renderState, doc.getWindow(), 0);
			}
		}
		return new BasicInset(type, value);
	}

	private int getInsetPixels(int value, int type, int availSize, int autoValue) {
		switch (type) {
			case TYPE_PIXELS:
				return value;
			case TYPE_UNDEFINED:
				return 0;
			case TYPE_AUTO:
				return autoValue;
			case TYPE_PERCENT:
				return availSize * value / 100;
			default:
				throw new IllegalStateException();
		}
	}

	/**
	 * <p>Getter for the field bottom.</p>
	 *
	 * @return a int.
	 */
	public int getBottom() {
		return this.bottom;
	}

	/**
	 * <p>Getter for the field bottomType.</p>
	 *
	 * @return a int.
	 */
	public int getBottomType() {
		return this.bottomType;
	}

	/**
	 * <p>Getter for the field left.</p>
	 *
	 * @return a int.
	 */
	public int getLeft() {
		return this.left;
	}

	/**
	 * <p>Getter for the field leftType.</p>
	 *
	 * @return a int.
	 */
	public int getLeftType() {
		return this.leftType;
	}

	/**
	 * <p>Getter for the field right.</p>
	 *
	 * @return a int.
	 */
	public int getRight() {
		return this.right;
	}

	/**
	 * <p>Getter for the field rightType.</p>
	 *
	 * @return a int.
	 */
	public int getRightType() {
		return this.rightType;
	}

	/**
	 * <p>Getter for the field top.</p>
	 *
	 * @return a int.
	 */
	public int getTop() {
		return this.top;
	}

	/**
	 * <p>Getter for the field topType.</p>
	 *
	 * @return a int.
	 */
	public int getTopType() {
		return this.topType;
	}

	/**
	 * <p>Setter for the field bottom.</p>
	 *
	 * @param bottom a int.
	 */
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	/**
	 * <p>Setter for the field bottomType.</p>
	 *
	 * @param bottomType a int.
	 */
	public void setBottomType(int bottomType) {
		this.bottomType = bottomType;
	}

	/**
	 * <p>Setter for the field left.</p>
	 *
	 * @param left a int.
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * <p>Setter for the field leftType.</p>
	 *
	 * @param leftType a int.
	 */
	public void setLeftType(int leftType) {
		this.leftType = leftType;
	}

	/**
	 * <p>Setter for the field right.</p>
	 *
	 * @param right a int.
	 */
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * <p>Setter for the field rightType.</p>
	 *
	 * @param rightType a int.
	 */
	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	/**
	 * <p>Setter for the field top.</p>
	 *
	 * @param top a int.
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * <p>Setter for the field topType.</p>
	 *
	 * @param topType a int.
	 */
	public void setTopType(int topType) {
		this.topType = topType;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[" + this.top + "," + this.left + "," + this.bottom + "," + this.right + "]";
	}
}
