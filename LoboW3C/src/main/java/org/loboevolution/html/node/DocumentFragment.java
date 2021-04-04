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

package org.loboevolution.html.node;

/**
 * A minimal document object that has no parent. It is used as a lightweight
 * version of Document that stores a segment of a document structure comprised
 * of nodes just like a standard document. The key difference is that because
 * the document fragment isn't part of the active document tree structure,
 * changes made to the fragment don't affect the document, cause reflow, or
 * incur any performance impact that can occur when changes are made.
 *
 *
 *
 */
public interface DocumentFragment extends Node {

}
