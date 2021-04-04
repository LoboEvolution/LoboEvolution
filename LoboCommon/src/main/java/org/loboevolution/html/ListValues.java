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

package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>ListValues class.</p>
 *
 *
 *
 */
public enum ListValues {
	
	NONE(-1),
	
	TYPE_NONE(0),
	
	TYPE_DISC(1),
	
	TYPE_CIRCLE(2),
	
	TYPE_SQUARE(3),
	
	TYPE_DECIMAL(4),
	
	TYPE_LOWER_ALPHA(5),
	
	TYPE_UPPER_ALPHA(6),
	
	TYPE_LOWER_LATIN(7),
	
	TYPE_UPPER_LATIN(8),
	
	TYPE_LOWER_ROMAN(9),
	
	TYPE_UPPER_ROMAN(10),
	
	TYPE_DECIMAL_LEADING_ZERO(11),
	
	TYPE_URL(12),
	
	POSITION_INSIDE(253),
	
	POSITION_OUTSIDE(254),
	
	POSITION_UNSET(255),
	
	TYPE_UNSET(256);

	private final int value;
	private static final Map<Integer, ListValues> ENUM_MAP;
	
	static {
		Map<Integer, ListValues> map = new HashMap<>();
		for (ListValues instance : ListValues.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	ListValues(Integer value) {
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
	 * @return a {@link org.loboevolution.html.ListValues} object.
	 */
	public static ListValues get(Integer actionName) {
		ListValues value = ENUM_MAP.get(actionName);
		return value == null ? ListValues.NONE : value;
	}
}
