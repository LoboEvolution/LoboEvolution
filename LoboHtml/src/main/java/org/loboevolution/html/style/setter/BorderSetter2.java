package org.loboevolution.html.style.setter;

import org.loboevolution.common.Strings;
import org.loboevolution.html.style.AbstractCSSProperties;
import org.loboevolution.html.style.HtmlValues;
import org.loboevolution.laf.ColorFactory;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

public class BorderSetter2 implements SubPropertySetter {

	private final String name;

	public BorderSetter2(String baseName) {
		this.name = baseName;
	}

	public void changeValue(AbstractCSSProperties properties, String value, CSSStyleDeclarationImpl declaration) {
		this.changeValue(properties, value, declaration, true);
	}

	@Override
	public void changeValue(AbstractCSSProperties properties, String value, CSSStyleDeclarationImpl declaration, boolean important) {
		properties.setPropertyValueLCAlt(this.name, value, important);
		if (Strings.isNotBlank(value)) {
			final String[] array = HtmlValues.splitCssValue(value);
			String color = null;
			String style = null;
			String width = null;
			for (final String token : array) {
				if (HtmlValues.isBorderStyle(token)) {
					style = token;
				} else if (ColorFactory.getInstance().isColor(token)) {
					color = token;
				} else {
					width = token;
				}
			}
			final String name = this.name;
			if (style != null) {
				properties.setPropertyValueLCAlt(name + "-style", style, important);
				if(color == null) {
					color = "black";
				}
				
				if(width == null) {
					width = "2px";
				}
			}
			
			if (color != null) {
				properties.setPropertyValueLCAlt(name + "-color", color, important);
			}
			if (width != null) {
				properties.setPropertyValueLCAlt(name + "-width", width, important);
			}				
		}
	}
}