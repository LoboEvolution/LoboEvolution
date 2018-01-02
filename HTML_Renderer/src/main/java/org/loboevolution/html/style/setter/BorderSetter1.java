package org.loboevolution.html.style.setter;

import org.loboevolution.html.style.AbstractCSS2Properties;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.steadystate.css.util.CSSProperties;

public class BorderSetter1 implements SubPropertySetter, CSSProperties {

	
	@Override
	public void changeValue(AbstractCSS2Properties properties, String newValue, CSSStyleDeclaration declaration, boolean important) {
		properties.setPropertyValueLCAlt(BORDER, newValue, important);
		properties.setPropertyValueProcessed(BORDER_TOP, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_LEFT, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_BOTTOM, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_RIGHT, newValue, declaration, important);
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
