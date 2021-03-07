package org.loboevolution.html.dom;

import org.loboevolution.html.node.WindowEventHandlers;

/**
 * Provides special properties (beyond those inherited from the regular
 * HTMLElement interface) for manipulating &lt;body&gt; elements.
 */
public interface HTMLBodyElement extends HTMLElement, WindowEventHandlers {

	@Deprecated
	String getALink();

	void setALink(String aLink);

	@Deprecated

	String getBackground();

	void setBackground(String background);

	@Deprecated

	String getBgColor();

	void setBgColor(String bgColor);

	String getBgProperties();

	void setBgProperties(String bgProperties);

	@Deprecated

	String getLink();

	void setLink(String link);

	@Deprecated

	boolean isNoWrap();

	void setNoWrap(boolean noWrap);

	@Deprecated

	String getText();

	void setText(String text);

	@Deprecated

	String getVLink();

	void setVLink(String vLink);

}
