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

package org.loboevolution.html.js.css;

import org.htmlunit.cssparser.parser.media.MediaQuery;
import org.loboevolution.html.node.css.MediaList;

public class MediaListImpl implements MediaList {

    private final org.htmlunit.cssparser.dom.MediaListImpl media;

    public MediaListImpl(org.htmlunit.cssparser.dom.MediaListImpl media) {
        this.media = media;
    }

    /** {@inheritDoc} */
    @Override
    public String getMediaText() {
        return media.getMediaText();
    }

    /** {@inheritDoc} */
    @Override
    public int getLength() {
        return media == null ? 0 : media.getLength();
    }

    /** {@inheritDoc} */
    @Override
    public String item(int index) {
        if (index < 0 || index >= getLength()) {
            return null;
        }
        final MediaQuery mq = media.mediaQuery(index);
        return mq.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void appendMedium(String medium) {

    }

    /** {@inheritDoc} */
    @Override
    public void deleteMedium(String medium) {

    }

    @Override
    public String toString() {
        return "[object MediaList]";
    }
}
