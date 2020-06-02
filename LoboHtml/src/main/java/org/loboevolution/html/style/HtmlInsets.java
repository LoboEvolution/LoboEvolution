/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The XAMJ Project

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html.style;

import java.awt.Insets;

import org.loboevolution.html.renderstate.RenderState;

/**
 * <p>HtmlInsets class.</p>
 *
 * @author utente
 * @version $Id: $Id
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
	 * @param defaultTop a int.
	 * @param defaultLeft a int.
	 * @param defaultBottom a int.
	 * @param defaultRight a int.
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @param autoX a int.
	 * @param autoY a int.
	 * @return a {@link java.awt.Insets} object.
	 */
	public Insets getAWTInsets(int defaultTop, int defaultLeft, int defaultBottom, int defaultRight,
			int availWidth, int availHeight, int autoX, int autoY) {
		final int top = getInsetPixels(this.top, this.topType, defaultTop, availHeight, autoY);
		final int left = getInsetPixels(this.left, this.leftType, defaultLeft, availWidth, autoX);
		final int bottom = getInsetPixels(this.bottom, this.bottomType, defaultBottom, availHeight, autoY);
		final int right = getInsetPixels(this.right, this.rightType, defaultRight, availWidth, autoX);
		return new Insets(top, left, bottom, right);
	}
	
	/**
	 * <p>getSimpleAWTInsets.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 * @return a {@link java.awt.Insets} object.
	 */
	public Insets getSimpleAWTInsets(int availWidth, int availHeight) {
		final int top = getInsetPixels(this.top, this.topType, 0, availHeight, 0);
		final int left = getInsetPixels(this.left, this.leftType, 0, availWidth, 0);
		final int bottom = getInsetPixels(this.bottom, this.bottomType, 0, availHeight, 0);
		final int right = getInsetPixels(this.right, this.rightType, 0, availWidth, 0);
		return new Insets(top, left, bottom, right);
	}
	
	/**
	 * <p>updateBottomInset.</p>
	 *
	 * @param insets a {@link org.loboevolution.html.style.HtmlInsets} object.
	 * @param sizeText a {@link java.lang.String} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	protected static HtmlInsets updateBottomInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.bottomType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.bottomType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.bottom = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.bottom = 0;
			}
		} else {
			insets.bottomType = HtmlInsets.TYPE_PIXELS;
			insets.bottom = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	/**
	 * <p>updateLeftInset.</p>
	 *
	 * @param insets a {@link org.loboevolution.html.style.HtmlInsets} object.
	 * @param sizeText a {@link java.lang.String} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	protected static HtmlInsets updateLeftInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.leftType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.leftType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.left = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.left = 0;
			}
		} else {
			insets.leftType = HtmlInsets.TYPE_PIXELS;
			insets.left = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	/**
	 * <p>updateRightInset.</p>
	 *
	 * @param insets a {@link org.loboevolution.html.style.HtmlInsets} object.
	 * @param sizeText a {@link java.lang.String} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	protected static HtmlInsets updateRightInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.rightType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.rightType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.right = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.right = 0;
			}
		} else {
			insets.rightType = HtmlInsets.TYPE_PIXELS;
			insets.right = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	/**
	 * <p>updateTopInset.</p>
	 *
	 * @param insets a {@link org.loboevolution.html.style.HtmlInsets} object.
	 * @param sizeText a {@link java.lang.String} object.
	 * @param renderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.style.HtmlInsets} object.
	 */
	protected static HtmlInsets updateTopInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			return insets;
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if ("auto".equalsIgnoreCase(sizeText)) {
			insets.topType = HtmlInsets.TYPE_AUTO;
		} else if (sizeText.endsWith("%")) {
			insets.topType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.top = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (final Exception nfe) {
				insets.top = 0;
			}
		} else {
			insets.topType = HtmlInsets.TYPE_PIXELS;
			insets.top = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}
	
	private int getInsetPixels(int value, int type, int defaultValue, int availSize, int autoValue) {
		if (type == TYPE_PIXELS) {
			return value;
		} else if (type == TYPE_UNDEFINED) {
			return defaultValue;
		} else if (type == TYPE_AUTO) {
			return autoValue;
		} else if (type == TYPE_PERCENT) {
			return availSize * value / 100;
		} else {
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
