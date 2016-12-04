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
/*
 * Created on Dec 4, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.dombl.NodeVisitor;
import org.lobobrowser.html.dombl.StopVisitorException;
import org.lobobrowser.html.domfilter.NodeFilter;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.w3c.html.HTMLTableRowElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * The Class HTMLTableRowElementImpl.
 */
public class HTMLTableRowElementImpl extends HTMLElementImpl implements HTMLTableRowElement {

	/**
	 * Instantiates a new HTML table row element impl.
	 *
	 * @param name
	 *            the name
	 */
	public HTMLTableRowElementImpl(String name) {
		super(name, true);
	}

	/**
	 * Instantiates a new HTML table row element impl.
	 */
	public HTMLTableRowElementImpl() {
		super(HtmlProperties.TR, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getRowIndex()
	 */
	@Override
	public int getRowIndex() {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
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
							throw new StopVisitorException(Integer.valueOf(this.count));
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getSectionRowIndex()
	 */
	@Override
	public int getSectionRowIndex() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getCells()
	 */
	@Override
	public HTMLCollection getCells() {
		NodeFilter filter = new NodeFilter() {
			@Override
			public boolean accept(Node node) {
				return node instanceof HTMLTableCellElementImpl;
			}
		};
		return new DescendentHTMLCollection(this, filter, this.getTreeLock(), false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getAlign()
	 */
	@Override
	public String getAlign() {
		return this.getAttribute(HtmlAttributeProperties.ALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableRowElement#setAlign(java.lang.String)
	 */
	@Override
	public void setAlign(String align) {
		this.setAttribute(HtmlAttributeProperties.ALIGN, align);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getBgColor()
	 */
	@Override
	public String getBgColor() {
		return this.getAttribute(HtmlAttributeProperties.BGCOLOR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableRowElement#setBgColor(java.lang.String)
	 */
	@Override
	public void setBgColor(String bgColor) {
		this.setAttribute(HtmlAttributeProperties.BGCOLOR, bgColor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getCh()
	 */
	@Override
	public String getCh() {
		return this.getAttribute(HtmlAttributeProperties.CH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#setCh(java.lang.String)
	 */
	@Override
	public void setCh(String ch) {
		this.setAttribute(HtmlAttributeProperties.CH, ch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getChOff()
	 */
	@Override
	public String getChOff() {
		return this.getAttribute(HtmlAttributeProperties.CHOFF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableRowElement#setChOff(java.lang.String)
	 */
	@Override
	public void setChOff(String chOff) {
		this.setAttribute(HtmlAttributeProperties.CHOFF, chOff);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#getVAlign()
	 */
	@Override
	public String getVAlign() {
		return this.getAttribute(HtmlAttributeProperties.VALIGN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.w3c.html.HTMLTableRowElement#setVAlign(java.lang.String)
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#insertCell(int)
	 */
	@Override
	public HTMLElement insertCell(int index) throws DOMException {
		return this.insertCell(index, HtmlProperties.TD);
	}

	/**
	 * Insert cell.
	 *
	 * @param index
	 *            the index
	 * @param tagName
	 *            the tag name
	 * @return the HTML element
	 * @throws DOMException
	 *             the DOM exception
	 */
	private HTMLElement insertCell(int index, String tagName) throws DOMException {
		org.w3c.dom.Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
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
					Node node = nl.get(i);
					if (node instanceof org.lobobrowser.w3c.html.HTMLTableCellElement) {
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
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#deleteCell(int)
	 */
	@Override
	public void deleteCell(int index) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					Node node = nl.get(i);
					if (node instanceof org.lobobrowser.w3c.html.HTMLTableCellElement) {
						if (trcount == index) {
							this.removeChildAt(index);
						}
						trcount++;
					}
				}
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.w3c.html.HTMLTableRowElement#insertCell()
	 */
	@Override
	public HTMLElement insertCell() {
		// TODO Auto-generated method stub
		return null;
	}
}
