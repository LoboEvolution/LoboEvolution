/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.BorderInfo;

import java.awt.*;

/**
 * <p>FieldsetRenderState class.</p>
 */
public class FieldsetRenderState extends BlockRenderState {

    /**
     * <p>Constructor for BlockRenderState.</p>
     *
     * @param prevRenderState a {@link RenderState} object.
     * @param element         a {@link HTMLElementImpl} object.
     */
    public FieldsetRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public HtmlInsets getMarginInsets() {
        HtmlInsets insets = this.marginInsets;
        if (insets != INVALID_INSETS) {
            return insets;
        }
        insets = super.getMarginInsets();
        if (insets == null || insets.htmlInsetsIsVoid()) {
            insets = getDefaultMarginInsets();
        }
        this.marginInsets = insets;
        return insets;
    }

    @Override
    public HtmlInsets getPaddingInsets() {
        HtmlInsets insets = this.paddingInsets;
        if (insets != INVALID_INSETS) {
            return insets;
        }
        insets = super.getPaddingInsets();
        if (insets == null || insets.htmlInsetsIsVoid()) {
            insets = getDefaultPaddingInsets();
        }
        this.marginInsets = insets;
        return insets;
    }

    /** {@inheritDoc} */
    @Override
    public BorderInfo getBorderInfo() {
        BorderInfo binfo = this.borderInfo;
        if (binfo != INVALID_BORDER_INFO) {
            return binfo;
        }
        binfo = super.getBorderInfo();
        if (binfo == null || binfo.borderInfoIsVoid()) {

            if (binfo == null) {
                binfo = new BorderInfo();
            }

            binfo.setInsets(getDefaultBordernsets());
            binfo.setTopColor(Color.DARK_GRAY);
            binfo.setLeftColor(Color.DARK_GRAY);
            binfo.setRightColor(Color.DARK_GRAY);
            binfo.setBottomColor(Color.DARK_GRAY);
            binfo.setTopStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setLeftStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setRightStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setBottomStyle(BorderInsets.BORDER_STYLE_SOLID);

        }
        this.borderInfo = binfo;
        return binfo;
    }


    private HtmlInsets getDefaultMarginInsets() {
        HtmlInsets insets = new HtmlInsets();
        final int leftRight = HtmlValues.getPixelSize("2px", null, element.getDocumentNode().getDefaultView(), -1);
        insets.setLeft(leftRight);
        insets.setRight(leftRight);
        insets.setLeftType(HtmlInsets.TYPE_PIXELS);
        insets.setRightType(HtmlInsets.TYPE_PIXELS);
        return insets;
    }

    private HtmlInsets getDefaultPaddingInsets() {
        HtmlInsets insets = new HtmlInsets();
        final int top = HtmlValues.getPixelSize("0.35rem", null, element.getDocumentNode().getDefaultView(), -1);
        final int bottom = HtmlValues.getPixelSize("0.625rem", null, element.getDocumentNode().getDefaultView(), -1);
        final int leftRight = HtmlValues.getPixelSize("0.65rem", null, element.getDocumentNode().getDefaultView(), -1);
        insets.setTop(top);
        insets.setBottom(bottom);
        insets.setLeft(leftRight);
        insets.setRight(leftRight);
        insets.setTopType(HtmlInsets.TYPE_PIXELS);
        insets.setBottomType(HtmlInsets.TYPE_PIXELS);
        insets.setLeftType(HtmlInsets.TYPE_PIXELS);
        insets.setRightType(HtmlInsets.TYPE_PIXELS);
        return insets;
    }


    private HtmlInsets getDefaultBordernsets() {
        final int border = HtmlValues.getPixelSize("2px", null, element.getDocumentNode().getDefaultView(), -1);
        return new HtmlInsets(border, HtmlInsets.TYPE_PIXELS);
    }
}
