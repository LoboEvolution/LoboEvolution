/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.renderer;

class ExportedRenderable {
	/**
	 * -1 (left), 0, or +1 (right).
	 */
	public final int alignment;
	public final RBlockViewport originalTarget;

	public final BoundableRenderable renderable;

	/**
	 * Coordinates in original target, for aligned blocks.
	 */
	public final int x, y;

	/**
	 * <p>Constructor for ExportedRenderable.</p>
	 *
	 * @param originalTarget a {@link org.loboevolution.html.renderer.RBlockViewport} object.
	 * @param renderable a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 * @param x a int.
	 * @param y a int.
	 * @param alignment a int.
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
