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
package org.loboevolution.laf;

import lombok.Getter;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>LAFType class.</p>
 */
@Getter
public enum LAFType {

	ACRYL("Acryl"),

	AERO("Aero"),

	ALUMINIUM("Aluminium"),

	BERNSTEIN("Bernstein"),

	BOLD("Bold"),

	FAST("Fast"),

	GRAPHITE("Graphite"),

	HIFI("HiFi"),

	ITALIC("Italic"),

	LUNA("Luna"),

	MONOSPACED("Monospaced"),

	MCWIN("McWin"),

	MINT("Mint"),

	NOIRE("Noire"),

	SMART("Smart"),
	
	MODERN("Modern"),
	
	WHITE_BLACK("Light"),
	
	BLACK_WHITE("Black");

	
	private final String value;
	private static final Map<String, LAFType> ENUM_MAP;
	
	static {
		final Map<String, LAFType> map = new HashMap<>();
		for (final LAFType instance : LAFType.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	LAFType(final String value) {
		this.value = value;
	}

	/**
	 * <p>get.</p>
	 *
	 * @param actionName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.laf.LAFType} object.
	 */
	public static LAFType get(final String actionName) {
		return ENUM_MAP.get(actionName);
	}
}
