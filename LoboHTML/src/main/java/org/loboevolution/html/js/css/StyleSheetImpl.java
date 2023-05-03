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

import org.htmlunit.cssparser.dom.CSSStyleSheetImpl;
import org.loboevolution.html.node.Element;
import org.loboevolution.html.node.Node;
import org.loboevolution.html.node.css.CSSStyleSheet;
import org.loboevolution.html.node.css.MediaList;
import org.loboevolution.html.node.css.StyleSheet;

public class StyleSheetImpl implements StyleSheet {

    private final CSSStyleSheetImpl cssStyleSheet;

    private Node ownerNode;

    public StyleSheetImpl(CSSStyleSheetImpl cssStyleSheet) {
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
    public Element getOwnerNode() {
        return (Element) ownerNode;
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

    public void setOwnerNode(Node ownerNode) {
        this.ownerNode = ownerNode;
    }

    @Override
    public String toString() {
        return "[object StyleSheet]";
    }
}
