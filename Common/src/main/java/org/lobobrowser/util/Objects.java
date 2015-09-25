/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Apr 10, 2005
 */
package org.lobobrowser.util;

/**
 * The Class Objects.
 *
 * @author J. H. S.
 */
public class Objects {
    /**
     * Instantiates a new objects.
     */
    private Objects() {
    }
    
    /**
     * Equals.
     *
     * @param obj1
     *            the obj1
     * @param obj2
     *            the obj2
     * @return true, if successful
     */
    public static boolean equals(Object obj1, Object obj2) {
        return obj1 == null ? (obj2 == null) : (obj1.equals(obj2));
    }
    
    /**
     * Checks if is box class.
     *
     * @param clazz
     *            the clazz
     * @return true, if is box class
     */
    public static boolean isBoxClass(Class clazz) {
        return (clazz == Integer.class) || (clazz == Boolean.class)
                || (clazz == Double.class) || (clazz == Float.class)
                || (clazz == Long.class) || (clazz == Byte.class)
                || (clazz == Short.class) || (clazz == Character.class);
    }
    
    /**
     * Are assignable to.
     *
     * @param objects
     *            the objects
     * @param types
     *            the types
     * @return true, if successful
     */
    public static boolean areAssignableTo(Object[] objects, Class[] types) {
        int length = objects.length;
        if (length != types.length) {
            return false;
        }
        for (int i = 0; i < length; i++) {
            if (!isAssignableOrBox(objects[i], types[i])) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks if is assignable or box.
     *
     * @param value
     *            the value
     * @param clazz
     *            the clazz
     * @return true, if is assignable or box
     */
    public static boolean isAssignableOrBox(Object value, Class clazz) {
        if (clazz.isInstance(value)) {
            return true;
        }
        if (clazz.isPrimitive()) {
            if (((clazz == double.class) && (value instanceof Double))
                    || ((clazz == int.class) && (value instanceof Integer))
                    || ((clazz == long.class) && (value instanceof Long))
                    || ((clazz == boolean.class) && (value instanceof Boolean))
                    || ((clazz == byte.class) && (value instanceof Byte))
                    || ((clazz == char.class) && (value instanceof Character))
                    || ((clazz == short.class) && (value instanceof Short))
                    || ((clazz == float.class) && (value instanceof Float))) {
                return true;
            }
        }
        if (isNumeric(clazz) && isNumeric(value)) {
            return true;
        }
        if (clazz.isAssignableFrom(String.class)) {
            return (value == null) || !value.getClass().isPrimitive();
        }
        return false;
    }
    
    /**
     * Checks if is numeric.
     *
     * @param clazz
     *            the clazz
     * @return true, if is numeric
     */
    private static boolean isNumeric(Class clazz) {
        return Number.class.isAssignableFrom(clazz) || (clazz.isPrimitive()
                && ((clazz == int.class) || (clazz == double.class)
                        || (clazz == byte.class) || (clazz == short.class)
                        || (clazz == float.class) || (clazz == long.class)));
    }
    
    /**
     * Checks if is numeric.
     *
     * @param value
     *            the value
     * @return true, if is numeric
     */
    private static boolean isNumeric(Object value) {
        if (value == null) {
            return false;
        }
        return isNumeric(value.getClass());
    }
}
