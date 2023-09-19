/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.nodeimpl.event;

import org.loboevolution.html.dom.nodeimpl.NodeImpl;

/**
 * A listener of document changes.
 */
public interface DocumentNotificationListener {
	/**
	 * This is called when the whole document is potentially invalid, e.g. when a
	 * new style sheet has been added.
	 */
	void allInvalidated();

	/**
	 * Called when a external script (a SCRIPT tag with a src attribute) is about to
	 * start loading.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	void externalScriptLoading(NodeImpl node);

	/**
	 * This is called when the node has changed, but it is unclear if it's a size
	 * change or a look change. Typically, a node attribute has changed, but the set
	 * of child nodes has not changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	void invalidated(NodeImpl node);

	/**
	 * Called if something such as a color or decoration has changed. This would be
	 * something which does not affect the rendered size.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	void lookInvalidated(NodeImpl node);

	/**
	 * Called when the node (with all its contents) is first created by the parser.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	void nodeLoaded(NodeImpl node);

	/**
	 * Changed if the position of the node in a parent has changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	void positionInvalidated(NodeImpl node);

	/**
	 * Called if a property related to the node's size has changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	void sizeInvalidated(NodeImpl node);

	/**
	 * The children of the node might have changed.
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} object.
	 */
	void structureInvalidated(NodeImpl node);
}
