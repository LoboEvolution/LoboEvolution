package org.loboevolution.html.node;

/**
 * A Node containing a doctype.
 */
public interface DocumentType extends Node {

	String getName();

	String getPublicId();

	String getSystemId();

}
