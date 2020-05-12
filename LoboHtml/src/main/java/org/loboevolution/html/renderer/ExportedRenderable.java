/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

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
