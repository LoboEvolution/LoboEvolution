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
package org.loboevolution.html.style;

import java.awt.Insets;
import java.util.StringTokenizer;

import org.loboevolution.html.renderstate.RenderState;

/**
 * The Class HtmlInsets.
 */
public class HtmlInsets implements CSSValuesProperties {

	/** The Constant TYPE_UNDEFINED. */
	public static final int TYPE_UNDEFINED = 0;

	/** The Constant TYPE_PIXELS. */
	public static final int TYPE_PIXELS = 1;

	/** The Constant TYPE_AUTO. */
	public static final int TYPE_AUTO = 2;

	/** The Constant TYPE_PERCENT. */
	public static final int TYPE_PERCENT = 3;

	/** The right. */
	public int top, bottom, left, right;

	/* Types assumed to be initialized as UNDEFINED */
	/** The right type. */
	public int topType, bottomType, leftType, rightType;
	
	/**
	 * Update top inset.
	 *
	 * @param insets
	 *            the insets
	 * @param sizeText
	 *            the size text
	 * @param renderState
	 *            the render state
	 * @return the html insets
	 */
	public static HtmlInsets updateTopInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			sizeText = "0px";
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if (AUTO.equalsIgnoreCase(sizeText)) {
			insets.topType = HtmlInsets.TYPE_AUTO;

		} else if (INHERIT.equalsIgnoreCase(sizeText)) {
			if (renderState != null && renderState.getPreviousRenderState() != null
					&& renderState.getPreviousRenderState().getMarginInsets() != null) {
				insets.top = renderState.getPreviousRenderState().getMarginInsets().getTop();
				insets.topType = renderState.getPreviousRenderState().getMarginInsets().getTopType();
			}

		} else if (INITIAL.equalsIgnoreCase(sizeText)) {
			insets.topType = HtmlInsets.TYPE_PIXELS;
			insets.top = HtmlValues.getPixelSize(sizeText, renderState, 0);

		} else if (MEDIUM.equalsIgnoreCase(sizeText)) {
			insets.topType = HtmlInsets.TYPE_PIXELS;
			insets.top = HtmlValues.getPixelSize("3px", renderState, 0);

		} else if (sizeText.endsWith("%")) {
			insets.topType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.top = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (NumberFormatException nfe) {
				insets.top = 0;
			}
		} else {
			insets.topType = HtmlInsets.TYPE_PIXELS;
			insets.top = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	/**
	 * Update left inset.
	 *
	 * @param insets
	 *            the insets
	 * @param sizeText
	 *            the size text
	 * @param renderState
	 *            the render state
	 * @return the html insets
	 */
	public static HtmlInsets updateLeftInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			sizeText = "0px";
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if (AUTO.equalsIgnoreCase(sizeText)) {
			insets.leftType = HtmlInsets.TYPE_AUTO;
		} else if (INHERIT.equalsIgnoreCase(sizeText)) {
			if (renderState != null && renderState.getPreviousRenderState() != null
					&& renderState.getPreviousRenderState().getMarginInsets() != null) {
				insets.left = renderState.getPreviousRenderState().getMarginInsets().getLeft();
				insets.leftType = renderState.getPreviousRenderState().getMarginInsets().getLeftType();
			}
		} else if (INITIAL.equalsIgnoreCase(sizeText)) {
			insets.leftType = HtmlInsets.TYPE_PIXELS;
			insets.left = HtmlValues.getPixelSize(sizeText, renderState, 0);

		} else if (MEDIUM.equalsIgnoreCase(sizeText)) {
			insets.leftType = HtmlInsets.TYPE_PIXELS;
			insets.left = HtmlValues.getPixelSize("3px", renderState, 0);

		} else if (sizeText.endsWith("%")) {
			insets.leftType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.left = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (NumberFormatException nfe) {
				insets.left = 0;
			}
		} else {
			insets.leftType = HtmlInsets.TYPE_PIXELS;
			insets.left = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	/**
	 * Update bottom inset.
	 *
	 * @param insets
	 *            the insets
	 * @param sizeText
	 *            the size text
	 * @param renderState
	 *            the render state
	 * @return the html insets
	 */
	public static HtmlInsets updateBottomInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			sizeText = "0px";
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if (AUTO.equalsIgnoreCase(sizeText)) {
			insets.bottomType = HtmlInsets.TYPE_AUTO;
		} else if (INHERIT.equalsIgnoreCase(sizeText)) {
			if (renderState != null && renderState.getPreviousRenderState() != null
					&& renderState.getPreviousRenderState().getMarginInsets() != null) {
				insets.bottom = renderState.getPreviousRenderState().getMarginInsets().getBottom();
				insets.bottomType = renderState.getPreviousRenderState().getMarginInsets().getBottomType();
			}
		} else if (INITIAL.equalsIgnoreCase(sizeText)) {
			insets.bottomType = HtmlInsets.TYPE_PIXELS;
			insets.bottom = HtmlValues.getPixelSize(sizeText, renderState, 0);

		} else if (MEDIUM.equalsIgnoreCase(sizeText)) {
			insets.bottomType = HtmlInsets.TYPE_PIXELS;
			insets.bottom = HtmlValues.getPixelSize("3px", renderState, 0);

		} else if (sizeText.endsWith("%")) {
			insets.bottomType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.bottom = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (NumberFormatException nfe) {
				insets.bottom = 0;
			}
		} else {
			insets.bottomType = HtmlInsets.TYPE_PIXELS;
			insets.bottom = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	/**
	 * Update right inset.
	 *
	 * @param insets
	 *            the insets
	 * @param sizeText
	 *            the size text
	 * @param renderState
	 *            the render state
	 * @return the html insets
	 */
	public static HtmlInsets updateRightInset(HtmlInsets insets, String sizeText, RenderState renderState) {
		if (sizeText == null) {
			sizeText = "0px";
		}
		sizeText = sizeText.trim();
		if (sizeText.length() == 0) {
			return insets;
		}
		if (insets == null) {
			insets = new HtmlInsets();
		}
		if (AUTO.equalsIgnoreCase(sizeText)) {
			insets.rightType = HtmlInsets.TYPE_AUTO;
		} else if (INHERIT.equalsIgnoreCase(sizeText)) {
			if (renderState != null && renderState.getPreviousRenderState() != null
					&& renderState.getPreviousRenderState().getMarginInsets() != null) {
				insets.right = renderState.getPreviousRenderState().getMarginInsets().getRight();
				insets.rightType = renderState.getPreviousRenderState().getMarginInsets().getRightType();
			}
		} else if (INITIAL.equalsIgnoreCase(sizeText)) {
			insets.rightType = HtmlInsets.TYPE_PIXELS;
			insets.right = HtmlValues.getPixelSize(sizeText, renderState, 0);

		} else if (MEDIUM.equalsIgnoreCase(sizeText)) {
			insets.rightType = HtmlInsets.TYPE_PIXELS;
			insets.right = HtmlValues.getPixelSize("3px", renderState, 0);

		} else if (sizeText.endsWith("%")) {
			insets.rightType = HtmlInsets.TYPE_PERCENT;
			try {
				insets.right = Integer.parseInt(sizeText.substring(0, sizeText.length() - 1));
			} catch (NumberFormatException nfe) {
				insets.right = 0;
			}
		} else {
			insets.rightType = HtmlInsets.TYPE_PIXELS;
			insets.right = HtmlValues.getPixelSize(sizeText, renderState, 0);
		}
		return insets;
	}

	/**
	 * Gets the insets.
	 *
	 * @param insetsSpec
	 *            the insets spec
	 * @param renderState
	 *            the render state
	 * @param negativeOK
	 *            the negative ok
	 * @return the insets
	 */
	public static Insets getInsets(String insetsSpec, RenderState renderState, boolean negativeOK) {
		int[] insetsArray = new int[4];
		int size = 0;
		StringTokenizer tok = new StringTokenizer(insetsSpec);
		if (tok.hasMoreTokens()) {
			String token = tok.nextToken();
			insetsArray[0] = HtmlValues.getPixelSize(token, renderState, 0);
			if (negativeOK || insetsArray[0] >= 0) {
				size = 1;
				if (tok.hasMoreTokens()) {
					token = tok.nextToken();
					insetsArray[1] = HtmlValues.getPixelSize(token, renderState, 0);
					if (negativeOK || insetsArray[1] >= 0) {
						size = 2;
						if (tok.hasMoreTokens()) {
							token = tok.nextToken();
							insetsArray[2] = HtmlValues.getPixelSize(token, renderState, 0);
							if (negativeOK || insetsArray[2] >= 0) {
								size = 3;
								if (tok.hasMoreTokens()) {
									token = tok.nextToken();
									insetsArray[3] = HtmlValues.getPixelSize(token, renderState, 0);
									size = 4;
									if (negativeOK || insetsArray[3] >= 0) {
										// nop
									} else {
										insetsArray[3] = 0;
									}
								}
							} else {
								size = 4;
								insetsArray[2] = 0;
							}
						}
					} else {
						size = 4;
						insetsArray[1] = 0;
					}
				}
			} else {
				size = 1;
				insetsArray[0] = 0;
			}
		}
		if (size == 4) {
			return new Insets(insetsArray[0], insetsArray[3], insetsArray[2], insetsArray[1]);
		} else if (size == 1) {
			int val = insetsArray[0];
			return new Insets(val, val, val, val);
		} else if (size == 2) {
			return new Insets(insetsArray[0], insetsArray[1], insetsArray[0], insetsArray[1]);
		} else if (size == 3) {
			return new Insets(insetsArray[0], insetsArray[1], insetsArray[2], insetsArray[1]);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the AWT insets.
	 *
	 * @param defaultTop
	 *            the default top
	 * @param defaultLeft
	 *            the default left
	 * @param defaultBottom
	 *            the default bottom
	 * @param defaultRight
	 *            the default right
	 * @param availWidth
	 *            the avail width
	 * @param availHeight
	 *            the avail height
	 * @param autoX
	 *            the auto x
	 * @param autoY
	 *            the auto y
	 * @return the AWT insets
	 */
	public Insets getAWTInsets(int defaultTop, int defaultLeft, int defaultBottom, int defaultRight, int availWidth,
			int availHeight, int autoX, int autoY) {
		int top = getInsetPixels(this.top, this.topType, defaultTop, availHeight, autoY);
		int left = getInsetPixels(this.left, this.leftType, defaultLeft, availWidth, autoX);
		int bottom = getInsetPixels(this.bottom, this.bottomType, defaultBottom, availHeight, autoY);
		int right = getInsetPixels(this.right, this.rightType, defaultRight, availWidth, autoX);
		return new Insets(top, left, bottom, right);
	}

	/**
	 * Gets the simple awt insets.
	 *
	 * @param availWidth
	 *            the avail width
	 * @param availHeight
	 *            the avail height
	 * @return the simple awt insets
	 */
	public Insets getSimpleAWTInsets(int availWidth, int availHeight) {
		int top = getInsetPixels(this.top, this.topType, 0, availHeight, 0);
		int left = getInsetPixels(this.left, this.leftType, 0, availWidth, 0);
		int bottom = getInsetPixels(this.bottom, this.bottomType, 0, availHeight, 0);
		int right = getInsetPixels(this.right, this.rightType, 0, availWidth, 0);
		return new Insets(top, left, bottom, right);
	}
	
	/**
	 * Checks if is length.
	 *
	 * @param token
	 *            the token
	 * @return true, if is length
	 */
	public static boolean isLength(String token) {
		if (token.endsWith("px") || token.endsWith("pt") || token.endsWith("pc") || token.endsWith("em")
				|| token.endsWith("mm") || token.endsWith("ex") || token.endsWith("em")) {
			return true;
		}
		try {
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * Gets the inset pixels.
	 *
	 * @param value
	 *            the value
	 * @param type
	 *            the type
	 * @param defaultValue
	 *            the default value
	 * @param availSize
	 *            the avail size
	 * @param autoValue
	 *            the auto value
	 * @return the inset pixels
	 */
	private static int getInsetPixels(int value, int type, int defaultValue, int availSize, int autoValue) {
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
	 * Gets the right.
	 *
	 * @return the right
	 */
	public int getTop() {
		return top;
	}

	/**
	 * Sets the right.
	 *
	 * @param top
	 *            the new right
	 */
	public void setTop(int top) {
		this.top = top;
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public int getBottom() {
		return bottom;
	}

	/**
	 * Sets the right.
	 *
	 * @param bottom
	 *            the new right
	 */
	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public int getLeft() {
		return left;
	}

	/**
	 * Sets the right.
	 *
	 * @param left
	 *            the new right
	 */
	public void setLeft(int left) {
		this.left = left;
	}

	/**
	 * Gets the right.
	 *
	 * @return the right
	 */
	public int getRight() {
		return right;
	}

	/**
	 * Sets the right.
	 *
	 * @param right
	 *            the new right
	 */
	public void setRight(int right) {
		this.right = right;
	}

	/**
	 * Gets the right type.
	 *
	 * @return the right type
	 */
	public int getTopType() {
		return topType;
	}

	/**
	 * Sets the right type.
	 *
	 * @param topType
	 *            the new right type
	 */
	public void setTopType(int topType) {
		this.topType = topType;
	}

	/**
	 * Gets the right type.
	 *
	 * @return the right type
	 */
	public int getBottomType() {
		return bottomType;
	}

	/**
	 * Sets the right type.
	 *
	 * @param bottomType
	 *            the new right type
	 */
	public void setBottomType(int bottomType) {
		this.bottomType = bottomType;
	}

	/**
	 * Gets the right type.
	 *
	 * @return the right type
	 */
	public int getLeftType() {
		return leftType;
	}

	/**
	 * Sets the right type.
	 *
	 * @param leftType
	 *            the new right type
	 */
	public void setLeftType(int leftType) {
		this.leftType = leftType;
	}

	/**
	 * Gets the right type.
	 *
	 * @return the right type
	 */
	public int getRightType() {
		return rightType;
	}

	/**
	 * Sets the right type.
	 *
	 * @param rightType
	 *            the new right type
	 */
	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[" + this.top + "," + this.left + "," + this.bottom + "," + this.right + "]";
	}
}
