/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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
package org.loboevolution.apache.xml.dtm;

import org.loboevolution.apache.xpath.objects.XString;
import org.loboevolution.html.node.Node;

/**
 * <code>DTM</code> is an XML document model expressed as a table rather than an object tree. It
 * attempts to provide an interface to a parse tree that has very little object creation. (DTM
 * implementations may also support incremental construction of the model, but that's hidden from
 * the DTM API.)
 *
 * <p>Nodes in the DTM are identified by integer "handles". A handle must be unique within a
 * process, and carries both node identification and document identification. It must be possible to
 * compare two handles (and thus their nodes) for identity with "==".
 *
 * <p>Namespace URLs, local-names, and expanded-names can all be represented by and tested as
 * integer ID values. An expanded name represents (and may or may not directly contain) a
 * combination of the URL ID, and the local-name ID. Note that the namespace URL id can be 0, which
 * should have the meaning that the namespace is null. For consistancy, zero should not be used for
 * a local-name index.
 *
 * <p>Text content of a node is represented by an index and length, permitting efficient storage
 * such as a shared FastStringBuffer.
 *
 * <p>The model of the tree, as well as the general navigation model, is that of XPath 1.0, for the
 * moment. The model will eventually be adapted to match the XPath 2.0 data model, XML Schema, and
 * InfoSet.
 *
 * <p>DTM does _not_ directly support the W3C's Document Object Model. However, it attempts to come
 * close enough that an implementation of DTM can be created that wraps a DOM and vice versa.
 *
 * <p><strong>Please Note:</strong> The DTM API is still <strong>Subject To Change.</strong> This
 * wouldn't affect most users, but might require updating some extensions.
 *
 * <p>The largest change being contemplated is a reconsideration of the Node Handle representation.
 * We are still not entirely sure that an integer packed with two numeric subfields is really the
 * best solution. It has been suggested that we move up to a Long, to permit more nodes per document
 * without having to reduce the number of slots in the DTMManager. There's even been a proposal that
 * we replace these integers with "cursor" objects containing the internal node id and a pointer to
 * the actual DTM object; this might reduce the need to continuously consult the DTMManager to
 * retrieve the latter, and might provide a useful "hook" back into normal Java heap management. But
 * changing this datatype would have huge impact on Xalan's internals -- especially given Java's
 * lack of C-style typedefs -- so we won't cut over unless we're convinced the new solution really
 * would be an improvement!
 */
public interface DTM {

  /** Null node handles are represented by this value. */
  int NULL = -1;

  /** The node is an <code>Element</code>. */
  short ELEMENT_NODE = 1;

  /** The node is an <code>Attr</code>. */
  short ATTRIBUTE_NODE = 2;

  /** The node is a <code>Text</code> node. */
  short TEXT_NODE = 3;

  /** The node is a <code>CDATASection</code>. */
  short CDATA_SECTION_NODE = 4;

  /** The node is an <code>EntityReference</code>. */
  short ENTITY_REFERENCE_NODE = 5;

  /** The node is an <code>Entity</code>. */
  short ENTITY_NODE = 6;

  /** The node is a <code>ProcessingInstruction</code>. */
  short PROCESSING_INSTRUCTION_NODE = 7;

  /** The node is a <code>Comment</code>. */
  short COMMENT_NODE = 8;

  /** The node is a <code>Document</code>. */
  short DOCUMENT_NODE = 9;

  /** The node is a <code>DocumentType</code>. */
  short DOCUMENT_TYPE_NODE = 10;

  /** The node is a <code>DocumentFragment</code>. */
  short DOCUMENT_FRAGMENT_NODE = 11;

  /** The node is a <code>Notation</code>. */
  short NOTATION_NODE = 12;

  /**
   * The node is a <code>namespace node</code>. Note that this is not currently a node type defined
   * by the DOM API.
   */
  short NAMESPACE_NODE = 13;

  /** The number of valid nodetypes. */
  short NTYPES = 14;

  // ========= Document Navigation Functions =========

  /**
   * This returns a stateless "traverser", that can navigate over an XPath axis, though not in
   * document order.
   *
   * @param axis One of Axes.ANCESTORORSELF, etc.
   * @return A DTMAxisIterator, or null if the givin axis isn't supported.
   */
  DTMAxisTraverser getAxisTraverser(final int axis);

  /**
   * This is a shortcut to the iterators that implement XPath axes. Returns a bare-bones iterator
   * that must be initialized with a start node (using iterator.setStartNode()).
   *
   * @param axis One of Axes.ANCESTORORSELF, etc.
   * @return A DTMAxisIterator, or null if the givin axis isn't supported.
   */
  DTMAxisIterator getAxisIterator(final int axis);

  /**
   * Given a node handle, get the handle of the node's first child.
   *
   * @param nodeHandle int Handle of the node.
   * @return int DTM node-number of first child, or DTM.NULL to indicate none exists.
   */
  int getFirstChild(int nodeHandle);

  /**
   * Given a node handle, get the handle of the node's last child.
   *
   * @param nodeHandle int Handle of the node.
   * @return int Node-number of last child, or DTM.NULL to indicate none exists.
   */
  int getLastChild(int nodeHandle);

  /**
   * Retrieves an attribute node by local name and namespace URI
   *
   * <p>%TBD% Note that we currently have no way to support the DOM's old getAttribute() call, which
   * accesses only the qname.
   *
   * @param elementHandle Handle of the node upon which to look up this attribute.
   * @param namespaceURI The namespace URI of the attribute to retrieve, or null.
   * @param name The local name of the attribute to retrieve.
   * @return The attribute node handle with the specified name ( <code>nodeName</code>) or <code>
   *     DTM.NULL</code> if there is no such attribute.
   */
  int getAttributeNode(int elementHandle, String namespaceURI, String name);

  /**
   * Given a node handle, get the index of the node's first attribute.
   *
   * @param nodeHandle int Handle of the node.
   * @return Handle of first attribute, or DTM.NULL to indicate none exists.
   */
  int getFirstAttribute(int nodeHandle);

  /**
   * Given a node handle, get the index of the node's first namespace node.
   *
   * @param nodeHandle handle to node, which should probably be an element node, but need not be.
   * @param inScope true if all namespaces in scope should be returned, false if only the node's own
   *     namespace declarations should be returned.
   * @return handle of first namespace, or DTM.NULL to indicate none exists.
   */
  int getFirstNamespaceNode(int nodeHandle, boolean inScope);

  /**
   * Given a node handle, advance to its next sibling.
   *
   * @param nodeHandle int Handle of the node.
   * @return int Node-number of next sibling, or DTM.NULL to indicate none exists.
   */
  int getNextSibling(int nodeHandle);

  /**
   * Given a node handle, find its preceeding sibling. WARNING: DTM implementations may be
   * asymmetric; in some, this operation has been resolved by search, and is relatively expensive.
   *
   * @param nodeHandle the id of the node.
   * @return int Node-number of the previous sib, or DTM.NULL to indicate none exists.
   */
  int getPreviousSibling(int nodeHandle);

  /**
   * Given a node handle, advance to the next attribute. If an element, we advance to its first
   * attribute; if an attr, we advance to the next attr of the same element.
   *
   * @param nodeHandle int Handle of the node.
   * @return int DTM node-number of the resolved attr, or DTM.NULL to indicate none exists.
   */
  int getNextAttribute(int nodeHandle);

  /**
   * Given a namespace handle, advance to the next namespace in the same scope (local or
   * local-plus-inherited, as selected by getFirstNamespaceNode)
   *
   * @param baseHandle handle to original node from where the first child was relative to (needed to
   *     return nodes in document order).
   * @param namespaceHandle handle to node which must be of type NAMESPACE_NODE. NEEDSDOC @param
   *     inScope
   * @return handle of next namespace, or DTM.NULL to indicate none exists.
   */
  int getNextNamespaceNode(int baseHandle, int namespaceHandle, boolean inScope);

  /**
   * Given a node handle, find its parent node.
   *
   * @param nodeHandle the id of the node.
   * @return int Node handle of parent, or DTM.NULL to indicate none exists.
   */
  int getParent(int nodeHandle);

  /**
   * Given a DTM which contains only a single document, find the Node Handle of the Document node.
   * Note that if the DTM is configured so it can contain multiple documents, this call will return
   * the Document currently under construction -- but may return null if it's between documents.
   * Generally, you should use getOwnerDocument(nodeHandle) or getDocumentRoot(nodeHandle) instead.
   *
   * @return int Node handle of document, or DTM.NULL if a shared DTM can not tell us which Document
   *     is currently active.
   */
  int getDocument();

  /**
   * Given a node handle, find the owning document node. This version mimics the behavior of the DOM
   * call by the same name.
   *
   * @param nodeHandle the id of the node.
   * @return int Node handle of owning document, or DTM.NULL if the node was a Document.
   * @see #getDocumentRoot(int nodeHandle)
   */
  int getOwnerDocument(int nodeHandle);

  /**
   * Given a node handle, find the owning document node.
   *
   * @param nodeHandle the id of the node.
   * @return int Node handle of owning document, or the node itself if it was a Document. (Note
   *     difference from DOM, where getOwnerDocument returns null for the Document node.)
   * @see #getOwnerDocument(int nodeHandle)
   */
  int getDocumentRoot(int nodeHandle);

  /**
   * Get the string-value of a node as a String object (see <a
   * href="http://www.w3.org/TR/xpath#data-model">...</a> for the definition of a node's
   * string-value).
   *
   * @param nodeHandle The node ID.
   * @return A string object that represents the string-value of the given node.
   */
  XString getStringValue(int nodeHandle);

  /**
   * Given a node handle, return an ID that represents the node's expanded name.
   *
   * @param nodeHandle The handle to the node in question.
   * @return the expanded-name id of the node.
   */
  int getExpandedTypeID(int nodeHandle);

  /**
   * Given an expanded name, return an ID. If the expanded-name does not exist in the internal
   * tables, the entry will be created, and the ID will be returned. Any additional nodes that are
   * created that have this expanded name will use this ID.
   *
   * <p>NEEDSDOC @param namespace NEEDSDOC @param localName NEEDSDOC @param type
   *
   * @return the expanded-name id of the node.
   */
  int getExpandedTypeID(String namespace, String localName, int type);

  /**
   * Given a node handle, return its DOM-style node name. This will include names such as #text or
   * #document.
   *
   * @param nodeHandle the id of the node.
   * @return String Name of this node, which may be an empty string. %REVIEW% Document when empty
   *     string is possible...
   */
  String getNodeName(int nodeHandle);

  /**
   * Given a node handle, return the XPath node name. This should be the name as described by the
   * XPath data model, NOT the DOM-style name.
   *
   * @param nodeHandle the id of the node.
   * @return String Name of this node.
   */
  String getNodeNameX(int nodeHandle);

  /**
   * Given a node handle, return its DOM-style localname. (As defined in Namespaces, this is the
   * portion of the name after the prefix, if present, or the whole node name if no prefix exists)
   *
   * @param nodeHandle the id of the node.
   * @return String Local name of this node.
   */
  String getLocalName(int nodeHandle);

  /**
   * Given a namespace handle, return the prefix that the namespace decl is mapping. Given a node
   * handle, return the prefix used to map to the namespace. (As defined in Namespaces, this is the
   * portion of the name before any colon character).
   *
   * <p>%REVIEW% Are you sure you want "" for no prefix?
   *
   * @param nodeHandle the id of the node.
   * @return String prefix of this node's name, or "" if no explicit namespace prefix was given.
   */
  String getPrefix(int nodeHandle);

  /**
   * Given a node handle, return its DOM-style namespace URI (As defined in Namespaces, this is the
   * declared URI which this node's prefix -- or default in lieu thereof -- was mapped to.)
   *
   * @param nodeHandle the id of the node.
   * @return String URI value of this node's namespace, or null if no namespace was resolved.
   */
  String getNamespaceURI(int nodeHandle);

  /**
   * Given a node handle, return its node value. This is mostly as defined by the DOM, but may
   * ignore some conveniences.
   *
   * <p>
   *
   * @param nodeHandle The node id.
   * @return String Value of this node, or null if not meaningful for this node type.
   */
  String getNodeValue(int nodeHandle);

  /**
   * Given a node handle, return its DOM-style node type.
   *
   * <p>%REVIEW% Generally, returning short is false economy. Return int?
   *
   * @param nodeHandle The node id.
   * @return int Node type, as per the DOM's Node._NODE constants.
   */
  short getNodeType(int nodeHandle);

  // ============== Document query functions ==============

  /**
   * Returns the <code>Element</code> whose <code>ID</code> is given by <code>elementId</code>. If
   * no such element exists, returns <code>DTM.NULL</code>. Behavior is not defined if more than one
   * element has this <code>ID</code>. Attributes (including those with the name "ID") are not of
   * type ID unless so defined by DTD/Schema information available to the DTM implementation.
   * Implementations that do not know whether attributes are of type ID or not are expected to
   * return <code>DTM.NULL</code>.
   *
   * <p>%REVIEW% Presumably IDs are still scoped to a single document, and this operation searches
   * only within a single document, right? Wouldn't want collisions between DTMs in the same
   * process.
   *
   * @param elementId The unique <code>id</code> value for an element.
   * @return The handle of the matching element.
   */
  int getElementById(String elementId);

  // ============== Boolean methods ================

  /**
   * Figure out whether nodeHandle2 should be considered as being later in the document than
   * nodeHandle1, in Document Order as defined by the XPath model. This may not agree with the
   * ordering defined by other XML applications.
   *
   * <p>There are some cases where ordering isn't defined, and neither are the results of this
   * function -- though we'll generally return true.
   *
   * <p>%REVIEW% Make sure this does the right thing with attribute nodes!!!
   *
   * <p>%REVIEW% Consider renaming for clarity. Perhaps isDocumentOrder(a,b)?
   *
   * @param firstNodeHandle DOM Node to perform position comparison on.
   * @param secondNodeHandle DOM Node to perform position comparison on.
   * @return false if secondNode comes before firstNode, otherwise return true. You can think of
   *     this as <code>(firstNode.documentOrderPosition &lt;= secondNode.documentOrderPosition)
   *     </code>.
   */
  boolean isNodeAfter(int firstNodeHandle, int secondNodeHandle);

  /**
   * Return an DOM node for the given node.
   *
   * @param nodeHandle The node ID.
   * @return A node representation of the DTM node.
   */
  Node getNode(int nodeHandle);

  // ==== Construction methods (may not be supported by some implementations!)
  // =====

}
