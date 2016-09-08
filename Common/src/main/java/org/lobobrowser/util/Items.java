/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
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
    private static Map<Object,Map<String,Object>> sourceMap = new WeakHashMap<Object,Map<String,Object>>();
    
    /**
     * Gets the item.
     *
     * @param source
     *            the source
     * @param name
     *            the name
     * @return the item
     */
    public static Object getItem(Object source, String name) {
        Map<Object,Map<String,Object>> sm = sourceMap;
        synchronized (sm) {
            Map<String,Object> itemMap = (Map<String,Object>) sm.get(source);
            if (itemMap == null) {
                return null;
            }
            return itemMap.get(name);
        }
    }
    
    /**
     * Sets the item.
     *
     * @param source
     *            the source
     * @param name
     *            the name
     * @param value
     *            the value
     */
    public static void setItem(Object source, String name, Object value) {
        Map<Object,Map<String,Object>> sm = sourceMap;
        synchronized (sm) {
            Map<String,Object> itemMap = (Map<String,Object>) sm.get(source);
            if (itemMap == null) {
                itemMap = new HashMap<String,Object>(1);
                sm.put(source, itemMap);
            }
            itemMap.put(name, value);
        }
    }
}
