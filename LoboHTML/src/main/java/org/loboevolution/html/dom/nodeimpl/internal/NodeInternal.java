package org.loboevolution.html.dom.nodeimpl.internal;

import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Strings;
import org.loboevolution.html.dom.nodeimpl.NodeImpl;
import org.loboevolution.html.dom.nodeimpl.NodeListImpl;
import org.loboevolution.html.node.Attr;
import org.loboevolution.html.node.Node;

/**
 * <p>Abstract NodeInternal class.</p>
 */
public abstract class NodeInternal extends NodeImpl {

    public Node appendChildInternal(final Node newChild) {

        if (Strings.isNotBlank(getNamespaceURI()) && Strings.isBlank(newChild.getNamespaceURI())) {
            newChild.setNamespaceURI(getNamespaceURI());
        }

        nodeList.add(newChild);
        newChild.setParentImpl(this);

        if(this instanceof Attr)
            updateValueFromChildren();

        return newChild;
    }

    public Node replaceChildInternal(final Node newChild, final Node oldChild) throws DOMException {

        final int idx = this.nodeList.indexOf(oldChild);
        if (idx == -1) {
            throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
        }

        final int idx2 = this.nodeList.indexOf(newChild);
        newChild.setParentImpl(this);
        this.nodeList.set(idx, newChild);
        setNodeValue(newChild.getNodeValue());
        if (idx2 != -1) {
            this.nodeList.remove(idx2);
        }

        if (!this.notificationsSuspended) {
            informStructureInvalid();
        }

        return oldChild;
    }

    public Node removeChildInternal(final Node oldChild) {
        synchronized (this) {

            if (oldChild == null) {
                throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild is null");
            }

            if (!this.nodeList.remove(oldChild)) {
                throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
            }
        }

        if (!this.notificationsSuspended) {
            informStructureInvalid();
        }
        oldChild.setParentImpl(this);

        if(this instanceof Attr)
            updateValueFromChildren();

        return oldChild;
    }

    protected void updateValueFromChildren() {
        NodeListImpl children = this.nodeList;

        if (children.getLength() == 0) {
            this.setNodeValue("");
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < children.getLength(); i++) {
            Node n = children.item(i);
            sb.append(n.getNodeValue());
        }

        this.setNodeValue(sb.toString());
    }
}
