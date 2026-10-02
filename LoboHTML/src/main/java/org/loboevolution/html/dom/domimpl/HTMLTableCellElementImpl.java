/*
 * MIT License
 *
 * Copyright (c) 2014 - 2026 LoboEvolution
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
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.dom.HTMLTableCellElement;
import org.loboevolution.html.dom.HTMLTableElement;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Node;
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
        return getAttribute("char");
	}

	/** {@inheritDoc} */
	@Override
	public String getChOff() {
		return getAttribute("charoff");
	}

	/** {@inheritDoc} */
	@Override
	public int getColSpan() {
		final String colSpanText = getAttribute("colspan");
		final HTMLDocumentImpl doc =  (HTMLDocumentImpl)this.document;
		final int value = HtmlValues.getPixelSize(colSpanText, null, doc.getDefaultView(), 1);
		return Math.max(1, value);
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
		final int value = HtmlValues.getPixelSize(rowSpanText, null, doc.getDefaultView(), 1);
		return Math.max(1, value);
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
		setAttribute("char", ch);
	}

	/** {@inheritDoc} */
	@Override
	public void setChOff(final String chOff) {
		setAttribute("charoff", chOff);
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
		setAttribute("nowrap", noWrap ? "" : null);
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
		try {
			return Integer.valueOf(calculateOffsetWidth());
		} catch (Exception e) {
			return 0;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Integer getOffsetHeight() {
		return Integer.valueOf(calculateOffsetHeight());
	}

	/** {@inheritDoc} */
	@Override
	public Integer getClientWidth() {
		return Integer.valueOf(calculateClientWidth());
	}

	/** {@inheritDoc} */
	@Override
	public int getClientHeight() {
		return calculateClientHeight();
	}

	int calculateOffsetWidth() {
		try {
			final CSSStyleDeclaration currentStyle = getCurrentStyle();
			if (currentStyle == null) {
				return 0;
			}

			final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
			final String width = currentStyle.getWidth();
			final int contentWidth = HtmlValues.getPixelSize(width, getRenderState(), doc.getDefaultView(), 0);
			final int paddingLeft = HtmlValues.getPixelSize(currentStyle.getPaddingLeft(), getRenderState(), doc.getDefaultView(), 0);
			final int paddingRight = HtmlValues.getPixelSize(currentStyle.getPaddingRight(), getRenderState(), doc.getDefaultView(), 0);
			final int borderLeft = HtmlValues.getPixelSize(currentStyle.getBorderLeftWidth(), getRenderState(), doc.getDefaultView(), 0);
			final int borderRight = HtmlValues.getPixelSize(currentStyle.getBorderRightWidth(), getRenderState(), doc.getDefaultView(), 0);

			final boolean isBorderCollapse = isBorderCollapse();

			if (isBorderCollapse) {
				return contentWidth + paddingLeft + paddingRight + borderLeft / 2 + borderRight / 2;
			} else {
				return contentWidth + paddingLeft + paddingRight + borderLeft + borderRight;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	int calculateOffsetHeight() {
		final CSSStyleDeclaration currentStyle = getCurrentStyle();
		if (currentStyle == null) {
			return 0;
		}

		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final int contentHeight = HtmlValues.getPixelSize(currentStyle.getHeight(), getRenderState(), doc.getDefaultView(), 0);
		final int paddingTop = HtmlValues.getPixelSize(currentStyle.getPaddingTop(), getRenderState(), doc.getDefaultView(), 0);
		final int paddingBottom = HtmlValues.getPixelSize(currentStyle.getPaddingBottom(), getRenderState(), doc.getDefaultView(), 0);
		final int borderTop = HtmlValues.getPixelSize(currentStyle.getBorderTopWidth(), getRenderState(), doc.getDefaultView(), 0);
		final int borderBottom = HtmlValues.getPixelSize(currentStyle.getBorderBottomWidth(), getRenderState(), doc.getDefaultView(), 0);

		final boolean isBorderCollapse = isBorderCollapse();

		if (isBorderCollapse) {
			return contentHeight + paddingTop + paddingBottom + borderTop / 2 + borderBottom / 2;
		} else {
			return contentHeight + paddingTop + paddingBottom + borderTop + borderBottom;
		}
	}

	int calculateClientWidth() {
		final CSSStyleDeclaration currentStyle = getCurrentStyle();
		if (currentStyle == null) {
			return 0;
		}

		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final int contentWidth = HtmlValues.getPixelSize(currentStyle.getWidth(), getRenderState(), doc.getDefaultView(), 0);
		final int paddingLeft = HtmlValues.getPixelSize(currentStyle.getPaddingLeft(), getRenderState(), doc.getDefaultView(), 0);
		final int paddingRight = HtmlValues.getPixelSize(currentStyle.getPaddingRight(), getRenderState(), doc.getDefaultView(), 0);

		return contentWidth + paddingLeft + paddingRight;
	}

	int calculateClientHeight() {
		final CSSStyleDeclaration currentStyle = getCurrentStyle();
		if (currentStyle == null) {
			return 0;
		}

		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		final int contentHeight = HtmlValues.getPixelSize(currentStyle.getHeight(), getRenderState(), doc.getDefaultView(), 0);
		final int paddingTop = HtmlValues.getPixelSize(currentStyle.getPaddingTop(), getRenderState(), doc.getDefaultView(), 0);
		final int paddingBottom = HtmlValues.getPixelSize(currentStyle.getPaddingBottom(), getRenderState(), doc.getDefaultView(), 0);

		return contentHeight + paddingTop + paddingBottom;
	}

	boolean isBorderCollapse() {
		final Object parentNode = getParentNode();
		if (parentNode != null) {
			Node ancestor = (Node) parentNode;
			while (ancestor != null && !(ancestor instanceof HTMLTableElement)) {
				ancestor = ancestor.getParentNode();
			}
			if (ancestor instanceof HTMLTableElement table) {
				final String borderCollapse = table.getCurrentStyle().getBorderCollapse();
				return "collapse".equalsIgnoreCase(borderCollapse);
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return "[object HTMLTableCellElement]";
	}
}
