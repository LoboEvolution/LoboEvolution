package org.loboevolution.html.dom;

/**
 * Contains the descriptive information, or metadata, for a document. This
 * object inherits all of the properties and methods described in the
 * HTMLElement interface.
 */
public interface HTMLHeadElement extends HTMLElement {

	String getProfile();

	void setProfile(String profile);

}
