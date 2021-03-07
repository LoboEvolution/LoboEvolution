package org.loboevolution.html.dom;

import org.loboevolution.html.node.DOMTokenList;
import org.loboevolution.html.node.Document;
import org.loboevolution.html.node.js.WindowProxy;

/**
 * Provides special properties and methods (beyond those of the HTMLElement
 * interface it also has available to it by inheritance) for manipulating the
 * layout and presentation of inline frame elements.
 */
public interface HTMLIFrameElement extends HTMLElement {

	/**
	 * Sets or retrieves how the object is aligned with adjacent text.
	 */
	@Deprecated

	String getAlign();

	void setAlign(String align);

	String getAllow();

	void setAllow(String allow);

	boolean isAllowFullscreen();

	void setAllowFullscreen(boolean allowFullscreen);

	boolean isAllowPaymentRequest();

	void setAllowPaymentRequest(boolean allowPaymentRequest);

	/**
	 * Retrieves the document object of the page or frame.
	 */

	Document getContentDocument();

	/**
	 * Retrieves the object of the specified.
	 */

	WindowProxy getContentWindow();

	/**
	 * Sets or retrieves whether to display a border for the frame.
	 */
	@Deprecated

	String getFrameBorder();

	void setFrameBorder(String frameBorder);

	/**
	 * Sets or retrieves the height of the object.
	 */

	String getHeight();

	void setHeight(String height);

	/**
	 * Sets or retrieves a URI to a long description of the object.
	 */
	@Deprecated

	String getLongDesc();

	void setLongDesc(String longDesc);

	/**
	 * Sets or retrieves the top and bottom margin heights before displaying the
	 * text in a frame.
	 */
	@Deprecated

	String getMarginHeight();

	void setMarginHeight(String marginHeight);

	/**
	 * Sets or retrieves the left and right margin widths before displaying the text
	 * in a frame.
	 */
	@Deprecated

	String getMarginWidth();

	void setMarginWidth(String marginWidth);

	/**
	 * Sets or retrieves the frame name.
	 */

	String getName();

	void setName(String name);

	DOMTokenList getSandbox();

	/**
	 * Sets or retrieves whether the frame can be scrolled.
	 */
	@Deprecated

	String getScrolling();

	void setScrolling(String scrolling);

	/**
	 * Sets or retrieves a URL to be loaded by the object.
	 */

	String getSrc();

	void setSrc(String src);

	/**
	 * Sets or retrives the content of the page that is to contain.
	 */

	String getSrcdoc();

	void setSrcdoc(String srcdoc);

	/**
	 * Sets or retrieves the width of the object.
	 */

	String getWidth();

	void setWidth(String width);

	Document getSVGDocument();

}
