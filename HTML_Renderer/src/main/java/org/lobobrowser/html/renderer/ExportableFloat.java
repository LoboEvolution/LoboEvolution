/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.renderer;

/**
 * The Class ExportableFloat.
 */
public class ExportableFloat {

	/** The Constant EMPTY_ARRAY. */
	public static final ExportableFloat[] EMPTY_ARRAY = new ExportableFloat[0];

	/** The element. */
	private BoundableRenderable element;

	/** The left float. */
	private boolean leftFloat;

	/** The orig x. */
	private int origX;

	/** The orig y. */
	private int origY;

	/**
	 * Instantiates a new exportable float.
	 *
	 * @param element
	 *            the element
	 * @param leftFloat
	 *            the left float
	 * @param origX
	 *            the orig x
	 * @param origY
	 *            the orig y
	 */
	public ExportableFloat(BoundableRenderable element, boolean leftFloat, int origX, int origY) {
		super();
		this.element = element;
		this.leftFloat = leftFloat;
		this.origX = origX;
		this.origY = origY;
	}

	/**
	 * Gets the element.
	 *
	 * @return the element
	 */
	public BoundableRenderable getElement() {
		return element;
	}

	/**
	 * Sets the element.
	 *
	 * @param element
	 *            the new element
	 */
	public void setElement(BoundableRenderable element) {
		this.element = element;
	}

	/**
	 * Checks if is left float.
	 *
	 * @return the left float
	 */
	public boolean isLeftFloat() {
		return leftFloat;
	}

	/**
	 * Sets the left float.
	 *
	 * @param leftFloat
	 *            the new left float
	 */
	public void setLeftFloat(boolean leftFloat) {
		this.leftFloat = leftFloat;
	}

	/**
	 * Gets the orig x.
	 *
	 * @return the orig x
	 */
	public int getOrigX() {
		return origX;
	}

	/**
	 * Sets the orig x.
	 *
	 * @param origX
	 *            the new orig x
	 */
	public void setOrigX(int origX) {
		this.origX = origX;
	}

	/**
	 * Gets the orig y.
	 *
	 * @return the orig y
	 */
	public int getOrigY() {
		return origY;
	}

	/**
	 * Sets the orig y.
	 *
	 * @param origY
	 *            the new orig y
	 */
	public void setOrigY(int origY) {
		this.origY = origY;
	}
}
