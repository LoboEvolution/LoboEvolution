/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2015 Lobo Evolution

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

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.dombl.NodeImpl;
import org.lobobrowser.html.dombl.NodeVisitor;
import org.lobobrowser.html.dombl.StopVisitorException;
import org.lobobrowser.html.domfilter.NodeFilter;
import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLTableRowElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLTableRowElementImpl extends HTMLElementImpl implements
		HTMLTableRowElement {
	public HTMLTableRowElementImpl(String name) {
		super(name, true);
	}

	public HTMLTableRowElementImpl() {
		super(HtmlProperties.TR, true);
	}

	public int getRowIndex() {
		NodeImpl parent = (NodeImpl) this.getParentNode();
		if (parent == null) {
			return -1;
		}
		try {
			parent.visit(new NodeVisitor() {
				private int count = 0;

				public void visit(Node node) {
					if (node instanceof HTMLTableRowElementImpl) {
						if (HTMLTableRowElementImpl.this == node) {
							throw new StopVisitorException(new Integer(
									this.count));
						}
						this.count++;
					}
				}
			});
		} catch (StopVisitorException sve) {
			return ((Integer) sve.getTag()).intValue();
		}
		return -1;
	}

	public int getSectionRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	public HTMLCollection getCells() {
		NodeFilter filter = new NodeFilter() {
			public boolean accept(Node node) {
				return node instanceof HTMLTableCellElementImpl;
			}
		};
		return new DescendentHTMLCollection(this, filter, this.getTreeLock(), false);
	}

	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	public String getBgColor() {
		return this.getAttribute(HtmlAttributeProperties.BGCOLOR);
	}

	public void setBgColor(String bgColor) {
		this.setAttribute(HtmlAttributeProperties.BGCOLOR, bgColor);
	}

	public String getCh() {
		return this.getAttribute(HtmlAttributeProperties.CH);
	}

	public void setCh(String ch) {
		this.setAttribute(HtmlAttributeProperties.CH, ch);
	}

	public String getChOff() {
		return this.getAttribute(HtmlAttributeProperties.CHOFF);
	}

	public void setChOff(String chOff) {
		this.setAttribute(HtmlAttributeProperties.CHOFF, chOff);
	}

	public String getVAlign() {
		return this.getAttribute(HtmlAttributeProperties.VALIGN);
	}

	public void setVAlign(String vAlign) {
		this.setAttribute(HtmlAttributeProperties.VALIGN, vAlign);
	}

	/**
	 * Inserts a TH element at the specified index.
	 * <p>
	 * Note: This method is non-standard.
	 * 
	 * @param index
	 *            The cell index to insert at.
	 * @return The element that was inserted.
	 * @throws DOMException
	 *             When the index is out of range.
	 */
	public HTMLElement insertHeader(int index) throws DOMException {
		return this.insertCell(index, HtmlProperties.TH);
	}

	public HTMLElement insertCell(int index) throws DOMException {
		return this.insertCell(index, HtmlProperties.TD);
	}

	private HTMLElement insertCell(int index, String tagName)
			throws DOMException {
		org.w3c.dom.Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
					"Orphan element");
		}
		HTMLElement cellElement = (HTMLElement) doc.createElement(tagName);
		synchronized (this.getTreeLock()) {
			if (index == -1) {
				this.appendChild(cellElement);
				return cellElement;
			}
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					Node node = (Node) nl.get(i);
					if (node instanceof org.lobobrowser.html.w3c.HTMLTableCellElement) {
						if (trcount == index) {
							this.insertAt(cellElement, i);
							return cellElement;
						}
						trcount++;
					}
				}
			} else {
				this.appendChild(cellElement);
				return cellElement;
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR,
				"Index out of range");
	}

	public void deleteCell(int index) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					Node node = (Node) nl.get(i);
					if (node instanceof org.lobobrowser.html.w3c.HTMLTableCellElement) {
						if (trcount == index) {
							this.removeChildAt(index);
						}
						trcount++;
					}
				}
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR,
				"Index out of range");
	}

	@Override
	public HTMLElement insertCell() {
		// TODO Auto-generated method stub
		return null;
	}
}
