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
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.domimpl;

import java.util.ArrayList;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.HtmlProperties;
import org.lobobrowser.html.dombl.DescendentHTMLCollection;
import org.lobobrowser.html.domfilter.ElementTableAttributeFilter;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.renderstate.TableRenderState;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlLength;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.w3c.html.HTMLCollection;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.w3c.html.HTMLTableCaptionElement;
import org.lobobrowser.w3c.html.HTMLTableElement;
import org.lobobrowser.w3c.html.HTMLTableSectionElement;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

/**
 * The Class HTMLTableElementImpl.
 */
public class HTMLTableElementImpl extends HTMLAbstractUIElement implements
HTMLTableElement {

    /**
     * Instantiates a new HTML table element impl.
     */
    public HTMLTableElementImpl() {
        super(HtmlProperties.TABLE);
    }

    /**
     * Instantiates a new HTML table element impl.
     *
     * @param name
     *            the name
     */
    public HTMLTableElementImpl(String name) {
        super(name);
    }

    /** The caption. */
    private HTMLTableCaptionElement caption;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getCaption()
     */
    @Override
    public HTMLTableCaptionElement getCaption() {
        return this.caption;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.html.HTMLTableElement#setCaption(org.lobobrowser.html
     * .w3c.HTMLTableCaptionElement)
     */
    @Override
    public void setCaption(HTMLTableCaptionElement caption) throws DOMException {
        this.caption = caption;
    }

    /** The thead. */
    private HTMLTableSectionElement thead;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getTHead()
     */
    @Override
    public HTMLTableSectionElement getTHead() {
        return this.thead;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.html.HTMLTableElement#setTHead(org.lobobrowser.w3c.html
     * .HTMLTableSectionElement)
     */
    @Override
    public void setTHead(HTMLTableSectionElement tHead) throws DOMException {
        this.thead = tHead;
    }

    /** The tfoot. */
    private HTMLTableSectionElement tfoot;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getTFoot()
     */
    @Override
    public HTMLTableSectionElement getTFoot() {
        return this.tfoot;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.html.HTMLTableElement#setTFoot(org.lobobrowser.w3c.html
     * .HTMLTableSectionElement)
     */
    @Override
    public void setTFoot(HTMLTableSectionElement tFoot) throws DOMException {
        this.tfoot = tFoot;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getRows()
     */
    @Override
    public HTMLCollection getRows() {
        return new DescendentHTMLCollection(this,
                new ElementTableAttributeFilter(HtmlProperties.TR),
                this.getTreeLock(), false);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getTBodies()
     */
    @Override
    public HTMLCollection getTBodies() {
        return new DescendentHTMLCollection(this,
                new ElementTableAttributeFilter(HtmlProperties.TBODY),
                this.getTreeLock(), false);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getAlign()
     */
    @Override
    public String getAlign() {
        return this.getAttribute(HtmlAttributeProperties.ALIGN);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#setAlign(java.lang.String)
     */
    @Override
    public void setAlign(String align) {
        this.setAttribute(HtmlAttributeProperties.ALIGN, align);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getBgColor()
     */
    @Override
    public String getBgColor() {
        return this.getAttribute(HtmlAttributeProperties.BGCOLOR);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#setBgColor(java.lang.String)
     */
    @Override
    public void setBgColor(String bgColor) {
        this.setAttribute(HtmlAttributeProperties.BGCOLOR, bgColor);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getBorder()
     */
    @Override
    public String getBorder() {
        return this.getAttribute(HtmlAttributeProperties.BORDER);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#setBorder(java.lang.String)
     */
    @Override
    public void setBorder(String border) {
        this.setAttribute(HtmlAttributeProperties.BORDER, border);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getCellPadding()
     */
    @Override
    public String getCellPadding() {
        return this.getAttribute(HtmlAttributeProperties.CELLPADDING);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.html.HTMLTableElement#setCellPadding(java.lang.String)
     */
    @Override
    public void setCellPadding(String cellPadding) {
        this.setAttribute(HtmlAttributeProperties.CELLPADDING, cellPadding);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getCellSpacing()
     */
    @Override
    public String getCellSpacing() {
        return this.getAttribute(HtmlAttributeProperties.CELLSPACING);
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.w3c.html.HTMLTableElement#setCellSpacing(java.lang.String)
     */
    @Override
    public void setCellSpacing(String cellSpacing) {
        this.setAttribute(HtmlAttributeProperties.CELLSPACING, cellSpacing);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getFrame()
     */
    @Override
    public String getFrame() {
        return this.getAttribute(HtmlAttributeProperties.FRAME);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#setFrame(java.lang.String)
     */
    @Override
    public void setFrame(String frame) {
        this.setAttribute(HtmlAttributeProperties.FRAME, frame);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getRules()
     */
    @Override
    public String getRules() {
        return this.getAttribute(HtmlAttributeProperties.RULES);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#setRules(java.lang.String)
     */
    @Override
    public void setRules(String rules) {
        this.setAttribute(HtmlAttributeProperties.RULES, rules);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getSummary()
     */
    @Override
    public String getSummary() {
        return this.getAttribute(HtmlAttributeProperties.SUMMARY);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#setSummary(java.lang.String)
     */
    @Override
    public void setSummary(String summary) {
        this.setAttribute(HtmlAttributeProperties.SUMMARY, summary);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#getWidth()
     */
    @Override
    public String getWidth() {
        return this.getAttribute(HtmlAttributeProperties.WIDTH);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#setWidth(java.lang.String)
     */
    @Override
    public void setWidth(String width) {
        this.setAttribute(HtmlAttributeProperties.WIDTH, width);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.render.RenderableContext#getHeightLength()
     */
    /**
     * Gets the height length.
     *
     * @param availHeight
     *            the avail height
     * @return the height length
     */
    public HtmlLength getHeightLength(int availHeight) {
	    AbstractCSS2Properties props = this.getCurrentStyle();
	    String heightText = props == null ? null : props.getHeight();
	    if (heightText == null) {
	        return new HtmlLength(
	                this.getAttribute(HtmlAttributeProperties.HEIGHT));
	    } else {
	        return new HtmlLength(HtmlValues.getPixelSize(heightText,
	                this.getRenderState(), 0, availHeight));
	    }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.render.RenderableContext#getWidthLength()
     */
    /**
     * Gets the width length.
     *
     * @param availWidth
     *            the avail width
     * @return the width length
     */
    public HtmlLength getWidthLength(int availWidth) {
	    AbstractCSS2Properties props = this.getCurrentStyle();
	    String widthText = props == null ? null : props.getWidth();
	    if (widthText == null) {
	        return new HtmlLength(
	                this.getAttribute(HtmlAttributeProperties.WIDTH));
	    } else {
	        return new HtmlLength(HtmlValues.getPixelSize(widthText,
	                this.getRenderState(), 0, availWidth));
	    }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#createTHead()
     */
    @Override
    public HTMLElement createTHead() {
        org.w3c.dom.Document doc = this.document;
        return doc == null ? null : (HTMLElement) doc.createElement("thead");
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#deleteTHead()
     */
    @Override
    public void deleteTHead() {
        this.removeChildren(new ElementTableAttributeFilter("THEAD"));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#createTFoot()
     */
    @Override
    public HTMLElement createTFoot() {
        org.w3c.dom.Document doc = this.document;
        return doc == null ? null : (HTMLElement) doc.createElement("tfoot");
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#deleteTFoot()
     */
    @Override
    public void deleteTFoot() {
        this.removeChildren(new ElementTableAttributeFilter("TFOOT"));
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#createCaption()
     */
    @Override
    public HTMLElement createCaption() {
        org.w3c.dom.Document doc = this.document;
        return doc == null ? null : (HTMLElement) doc.createElement("caption");
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#deleteCaption()
     */
    @Override
    public void deleteCaption() {
        this.removeChildren(new ElementTableAttributeFilter("CAPTION"));
    }

    /**
     * Inserts a row at the index given. If <code>index</code> is
     * <code>-1</code>, the row is appended as the last row.
     *
     * @param index
     *            the index
     * @return the HTML element
     * @throws DOMException
     *             the DOM exception
     */
    @Override
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
                    Node node = nl.get(i);
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

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#deleteRow(int)
     */
    @Override
    public void deleteRow(int index) throws DOMException {
        synchronized (this.getTreeLock()) {
            ArrayList<Node> nl = this.nodeList;
            if (nl != null) {
                int size = nl.size();
                int trcount = 0;
                for (int i = 0; i < size; i++) {
                    Node node = nl.get(i);
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

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.domimpl.HTMLElementImpl#createRenderState(org.lobobrowser
     * .html.renderstate.RenderState)
     */
    @Override
    protected RenderState createRenderState(RenderState prevRenderState) {
        return new TableRenderState(prevRenderState, this);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.w3c.html.HTMLTableElement#insertRow()
     */
    @Override
    public HTMLElement insertRow() {
        // TODO Auto-generated method stub
        return null;
    }

	@Override
	public HTMLElement createTBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HTMLElement deleteTBody() {
		// TODO Auto-generated method stub
		return null;
	}
}
