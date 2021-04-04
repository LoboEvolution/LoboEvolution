/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderer.table;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;

import org.loboevolution.html.renderer.BoundableRenderable;

class TableMatrixEvents {
	
	private BoundableRenderable armedRenderable;
	
	private List<RTableCell> allCells;
	
	/**
	 * <p>Constructor for TableMatrixEvents.</p>
	 *
	 * @param allCells a {@link java.util.List} object.
	 */
	public TableMatrixEvents(List<RTableCell> allCells) {
		this.allCells = allCells;
	}
	
	/**
	 * <p>onDoubleClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		for (RTableCell cell : allCells) {
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onDoubleClick(event, x - bounds.x, y - bounds.y)) {
					return false;
				}
				break;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseClick(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/**
	 * <p>onMouseClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		for (RTableCell cell : allCells) {
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onMouseClick(event, x - bounds.x, y - bounds.y)) {
					return false;
				}
				break;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseDisarmed(java.awt.event.
	 * MouseEvent)
	 */
	/**
	 * <p>onMouseDisarmed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	public boolean onMouseDisarmed(MouseEvent event) {
		final BoundableRenderable ar = this.armedRenderable;
		if (ar != null) {
			this.armedRenderable = null;
			return ar.onMouseDisarmed(event);
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMousePressed(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/**
	 * <p>onMousePressed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		for (RTableCell cell : allCells) {
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				if (!cell.onMousePressed(event, x - bounds.x, y - bounds.y)) {
					this.armedRenderable = cell;
					return false;
				}
				break;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.BoundableRenderable#onMouseReleased(java.awt.event.
	 * MouseEvent, int, int)
	 */
	/**
	 * <p>onMouseReleased.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		boolean found = false;
		for (RTableCell cell : allCells) {
			final Rectangle bounds = cell.getVisualBounds();
			if (bounds.contains(x, y)) {
				found = true;
				final BoundableRenderable oldArmedRenderable = this.armedRenderable;
				if (oldArmedRenderable != null && cell != oldArmedRenderable) {
					oldArmedRenderable.onMouseDisarmed(event);
					this.armedRenderable = null;
				}
				if (!cell.onMouseReleased(event, x - bounds.x, y - bounds.y)) {
					return false;
				}
				break;
			}
		}
		if (!found) {
			final BoundableRenderable oldArmedRenderable = this.armedRenderable;
			if (oldArmedRenderable != null) {
				oldArmedRenderable.onMouseDisarmed(event);
				this.armedRenderable = null;
			}
		}
		return true;
	}

}
