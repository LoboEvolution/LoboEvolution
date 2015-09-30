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
 * The Class ExportedRenderable.
 */
class ExportedRenderable {

	/** The original target. */
	public final RBlockViewport originalTarget;

	/** The renderable. */
	public final BoundableRenderable renderable;

	/**
	 * Coordinates in original target, for aligned blocks.
	 */
	public final int x, y;

	/**
	 * -1 (left), 0, or +1 (right).
	 */
	public final int alignment;

	/**
	 * Instantiates a new exported renderable.
	 *
	 * @param originalTarget
	 *            the original target
	 * @param renderable
	 *            the renderable
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param alignment
	 *            the alignment
	 */
	public ExportedRenderable(final RBlockViewport originalTarget, final BoundableRenderable renderable, final int x,
			final int y, final int alignment) {
		super();
		this.originalTarget = originalTarget;
		this.x = x;
		this.y = y;
		this.alignment = alignment;
		this.renderable = renderable;
	}
}
