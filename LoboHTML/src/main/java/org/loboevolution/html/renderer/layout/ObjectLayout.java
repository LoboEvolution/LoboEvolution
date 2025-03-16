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

import org.loboevolution.html.HtmlObject;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.control.UIControl;
import org.loboevolution.html.control.UIControlWrapper;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.domimpl.HTMLElementImpl;
import org.loboevolution.html.renderer.RBlockViewport;
import org.loboevolution.html.renderer.RElement;

public class ObjectLayout extends CommonWidgetLayout {
    /**
     * Must use this ThreadLocal because an ObjectLayout instance is shared across
     * renderers.
     */
    private final ThreadLocal<HtmlObject> htmlObject = new ThreadLocal<>();

    private final boolean tryToRenderContent;

    /**
     * @param tryToRenderContent If the object is unknown, content is rendered as  HTML.
     */
    public ObjectLayout(final boolean tryToRenderContent) {
        super(ADD_INLINE);
        this.tryToRenderContent = tryToRenderContent;
    }

    @Override
    public  RElement createRenderable(final RBlockViewport bodyLayout, final HTMLElement markupElement) {
        final HtmlObject ho = this.htmlObject.get();
        final UIControl uiControl = new UIControlWrapper(ho);
        final HTMLElementImpl markupElementImpl = (HTMLElementImpl) markupElement;
        return new RUIControl(markupElementImpl, uiControl, bodyLayout.getContainer(), bodyLayout.getUserAgentContext());
    }

    @Override
    public void layoutMarkup(final RBlockViewport bodyLayout, final HTMLElement markupElement) {
        if (this.tryToRenderContent) {
            final HTMLElementImpl markupElementImpl = (HTMLElementImpl) markupElement;
            bodyLayout.layoutMarkup(markupElementImpl);
        }
    }
}