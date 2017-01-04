/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
import org.lobobrowser.html.info.BorderInfo;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;

/**
 * The Class IFrameRenderState.
 */
public class IFrameRenderState extends StyleSheetRenderState {

    /**
     * Instantiates a new i frame render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param element
     *            the element
     */
    public IFrameRenderState(RenderState prevRenderState,
            HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getOverflowX()
     */
    @Override
    public int getOverflowX() {
        int overflow = this.overflowX;
        if (overflow != -1) {
            return overflow;
        }
        overflow = super.getOverflowX();
        if (overflow == OVERFLOW_NONE) {
            HTMLElementImpl element = this.element;
            if (element != null) {
                String scrolling = element
                        .getAttribute(HtmlAttributeProperties.SCROLLING);
                if (scrolling != null) {
                    scrolling = scrolling.trim().toLowerCase();
                    if ("no".equals(scrolling)) {
                        overflow = OVERFLOW_HIDDEN;
                    } else if ("yes".equals(scrolling)) {
                        overflow = OVERFLOW_SCROLL;
                    } else if ("auto".equals(scrolling)) {
                        overflow = OVERFLOW_AUTO;
                    }
                }
            }
        }
        this.overflowX = overflow;
        return overflow;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getOverflowY()
     */
    @Override
    public int getOverflowY() {
        int overflow = this.overflowY;
        if (overflow != -1) {
            return overflow;
        }
        overflow = super.getOverflowY();
        if (overflow == OVERFLOW_NONE) {
            HTMLElementImpl element = this.element;
            if (element != null) {
                String scrolling = element
                        .getAttribute(HtmlAttributeProperties.SCROLLING);
                if (scrolling != null) {
                    scrolling = scrolling.trim().toLowerCase();
                    if ("no".equals(scrolling)) {
                        overflow = OVERFLOW_HIDDEN;
                    } else if ("yes".equals(scrolling)) {
                        overflow = OVERFLOW_SCROLL;
                    } else if ("auto".equals(scrolling)) {
                        overflow = OVERFLOW_AUTO;
                    }
                }
            }
        }
        this.overflowY = overflow;
        return overflow;
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getBorderInfo()
     */
    @Override
    public BorderInfo getBorderInfo() {
        BorderInfo binfo = this.borderInfo;
        if (binfo != INVALID_BORDER_INFO) {
            return binfo;
        }
        binfo = super.getBorderInfo();
        if ((binfo == null)
                || ((binfo.getTopStyle() == HtmlValues.BORDER_STYLE_NONE)
                        && (binfo.getBottomStyle() == HtmlValues.BORDER_STYLE_NONE)
                        && (binfo.getLeftStyle() == HtmlValues.BORDER_STYLE_NONE) && (binfo
                                .getRightStyle() == HtmlValues.BORDER_STYLE_NONE))) {
            if (binfo == null) {
                binfo = new BorderInfo();
            }
            HTMLElementImpl element = this.element;
            if (element != null) {
                String border = element.getAttribute(HtmlAttributeProperties.FRAMEBORDER);
                int value = 0;
                if (border != null) {
                    border = border.trim();
                    value = HtmlValues.getPixelSize(border, this, 0);
                }
                
                HtmlInsets borderInsets = new HtmlInsets();
                borderInsets.top = borderInsets.left = borderInsets.right = borderInsets.bottom = (value != 0 ? 1 : 0);
                borderInsets.topType = borderInsets.leftType = borderInsets.rightType = borderInsets.bottomType = HtmlInsets.TYPE_PIXELS;
                binfo.setInsets(borderInsets);
                if (binfo.getTopColor() == null) {
                    binfo.setTopColor(Color.DARK_GRAY);
                }
                if (binfo.getLeftColor() == null) {
                    binfo.setLeftColor(Color.DARK_GRAY);
                }
                if (binfo.getRightColor() == null) {
                    binfo.setRightColor(Color.LIGHT_GRAY);
                }
                if (binfo.getBottomColor() == null) {
                    binfo.setBottomColor(Color.LIGHT_GRAY);
                }
                if (value != 0) {
                    binfo.setTopStyle(HtmlValues.BORDER_STYLE_SOLID);
                    binfo.setLeftStyle(HtmlValues.BORDER_STYLE_SOLID);
                    binfo.setRightStyle(HtmlValues.BORDER_STYLE_SOLID);
                    binfo.setBottomStyle(HtmlValues.BORDER_STYLE_SOLID);
                }
            }
        }
        this.borderInfo = binfo;
        return binfo;
    }
}
