package org.loboevolution.html.dom;

/**
 * Provides special properties (beyond those defined on the regular HTMLElement
 * interface it also has available to it by inheritance) for manipulating
 * ordered list elements.
 */
public interface HTMLOListElement extends HTMLElement {

	@Deprecated

	boolean isCompact();

	void setCompact(boolean compact);

	boolean isReversed();

	void setReversed(boolean reversed);

	/**
	 * The starting number.
	 */

	int getStart();

	void setStart(int start);

	String getType();

	void setType(String type);

	boolean getCompact();

}
