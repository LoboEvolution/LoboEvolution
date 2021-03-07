package org.loboevolution.html.node;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Code {
	
	  NONE(-1),
	  INDEX_SIZE_ERR(1),
	  DOMSTRING_SIZE_ERR(2),
	  HIERARCHY_REQUEST_ERR(3),
	  WRONG_DOCUMENT_ERR(4),
	  INVALID_CHARACTER_ERR(5),
	  NO_DATA_ALLOWED_ERR(6),
	  NO_MODIFICATION_ALLOWED_ERR(7),
	  NOT_FOUND_ERR(8),
	  NOT_SUPPORTED_ERR(9),
	  INUSE_ATTRIBUTE_ERR(10),
	  INVALID_STATE_ERR(11),
	  SYNTAX_ERR(12),
	  INVALID_MODIFICATION_ERR(13),
	  NAMESPACE_ERR(14),
	  INVALID_ACCESS_ERR(15),
	  VALIDATION_ERR(16),
	  TYPE_MISMATCH_ERR(17),
	  SECURITY_ERR(18),
	  NETWORK_ERR(19),
	  ABORT_ERR(20),
	  URL_MISMATCH_ERR(21),
	  QUOTA_EXCEEDED_ERR(22),
	  TIMEOUT_ERR(23),
	  INVALID_NODE_TYPE_ERR(24),
	  DATA_CLONE_ERR(25),;

	private final int value;
	private static final Map<Integer, Code> ENUM_MAP;
	
	static {
		Map<Integer, Code> map = new HashMap<>();
		for (Code instance : Code.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	Code(Integer value) {
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
	public static Code get(Integer actionName) {
		Code value = ENUM_MAP.get(actionName);
		return value == null ? Code.NONE : value;
	}
}