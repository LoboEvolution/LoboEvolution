/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.info.BackgroundInfo;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;

/**
 * <p>BodyRenderState class.</p>
 */
public class BodyRenderState extends StyleSheetRenderState {

    /**
     * <p>Constructor for BodyRenderState.</p>
     *
     * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
     * @param element         a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     */
    public BodyRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState, element);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BackgroundInfo getBackgroundInfo() {
        BackgroundInfo binfo = this.iBackgroundInfo;
        if (binfo != INVALID_BACKGROUND_INFO) {
            return binfo;
        }
        binfo = super.getBackgroundInfo();
        if (binfo == null || binfo.getBackgroundColor() == null) {
            final String bgcolor = this.element.getAttribute("bgcolor");
            final String background = this.element.getAttribute("background");
            if (Strings.isNotBlank(bgcolor)) {
                if (binfo == null) {
                    binfo = new BackgroundInfo();
                }
                binfo.setBackgroundColor(ColorFactory.getInstance().getColor(bgcolor));
            }

            if (Strings.isNotBlank(background)) {
                if (binfo == null) {
                    binfo = new BackgroundInfo();
                }
                binfo.setBackgroundImage(this.document.getFullURL(background));
            }
        }
        this.iBackgroundInfo = binfo;
        return binfo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Color getColor() {
        Color c = super.getColor();
        if (c != null) {
            return c;
        }
        String tcolor = this.element.getAttribute("text");

        if (Strings.isNotBlank(tcolor)) {
            c = ColorFactory.getInstance().getColor(tcolor);
        }

        return c;
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
            final int margin = HtmlValues.getPixelSize("8px", null, this.document.getDefaultView(), 0);
            insets = new HtmlInsets(margin, HtmlInsets.TYPE_PIXELS);
        }

        this.marginInsets = insets;
        return insets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public int getDefaultDisplay() {
        return DISPLAY_BLOCK;
    }
}
