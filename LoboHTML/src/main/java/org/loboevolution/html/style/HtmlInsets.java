/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

import lombok.Data;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLDocumentImpl;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderstate.RenderState;

import java.awt.*;

/**
 * <p>HtmlInsets class.</p>
 */
@Data
public class HtmlInsets {
	
	/** Constant TYPE_UNDEFINED=0 */
	public static final int TYPE_UNDEFINED = 0;
	
	/** Constant TYPE_PIXELS=1 */
	public static final int TYPE_PIXELS = 1;
	
	/** Constant TYPE_AUTO=2 */
	public static final int TYPE_AUTO = 2;

	/** Constant TYPE_PERCENT=3 */
	public static final int TYPE_PERCENT = 3;
	
	private int top;

	private int bottom;

	private int left;

	private int right;

	private int topType;

	private int bottomType;

	private int leftType;

	private int rightType;

	/**
	 * <p>Constructor for HtmlInsets.</p>
	 */
	public HtmlInsets() {
	}

	/**
	 * <p>Constructor for HtmlInsets.</p>
	 */
	public HtmlInsets(int value, int valueType) {
		top = left = right = bottom = value;
		topType = leftType = rightType = bottomType = valueType;
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
		final int bottom = getInsetPixels(this.bottom, this.bottomType, availHeight, autoY);

		int autoLeft = autoX;
		int autoRight = autoX;

		if (leftType == TYPE_AUTO && rightType == TYPE_AUTO) {
			autoLeft = autoLeft / 2;
			autoRight = 0;
		}

		final int left = getInsetPixels(this.left, this.leftType, availWidth, autoLeft);
		final int right = getInsetPixels(this.right, this.rightType, availWidth, autoRight);
		return new Insets(top, left, bottom, right);
	}

	/**
	 * <p>htmlInsetsIsVoid.</p>
	 * @return a {@link java.lang.Boolean} object.
	 */
	public boolean htmlInsetsIsVoid() {
		return top == 0 && bottom == 0 && left == 0 && right == 0;
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
				value = HtmlValues.getPixelSize(sizeText, renderState, doc.getDefaultView(), 0);
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

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[" + this.top + "," + this.left + "," + this.bottom + "," + this.right + "]";
	}
}
