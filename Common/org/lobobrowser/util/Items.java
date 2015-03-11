package org.lobobrowser.util;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * The Class Items.
 */
public class Items {
	
	/**
	 * Instantiates a new items.
	 */
	private Items() {
	}

	/** The source map. */
	private static Map sourceMap = new WeakHashMap();

	/**
	 * Gets the item.
	 *
	 * @param source the source
	 * @param name the name
	 * @return the item
	 */
	public static Object getItem(Object source, String name) {
		Map sm = sourceMap;
		synchronized (sm) {
			Map itemMap = (Map) sm.get(source);
			if (itemMap == null) {
				return null;
			}
			return itemMap.get(name);
		}
	}

	/**
	 * Sets the item.
	 *
	 * @param source the source
	 * @param name the name
	 * @param value the value
	 */
	public static void setItem(Object source, String name, Object value) {
		Map sm = sourceMap;
		synchronized (sm) {
			Map itemMap = (Map) sm.get(source);
			if (itemMap == null) {
				itemMap = new HashMap(1);
				sm.put(source, itemMap);
			}
			itemMap.put(name, value);
		}
	}
}
