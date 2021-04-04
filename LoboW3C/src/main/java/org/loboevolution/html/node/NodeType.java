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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>NodeType class.</p>
 *
 *
 *
 */
public enum NodeType {
	
	 NONE(-1),
	 ELEMENT_NODE(1),
	 ATTRIBUTE_NODE(2),
	 TEXT_NODE(3),
	 CDATA_SECTION_NODE(4),
	 ENTITY_REFERENCE_NODE(5),
	 ENTITY_NODE (6),
	 PROCESSING_INSTRUCTION_NODE(7),
	 COMMENT_NODE(8),
	 DOCUMENT_NODE (9),
	 DOCUMENT_TYPE_NODE(10),
	 DOCUMENT_FRAGMENT_NODE(11),
	 NOTATION_NODE (12);

	private final int value;
	private static final Map<Integer, NodeType> ENUM_MAP;
	
	static {
		Map<Integer, NodeType> map = new HashMap<>();
		for (NodeType instance : NodeType.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	NodeType(Integer value) {
		this.value = value;
	}

	/**
	 * <p>Getter for the field value.</p>
	 *
	 * @return a {@link java.lang.Integer} object.
	 */
	public Integer getValue() {
		return value;
	}

	/**
	 * <p>isEqual.</p>
	 *
	 * @param value a {@link java.lang.Integer} object.
	 * @return a boolean.
	 */
	public boolean isEqual(Integer value) {
		return this.value == value;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param actionName a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.node.NodeType} object.
	 */
	public static NodeType get(Integer actionName) {
		NodeType value = ENUM_MAP.get(actionName);
		return value == null ? NodeType.NONE : value;
	}
}
