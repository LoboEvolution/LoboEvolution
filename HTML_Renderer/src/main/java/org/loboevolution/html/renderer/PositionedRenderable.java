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
package org.loboevolution.html.renderer;

/**
 * The Class PositionedRenderable.
 */
public class PositionedRenderable {

	/** The Constant EMPTY_ARRAY. */
	public static final PositionedRenderable[] EMPTY_ARRAY = new PositionedRenderable[0];

	/** The renderable. */
	private BoundableRenderable renderable;

	/** The vertical alignable. */
	private boolean verticalAlignable;

	/** The ordinal. */
	private int ordinal;

	/** The is float. */
	private boolean isFloat;

	/**
	 * Instantiates a new positioned renderable.
	 *
	 * @param renderable
	 *            the renderable
	 * @param verticalAlignable
	 *            the vertical alignable
	 * @param ordinal
	 *            the ordinal
	 * @param isFloat
	 *            the is float
	 */
	public PositionedRenderable(final BoundableRenderable renderable, final boolean verticalAlignable, int ordinal,
			boolean isFloat) {
		super();
		this.renderable = renderable;
		this.verticalAlignable = verticalAlignable;
		this.ordinal = ordinal;
		this.isFloat = isFloat;
	}

	/**
	 * Gets the renderable.
	 *
	 * @return the renderable
	 */
	public BoundableRenderable getRenderable() {
		return renderable;
	}

	/**
	 * Sets the renderable.
	 *
	 * @param renderable
	 *            the new renderable
	 */
	public void setRenderable(BoundableRenderable renderable) {
		this.renderable = renderable;
	}

	/**
	 * Checks if is vertical alignable.
	 *
	 * @return the vertical alignable
	 */
	public boolean isVerticalAlignable() {
		return verticalAlignable;
	}

	/**
	 * Sets the vertical alignable.
	 *
	 * @param verticalAlignable
	 *            the new vertical alignable
	 */
	public void setVerticalAlignable(boolean verticalAlignable) {
		this.verticalAlignable = verticalAlignable;
	}

	/**
	 * Gets the ordinal.
	 *
	 * @return the ordinal
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * Sets the ordinal.
	 *
	 * @param ordinal
	 *            the new ordinal
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	/**
	 * Checks if is float.
	 *
	 * @return true, if is float
	 */
	public boolean isFloat() {
		return isFloat;
	}

	/**
	 * Sets the float.
	 *
	 * @param isFloat
	 *            the new float
	 */
	public void setFloat(boolean isFloat) {
		this.isFloat = isFloat;
	}
}
