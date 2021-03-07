package org.loboevolution.jsenum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public enum EventPhase {
	
	 AT_TARGET("AT_TARGET"),

	 BUBBLING_PHASE("BUBBLING_PHASE"),

	 CAPTURING_PHASE("CAPTURING_PHASE"),

	 NONE("NONE");

	private final String value;
	private static final Map<String, EventPhase> ENUM_MAP;
	
	static {
		Map<String, EventPhase> map = new HashMap<>();
		for (EventPhase instance : EventPhase.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	EventPhase(String value) {
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
	public static EventPhase get(String actionName) {
		EventPhase value = ENUM_MAP.get(actionName);
		return value == null ? EventPhase.NONE : value;
	}

}
