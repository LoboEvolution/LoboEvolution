package org.loboevolution.html.dom;

import org.loboevolution.html.node.DOMTokenList;

/**
 * Reference information for external resources and the relationship of those
 * resources to a document and vice-versa. This object inherits all of the
 * properties and methods of the HTMLElement interface.
 */
public interface HTMLLinkElement extends HTMLElement {

	String getAs();

	void setAs(String as);

	/**
	 * Sets or retrieves the character set used to encode the object.
	 */
	@Deprecated

	String getCharset();

	void setCharset(String charset);

	String getCrossOrigin();

	void setCrossOrigin(String crossOrigin);

	boolean isDisabled();

	void setDisabled(boolean disabled);

	/**
	 * Sets or retrieves a destination URL or an anchor point.
	 */

	String getHref();

	void setHref(String href);

	/**
	 * Sets or retrieves the language code of the object.
	 */

	String getHreflang();

	void setHreflang(String hreflang);

	String getImageSizes();

	void setImageSizes(String imageSizes);

	String getImageSrcset();

	void setImageSrcset(String imageSrcset);

	String getIntegrity();

	void setIntegrity(String integrity);

	/**
	 * Sets or retrieves the media type.
	 */

	String getMedia();

	void setMedia(String media);

	String getReferrerPolicy();

	void setReferrerPolicy(String referrerPolicy);

	/**
	 * Sets or retrieves the relationship between the object and the destination of
	 * the link.
	 */

	String getRel();

	void setRel(String rel);

	DOMTokenList getRelList();

	/**
	 * Sets or retrieves the relationship between the object and the destination of
	 * the link.
	 */
	@Deprecated

	String getRev();

	void setRev(String rev);

	DOMTokenList getSizes();

	/**
	 * Sets or retrieves the window or frame at which to target content.
	 */
	@Deprecated

	String getTarget();

	void setTarget(String target);

	/**
	 * Sets or retrieves the MIME type of the object.
	 */

	String getType();

	void setType(String type);

}
