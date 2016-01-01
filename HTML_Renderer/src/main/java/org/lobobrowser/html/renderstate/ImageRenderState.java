/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.renderstate;

import java.awt.Color;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLElementImpl;
import org.lobobrowser.html.info.BorderInfo;
import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlInsets;
import org.lobobrowser.html.style.HtmlValues;

/**
 * The Class ImageRenderState.
 */
public class ImageRenderState extends StyleSheetRenderState {

    /**
     * Instantiates a new image render state.
     *
     * @param prevRenderState
     *            the prev render state
     * @param element
     *            the element
     */
    public ImageRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.renderstate.StyleSheetRenderState#getMarginInsets()
     */
    @Override
    public HtmlInsets getMarginInsets() {
        HtmlInsets mi = this.marginInsets;
        if (mi != INVALID_INSETS) {
            return mi;
        }
        AbstractCSS2Properties props = this.getCssProperties();
        if (props == null) {
            mi = null;
        } else {
            mi = HtmlValues.getMarginInsets(props, this);
        }
        if (mi == null) {
            int hspace = 0;
            int vspace = 0;
            boolean createNew = false;
            String hspaceText = this.element
                    .getAttribute(HtmlAttributeProperties.HSPACE);
            if ((hspaceText != null) && (hspaceText.length() != 0)) {
                createNew = true;
                try {
                    hspace = Integer.parseInt(hspaceText);
                } catch (NumberFormatException nfe) {
                    // TODO: Percentages?
                }
            }
            String vspaceText = this.element
                    .getAttribute(HtmlAttributeProperties.VSPACE);
            if ((vspaceText != null) && (vspaceText.length() != 0)) {
                createNew = true;
                try {
                    vspace = Integer.parseInt(vspaceText);
                } catch (NumberFormatException nfe) {
                    // TODO: Percentages?
                }
            }
            if (createNew) {
                mi = new HtmlInsets();
                mi.top = vspace;
                mi.topType = HtmlInsets.TYPE_PIXELS;
                mi.bottom = vspace;
                mi.bottomType = HtmlInsets.TYPE_PIXELS;
                mi.left = hspace;
                mi.leftType = HtmlInsets.TYPE_PIXELS;
                mi.right = hspace;
                mi.rightType = HtmlInsets.TYPE_PIXELS;
            }
        }
        this.marginInsets = mi;
        return mi;
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
                String border = element
                        .getAttribute(HtmlAttributeProperties.BORDER);
                if (border != null) {
                    border = border.trim();
                    int value;
                    int valueType;
                    if (border.endsWith("%")) {
                        valueType = HtmlInsets.TYPE_PERCENT;
                        try {
                            value = Integer.parseInt(border.substring(0,
                                    border.length() - 1));
                        } catch (NumberFormatException nfe) {
                            value = 0;
                        }
                    } else {
                        valueType = HtmlInsets.TYPE_PIXELS;
                        try {
                            value = Integer.parseInt(border);
                        } catch (NumberFormatException nfe) {
                            value = 0;
                        }
                    }
                    HtmlInsets borderInsets = new HtmlInsets();
                    borderInsets.top = borderInsets.left = borderInsets.right = borderInsets.bottom = value;
                    borderInsets.topType = borderInsets.leftType = borderInsets.rightType = borderInsets.bottomType = valueType;
                    binfo.setInsets(borderInsets);
                    if (binfo.getTopColor() == null) {
                        binfo.setTopColor(Color.BLACK);
                    }
                    if (binfo.getLeftColor() == null) {
                        binfo.setLeftColor(Color.BLACK);
                    }
                    if (binfo.getRightColor() == null) {
                        binfo.setRightColor(Color.BLACK);
                    }
                    if (binfo.getBottomColor() == null) {
                        binfo.setBottomColor(Color.BLACK);
                    }
                    if (value != 0) {
                        binfo.setTopStyle(HtmlValues.BORDER_STYLE_SOLID);
                        binfo.setLeftStyle(HtmlValues.BORDER_STYLE_SOLID);
                        binfo.setRightStyle(HtmlValues.BORDER_STYLE_SOLID);
                        binfo.setBottomStyle(HtmlValues.BORDER_STYLE_SOLID);
                    }
                }
            }
        }
        this.borderInfo = binfo;
        return binfo;
    }
}
