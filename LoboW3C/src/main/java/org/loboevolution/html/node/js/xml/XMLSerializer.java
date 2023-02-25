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

package org.loboevolution.html.node.js.xml;

import org.loboevolution.html.node.Node;

/**
 * <p>XMLSerializer interface.</p>
 */
public interface XMLSerializer {

    /**
     * The subtree rooted by the specified element is serialized to a string.
     *
     * @param root
     *            the root of the subtree to be serialized (this may be any
     *            node, even a document)
     * @return the serialized string
     */
    String serializeToString(Node root);
}
