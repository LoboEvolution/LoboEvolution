/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Mar 20, 2005
 */
package org.loboevolution.util;

// import java.util.logging.*;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * The Class Bean.
 *
 * @author J. H. S.
 */
public class Bean {
	
	/** The clazz. */
	private final Class<?> clazz;

	/** The property descriptors. */
	private Map<String, PropertyDescriptor> propertyDescriptors = null;

	/**
	 * Instantiates a new bean.
	 *
	 * @param clazz
	 *            the clazz
	 */
	public Bean(Class<?> clazz) {
		this.clazz = clazz;
	}

	

	/**
	 * Populate descriptors.
	 *
	 * @param map
	 *            the map
	 * @param clazz
	 *            the clazz
	 * @throws IntrospectionException
	 *             the introspection exception
	 */
	private void populateDescriptors(Map<String, PropertyDescriptor> map, Class<?> clazz)
			throws IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
		PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pds) {
			map.put(pd.getName(), pd);
		}
		if (clazz.isInterface()) {
			Type[] interfaces = clazz.getGenericInterfaces();
			for (Type interface1 : interfaces) {
				this.populateDescriptors(map, (Class<?>) interface1);
			}
		}
	}

	/**
	 * Gets the property descriptor.
	 *
	 * @param propertyName
	 *            the property name
	 * @return the property descriptor
	 * @throws IntrospectionException
	 *             the introspection exception
	 */
	public PropertyDescriptor getPropertyDescriptor(String propertyName) throws IntrospectionException {
		synchronized (this) {
			if (this.propertyDescriptors == null) {
				this.propertyDescriptors = new HashMap<String, PropertyDescriptor>();
				this.populateDescriptors(this.propertyDescriptors, this.clazz);
			}
			return this.propertyDescriptors.get(propertyName);
		}
	}

	/**
	 * Gets the property descriptors map.
	 *
	 * @return the property descriptors map
	 * @throws IntrospectionException
	 *             the introspection exception
	 */
	public Map<String, PropertyDescriptor> getPropertyDescriptorsMap() throws IntrospectionException {
		synchronized (this) {
			if (this.propertyDescriptors == null) {
				this.propertyDescriptors = new HashMap<String, PropertyDescriptor>();
				this.populateDescriptors(this.propertyDescriptors, this.clazz);
			}
			return this.propertyDescriptors;
		}
	}

	/**
	 * Gets the property descriptors.
	 *
	 * @return the property descriptors
	 */
	public PropertyDescriptor[] getPropertyDescriptors() throws IntrospectionException {
		synchronized (this) {
			return this.getPropertyDescriptorsMap().values().toArray(new PropertyDescriptor[0]);
		}
	}

	/**
	 * Sets the property for fqn.
	 *
	 * @param receiver
	 *            the receiver
	 * @param fullyQualifiedPropertyName
	 *            the fully qualified property name
	 * @param value
	 *            the value
	 * @throws Exception
	 *             the exception
	 */
	public void setPropertyForFQN(Object receiver, String fullyQualifiedPropertyName, Object value) throws Exception {
		int idx = fullyQualifiedPropertyName.indexOf('.');
		if (idx == -1) {
			PropertyDescriptor pd = this.getPropertyDescriptor(fullyQualifiedPropertyName);
			if (pd == null) {
				throw new IllegalStateException("Property '" + fullyQualifiedPropertyName + "' unknown");
			}
			Method method = pd.getWriteMethod();
			if (method == null) {
				throw new IllegalStateException("Property '" + fullyQualifiedPropertyName + "' not settable");
			}
			Object actualValue = convertValue(value, pd.getPropertyType());
			method.invoke(receiver, new Object[] { actualValue });
		} else {
			String prefix = fullyQualifiedPropertyName.substring(0, idx);
			PropertyDescriptor pinfo = this.getPropertyDescriptor(prefix);
			if (pinfo == null) {
				throw new IllegalStateException("Property '" + prefix + "' unknown");
			}
			Method readMethod = pinfo.getReadMethod();
			if (readMethod == null) {
				throw new IllegalStateException("Property '" + prefix + "' not readable");
			}
			Object newReceiver = readMethod.invoke(receiver, new Object[0]);
			// Class newClass = pinfo.getPropertyType();
			String nameRest = fullyQualifiedPropertyName.substring(idx + 1);
			this.setPropertyForFQN(newReceiver, nameRest, value);
		}
	}

	/**
	 * Convert value.
	 *
	 * @param value
	 *            the value
	 * @param targetType
	 *            the target type
	 * @return the object
	 */
	private static Object convertValue(Object value, Class<?> targetType) {
		boolean targetString = targetType.isAssignableFrom(String.class);
		if (targetString) {
			value = String.valueOf(value);
		} else if (!(value instanceof Byte) && (targetType == Byte.class || targetType == byte.class)) {
			value = Byte.valueOf(String.valueOf(value));
		} else if (!(value instanceof Boolean) && (targetType == Boolean.class || targetType == boolean.class)) {
			value = Boolean.valueOf(String.valueOf(value));
		} else if (!(value instanceof Short) && (targetType == Short.class || targetType == short.class)) {
			value = Short.valueOf(String.valueOf(value));
		} else if (!(value instanceof Integer) && (targetType == Integer.class || targetType == int.class)) {
			value = Integer.valueOf(String.valueOf(value));
		} else if (!(value instanceof Long) && (targetType == Long.class || targetType == long.class)) {
			value = Long.valueOf(String.valueOf(value));
		}
		return value;
	}
}
