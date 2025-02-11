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

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.htmlunit.cssparser.dom.MediaListImpl;
import org.loboevolution.common.Strings;
import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.css.MediaQueryList;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.StyleSheetAggregator;

/**
 * <p>MediaQueryListImpl class.</p>
 */
@AllArgsConstructor
@Getter
public class MediaQueryListImpl implements MediaQueryList {

    private final WindowImpl window;

    private final String media;

    /**
     * <p>isMatches.</p>
     *
     * @return a boolean.
     * @throws java.lang.Exception if any.
     */
    public boolean isMatches() throws Exception {
        final String processedText = CSSUtilities.preProcessCss(media);
        if (Strings.isBlank(processedText)) return true;
        final MediaListImpl media = CSSUtilities.parseMedia(processedText);

        if (Strings.isBlank(media.getMediaText()) ||
                "screen".equalsIgnoreCase(media.getMediaText()) ||
                "all".equalsIgnoreCase(media.getMediaText()) ||
                "print".equalsIgnoreCase( media.getMediaText())) {
            return true;
        }

        return StyleSheetAggregator.isActive(window, media);
    }
}

