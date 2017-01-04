/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.layout;

/**
 * The Class LayoutValue.
 */
public class LayoutValue {

    /** The width. */
    private int width;

    /** The height. */
    private int height;

    /** The has h scroll bar. */
    private boolean hasHScrollBar;

    /** The has v scroll bar. */
    private boolean hasVScrollBar;

    /**
     * Instantiates a new layout value.
     *
     * @param width
     *            the width
     * @param height
     *            the height
     * @param hasHScrollBar
     *            the has h scroll bar
     * @param hasVScrollBar
     *            the has v scroll bar
     */
    public LayoutValue(int width, int height, boolean hasHScrollBar,
            boolean hasVScrollBar) {
        this.width = width;
        this.height = height;
        this.hasHScrollBar = hasHScrollBar;
        this.hasVScrollBar = hasVScrollBar;
    }

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Checks if is has h scroll bar.
	 *
	 * @return the has h scroll bar
	 */
	public boolean isHasHScrollBar() {
		return hasHScrollBar;
	}

	/**
	 * Sets the has h scroll bar.
	 *
	 * @param hasHScrollBar
	 *            the new has h scroll bar
	 */
	public void setHasHScrollBar(boolean hasHScrollBar) {
		this.hasHScrollBar = hasHScrollBar;
	}

	/**
	 * Checks if is has v scroll bar.
	 *
	 * @return the has v scroll bar
	 */
	public boolean isHasVScrollBar() {
		return hasVScrollBar;
	}

	/**
	 * Sets the has v scroll bar.
	 *
	 * @param hasVScrollBar
	 *            the new has v scroll bar
	 */
	public void setHasVScrollBar(boolean hasVScrollBar) {
		this.hasVScrollBar = hasVScrollBar;
	}
}
