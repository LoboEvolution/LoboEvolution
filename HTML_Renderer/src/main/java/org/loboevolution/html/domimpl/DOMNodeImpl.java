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
package org.loboevolution.html.domimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.loboevolution.html.HtmlAttributeProperties;
import org.loboevolution.html.HtmlRendererContext;
import org.loboevolution.html.dombl.ChildHTMLCollection;
import org.loboevolution.html.dombl.ModelNode;
import org.loboevolution.html.dombl.NodeVisitor;
import org.loboevolution.html.dombl.SkipVisitorException;
import org.loboevolution.html.dombl.StopVisitorException;
import org.loboevolution.html.dombl.UINode;
import org.loboevolution.html.domfilter.NodeFilter;
import org.loboevolution.html.domfilter.TextFilter;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.loboevolution.util.Objects;
import org.loboevolution.util.Strings;
import org.loboevolution.util.Urls;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

/**
 * The Class DOMNodeImpl.
 */
public abstract class DOMNodeImpl extends AbstractScriptableDelegate implements Node, ModelNode, HtmlAttributeProperties {

	/** The Constant EMPTY_ARRAY. */
	private static final DOMNodeImpl[] EMPTY_ARRAY = new DOMNodeImpl[0];

	/** The Constant INVALID_RENDER_STATE. */
	private static final RenderState INVALID_RENDER_STATE = new StyleSheetRenderState(null);

	/** The Constant logger. */
	protected static final Logger logger = LogManager.getLogger(DOMNodeImpl.class.getName());

	/** The ui node. */
	protected UINode uiNode;

	/** The node list. */
	protected ArrayList<Node> nodeList;

	/** The document. */
	protected volatile Document document;

	/**
	 * A tree lock is less deadlock-prone than a node-level lock. This is
	 * assigned in setOwnerDocument.
	 */
	private volatile Object treeLock = this;
		
	/** The user data. */
	private Map<String, Object> userData;
	
	/** The user data handlers. */
	private Map<String, UserDataHandler> userDataHandlers;

	/** The notifications suspended. */
	protected volatile boolean notificationsSuspended = false;

	/** The render state. */
	private RenderState renderState = INVALID_RENDER_STATE;
	
	/** The children collection. */
	private ChildHTMLCollection childrenCollection;

	/** The prefix. */
	private volatile String prefix;
	
	/** The parent node. */
	protected volatile Node parentNode;

	/**
	 * Sets the UI node.
	 *
	 * @param uiNode
	 *            the new UI node
	 */
	public void setUINode(UINode uiNode) {
		// Called in GUI thread always.
		this.uiNode = uiNode;
	}

	/**
	 * Gets the UI node.
	 *
	 * @return the UI node
	 */
	public UINode getUINode() {
		// Called in GUI thread always.
		return this.uiNode;
	}

	/**
	 * Tries to get a UINode associated with the current node. Failing that, it
	 * tries ancestors recursively. This method will return the closest
	 * <i>block-level</i> renderer node, if any.
	 *
	 * @return the UI node
	 */
	public UINode findUINode() {
		// Called in GUI thread always.
		UINode uiNode = this.uiNode;
		if (uiNode != null) {
			return uiNode;
		}
		DOMNodeImpl parentNode = (DOMNodeImpl) this.getParentNode();
		return parentNode == null ? null : parentNode.findUINode();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#appendChild(org.w3c.dom.Node)
	 */
	@Override
	public Node appendChild(Node newChild) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl == null) {
				nl = new ArrayList<Node>(3);
				this.nodeList = nl;
			}
			nl.add(newChild);
			if (newChild instanceof DOMNodeImpl) {
				((DOMNodeImpl) newChild).setParentImpl(this);
			}
		}

		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
		return newChild;
	}

	/**
	 * Removes the all children.
	 */
	public void removeAllChildren() {
		synchronized (this.getTreeLock()) {
			this.removeAllChildrenImpl();
		}
	}

	/**
	 * Removes the all children impl.
	 */
	protected void removeAllChildrenImpl() {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				nl.clear();
				// this.nodeList = null;
			}
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
	}

	/**
	 * Gets the node list.
	 *
	 * @param filter
	 *            the filter
	 * @return the node list
	 */
	public NodeList getNodeList(NodeFilter filter) {
		Collection<DOMNodeImpl> collection = new ArrayList<DOMNodeImpl>();
		synchronized (this.getTreeLock()) {
			this.appendChildrenToCollectionImpl(filter, collection);
		}
		return new DOMNodeListImpl(collection);
	}

	/**
	 * Gets the children array.
	 *
	 * @return the children array
	 */
	public DOMNodeImpl[] getChildrenArray() {
		ArrayList<Node> nl = this.nodeList;
		synchronized (this.getTreeLock()) {
			return nl == null ? null : (DOMNodeImpl[]) nl.toArray(DOMNodeImpl.EMPTY_ARRAY);
		}
	}

	/**
	 * Gets the child count.
	 *
	 * @return the child count
	 */
	public int getChildCount() {
		ArrayList<Node> nl = this.nodeList;
		synchronized (this.getTreeLock()) {
			return nl == null ? 0 : nl.size();
		}
	}

	/**
	 * Gets the children.
	 *
	 * @return the children
	 */
	public ChildHTMLCollection getChildren() {
		// Method required by JavaScript
		synchronized (this) {
			ChildHTMLCollection collection = this.childrenCollection;
			if (collection == null) {
				collection = new ChildHTMLCollection(this);
				this.childrenCollection = collection;
			}
			return collection;
		}
	}

	/**
	 * Creates an <code>ArrayList</code> of descendent nodes that the given
	 * filter condition.
	 *
	 * @param filter
	 *            the filter
	 * @param nestIntoMatchingNodes
	 *            the nest into matching nodes
	 * @return the descendents
	 */
	public ArrayList<DOMNodeImpl> getDescendents(NodeFilter filter, boolean nestIntoMatchingNodes) {
		ArrayList<DOMNodeImpl> al = new ArrayList<DOMNodeImpl>();
		synchronized (this.getTreeLock()) {
			this.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
		}
		return al;
	}

	/**
	 * Extracts all descendents that match the filter, except those descendents
	 * of nodes that match the filter.
	 *
	 * @param filter
	 *            the filter
	 * @param al
	 *            the al
	 * @param nestIntoMatchingNodes
	 *            the nest into matching nodes
	 */
	private void extractDescendentsArrayImpl(NodeFilter filter, ArrayList<DOMNodeImpl> al,
			boolean nestIntoMatchingNodes) {
		ArrayList<Node> nl = this.nodeList;
		if (nl != null) {
			Iterator<Node> i = nl.iterator();
			while (i.hasNext()) {
				DOMNodeImpl n = (DOMNodeImpl) i.next();
				if (filter.accept(n)) {
					al.add(n);
					if (nestIntoMatchingNodes) {
						n.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
					}
				} else if (n.getNodeType() == Node.ELEMENT_NODE) {
					n.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
				}
			}
		}
	}

	/**
	 * Append children to collection impl.
	 *
	 * @param filter
	 *            the filter
	 * @param collection
	 *            the collection
	 */
	private void appendChildrenToCollectionImpl(NodeFilter filter, Collection<DOMNodeImpl> collection) {
		ArrayList<Node> nl = this.nodeList;
		if (nl != null) {
			Iterator<Node> i = nl.iterator();
			while (i.hasNext()) {
				DOMNodeImpl node = (DOMNodeImpl) i.next();
				if (filter.accept(node)) {
					collection.add(node);
				}
				node.appendChildrenToCollectionImpl(filter, collection);
			}
		}
	}

	/**
	 * Should create a node with some cloned properties, like the node name, but
	 * not attributes or children.
	 *
	 * @return the node
	 */
	protected abstract Node createSimilarNode();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#cloneNode(boolean)
	 */
	@Override
	public Node cloneNode(boolean deep) {
		try {
			Node newNode = this.createSimilarNode();
			NodeList children = this.getChildNodes();
			int length = children.getLength();
			for (int i = 0; i < length; i++) {
				Node child = children.item(i);
				Node newChild = deep ? child.cloneNode(deep) : child;
				newNode.appendChild(newChild);
			}
			if (newNode instanceof Element) {
				Element elem = (Element) newNode;
				NamedNodeMap nnmap = this.getAttributes();
				if (nnmap != null) {
					int nnlength = nnmap.getLength();
					for (int i = 0; i < nnlength; i++) {
						Attr attr = (Attr) nnmap.item(i);
						elem.setAttributeNode((Attr) attr.cloneNode(true));
					}
				}
			}

			synchronized (this) {
				if (userDataHandlers != null && userData != null) {
					for (Object element : userDataHandlers.entrySet()) {
						Map.Entry entry = (Map.Entry) element;
						UserDataHandler handler = (UserDataHandler) entry.getValue();
						handler.handle(UserDataHandler.NODE_CLONED, (String) entry.getKey(),
								userData.get(entry.getKey()), this, newNode);
					}
				}
			}

			return newNode;
		} catch (Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}

	/**
	 * Gets the node index.
	 *
	 * @return the node index
	 */
	private int getNodeIndex() {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		return parent == null ? -1 : parent.getChildIndex(this);
	}

	/**
	 * Gets the child index.
	 *
	 * @param child
	 *            the child
	 * @return the child index
	 */
	public int getChildIndex(Node child) {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			return nl == null ? -1 : nl.indexOf(child);
		}
	}

	/**
	 * Gets the child at index.
	 *
	 * @param index
	 *            the index
	 * @return the child at index
	 */
	public Node getChildAtIndex(int index) {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			try {
				return nl == null ? null : (Node) nl.get(index);
			} catch (IndexOutOfBoundsException iob) {
				logger.error("getChildAtIndex(): Bad index=" + index + " for node=" + this + ".");
				return null;
			}
		}
	}

	/**
	 * Checks if is ancestor of.
	 *
	 * @param other
	 *            the other
	 * @return true, if is ancestor of
	 */
	private boolean isAncestorOf(Node other) {
		DOMNodeImpl parent = (DOMNodeImpl) other.getParentNode();
		if (parent == this) {
			return true;
		} else if (parent == null) {
			return false;
		} else {
			return this.isAncestorOf(parent);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#compareDocumentPosition(org.w3c.dom.Node)
	 */
	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		Node parent = this.getParentNode();
		if (!(other instanceof DOMNodeImpl)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
		}
		if (parent != null && parent == other.getParentNode()) {
			int thisIndex = this.getNodeIndex();
			int otherIndex = ((DOMNodeImpl) other).getNodeIndex();
			if (thisIndex == -1 || otherIndex == -1) {
				return Node.DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
			}
			if (thisIndex < otherIndex) {
				return Node.DOCUMENT_POSITION_FOLLOWING;
			} else {
				return Node.DOCUMENT_POSITION_PRECEDING;
			}
		} else if (this.isAncestorOf(other)) {
			return Node.DOCUMENT_POSITION_CONTAINED_BY;
		} else if (((DOMNodeImpl) other).isAncestorOf(this)) {
			return Node.DOCUMENT_POSITION_CONTAINS;
		} else {
			return Node.DOCUMENT_POSITION_DISCONNECTED;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getAttributes()
	 */
	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getOwnerDocument()
	 */
	@Override
	public Document getOwnerDocument() {
		return this.document;
	}

	/**
	 * Sets the owner document.
	 *
	 * @param value
	 *            the new owner document
	 */
	public void setOwnerDocument(Document value) {
		this.document = value;
		this.setTreeLock(value == null ? this : (Object) value);
	}

	/**
	 * Sets the owner document.
	 *
	 * @param value
	 *            the value
	 * @param deep
	 *            the deep
	 */
	public void setOwnerDocument(Document value, boolean deep) {
		this.document = value;
		this.setTreeLock(value == null ? this : (Object) value);
		if (deep) {
			synchronized (this.getTreeLock()) {
				ArrayList<Node> nl = this.nodeList;
				if (nl != null) {
					Iterator<Node> i = nl.iterator();
					while (i.hasNext()) {
						DOMNodeImpl child = (DOMNodeImpl) i.next();
						child.setOwnerDocument(value, deep);
					}
				}
			}
		}
	}

	/**
	 * Visit impl.
	 *
	 * @param visitor
	 *            the visitor
	 */
	protected void visitImpl(NodeVisitor visitor) {
		try {
			visitor.visit(this);
		} catch (SkipVisitorException sve) {
			return;
		} catch (StopVisitorException sve) {
			throw sve;
		}
		ArrayList<Node> nl = this.nodeList;
		if (nl != null) {
			Iterator<Node> i = nl.iterator();
			while (i.hasNext()) {
				DOMNodeImpl child = (DOMNodeImpl) i.next();
				try {
					// Call with child's synchronization
					child.visit(visitor);
				} catch (StopVisitorException sve) {
					throw sve;
				}
			}
		}
	}

	/**
	 * Visit.
	 *
	 * @param visitor
	 *            the visitor
	 */
	public void visit(NodeVisitor visitor) {
		synchronized (this.getTreeLock()) {
			this.visitImpl(visitor);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#insertBefore(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			int idx = nl == null ? -1 : nl.indexOf(refChild);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild not found");
			}
			nl.add(idx, newChild);
			if (newChild instanceof DOMNodeImpl) {
				((DOMNodeImpl) newChild).setParentImpl(this);
			}
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
		return newChild;
	}

	/**
	 * Insert at.
	 *
	 * @param newChild
	 *            the new child
	 * @param idx
	 *            the idx
	 * @return the node
	 * @throws DOMException
	 *             the DOM exception
	 */
	protected Node insertAt(Node newChild, int idx) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl == null) {
				nl = new ArrayList<Node>();
				this.nodeList = nl;
			}
			nl.add(idx, newChild);
			if (newChild instanceof DOMNodeImpl) {
				((DOMNodeImpl) newChild).setParentImpl(this);
			}
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
		return newChild;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#replaceChild(org.w3c.dom.Node, org.w3c.dom.Node)
	 */
	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			int idx = nl == null ? -1 : nl.indexOf(oldChild);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
			}
			nl.set(idx, newChild);
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
		return newChild;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#removeChild(org.w3c.dom.Node)
	 */
	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl == null || !nl.remove(oldChild)) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
			}
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
		return oldChild;
	}

	/**
	 * Removes the child at.
	 *
	 * @param index
	 *            the index
	 * @return the node
	 * @throws DOMException
	 *             the DOM exception
	 */
	public Node removeChildAt(int index) throws DOMException {
		try {
			synchronized (this.getTreeLock()) {
				ArrayList<Node> nl = this.nodeList;
				if (nl == null) {
					throw new DOMException(DOMException.INDEX_SIZE_ERR, "Empty list of children");
				}
				Node n = nl.remove(index);
				if (n == null) {
					throw new DOMException(DOMException.INDEX_SIZE_ERR, "No node with that index");
				}
				return n;
			}
		} finally {
			if (!this.notificationsSuspended) {
				this.informStructureInvalid();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#hasChildNodes()
	 */
	@Override
	public boolean hasChildNodes() {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			return nl != null && !nl.isEmpty();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getBaseURI()
	 */
	@Override
	public String getBaseURI() {
		Document document = this.document;
		return document == null ? null : document.getBaseURI();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getChildNodes()
	 */
	@Override
	public NodeList getChildNodes() {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			return new DOMNodeListImpl(nl == null ? Collections.EMPTY_LIST : nl);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getFirstChild()
	 */
	@Override
	public Node getFirstChild() {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			try {
				return nl == null ? null : (Node) nl.get(0);
			} catch (IndexOutOfBoundsException iob) {
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getLastChild()
	 */
	@Override
	public Node getLastChild() {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			try {
				return nl == null ? null : (Node) nl.get(nl.size() - 1);
			} catch (IndexOutOfBoundsException iob) {
				return null;
			}
		}
	}

	/**
	 * Gets the previous to.
	 *
	 * @param node
	 *            the node
	 * @return the previous to
	 */
	private Node getPreviousTo(Node node) {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			int idx = nl == null ? -1 : nl.indexOf(node);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "node not found");
			}
			try {
				return nl.get(idx - 1);
			} catch (IndexOutOfBoundsException iob) {
				return null;
			}
		}
	}

	/**
	 * Gets the next to.
	 *
	 * @param node
	 *            the node
	 * @return the next to
	 */
	private Node getNextTo(Node node) {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			int idx = nl == null ? -1 : nl.indexOf(node);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "node not found");
			}
			try {
				return nl.get(idx + 1);
			} catch (IndexOutOfBoundsException iob) {
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getPreviousSibling()
	 */
	@Override
	public Node getPreviousSibling() {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		return parent == null ? null : parent.getPreviousTo(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getNextSibling()
	 */
	@Override
	public Node getNextSibling() {
		DOMNodeImpl parent = (DOMNodeImpl) this.getParentNode();
		return parent == null ? null : parent.getNextTo(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getFeature(java.lang.String, java.lang.String)
	 */
	@Override
	public Object getFeature(String feature, String version) {
		// TODO What should this do?
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#setUserData(java.lang.String, java.lang.Object,
	 * org.w3c.dom.UserDataHandler)
	 */
	@Override
	public Object setUserData(String key, Object data, UserDataHandler handler) {
		if (org.loboevolution.html.parser.HtmlParser.MODIFYING_KEY.equals(key)) {
			boolean ns = Boolean.TRUE == data;
			this.notificationsSuspended = ns;
			if (!ns) {
				this.informNodeLoaded();
			}
		}
		// here we spent some effort preventing our maps from growing too much
		synchronized (this) {
			if (handler != null) {
				if (this.userDataHandlers == null) {
					this.userDataHandlers = new HashMap<String, UserDataHandler>();
				} else {
					this.userDataHandlers.put(key, handler);
				}
			}

			Map<String, Object> userData = this.userData;
			if (data != null) {
				if (userData == null) {
					userData = new HashMap<String, Object>();
					this.userData = userData;
				}
				return userData.put(key, data);
			} else if (userData != null) {
				return userData.remove(key);
			} else {
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getUserData(java.lang.String)
	 */
	@Override
	public Object getUserData(String key) {
		synchronized (this) {
			Map<String, Object> ud = this.userData;
			return ud == null ? null : ud.get(key);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getLocalName()
	 */
	@Override
	public abstract String getLocalName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#hasAttributes()
	 */
	@Override
	public boolean hasAttributes() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getNamespaceURI()
	 */
	@Override
	public String getNamespaceURI() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getNodeName()
	 */
	@Override
	public abstract String getNodeName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getNodeValue()
	 */
	@Override
	public abstract String getNodeValue() throws DOMException;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getPrefix()
	 */
	@Override
	public String getPrefix() {
		return this.prefix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#setPrefix(java.lang.String)
	 */
	@Override
	public void setPrefix(String prefix) throws DOMException {
		this.prefix = prefix;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#setNodeValue(java.lang.String)
	 */
	@Override
	public abstract void setNodeValue(String nodeValue) throws DOMException;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getNodeType()
	 */
	@Override
	public abstract short getNodeType();

	/**
	 * Gets the text content of this node and its descendents.
	 *
	 * @return the text content
	 * @throws DOMException
	 *             the DOM exception
	 */
	@Override
	public String getTextContent() throws DOMException {
		StringBuffer sb = new StringBuffer();
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				Iterator<Node> i = nl.iterator();
				while (i.hasNext()) {
					Node node = i.next();
					short type = node.getNodeType();
					switch (type) {
					case Node.CDATA_SECTION_NODE:
					case Node.TEXT_NODE:
					case Node.ELEMENT_NODE:
						String textContent = node.getTextContent();
						if (textContent != null) {
							sb.append(textContent);
						}
						break;
					default:
						break;
					}
				}
			}
		}
		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#setTextContent(java.lang.String)
	 */
	@Override
	public void setTextContent(String textContent) throws DOMException {
		synchronized (this.getTreeLock()) {
			this.removeChildrenImpl(new TextFilter());
			if (textContent != null && !"".equals(textContent)) {
				DOMTextImpl t = new DOMTextImpl(textContent);
				t.setOwnerDocument(this.document);
				t.setParentImpl(this);
				ArrayList<Node> nl = this.nodeList;
				if (nl == null) {
					nl = new ArrayList<Node>();
					this.nodeList = nl;
				}
				nl.add(t);
			}
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
	}

	/**
	 * Removes the children.
	 *
	 * @param filter
	 *            the filter
	 */
	protected void removeChildren(NodeFilter filter) {
		synchronized (this.getTreeLock()) {
			this.removeChildrenImpl(filter);
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
	}

	/**
	 * Removes the children impl.
	 *
	 * @param filter
	 *            the filter
	 */
	protected void removeChildrenImpl(NodeFilter filter) {
		ArrayList<Node> nl = this.nodeList;
		if (nl != null) {
			int len = nl.size();
			for (int i = len; --i >= 0;) {
				Node node = nl.get(i);
				if (filter.accept(node)) {
					nl.remove(i);
				}
			}
		}
	}

	/**
	 * Insert after.
	 *
	 * @param newChild
	 *            the new child
	 * @param refChild
	 *            the ref child
	 * @return the node
	 */
	public Node insertAfter(Node newChild, Node refChild) {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			int idx = nl == null ? -1 : nl.indexOf(refChild);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild not found");
			}
			nl.add(idx + 1, newChild);
			if (newChild instanceof DOMNodeImpl) {
				((DOMNodeImpl) newChild).setParentImpl(this);
			}
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
		return newChild;
	}

	/**
	 * Replace adjacent text nodes.
	 *
	 * @param node
	 *            the node
	 * @param textContent
	 *            the text content
	 * @return the text
	 */
	public Text replaceAdjacentTextNodes(Text node, String textContent) {
		try {
			synchronized (this.getTreeLock()) {
				ArrayList<Node> nl = this.nodeList;
				if (nl == null) {
					throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not a child");
				}
				int idx = nl.indexOf(node);
				if (idx == -1) {
					throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not a child");
				}
				int firstIdx = idx;
				List<Object> toDelete = new LinkedList<Object>();
				for (int adjIdx = idx; --adjIdx >= 0;) {
					Object child = this.nodeList.get(adjIdx);
					if (child instanceof Text) {
						firstIdx = adjIdx;
						toDelete.add(child);
					}
				}
				int length = this.nodeList.size();
				for (int adjIdx = idx; ++adjIdx < length;) {
					Object child = this.nodeList.get(adjIdx);
					if (child instanceof Text) {
						toDelete.add(child);
					}
				}
				this.nodeList.removeAll(toDelete);
				DOMTextImpl textNode = new DOMTextImpl(textContent);
				textNode.setOwnerDocument(this.document);
				textNode.setParentImpl(this);
				this.nodeList.add(firstIdx, textNode);
				return textNode;
			}
		} finally {
			if (!this.notificationsSuspended) {
				this.informStructureInvalid();
			}
		}
	}

	/**
	 * Replace adjacent text nodes.
	 *
	 * @param node
	 *            the node
	 * @return the text
	 */
	public Text replaceAdjacentTextNodes(Text node) {
		try {
			synchronized (this.getTreeLock()) {
				ArrayList<Node> nl = this.nodeList;
				if (nl == null) {
					throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not a child");
				}
				int idx = nl.indexOf(node);
				if (idx == -1) {
					throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not a child");
				}
				StringBuffer textBuffer = new StringBuffer();
				int firstIdx = idx;
				List<Object> toDelete = new LinkedList<Object>();
				for (int adjIdx = idx; --adjIdx >= 0;) {
					Object child = this.nodeList.get(adjIdx);
					if (child instanceof Text) {
						firstIdx = adjIdx;
						toDelete.add(child);
						textBuffer.append(((Text) child).getNodeValue());
					}
				}
				int length = this.nodeList.size();
				for (int adjIdx = idx; ++adjIdx < length;) {
					Object child = this.nodeList.get(adjIdx);
					if (child instanceof Text) {
						toDelete.add(child);
						textBuffer.append(((Text) child).getNodeValue());
					}
				}
				this.nodeList.removeAll(toDelete);
				DOMTextImpl textNode = new DOMTextImpl(textBuffer.toString());
				textNode.setOwnerDocument(this.document);
				textNode.setParentImpl(this);
				this.nodeList.add(firstIdx, textNode);
				return textNode;
			}
		} finally {
			if (!this.notificationsSuspended) {
				this.informStructureInvalid();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#getParentNode()
	 */
	@Override
	public Node getParentNode() {
		// Should it be synchronized? Could have side-effects.
		return this.parentNode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#isSameNode(org.w3c.dom.Node)
	 */
	@Override
	public boolean isSameNode(Node other) {
		return this == other;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#isSupported(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isSupported(String feature, String version) {
		return "HTML".equals(feature) && version.compareTo("4.01") <= 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#lookupNamespaceURI(java.lang.String)
	 */
	@Override
	public String lookupNamespaceURI(String prefix) {
		return null;
	}

	/**
	 * Equal attributes.
	 *
	 * @param arg
	 *            the arg
	 * @return true, if successful
	 */
	public boolean equalAttributes(Node arg) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#isEqualNode(org.w3c.dom.Node)
	 */
	@Override
	public boolean isEqualNode(Node arg) {
		return arg instanceof DOMNodeImpl && this.getNodeType() == arg.getNodeType()
				&& Objects.equals(this.getNodeName(), arg.getNodeName())
				&& Objects.equals(this.getNodeValue(), arg.getNodeValue())
				&& Objects.equals(this.getLocalName(), arg.getLocalName())
				&& Objects.equals(this.nodeList, ((DOMNodeImpl) arg).nodeList) && this.equalAttributes(arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#isDefaultNamespace(java.lang.String)
	 */
	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return namespaceURI == null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#lookupPrefix(java.lang.String)
	 */
	@Override
	public String lookupPrefix(String namespaceURI) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.w3c.dom.Node#normalize()
	 */
	@Override
	public void normalize() {
		synchronized (this.getTreeLock()) {
			ArrayList<Node> nl = this.nodeList;
			if (nl != null) {
				Iterator<Node> i = nl.iterator();
				List<Node> textNodes = new LinkedList<Node>();
				boolean prevText = false;
				while (i.hasNext()) {
					Node child = i.next();
					if (child.getNodeType() == Node.TEXT_NODE) {
						if (!prevText) {
							prevText = true;
							textNodes.add(child);
						}
					} else {
						prevText = false;
					}
				}
				i = textNodes.iterator();
				while (i.hasNext()) {
					Text text = (Text) i.next();
					this.replaceAdjacentTextNodes(text);
				}
			}
		}
		if (!this.notificationsSuspended) {
			this.informStructureInvalid();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.getNodeName();
	}

	/**
	 * Gets the user agent context.
	 *
	 * @return the user agent context
	 */
	public UserAgentContext getUserAgentContext() {
		Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getUserAgentContext();
		} else {
			return null;
		}
	}

	/**
	 * Gets the html renderer context.
	 *
	 * @return the html renderer context
	 */
	public HtmlRendererContext getHtmlRendererContext() {
		Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getHtmlRendererContext();
		} else {
			return null;
		}
	}

	/**
	 * Sets the parent impl.
	 *
	 * @param parent
	 *            the new parent impl
	 */
	protected final void setParentImpl(Node parent) {
		// Call holding treeLock.
		this.parentNode = parent;
	}

	// -----ModelNode implementation

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContext#getAlignmentX()
	 */
	/**
	 * Gets the alignment x.
	 *
	 * @return the alignment x
	 */
	public float getAlignmentX() {
		// TODO: Removable method?
		return 0.5f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContext#getAlignmentY()
	 */
	/**
	 * Gets the alignment y.
	 *
	 * @return the alignment y
	 */
	public float getAlignmentY() {
		return 0.5f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContext#getFullURL(String)
	 */
	@Override
	public URL getFullURL(String spec) throws MalformedURLException {
		Object doc = this.document;
		String cleanSpec = Urls.encodeIllegalCharacters(spec);
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getFullURL(cleanSpec);
		} else {
			return new URL(cleanSpec);
		}
	}

	/**
	 * Gets the document url.
	 *
	 * @return the document url
	 */
	public URL getDocumentURL() {
		Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getDocumentURL();
		} else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContext#getDocumentItem(
	 * String)
	 */
	@Override
	public Object getDocumentItem(String name) {
		Document document = this.document;
		return document == null ? null : document.getUserData(name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.render.RenderableContext#setDocumentItem(
	 * String, java.lang.Object)
	 */
	@Override
	public void setDocumentItem(String name, Object value) {
		Document document = this.document;
		if (document == null) {
			return;
		}
		document.setUserData(name, value, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.render.RenderableContext#isEqualOrDescendentOf(org.
	 * xamjwg.html.renderer.RenderableContext)
	 */
	@Override
	public final boolean isEqualOrDescendentOf(ModelNode otherContext) {
		if (otherContext == this) {
			return true;
		}
		Object parent = this.getParentNode();
		if (parent instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) parent).isEqualOrDescendentOf(otherContext);
		} else {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.ModelNode#getParentModelNode()
	 */
	@Override
	public final ModelNode getParentModelNode() {
		return (ModelNode) this.parentNode;
	}

	/**
	 * Inform size invalid.
	 */
	public void informSizeInvalid() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.sizeInvalidated(this);
		}
	}

	/**
	 * Inform look invalid.
	 */
	public void informLookInvalid() {
		this.forgetRenderState();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.lookInvalidated(this);
		}
	}

	/**
	 * Inform position invalid.
	 */
	public void informPositionInvalid() {
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.positionInParentInvalidated(this);
		}
	}

	/**
	 * Inform invalid.
	 */
	public void informInvalid() {
		// This is called when an attribute or child changes.
		this.forgetRenderState();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.invalidated(this);
		}
	}

	/**
	 * Inform structure invalid.
	 */
	public void informStructureInvalid() {
		// This is called when an attribute or child changes.
		this.forgetRenderState();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.structureInvalidated(this);
		}
	}

	/**
	 * Inform node loaded.
	 */
	protected void informNodeLoaded() {
		// This is called when an attribute or child changes.
		this.forgetRenderState();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.nodeLoaded(this);
		}
	}

	/**
	 * Inform external script loading.
	 */
	protected void informExternalScriptLoading() {
		// This is called when an attribute or child changes.
		this.forgetRenderState();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.externalScriptLoading(this);
		}
	}

	/**
	 * Inform layout invalid.
	 */
	public void informLayoutInvalid() {
		// This is called by the style properties object.
		this.forgetRenderState();
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.invalidated(this);
		}
	}

	/**
	 * Inform document invalid.
	 */
	public void informDocumentInvalid() {
		// This is called when an attribute or child changes.
		HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.allInvalidated(true);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.dombl.ModelNode#getRenderState()
	 */
	@Override
	public RenderState getRenderState() {
		// Generally called from the GUI thread, except for
		// offset properties.
		RenderState rs;
		synchronized (this.getTreeLock()) {
			rs = this.renderState;
			if (!INVALID_RENDER_STATE.equals(rs)) {
				return rs;
			}
			Object parent = this.parentNode;
			if (parent != null || this instanceof Document) {
				RenderState prs = this.getParentRenderState(parent);
				rs = this.createRenderState(prs);
				this.renderState = rs;
				return rs;
			} else {
				// Return null without caching.
				// Scenario is possible due to Javascript.
				return null;
			}
		}
	}

	/**
	 * Gets the parent render state.
	 *
	 * @param parent
	 *            the parent
	 * @return the parent render state
	 */
	protected final RenderState getParentRenderState(Object parent) {
		if (parent instanceof DOMNodeImpl) {
			return ((DOMNodeImpl) parent).getRenderState();
		} else {
			return null;
		}
	}

	/**
	 * Creates the render state.
	 *
	 * @param prevRenderState
	 *            the prev render state
	 * @return the render state
	 */
	protected RenderState createRenderState(RenderState prevRenderState) {
		return prevRenderState;
	}

	/**
	 * Forget render state.
	 */
	protected void forgetRenderState() {
		synchronized (this.getTreeLock()) {
			if (this.renderState != INVALID_RENDER_STATE) {
				this.renderState = INVALID_RENDER_STATE;
				// Note that getRenderState() "validates"
				// ancestor states as well.
				ArrayList<Node> nl = this.nodeList;
				if (nl != null) {
					Iterator<Node> i = nl.iterator();
					while (i.hasNext()) {
						((DOMNodeImpl) i.next()).forgetRenderState();
					}
				}
			}
		}
	}

	/**
	 * Gets the inner html.
	 *
	 * @return the inner html
	 */
	public String getInnerHTML() {
		StringBuffer buffer = new StringBuffer();
		synchronized (this) {
			this.appendInnerHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/**
	 * Append inner html impl.
	 *
	 * @param buffer
	 *            the buffer
	 */
	protected void appendInnerHTMLImpl(StringBuffer buffer) {
		ArrayList<Node> nl = this.nodeList;
		int size;
		if (nl != null && (size = nl.size()) > 0) {
			for (int i = 0; i < size; i++) {
				Node child = nl.get(i);
				if (child instanceof HTMLElementImpl) {
					((HTMLElementImpl) child).appendOuterHTMLImpl(buffer);
				} else if (child instanceof Comment) {
					buffer.append("<!--" + ((Comment) child).getTextContent() + "-->");
				} else if (child instanceof Text) {
					String text = ((Text) child).getTextContent();
					String encText = this.htmlEncodeChildText(text);
					buffer.append(encText);
				} else if (child instanceof ProcessingInstruction) {
					buffer.append(child.toString());
				}
			}
		}
	}

	/**
	 * Html encode child text.
	 *
	 * @param text
	 *            the text
	 * @return the string
	 */
	protected String htmlEncodeChildText(String text) {
		return Strings.strictHtmlEncode(text, false);
	}

	/**
	 * Gets the inner text.
	 *
	 * @return the inner text
	 */
	public String getInnerText() {
		StringBuffer buffer = new StringBuffer();
		synchronized (this.getTreeLock()) {
			this.appendInnerTextImpl(buffer);
		}
		return buffer.toString();
	}

	/**
	 * Append inner text impl.
	 *
	 * @param buffer
	 *            the buffer
	 */
	protected void appendInnerTextImpl(StringBuffer buffer) {
		ArrayList<Node> nl = this.nodeList;
		if (nl == null) {
			return;
		}
		int size = nl.size();
		if (size == 0) {
			return;
		}
		for (int i = 0; i < size; i++) {
			Node child = nl.get(i);
			if (child instanceof DOMElementImpl) {
				((DOMElementImpl) child).appendInnerTextImpl(buffer);
			}
			if (child instanceof Text) {
				buffer.append(((Text) child).getTextContent());
			}
		}
	}

	/**
	 * Gets the a tree lock is less deadlock-prone than a node-level lock.
	 *
	 * @return the a tree lock is less deadlock-prone than a node-level lock
	 */
	public Object getTreeLock() {
		return treeLock;
	}

	/**
	 * Sets the a tree lock is less deadlock-prone than a node-level lock.
	 *
	 * @param treeLock
	 *            the new a tree lock is less deadlock-prone than a node-level
	 *            lock
	 */
	public void setTreeLock(Object treeLock) {
		this.treeLock = treeLock;
	}
}
