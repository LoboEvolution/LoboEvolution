package org.loboevolution.html.style.setter;

import org.loboevolution.html.style.AbstractCSSProperties;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;

public class BorderSetter1 implements SubPropertySetter {

	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration) {
		this.changeValue(properties, newValue, declaration, true);
	}

	@Override
	public void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration, boolean important) {
		properties.setPropertyValueLCAlt(BORDER, newValue, important);
		properties.setPropertyValueProcessed(BORDER_TOP, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_LEFT, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_BOTTOM, newValue, declaration, important);
		properties.setPropertyValueProcessed(BORDER_RIGHT, newValue, declaration, important);
	}
}