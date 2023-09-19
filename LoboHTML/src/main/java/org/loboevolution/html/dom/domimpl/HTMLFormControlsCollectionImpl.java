package org.loboevolution.html.dom.domimpl;

import org.loboevolution.html.dom.HTMLFormControlsCollection;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.node.traversal.NodeFilter;

/**
 * <p>HTMLFormControlsCollectionImpl class.</p>
 */
public class HTMLFormControlsCollectionImpl extends HTMLCollectionImpl implements HTMLFormControlsCollection {

    /**
     * <p>
     * Constructor for HTMLCollectionImpl.
     * </p>
     *
     * @param rootNode a {@link NodeImpl} object.
     * @param filter   a {@link NodeFilter} object.
     */
    public HTMLFormControlsCollectionImpl(NodeImpl rootNode, NodeFilter filter) {
        super(rootNode, filter);
    }
}
