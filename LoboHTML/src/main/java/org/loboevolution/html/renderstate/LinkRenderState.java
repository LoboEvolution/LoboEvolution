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

    private final String COLOR_VISITED = "#551A8B";

    private final String DEFAULT_COLOR = "Blue";

    private RenderState delegate;

    private HtmlRendererContext rcontext;

    private HTMLLinkElementImpl element;

    /**
     * <p>Constructor for RenderStateDelegator.</p>
     *
     * @param delegate a {@link RenderState} object.
     */
    public LinkRenderState(RenderState delegate, HtmlRendererContext rcontext, HTMLLinkElementImpl element) {
        super(delegate, element);
        this.delegate = delegate;
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
            vlink = (vlink == null) ? COLOR_VISITED : vlink;
            link = (link == null) ? DEFAULT_COLOR : link;
            String colorText = visited ? vlink : link;

            CSSStyleDeclaration props = this.getCssProperties();
            String color = props == null ? null : props.getColor();
            return ColorFactory.getInstance().getColor(color == null ? colorText : color);
        }
        return Color.BLUE;
    }
}
