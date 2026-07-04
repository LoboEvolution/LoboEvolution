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

package org.loboevolution.html.js.css;

import org.htmlunit.cssparser.dom.Property;
import org.htmlunit.cssparser.parser.media.MediaQuery;
import org.loboevolution.css.MediaList;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.js.JavaClassWrapperFactory;

import java.util.List;

/**
 * <p>MediaListImpl class.</p>
 */
public class MediaListImpl extends AbstractScriptableDelegate implements MediaList {

    static {
        JavaClassWrapperFactory.getInstance().registerCustomClassName(MediaListImpl.class, "MediaList");
    }

    private final org.htmlunit.cssparser.dom.MediaListImpl media;

    public MediaListImpl(final org.htmlunit.cssparser.dom.MediaListImpl media) {
        this.media = media;
    }

    /**
     * <p>getMediaText.</p>
     * @return the media text string.
     */
    public String getMediaText() {
        return media == null ? "" : media.getMediaText();
    }

    /**
     * <p>getLength.</p>
     * @return the number of media queries.
     */
    public int getLength() {
        return media == null ? 0 : media.getLength();
    }

    /** {@inheritDoc} */
    @Override
    public String item(final int index) {
        if (media == null || index < 0 || index >= getLength()) {
            return null;
        }
        final MediaQuery mq = media.mediaQuery(index);
        return mq == null ? null : formatMediaQuery(mq);
    }

    /**
     * Formats a media query to the expected string representation.
     * @param mq the media query
     * @return formatted media query string
     */
    private String formatMediaQuery(final MediaQuery mq) {
        final StringBuilder sb = new StringBuilder();
        boolean hasMedia = false;

        if (mq.isOnly()) {
            sb.append("only ");
            sb.append(mq.getMedia());
            hasMedia = true;
        } else if (mq.isNot()) {
            sb.append("not ");
            sb.append(mq.getMedia());
            hasMedia = true;
        } else {
            if (mq.getMedia() != null && !mq.getMedia().isEmpty()) {
                sb.append(mq.getMedia());
                hasMedia = true;
            }
        }

        final List<Property> properties = mq.getProperties();
        for (final Property prop : properties) {
            if (hasMedia) {
                sb.append(" and ");
            } else {
                hasMedia = true;
            }
            sb.append("(");
            sb.append(prop.getName());
            if (prop.getValue() != null) {
                final String value = prop.getValue().getCssText();
                if (value != null && !value.isEmpty()) {
                    sb.append(": ");
                    sb.append(value);
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

    /** {@inheritDoc} */
    @Override
    public void appendMedium(final String medium) {

    }

    /** {@inheritDoc} */
    @Override
    public void deleteMedium(final String medium) {

    }

    @Override
    public String toString() {
        return getLength() > 0 ? getMediaText()  : "";
    }
}