/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * <p>Objects class.</p>
 */
public class Objects {

	/**
	 * <p>equals.</p>
	 *
	 * @param obj1 a {@link java.lang.Object} object.
	 * @param obj2 a {@link java.lang.Object} object.
	 * @return a boolean.
	 */
	public static boolean equals(Object obj1, Object obj2) {
		return obj1 == obj2;
	}

	public static <M> void merge(M target, M destination) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(target.getClass());

			// Iterate over all the attributes
			for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {

				// Only copy writable attributes
				if (descriptor.getWriteMethod() != null) {
					Object originalValue = descriptor.getReadMethod().invoke(target);
					Object defaultValue = descriptor.getReadMethod().invoke(destination);
					if (originalValue == null || "".equals(originalValue)) {
						descriptor.getWriteMethod().invoke(target, defaultValue);
					} else {
						if (defaultValue != null && !"".equals(defaultValue)) {
							descriptor.getWriteMethod().invoke(target, defaultValue);
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
