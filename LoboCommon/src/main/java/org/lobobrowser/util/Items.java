package org.lobobrowser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class Items {
	private static Map sourceMap = new WeakHashMap();

	public static Object getItem(Object source, String name) {
		final Map sm = sourceMap;
		synchronized (sm) {
			final Map itemMap = (Map) sm.get(source);
			if (itemMap == null) {
				return null;
			}
			return itemMap.get(name);
		}
	}

	public static void setItem(Object source, String name, Object value) {
		final Map sm = sourceMap;
		synchronized (sm) {
			Map itemMap = (Map) sm.get(source);
			if (itemMap == null) {
				itemMap = new HashMap(1);
				sm.put(source, itemMap);
			}
			itemMap.put(name, value);
		}
	}

	private Items() {
	}
}
