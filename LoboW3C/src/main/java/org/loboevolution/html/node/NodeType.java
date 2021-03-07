package org.loboevolution.html.node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
	 * @return a {@link org.loboevolution.html.AlignValues} object.
	 */
	public static NodeType get(Integer actionName) {
		NodeType value = ENUM_MAP.get(actionName);
		return value == null ? NodeType.NONE : value;
	}
}
