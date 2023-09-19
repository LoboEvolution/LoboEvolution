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
/*
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRenderState;

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * <p>HTMLTableElementImpl class.</p>
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
		boolean isBorder = hasAttribute("border");
		if (isBorder) {
			String border = getAttribute("border");
			return "border".equals(border) ? "1" : border;
		}
		return null;
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
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
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
            throw new DOMException(DOMException.INDEX_SIZE_ERR, "The index is greater than the number of cells in the table ");
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
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
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
