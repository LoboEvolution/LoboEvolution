package org.lobobrowser.html.style.setter;

import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;
import org.w3c.dom.css.CSSStyleDeclaration;

public class FourCornersSetter implements SubPropertySetter {

	/** The prefix. */
	private final String prefix;

	/** The suffix. */
	private final String suffix;

	/** The property. */
	private final String property;

	/**
	 * Instantiates a new four corners setter.
	 *
	 * @param property
	 *            the property
	 * @param prefix
	 *            the prefix
	 * @param suffix
	 *            the suffix
	 */
	public FourCornersSetter(String property, String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
		this.property = property;
	}

	@Override
	public void changeValue(AbstractCSS2Properties properties, String newValue, CSSStyleDeclaration declaration,
			boolean important) {
		properties.setPropertyValueLCAlt(this.property, newValue, important);
		if (newValue != null && newValue.length() > 0) {
			String[] array = HtmlValues.splitCssValue(newValue);
			int size = array.length;
			if (size == 1) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				String value = array[0];
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, value, important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, value, important);
			} else if (size >= 4) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[3], important);
			} else if (size == 2) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
			} else if (size == 3) {
				String prefix = this.prefix;
				String suffix = this.suffix;
				properties.setPropertyValueLCAlt(prefix + "top" + suffix, array[0], important);
				properties.setPropertyValueLCAlt(prefix + "right" + suffix, array[1], important);
				properties.setPropertyValueLCAlt(prefix + "bottom" + suffix, array[2], important);
				properties.setPropertyValueLCAlt(prefix + "left" + suffix, array[1], important);
			}
		}
	}
	
	/**
	 * Change value.
	 *
	 * @param properties
	 *            the properties
	 * @param newValue
	 *            the new value
	 * @param declaration
	 *            the declaration
	 */
	public void changeValue(AbstractCSS2Properties properties, String newValue, CSSStyleDeclaration declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}
}