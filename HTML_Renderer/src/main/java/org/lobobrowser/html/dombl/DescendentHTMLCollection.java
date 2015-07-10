/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Dec 3, 2005
 */
package org.lobobrowser.html.dombl;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domfilter.NodeFilter;
import org.lobobrowser.html.domimpl.DOMElementImpl;
import org.lobobrowser.html.domimpl.DOMNodeImpl;
import org.lobobrowser.html.domimpl.HTMLDocumentImpl;
import org.lobobrowser.html.w3c.HTMLCollection;
import org.lobobrowser.js.AbstractScriptableDelegate;
import org.lobobrowser.util.Nodes;
import org.w3c.dom.Node;

/**
 * The Class DescendentHTMLCollection.
 */
public class DescendentHTMLCollection extends AbstractScriptableDelegate
implements HTMLCollection {

    /** The root node. */
    private final DOMNodeImpl rootNode;

    /** The node filter. */
    private final NodeFilter nodeFilter;

    /** The tree lock. */
    private final Object treeLock;

    /** The nest into matching nodes. */
    private final boolean nestIntoMatchingNodes;

    /**
     * Instantiates a new descendent html collection.
     *
     * @param node
     *            the node
     * @param filter
     *            the filter
     * @param treeLock
     *            the tree lock
     */
    public DescendentHTMLCollection(DOMNodeImpl node, NodeFilter filter,
            Object treeLock) {
        this(node, filter, treeLock, true);
    }

    /**
     * Instantiates a new descendent html collection.
     *
     * @param node
     *            the node
     * @param filter
     *            the filter
     * @param treeLock
     *            the tree lock
     * @param nestMatchingNodes
     *            the nest matching nodes
     */
    public DescendentHTMLCollection(DOMNodeImpl node, NodeFilter filter,
            Object treeLock, boolean nestMatchingNodes) {
        rootNode = node;
        nodeFilter = filter;
        this.treeLock = treeLock;
        this.nestIntoMatchingNodes = nestMatchingNodes;
        HTMLDocumentImpl document = (HTMLDocumentImpl) node.getOwnerDocument();
        document.addDocumentNotificationListener(new LocalNotificationListener(
                document, this));
    }

    /** The items by name. */
    private Map<String, DOMElementImpl> itemsByName = null;

    /** The items by index. */
    private List<DOMNodeImpl> itemsByIndex = null;

    /**
     * Ensure populated impl.
     */
    private void ensurePopulatedImpl() {
        if (this.itemsByName == null) {
            ArrayList<DOMNodeImpl> descendents = this.rootNode.getDescendents(
                    this.nodeFilter, this.nestIntoMatchingNodes);
            this.itemsByIndex = descendents == null ? Collections.emptyList()
                    : descendents;
            int size = descendents == null ? 0 : descendents.size();
            Map<String, DOMElementImpl> itemsByName = new HashMap<String, DOMElementImpl>(
                    (size * 3) / 2);
            this.itemsByName = itemsByName;
            for (int i = 0; i < size; i++) {
                Object descNode = descendents.get(i);
                if (descNode instanceof DOMElementImpl) {
                    DOMElementImpl element = (DOMElementImpl) descNode;
                    String id = element.getId();
                    if ((id != null) && (id.length() != 0)) {
                        itemsByName.put(id, element);
                    }
                    String name = element
                            .getAttribute(HtmlAttributeProperties.NAME);
                    if ((name != null) && (name.length() != 0)
                            && !name.equals(id)) {
                        itemsByName.put(name, element);
                    }
                }
            }
        }
    }

    /**
     * Invalidate.
     */
    private void invalidate() {
        synchronized (this.treeLock) {
            this.itemsByName = null;
            this.itemsByIndex = null;
        }
    }

    /**
     * Checks if is valid.
     *
     * @return true, if is valid
     */
    private boolean isValid() {
        synchronized (this.treeLock) {
            return (this.itemsByName != null) && (this.itemsByIndex != null);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLCollection#getLength()
     */
    @Override
    public int getLength() {
        synchronized (this.treeLock) {
            this.ensurePopulatedImpl();
            return this.itemsByIndex.size();
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLCollection#item(int)
     */
    @Override
    public Node item(int index) {
        synchronized (this.treeLock) {
            this.ensurePopulatedImpl();
            try {
                return this.itemsByIndex.get(index);
            } catch (IndexOutOfBoundsException iob) {
                return null;
            }
        }
    }

    /*
     * (non-Javadoc)
     * @see org.lobobrowser.html.w3c.HTMLCollection#namedItem(java.lang.String)
     */
    @Override
    public Node namedItem(String name) {
        synchronized (this.treeLock) {
            this.ensurePopulatedImpl();
            return this.itemsByName.get(name);
        }
    }

    /**
     * Index of.
     *
     * @param node
     *            the node
     * @return the int
     */
    public int indexOf(Node node) {
        synchronized (this.treeLock) {
            this.ensurePopulatedImpl();
            return this.itemsByIndex.indexOf(node);
        }
    }

    /**
     * The listener interface for receiving localNotification events. The class
     * that is interested in processing a localNotification event implements
     * this interface, and the object created with that class is registered with
     * a component using the component's
     * <code>addLocalNotificationListener</code> method. When the
     * localNotification event occurs, that object's appropriate method is
     * invoked.
     *
     * @see LocalNotificationEvent
     */
    private static class LocalNotificationListener extends
    DocumentNotificationAdapter {
        // Needs to be a static class with a weak reference to
        // the collection object.
        /** The document. */
        private final HTMLDocumentImpl document;

        /** The collection ref. */
        private final WeakReference<DescendentHTMLCollection> collectionRef;

        /**
         * Instantiates a new local notification listener.
         *
         * @param document
         *            the document
         * @param collection
         *            the collection
         */
        public LocalNotificationListener(final HTMLDocumentImpl document,
                final DescendentHTMLCollection collection) {
            super();
            this.document = document;
            this.collectionRef = new WeakReference<DescendentHTMLCollection>(
                    collection);
        }

        /*
         * (non-Javadoc)
         * @see
         * org.lobobrowser.html.dombl.DocumentNotificationAdapter#structureInvalidated
         * (org.lobobrowser.html.domimpl.DOMNodeImpl)
         */
        @Override
        public void structureInvalidated(DOMNodeImpl node) {
            DescendentHTMLCollection collection = this.collectionRef.get();
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

        /*
         * (non-Javadoc)
         * @see
         * org.lobobrowser.html.dombl.DocumentNotificationAdapter#nodeLoaded(org.lobobrowser
         * .html.domimpl.DOMNodeImpl)
         */
        @Override
        public void nodeLoaded(DOMNodeImpl node) {
            this.structureInvalidated(node);
        }
    }

}
