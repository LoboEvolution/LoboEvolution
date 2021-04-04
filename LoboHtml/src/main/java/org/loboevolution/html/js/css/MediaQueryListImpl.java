/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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

package org.loboevolution.html.js.css;

import com.gargoylesoftware.css.dom.MediaListImpl;

import org.loboevolution.html.js.WindowImpl;
import org.loboevolution.html.node.css.MediaQueryList;
import org.loboevolution.html.style.CSSUtilities;
import org.loboevolution.html.style.StyleSheetAggregator;

/**
 * <p>MediaQueryListImpl class.</p>
 *
 *
 *
 */
public class MediaQueryListImpl implements MediaQueryList {

    private final String media;

    private final WindowImpl window;

    /**
     * <p>Constructor for MediaQueryListImpl.</p>
     *
     * @param window a {@link org.loboevolution.html.js.WindowImpl} object.
     * @param mediaQueryString a {@link java.lang.String} object.
     */
    public MediaQueryListImpl(WindowImpl window, String mediaQueryString) {
        this.window = window;
        this.media = mediaQueryString;
    }

    /**
     * <p>Getter for the field <code>media</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getMedia() {
        return media;
    }

    /**
     * <p>isMatches.</p>
     *
     * @return a boolean.
     * @throws java.lang.Exception if any.
     */
    public boolean isMatches() throws Exception {
        final String processedText = CSSUtilities.preProcessCss(media);
        MediaListImpl media = CSSUtilities.parseMedia(processedText);
        return StyleSheetAggregator.isActive(window, media);
    }
}

