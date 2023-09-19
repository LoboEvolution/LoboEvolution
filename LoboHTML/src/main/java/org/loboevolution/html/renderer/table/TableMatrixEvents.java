/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderer.table;

import org.loboevolution.html.renderer.BoundableRenderable;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

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
	public boolean onDoubleClick(final MouseEvent event, int x, int y) {
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

	/**
	 * <p>onMouseClick.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMouseClick(final MouseEvent event, int x, int y) {
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

	/**
	 * <p>onMouseDisarmed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @return a boolean.
	 */
	public boolean onMouseDisarmed(final MouseEvent event) {
		final BoundableRenderable ar = this.armedRenderable;
		if (ar != null) {
			this.armedRenderable = null;
			return ar.onMouseDisarmed(event);
		} else {
			return true;
		}
	}

	/**
	 * <p>onMousePressed.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMousePressed(final MouseEvent event, int x, int y) {
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

	/**
	 * <p>onMouseReleased.</p>
	 *
	 * @param event a {@link java.awt.event.MouseEvent} object.
	 * @param x a int.
	 * @param y a int.
	 * @return a boolean.
	 */
	public boolean onMouseReleased(final MouseEvent event, int x, int y) {
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
