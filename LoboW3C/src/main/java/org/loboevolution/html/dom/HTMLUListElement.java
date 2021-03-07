package org.loboevolution.html.dom;

/**
 * Provides special properties (beyond those defined on the regular HTMLElement
 * interface it also has available to it by inheritance) for manipulating
 * unordered list elements.
 */
public interface HTMLUListElement extends HTMLElement {

	@Deprecated

	boolean isCompact();

	void setCompact(boolean compact);

	@Deprecated

	String getType();

	void setType(String type);

}
