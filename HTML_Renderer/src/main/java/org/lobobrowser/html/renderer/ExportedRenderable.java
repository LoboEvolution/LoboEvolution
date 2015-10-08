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
	 * @return the originalTarget
	 */
	public RBlockViewport getOriginalTarget() {
		return originalTarget;
	}

	/**
	 * @param originalTarget the originalTarget to set
	 */
	public void setOriginalTarget(RBlockViewport originalTarget) {
		this.originalTarget = originalTarget;
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
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the alignment
	 */
	public int getAlignment() {
		return alignment;
	}

	/**
	 * @param alignment the alignment to set
	 */
	public void setAlignment(int alignment) {
		this.alignment = alignment;
	}
}
