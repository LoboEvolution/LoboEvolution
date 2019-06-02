/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/
/*
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.domimpl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lobo.common.Nodes;
import org.lobobrowser.html.dom.HTMLCollection;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.w3c.dom.Node;

public class DescendentHTMLCollection extends AbstractScriptableDelegate implements HTMLCollection {
	private static class LocalNotificationListener extends DocumentNotificationAdapter {
		private final WeakReference collectionRef;
		// Needs to be a static class with a weak reference to
		// the collection object.
		private final HTMLDocumentImpl document;

		public LocalNotificationListener(final HTMLDocumentImpl document, final DescendentHTMLCollection collection) {
			super();
			this.document = document;
			this.collectionRef = new WeakReference(collection);
		}

		@Override
		public void nodeLoaded(NodeImpl node) {
			structureInvalidated(node);
		}

		@Override
		public void structureInvalidated(NodeImpl node) {
			final DescendentHTMLCollection collection = (DescendentHTMLCollection) this.collectionRef.get();
			if (collection == null) {
				// Gone!
				this.document.removeDocumentNotificationListener(this);
				return;
			}
			if (collection.isValid()) {
				if (Nodes.isSameOrAncestorOf(collection.rootNode, node)) {
					collection.invalidate();
				}
			}
		}
	}

	private List itemsByIndex = null;
	private Map itemsByName = null;
	private final boolean nestIntoMatchingNodes;

	private final NodeFilter nodeFilter;

	private final NodeImpl rootNode;

	private final Object treeLock;

	public DescendentHTMLCollection(NodeImpl node, NodeFilter filter, Object treeLock) {
		this(node, filter, treeLock, true);
	}

	/**
	 * @param node
	 * @param filter
	 */
	public DescendentHTMLCollection(NodeImpl node, NodeFilter filter, Object treeLock, boolean nestMatchingNodes) {
		this.rootNode = node;
		this.nodeFilter = filter;
		this.treeLock = treeLock;
		this.nestIntoMatchingNodes = nestMatchingNodes;
		final HTMLDocumentImpl document = (HTMLDocumentImpl) node.getOwnerDocument();
		document.addDocumentNotificationListener(new LocalNotificationListener(document, this));
	}

	private void ensurePopulatedImpl() {
		if (this.itemsByName == null) {
			final ArrayList descendents = this.rootNode.getDescendents(this.nodeFilter, this.nestIntoMatchingNodes);
			this.itemsByIndex = descendents == null ? Collections.EMPTY_LIST : descendents;
			final int size = descendents == null ? 0 : descendents.size();
			final Map itemsByName = new HashMap(size * 3 / 2);
			this.itemsByName = itemsByName;
			for (int i = 0; i < size; i++) {
				final Object descNode = descendents.get(i);
				if (descNode instanceof ElementImpl) {
					final ElementImpl element = (ElementImpl) descNode;
					final String id = element.getId();
					if (id != null && id.length() != 0) {
						itemsByName.put(id, element);
					}
					final String name = element.getAttribute("name");
					if (name != null && name.length() != 0 && !name.equals(id)) {
						itemsByName.put(name, element);
					}
				}
			}
		}
	}

	@Override
	public int getLength() {
		synchronized (this.treeLock) {
			ensurePopulatedImpl();
			return this.itemsByIndex.size();
		}
	}

	public int indexOf(Node node) {
		synchronized (this.treeLock) {
			ensurePopulatedImpl();
			return this.itemsByIndex.indexOf(node);
		}
	}

	private void invalidate() {
		synchronized (this.treeLock) {
			this.itemsByName = null;
			this.itemsByIndex = null;
		}
	}

	private boolean isValid() {
		synchronized (this.treeLock) {
			return this.itemsByName != null && this.itemsByIndex != null;
		}
	}

	@Override
	public Node item(int index) {
		synchronized (this.treeLock) {
			ensurePopulatedImpl();
			try {
				return (Node) this.itemsByIndex.get(index);
			} catch (final java.lang.IndexOutOfBoundsException iob) {
				return null;
			}
		}
	}

//	private final class NodeCounter implements NodeVisitor {
//		private int count = 0;
//		
//		public final void visit(Node node) {
//			if(nodeFilter.accept(node)) {
//				this.count++;
//				throw new SkipVisitorException();
//			}
//		}
//		
//		public int getCount() {
//			return this.count;
//		}
//	}	
//
//	private final class NodeScanner implements NodeVisitor {
//		private int count = 0;
//		private Node foundNode = null;
//		private final int targetIndex;
//		
//		public NodeScanner(int idx) {
//			this.targetIndex = idx;
//		}
//		
//		public final void visit(Node node) {
//			if(nodeFilter.accept(node)) {
//				if(this.count == this.targetIndex) {
//					this.foundNode = node;
//					throw new StopVisitorException();
//				}
//				this.count++;
//				throw new SkipVisitorException();
//			}
//		}
//		
//		public Node getNode() {
//			return this.foundNode;
//		}
//	}	
//
//	private final class NodeScanner2 implements NodeVisitor {
//		private int count = 0;
//		private int foundIndex = -1;
//		private final Node targetNode;
//		
//		public NodeScanner2(Node node) {
//			this.targetNode = node;
//		}
//		
//		public final void visit(Node node) {
//			if(nodeFilter.accept(node)) {
//				if(node == this.targetNode) {
//					this.foundIndex = this.count;
//					throw new StopVisitorException();
//				}
//				this.count++;
//				throw new SkipVisitorException();
//			}
//		}
//		
//		public int getIndex() {
//			return this.foundIndex;
//		}
//	}	

	@Override
	public Node namedItem(String name) {
		synchronized (this.treeLock) {
			ensurePopulatedImpl();
			return (Node) this.itemsByName.get(name);
		}
	}

}
