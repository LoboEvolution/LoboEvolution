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
public class FieldsetRenderState extends AbstractMarginRenderState {

    /**
     * <p>Constructor for BlockRenderState.</p>
     *
     * @param prevRenderState a {@link RenderState} object.
     * @param element         a {@link HTMLElementImpl} object.
     */
    public FieldsetRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /** {@inheritDoc} */
    @Override
    protected HtmlInsets getDefaultMarginInsets() {
        final HtmlInsets insets = new HtmlInsets();
        final int leftRight = HtmlValues.getPixelSize("2px", null, element.getDocumentNode().getDefaultView(), -1);
        insets.setLeft(leftRight);
        insets.setRight(leftRight);
        insets.setLeftType(HtmlInsets.TYPE_PIXELS);
        insets.setRightType(HtmlInsets.TYPE_PIXELS);
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
        this.paddingInsets = insets;
        return insets;
    }

    private HtmlInsets getDefaultPaddingInsets() {
        HtmlInsets insets = new HtmlInsets();
        final int leftRight = HtmlValues.getPixelSize("0.75em", null, element.getDocumentNode().getDefaultView(), -1);
        final int top = HtmlValues.getPixelSize("0.35em", null, element.getDocumentNode().getDefaultView(), -1);
        final int bottom = HtmlValues.getPixelSize("0.625em", null, element.getDocumentNode().getDefaultView(), -1);
        insets.setTop(top);
        insets.setBottom(bottom);
        insets.setLeft(leftRight);
        insets.setRight(leftRight);
        insets.setLeftType(HtmlInsets.TYPE_PIXELS);
        insets.setRightType(HtmlInsets.TYPE_PIXELS);
        insets.setTopType(HtmlInsets.TYPE_PIXELS);
        insets.setBottomType(HtmlInsets.TYPE_PIXELS);
        return insets;
    }
}
