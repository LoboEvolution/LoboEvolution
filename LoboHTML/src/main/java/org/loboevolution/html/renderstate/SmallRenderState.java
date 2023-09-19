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
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.CSSValues;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.html.style.FontValues;
import org.loboevolution.laf.FontFactory;
import org.loboevolution.laf.FontKey;

import java.awt.*;

/**
 * <p>SmallRenderState class.</p>
 */
public class SmallRenderState extends RenderStateDelegator {

    private final HTMLElementImpl element;

    private final RenderState prevRenderState;

    /**
     * <p>Constructor for SmallRenderState.</p>
     *
     * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
     * @param element a {@link org.loboevolution.html.dom.domimpl.HTMLElementImpl} object.
     */
    public SmallRenderState(RenderState prevRenderState, HTMLElementImpl element) {
        super(prevRenderState);
        this.element = element;
        this.prevRenderState = prevRenderState;
    }


    @Override
    public Font getFont() {
        final CSSStyleDeclaration props = element.getCurrentStyle();
        final HtmlRendererConfig config = element.getHtmlRendererConfig();
        final String fontSize = props == null ? null : props.getFontSize();
        FontKey key = FontValues.getDefaultFontKey(config);
        final String fSize = Strings.isNotBlank(fontSize) ? fontSize : CSSValues.SMALLER.getValue();
        key.setFontSize(FontValues.getFontSize(fSize, element.getDocumentNode().getDefaultView(), prevRenderState));
        return FontFactory.getInstance().getFont(FontValues.getFontKey(key, element, props, prevRenderState));
    }

    @Override
    public int getDefaultDisplay() {
        return DISPLAY_BLOCK;
    }
}
