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
 * Created on Sep 3, 2005
 */
package org.lobobrowser.html.dom.domimpl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lobo.common.Strings;
import org.lobobrowser.html.parser.HtmlParser;
import org.lobobrowser.html.renderstate.RenderState;
import org.lobobrowser.html.renderstate.StyleSheetRenderState;
import org.lobobrowser.http.HtmlRendererContext;
import org.lobobrowser.http.UserAgentContext;
import org.lobobrowser.js.AbstractScriptableDelegate;
import java.util.Objects;
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

import org.lobo.common.Nodes;
import org.lobo.common.Urls;
import org.lobobrowser.html.dom.HTMLCollection;
import org.lobobrowser.html.dom.NodeFilter;
import org.lobobrowser.html.dom.filter.TextFilter;

public abstract class NodeImpl extends AbstractScriptableDelegate implements Node, ModelNode {
		
	private static final RenderState INVALID_RENDER_STATE = new StyleSheetRenderState(null);
	
	protected static final Logger logger = Logger.getLogger(NodeImpl.class.getName());
	
	private HTMLCollection childrenCollection;
	
	protected volatile Document document;
	
	protected NodeListImpl nodeList = new NodeListImpl();

	protected volatile boolean notificationsSuspended = false;

	protected volatile Node parentNode;

	private volatile String prefix;

	private RenderState renderState = INVALID_RENDER_STATE;

	protected volatile Object treeLock = this;

	protected UINode uiNode;

	private Map<String, Object> userData;

	private Map<String, UserDataHandler> userDataHandlers;

	public NodeImpl() {
		super();
	}
	
	@Override
	public Node appendChild(Node newChild) throws DOMException {
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
		for (Node n : Nodes.iterable(nodeList)) {
			NodeImpl node = (NodeImpl) n;
			if (filter.accept(node)) {
				collection.add(node);
			}
			node.appendChildrenToCollectionImpl(filter, collection);
		}
	}

	protected void appendInnerHTMLImpl(StringBuffer buffer) {
		for (Node child : Nodes.iterable(nodeList)) {
			if (child instanceof HTMLElementImpl) {
				((HTMLElementImpl) child).appendOuterHTMLImpl(buffer);
			} else if (child instanceof Comment) {
				buffer.append("<!--" + ((Comment) child).getTextContent() + "-->");
			} else if (child instanceof Text) {
				final String text = ((Text) child).getTextContent();
				final String encText = htmlEncodeChildText(text);
				buffer.append(encText);
			} else if (child instanceof ProcessingInstruction) {
				buffer.append(child.toString());
			}
		}
	}

	protected void appendInnerTextImpl(StringBuffer buffer) {
		for (Node child : Nodes.iterable(nodeList)) {
			if (child instanceof ElementImpl) {
				((ElementImpl) child).appendInnerTextImpl(buffer);
			}
			if (child instanceof Comment) {
				// skip
			} else if (child instanceof Text) {
				buffer.append(((Text) child).getTextContent());
			}
		}
	}

	@Override
	public Node cloneNode(boolean deep) {
		try {
			final Node newNode = createSimilarNode();
			final NodeList children = getChildNodes();
			final int length = children.getLength();
			for (int i = 0; i < length; i++) {
				final Node child = children.item(i);
				final Node newChild = deep ? child.cloneNode(deep) : child;
				newNode.appendChild(newChild);
			}
			if (newNode instanceof Element) {
				final Element elem = (Element) newNode;
				final NamedNodeMap nnmap = getAttributes();
				if (nnmap != null) {
					final int nnlength = nnmap.getLength();
					for (int i = 0; i < nnlength; i++) {
						final Attr attr = (Attr) nnmap.item(i);
						elem.setAttributeNode((Attr) attr.cloneNode(true));
					}
				}
			}

			synchronized (this) {
				if (this.userDataHandlers != null && this.userData != null) {
					for (final Entry<String, UserDataHandler> entry2 : this.userDataHandlers.entrySet()) {
						final Map.Entry<String, UserDataHandler> entry = entry2;
						final UserDataHandler handler = entry.getValue();
						handler.handle(UserDataHandler.NODE_CLONED, entry.getKey(), this.userData.get(entry.getKey()),
								this, newNode);
					}
				}
			}

			return newNode;
		} catch (final Exception err) {
			throw new IllegalStateException(err.getMessage());
		}
	}

	@Override
	public short compareDocumentPosition(Node other) throws DOMException {
		final Node parent = getParentNode();
		if (!(other instanceof NodeImpl)) {
			throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Unknwon node implementation");
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

	protected RenderState createRenderState(RenderState prevRenderState) {
		return prevRenderState;
	}

	/**
	 * Should create a node with some cloned properties, like the node name, but not
	 * attributes or children.
	 */
	protected abstract Node createSimilarNode();

	public boolean equalAttributes(Node arg) {
		return false;
	}

	/**
	 * Extracts all descendents that match the filter, except those descendents of
	 * nodes that match the filter.
	 * 
	 * @param filter
	 * @param al
	 */
	private void extractDescendentsArrayImpl(NodeFilter filter, ArrayList<Node> al, boolean nestIntoMatchingNodes) {
		for (Node node : Nodes.iterable(nodeList)) {
			NodeImpl n = (NodeImpl) node;
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

	/**
	 * Tries to get a UINode associated with the current node. Failing that, it
	 * tries ancestors recursively. This method will return the closest
	 * <i>block-level</i> renderer node, if any.
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

	protected void forgetRenderState() {
		synchronized (this.treeLock) {
			if (this.renderState != INVALID_RENDER_STATE) {
				this.renderState = INVALID_RENDER_STATE;
				for (Node node : Nodes.iterable(nodeList)) {
					((NodeImpl) node).forgetRenderState();
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RenderableContext#getAlignmentX()
	 */
	public float getAlignmentX() {
		return 0.5f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RenderableContext#getAlignmentY()
	 */
	public float getAlignmentY() {
		return 0.5f;
	}

	@Override
	public NamedNodeMap getAttributes() {
		return null;
	}

	@Override
	public String getBaseURI() {
		final Document document = this.document;
		return document == null ? null : document.getBaseURI();
	}

	Node getChildAtIndex(int index) {
		return this.nodeList.get(index);
	}

	int getChildCount() {
		return nodeList.getLength();
	}

	int getChildIndex(Node child) {
		return this.nodeList.indexOf(child);
	}

	@Override
	public NodeList getChildNodes() {
		return this.nodeList;
	}

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

	public NodeImpl[] getChildrenArray() {
		return this.nodeList.toArray();
	}

	/**
	 * Creates an <code>NodeList</code> of descendent nodes that the given filter
	 * condition.
	 */
	public NodeList getDescendents(NodeFilter filter, boolean nestIntoMatchingNodes) {
		final ArrayList<Node> al = new ArrayList<Node>();
		synchronized (this.treeLock) {
			extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
		}
		return new NodeListImpl(al);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.xamjwg.html.renderer.RenderableContext#getDocumentItem(java.lang.String)
	 */
	@Override
	public Object getDocumentItem(String name) {
		final org.w3c.dom.Document document = this.document;
		return document == null ? null : document.getUserData(name);
	}

	public URL getDocumentURL() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getDocumentURL();
		} else {
			return null;
		}
	}

	@Override
	public Object getFeature(String feature, String version) {
		// TODO What should this do?
		return null;
	}

	@Override
	public Node getFirstChild() {
		return this.nodeList.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.xamjwg.html.renderer.RenderableContext#getFullURL(java.lang.String)
	 */
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

	public HtmlRendererContext getHtmlRendererContext() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getHtmlRendererContext();
		} else {
			return null;
		}
	}

	public String getInnerHTML() {
		final StringBuffer buffer = new StringBuffer();
		synchronized (this) {
			appendInnerHTMLImpl(buffer);
		}
		return buffer.toString();
	}

	/**
	 * Attempts to convert the subtree starting at this point to a close text
	 * representation. BR elements are converted to line breaks, and so forth.
	 */
	public String getInnerText() {
		final StringBuffer buffer = new StringBuffer();
		synchronized (this.treeLock) {
			appendInnerTextImpl(buffer);
		}
		return buffer.toString();
	}

	@Override
	public Node getLastChild() {
		if(this.nodeList == null)
			return null;

		int size = this.nodeList.getLength();
		int index = size - 1;
		if (size > index && index > -1) {
			return this.nodeList.get(this.nodeList.getLength() - 1);
		} else {
			return null;
		}
	}

	@Override
	public abstract String getLocalName();

	@Override
	public String getNamespaceURI() {
		return null;
	}

	@Override
	public Node getNextSibling() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		return parent == null ? null : parent.getNextTo(this);
	}
	
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
			return null;
		} else {
			return null;
		}
	}

	private Node getNextTo(Node node) {
		int idx = this.nodeList.indexOf(node);
		if (idx == -1) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "node not found");
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

	protected NodeList getNodeList(NodeFilter filter) {
		final List<Node> collection = new ArrayList<Node>();
		synchronized (this.treeLock) {
			appendChildrenToCollectionImpl(filter, collection);
		}
		return new NodeListImpl(collection);
	}

	@Override
	public abstract String getNodeName();

	@Override
	public abstract short getNodeType();

	@Override
	public abstract String getNodeValue() throws DOMException;

	@Override
	public Document getOwnerDocument() {
		return this.document;
	}

	@Override
	public final ModelNode getParentModelNode() {
		return (ModelNode) this.parentNode;
	}

	@Override
	public Node getParentNode() {
		return this.parentNode;
	}

	protected final RenderState getParentRenderState(Object parent) {
		if (parent instanceof NodeImpl) {
			return ((NodeImpl) parent).getRenderState();
		} else {
			return null;
		}
	}

	@Override
	public String getPrefix() {
		return this.prefix;
	}

	@Override
	public Node getPreviousSibling() {
		final NodeImpl parent = (NodeImpl) getParentNode();
		return parent == null ? null : parent.getPreviousTo(this);
	}
	
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
			return null;
		} else {
			return null;
		}
	}

	private Node getPreviousTo(Node node) {
		final int idx = this.nodeList.indexOf(node);
		if (idx == -1) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "node not found");
		}
		
		int size = this.nodeList.getLength();
		int index = idx - 1;
		if (size > index && index > -1) {
			return this.nodeList.item(index);
		} else {
			return null;
		}
	}
	

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
	 * Gets the text content of this node and its descendents.
	 */
	@Override
	public String getTextContent() throws DOMException {
		final StringBuffer sb = new StringBuffer();
		for (Node node : Nodes.iterable(nodeList)) {
			final short type = node.getNodeType();
			switch (type) {
			case Node.CDATA_SECTION_NODE:
			case Node.TEXT_NODE:
			case Node.ELEMENT_NODE:
				final String textContent = node.getTextContent();
				if (textContent != null) {
					sb.append(textContent);
				}
				break;
			default:
				break;
			}
		}
		return sb.toString();
	}

	public UINode getUINode() {
		// Called in GUI thread always.
		return this.uiNode;
	}

	public UserAgentContext getUserAgentContext() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocumentImpl) {
			return ((HTMLDocumentImpl) doc).getUserAgentContext();
		} else {
			return null;
		}
	}

	@Override
	public Object getUserData(String key) {
		synchronized (this) {
			final Map<String, Object> ud = this.userData;
			return ud == null ? null : ud.get(key);
		}
	}

	@Override
	public boolean hasAttributes() {
		return false;
	}

	@Override
	public boolean hasChildNodes() {
		return this.nodeList.getLength() > 0;
	}

	protected String htmlEncodeChildText(String text) {
		return Strings.strictHtmlEncode(text, false);
	}

	public void informDocumentInvalid() {
		// This is called when an attribute or child changes.
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.allInvalidated(true);
		}
	}

	protected void informExternalScriptLoading() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.externalScriptLoading(this);
		}
	}

	public void informInvalid() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.invalidated(this);
		}
	}

	public void informLayoutInvalid() {
		// This is called by the style properties object.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.invalidated(this);
		}
	}

	public void informLookInvalid() {
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.lookInvalidated(this);
		}
	}

	protected void informNodeLoaded() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.nodeLoaded(this);
		}
	}

	public void informPositionInvalid() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.positionInParentInvalidated(this);
		}
	}

	public void informSizeInvalid() {
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.sizeInvalidated(this);
		}
	}

	public void informStructureInvalid() {
		// This is called when an attribute or child changes.
		forgetRenderState();
		final HTMLDocumentImpl doc = (HTMLDocumentImpl) this.document;
		if (doc != null) {
			doc.structureInvalidated(this);
		}
	}

	public Node insertAfter(Node newChild, Node refChild) {
		final int idx = this.nodeList.indexOf(refChild);
		if (idx == -1) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild not found");
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

	protected Node insertAt(Node newChild, int idx) throws DOMException {
		this.nodeList.add(idx, newChild);
		if (newChild instanceof NodeImpl) {
			((NodeImpl) newChild).setParentImpl(this);
		}

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	@Override
	public Node insertBefore(Node newChild, Node refChild) throws DOMException {
		synchronized (this.treeLock) {
			final int idx = this.nodeList.indexOf(refChild);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild not found");
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

	@Override
	public boolean isDefaultNamespace(String namespaceURI) {
		return namespaceURI == null;
	}

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
	 * org.xamjwg.html.renderer.RenderableContext#isEqualOrDescendentOf(org.xamjwg.
	 * html.renderer.RenderableContext)
	 */
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

	@Override
	public boolean isSameNode(Node other) {
		return this == other;
	}

	@Override
	public boolean isSupported(String feature, String version) {
		return "HTML".equals(feature) && version.compareTo("4.01") <= 0;
	}

	@Override
	public String lookupNamespaceURI(String prefix) {
		return null;
	}

	@Override
	public String lookupPrefix(String namespaceURI) {
		return null;
	}

	@Override
	public void normalize() {
		final List<Node> textNodes = new LinkedList<Node>();
		boolean prevText = false;
		for (Node child : Nodes.iterable(nodeList)) {
			if (child.getNodeType() == Node.TEXT_NODE) {
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

	protected void removeAllChildren() {
		synchronized (this.treeLock) {
			removeAllChildrenImpl();
		}
	}

	protected void removeAllChildrenImpl() {
		this.nodeList.clear();
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	@Override
	public Node removeChild(Node oldChild) throws DOMException {
		synchronized (this.treeLock) {
			if (!this.nodeList.remove(oldChild)) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
			}
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return oldChild;
	}

	public Node removeChildAt(int index) throws DOMException {
		try {
			final Node n = this.nodeList.remove(index);
			if (n == null) {
				throw new DOMException(DOMException.INDEX_SIZE_ERR, "No node with that index");
			}
			return n;
		} finally {
			if (!this.notificationsSuspended) {
				informStructureInvalid();
			}
		}
	}

	protected void removeChildren(NodeFilter filter) {
		synchronized (this.treeLock) {
			removeChildrenImpl(filter);
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	protected void removeChildrenImpl(NodeFilter filter) {
		final int len = this.nodeList.getLength();
		for (int i = len; --i >= 0;) {
			final Node node = this.nodeList.item(i);
			if (filter.accept(node)) {
				this.nodeList.remove(i);
			}
		}
	}

	public Text replaceAdjacentTextNodes(Text node) {
		try {
				final int idx = nodeList.indexOf(node);
				if (idx == -1) {
					throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not a child");
				}
				final StringBuffer textBuffer = new StringBuffer();
				int firstIdx = idx;
				final List<Node> toDelete = new LinkedList<Node>();
				for (int adjIdx = idx; --adjIdx >= 0;) {
					final Node child = this.nodeList.item(adjIdx);
					if (child instanceof Text) {
						firstIdx = adjIdx;
						toDelete.add(child);
						textBuffer.append(((Text) child).getNodeValue());
					}
				}
				final int length = this.nodeList.getLength();
				for (int adjIdx = idx; ++adjIdx < length;) {
					final Node child = this.nodeList.item(adjIdx);
					if (child instanceof Text) {
						toDelete.add(child);
						textBuffer.append(((Text) child).getNodeValue());
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

	public Text replaceAdjacentTextNodes(Text node, String textContent) {
		try {
			final int idx = this.nodeList.indexOf(node);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not a child");
			}
			int firstIdx = idx;
			final List<Node> toDelete = new LinkedList<Node>();
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

	@Override
	public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
	
			final int idx = this.nodeList.indexOf(oldChild);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
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
	 * org.xamjwg.html.renderer.RenderableContext#setDocumentItem(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public void setDocumentItem(String name, Object value) {
		final org.w3c.dom.Document document = this.document;
		if (document == null) {
			return;
		}
		document.setUserData(name, value, null);
	}

	@Override
	public abstract void setNodeValue(String nodeValue) throws DOMException;

	public void setOwnerDocument(Document value) {
		this.document = value;
		this.treeLock = value == null ? this : (Object) value;
	}

	void setOwnerDocument(Document value, boolean deep) {
		this.document = value;
		this.treeLock = value == null ? this : (Object) value;
		if (deep) {
			for (Node node : Nodes.iterable(nodeList)) {
				final NodeImpl child = (NodeImpl) node;
				child.setOwnerDocument(value, deep);
			}
		}
	}

	final void setParentImpl(Node parent) {
		// Call holding treeLock.
		this.parentNode = parent;
	}

	@Override
	public void setPrefix(String prefix) throws DOMException {
		this.prefix = prefix;
	}

	@Override
	public void setTextContent(String textContent) throws DOMException {
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

	public void setUINode(UINode uiNode) {
		// Called in GUI thread always.
		this.uiNode = uiNode;
	}

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

	@Override
	public String toString() {
		return getNodeName();
	}

	void visit(NodeVisitor visitor) {
		synchronized (this.treeLock) {
			visitImpl(visitor);
		}
	}

	void visitImpl(NodeVisitor visitor) {
		try {
			visitor.visit(this);
		} catch (final SkipVisitorException sve) {
			return;
		} catch (final StopVisitorException sve) {
			throw sve;
		}
		for (Node node : Nodes.iterable(nodeList)) {
			final NodeImpl child = (NodeImpl) node;
			try {
				child.visit(visitor);
			} catch (final StopVisitorException sve) {
				throw sve;
			}
		}
	}

	public void warn(String message) {
		logger.log(Level.WARNING, message);
	}

	@Override
	public void warn(String message, Throwable err) {
		logger.log(Level.WARNING, message, err);
	}
}