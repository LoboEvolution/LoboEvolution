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
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.domfilter.ElementTableAttributeFilter;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlLength;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.TableRenderState;
import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.html.w3c.HTMLElement;
import org.lobobrowser.html.w3c.HTMLTableCaptionElement;
import org.lobobrowser.html.w3c.HTMLTableElement;
import org.lobobrowser.html.w3c.HTMLTableSectionElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLTableElementImpl extends HTMLAbstractUIElement implements
		HTMLTableElement {

	public HTMLTableElementImpl() {
		super(HtmlProperties.TABLE);
	}

	public HTMLTableElementImpl(String name) {
		super(name);
	}

	private HTMLTableCaptionElement caption;

	public HTMLTableCaptionElement getCaption() {
		return this.caption;
	}

	public void setCaption(HTMLTableCaptionElement caption) throws DOMException {
		this.caption = caption;
	}

	private HTMLTableSectionElement thead;

	public HTMLTableSectionElement getTHead() {
		return this.thead;
	}

	public void setTHead(HTMLTableSectionElement tHead) throws DOMException {
		this.thead = tHead;
	}

	private HTMLTableSectionElement tfoot;

	public HTMLTableSectionElement getTFoot() {
		return this.tfoot;
	}

	public void setTFoot(HTMLTableSectionElement tFoot) throws DOMException {
		this.tfoot = tFoot;
	}

	public HTMLCollection getRows() {
		return new DescendentHTMLCollection(this, new ElementTableAttributeFilter(HtmlProperties.TR),
				this.getTreeLock(), false);
	}

	public HTMLCollection getTBodies() {
		return new DescendentHTMLCollection(this, new ElementTableAttributeFilter(HtmlProperties.TBODY),
				this.getTreeLock(), false);
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

	public String getBorder() {
		return this.getAttribute(HtmlAttributeProperties.BORDER);
	}

	public void setBorder(String border) {
		this.setAttribute(HtmlAttributeProperties.BORDER, border);
	}

	public String getCellPadding() {
		return this.getAttribute(HtmlAttributeProperties.CELLPADDING);
	}

	public void setCellPadding(String cellPadding) {
		this.setAttribute(HtmlAttributeProperties.CELLPADDING, cellPadding);
	}

	public String getCellSpacing() {
		return this.getAttribute(HtmlAttributeProperties.CELLSPACING);
	}

	public void setCellSpacing(String cellSpacing) {
		this.setAttribute(HtmlAttributeProperties.CELLSPACING, cellSpacing);
	}

	public String getFrame() {
		return this.getAttribute(HtmlAttributeProperties.FRAME);
	}

	public void setFrame(String frame) {
		this.setAttribute(HtmlAttributeProperties.FRAME, frame);
	}

	public String getRules() {
		return this.getAttribute(HtmlAttributeProperties.RULES);
	}

	public void setRules(String rules) {
		this.setAttribute(HtmlAttributeProperties.RULES, rules);
	}

	public String getSummary() {
		return this.getAttribute(HtmlAttributeProperties.SUMMARY);
	}

	public void setSummary(String summary) {
		this.setAttribute(HtmlAttributeProperties.SUMMARY, summary);
	}

	public String getWidth() {
		return this.getAttribute(HtmlAttributeProperties.WIDTH);
	}

	public void setWidth(String width) {
		this.setAttribute(HtmlAttributeProperties.WIDTH, width);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.RenderableContext#getHeightLength()
	 */
	public HtmlLength getHeightLength(int availHeight) {
		try {
			AbstractCSS2Properties props = this.getCurrentStyle();
			String heightText = props == null ? null : props.getHeight();
			if (heightText == null) {
				return new HtmlLength(this.getAttribute(HtmlAttributeProperties.HEIGHT));
			} else {
				return new HtmlLength(HtmlValues.getPixelSize(heightText,
						this.getRenderState(), 0, availHeight));
			}
		} catch (Exception err) {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.lobobrowser.html.render.RenderableContext#getWidthLength()
	 */
	public HtmlLength getWidthLength(int availWidth) {
		try {
			AbstractCSS2Properties props = this.getCurrentStyle();
			String widthText = props == null ? null : props.getWidth();
			if (widthText == null) {
				return new HtmlLength(this.getAttribute(HtmlAttributeProperties.WIDTH));
			} else {
				return new HtmlLength(HtmlValues.getPixelSize(widthText,
						this.getRenderState(), 0, availWidth));
			}
		} catch (Exception err) {
			return null;
		}
	}

	public HTMLElement createTHead() {
		org.w3c.dom.Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("thead");
	}

	public void deleteTHead() {
		this.removeChildren(new ElementTableAttributeFilter("THEAD"));
	}

	public HTMLElement createTFoot() {
		org.w3c.dom.Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("tfoot");
	}

	public void deleteTFoot() {
		this.removeChildren(new ElementTableAttributeFilter("TFOOT"));
	}

	public HTMLElement createCaption() {
		org.w3c.dom.Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("caption");
	}

	public void deleteCaption() {
		this.removeChildren(new ElementTableAttributeFilter("CAPTION"));
	}

	/**
	 * Inserts a row at the index given. If <code>index</code> is
	 * <code>-1</code>, the row is appended as the last row.
	 */
	public HTMLElement insertRow(int index) throws DOMException {
		org.w3c.dom.Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR,
					"Orphan element");
		}
		HTMLElement rowElement = (HTMLElement) doc.createElement("TR");
		synchronized (this.getTreeLock()) {
			if (index == -1) {
				this.appendChild(rowElement);
				return rowElement;
			}
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					Node node = (Node) nl.get(i);
					if ("TR".equalsIgnoreCase(node.getNodeName())) {
						if (trcount == index) {
							this.insertAt(rowElement, i);
							return rowElement;
						}
						trcount++;
					}
				}
			} else {
				this.appendChild(rowElement);
				return rowElement;
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR,
				"Index out of range");
	}

	public void deleteRow(int index) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					Node node = (Node) nl.get(i);
					if ("TR".equalsIgnoreCase(node.getNodeName())) {
						if (trcount == index) {
							this.removeChildAt(i);
							return;
						}
						trcount++;
					}
				}
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR,
				"Index out of range");
	}

	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableRenderState(prevRenderState, this);
	}

	@Override
	public HTMLElement insertRow() {
		// TODO Auto-generated method stub
		return null;
	}
}
