/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
/*
 * Created on Sep 3, 2005
 */
package org.loboevolution.html.dom.nodeimpl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.htmlunit.cssparser.dom.DOMException;
import org.loboevolution.common.Nodes;
import org.loboevolution.common.Strings;
import org.loboevolution.common.Urls;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.gui.HtmlRendererContext;
import org.loboevolution.gui.LocalHtmlRendererConfig;
import org.loboevolution.html.dom.*;
import org.loboevolution.html.dom.filter.ElementFilter;
import org.loboevolution.html.dom.nodeimpl.event.EventTargetImpl;
import org.loboevolution.html.renderstate.RenderState;
import org.loboevolution.traversal.NodeFilter;
import org.loboevolution.html.dom.domimpl.*;
import org.loboevolution.html.dom.filter.TextFilter;
import org.loboevolution.html.dom.xpath.XPathNSResolverImpl;
import org.loboevolution.html.node.*;
import org.loboevolution.html.parser.XHtmlParser;
import org.loboevolution.html.renderstate.StyleSheetRenderState;
import org.loboevolution.html.xpath.XPathNSResolver;
import org.loboevolution.http.UserAgentContext;

import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <p>Abstract NodeImpl class.</p>
 */
@Slf4j
public abstract class NodeImpl extends EventTargetImpl implements Node, Cloneable {
	private static final RenderState INVALID_RENDER_STATE = new StyleSheetRenderState(null);

	protected volatile Document document;
	@Getter
	protected final NodeListImpl nodeList = new NodeListImpl();
	protected volatile boolean notificationsSuspended = false;
	protected volatile Node parentNode;
	private volatile String prefix;
	@Setter
	private String namespaceURI;
	private RenderState renderState = INVALID_RENDER_STATE;
	protected UINode uiNode;
	private Map<String, Object> userData;
	private Map<String, UserDataHandler> userDataHandlers;

	/**
	 * <p>Constructor for NodeImpl.</p>
	 */
	public NodeImpl() {
		super();
		setTarget(this);
	}

	/** {@inheritDoc} */
	@Override
	public Node appendChild(final Node newChild) {

		if (newChild.getNodeType() == Node.DOCUMENT_NODE) {
			final NodeListImpl list = getNodeList();
			list.forEach(n -> {
				if (n.getNodeType() == Node.DOCUMENT_NODE) {
					throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append a document.");
				}
			});
		}

		if (newChild.getNodeType() == Node.ATTRIBUTE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Use setAttributeNode to add attribute nodes.");
		}

		if (getNodeType() == Node.ATTRIBUTE_NODE) {
			if (newChild.getNodeType() == Node.ELEMENT_NODE) {
				throw new DOMException(DOMException.NOT_SUPPORTED_ERR, "Cannot insert Node.");
			}
		}

		if (newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
			final NodeListImpl list = getNodeList();
			list.forEach(n -> {
				if (n.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
					throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Only one doctype on document allowed.");
				}
			});
		}

		if (newChild.getOwnerDocument() != null && getOwnerDocument() != null &&
				!Objects.equals(newChild.getOwnerDocument(), getOwnerDocument())) {
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
		}

		if (newChild.getNodeType() == Node.ELEMENT_NODE) {

			if (newChild.isSameNode(this)) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot insert itself or an ancestor.");
			}

			if (getNodeType() == Node.DOCUMENT_NODE) {
				final NodeListImpl list = getNodeList();
				list.forEach(node ->{
					if(Objects.equals(node, newChild)) {
						throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot insert itself or an ancestor.");
					}


					if (node.getNodeType() == Node.ELEMENT_NODE && !(node instanceof HTMLHtmlElement)) {
						throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, " Only one element on document allowed.");
					}
				});
			}
		}

		newChild.setParentImpl(this);

		if (Strings.isNotBlank(getNamespaceURI()) && Strings.isBlank(newChild.getNamespaceURI())) {
			newChild.setNamespaceURI(getNamespaceURI());
		}

		nodeList.add(newChild);

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}

		return newChild;
	}

	/** {@inheritDoc} */
	@Override
	public Node prependChild(final Node newChild) {
		return prepend(newChild);
	}

	/** {@inheritDoc} */
	@Override
	public Node prepend(final Node newChild) {
		return insertBefore(newChild, this.getFirstChild());
	}

	/** {@inheritDoc} */
	@Override
	public void appendChildrenToCollectionImpl(final NodeFilter filter, final Collection<Node> collection) {
		nodeList.forEach(node -> {
			if (filter.acceptNode(node) == NodeFilter.FILTER_ACCEPT) {
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
	protected void appendInnerHTMLImpl(final StringBuilder buffer) {
		nodeList.forEach(child -> {
			if (child instanceof HTMLElementImpl) {
				((HTMLElementImpl) child).appendOuterHTMLImpl(buffer);
			} else if (child instanceof Comment) {
				buffer.append("<!--").append((child).getTextContent()).append("-->");
			} else if (child instanceof Text) {
				final String text = child.getTextContent();
				final String encText = htmlEncodeChildText(text);
				buffer.append(encText);
			} else if (child instanceof ProcessingInstruction) {
				buffer.append(child);
			}
		});
	}

	/**
	 * <p>appendInnerTextImpl.</p>
	 *
	 * @param buffer a {@link java.lang.StringBuilder} object.
	 */
	protected void appendInnerTextImpl(final StringBuilder buffer) {
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
	public Node cloneNode(final boolean deep) {
		try {
			final Node newNode = clone();
			final int length = newNode.getChildNodes().getLength();
			if (deep && length == 0) {
				final NodeListImpl childNodes = (NodeListImpl) getChildNodes();
				childNodes.forEach(child -> newNode.appendChild(child.cloneNode(true)));

				if (newNode instanceof Element elem) {
                    final NamedNodeMap nnmap = elem.getAttributes();
					if (nnmap != null) {
						for (final Node attr : Nodes.iterable(nnmap)) {
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
	public boolean contains(final Node other) {
		for (Node parent = other; parent != null; parent = parent.getParentElement()) {
			if (this == parent) {
				return true;
			}
		}
		return false;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isSupported(final String feature, final String version) {
		String featureSupp = feature;
		if(Strings.isNotBlank(featureSupp)) featureSupp = featureSupp.toLowerCase();
		if(Strings.isNotBlank(featureSupp) && featureSupp.startsWith("+")) featureSupp = featureSupp.substring(1).toLowerCase();
		return ("core".equals(featureSupp) || "xml".equals(featureSupp)
				|| "1.0".equals(version) || "2.0".equals(version) || "3.0".equals(version));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public short compareDocumentPosition(final Node other) {
        if (other == this) {
			return 0;
		}

		if (getNodeType() == DOCUMENT_NODE && other.getParentNode() == null) {
			return DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC | DOCUMENT_POSITION_DISCONNECTED;
		}

		if (other.getNodeType() == DOCUMENT_NODE && getParentNode() == null) {
			return DOCUMENT_POSITION_PRECEDING | DOCUMENT_POSITION_FOLLOWING;
		}

		if (Objects.equals(other.getDocumentNode(), getDocumentNode())) {
			final List<Node> ancestry = new ArrayList<>();
			ancestry.add(other);
			Node p = other.getParentNode();
			while (p != null) {
				if (p == this) {
					return DOCUMENT_POSITION_CONTAINED_BY | DOCUMENT_POSITION_FOLLOWING;
				}
				ancestry.add(p);
				p = p.getParentNode();
			}
			p = this.getParentNode();
			Node k = this;
			final int alen = ancestry.size();
			while (p != null) {
				for (int apos = 0; apos < alen; apos++) {
					if (p == ancestry.get(apos)) {
						final int sibpos = apos - 1;
						if (sibpos < 0) {
							return DOCUMENT_POSITION_CONTAINS | DOCUMENT_POSITION_PRECEDING;
						}
						final Node sibling = ancestry.get(sibpos);
                        if (sibling instanceof Attr) {
							final NamedNodeMap nnm = p.getAttributes();
							for (int i = nnm.getLength() - 1; i >= 0; i--) {
								final Node n = nnm.item(i);
								if (n == sibling) {
									return DOCUMENT_POSITION_FOLLOWING;
								} else if (n == k) {
									return DOCUMENT_POSITION_PRECEDING;
								}
							}
						} else {
							for (int i = p.getChildNodes().getLength() - 1; i >= 0; i--) {
								final Node n = p.getChildNodes().item(i);
								if (n == sibling) {
									return DOCUMENT_POSITION_FOLLOWING;
								} else if (n == k) {
									return DOCUMENT_POSITION_PRECEDING;
								}
							}
						}
						throw new IllegalStateException("Sibling nodes appear not to be siblings?");
					}
				}
				k = p;
				p = p.getParentNode();
			}
		}
		return Node.DOCUMENT_POSITION_DISCONNECTED;
	}

	/**
	 * <p>createRenderState.</p>
	 *
	 * @param prevRenderState a {@link RenderState} object.
	 * @return a {@link RenderState} object.
	 */
	protected RenderState createRenderState(final RenderState prevRenderState) {
		return prevRenderState;
	}

	@Override
	public NamedNodeMap getAttributes() {return null;}

	/** {@inheritDoc} */
	@Override
	public void extractDescendentsArrayImpl(final NodeFilter filter, final List<Node> al, final boolean nestIntoMatchingNodes) {
		nodeList.forEach(n -> {
			if (filter.acceptNode(n) == NodeFilter.FILTER_ACCEPT) {
				al.add(n);
				if (nestIntoMatchingNodes) {
					n.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
				}
			} else if (n.getNodeType() == Node.ELEMENT_NODE) {
				n.extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public UINode findUINode() {
		// Called in GUI thread always.
		final UINode uiNode = this.uiNode;
		if (uiNode != null) {
			return uiNode;
		}
		final Node parentNode = getParentNode();
		return parentNode == null ? null : parentNode.findUINode();
	}

	@Override
	public void forgetRenderState() {
		synchronized (this) {
			if (this.renderState != INVALID_RENDER_STATE) {
				this.renderState = INVALID_RENDER_STATE;
				nodeList.forEach(Node::forgetRenderState);
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	public String getBaseURI() {
		final Document document = this.document;
		return document == null ? null : document.getBaseURI();
	}

	/**
	 * <p>getChildIndex.</p>
	 *
	 * @param child a {@link org.loboevolution.html.node.Node} object.
	 * @return a {@link java.lang.Integer} object.
	 */
	public int getChildIndex(final Node child) {
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
		return new HTMLCollectionImpl(this, new ElementFilter(null));
	}

	/**
	 * Creates an NodeList of descendent nodes that the given filter
	 * condition.
	 *
	 * @param filter a {@link NodeFilter} object.
	 * @param nestIntoMatchingNodes a boolean.
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	public NodeList getDescendents(final NodeFilter filter, final boolean nestIntoMatchingNodes) {
		final ArrayList<Node> al = new ArrayList<>();
		synchronized (this) {
			extractDescendentsArrayImpl(filter, al, nestIntoMatchingNodes);
		}
		return new NodeListImpl(al);
	}

	/** {@inheritDoc} */
	@Override
	public Object getDocumentItem(final String name) {
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
		if (doc instanceof HTMLDocument) {
			return ((HTMLDocumentImpl) doc).getDocumentURL();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node getFirstChild() {
		return nodeList.
				stream().
				findFirst().
				orElse(null);
	}


	/**
	 * <p>getFirstChildByFilter.</p>
	 *
	 * @param filter a {@link NodeFilter} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 */
	public Node getFirstChildByFilter(final NodeFilter filter) {
		final NodeListImpl nodeList = (NodeListImpl) getNodeList(filter);
		if (nodeList.getLength() == 0) {
			return null;
		} else {
			final Optional<Node> findFirst = nodeList.stream().findFirst();
			if (findFirst.isPresent()) {
				return findFirst.get();
			}
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	public URL getFullURL(final String spec) throws Exception {
		final Object doc = this.document;
		final String cleanSpec  = Urls.encodeIllegalCharacters(spec);
		if (doc instanceof HTMLDocument) {
			return ((HTMLDocumentImpl) doc).getFullURL(cleanSpec );
		} else {
			return new URI(cleanSpec).toURL();
		}
	}

	/**
	 * <p>getHtmlRendererContext.</p>
	 *
	 * @return a {@link HtmlRendererContext} object.
	 */
	public HtmlRendererContext getHtmlRendererContext() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocument) {
			return ((HTMLDocumentImpl) doc).getHtmlRendererContext();
		} else {
			return null;
		}
	}

	/**
	 * <p>getHtmlRendererContext.</p>
	 *
	 * @return a {@link HtmlRendererConfig} object.
	 */
	public HtmlRendererConfig getHtmlRendererConfig() {
		final Object doc = this.document;
		if (doc instanceof HTMLDocument) {
			return ((HTMLDocumentImpl) doc).getConfig();
		} else {
			return new LocalHtmlRendererConfig();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node getLastChild() {
		final int size = this.nodeList.getLength();
		final int index = size - 1;
		if (size > index && index > -1) {
			return this.nodeList.get(index);
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
		return "*".equals(namespaceURI) ? null : namespaceURI;
	}

	/** {@inheritDoc} */
	@Override
	public Node getNextSibling() {
		final Node parent = getParentNode();
		return parent == null ? null : parent.getNextTo(this);
	}

	/**
	 * <p>getNextElementSibling.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	public Element getNextElementSibling() {
		final Node parent = this.getParentNode();
		if (parent != null) {
			Node next = this;
			do {
				next = parent.getNextTo(next);
				if (next instanceof Element) {
					return (Element) next;
				}
			} while (next != null);
		}
		return null;
	}

	@Override
	public Node getNextTo(final Node node) {
		final int idx = this.nodeList.indexOf(node);
		if (idx == -1) {
			return null;
		}

		final int size = this.nodeList.getLength();
		final int index = idx + 1;
		if (size > index) {
			return this.nodeList.item(index);
		} else {
			return null;
		}
	}

	/**
	 * <p>Getter for the field nodeList.</p>
	 *
	 * @param filter a {@link NodeFilter} object.
	 * @return a {@link org.loboevolution.html.node.NodeList} object.
	 */
	public NodeList getNodeList(final NodeFilter filter) {
		final List<Node> collection = new ArrayList<>();
		synchronized (this) {
			appendChildrenToCollectionImpl(filter, collection);
		}
		return new NodeListImpl(collection);
	}

	/** {@inheritDoc} */
	@Override
	public abstract String getNodeName();

	/** {@inheritDoc} */
	@Override
	public abstract int getNodeType();

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
	 * @return a {@link RenderState} object.
	 */
	protected final RenderState getParentRenderState(final Object parent) {
		if (parent instanceof Node) {
			return (RenderState) ((Node)parent).getRenderState();
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
		final Node parent = getParentNode();
		return parent == null ? null : parent.getPreviousTo(this);
	}

	/**
	 * <p>getPreviousElementSibling.</p>
	 *
	 * @return a {@link org.loboevolution.html.node.Element} object.
	 */
	public Element getPreviousElementSibling() {
		final Node parent = this.getParentNode();
		if (parent != null) {
			Node previous = this;
			do {
				previous = parent.getPreviousTo(previous);
				if (previous instanceof Element) {
					return (Element) previous;
				}
			} while (previous != null);
		}
		return null;
	}

	@Override
	public Node getPreviousTo(final Node node) {
		final int idx = this.nodeList.indexOf(node);
		if (idx == -1) {
			return null;
		}

		final int size = this.nodeList.getLength();
		final int index = idx - 1;
		if (size > index && index > -1) {
			return this.nodeList.item(index);
		} else {
			return null;
		}
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public RenderState getRenderState() {
		// Generally called from the GUI thread, except for
		// offset properties.
		RenderState rs;
		synchronized (this) {
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

		if(this instanceof DocumentType) {
			return null;
		}

		final StringBuilder sb = new StringBuilder();
		nodeList.forEach(child -> {
			final int type = child.getNodeType();
			switch (type) {
				case Node.CDATA_SECTION_NODE:
				case Node.TEXT_NODE:
				case Node.ELEMENT_NODE:
					final String textContent = child.getTextContent();
					if (textContent != null) {
						sb.append(textContent);
					}
					break;
				default:
					break;
			}
		});

		return sb.isEmpty() ? null : sb.toString();
	}

	/**
	 * <p>getUINode.</p>
	 *
	 * @return a {@link UINode} object.
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
		if (doc instanceof HTMLDocument) {
			return ((HTMLDocumentImpl) doc).getUserAgentContext();
		} else {
			return null;
		}
	}

	/** {@inheritDoc} */
	@Override
	public Object getUserData(final String key) {
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
	protected String htmlEncodeChildText(final String text) {
		return Strings.strictHtmlEncode(text, false);
	}

	/**
	 * <p>informInvalid.</p>
	 */
	public void informInvalid() {
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
	public Node insertAfter(final Node newChild, final Node refChild) {
		final int idx = this.nodeList.indexOf(refChild);
		if (idx == -1) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild not found");
		}

		if (newChild instanceof NodeImpl) {
			newChild.setParentImpl(this);
		}
		this.nodeList.add(idx + 1, newChild);

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	/**
	 * <p>insertAt.</p>
	 *
	 * @param newChild a {@link org.loboevolution.html.node.Node} object.
	 * @param idx a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 * @throws DOMException if any.
	 */
	protected Node insertAt(final Node newChild, final int idx) {

		if (newChild instanceof NodeImpl) {
			newChild.setParentImpl(this);
		}
		this.nodeList.add(idx, newChild);

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	/** {@inheritDoc} */
	@Override
	public Node insertBefore(final Node newChild, final Node refChild) {
		synchronized (this) {
			if(refChild == null) {
				appendChild(newChild);
				if (!this.notificationsSuspended) {
					informStructureInvalid();
				}
				return newChild;
			}

			if (newChild.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
				final NodeListImpl list = getNodeList();
				list.forEach(n -> {
					if (n.getNodeType() == Node.DOCUMENT_TYPE_NODE) {
						throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, " Only one doctype on document allowed.");
					}
				});
			}

			if (!getNodeList().contains(refChild)) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "Not a child of this node.");
			}

			if(!Objects.equals(newChild.getOwnerDocument(), getOwnerDocument())) {
				throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");
			}

			final int idx = this.nodeList.indexOf(refChild);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "refChild not found");
			}

			if (newChild.getNodeType() == Node.DOCUMENT_NODE) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot append a document.");
			}

			if (newChild.getNodeType() == Node.ATTRIBUTE_NODE) {
				throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Use setAttributeNode to add attribute nodes.");
			}

			if (newChild.getNodeType() == Node.ELEMENT_NODE) {

				if (newChild.isSameNode(this)) {
					throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Cannot insert itself or an ancestor.");
				}

			}


			if (newChild instanceof NodeImpl) {
				newChild.setParentImpl(this);
			}
			if (!this.nodeList.contains(newChild)) {
				this.nodeList.add(idx, newChild);
			}
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		return newChild;
	}

	/** {@inheritDoc} */
	@Override
	public boolean isDefaultNamespace(final String namespaceURI) {
		return Document.HTML_NAMESPACE_URI.equals(namespaceURI);
	}

	/** {@inheritDoc} */
	@Override
	public boolean isEqualNode(final Node arg) {
		return arg instanceof NodeImpl
				&& getNodeType() == arg.getNodeType()
				&& Objects.equals(getNodeName().toUpperCase(), arg.getNodeName().toUpperCase())
				&& Objects.equals(getNodeValue(), arg.getNodeValue())
				&& ((getLocalName() == null && getLocalName() == null) || Objects.equals(getLocalName().toUpperCase(), arg.getLocalName().toUpperCase()));

	}

	/** {@inheritDoc} */
	@Override
	public final boolean isEqualOrDescendentOf(final ModelNode otherContext) {
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
	public boolean isSameNode(final Node other) {
		return this == other;
	}

	/** {@inheritDoc} */
	@Override
	public String lookupNamespaceURI(final String prefix) {
		final XPathNSResolver xpath = new XPathNSResolverImpl(this);
		return xpath.lookupNamespaceURI(prefix);
	}

	/** {@inheritDoc} */
	@Override
	public String lookupPrefix(final String namespaceURI) {

		if (namespaceURI == null) {
			return null;
		}
		switch (getNodeType()) {
			case Node.ATTRIBUTE_NODE:
				if (namespaceURI.equals(getNamespaceURI())) {
					return getPrefix();
				}

				if (getParentNode() != null && getParentNode().getNodeType() == Node.ELEMENT_NODE) {
					return getParentNode().lookupPrefix(namespaceURI);
				}
				return null;
			case Node.ELEMENT_NODE:
				if (namespaceURI.equals(getNamespaceURI())) {
					return getPrefix();
				}
				return null;
			default:
				return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize() {
		for (Node child = getFirstChild(); child != null; child = child.getNextSibling()) {
			{
				switch (child.getNodeType()) {
					case TEXT_NODE:
					case CDATA_SECTION_NODE:
						while (child.getNextSibling() != null &&
								(child.getNextSibling().getNodeType() == TEXT_NODE ||
										child.getNextSibling().getNodeType() == CDATA_SECTION_NODE)) {
							final Text text = (Text) child;
							text.appendData(child.getNextSibling().getNodeValue());
							removeChild(child.getNextSibling());
						}
						break;
					case ELEMENT_NODE:
						final NamedNodeMap attrs = child.getAttributes();
						final int len = attrs.getLength();
						for (int i = 0; i < len; i++) {
							final Node attr = attrs.item(i);
							attr.normalize();
						}
						// Fall through
					case DOCUMENT_NODE:
					case DOCUMENT_FRAGMENT_NODE:
					case ATTRIBUTE_NODE:
					case ENTITY_REFERENCE_NODE:
						child.normalize();
						break;
					default:
						break;
				}
			}
		}

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	/** {@inheritDoc} */
	@Override
	public Node removeChild(final Node oldChild) {
		synchronized (this) {
			if (!this.nodeList.remove(oldChild)) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
			}
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
		oldChild.setParentImpl(null);
		return oldChild;
	}

	/**
	 * <p>removeChildAt.</p>
	 *
	 * @param index a {@link java.lang.Integer} object.
	 * @return a {@link org.loboevolution.html.node.Node} object.
	 * @throws DOMException if any.
	 */
	public Node removeChildAt(final int index) {
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

	/**
	 * <p>removeChildren.</p>
	 *
	 * @param filter a {@link NodeFilter} object.
	 */
	protected void removeTableChildren(final NodeFilter filter) {
		synchronized (this) {
			removeChildrenImpl(filter);
		}
		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}
	}

	/**
	 * <p>removeChildrenImpl.</p>
	 *
	 * @param filter a {@link NodeFilter} object.
	 */
	protected void removeChildrenImpl(final NodeFilter filter) {
		final AtomicInteger count = new AtomicInteger();
		final AtomicInteger index = new AtomicInteger(-1);
		nodeList.forEach(node -> {
			if (node instanceof Element) {
				if (filter.acceptNode(node) == NodeFilter.FILTER_ACCEPT && count.get() == 0) {
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
	 * @param node a {@link org.loboevolution.html.node.Text} object.
	 * @param textContent a {@link java.lang.String} object.
	 * @return a {@link org.loboevolution.html.node.Text} object.
	 */
	public Text replaceAdjacentTextNodes(final Text node, final String textContent) {
		try {
			final int idx = this.nodeList.indexOf(node);
			if (idx == -1) {
				throw new DOMException(DOMException.NOT_FOUND_ERR, "Node not a child");
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
	public Node replaceChild(final Node newChild, final Node oldChild) {

		if (Objects.equals(newChild, oldChild)) {
			return oldChild;
		}

		final int idx = this.nodeList.indexOf(oldChild);
		if (idx == -1) {
			throw new DOMException(DOMException.NOT_FOUND_ERR, "oldChild not found");
		}

		if (getNodeType() == Node.DOCUMENT_TYPE_NODE && newChild.getNodeType() == Node.ATTRIBUTE_NODE) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Document cannot append Attr");
		}

		if (getNodeType() == Node.ENTITY_REFERENCE_NODE) {
			throw new DOMException(DOMException.NO_MODIFICATION_ALLOWED_ERR, "readonly node");
		}

		if (newChild.contains(oldChild)) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Child node is already a parent.");
		}

		if (Objects.equals(newChild, this)) {
			throw new DOMException(DOMException.HIERARCHY_REQUEST_ERR, "Node is equals");
		}

		if (newChild.getOwnerDocument() == null) {
			newChild.setOwnerDocument(getOwnerDocument());
		}

		if(!Objects.equals(newChild.getOwnerDocument(), getOwnerDocument()))
			throw new DOMException(DOMException.WRONG_DOCUMENT_ERR, "Different Document");


		final int idx2 = this.nodeList.indexOf(newChild);
		newChild.setParentImpl(this);
		this.nodeList.set(idx, newChild);
		if (idx2 != -1) {
			this.nodeList.remove(idx2);
		}

		if (!this.notificationsSuspended) {
			informStructureInvalid();
		}

		return oldChild;
	}

	/** {@inheritDoc} */
	@Override
	public void setDocumentItem(final String name, final Object value) {
		final Document document = this.document;
		if (document != null) {
			document.setUserData(name, value, null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public abstract void setNodeValue(final String nodeValue) throws DOMException;

	/**
	 * <p>setOwnerDocument.</p>
	 *
	 * @param value a {@link org.loboevolution.html.node.Document} object.
	 */
	public void setOwnerDocument(final Document value) {
		this.document = value;
	}

	/** {@inheritDoc} */
	@Override
	public void setOwnerDocument(final Document value, final boolean deep) {
		this.document = value;
		if (deep) {
			nodeList.forEach(child -> child.setOwnerDocument(value, deep));
		}
	}

	/** {@inheritDoc} */
	@Override
	public final void setParentImpl(final Node parent) {
		this.parentNode = parent;
	}

	/** {@inheritDoc} */
	@Override
	public void setPrefix(final String prefix) throws DOMException {

		if (namespaceURI == null ||
				("xml".equals(prefix) && !Document.XML_NAMESPACE_URI.equals(getNamespaceURI()))) {
			throw new DOMException(DOMException.NAMESPACE_ERR, "Wrong namespace for prefix xml");
		}

		if (prefix != null && !Strings.isXMLIdentifier(prefix)) {
			throw new DOMException(DOMException.NAMESPACE_ERR, "Invalid prefix");
		}
		this.prefix = prefix;
	}

	/** {@inheritDoc} */
	@Override
	public void setTextContent(final String textContent) {
		synchronized (this) {
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
	 * @param uiNode a {@link UINode} object.
	 */
	public void setUINode(final UINode uiNode) {
		// Called in GUI thread always.
		this.uiNode = uiNode;
	}

	/** {@inheritDoc} */
	@Override
	public Object setUserData(final String key, final Object data, final UserDataHandler handler) {
		if (XHtmlParser.MODIFYING_KEY.equals(key)) {
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

	@Override
	public Node getFeature(final String feature, final String version) {
		return isSupported(feature, version)  ? this : null;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return getNodeName();
	}

	/** {@inheritDoc} */
	@Override
	public void visit(final NodeVisitor visitor) {
		synchronized (this) {
			visitImpl(visitor);
		}
	}

	/**
	 * <p>visitImpl.</p>
	 *
	 * @param visitor a {@link NodeVisitor} object.
	 */
	public void visitImpl(final NodeVisitor visitor) {
		visitor.visit(this);
		nodeList.forEach(child -> child.visit(visitor));
	}

	/**
	 * <p>warn.</p>
	 *
	 * @param message a {@link java.lang.String} object.
	 */
	public void warn(final String message) {
		log.warn(message);
	}

	/** {@inheritDoc} */
	@Override
	public void warn(final String message, final Throwable err) {
		log.warn(message, err);
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

	public short getNONE() {
		return NONE;
	}

	public short getELEMENT_NODE() {
		return ELEMENT_NODE;
	}

	public short getATTRIBUTE_NODE() {
		return ATTRIBUTE_NODE;
	}

	public short getTEXT_NODE() {
		return TEXT_NODE;
	}

	public short getCDATA_SECTION_NODE() {
		return CDATA_SECTION_NODE;
	}

	public short getENTITY_REFERENCE_NODE() {
		return ENTITY_REFERENCE_NODE;
	}

	public short getENTITY_NODE() {
		return ENTITY_NODE;
	}

	public short getPROCESSING_INSTRUCTION_NODE() {
		return PROCESSING_INSTRUCTION_NODE;
	}

	public short getCOMMENT_NODE() {
		return COMMENT_NODE;
	}

	public short getDOCUMENT_NODE() {
		return DOCUMENT_NODE;
	}

	public short getDOCUMENT_TYPE_NODE() {
		return DOCUMENT_TYPE_NODE;
	}

	public short getDOCUMENT_FRAGMENT_NODE() {
		return DOCUMENT_FRAGMENT_NODE;
	}

	public short getNOTATION_NODE() {
		return NOTATION_NODE;
	}

	public short getDOCUMENT_POSITION_DISCONNECTED() {
		return DOCUMENT_POSITION_DISCONNECTED;
	}

	public short getDOCUMENT_POSITION_PRECEDING() {
		return DOCUMENT_POSITION_PRECEDING;
	}

	public short getDOCUMENT_POSITION_FOLLOWING() {
		return DOCUMENT_POSITION_FOLLOWING;
	}

	public short getDOCUMENT_POSITION_CONTAINS() {
		return DOCUMENT_POSITION_CONTAINS;
	}

	public short getDOCUMENT_POSITION_CONTAINED_BY() {
		return DOCUMENT_POSITION_CONTAINED_BY;
	}

	public short getDOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC() {
		return DOCUMENT_POSITION_IMPLEMENTATION_SPECIFIC;
	}
}
