package org.loboevolution.html.style.setter;

import org.loboevolution.html.renderstate.BorderRenderState;
import org.loboevolution.html.style.AbstractCSS2Properties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.util.Strings;
import org.loboevolution.util.gui.ColorFactory;
import org.w3c.dom.css.CSSStyleDeclaration;

public class BorderSetter2 implements SubPropertySetter {

	/** The name. */
	private final String name;

	/**
	 * Instantiates a new border setter2.
	 *
	 * @param baseName
	 *            the base name
	 */
	public BorderSetter2(String baseName) {
		this.name = baseName;
	}
	
	@Override
	public void changeValue(AbstractCSS2Properties properties, String value, CSSStyleDeclaration declaration,
			boolean important) {
		properties.setPropertyValueLCAlt(this.name, value, important);
		if (!Strings.isBlank(value)) {
			String[] array = HtmlValues.splitCssValue(value);
			String color = null;
			String style = null;
			String width = null;
			for (String token : array) {
				if (BorderRenderState.isBorderStyle(token)) {
					style = token;
				} else if (ColorFactory.getInstance().isColor(token)) {
					color = token;
				} else {
					width = token;
				}
			}
			String name = this.name;
			if (color != null) {
				properties.setPropertyValueLCAlt(name + "-color", color, important);
			}
			if (width != null) {
				properties.setPropertyValueLCAlt(name + "-width", width, important);
			}
			if (style != null) {
				properties.setPropertyValueLCAlt(name + "-style", style, important);
			}
		}
	}
	
	/**
	 * Change value.
	 *
	 * @param properties
	 *            the properties
	 * @param value
	 *            the value
	 * @param declaration
	 *            the declaration
	 */
	public void changeValue(AbstractCSS2Properties properties, String value, CSSStyleDeclaration declaration) {
		this.changeValue(properties, value, declaration, true);
	}
}