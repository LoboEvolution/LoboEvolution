/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>ListValues class.</p> 
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
		final Map<Integer, ListValues> map = new HashMap<>();
		for (final ListValues instance : ListValues.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	ListValues(final Integer value) {
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
	public boolean isEqual(final Integer value) {
		return this.value == value;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param actionName a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.ListValues} object.
	 */
	public static ListValues get(final Integer actionName) {
		final ListValues value = ENUM_MAP.get(actionName);
		return value == null ? ListValues.NONE : value;
	}
}
