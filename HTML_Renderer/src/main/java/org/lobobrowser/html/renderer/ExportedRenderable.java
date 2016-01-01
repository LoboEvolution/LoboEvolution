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
package org.lobobrowser.html.renderer;

/**
 * The Class ExportedRenderable.
 */
public class ExportedRenderable {

	/** The original target. */
	private RBlockViewport originalTarget;

	/** The renderable. */
	private BoundableRenderable renderable;

	/**
	 * Coordinates in original target, for aligned blocks.
	 */
	private int x, y;

	/**
	 * -1 (left), 0, or +1 (right).
	 */
	private int alignment;

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

	/**
	 * Gets the original target.
	 *
	 * @return the original target
	 */
	public RBlockViewport getOriginalTarget() {
		return originalTarget;
	}

	/**
	 * Sets the original target.
	 *
	 * @param originalTarget
	 *            the new original target
	 */
	public void setOriginalTarget(RBlockViewport originalTarget) {
		this.originalTarget = originalTarget;
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
	 * Gets the coordinates in original target, for aligned blocks.
	 *
	 * @return the coordinates in original target, for aligned blocks
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the coordinates in original target, for aligned blocks.
	 *
	 * @param x
	 *            the new coordinates in original target, for aligned blocks
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the coordinates in original target, for aligned blocks.
	 *
	 * @return the coordinates in original target, for aligned blocks
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the coordinates in original target, for aligned blocks.
	 *
	 * @param y
	 *            the new coordinates in original target, for aligned blocks
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Gets the -1 (left), 0, or +1 (right).
	 *
	 * @return the -1 (left), 0, or +1 (right)
	 */
	public int getAlignment() {
		return alignment;
	}

	/**
	 * Sets the -1 (left), 0, or +1 (right).
	 *
	 * @param alignment
	 *            the new -1 (left), 0, or +1 (right)
	 */
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}
}
