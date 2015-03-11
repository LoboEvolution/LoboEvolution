/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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
package org.lobobrowser.html.renderer;


/**
 * The Class PositionedRenderable.
 */
class PositionedRenderable {
	
	/** The Constant EMPTY_ARRAY. */
	public static final PositionedRenderable[] EMPTY_ARRAY = new PositionedRenderable[0];
	
	/** The renderable. */
	public final BoundableRenderable renderable;
	
	/** The vertical alignable. */
	public final boolean verticalAlignable;
	
	/** The ordinal. */
	public final int ordinal;
	
	/** The is float. */
	public final boolean isFloat;

	/**
	 * Instantiates a new positioned renderable.
	 *
	 * @param renderable the renderable
	 * @param verticalAlignable the vertical alignable
	 * @param ordinal the ordinal
	 * @param isFloat the is float
	 */
	public PositionedRenderable(final BoundableRenderable renderable,
			final boolean verticalAlignable, int ordinal, boolean isFloat) {
		super();
		this.renderable = renderable;
		this.verticalAlignable = verticalAlignable;
		this.ordinal = ordinal;
		this.isFloat = isFloat;
	}
}