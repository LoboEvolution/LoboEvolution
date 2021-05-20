/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.html.dom.HTMLCollection;
import org.loboevolution.html.dom.HTMLElement;
import org.loboevolution.html.dom.NodeFilter;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.filter.TextFilter;
import org.loboevolution.html.node.*;
import org.loboevolution.html.parser.HtmlParser;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.http.HtmlRendererContext;
import org.loboevolution.http.UserAgentContext;
import org.loboevolution.js.AbstractScriptableDelegate;
import org.w3c.dom.UserDataHandler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Abstract NodeImpl class.</p>
 */
public abstract class NodeImpl extends AbstractScriptableDelegate implements Node, ModelNode, Cloneable {
		
	private static final RenderState INVALID_RENDER_STATE = new StyleSheetRenderState(null);
	
	/** Constant logger */
	protected static final Logger logger = Logger.getLogger(NodeImpl.class.getName());
	
	private HTMLCollection childrenCollection;
	
	protected volatile Document document;

	protected final NodeListImpl nodeList = new NodeListImpl();

	protected volatile boolean notificationsSuspended = false;

	protected volatile Node parentNode;

	private volatile String prefix;

	private RenderState renderState = INVALID_RENDER_STATE;

	protected volatile Object treeLock = this;

	protected UINode uiNode;

	private Map<String, Object> userData;

	private Map<String, UserDataHandler> userDataHandlers;

	/**
	 * <p>Constructor for NodeImpl.</p>
	 */
	public NodeImpl() {
		super();
	}
	
	/** {@inheritDoc} */
	@Override
	public Node appendChild(Node newChild) {
		synchronized (this.treeLock) {
			nodeList.add(newChild);
			if (newChild instanceof NodeImpl) {
				((NodeImpl) newChild).setParentImpl(this);
			}
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	private void appendChildrenToCollectionImpl(NodeFilter filter, Collection<Node> collection) {
		nodeList.forEach(child -> {
			NodeImpl node = (NodeImpl) child;
			if (filter.acceptNode(node)) {
				collection.add(node);
			}
			node.appendChildrenToCollectionImpl(filter, collection);
		});
	}

	/**
	 * <p>appendInnerHTMLImpl.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 */
	protected void appendInnerHTMLImpl(StringBuilder buffer) {
		nodeList.forEach(child -> {
			if (child instanceof HTMLElementImpl) {
				HTMLElementImpl elem = (HTMLElementImpl) child;
				if (elem.getOuter() != null) {
					buffer.append(elem.getOuter());
				} else {
					((HTMLElementImpl) child).appendOuterHTMLImpl(buffer);
				}
			} else if (child instanceof Comment) {
				buffer.append("<!--").append(((Comment) child).getTextContent()).append("-->");
			} else if (child instanceof Text) {
				final String text = child.getTextContent();
				final String encText = htmlEncodeChildText(text);
				buffer.append(encText);
			} else if (child instanceof ProcessingInstruction) {
				buffer.append(child.toString());
			}
		});
	}

	/**
	 * <p>appendInnerTextImpl.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 */
	protected void appendInnerTextImpl(StringBuilder buffer) {
		nodeList.forEach(child -> {
			if (child instanceof ElementImpl) {
				((ElementImpl) child).appendInnerTextImpl(buffer);
			}
			if (child instanceof Text) {
				buffer.append(child.getTextContent());
			}
		});
	}
	
	/** {@inheritDoc} */
	@Override
	public Node cloneNode() {
		return cloneNode(false);
	}

	/** {@inheritDoc} */
	@Override
	public Node cloneNode(boolean deep) {
		try {
			final Node newNode = clone();
			final int length = newNode.getChildNodes().getLength();
			if (deep && length == 0) {
				NodeListImpl childNodes = (NodeListImpl) getChildNodes();
				childNodes.forEach(child -> newNode.appendChild(child.cloneNode(true)));

				if (newNode instanceof Element) {
					final Element elem = (Element) newNode;
					final NamedNodeMap nnmap = elem.getAttributes();
					if (nnmap != null) {
						for (Attr attr : Nodes.iterable(nnmap)) {
							elem.setAttributeNode((Attr) attr.cloneNode(true));
						}
					}
				}
			}
			return newNode;
		} catch (final Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}
	
	/** {@inheritDoc} */
	@Override
	public boolean contains(Node other) {
		for (Node parent = other; parent != null; parent = parent.getParentElement()) {
			if (this == parent) {
				return true;
			}
		}
		return false;
	}
	
	
	/** {@inheritDoc} */
	@Override
	public short compareDocumentPosition(Node other) {
		final Node parent = getParentNode();
		if (!(other instanceof NodeImpl)) {
			throw new DOMException(Code.NOT_SUPPORTED_ERR, "Unknwon node implementation");
		}
		if (parent != null && parent == other.getParentNode()) {
			final int thisIndex = getNodeIndex();
			final int otherIndex = ((NodeImpl) other).getNodeIndex();
			if (thisIndex == -1 || otherIndex == -1) {
				return Node.DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
			}
			if (thisIndex < otherIndex) {
				return Node.DOCUMENT_POSITION_FOLLOWING;
			} else {
				return Node.DOCUMENT_POSITION_PRECEDING;
			}
		} else if (isAncestorOf(other)) {
			return Node.DOCUMENT_POSITION_CONTAINED_BY;
		} else if (((NodeImpl) other).isAncestorOf(this)) {
			return Node.DOCUMENT_POSITION_CONTAINS;
		} else {
			return Node.DOCUMENT_POSITION_DISCONNECTED;
		}
	}

	/**
	 * <p>createRenderState.</p>
	 *
	 * @param prevRenderState a {@link org.loboevolution.html.renderstate.RenderState} object.
	 * @return a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	protected RenderState createRenderState(RenderState prevRenderState) {
		return prevRenderState;
	}

	/**
	 * <p>equalAttributes.</p>
	 *
	 * @param arg a {@link org.loboevolution.html.node.Node} object.
	 * @return a boolean.
	 */
	public boolean equalAttributes(Node arg) {
		return false;
	}

	/**
	 * Extracts all descendents that match the filter, except those descendents of
	 * nodes that match the filter.
	 * 
	 * @param filter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 * @param al a {@link java.util.ArrayList} object.
	 */
	private void extractDescendentsArrayImpl(NodeFilter filter, ArrayList<Node> al, boolean nestIntoMatchingNodes) {
		nodeList.forEach(child -> {
			NodeImpl n = (NodeImpl) child;
			if (filter.acceptNode(n)) {
				al.add(n);
				if (nestIntoMatchingNodes) {
					n.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
				}
			} else if (n.getNodeType() == NodeType.ELEMENT_NODE) {
				n.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
			}
		});
	}

	/**
	 * Tries to get a UINode associated with the current node. Failing that, it
	 * tries ancestors recursively. This method will return the closest
	 * <i>block-level</i> renderer node, if any.
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.UINode} object.
	 */
	public UINode findUINode() {
		// Called in GUI thread always.
		final UINode uiNode = this.uiNode;
		if (uiNode != null) {
			return uiNode;
		}
		final NodeImpl parentNode = (NodeImpl) getParentNode();
		return parentNode == null ? null : parentNode.findUINode();
	}

	/**
	 * <p>forgetRenderState.</p>
	 */
	protected void forgetRenderState() {
		synchronized (this.treeLock) {
			if (this.renderState != INVALID_RENDER_STATE) {
				this.renderState = INVALID_RENDER_STATE;
				nodeList.forEach(child -> ((NodeImpl) child).forgetRenderState());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContext#getAlignmentX()
	 */
	/**
	 * <p>getAlignmentX.</p>
	 *
	 * @return a float.
	 */
	public float getAlignmentX() {
		return 0.5f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContext#getAlignmentY()
	 */
	/**
	 * <p>getAlignmentY.</p>
	 *
	 * @return a float.
	 */
	public float getAlignmentY() {
		return 0.5f;
	}

	/** {@inheritDoc} */
	@Override
	public String getBaseURI() {
		final Document document = this.document;
		return document == null ? null : document.getBaseURI();
	}

	/**
	 * <p>getChildAtIndex.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	public Node getChildAtIndex(int index) {
		return this.nodeList.get(index);
	}

	/**
	 * <p>getChildCount.</p>
	 *
	 * @return a int.
	 */
	public int getChildCount() {
		return nodeList.getLength();
	}

	/**
	 * <p>getChildIndex.</p>
	 *
	 * @param child a {@link org.loboevolution.html.node.Node} object.
	 * @return a int.
	 */
	public int getChildIndex(Node child) {
		return this.nodeList.indexOf(child);
	}

	/** {@inheritDoc} */
	@Override
	public NodeList getChildNodes() {
		return this.nodeList;
	}

	/**
	 * <p>getChildren.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.HTMLCollection} object.
	 */
	public HTMLCollection getChildren() {
		synchronized (this) {
			HTMLCollection collection = this.childrenCollection;
			if (collection == null) {
				collection = new HTMLCollectionImpl(this);
				this.childrenCollection = collection;
			}
			return collection;
		}
	}

	/**
	 * <p>getChildrenArray.</p>
	 *
	 * @return an array of {@link org.loboevolution.html.dom.nodeimpl.NodeImpl} objects.
	 */
	public NodeImpl[] getChildrenArray() {
		return this.nodeList.toArray();
	}

	/**
	 * Creates an NodeList of descendent nodes that the given filter
	 * condition.
	 *
	 * @param filter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 * @param nestIntoMatchingNodes a boolean.
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	public NodeList getDescendents(NodeFilter filter, boolean nestIntoMatchingNodes) {
		final ArrayList<Node> al = new ArrayList<>();
		synchronized (this.treeLock) {
			extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
		}
		return new NodeListImpl(al);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.RenderableContext#getDocumentItem(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public Object getDocumentItem(String name) {
		final Document document = this.document;
		return document == null ? null : document.getUserData(name);
	}

	/**
	 * <p>getDocumentURL.</p>
	 *
	 * @return a {@link java.net.URL} object.
	 */
	public URL getDocumentURL() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getDocumentURL();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node getFirstChild() {
		if (this.nodeList == null)
			return null;

		int size = this.nodeList.getLength();
		if (size > 0)
			return this.nodeList.get(0);
		return null;
	}
	
	
	/**
	 * <p>getFirstChildByFilter.</p>
	 *
	 * @param filter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	public Node getFirstChildByFilter(NodeFilter filter) {
		NodeListImpl nodeList = (NodeListImpl) getNodeList(filter);
		if (nodeList.getLength() == 0) {
			return null;
		} else {
			Optional<Node> findFirst = nodeList.stream().findFirst();
			if (findFirst.isPresent()) {
				return findFirst.get();
			}
		}
		return null;
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.loboevolution.html.rendered.RenderableContext#getFullURL(java.lang.String)
	 */
	/** {@inheritDoc} */
	@Override
	public URL getFullURL(String spec) throws MalformedURLException {
		final Object doc = this.document;
		final String cleanSpec  = Urls.encodeIllegalCharacters(spec);
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getFullURL(cleanSpec );
		} else {
			return new URL(cleanSpec );
		}
	}

	/**
	 * <p>getHtmlRendererContext.</p>
	 *
	 * @return a {@link org.loboevolution.http.HtmlRendererContext} object.
	 */
	public HtmlRendererContext getHtmlRendererContext() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getHtmlRendererContext();
		} else {
			return null;
		}
	}

	/**
	 * <p>getInnerHTML.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getInnerHTML() {
		final StringBuilder buffer = new StringBuilder();
		synchronized (this) {
			appendInnerHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/**
	 * Attempts to convert the subtree starting at this point to a close text
	 * representation. BR elements are converted to line breaks, and so forth.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getInnerText() {
		final StringBuilder buffer = new StringBuilder();
		synchronized (this.treeLock) {
			appendInnerTextImpl(buffer);
		}
		return buffer.toString();
	}

	/** {@inheritDoc} */
	@Override
	public Node getLastChild() {
		if (this.nodeList == null)
			return null;

		int size = this.nodeList.getLength();
		int index = size - 1;
		if (size > index && index > -1) {
			return this.nodeList.get(this.nodeList.getLength() - 1);
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public abstract String getLocalName();

	/** {@inheritDoc} */
	@Override
	public String getNamespaceURI() {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public Node getNextSibling() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		return parent == null ? null : parent.getNextTo(this);
	}
	
	/**
	 * <p>getNextElementSibling.</p>
	 *
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element getNextElementSibling() {
		final NodeImpl parent = (NodeImpl) this.getParentNode();
		if (parent != null) {
			Node next = this;
			do {
				next = parent.getNextTo(next);
				if ((next != null) && (next instanceof Element)) {
					return (Element) next;
				}
			} while (next != null);
		}
		return null;
	}

	private Node getNextTo(Node node) {
		int idx = this.nodeList.indexOf(node);
		if (idx == -1) {
			throw new DOMException(Code.NOT_FOUND_ERR, "node not found");
		}

		int size = this.nodeList.getLength();
		int index = idx + 1;
		if (size > index && index > -1) {
			return this.nodeList.item(index);
		} else {
			return null;
		}
	}

	private int getNodeIndex() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		return parent == null ? -1 : parent.getChildIndex(this);
	}

	/**
	 * <p>Getter for the field nodeList.</p>
	 *
	 * @param filter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	public NodeList getNodeList(NodeFilter filter) {
		final List<Node> collection = new ArrayList<>();
		synchronized (this.treeLock) {
			appendChildrenToCollectionImpl(filter, collection);
		}
		return new NodeListImpl(collection);
	}

	/** {@inheritDoc} */
	@Override
	public abstract String getNodeName();

	/** {@inheritDoc} */
	@Override
	public abstract NodeType getNodeType();

	/** {@inheritDoc} */
	@Override
	public abstract String getNodeValue() throws DOMException;

	/** {@inheritDoc} */
	@Override
	public Document getOwnerDocument() {
		return this.document;
	}

	/** {@inheritDoc} */
	@Override
	public final ModelNode getParentModelNode() {
		return (ModelNode) this.parentNode;
	}

	/** {@inheritDoc} */
	@Override
	public Node getParentNode() {
		return this.parentNode;
	}
	
	/** {@inheritDoc} */
	@Override
	public HTMLElement getParentElement() {
		if (this.parentNode instanceof HTMLElement) {
			return (HTMLElement) this.parentNode;
		} else {
			return null;
		}
	}

	/**
	 * <p>getParentRenderState.</p>
	 *
	 * @param parent a {@link java.lang.Object} object.
	 * @return a {@link org.loboevolution.html.renderstate.RenderState} object.
	 */
	protected final RenderState getParentRenderState(Object parent) {
		if (parent instanceof NodeImpl) {
			return ((NodeImpl) parent).getRenderState();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getPrefix() {
		return this.prefix;
	}

	/** {@inheritDoc} */
	@Override
	public Node getPreviousSibling() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		return parent == null ? null : parent.getPreviousTo(this);
	}
	
	/**
	 * <p>getPreviousElementSibling.</p>
	 *
	 * @return a {@link org.w3c.dom.Element} object.
	 */
	public Element getPreviousElementSibling() {
		final NodeImpl parent = (NodeImpl) this.getParentNode();
		if (parent != null) {
			Node previous = this;
			do {
				previous = parent.getPreviousTo(previous);
				if ((previous != null) && (previous instanceof Element)) {
					return (Element) previous;
				}
			} while (previous != null);
		}
		return null;
	}

	private Node getPreviousTo(Node node) {
		final int idx = this.nodeList.indexOf(node);
		if (idx == -1) {
			throw new DOMException(Code.NOT_FOUND_ERR, "node not found");
		}
		
		int size = this.nodeList.getLength();
		int index = idx - 1;
		if (size > index && index > -1) {
			return this.nodeList.item(index);
		} else {
			return null;
		}
	}
	

	/** {@inheritDoc} */
	@Override
	public RenderState getRenderState() {
		// Generally called from the GUI thread, except for
		// offset properties.
		RenderState rs;
		synchronized (this.treeLock) {
			rs = this.renderState;
			if (rs != INVALID_RENDER_STATE) {
				return rs;
			}
			final Object parent = this.parentNode;
			if (parent != null || this instanceof Document) {
				final RenderState prs = getParentRenderState(parent);
				rs = createRenderState(prs);
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
	 * {@inheritDoc}
	 *
	 * Gets the text content of this node and its descendents.
	 */
	@Override
	public String getTextContent() {
		final StringBuilder sb = new StringBuilder();
		nodeList.forEach(child -> {
			final NodeType type = child.getNodeType();
			switch (type) {
			case CDATA_SECTION_NODE:
			case TEXT_NODE:
			case ELEMENT_NODE:
				final String textContent = child.getTextContent();
				if (textContent != null) {
					sb.append(textContent);
				}
				break;
			default:
				break;
			}
		});
		return sb.toString();
	}

	/**
	 * <p>getUINode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.UINode} object.
	 */
	public UINode getUINode() {
		return this.uiNode;
	}

	/**
	 * <p>getUserAgentContext.</p>
	 *
	 * @return a {@link org.loboevolution.http.UserAgentContext} object.
	 */
	public UserAgentContext getUserAgentContext() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getUserAgentContext();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Object getUserData(String key) {
		synchronized (this) {
			final Map<String, Object> ud = this.userData;
			return ud == null ? null : ud.get(key);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean hasChildNodes() {
		return this.nodeList.getLength() > 0;
	}

	/**
	 * <p>htmlEncodeChildText.</p>
	 *
	 * @param text a {@link java.lang.String} object.
	 * @return a {@link java.lang.String} object.
	 */
	protected String htmlEncodeChildText(String text) {
		return Strings.strictHtmlEncode(text, false);
	}

	/**
	 * <p>informDocumentInvalid.</p>
	 */
	public void informDocumentInvalid() {
		// This is called when an attribute or child changes.
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.allInvalidated(true);
		}
	}

	/**
	 * <p>informExternalScriptLoading.</p>
	 */
	protected void informExternalScriptLoading() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.externalScriptLoading(this);
		}
	}

	/**
	 * <p>informInvalid.</p>
	 */
	public void informInvalid() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.invalidated(this);
		}
	}

	/**
	 * <p>informLayoutInvalid.</p>
	 */
	public void informLayoutInvalid() {
		// This is called by the style properties object.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.invalidated(this);
		}
	}

	/**
	 * <p>informLookInvalid.</p>
	 */
	public void informLookInvalid() {
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.lookInvalidated(this);
		}
	}

	/**
	 * <p>informNodeLoaded.</p>
	 */
	protected void informNodeLoaded() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.nodeLoaded(this);
		}
	}

	/**
	 * <p>informPositionInvalid.</p>
	 */
	public void informPositionInvalid() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.positionInParentInvalidated(this);
		}
	}

	/**
	 * <p>informSizeInvalid.</p>
	 */
	public void informSizeInvalid() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.sizeInvalidated(this);
		}
	}

	/**
	 * <p>informStructureInvalid.</p>
	 */
	public void informStructureInvalid() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.structureInvalidated(this);
		}
	}
	
	/**
	 * <p>getDocumentNode.</p>
	 *
	 * @return the document
	 */
	public Document getDocumentNode() {
		return document;
	}

	/**
	 * <p>insertAfter.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @param refChild a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	public Node insertAfter(Node newChild, Node refChild) {
		final int idx = this.nodeList.indexOf(refChild);
		if (idx == -1) {
			throw new DOMException(Code.NOT_FOUND_ERR, "refChild not found");
		}
		this.nodeList.add(idx + 1, newChild);
		if (newChild instanceof NodeImpl) {
			((NodeImpl) newChild).setParentImpl(this);
		}

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	/**
	 * <p>insertAt.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @param idx a int.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	protected Node insertAt(Node newChild, int idx) {
		this.nodeList.add(idx, newChild);
		if (newChild instanceof NodeImpl) {
			((NodeImpl) newChild).setParentImpl(this);
		}

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	/** {@inheritDoc} */
	@Override
	public Node insertBefore(Node newChild, Node refChild) {
		synchronized (this.treeLock) {
			final int idx = this.nodeList.indexOf(refChild);
			if (idx == -1) {
				throw new DOMException(Code.NOT_FOUND_ERR, "refChild not found");
			}
			this.nodeList.add(idx, newChild);
			if (newChild instanceof NodeImpl) {
				((NodeImpl) newChild).setParentImpl(this);
			}
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	private boolean isAncestorOf(Node other) {
		final NodeImpl parent = (NodeImpl) other.getParentNode();
		if (parent == this) {
			return true;
		} else if (parent == null) {
			return false;
		} else {
			return isAncestorOf(parent);
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return namespaceURI == null;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEqualNode(Node arg) {
		return arg instanceof NodeImpl && getNodeType() == arg.getNodeType()
				&& Objects.equals(getNodeName(), arg.getNodeName())
				&& Objects.equals(getNodeValue(), arg.getNodeValue())
				&& Objects.equals(getLocalName(), arg.getLocalName())
				&& Objects.equals(this.nodeList, ((NodeImpl) arg).nodeList) && equalAttributes(arg);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.RenderableContext#isEqualOrDescendentOf(org.loboevolution.html.rendered.RenderableContext)
	 */
	/** {@inheritDoc} */
	@Override
	public final boolean isEqualOrDescendentOf(ModelNode otherContext) {
		if (otherContext == this) {
			return true;
		}
		final Object parent = getParentNode();
		if (parent instanceof HTMLElementImpl) {
			return ((HTMLElementImpl) parent).isEqualOrDescendentOf(otherContext);
		} else {
			return false;
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSameNode(Node other) {
		return this == other;
	}

	/** {@inheritDoc} */
	@Override
	public String lookupNamespaceURI(String prefix) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public String lookupPrefix(String namespaceURI) {
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public void normalize() {
		final List<Node> textNodes = new LinkedList<>();
		boolean prevText = false;
		for (Iterator<Node> i= nodeList.iterator(); i.hasNext(); ) {
			Node child = i.next();
			if (child.getNodeType() == NodeType.TEXT_NODE) {
				if (!prevText) {
					prevText = true;
					textNodes.add(child);
				}
			} else {
				prevText = false;
			}
		}
		for (Node child : textNodes) {
			final Text text = (Text) child;
			this.replaceAdjacentTextNodes(text);
		}

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	/**
	 * <p>removeAllChildren.</p>
	 */
	protected void removeAllChildren() {
		synchronized (this.treeLock) {
			removeAllChildrenImpl();
		}
	}

	/**
	 * <p>removeAllChildrenImpl.</p>
	 */
	protected void removeAllChildrenImpl() {
		this.nodeList.clear();
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node removeChild(Node oldChild) {
		synchronized (this.treeLock) {
			if (!this.nodeList.remove(oldChild)) {
				throw new DOMException(Code.NOT_FOUND_ERR, "oldChild not found");
			}
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return oldChild;
	}

	/**
	 * <p>removeChildAt.</p>
	 *
	 * @param index a int.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 * @throws org.w3c.dom.DOMException if any.
	 */
	public Node removeChildAt(int index) {
		try {
			final Node n = this.nodeList.remove(index);
			if (n == null) {
				throw new DOMException(Code.INDEX_SIZE_ERR, "No node with that index");
			}
			return n;
		} finally {
			if (!this.notificationsSuspended) {
				informStructureInvalid();
			}
		}
	}

	/**
	 * <p>removeChildren.</p>
	 *
	 * @param filter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 */
	protected void removeTableChildren(NodeFilter filter) {
		synchronized (this.treeLock) {
			removeChildrenImpl(filter);
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	/**
	 * <p>removeChildrenImpl.</p>
	 *
	 * @param filter a {@link org.loboevolution.html.dom.NodeFilter} object.
	 */
	protected void removeChildrenImpl(NodeFilter filter) {
		AtomicInteger count = new AtomicInteger();
		AtomicInteger index = new AtomicInteger(-1);
		nodeList.forEach(node -> {
			if (node instanceof Element) {
				if (filter.acceptNode(node) && count.get() == 0) {
					index.set(nodeList.indexOf(node));
					count.incrementAndGet();
				}
			}
		});
		if (index.get() > -1) nodeList.remove(index.get());
	}

	/**
	 * <p>replaceAdjacentTextNodes.</p>
	 *
	 * @param node a {@link org.w3c.dom.Text} object.
	 * @return a {@link org.w3c.dom.Text} object.
	 */
	public Text replaceAdjacentTextNodes(Text node) {
		try {
				final int idx = nodeList.indexOf(node);
				if (idx == -1) {
					throw new DOMException(Code.NOT_FOUND_ERR, "Node not a child");
				}
				final StringBuilder textBuffer = new StringBuilder();
				int firstIdx = idx;
				final List<Node> toDelete = new LinkedList<>();
				for (int adjIdx = idx; --adjIdx >= 0;) {
					final Node child = this.nodeList.item(adjIdx);
					if (child instanceof Text) {
						firstIdx = adjIdx;
						toDelete.add(child);
						textBuffer.append(child.getNodeValue());
					}
				}
				final int length = this.nodeList.getLength();
				for (int adjIdx = idx; ++adjIdx < length;) {
					final Node child = this.nodeList.item(adjIdx);
					if (child instanceof Text) {
						toDelete.add(child);
						textBuffer.append(child.getNodeValue());
					}
				}
				this.nodeList.removeAll(toDelete);
				final TextImpl textNode = new TextImpl(textBuffer.toString());
				textNode.setOwnerDocument(this.document);
				textNode.setParentImpl(this);
				this.nodeList.add(firstIdx, textNode);
				return textNode;
		} finally {
			if (!this.notificationsSuspended) {
				informStructureInvalid();
			}
		}
	}

	/**
	 * <p>replaceAdjacentTextNodes.</p>
	 *
	 * @param node a {@link org.w3c.dom.Text} object.
	 * @param textContent a {@link java.lang.String} object.
	 * @return a {@link org.w3c.dom.Text} object.
	 */
	public Text replaceAdjacentTextNodes(Text node, String textContent) {
		try {
			final int idx = this.nodeList.indexOf(node);
			if (idx == -1) {
				throw new DOMException(Code.NOT_FOUND_ERR, "Node not a child");
			}
			int firstIdx = idx;
			final List<Node> toDelete = new LinkedList<>();
			for (int adjIdx = idx; --adjIdx >= 0;) {
				final Node child = this.nodeList.item(adjIdx);
				if (child instanceof Text) {
					firstIdx = adjIdx;
					toDelete.add(child);
				}
			}
			final int length = this.nodeList.getLength();
			for (int adjIdx = idx; ++adjIdx < length;) {
				final Node child = this.nodeList.item(adjIdx);
				if (child instanceof Text) {
					toDelete.add(child);
				}
			}
			this.nodeList.removeAll(toDelete);
			final TextImpl textNode = new TextImpl(textContent);
			textNode.setOwnerDocument(this.document);
			textNode.setParentImpl(this);
			this.nodeList.add(firstIdx, textNode);
			return textNode;

		} finally {
			if (!this.notificationsSuspended) {
				informStructureInvalid();
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node replaceChild(Node newChild, Node oldChild) {
	
			final int idx = this.nodeList.indexOf(oldChild);
			if (idx == -1) {
				throw new DOMException(Code.NOT_FOUND_ERR, "oldChild not found");
			}
			this.nodeList.set(idx, newChild);
		
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.loboevolution.html.rendered.RenderableContext#setDocumentItem(java.lang.String,
	 * java.lang.Object)
	 */
	/** {@inheritDoc} */
	@Override
	public void setDocumentItem(String name, Object value) {
		final Document document = this.document;
		if (document != null) {
			document.setUserData(name, value, null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public abstract void setNodeValue(String nodeValue) throws DOMException;

	/**
	 * <p>setOwnerDocument.</p>
	 *
	 * @param value a {@link org.w3c.dom.Document} object.
	 */
	public void setOwnerDocument(Document value) {
		this.document = value;
		this.treeLock = value == null ? this : value;
	}

	/**
	 * <p>setOwnerDocument.</p>
	 *
	 * @param value a {@link org.loboevolution.html.node.Document} object.
	 * @param deep a boolean.
	 */
	public void setOwnerDocument(Document value, boolean deep) {
		this.document = value;
		this.treeLock = value == null ? this : value;
		if (deep) {
			nodeList.forEach(node -> {
				final NodeImpl child = (NodeImpl) node;
				child.setOwnerDocument(value, deep);
			});
		}
	}

	/**
	 * <p>setParentImpl.</p>
	 *
	 * @param parent a {@link org.loboevolution.html.node.Node} object.
	 */
	protected final void setParentImpl(Node parent) {
		this.parentNode = parent;
	}

	/** {@inheritDoc} */
	@Override
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(String textContent) {
		synchronized (this.treeLock) {
			removeChildrenImpl(new TextFilter());
			if (Strings.isNotBlank(textContent)) {
				final TextImpl t = new TextImpl(textContent);
				t.setOwnerDocument(this.document);
				t.setParentImpl(this);
				this.nodeList.add(t);
			}
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	/**
	 * <p>setUINode.</p>
	 *
	 * @param uiNode a {@link org.loboevolution.html.dom.domimpl.UINode} object.
	 */
	public void setUINode(UINode uiNode) {
		// Called in GUI thread always.
		this.uiNode = uiNode;
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		if (HtmlParser.MODIFYING_KEY.equals(key)) {
			final boolean ns = Boolean.TRUE == data;
			this.notificationsSuspended = ns;
			if (!ns) {
				informNodeLoaded();
			}
		}
		synchronized (this) {
			if (handler != null) {
				if (this.userDataHandlers == null) {
					this.userDataHandlers = new HashMap<>();
				} else {
					this.userDataHandlers.remove(key);
				}
				this.userDataHandlers.put(key, handler);
			}

			Map<String, Object> userData = this.userData;
			if (data != null) {
				if (userData == null) {
					userData = new HashMap<>();
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
	
	/** {@inheritDoc} */
	@Override
	public boolean isIsConnected() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public Node getRootNode() {
		return this;
	}

	
	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getNodeName();
	}

	/**
	 * <p>visit.</p>
	 *
	 * @param visitor a {@link org.loboevolution.html.dom.nodeimpl.NodeVisitor} object.
	 */
	public void visit(NodeVisitor visitor) {
		synchronized (this.treeLock) {
			visitImpl(visitor);
		}
	}

	/**
	 * <p>visitImpl.</p>
	 *
	 * @param visitor a {@link org.loboevolution.html.dom.nodeimpl.NodeVisitor} object.
	 */
	protected void visitImpl(NodeVisitor visitor) {
		try {
			visitor.visit(this);
		} catch (Exception sve) {
			throw sve;
		}
		nodeList.forEach(node -> {
			final NodeImpl child = (NodeImpl) node;
			try {
				child.visit(visitor);
			} catch (Exception sve) {
				throw sve;
			}
		});
	}

	/**
	 * <p>warn.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	/** {@inheritDoc} */
	@Override
	public void warn(String message, Throwable err) {
		logger.log(Level.WARNING, message, err);
	}

	/** {@inheritDoc} */
	@Override
	public Node clone() {
		try {
			return (Node)super.clone();
		} catch (final CloneNotSupportedException e) {
			throw new IllegalStateException(e);
		}
	}
}
