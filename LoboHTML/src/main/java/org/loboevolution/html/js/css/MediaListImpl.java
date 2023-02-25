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

package org.loboevolution.html.js.css;

import com.gargoylesoftware.css.parser.media.MediaQuery;
import org.loboevolution.html.node.css.MediaList;

public class MediaListImpl implements MediaList {

    private final com.gargoylesoftware.css.dom.MediaListImpl media;

    public MediaListImpl(com.gargoylesoftware.css.dom.MediaListImpl media) {
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
