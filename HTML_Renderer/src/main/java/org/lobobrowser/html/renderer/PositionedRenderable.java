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
    public PositionedRenderable(final BoundableRenderable renderable,
            final boolean verticalAlignable, int ordinal, boolean isFloat) {
        super();
        this.renderable = renderable;
        this.verticalAlignable = verticalAlignable;
        this.ordinal = ordinal;
        this.isFloat = isFloat;
    }

	/**
	 * @return the renderable
	 */
	public BoundableRenderable getRenderable() {
		return renderable;
	}

	/**
	 * @param renderable the renderable to set
	 */
	public void setRenderable(BoundableRenderable renderable) {
		this.renderable = renderable;
	}

	/**
	 * @return the verticalAlignable
	 */
	public boolean isVerticalAlignable() {
		return verticalAlignable;
	}

	/**
	 * @param verticalAlignable the verticalAlignable to set
	 */
	public void setVerticalAlignable(boolean verticalAlignable) {
		this.verticalAlignable = verticalAlignable;
	}

	/**
	 * @return the ordinal
	 */
	public int getOrdinal() {
		return ordinal;
	}

	/**
	 * @param ordinal the ordinal to set
	 */
	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	/**
	 * @return the isFloat
	 */
	public boolean isFloat() {
		return isFloat;
	}

	/**
	 * @param isFloat the isFloat to set
	 */
	public void setFloat(boolean isFloat) {
		this.isFloat = isFloat;
	}
}
