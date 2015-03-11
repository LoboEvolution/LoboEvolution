package org.lobobrowser.html.dombl;

import org.lobobrowser.html.domimpl.DOMNodeImpl;


/**
 * A listener of document changes.
 */
public interface DocumentNotificationListener {
	
	/**
	 * Called if a property related to the node's size has changed.
	 *
	 * @param node the node
	 */
	public void sizeInvalidated(DOMNodeImpl node);

	/**
	 * Called if something such as a color or decoration has changed. This would
	 * be something which does not affect the rendered size.
	 *
	 * @param node the node
	 */
	public void lookInvalidated(DOMNodeImpl node);

	/**
	 * Changed if the position of the node in a parent has changed.
	 *
	 * @param node the node
	 */
	public void positionInvalidated(DOMNodeImpl node);

	/**
	 * This is called when the node has changed, but it is unclear if it's a
	 * size change or a look change. Typically, a node attribute has changed,
	 * but the set of child nodes has not changed.
	 *
	 * @param node the node
	 */
	public void invalidated(DOMNodeImpl node);

	/**
	 * Called when the node (with all its contents) is first created by the
	 * parser.
	 *
	 * @param node the node
	 */
	public void nodeLoaded(DOMNodeImpl node);

	/**
	 * The children of the node might have changed.
	 *
	 * @param node the node
	 */
	public void structureInvalidated(DOMNodeImpl node);

	/**
	 * Called when a external script (a SCRIPT tag with a src attribute) is
	 * about to start loading.
	 *
	 * @param node the node
	 */
	public void externalScriptLoading(DOMNodeImpl node);

	/**
	 * This is called when the whole document is potentially invalid, e.g. when
	 * a new style sheet has been added.
	 */
	public void allInvalidated();
}
