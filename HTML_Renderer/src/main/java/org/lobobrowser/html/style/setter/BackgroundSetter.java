package org.lobobrowser.html.style.setter;

import org.lobobrowser.html.style.AbstractCSS2Properties;
import org.lobobrowser.html.style.HtmlValues;
import org.lobobrowser.util.gui.ColorFactory;
import org.w3c.dom.css.CSSStyleDeclaration;

import com.steadystate.css.util.CSSProperties;

public class BackgroundSetter implements SubPropertySetter, CSSProperties {

	@Override
	public void changeValue(AbstractCSS2Properties properties, String newValue, CSSStyleDeclaration declaration,
			boolean important) {
		properties.setPropertyValueLCAlt(BACKGROUND, newValue, important);
		if (newValue != null && newValue.length() > 0) {
			String[] tokens = HtmlValues.splitCssValue(newValue);
			boolean hasXPosition = false;
			boolean hasYPosition = false;
			String color = null;
			String image = null;
			String backgroundRepeat = null;
			String position = null;
			for (String token : tokens) {
				if (ColorFactory.getInstance().isColor(token)) {
					color = token;
				} else if (HtmlValues.isUrl(token)) {
					image = token;
				} else if (HtmlValues.isBackgroundRepeat(token)) {
					backgroundRepeat = token;
				} else if (HtmlValues.isBackgroundPosition(token)) {
					if (hasXPosition && !hasYPosition) {
						position += " " + token;
						hasYPosition = true;
					} else {
						hasXPosition = true;
						position = token;
					}
				} else if ("none".equals(newValue.toLowerCase())) {
					color = "transparent";
					image = "none";
				}
			}
			if (color != null) {
				properties.setPropertyValueLCAlt(BACKGROUND_COLOR, color, important);
			}
			if (image != null) {
				properties.setPropertyValueProcessed(BACKGROUND_IMAGE, image, declaration, important);
			}
			if (backgroundRepeat != null) {
				properties.setPropertyValueLCAlt(BACKGROUND_REPEAT, backgroundRepeat, important);
			}
			if (position != null) {
				properties.setPropertyValueLCAlt(BACKGROUND_POSITION, position, important);
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
