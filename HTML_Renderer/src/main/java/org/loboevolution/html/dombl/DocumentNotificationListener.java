/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.dombl;

import org.loboevolution.html.domimpl.DOMNodeImpl;

/**
 * A listener of document changes.
 *
 * @see DocumentNotificationEvent
 */
public interface DocumentNotificationListener {

	/**
	 * Called if a property related to the node's size has changed.
	 *
	 * @param node
	 *            the node
	 */
	void sizeInvalidated(DOMNodeImpl node);

	/**
	 * Called if something such as a color or decoration has changed. This would
	 * be something which does not affect the rendered size.
	 *
	 * @param node
	 *            the node
	 */
	void lookInvalidated(DOMNodeImpl node);

	/**
	 * Changed if the position of the node in a parent has changed.
	 *
	 * @param node
	 *            the node
	 */
	void positionInvalidated(DOMNodeImpl node);

	/**
	 * This is called when the node has changed, but it is unclear if it's a
	 * size change or a look change. Typically, a node attribute has changed,
	 * but the set of child nodes has not changed.
	 *
	 * @param node
	 *            the node
	 */
	void invalidated(DOMNodeImpl node);

	/**
	 * Called when the node (with all its contents) is first created by the
	 * parser.
	 *
	 * @param node
	 *            the node
	 */
	void nodeLoaded(DOMNodeImpl node);

	/**
	 * The children of the node might have changed.
	 *
	 * @param node
	 *            the node
	 */
	void structureInvalidated(DOMNodeImpl node);

	/**
	 * Called when a external script (a SCRIPT tag with a src attribute) is
	 * about to start loading.
	 *
	 * @param node
	 *            the node
	 */
	void externalScriptLoading(DOMNodeImpl node);

	/**
	 * This is called when the whole document is potentially invalid, e.g. when
	 * a new style sheet has been added.
	 */
	void allInvalidated();
}
