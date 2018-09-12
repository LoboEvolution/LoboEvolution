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
package org.loboevolution.html.renderertable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.loboevolution.util.Objects;

import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.domfilter.ColumnsFilter;
import org.loboevolution.html.domimpl.DOMNodeImpl;
import org.loboevolution.html.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.BoundableRenderable;
import org.loboevolution.html.renderer.FrameContext;
import org.loboevolution.html.renderer.RElement;
import org.loboevolution.html.renderer.RenderableContainer;
import org.loboevolution.html.renderer.VirtualCell;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.http.UserAgentContext;
import org.w3c.dom.Node;

public class RTableRow {
		
	/**
	 * Populates the ROWS and ALL_CELLS collections.
	 * @return 
	 */
	public static ArrayList<HTMLElementImpl> populateRows(HTMLElementImpl te, ArrayList<ArrayList<VirtualCell>> rows,
			ArrayList<BoundableRenderable> allCells, ArrayList<HTMLElementImpl> rowElements,
			UserAgentContext parserContext, HtmlRendererContext rendererContext, FrameContext frameContext,
			RElement relement, RenderableContainer container) {

		ArrayList<HTMLElementImpl> rElements = rowElements;
		Map<HTMLElementImpl, ArrayList<VirtualCell>> rowElementToRowArray = new HashMap<>(2);
		List<DOMNodeImpl> cellList = te.getDescendents(new ColumnsFilter(), false);
		ArrayList<VirtualCell> currentNullRow = null;
		Iterator<DOMNodeImpl> ci = cellList.iterator();
		while (ci.hasNext()) {
			final HTMLElementImpl columnNode = (HTMLElementImpl) ci.next();
			final HTMLElementImpl rowElement = getParentRow(columnNode, te);
			if ((rowElement != null) && (rowElement.getRenderState().getDisplay() == RenderState.DISPLAY_NONE)) {
				continue;
			}
			ArrayList<VirtualCell> row;
			if (rowElement != null) {
				currentNullRow = null;
				row = rowElementToRowArray.get(rowElement);
				if (row == null) {
					row = new ArrayList<VirtualCell>();
					rowElementToRowArray.put(rowElement, row);
					rows.add(row);
				}
			} else {
				if (currentNullRow != null) {
					row = currentNullRow;
				} else {
					row = new ArrayList<VirtualCell>();
					currentNullRow = row;
					rows.add(row);
				}
			}
			rElements.add(rowElement);
			RTableCell ac = (RTableCell) columnNode.getUINode();
			if (ac == null) {
				ac = new RTableCell(columnNode, parserContext, rendererContext, frameContext, container);
				ac.setParent(relement);
				columnNode.setUINode(ac);
			}
			VirtualCell vc = new VirtualCell(ac, true);
			ac.setTopLeftVirtualCell(vc);
			row.add(vc);
			allCells.add(ac);
		}
		
		return rElements;
	}

	/**
	 * Gets the parent row.
	 *
	 * @param cellNode
	 *            the cell node
	 * @return the parent row
	 */
	private final static HTMLElementImpl getParentRow(HTMLElementImpl cellNode, HTMLElementImpl te) {
		Node parentNode = cellNode.getParentNode();
		while (true) {
			if (parentNode == null || Objects.equals(parentNode, te)) {
				return null;
			} else if (parentNode instanceof HTMLElementImpl) {
				final HTMLElementImpl parentElem = (HTMLElementImpl) parentNode;
				final int parentDisplay = parentElem.getRenderState().getDisplay();
				if (parentDisplay == RenderState.DISPLAY_TABLE_ROW) {
					return parentElem;
				}
				if (parentDisplay == RenderState.DISPLAY_TABLE) {
					return null;
				}
			}
			parentNode = parentNode.getParentNode();
		}
	}
}
