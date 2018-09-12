/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.gui.mouse;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.PositionedRenderable;
import org.loboevolution.html.renderertable.RTableCell;
import org.loboevolution.util.Objects;

public class TableMouse {
		
	/** The all cells. */
	private List<BoundableRenderable> allCells;
	
	/** The armed renderable. */
	private BoundableRenderable armedRenderable;
	
	/** The positioned renderables. */
	private SortedSet<PositionedRenderable> positionedRenderables;
	
	public TableMouse(List<BoundableRenderable> cells, SortedSet<PositionedRenderable> pRenderables) {
		this.allCells = cells;
		this.positionedRenderables = pRenderables;
	}
	
	/**
	 * On mouse click.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onMouseClick(MouseEvent event, int x, int y) {
		if (positionedRenderables("onMouseClick", event, x, y)) {
			int numCells = allCells.size();
			for (int i = 0; i < numCells; i++) {
				RTableCell cell = (RTableCell) allCells.get(i);
				Rectangle bounds = cell.getBounds();
				if (bounds.contains(x, y)) {
					if (!cell.onMouseClick(event, x - bounds.x, y - bounds.y)) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}

	/**
	 * On double click.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onDoubleClick(MouseEvent event, int x, int y) {
		if (positionedRenderables("onDoubleClick", event, x, y)) {
			int numCells = allCells.size();
			for (int i = 0; i < numCells; i++) {
				RTableCell cell = (RTableCell) allCells.get(i);
				Rectangle bounds = cell.getBounds();
				if (bounds.contains(x, y)) {
					if (!cell.onDoubleClick(event, x - bounds.x, y - bounds.y)) {
						return false;
					}
					break;
				}
			}
		}
		return true;
	}

	/**
	 * On mouse disarmed.
	 *
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean onMouseDisarmed(MouseEvent event) {
		BoundableRenderable ar = armedRenderable;
		if (ar != null) {
			armedRenderable = null;
			return ar.onMouseDisarmed(event);
		} else {
			return true;
		}
	}

	/**
	 * On mouse pressed.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onMousePressed(MouseEvent event, int x, int y) {
		if (positionedRenderables("onMousePressed", event, x, y)) {
			int numCells = allCells.size();
			for (int i = 0; i < numCells; i++) {
				RTableCell cell = (RTableCell) allCells.get(i);
				Rectangle bounds = cell.getBounds();
				if (bounds.contains(x, y)) {
					if (!cell.onMousePressed(event, x - bounds.x, y - bounds.y)) {
						armedRenderable = cell;
						return false;
					}
					break;
				}
			}
		}
		return true;
	}

	/**
	 * On mouse released.
	 *
	 * @param event
	 *            the event
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @return true, if successful
	 */
	public boolean onMouseReleased(MouseEvent event, int x, int y) {
		if (positionedRenderables("onMouseReleased", event, x, y)) {
			int numCells = allCells.size();
			boolean found = false;
			for (int i = 0; i < numCells; i++) {
				RTableCell cell = (RTableCell) allCells.get(i);
				Rectangle bounds = cell.getBounds();
				if (bounds.contains(x, y)) {
					found = true;
					BoundableRenderable oldArmedRenderable = armedRenderable;
					if (!Objects.equals(cell, oldArmedRenderable)) {
						oldArmedRenderable.onMouseDisarmed(event);
						armedRenderable = null;
					}
					if (!cell.onMouseReleased(event, x - bounds.x, y - bounds.y)) {
						return false;
					}
					break;
				}
			}
			if (!found) {
				BoundableRenderable oldArmedRenderable = armedRenderable;
				if (oldArmedRenderable != null) {
					oldArmedRenderable.onMouseDisarmed(event);
					armedRenderable = null;
				}
			}
		}
		return true;
	}
	
	
	private boolean positionedRenderables(String evt, MouseEvent event, int x, int y) {
		Collection<PositionedRenderable> prs = this.positionedRenderables;
		if (prs != null) {
			Iterator<PositionedRenderable> i = prs.iterator();
			while (i.hasNext()) {
				PositionedRenderable pr = i.next();
				BoundableRenderable r = pr.getRenderable();
				Rectangle bounds = r.getBounds();
				if (bounds.contains(x, y)) {
					int childX = x - r.getX();
					int childY = y - r.getY();
					switch (evt) {
					case "onMouseReleased":
						if (!r.onMouseReleased(event, childX, childY)) {
							return false;
						}
						break;
					case "onMousePressed":
						if (!r.onMousePressed(event, childX, childY)) {
							return false;
						}
						break;
					case "onDoubleClick":
						if (!r.onDoubleClick(event, childX, childY)) {
							return false;
						}
						break;
					default:
						return true;
					}
				}
			}
		}
		return true;
	}
}