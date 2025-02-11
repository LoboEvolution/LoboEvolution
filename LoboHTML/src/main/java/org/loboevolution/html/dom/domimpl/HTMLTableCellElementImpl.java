/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
 * Created on Dec 4, 2005
 */
package org.loboevolution.html.dom.domimpl;

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.TableCellRenderState;
import org.loboevolution.html.style.HtmlValues;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>HTMLTableCellElementImpl class.</p>
 */
public class HTMLTableCellElementImpl extends HTMLElementImpl implements HTMLTableCellElement {

    private int index = -1;

	/**
	 * <p>Constructor for HTMLTableCellElementImpl.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 */
	public HTMLTableCellElementImpl(final String name) {
		super(name);
	}

	/** {@inheritDoc} */
	@Override
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return new TableCellRenderState(prevRenderState, this);
	}

	/** {@inheritDoc} */
	@Override
	public String getAbbr() {
		return getAttribute("abbr");
	}

	/** {@inheritDoc} */
	@Override
	public String getAlign() {
		return getAttribute("align");
	}

	/** {@inheritDoc} */
	@Override
	public String getAxis() {
		return getAttribute("axis");
	}

	/** {@inheritDoc} */
	@Override
	public String getBgColor() {
		return getAttribute("bgcolor");
	}

	/** {@inheritDoc} */
	@Override
	public int getCellIndex() {
		if (index >= 0) {
			return index;
		} else {
			final AtomicInteger index = new AtomicInteger(-1);
			final AtomicInteger count = new AtomicInteger(-1);
			if (getParentNode() != null) {
				final NodeListImpl childNodes = (NodeListImpl) getParentNode().getChildNodes();
				childNodes.forEach(node -> {
					if (node instanceof HTMLTableCellElementImpl cell) {
                        count.incrementAndGet();
						if (cell.getId().equals(getId()))
							index.set(count.get());
					}
				});
			}
			return index.get();
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getCh() {
		return getAttribute("ch");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("choff");
	}

	/** {@inheritDoc} */
	@Override
	public int getColSpan() {
		final String colSpanText = getAttribute("colspan");
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(colSpanText, null, doc.getDefaultView(), 1);
	}

	/** {@inheritDoc} */
	@Override
	public String getHeaders() {
		return getAttribute("headers");
	}

	/** {@inheritDoc} */
	@Override
	public String getHeight() {
		return getAttribute("height");
	}

	/** {@inheritDoc} */
	@Override
	public boolean isNoWrap() {
		return "nowrap".equalsIgnoreCase(getAttribute("nowrap"));
	}

	/** {@inheritDoc} */
	@Override
	public int getRowSpan() {
		final String rowSpanText = getAttribute("rowspan");
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		return HtmlValues.getPixelSize(rowSpanText, null, doc.getDefaultView(), 1);
	}

	/** {@inheritDoc} */
	@Override
	public String getScope() {
		return getAttribute("scope");
	}

	/** {@inheritDoc} */
	@Override
	public String getvAlign() {
		return getAttribute("valign");
	}

	/** {@inheritDoc} */
	@Override
	public String getWidth() {
		return getAttribute("width");
	}
	
	/**
	 * <p>Setter for the field <code>index</code>.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 */
	protected void setIndex(final int index) {
		this.index = index;
	}

	/** {@inheritDoc} */
	@Override
	public void setAbbr(final String abbr) {
		setAttribute("abbr", abbr);
	}

	/** {@inheritDoc} */
	@Override
	public void setAlign(final String align) {
		setAttribute("align", align);
	}

	/** {@inheritDoc} */
	@Override
	public void setAxis(final String axis) {
		setAttribute("axis", axis);
	}

	/** {@inheritDoc} */
	@Override
	public void setBgColor(final String bgColor) {
		setAttribute("bgcolor", bgColor);
	}

	/** {@inheritDoc} */
	@Override
	public void setCh(final String ch) {
		setAttribute("ch", ch);
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(final String chOff) {
		setAttribute("choff", chOff);
	}

	/** {@inheritDoc} */
	@Override
	public void setColSpan(final Object colSpan) {
		if (colSpan!= null && Strings.isNumeric(colSpan.toString()) && Double.parseDouble(colSpan.toString()) > 0) {
			setAttribute("colspan", String.valueOf(colSpan));
		} else {
			setAttribute("colspan", "1");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setHeaders(final String headers) {
		setAttribute("headers", headers);
	}

	/** {@inheritDoc} */
	@Override
	public void setHeight(final String height) {
		setAttribute("height", height);
	}

	/** {@inheritDoc} */
	@Override
	public void setNoWrap(final boolean noWrap) {
		setAttribute("nowrap", noWrap ? "nowrap" : null);
	}

	/** {@inheritDoc} */
	@Override
	public void setRowSpan(final Object rowSpan) {
		if (rowSpan != null && Strings.isNumeric(rowSpan.toString()) && Double.parseDouble(rowSpan.toString()) > 0) {
			setAttribute("rowspan", String.valueOf(rowSpan));
		} else {
			setAttribute("rowspan", "1");
		}
	}

	/** {@inheritDoc} */
	@Override
	public void setScope(final String scope) {
		setAttribute("scope", scope);
	}

	/** {@inheritDoc} */
	@Override
	public void setvAlign(final String vAlign) {
		setAttribute("valign", vAlign);
	}

	/** {@inheritDoc} */
	@Override
	public void setWidth(final String width) {
		setAttribute("width", width);
	}

	/** {@inheritDoc} */
	@Override
	public Integer getOffsetWidth() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Integer getClientWidth() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableCellElement]";
	}
}
