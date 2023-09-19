/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
 * <p>AlignValues class.</p>
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
