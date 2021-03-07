package org.loboevolution.jsenum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Decoding {

	ASYNC("async"),

	SYNC("sync"),

	AUTO("auto");

	private final String value;
	private static final Map<String, Decoding> ENUM_MAP;

	static {
		Map<String, Decoding> map = new HashMap<>();
		for (Decoding instance : Decoding.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	Decoding(String value) {
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
	public static Decoding get(String actionName) {
		Decoding value = ENUM_MAP.get(actionName);
		return value == null ? Decoding.SYNC : value;
	}

}
