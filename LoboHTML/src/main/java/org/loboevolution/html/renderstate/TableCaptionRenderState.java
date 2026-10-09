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

package org.loboevolution.html.renderstate;

import org.loboevolution.common.Strings;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.css.CSSStyleDeclaration;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.html.style.HtmlInsets;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;
import java.awt.*;

/**
 * <p>TableCaptionRenderState class.</p>
 */
public class TableCaptionRenderState extends DisplayRenderState {

    /**
     * <p>Constructor for TableCaptionRenderState.</p>
     *
     * @param prevRenderState a {@link RenderState} object.
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     */
    public TableCaptionRenderState(final RenderState prevRenderState, final HTMLElementImpl element) {
        super(prevRenderState, element, RenderState.DISPLAY_TABLE_CAPTION);
    }

    @Override
    public Font getFont() {
        final CSSStyleDeclaration style = element.getCurrentStyle();
        final HtmlRendererConfig config = element.getHtmlRendererConfig();
        final FontKey key = FontValues.getDefaultFontKey(config);
        if (style == null || Strings.isBlank(style.getFontSize())) {
            key.setFontSize(14f);
        }
        if (style == null || Strings.isBlank(style.getFontWeight())) {
            key.setFontWeight(CSSValues.NORMAL.getValue());
        }
        return FontFactory.getInstance().getFont(
                FontValues.getFontKey(key, element, style, prevRenderState));
    }

    @Override
    public HtmlInsets getPaddingInsets() {
        return new HtmlInsets(8, HtmlInsets.TYPE_PIXELS);
    }

    @Override
    public HtmlInsets getMarginInsets() {
        return new HtmlInsets(4, HtmlInsets.TYPE_PIXELS);
    }

    @Override
    public int getAlignXPercent() {
        final CSSStyleDeclaration style = element.getCurrentStyle();
        String textAlign = style == null ? null : style.getTextAlign();
        if (Strings.isBlank(textAlign)) {
            textAlign = element.getAttribute("align");
        }
        if (Strings.isBlank(textAlign)) {
            return 50; // center default per caption
        }
        return switch (CSSValues.get(textAlign)) {
            case CENTER, MIDDLE -> 50;
            case LEFT -> 0;
            case RIGHT -> 100;
            default -> 50;
        };
    }

    @Override
    public int getAlignYPercent() {
        final CSSStyleDeclaration style = element.getCurrentStyle();
        String verticalAlign = style == null ? null : style.getVerticalAlign();
        if (Strings.isBlank(verticalAlign)) {
            return 50; // middle default
        }
        return switch (CSSValues.get(verticalAlign)) {
            case TOP -> 0;
            case MIDDLE, CENTER -> 50;
            case BOTTOM -> 100;
            default -> 50;
        };
    }
}