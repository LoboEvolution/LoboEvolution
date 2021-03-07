package org.loboevolution.jsenum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum VisibilityState {
	HIDDEN("hidden"),

	VISIBLE("visible");

	private final String value;
	private static final Map<String, VisibilityState> ENUM_MAP;

	static {
		Map<String, VisibilityState> map = new HashMap<>();
		for (VisibilityState instance : VisibilityState.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	VisibilityState(String value) {
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
	public static VisibilityState get(String actionName) {
		VisibilityState value = ENUM_MAP.get(actionName);
		return value == null ? VisibilityState.VISIBLE : value;
	}

}
