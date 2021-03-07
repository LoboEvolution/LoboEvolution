package org.loboevolution.html.dom;

import org.loboevolution.html.node.Element;

/**
 * Any HTML element. Some elements directly implement this interface, while
 * others implement it via an interface that inherits it.
 */
public interface HTMLElement extends Element {

	String getAccessKey();

	String getAccessKeyLabel();

	String getAutocapitalize();

	String getDir();

	String getContentEditable();

	String getInnerText();

	String getLang();

	String getTitle();

	double getOffsetHeight();

	double getOffsetLeft();

	Element getOffsetParent();

	int getOffsetTop();

	int getOffsetWidth();

	boolean isSpellcheck();

	boolean isDraggable();

	boolean isHidden();

	boolean isTranslate();

	void setAccessKey(String accessKey);

	void setAutocapitalize(String autocapitalize);

	void setDir(String dir);

	void setDraggable(boolean draggable);

	void setHidden(boolean hidden);

	void setInnerText(String innerText);

	void setLang(String lang);

	void setSpellcheck(boolean spellcheck);

	void setContentEditable(String contenteditable);

	void setTranslate(boolean translate);

	void setTitle(String title);

	void click();

}
