package org.loboevolution.jsenum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum DocumentReadyState {

	LOADING("loading"),

	INTERACTIVE("interactive"),

	COMPLETE("complete");

	private final String value;
	private static final Map<String, DocumentReadyState> ENUM_MAP;

	static {
		Map<String, DocumentReadyState> map = new HashMap<>();
		for (DocumentReadyState instance : DocumentReadyState.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	DocumentReadyState(String value) {
		this.value = value;
	}

	/**
	 * <p>
	 * Getter for the field value.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>
	 * isEqual.
	 * </p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean isEqual(String value) {
		return this.value.equals(value);
	}

	/**
	 * <p>
	 * get.
	 * </p>
	 *
	 * @param actionName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.jsenum} object.
	 */
	public static DocumentReadyState get(String actionName) {
		DocumentReadyState value = ENUM_MAP.get(actionName);
		return value == null ? DocumentReadyState.COMPLETE : value;
	}
}