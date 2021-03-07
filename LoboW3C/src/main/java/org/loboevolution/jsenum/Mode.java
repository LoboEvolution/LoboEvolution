package org.loboevolution.jsenum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Mode {
	
	END_TO_END("Range.END_TO_END"),

    END_TO_START("Range.END_TO_START"),

    START_TO_END("Range.START_TO_END"),

    START_TO_START("Range.START_TO_START");
    
    private final String value;
	private static final Map<String, Mode> ENUM_MAP;
	
	static {
		Map<String, Mode> map = new HashMap<>();
		for (Mode instance : Mode.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	Mode(String value) {
		this.value = value;
	}

	/**
	 * <p>Getter for the field value.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getValue() {
		return value;
	}

	/**
	 * <p>isEqual.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 * @return a boolean.
	 */
	public boolean isEqual(String value) {
		return this.value.equals(value);
	}

	/**
	 * <p>get.</p>
	 *
	 * @param actionName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.jsenum} object.
	 */
	public static Mode get(String actionName) {
		Mode value = ENUM_MAP.get(actionName);
		return value == null ? Mode.START_TO_START : value;
	}

}
