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

package org.loboevolution.html.renderstate;

import org.loboevolution.html.dom.domimpl.HTMLDialogElementImpl;
import org.loboevolution.html.style.BorderInsets;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.BorderInfo;
import org.loboevolution.js.Window;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;

/**
 * <p>DialogRenderState class.</p>
 */
public class DialogRenderState extends BlockRenderState {

    private final HTMLDialogElementImpl element;

    /**
     * <p>Constructor for DialogRenderState.</p>
     *
     * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
     * @param element         a {@link org.loboevolution.html.dom.domimpl.HTMLDialogElementImpl} object.
     */
    public DialogRenderState(final RenderState prevRenderState, final HTMLDialogElementImpl element) {
        super(prevRenderState, element);
        this.element = element;
    }

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
            binfo.setTopColor(Color.BLACK);
            binfo.setLeftColor(Color.BLACK);
            binfo.setRightColor(Color.BLACK);
            binfo.setBottomColor(Color.BLACK);
            binfo.setTopStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setLeftStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setRightStyle(BorderInsets.BORDER_STYLE_SOLID);
            binfo.setBottomStyle(BorderInsets.BORDER_STYLE_SOLID);

        }
        this.borderInfo = binfo;
        return binfo;
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

    @Override
    public int getPosition() {
        return POSITION_ABSOLUTE;
    }

    @Override
    public Color getBackgroundColor() {
        return ColorFactory.getInstance().getColor("#fff");
    }

    private HtmlInsets getDefaultBordernsets() {
        final int border = HtmlValues.getPixelSize("1px", null, element.getDocumentNode().getDefaultView(), -1);
        return new HtmlInsets(border, HtmlInsets.TYPE_PIXELS);
    }

    private HtmlInsets getDefaultPaddingInsets() {
        final HtmlInsets insets = new HtmlInsets();
        Window win  = element.getDocumentNode().getDefaultView();
        final int leftRight = HtmlValues.getPixelSize("20px", null, win, -1);
        final int top = HtmlValues.getPixelSize("20px", null, win, -1);
        final int bottom = HtmlValues.getPixelSize("20px", null, win, -1);
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

    @Override
    public String getLeft() {
        return "40%";
    }

    @Override
    public int getDisplay() {
        return element.getOpen() ? RenderState.DISPLAY_BLOCK : RenderState.DISPLAY_NONE;
    }
}
