/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.style;

import java.awt.Insets;

/**
 * The Class HtmlInsets.
 */
public class HtmlInsets {

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
     * Instantiates a new html insets.
     */
    public HtmlInsets() {
    }

    /** Gets the right.
	 *
	 * @return the right
	 */
    public int getTop() {
        return top;
    }

    /** Sets the right.
	 *
	 * @param top
	 *            the new right
	 */
    public void setTop(int top) {
        this.top = top;
    }

    /** Gets the right.
	 *
	 * @return the right
	 */
    public int getBottom() {
        return bottom;
    }

    /** Sets the right.
	 *
	 * @param bottom
	 *            the new right
	 */
    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    /** Gets the right.
	 *
	 * @return the right
	 */
    public int getLeft() {
        return left;
    }

    /** Sets the right.
	 *
	 * @param left
	 *            the new right
	 */
    public void setLeft(int left) {
        this.left = left;
    }

    /** Gets the right.
	 *
	 * @return the right
	 */
    public int getRight() {
        return right;
    }

    /** Sets the right.
	 *
	 * @param right
	 *            the new right
	 */
    public void setRight(int right) {
        this.right = right;
    }

    /** Gets the right type.
	 *
	 * @return the right type
	 */
    public int getTopType() {
        return topType;
    }

    /** Sets the right type.
	 *
	 * @param topType
	 *            the new right type
	 */
    public void setTopType(int topType) {
        this.topType = topType;
    }

    /** Gets the right type.
	 *
	 * @return the right type
	 */
    public int getBottomType() {
        return bottomType;
    }

    /** Sets the right type.
	 *
	 * @param bottomType
	 *            the new right type
	 */
    public void setBottomType(int bottomType) {
        this.bottomType = bottomType;
    }

    /** Gets the right type.
	 *
	 * @return the right type
	 */
    public int getLeftType() {
        return leftType;
    }

    /** Sets the right type.
	 *
	 * @param leftType
	 *            the new right type
	 */
    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    /** Gets the right type.
	 *
	 * @return the right type
	 */
    public int getRightType() {
        return rightType;
    }

    /** Sets the right type.
	 *
	 * @param rightType
	 *            the new right type
	 */
    public void setRightType(int rightType) {
        this.rightType = rightType;
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
    public Insets getAWTInsets(int defaultTop, int defaultLeft,
            int defaultBottom, int defaultRight, int availWidth,
            int availHeight, int autoX, int autoY) {
        int top = getInsetPixels(this.top, this.topType, defaultTop,
                availHeight, autoY);
        int left = getInsetPixels(this.left, this.leftType, defaultLeft,
                availWidth, autoX);
        int bottom = getInsetPixels(this.bottom, this.bottomType,
                defaultBottom, availHeight, autoY);
        int right = getInsetPixels(this.right, this.rightType, defaultRight,
                availWidth, autoX);
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
        int bottom = getInsetPixels(this.bottom, this.bottomType, 0,
                availHeight, 0);
        int right = getInsetPixels(this.right, this.rightType, 0, availWidth, 0);
        return new Insets(top, left, bottom, right);
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
    private static int getInsetPixels(int value, int type, int defaultValue,
            int availSize, int autoValue) {
        if (type == TYPE_PIXELS) {
            return value;
        } else if (type == TYPE_UNDEFINED) {
            return defaultValue;
        } else if (type == TYPE_AUTO) {
            return autoValue;
        } else if (type == TYPE_PERCENT) {
            return (availSize * value) / 100;
        } else {
            throw new IllegalStateException();
        }
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + this.top + "," + this.left + "," + this.bottom + ","
                + this.right + "]";
    }
}
