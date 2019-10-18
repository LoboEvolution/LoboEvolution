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

public class HtmlInsets {
	public static final int TYPE_AUTO = 2;
	public static final int TYPE_PERCENT = 3;
	public static final int TYPE_PIXELS = 1;
	public static final int TYPE_UNDEFINED = 0;
	
	public int top, bottom, left, right;

	/* Types assumed to be initialized as UNDEFINED */
	public int topType, bottomType, leftType, rightType;

	public HtmlInsets() {
	}

	public Insets getAWTInsets(int defaultTop, int defaultLeft, int defaultBottom, int defaultRight,
			int availWidth, int availHeight, int autoX, int autoY) {
		final int top = getInsetPixels(this.top, this.topType, defaultTop, availHeight, autoY);
		final int left = getInsetPixels(this.left, this.leftType, defaultLeft, availWidth, autoX);
		final int bottom = getInsetPixels(this.bottom, this.bottomType, defaultBottom, availHeight, autoY);
		final int right = getInsetPixels(this.right, this.rightType, defaultRight, availWidth, autoX);
		return new Insets(top, left, bottom, right);
	}
	
	public Insets getSimpleAWTInsets(int availWidth, int availHeight) {
		final int top = getInsetPixels(this.top, this.topType, 0, availHeight, 0);
		final int left = getInsetPixels(this.left, this.leftType, 0, availWidth, 0);
		final int bottom = getInsetPixels(this.bottom, this.bottomType, 0, availHeight, 0);
		final int right = getInsetPixels(this.right, this.rightType, 0, availWidth, 0);
		return new Insets(top, left, bottom, right);
	}
	
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

	public int getBottom() {
		return this.bottom;
	}

	public int getBottomType() {
		return this.bottomType;
	}

	public int getLeft() {
		return this.left;
	}

	public int getLeftType() {
		return this.leftType;
	}

	public int getRight() {
		return this.right;
	}

	public int getRightType() {
		return this.rightType;
	}

	public int getTop() {
		return this.top;
	}

	public int getTopType() {
		return this.topType;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public void setBottomType(int bottomType) {
		this.bottomType = bottomType;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public void setLeftType(int leftType) {
		this.leftType = leftType;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setRightType(int rightType) {
		this.rightType = rightType;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public void setTopType(int topType) {
		this.topType = topType;
	}

	@Override
	public String toString() {
		return "[" + this.top + "," + this.left + "," + this.bottom + "," + this.right + "]";
	}
}
