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
 * Created on Dec 3, 2005
 */
package org.loboevolution.html.dom.domimpl;

import java.util.Iterator;

import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.nodeimpl.DOMException;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableRenderState;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlLength;
import org.loboevolution.html.style.HtmlValues;

import org.loboevolution.html.node.Code;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;

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
	public HTMLTableCaptionElement createCaption() {
		final Document doc = this.document;
		return doc == null ? null : (HTMLTableCaptionElement) doc.createElement("caption");
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableSectionElement createTFoot() {
		final Document doc = this.document;
		return doc == null ? null : (HTMLTableSectionElement) doc.createElement("tfoot");
	}

	/** {@inheritDoc} */
	@Override
	public HTMLTableSectionElement createTHead() {
		final Document doc = this.document;
		return doc == null ? null : (HTMLTableSectionElement) doc.createElement("thead");
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLTableSectionElement createTBody() {
		Document doc = this.document;
		return doc == null ? null : (HTMLTableSectionElement) doc.createElement("tbody");
	}

	/** {@inheritDoc} */
	@Override
	public void deleteCaption() {
		removeChildren(new ElementFilter("CAPTION"));
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
	public HTMLTableRowElement insertRow(int index) {
		final Document doc = this.document;
		if (doc == null) {
			throw new DOMException(Code.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLTableRowElement rowElement = (HTMLTableRowElement) doc.createElement("TR");
		if (index == -1) {
			appendChild(rowElement);
			return rowElement;
		}
		int trcount = 0;
		for (Iterator<Node> i = nodeList.iterator(); i.hasNext();) {
			Node node = i.next();
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
	
	@Override
	public HTMLTableRowElement insertRow() {
		// TODO Auto-generated method stub
		return null;
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
	public void setTFoot(HTMLTableSectionElement tFoot) {
		this.tfoot = tFoot;
	}

	/** {@inheritDoc} */
	@Override
	public void setTHead(HTMLTableSectionElement tHead) {
		this.thead = tHead;
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}

	@Override
	public String getAccessKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAccessKeyLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAutocapitalize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Element getOffsetParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSpellcheck() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDraggable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isTranslate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAccessKey(String accessKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutocapitalize(String autocapitalize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDraggable(boolean draggable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHidden(boolean hidden) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSpellcheck(boolean spellcheck) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTranslate(boolean translate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void click() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return "[object HTMLTableElement]";
	}
}
