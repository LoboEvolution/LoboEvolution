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
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.dom.HTMLCollection;
import org.lobobrowser.html.dom.HTMLElement;
import org.lobobrowser.html.dom.HTMLTableCaptionElement;
import org.lobobrowser.html.dom.HTMLTableElement;
import org.lobobrowser.html.dom.HTMLTableSectionElement;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlLength;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderState;
import org.lobobrowser.html.style.TableRenderState;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

public class HTMLTableElementImpl extends HTMLAbstractUIElement implements HTMLTableElement {

	private HTMLTableCaptionElement caption;

	private HTMLTableSectionElement tfoot;

	private HTMLTableSectionElement thead;

	public HTMLTableElementImpl() {
		super("TABLE");
	}

	public HTMLTableElementImpl(String name) {
		super(name);
	}

	@Override
	public HTMLElement createCaption() {
		final org.w3c.dom.Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("caption");
	}

	@Override
	protected RenderState createRenderState(RenderState prevRenderState) {
		return new TableRenderState(prevRenderState, this);
	}

	@Override
	public HTMLElement createTFoot() {
		final org.w3c.dom.Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("tfoot");
	}

	@Override
	public HTMLElement createTHead() {
		final org.w3c.dom.Document doc = this.document;
		return doc == null ? null : (HTMLElement) doc.createElement("thead");
	}

	@Override
	public void deleteCaption() {
		removeChildren(new ElementFilter("CAPTION"));
	}

	@Override
	public void deleteRow(int index) throws DOMException {
		synchronized (this.treeLock) {
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					final Node node = (Node) nl.get(i);
					if ("TR".equalsIgnoreCase(node.getNodeName())) {
						if (trcount == index) {
							removeChildAt(i);
							return;
						}
						trcount++;
					}
				}
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
	}

	@Override
	public void deleteTFoot() {
		removeChildren(new ElementFilter("TFOOT"));
	}

	@Override
	public void deleteTHead() {
		removeChildren(new ElementFilter("THEAD"));
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
	public String getBorder() {
		return getAttribute("border");
	}

	@Override
	public HTMLTableCaptionElement getCaption() {
		return this.caption;
	}

	@Override
	public String getCellPadding() {
		return getAttribute("cellpadding");
	}

	@Override
	public String getCellSpacing() {
		return getAttribute("cellspacing");
	}

	@Override
	public String getFrame() {
		return getAttribute("frame");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RenderableContext#getHeightLength()
	 */
	public HtmlLength getHeightLength(int availHeight) {
		try {
			final AbstractCSS2Properties props = getCurrentStyle();
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

	@Override
	public HTMLCollection getRows() {
		return new DescendentHTMLCollection(this, new ElementFilter("TR"), this.treeLock, false);
	}

	@Override
	public String getRules() {
		return getAttribute("rules");
	}

	@Override
	public String getSummary() {
		return getAttribute("summary");
	}

	@Override
	public HTMLCollection getTBodies() {
		return new DescendentHTMLCollection(this, new ElementFilter("TBODY"), this.treeLock, false);
	}

	@Override
	public HTMLTableSectionElement getTFoot() {
		return this.tfoot;
	}

	@Override
	public HTMLTableSectionElement getTHead() {
		return this.thead;
	}

	@Override
	public String getWidth() {
		return getAttribute("width");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RenderableContext#getWidthLength()
	 */
	public HtmlLength getWidthLength(int availWidth) {
		try {
			final AbstractCSS2Properties props = getCurrentStyle();
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
	 * Inserts a row at the index given. If <code>index</code> is <code>-1</code>,
	 * the row is appended as the last row.
	 */
	@Override
	public HTMLElement insertRow(int index) throws DOMException {
		final org.w3c.dom.Document doc = this.document;
		if (doc == null) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Orphan element");
		}
		final HTMLElement rowElement = (HTMLElement) doc.createElement("TR");
		synchronized (this.treeLock) {
			if (index == -1) {
				appendChild(rowElement);
				return rowElement;
			}
			final ArrayList nl = this.nodeList;
			if (nl != null) {
				final int size = nl.size();
				int trcount = 0;
				for (int i = 0; i < size; i++) {
					final Node node = (Node) nl.get(i);
					if ("TR".equalsIgnoreCase(node.getNodeName())) {
						if (trcount == index) {
							insertAt(rowElement, i);
							return rowElement;
						}
						trcount++;
					}
				}
			} else {
				appendChild(rowElement);
				return rowElement;
			}
		}
		throw new DOMException(DOMException.INDEX_SIZE_ERR, "Index out of range");
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
	public void setBorder(String border) {
		setAttribute("border", border);
	}

	@Override
	public void setCaption(HTMLTableCaptionElement caption) throws DOMException {
		this.caption = caption;
	}

	@Override
	public void setCellPadding(String cellPadding) {
		setAttribute("cellpadding", cellPadding);
	}

	@Override
	public void setCellSpacing(String cellSpacing) {
		setAttribute("cellspacing", cellSpacing);
	}

	@Override
	public void setFrame(String frame) {
		setAttribute("frame", frame);
	}

	@Override
	public void setRules(String rules) {
		setAttribute("rules", rules);
	}

	@Override
	public void setSummary(String summary) {
		setAttribute("summary", summary);
	}

	@Override
	public void setTFoot(HTMLTableSectionElement tFoot) throws DOMException {
		this.tfoot = tFoot;
	}

	@Override
	public void setTHead(HTMLTableSectionElement tHead) throws DOMException {
		this.thead = tHead;
	}

	@Override
	public void setWidth(String width) {
		setAttribute("width", width);
	}
}
