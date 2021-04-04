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
 * <p>AlignValues class.</p>
 *
 *
 *
 */
public enum AlignValues {
	
	NONE(-1),
	
	TOP(0),
	
	MIDDLE(1),
	
	BOTTOM(2),
	
	ABSMIDDLE(3),
	
	ABSBOTTOM(4),
	
	BASELINE(5);
	
	private final int value;
	private static final Map<Integer, AlignValues> ENUM_MAP;
	
	static {
		Map<Integer, AlignValues> map = new HashMap<>();
		for (AlignValues instance : AlignValues.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	AlignValues(Integer value) {
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
	public static AlignValues get(Integer actionName) {
		AlignValues value = ENUM_MAP.get(actionName);
		return value == null ? AlignValues.NONE : value;
	}
}
