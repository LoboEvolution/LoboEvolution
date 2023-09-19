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
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.html.dom.HTMLBodyElement;
import org.loboevolution.html.dom.HTMLDocument;
import org.loboevolution.html.dom.domimpl.HTMLLinkElementImpl;
import org.loboevolution.html.node.css.CSSStyleDeclaration;
import org.loboevolution.laf.ColorFactory;

import java.awt.*;
import java.util.Optional;

/**
 * <p>LinkRenderState class.</p>
 */
public class LinkRenderState extends StyleSheetRenderState {

    private final HtmlRendererContext rcontext;

    private final HTMLLinkElementImpl element;

    /**
     * <p>Constructor for RenderStateDelegator.</p>
     *
     * @param delegate a {@link RenderState} object.
     */
    public LinkRenderState(RenderState delegate, HtmlRendererContext rcontext, HTMLLinkElementImpl element) {
        super(delegate, element);
        this.element = element;
        this.rcontext = rcontext;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTextDecorationMask() {
        CSSStyleDeclaration props = this.getCssProperties();
        String tdText = props == null ? null : props.getTextDecoration();
        if (Strings.isNotBlank(tdText)) {
            return super.getTextDecorationMask();
        } else {
            return RenderState.MASK_TEXTDECORATION_UNDERLINE;
        }
    }

    @Override
    public Color getColor() {
        return linkColor();
    }

    @Override
    public Optional<Cursor> getCursor() {
        return Optional.of(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private Color linkColor() {
        if (rcontext != null) {
            boolean visited = rcontext.isVisitedLink(element);
            String vlink = null;
            String link = null;
            HTMLDocument doc = (HTMLDocument) element.getDocumentNode();
            if (doc != null) {
                HTMLBodyElement body = (HTMLBodyElement) doc.getBody();
                if (body != null) {
                    vlink = body.getVLink();
                    link = body.getLink();
                }
            }
            String COLOR_VISITED = "#551A8B";
            vlink = (vlink == null) ? COLOR_VISITED : vlink;
            String DEFAULT_COLOR = "Blue";
            link = (link == null) ? DEFAULT_COLOR : link;
            String colorText = visited ? vlink : link;

            CSSStyleDeclaration props = this.getCssProperties();
            String color = props == null ? null : props.getColor();
            return ColorFactory.getInstance().getColor(color == null ? colorText : color);
        }
        return Color.BLUE;
    }

    @Override
    public int getDisplay() {
        return DISPLAY_INLINE_BLOCK;
    }
}
