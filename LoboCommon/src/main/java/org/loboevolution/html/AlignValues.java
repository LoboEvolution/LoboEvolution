/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
package org.loboevolution.html;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum AlignValues {
	
	NONE(-1),
	
	TOP(0),
	
	MIDDLE(1),
	
	BOTTOM(2),
	
	ABSMIDDLE(3),
	
	ABSBOTTOM(4),
	
	BASELINE(5);
	
	private int value;
	private static final Map<Integer, AlignValues> ENUM_MAP;
	
	static {
		Map<Integer, AlignValues> map = new HashMap<Integer, AlignValues>();
		for (AlignValues instance : AlignValues.values()) {
			map.put(instance.getValue(), instance);
		}
		ENUM_MAP = Collections.unmodifiableMap(map);
	}

	AlignValues(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public boolean isEqual(Integer value) {
		return this.value == value;
	}

	public static AlignValues get(Integer actionName) {
		AlignValues value = ENUM_MAP.get(actionName);
		return value == null ? AlignValues.NONE : value;
	}
}
