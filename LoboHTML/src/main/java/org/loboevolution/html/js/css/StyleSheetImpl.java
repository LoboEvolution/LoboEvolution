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

package org.loboevolution.html.js.css;

import lombok.Getter;
import lombok.Setter;
import org.htmlunit.cssparser.dom.CSSStyleSheetImpl;
import org.loboevolution.html.node.Element;
import org.loboevolution.css.CSSStyleSheet;
import org.loboevolution.css.MediaList;
import org.loboevolution.css.StyleSheet;

/**
 * <p>StyleSheetImpl class.</p>
 */
public class StyleSheetImpl implements StyleSheet {

    private final CSSStyleSheetImpl cssStyleSheet;

    @Getter
    @Setter
    private Element ownerNode;

    public StyleSheetImpl(final CSSStyleSheetImpl cssStyleSheet) {
        this.cssStyleSheet = cssStyleSheet;
    }

    /** {@inheritDoc} */
    @Override
    public String getType() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public String getHref() {
        return cssStyleSheet.getHref();
    }

    /** {@inheritDoc} */
    @Override
    public CSSStyleSheet parentStyleSheet() {
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public MediaList getMedia() {
        return new MediaListImpl(cssStyleSheet.getMedia());
    }

    /** {@inheritDoc} */
    @Override
    public boolean getDisabled() {
        return cssStyleSheet.getDisabled();
    }

    @Override
    public String toString() {
        return "[object StyleSheet]";
    }
}
