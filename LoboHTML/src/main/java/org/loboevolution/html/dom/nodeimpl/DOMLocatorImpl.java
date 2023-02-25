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
package org.loboevolution.html.dom.nodeimpl;

import lombok.Data;
import org.loboevolution.html.dom.DOMLocator;
import org.loboevolution.html.node.Node;

/**
 * Implementation of DOMLocator
 */
@Data
public class DOMLocatorImpl implements DOMLocator {
    private final int lineNumber;
    private final int columnNumber;
    private final int byteOffset;
    private final int utf16Offset;
    private final Node relatedNode;
    private final String uri;

    public DOMLocatorImpl(DOMLocator src) {
        this.lineNumber = src.getLineNumber();
        this.columnNumber = src.getColumnNumber();
        this.byteOffset = src.getByteOffset();
        this.utf16Offset = src.getUtf16Offset();
        this.relatedNode = src.getRelatedNode();
        this.uri = src.getUri();
    }
}
