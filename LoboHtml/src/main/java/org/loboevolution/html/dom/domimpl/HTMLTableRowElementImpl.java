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
/*
 * Created on Dec 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.HTMLTableRowElement;
import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRowRenderState;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * <p>HTMLTableRowElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLTableRowElementImpl extends HTMLElementImpl implements HTMLTableRowElement {
	
	/**
	 * <p>Constructor for HTMLTableRowElementImpl.</p>
	 */
	public HTMLTableRowElementImpl() {
		super("TR", true);
	}
	
    /** {@inheritDoc} */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new TableRowRenderState(prevRenderState, this);
    }

	/**
	 * <p>Constructor for HTMLTableRowElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableRowElementImpl(String name) {
		super(name, true);
	}

	/** {@inheritDoc} */
	@Override
	public void deleteCell(int index) throws DOMException {
		int trcount = 0;
		for (Node node : Nodes.iterable(nodeList)) {
			if (node instanceof HTMLTableCellElement) {
				if (trcount == index) {
					removeChildAt(index);
				}
				trcount++;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection getCells() {
		final NodeFilter filter = node -> node instanceof HTMLTableCellElementImpl;
        return new HTMLCollectionImpl(this, filter);
	}

	/** {@inheritDoc} */
	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	/** {@inheritDoc} */
	@Override
	public int getRowIndex() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		if (parent == null) {
			return -1;
		}
		try {
			parent.visit(new NodeVisitor() {
				private int count = 0;

				@Override
				public void visit(Node node) {
					if (node instanceof HTMLTableRowElementImpl) {
						if (HTMLTableRowElementImpl.this == node) {
							throw new StopVisitorException(this.count);
						}
						this.count++;
					}
				}
			});
		} catch (final StopVisitorException sve) {
			return (Integer) sve.getTag();
		}
		return -1;
	}

	/** {@inheritDoc} */
	@Override
	public int getSectionRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/** {@inheritDoc} */
	@Override
	public String getVAlign() {
		return getAttribute("valign");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement insertCell(int index) throws DOMException {
		return this.insertCell(index, "TD");
	}

	private HTMLElement insertCell(int index, String tagName) throws DOMException {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLElement cellElement = (HTMLElement) doc.createElement(tagName);
		synchronized (this.treeLock) {
			if (index == -1) {
				appendChild(cellElement);
				return cellElement;
			}

			int trcount = 0;
			for (Node node : Nodes.iterable(nodeList)) {
				if (node instanceof HTMLTableCellElement) {
					if (trcount == index) {
						insertAt(cellElement, nodeList.indexOf(node));
						return cellElement;
					}
					trcount++;
				}
			}
			appendChild(cellElement);
			return cellElement;
		}
	}

	/**
	 * Inserts a TH element at the specified index.
	 * <p>
	 * Note: This method is non-standard.
	 *
	 * @param index The cell index to insert at.
	 * @return The element that was inserted.
	 * @throws org.w3c.dom.DOMException When the index is out of range.
	 */
	public HTMLElement insertHeader(int index) throws DOMException {
		return this.insertCell(index, "TH");
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public void setCh(String ch) {
		setAttribute("ch", ch);
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(String chOff) {
		setAttribute("choff", chOff);
	}

	/** {@inheritDoc} */
	@Override
	public void setVAlign(String vAlign) {
		setAttribute("valign", vAlign);
	}
}
