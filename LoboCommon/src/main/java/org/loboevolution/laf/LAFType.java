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
package org.loboevolution.laf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>LAFType class.</p>
 */
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
		Map<String, LAFType> map = new HashMap<>();
		for (LAFType instance : LAFType.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	LAFType(String value) {
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
	 * <p>get.</p>
	 *
	 * @param actionName a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.laf.LAFType} object.
	 */
	public static LAFType get(String actionName) {
		return ENUM_MAP.get(actionName);
	}
}
