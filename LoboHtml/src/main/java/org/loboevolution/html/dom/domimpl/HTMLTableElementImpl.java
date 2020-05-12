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
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Nodes;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.HTMLTableCaptionElement;
import org.loboevolution.html.dom.HTMLTableElement;
import org.loboevolution.html.dom.HTMLTableSectionElement;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

/**
 * <p>HTMLTableElementImpl class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class HTMLTableElementImpl extends HTMLAbstractUIElement implements HTMLTableElement {

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
	public HTMLTableElementImpl(String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement createCaption() {
		final Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("caption");
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
		return doc == null ? null : (HTMLElement) doc.createElement("tfoot");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLElement createTHead() {
		final Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("thead");
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLElement createTBody() {
		Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("tbody");
	}

	/** {@inheritDoc} */
	@Override
	public void deleteCaption() {
		removeChildren(new ElementFilter("CAPTION"));
	}

	/** {@inheritDoc} */
	@Override
	public void deleteRow(int index) throws DOMException {
		int trcount = 0;
		for (Node node : Nodes.iterable(nodeList)) {
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
		removeChildren(new ElementFilter("TFOOT"));
	}

	/** {@inheritDoc} */
	@Override
	public void deleteTHead() {
		removeChildren(new ElementFilter("THEAD"));
	}
	
	/** {@inheritDoc} */
	@Override
	public void deleteTBody() {
		removeChildren(new ElementFilter("TBODY"));
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
	public HTMLTableCaptionElement getCaption() {
		return this.caption;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContext#getHeightLength()
	 */
	/**
	 * <p>getHeightLength.</p>
	 *
	 * @param availHeight a int.
	 * @return a {@link org.loboevolution.html.style.HtmlLength} object.
	 */
	public HtmlLength getHeightLength(int availHeight) {
		try {
			final AbstractCSSProperties props = getCurrentStyle();
			final String heightText = props == null ? null : props.getHeight();
			if (heightText == null) {
				return new HtmlLength(getAttribute("height"));
			} else {
				return new HtmlLength(HtmlValues.getPixelSize(heightText, getRenderState(), 0, availHeight));
			}
		} catch (final Exception err) {
			return null;
		}
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
	public HTMLCollection getTBodies() {
		return new HTMLCollectionImpl(this, new ElementFilter("TBODY"));
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableSectionElement getTFoot() {
		return this.tfoot;
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableSectionElement getTHead() {
		return this.thead;
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContext#getWidthLength()
	 */
	/**
	 * <p>getWidthLength.</p>
	 *
	 * @param availWidth a int.
	 * @return a {@link org.loboevolution.html.style.HtmlLength} object.
	 */
	public HtmlLength getWidthLength(int availWidth) {
		try {
			final AbstractCSSProperties props = getCurrentStyle();
			final String widthText = props == null ? null : props.getWidth();
			if (widthText == null) {
				return new HtmlLength(getAttribute("width"));
			} else {
				return new HtmlLength(HtmlValues.getPixelSize(widthText, getRenderState(), 0, availWidth));
			}
		} catch (final Exception err) {
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Inserts a row at the index given. If index is -1,
	 * the row is appended as the last row.
	 */
	@Override
	public HTMLElement insertRow(int index) throws DOMException {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLElement rowElement = (HTMLElement) doc.createElement("TR");
		if (index == -1) {
			appendChild(rowElement);
			return rowElement;
		}
		int trcount = 0;
		for (Node node : Nodes.iterable(nodeList)) {
			if ("TR".equalsIgnoreCase(node.getNodeName())) {
				if (trcount == index) {
					insertAt(rowElement, nodeList.indexOf(node));
					return rowElement;
				}
				trcount++;
			}
		}
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
	public void setCaption(HTMLTableCaptionElement caption) throws DOMException {
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
	public void setTFoot(HTMLTableSectionElement tFoot) throws DOMException {
		this.tfoot = tFoot;
	}

	/** {@inheritDoc} */
	@Override
	public void setTHead(HTMLTableSectionElement tHead) throws DOMException {
		this.thead = tHead;
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
