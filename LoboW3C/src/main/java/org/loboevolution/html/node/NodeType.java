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
 * <p>NodeType class.</p>
 */
public class NodeType {

	public final static int NONE = -1;
	public final static int ELEMENT_NODE = 1;
	public final static int ATTRIBUTE_NODE = 2;
	public final static int TEXT_NODE = 3;
	public final static int CDATA_SECTION_NODE = 4;
	public final static int ENTITY_REFERENCE_NODE = 5;
	public final static int ENTITY_NODE  = 6;
	public final static int PROCESSING_INSTRUCTION_NODE = 7;
	public final static int COMMENT_NODE = 8;
	public final static int DOCUMENT_NODE  = 9;
	public final static int DOCUMENT_TYPE_NODE = 10;
	public final static int DOCUMENT_FRAGMENT_NODE = 11;
	public final static int NOTATION_NODE  = 12;
}
