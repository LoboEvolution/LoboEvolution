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
package org.lobobrowser.html.renderstate;

import java.awt.Color;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.domimpl.HTMLTableCellElementImpl;
import org.lobobrowser.html.domimpl.HTMLTableRowElementImpl;
import org.lobobrowser.html.info.BackgroundInfo;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.html.style.RenderThreadState;
import org.lobobrowser.util.gui.ColorFactory;
import org.lobobrowser.w3c.html.HTMLElement;
import org.lobobrowser.w3c.html.HTMLTableElement;
import org.w3c.dom.css.CSS2Properties;

/**
 * The Class TableCellRenderState.
 */
public class TableCellRenderState extends DisplayRenderState {

    /**
     * Instantiates a new table cell render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param element
     *            the element
     */
    public TableCellRenderState(RenderState prevRenderState,
            HTMLElementImpl element) {
        super(prevRenderState, element, RenderState.DISPLAY_TABLE_CELL);
    }

    /** The align x percent. */
    private int alignXPercent = -1;

    /** The align y percent. */
    private int alignYPercent = -1;

    /** The background info. */
    private BackgroundInfo backgroundInfo = INVALID_BACKGROUND_INFO;

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#invalidate()
     */
    @Override
    public void invalidate() {
        super.invalidate();
        this.alignXPercent = -1;
        this.alignYPercent = -1;
        this.backgroundInfo = INVALID_BACKGROUND_INFO;
        this.paddingInsets = INVALID_INSETS;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderstate.StyleSheetRenderState#getAlignXPercent()
     */
    @Override
    public int getAlignXPercent() {
        int axp = this.alignXPercent;
        if (axp != -1) {
            return axp;
        }
        CSS2Properties props = this.getCssProperties();
        if (props != null) {
            String textAlign = props.getTextAlign();
            if ((textAlign != null) && (textAlign.length() != 0)) {
                return super.getAlignXPercent();
            }
        }
        // Parent already knows about HtmlAttributeProperties.ALIGN attribute,
        // but override because of
        // TH.
        String align = this.element.getAttribute(HtmlAttributeProperties.ALIGN);
        HTMLElement element = this.element;
        HTMLElement rowElement = null;
        Object parent = element.getParentNode();
        if (parent instanceof HTMLElement) {
            rowElement = (HTMLElement) parent;
        }
        if ((align == null) || (align.length() == 0)) {
            if (rowElement != null) {
                align = rowElement.getAttribute(HtmlAttributeProperties.ALIGN);
                if ((align != null) && (align.length() == 0)) {
                    align = null;
                }
            } else {
                align = null;
            }
        }
        if (align == null) {
            if ("TH".equalsIgnoreCase(element.getNodeName())) {
                axp = 50;
            } else {
                axp = 0;
            }
        } else if ("center".equalsIgnoreCase(align)
                || "middle".equalsIgnoreCase(align)) {
            axp = 50;
        } else if ("left".equalsIgnoreCase(align)) {
            axp = 0;
        } else if ("right".equalsIgnoreCase(align)) {
            axp = 100;
        } else {
            // TODO: justify, etc.
            axp = 0;
        }
        this.alignXPercent = axp;
        return axp;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderstate.StyleSheetRenderState#getAlignYPercent()
     */
    @Override
    public int getAlignYPercent() {
        int ayp = this.alignYPercent;
        if (ayp != -1) {
            return ayp;
        }
        CSS2Properties props = this.getCssProperties();
        if (props != null) {
            String textAlign = props.getVerticalAlign();
            if ((textAlign != null) && (textAlign.length() != 0)) {
                return super.getAlignYPercent();
            }
        }
        String valign = this.element
                .getAttribute(HtmlAttributeProperties.VALIGN);
        HTMLElement element = this.element;
        HTMLElement rowElement = null;
        Object parent = element.getParentNode();
        if (parent instanceof HTMLElement) {
            rowElement = (HTMLElement) parent;
        }
        if ((valign == null) || (valign.length() == 0)) {
            if (rowElement != null) {
                valign = rowElement
                        .getAttribute(HtmlAttributeProperties.VALIGN);
                if ((valign != null) && (valign.length() == 0)) {
                    valign = null;
                }
            } else {
                valign = null;
            }
        }
        if (valign == null) {
            ayp = 50;
        } else if ("top".equalsIgnoreCase(valign)) {
            ayp = 0;
        } else if ("middle".equalsIgnoreCase(valign)
                || "center".equalsIgnoreCase(valign)) {
            ayp = 50;
        } else if ("bottom".equalsIgnoreCase(valign)) {
            ayp = 100;
        } else {
            // TODO: baseline, etc.
            ayp = 50;
        }
        this.alignYPercent = ayp;
        return ayp;
    }

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderstate.StyleSheetRenderState#getBackgroundInfo()
     */
    @Override
    public BackgroundInfo getBackgroundInfo() {
        BackgroundInfo binfo = this.backgroundInfo;
        if (binfo != INVALID_BACKGROUND_INFO) {
            return binfo;
        }
        // Apply style based on deprecated attributes.
        binfo = super.getBackgroundInfo();
        HTMLTableCellElementImpl element = (HTMLTableCellElementImpl) this.element;
        HTMLTableRowElementImpl rowElement = null;
        Object parentNode = element.getParentNode();
        if (parentNode instanceof HTMLTableRowElementImpl) {
            rowElement = (HTMLTableRowElementImpl) parentNode;
        }
        if ((binfo == null) || (binfo.getBackgroundColor() == null)) {
            String bgColor = element.getBgColor();
            if ((bgColor == null) || "".equals(bgColor)) {
                if (rowElement != null) {
                    bgColor = rowElement.getBgColor();
                }
            }
            if ((bgColor != null) && !"".equals(bgColor)) {
                Color bgc = ColorFactory.getInstance().getColor(bgColor);
                if (binfo == null) {
                    binfo = new BackgroundInfo();
                }
                binfo.setBackgroundColor(bgc);
            }
        }
        
        this.backgroundInfo = binfo;
        return binfo;
    }

    /** Gets the table element.
	 *
	 * @return the table element
	 */
    private HTMLTableElement getTableElement() {
        org.w3c.dom.Node ancestor = this.element.getParentNode();
        while ((ancestor != null) && !(ancestor instanceof HTMLTableElement)) {
            ancestor = ancestor.getParentNode();
        }
        return (HTMLTableElement) ancestor;
    }

    /** The padding insets. */
    private HtmlInsets paddingInsets = INVALID_INSETS;

    /*
     * (non-Javadoc)
     * @see
     * org.lobobrowser.html.renderstate.StyleSheetRenderState#getPaddingInsets()
     */
    @Override
    public HtmlInsets getPaddingInsets() {
    	
    	HtmlInsets insets = this.paddingInsets;
        if (insets != INVALID_INSETS) {
            return insets;
        }else{
        	
            HTMLTableElement tableElement = this.getTableElement();
            if (tableElement == null) {
                return null;
            }
            
            String cellPaddingText = tableElement.getAttribute(HtmlAttributeProperties.CELLPADDING);
            if (cellPaddingText != null && cellPaddingText.length() != 0) {
                cellPaddingText = cellPaddingText.trim();
                int cellPadding = HtmlValues.getPixelSize(cellPaddingText, this, 0);
                int cellPaddingType = HtmlInsets.TYPE_PIXELS;
                
                if (cellPaddingText.endsWith("%")) {
                    cellPaddingType = HtmlInsets.TYPE_PERCENT;
                }
                
                insets = new HtmlInsets();
                insets.top = insets.left = insets.right = insets.bottom = cellPadding;
                insets.topType = insets.leftType = insets.rightType = insets.bottomType = cellPaddingType;
			} else {
				insets = super.getPaddingInsets();
			}
        }
        this.paddingInsets = insets;
        return insets;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getWhiteSpace()
     */
    @Override
    public int getWhiteSpace() {
        // Overrides super.
        if (RenderThreadState.getState().overrideNoWrap) {
            return WS_NOWRAP;
        }
        Integer ws = this.iWhiteSpace;
        if (ws != null) {
            return ws.intValue();
        }
        AbstractCSS2Properties props = this.getCssProperties();
        String whiteSpaceText = props == null ? null : props.getWhiteSpace();
        int wsValue;
        if (whiteSpaceText == null) {
            HTMLElementImpl element = this.element;
            if ((element != null) && element.getAttributeAsBoolean("nowrap")) {
                wsValue = WS_NOWRAP;
            } else {
                RenderState prs = this.prevRenderState;
                if (prs != null) {
                    wsValue = prs.getWhiteSpace();
                } else {
                    wsValue = WS_NORMAL;
                }
            }
        } else {
            String whiteSpaceTextTL = whiteSpaceText.toLowerCase();
            if ("nowrap".equals(whiteSpaceTextTL)) {
                wsValue = WS_NOWRAP;
            } else if ("pre".equals(whiteSpaceTextTL)) {
                wsValue = WS_PRE;
            } else {
                wsValue = WS_NORMAL;
            }
        }
        if (wsValue == WS_NOWRAP) {
            // In table cells, if the width is defined as an absolute value,
            // nowrap has no effect (IE and FireFox behavior).
            HTMLElementImpl element = this.element;
            String width = props == null ? null : props.getWidth();
            if (width == null) {
                width = element.getAttribute(HtmlAttributeProperties.WIDTH);
                if ((width != null) && (width.length() > 0)
                        && !width.endsWith("%")) {
                    wsValue = WS_NORMAL;
                }
            } else {
                if (!width.trim().endsWith("%")) {
                    wsValue = WS_NORMAL;
                }
            }
        }
        this.iWhiteSpace = new Integer(wsValue);
        return wsValue;
    }

}
