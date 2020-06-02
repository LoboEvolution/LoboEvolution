package org.loboevolution.html.style.setter;

import org.loboevolution.html.style.AbstractCSSProperties;

import com.gargoylesoftware.css.dom.CSSStyleDeclarationImpl;
import com.gargoylesoftware.css.util.CSSProperties;

public interface SubPropertySetter extends CSSProperties {
	
	void changeValue(AbstractCSSProperties properties, String newValue, CSSStyleDeclarationImpl declaration, boolean important);
}
