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

package org.loboevolution.jsenum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Direction class.</p>
 *
 *
 *
 */
public enum Direction {

	FORWARD("forward"),

	BACKWARD("backward"),

	NONE("none");

	private final String value;
	private static final Map<String, Direction> ENUM_MAP;

	static {
		Map<String, Direction> map = new HashMap<>();
		for (Direction instance : Direction.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	Direction(String value) {
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
	public static Direction get(String actionName) {
		Direction value = ENUM_MAP.get(actionName);
		return value == null ? Direction.NONE : value;
	}

}
