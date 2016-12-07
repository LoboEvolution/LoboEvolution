/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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

package org.lobobrowser.html.renderer;

import java.awt.Point;

/**
 * Contains a renderer node and a position in that node.
 */
public class RenderableSpot {

	/** The renderable. */
	private BoundableRenderable renderable;

	/** The x. */
	private int x;

	/** The y. */
	private int y;

	/**
	 * Instantiates a new renderable spot.
	 *
	 * @param renderable
	 *            the renderable
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 */
	public RenderableSpot(BoundableRenderable renderable, int x, int y) {
		super();
		this.renderable = renderable;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the point.
	 *
	 * @return the point
	 */
	public Point getPoint() {
		return new Point(this.x, this.y);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof RenderableSpot)) {
			return false;
		}
		RenderableSpot otherRp = (RenderableSpot) other;
		return (otherRp.renderable == this.renderable) && (otherRp.x == this.x) && (otherRp.y == this.y);
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
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x
	 *            the new x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y
	 *            the new y
	 */
	public void setY(int y) {
		this.y = y;
	}
}
