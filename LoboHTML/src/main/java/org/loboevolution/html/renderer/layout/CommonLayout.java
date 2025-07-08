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

package org.loboevolution.html.renderer.layout;

import org.loboevolution.html.node.Element;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.node.UINode;
import org.loboevolution.html.renderer.BaseBoundableRenderable;
import org.loboevolution.html.renderer.RBlockViewport;
import org.loboevolution.html.renderstate.RenderState;

public class CommonLayout implements MarkupLayout {

    private final int display;

    public CommonLayout(final int defaultDisplay) {
        this.display = defaultDisplay;
    }

    @Override
    public void layoutMarkup(final RBlockViewport bodyLayout, final Element markupElement) {
        final HTMLElementImpl markupElementImpl = (HTMLElementImpl)markupElement;
        final int display = calculateLayout(markupElementImpl);

        switch (display) {
            case RenderState.DISPLAY_TABLE_COLUMN:
            case RenderState.DISPLAY_TABLE_COLUMN_GROUP:
            case RenderState.DISPLAY_NONE:
                final UINode node = markupElementImpl.getUINode();
                if (node instanceof BaseBoundableRenderable) {
                    ((BaseBoundableRenderable) node).markLayoutValid();
                }
                break;
            case RenderState.DISPLAY_BLOCK:
            case RenderState.DISPLAY_TABLE_ROW:
                bodyLayout.layoutRBlock(markupElementImpl);
                break;
            case RenderState.DISPLAY_LIST_ITEM:
                final String tagName = markupElementImpl.getTagName();
                if ("UL".equalsIgnoreCase(tagName) || "OL".equalsIgnoreCase(tagName)) {
                    bodyLayout.layoutList(markupElementImpl);
                } else {
                    bodyLayout.layoutListItem(markupElementImpl);
                }
                break;
            case RenderState.DISPLAY_TABLE:
                bodyLayout.layoutRTable(markupElementImpl);
                break;
            case RenderState.DISPLAY_INLINE_TABLE:
            case RenderState.DISPLAY_INLINE_BLOCK:
                bodyLayout.layoutRInlineBlock(markupElementImpl);
                break;
            case RenderState.DISPLAY_FLEX_BOX:
                bodyLayout.layoutRFlex(markupElementImpl);
                break;
            case RenderState.DISPLAY_FLEX_CHILD:
                bodyLayout.layoutChildFlex(markupElementImpl);
                break;
            case RenderState.DISPLAY_TABLE_CELL:
            default:
                bodyLayout.layoutMarkup(markupElementImpl);
                break;
        }
    }

    private int calculateLayout(final HTMLElementImpl markupElementImpl) {
        final RenderState rs = markupElementImpl.getRenderState();
        final boolean isHidden = markupElementImpl.isHidden();
        final int defaultDispaly = rs == null ? this.display : rs.getDisplay();
        int display = isHidden ? RenderState.DISPLAY_NONE : defaultDispaly;

        if (display == RenderState.DISPLAY_INLINE || display == RenderState.DISPLAY_INLINE_BLOCK) {
            final int position = rs == null ? RenderState.POSITION_STATIC : rs.getPosition();
            if (position == RenderState.POSITION_ABSOLUTE || position == RenderState.POSITION_FIXED) {
                display = RenderState.DISPLAY_BLOCK;
            } else {
                final int boxFloat = rs == null ? RenderState.FLOAT_NONE : rs.getFloat();
                if (boxFloat != RenderState.FLOAT_NONE) {
                    display = RenderState.DISPLAY_BLOCK;
                }
            }
        }
        return display;
    }
}