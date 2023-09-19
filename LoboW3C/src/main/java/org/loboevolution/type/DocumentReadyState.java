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

package org.loboevolution.type;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>DocumentReadyState class.</p>
 *
 *
 *
 */
public enum DocumentReadyState {

	LOADING("loading"),

	INTERACTIVE("interactive"),

	COMPLETE("complete");

	private final String value;
	private static final Map<String, DocumentReadyState> ENUM_MAP;

	static {
		Map<String, DocumentReadyState> map = new HashMap<>();
		for (DocumentReadyState instance : DocumentReadyState.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	DocumentReadyState(String value) {
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
	 * @return a {@link org.loboevolution.type} object.
	 */
	public static DocumentReadyState get(String actionName) {
		DocumentReadyState value = ENUM_MAP.get(actionName);
		return value == null ? DocumentReadyState.COMPLETE : value;
	}
}
