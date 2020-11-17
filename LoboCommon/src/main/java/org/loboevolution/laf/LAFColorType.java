package org.loboevolution.laf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum LAFColorType {

	BACKGROUND_MODERN("rgb(37, 51, 61)"),

	FOREGROUND_MODERN("rgb(108, 216, 158)"),
	
	BACKGROUND_BLACK_WHITE("rgb(0,0,0)"),

	FOREGROUND_BLACK_WHITE("rgb(255,255,255)"),
	
	BACKGROUND_WHITE_BLACK("rgb(255,255,240)"),

	FOREGROUND_WHITE_BLACK("rgb(0,0,0)");

	private final String value;
	private static final Map<String, LAFColorType> ENUM_MAP;

	static {
		Map<String, LAFColorType> map = new HashMap<>();
		for (LAFColorType instance : LAFColorType.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	LAFColorType(String value) {
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
	 * @return a {@link org.loboevolution.laf.LAFColorType} object.
	 */
	public static LAFColorType get(String actionName) {
		return ENUM_MAP.get(actionName);
	}
}