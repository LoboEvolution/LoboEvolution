/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */
/*
 * Created on Oct 9, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.node.DocumentFragment;
import org.loboevolution.html.node.Node;

/**
 * <p>DocumentFragmentImpl class. </p>
 */
public class DocumentFragmentImpl extends EventTargetImpl implements DocumentFragment {

	/** Constructor for DocumentFragmentImpl.*/
	public DocumentFragmentImpl() {
		super();
	}

	/** {@inheritDoc} */
	@Override
	public String getNodeName() {
		return "[object DocumentFragment]";
	}

	@Override
	public Node appendChild(Node newChild) {
		if (newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append a fragment.");
		}
		return super.appendChild(newChild);
	}
	@Override
	public Node prependChild(Node newChild) {
		if (newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append a fragment.");
		}
		return super.prependChild(newChild);
	}

	/** {@inheritDoc} */
	@Override
	public int getNodeType() {
		return Node.DOCUMENT_FRAGMENT_NODE;
	}
}
