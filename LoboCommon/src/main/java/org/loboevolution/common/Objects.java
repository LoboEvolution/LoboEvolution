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

package org.loboevolution.common;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

/**
 * <p>Objects class.</p>
 */
public class Objects {

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
