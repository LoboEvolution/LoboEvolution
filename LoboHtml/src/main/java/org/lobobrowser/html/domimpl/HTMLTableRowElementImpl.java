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
/*
 * Created on Dec 4, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.dom.HTMLCollection;
import org.lobobrowser.html.dom.HTMLElement;
import org.lobobrowser.html.dom.HTMLTableRowElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLTableRowElementImpl extends HTMLElementImpl implements HTMLTableRowElement {
	public HTMLTableRowElementImpl() {
		super("TR", true);
	}

	public HTMLTableRowElementImpl(String name) {
		super(name, true);
	}

	@Override
	public void deleteCell(int index) throws DOMException {
		synchronized (this.treeLock) {
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					final Node node = (Node) nl.get(i);
					if (node instanceof org.lobobrowser.html.dom.HTMLTableCellElement) {
						if (trcount == index) {
							removeChildAt(index);
						}
						trcount++;
					}
				}
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
	}

	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	@Override
	public HTMLCollection getCells() {
		final NodeFilter filter = node -> node instanceof HTMLTableCellElementImpl;
		return new DescendentHTMLCollection(this, filter, this.treeLock, false);
	}

	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

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
							throw new StopVisitorException(new Integer(this.count));
						}
						this.count++;
					}
				}
			});
		} catch (final StopVisitorException sve) {
			return ((Integer) sve.getTag()).intValue();
		}
		return -1;
	}

	@Override
	public int getSectionRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getVAlign() {
		return getAttribute("valign");
	}

	@Override
	public HTMLElement insertCell(int index) throws DOMException {
		return this.insertCell(index, "TD");
	}

	private HTMLElement insertCell(int index, String tagName) throws DOMException {
		final org.w3c.dom.Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLElement cellElement = (HTMLElement) doc.createElement(tagName);
		synchronized (this.treeLock) {
			if (index == -1) {
				appendChild(cellElement);
				return cellElement;
			}
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					final Node node = (Node) nl.get(i);
					if (node instanceof org.lobobrowser.html.dom.HTMLTableCellElement) {
						if (trcount == index) {
							insertAt(cellElement, i);
							return cellElement;
						}
						trcount++;
					}
				}
			} else {
				appendChild(cellElement);
				return cellElement;
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
	}

	/**
	 * Inserts a TH element at the specified index.
	 * <p>
	 * Note: This method is non-standard.
	 * 
	 * @param index The cell index to insert at.
	 * @return The element that was inserted.
	 * @throws DOMException When the index is out of range.
	 */
	public HTMLElement insertHeader(int index) throws DOMException {
		return this.insertCell(index, "TH");
	}

	@Override
	public void setAlign(String align) {
		setAttribute("align", align);
	}

	@Override
	public void setBgColor(String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	@Override
	public void setCh(String ch) {
		setAttribute("ch", ch);
	}

	@Override
	public void setChOff(String chOff) {
		setAttribute("choff", chOff);
	}

	@Override
	public void setVAlign(String vAlign) {
		setAttribute("valign", vAlign);
	}
}
