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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.nodeimpl.DOMException;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Code;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRenderState;


/**
 * <p>HTMLTableElementImpl class.</p>
 *
 *
 *
 */
public class HTMLTableElementImpl extends HTMLElementImpl implements HTMLTableElement {

	private HTMLTableCaptionElement caption;

	private HTMLTableSectionElement tfoot;

	private HTMLTableSectionElement thead;


	/**
	 * <p>Constructor for HTMLTableElementImpl.</p>
	 */
	public HTMLTableElementImpl() {
		super("TABLE");
	}

	/**
	 * <p>Constructor for HTMLTableElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement createCaption() {
		final Document doc = this.document;
		if (doc == null ) return null;
		HTMLElement newChild = (HTMLElement) doc.createElement("caption");
		appendChild(newChild);
		return newChild;
	}
	
	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement createTFoot() {		
		final Document doc = this.document;
		if (doc == null ) return null;
		HTMLElement newChild = (HTMLElement) doc.createElement("tfoot");
		appendChild(newChild);
		return newChild;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement createTHead() {
		final Document doc = this.document;
		if (doc == null ) return null;
		HTMLElement newChild = (HTMLElement) doc.createElement("thead");
		appendChild(newChild);
		return newChild;
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLElement createTBody() {		
		final Document doc = this.document;
		if (doc == null ) return null;
		HTMLElement newChild = (HTMLElement) doc.createElement("tbody");
		appendChild(newChild);
		return newChild;
	}

	/** {@inheritDoc} */
	@Override
	public void deleteCaption() {
		removeTableChildren(new ElementFilter("CAPTION"));
	}

	/** {@inheritDoc} */
	@Override
	public void deleteRow(int index) {
		int trcount = 0;
		for (Iterator<Node> i = nodeList.iterator(); i.hasNext();) {
			Node node = i.next();
			if ("TR".equalsIgnoreCase(node.getNodeName())) {
				if (trcount == index) {
					removeChildAt(nodeList.indexOf(node));
					return;
				}
				trcount++;
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public void deleteTFoot() {
		removeTableChildren(new ElementFilter("TFOOT"));
	}

	/** {@inheritDoc} */
	@Override
	public void deleteTHead() {
		removeTableChildren(new ElementFilter("THEAD"));
	}
	
	/** {@inheritDoc} */
	@Override
	public void deleteTBody() {
		removeTableChildren(new ElementFilter("TBODY"));
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
	public String getBorder() {
		return getAttribute("border");
	}

	/** {@inheritDoc} */
	@Override
	public String getCellPadding() {
		return getAttribute("cellpadding");
	}

	/** {@inheritDoc} */
	@Override
	public String getCellSpacing() {
		return getAttribute("cellspacing");
	}

	/** {@inheritDoc} */
	@Override
	public String getFrame() {
		return getAttribute("frame");
	}


	/** {@inheritDoc} */
	@Override
	public HTMLCollection getRows() {
        return new HTMLCollectionImpl(this, new ElementFilter("TR"));
	}

	/** {@inheritDoc} */
	@Override
	public String getRules() {
		return getAttribute("rules");
	}

	/** {@inheritDoc} */
	@Override
	public String getSummary() {
		return getAttribute("summary");
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLTableCaptionElement getCaption() {
		if (this.caption == null) {
			Node node = getFirstChildByFilter(new ElementFilter("CAPTION"));
			if (node == null) {
				return null;
			} else {
				return (HTMLTableCaptionElement) node;
			}
		}
		return this.caption;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLCollection gettBodies() {
		return new HTMLCollectionImpl(this, new ElementFilter("TBODY"));
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableSectionElement gettFoot() {
		if (this.tfoot == null) {
			Node node = getFirstChildByFilter(new ElementFilter("TFOOT"));
			if (node == null) {
				return null;
			} else {
				return (HTMLTableSectionElement) node;
			}
		}
		return this.tfoot;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public HTMLTableSectionElement gettHead() {
		if (this.thead == null) {
			Node node = getFirstChildByFilter(new ElementFilter("THEAD"));
			if (node == null) {
				return null;
			} else {
				return (HTMLTableSectionElement) node;
			}
		}
		return this.thead;
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	
	/**
	 * {@inheritDoc}
	 *
	 * Inserts a row at the index given. If index is -1,
	 * the row is appended as the last row.
	 */
	@Override
	public HTMLTableRowElement insertRow(int index) throws Exception {
		return this.insertRow(index, "TR");
	}
	
	private HTMLTableRowElementImpl insertRow(Object objIndex, String tagName)  throws Exception {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(Code.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLTableRowElementImpl rowElement = (HTMLTableRowElementImpl) doc.createElement("TR");
		
		int index = -1;		
		if (objIndex instanceof Double) {
			index = ((Double) objIndex).intValue();
		} else {
			if (objIndex == null || "".equals(objIndex)) {
				index = 0;
			} else {
				index = Integer.parseInt(objIndex.toString());
			}
		}
		
		if (index  == 0 || index  == - 1) {
			appendChild(rowElement);
			AtomicInteger cellIndex = new AtomicInteger(-1);
			if (index == -1) {				
				NodeListImpl childNodes = (NodeListImpl) getChildNodes();
				childNodes.forEach(node -> {
					if (node instanceof HTMLTableRowElement) {
						cellIndex.incrementAndGet();
					}
				});
			}
			rowElement.setIndex(index == -1 ? cellIndex.get() : 0);
			return rowElement;
		}

		AtomicInteger trcount = new AtomicInteger();
		nodeList.forEach(node -> {
			if (node instanceof HTMLTableRowElement) {
				trcount.incrementAndGet();
			}
		});
		
		if (trcount.get() < index) {
            throw new DOMException(Code.INDEX_SIZE_ERR, "The index is greater than the number of cells in the table ");
		} else {
			rowElement.setIndex(index);
			insertAt(rowElement, index);
		}

		return rowElement;
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLTableRowElement insertRow() {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(Code.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLTableRowElement rowElement = (HTMLTableRowElement) doc.createElement("TR");
		appendChild(rowElement);
		return rowElement;
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
	public void setBorder(String border) {
		setAttribute("border", border);
	}

	/** {@inheritDoc} */
	@Override
	public void setCaption(HTMLTableCaptionElement caption) {
		this.caption = caption;
	}

	/** {@inheritDoc} */
	@Override
	public void setCellPadding(String cellPadding) {
		setAttribute("cellpadding", cellPadding);
	}

	/** {@inheritDoc} */
	@Override
	public void setCellSpacing(String cellSpacing) {
		setAttribute("cellspacing", cellSpacing);
	}

	/** {@inheritDoc} */
	@Override
	public void setFrame(String frame) {
		setAttribute("frame", frame);
	}

	/** {@inheritDoc} */
	@Override
	public void setRules(String rules) {
		setAttribute("rules", rules);
	}

	/** {@inheritDoc} */
	@Override
	public void setSummary(String summary) {
		setAttribute("summary", summary);
	}

	/** {@inheritDoc} */
	@Override
	public void settFoot(HTMLTableSectionElement tFoot) throws DOMException {
		this.tfoot = tFoot;
	}

	/** {@inheritDoc} */
	@Override
	public void settHead(HTMLTableSectionElement tHead) throws DOMException {
		this.thead = tHead;
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableElement]";
	}
}
