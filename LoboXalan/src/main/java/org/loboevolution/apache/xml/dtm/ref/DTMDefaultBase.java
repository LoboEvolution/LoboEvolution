/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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
package org.loboevolution.apache.xml.dtm.ref;

// for dumpDTM

import lombok.extern.slf4j.Slf4j;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMAxisTraverser;
import org.loboevolution.apache.xml.dtm.DTMException;
import org.loboevolution.apache.xml.dtm.DTMManager;
import org.loboevolution.apache.xml.utils.SuballocatedIntVector;
import org.loboevolution.apache.xpath.objects.XString;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.html.node.Node;
import org.loboevolution.javax.xml.transform.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * The <code>DTMDefaultBase</code> class serves as a helper base for DTMs. It sets up structures for
 * navigation and type, while leaving data management and construction to the derived classes.
 */
@Slf4j
public abstract class DTMDefaultBase implements DTM {
  static final boolean JJK_DEBUG = false;

  // This constant is likely to be removed in the future. Use the
  // getDocument() method instead of ROOTNODE to get at the root
  // node of a DTM.
  /** The identity of the root node. */
  public static final int ROOTNODE = 0;

  /** The number of nodes, which is also used to determine the next node index. */
  protected int m_size = 0;

  /** The expanded names, one array element for each node. */
  protected final SuballocatedIntVector m_exptype;

  /** First child values, one array element for each node. */
  protected final SuballocatedIntVector m_firstch;

  /** Next sibling values, one array element for each node. */
  protected final SuballocatedIntVector m_nextsib;

  /** Previous sibling values, one array element for each node. */
  protected SuballocatedIntVector m_prevsib;

  /** Previous sibling values, one array element for each node. */
  protected final SuballocatedIntVector m_parent;

  /** Vector of SuballocatedIntVectors of NS decl sets */
  protected List<SuballocatedIntVector> m_namespaceDeclSets = null;

  /** SuballocatedIntVector of elements at which corresponding namespaceDeclSets were defined */
  protected SuballocatedIntVector m_namespaceDeclSetElements = null;

  /**
   * These hold indexes to elements based on namespace and local name. The base lookup is the the
   * namespace. The second lookup is the local name, and the last array contains the the first free
   * element at the start, and the list of element handles following.
   */
  protected int[][][] m_elemIndexes;

  /** The default block size of the node arrays */
  public static final int DEFAULT_BLOCKSIZE = 512; // favor small docs.

  /** The number of blocks for the node arrays */
  public static final int DEFAULT_NUMBLOCKS = 32;

  /** The number of blocks used for small documents &amp; RTFs */
  public static final int DEFAULT_NUMBLOCKS_SMALL = 4;

  /* The block size of the node arrays */
  // protected final int m_blocksize;

  /** The value to use when the information has not been built yet. */
  protected static final int NOTPROCESSED = DTM.NULL - 1;

  /** The DTM manager who "owns" this DTM. */
  public final DTMManager m_mgr;

  /** m_mgr cast to DTMManagerDefault, or null if it isn't an instance (Efficiency hook) */
  protected DTMManagerDefault m_mgrDefault = null;

  /**
   * The document identity number(s). If we have overflowed the addressing range of the first that
   * was assigned to us, we may add others.
   */
  protected final SuballocatedIntVector m_dtmIdent;

  /*
   The mask for the identity. %REVIEW% Should this really be set to the _DEFAULT? What if a
   particular DTM wanted to use another value?
  */
  // protected final static int m_mask = DTMManager.IDENT_NODE_DEFAULT;

  /** The base URI for this document. */
  protected final String m_documentBaseURI;

  /**
   * The table for exandedNameID lookups. This may or may not be the same table as is contained in
   * the DTMManagerDefault.
   */
  protected final ExpandedNameTable m_expandedNameTable;

  /** true if indexing is turned on. */
  protected final boolean m_indexing;

  /**
   * Construct a DTMDefaultBase object using the default block size.
   *
   * @param mgr The DTMManager who owns this DTM.
   * @param source The object that is used to specify the construction source.
   * @param dtmIdentity The DTM identity ID for this DTM.
   * @param doIndexing true if the caller considers it worth it to use indexing schemes.
   */
  public DTMDefaultBase(final DTMManager mgr, final Source source, final int dtmIdentity, final boolean doIndexing) {
    this(mgr, source, dtmIdentity, doIndexing, DEFAULT_BLOCKSIZE, true);
  }

  /**
   * Construct a DTMDefaultBase object from a DOM node.
   *
   * @param mgr The DTMManager who owns this DTM.
   * @param source The object that is used to specify the construction source.
   * @param dtmIdentity The DTM identity ID for this DTM.
   * @param doIndexing true if the caller considers it worth it to use indexing schemes.
   * @param blocksize The block size of the DTM.
   * @param usePrevsib true if we want to build the previous sibling node array.
   */
  public DTMDefaultBase(
          final DTMManager mgr,
          final Source source,
          final int dtmIdentity,
          final boolean doIndexing,
          final int blocksize,
          final boolean usePrevsib) {
    // Use smaller sizes for the internal node arrays if the block size
    // is small.
    final int numblocks;
    if (blocksize <= 64) {
      numblocks = DEFAULT_NUMBLOCKS_SMALL;
      m_dtmIdent = new SuballocatedIntVector(4, 1);
    } else {
      numblocks = DEFAULT_NUMBLOCKS;
      m_dtmIdent = new SuballocatedIntVector(32);
    }

    m_exptype = new SuballocatedIntVector(blocksize, numblocks);
    m_firstch = new SuballocatedIntVector(blocksize, numblocks);
    m_nextsib = new SuballocatedIntVector(blocksize, numblocks);
    m_parent = new SuballocatedIntVector(blocksize, numblocks);

    // Only create the m_prevsib array if the usePrevsib flag is true.
    // Some DTM implementations (e.g. SAXImpl) do not need this array.
    // We can save the time to build it in those cases.
    if (usePrevsib) m_prevsib = new SuballocatedIntVector(blocksize, numblocks);

    m_mgr = mgr;
    if (mgr instanceof DTMManagerDefault) m_mgrDefault = (DTMManagerDefault) mgr;

    m_documentBaseURI = (null != source) ? source.getSystemId() : null;
    m_dtmIdent.setElementAt(dtmIdentity, 0);
    m_indexing = doIndexing;

    if (doIndexing) {
      m_expandedNameTable = new ExpandedNameTable();
    } else {
      // Note that this fails if we aren't talking to an instance of
      // DTMManagerDefault
      m_expandedNameTable = m_mgrDefault.getExpandedNameTable();
    }
  }

  /**
   * Ensure that the size of the element indexes can hold the information.
   *
   * @param namespaceID Namespace ID index.
   * @param LocalNameID Local name ID.
   */
  protected void ensureSizeOfIndex(final int namespaceID, final int LocalNameID) {

    if (null == m_elemIndexes) {
      m_elemIndexes = new int[namespaceID + 20][][];
    } else if (m_elemIndexes.length <= namespaceID) {
      final int[][][] indexes = m_elemIndexes;

      m_elemIndexes = new int[namespaceID + 20][][];

      System.arraycopy(indexes, 0, m_elemIndexes, 0, indexes.length);
    }

    int[][] localNameIndex = m_elemIndexes[namespaceID];

    if (null == localNameIndex) {
      localNameIndex = new int[LocalNameID + 100][];
      m_elemIndexes[namespaceID] = localNameIndex;
    } else if (localNameIndex.length <= LocalNameID) {
      final int[][] indexes = localNameIndex;

      localNameIndex = new int[LocalNameID + 100][];

      System.arraycopy(indexes, 0, localNameIndex, 0, indexes.length);

      m_elemIndexes[namespaceID] = localNameIndex;
    }

    int[] elemHandles = localNameIndex[LocalNameID];

    if (null == elemHandles) {
      elemHandles = new int[128];
      localNameIndex[LocalNameID] = elemHandles;
      elemHandles[0] = 1;
    } else if (elemHandles.length <= elemHandles[0] + 1) {
      final int[] indexes = elemHandles;

      elemHandles = new int[elemHandles[0] + 1024];

      System.arraycopy(indexes, 0, elemHandles, 0, indexes.length);

      localNameIndex[LocalNameID] = elemHandles;
    }
  }

  /**
   * Add a node to the element indexes. The node will not be added unless it's an element.
   *
   * @param expandedTypeID The expanded type ID of the node.
   * @param identity The node identity index.
   */
  protected void indexNode(final int expandedTypeID, final int identity) {

    final ExpandedNameTable ent = m_expandedNameTable;
    final short type = ent.getType(expandedTypeID);

    if (DTM.ELEMENT_NODE == type) {
      final int namespaceID = ent.getNamespaceID(expandedTypeID);
      final int localNameID = ent.getLocalNameID(expandedTypeID);

      ensureSizeOfIndex(namespaceID, localNameID);

      final int[] index = m_elemIndexes[namespaceID][localNameID];

      index[index[0]] = identity;

      index[0]++;
    }
  }

  /**
   * Find the first index that occurs in the list that is greater than or equal to the given value.
   *
   * @param list A list of integers.
   * @param start The start index to begin the search.
   * @param len The number of items to search.
   * @param value Find the slot that has a value that is greater than or identical to this argument.
   * @return The index in the list of the slot that is higher or identical to the identity argument,
   *     or -1 if no node is higher or equal.
   */
  protected int findGTE(final int[] list, final int start, final int len, final int value) {

    int low = start;
    int high = start + (len - 1);
    final int end = high;

    while (low <= high) {
      final int mid = (low + high) >>> 1;
      final int c = list[mid];

      if (c > value) high = mid - 1;
      else if (c < value) low = mid + 1;
      else return mid;
    }

    return (low <= end && list[low] > value) ? low : -1;
  }

  /**
   * Find the first matching element from the index at or after the given node.
   *
   * @param nsIndex The namespace index lookup.
   * @param lnIndex The local name index lookup.
   * @param firstPotential The first potential match that is worth looking at.
   * @return The first node that is greater than or equal to the firstPotential argument, or
   *     DTM.NOTPROCESSED if not found.
   */
  int findElementFromIndex(final int nsIndex, final int lnIndex, final int firstPotential) {

    final int[][][] indexes = m_elemIndexes;

    if (null != indexes && nsIndex < indexes.length) {
      final int[][] lnIndexs = indexes[nsIndex];

      if (null != lnIndexs && lnIndex < lnIndexs.length) {
        final int[] elems = lnIndexs[lnIndex];

        if (null != elems) {
          final int pos = findGTE(elems, 1, elems[0], firstPotential);

          if (pos > -1) {
            return elems[pos];
          }
        }
      }
    }

    return NOTPROCESSED;
  }

  /**
   * Get the next node identity value in the list, and call the iterator if it hasn't been added
   * yet.
   *
   * @param identity The node identity (index).
   * @return identity+1, or DTM.NULL.
   */
  protected abstract int getNextNodeIdentity(int identity);

  /**
   * This method should try and build one or more nodes in the table.
   *
   * @return The true if a next node is found or false if there are no more nodes.
   */
  protected abstract boolean nextNode();

  /** Stateless axis traversers, lazely built. */
  protected DTMAxisTraverser[] m_traversers;

  /**
   * Get the simple type ID for the given node identity.
   *
   * @param identity The node identity.
   * @return The simple type ID, or DTM.NULL.
   */
  protected short _type(final int identity) {

    final int info = _exptype(identity);

    if (NULL != info) {
      return m_expandedNameTable.getType(info);
    }
    return NULL;
  }

  /**
   * Get the expanded type ID for the given node identity.
   *
   * @param identity The node identity.
   * @return The expanded type ID, or DTM.NULL.
   */
  protected int _exptype(final int identity) {
    if (identity == DTM.NULL) return NULL;
    // Reorganized test and loop into single flow
    // Tiny performance improvement, saves a few bytes of code, clearer.
    // %OPT% Other internal getters could be treated simliarly
    while (identity >= m_size) {
      if (!nextNode() && identity >= m_size) return NULL;
    }
    return m_exptype.elementAt(identity);
  }

  /**
   * Get the level in the tree for the given node identity.
   *
   * @param identity The node identity.
   * @return The tree level, or DTM.NULL.
   */
  protected int _level(int identity) {
    while (identity >= m_size) {
      final boolean isMore = nextNode();
      if (!isMore && identity >= m_size) return NULL;
    }

    int i = 0;
    while (NULL != (identity = _parent(identity))) ++i;
    return i;
  }

  /**
   * Get the first child for the given node identity.
   *
   * @param identity The node identity.
   * @return The first child identity, or DTM.NULL.
   */
  protected int _firstch(final int identity) {

    // Boiler-plate code for each of the _xxx functions, except for the array.
    int info = (identity >= m_size) ? NOTPROCESSED : m_firstch.elementAt(identity);

    // Check to see if the information requested has been processed, and,
    // if not, advance the iterator until we the information has been
    // processed.
    while (info == NOTPROCESSED) {
      final boolean isMore = nextNode();

      if (identity >= m_size && !isMore) {
        return NULL;
      }
      info = m_firstch.elementAt(identity);
      if (info == NOTPROCESSED && !isMore) return NULL;
    }

    return info;
  }

  /**
   * Get the next sibling for the given node identity.
   *
   * @param identity The node identity.
   * @return The next sibling identity, or DTM.NULL.
   */
  protected int _nextsib(final int identity) {
    // Boiler-plate code for each of the _xxx functions, except for the array.
    int info = (identity >= m_size) ? NOTPROCESSED : m_nextsib.elementAt(identity);

    // Check to see if the information requested has been processed, and,
    // if not, advance the iterator until we the information has been
    // processed.
    while (info == NOTPROCESSED) {
      final boolean isMore = nextNode();

      if (identity >= m_size && !isMore) {
        return NULL;
      }
      info = m_nextsib.elementAt(identity);
      if (info == NOTPROCESSED && !isMore) return NULL;
    }

    return info;
  }

  /**
   * Get the previous sibling for the given node identity.
   *
   * @param identity The node identity.
   * @return The previous sibling identity, or DTM.NULL.
   */
  protected int _prevsib(final int identity) {

    if (identity < m_size) return m_prevsib.elementAt(identity);

    // Check to see if the information requested has been processed, and,
    // if not, advance the iterator until we the information has been
    // processed.
    while (true) {
      final boolean isMore = nextNode();

      if (identity >= m_size && !isMore) return NULL;
      else if (identity < m_size) return m_prevsib.elementAt(identity);
    }
  }

  /**
   * Get the parent for the given node identity.
   *
   * @param identity The node identity.
   * @return The parent identity, or DTM.NULL.
   */
  protected int _parent(final int identity) {

    if (identity < m_size) return m_parent.elementAt(identity);

    // Check to see if the information requested has been processed, and,
    // if not, advance the iterator until we the information has been
    // processed.
    while (true) {
      final boolean isMore = nextNode();

      if (identity >= m_size && !isMore) return NULL;
      else if (identity < m_size) return m_parent.elementAt(identity);
    }
  }

  /**
   * Diagnostics function to dump a single node.
   *
   * <p>%REVIEW% KNOWN GLITCH: If you pass it a node index rather than a node handle, it works just
   * fine... but the displayed identity number before the colon is different, which complicates
   * comparing it with nodes printed the other way. We could always OR the DTM ID into the value, to
   * suppress that distinction...
   *
   * <p>%REVIEW% This might want to be moved up to DTMDefaultBase, or possibly DTM itself, since
   * it's a useful diagnostic and uses only DTM's public APIs.
   */
  public String dumpNode(final int nodeHandle) {
    if (nodeHandle == DTM.NULL) return "[null]";

    final String typestring;
    switch (getNodeType(nodeHandle)) {
      case DTM.ATTRIBUTE_NODE:
        typestring = "ATTR";
        break;
      case DTM.CDATA_SECTION_NODE:
        typestring = "CDATA";
        break;
      case DTM.COMMENT_NODE:
        typestring = "COMMENT";
        break;
      case DTM.DOCUMENT_FRAGMENT_NODE:
        typestring = "DOC_FRAG";
        break;
      case DTM.DOCUMENT_NODE:
        typestring = "DOC";
        break;
      case DTM.DOCUMENT_TYPE_NODE:
        typestring = "DOC_TYPE";
        break;
      case DTM.ELEMENT_NODE:
        typestring = "ELEMENT";
        break;
      case DTM.ENTITY_NODE:
        typestring = "ENTITY";
        break;
      case DTM.ENTITY_REFERENCE_NODE:
        typestring = "ENT_REF";
        break;
      case DTM.NAMESPACE_NODE:
        typestring = "NAMESPACE";
        break;
      case DTM.NOTATION_NODE:
        typestring = "NOTATION";
        break;
      case DTM.NULL:
        typestring = "null";
        break;
      case DTM.PROCESSING_INSTRUCTION_NODE:
        typestring = "PI";
        break;
      case DTM.TEXT_NODE:
        typestring = "TEXT";
        break;
      default:
        typestring = "Unknown!";
        break;
    }

    return "["
        + nodeHandle
        + ": "
        + typestring
        + "(0x"
        + Integer.toHexString(getExpandedTypeID(nodeHandle))
        + ") "
        + getNodeNameX(nodeHandle)
        + " {"
        + getNamespaceURI(nodeHandle)
        + "}"
        + "=\""
        + getNodeValue(nodeHandle)
        + "\"]";
  }

  // ========= Document Navigation Functions =========

  /**
   * Given a node identity, return a node handle. If extended addressing has been used (multiple DTM
   * IDs), we need to map the high bits of the identity into the proper DTM ID.
   *
   * <p>This has been made FINAL to facilitate inlining, since we do not expect any subclass of
   * DTMDefaultBase to ever change the algorithm. (I don't really like doing so, and would love to
   * have an excuse not to...)
   *
   * <p>%REVIEW% Is it worth trying to specialcase small documents? %REVIEW% Should this be exposed
   * at the package/public layers?
   *
   * @param nodeIdentity Internal offset to this node's records.
   * @return NodeHandle (external representation of node)
   */
  public final int makeNodeHandle(final int nodeIdentity) {
    if (NULL == nodeIdentity) return NULL;

    return m_dtmIdent.elementAt(nodeIdentity >>> DTMManager.IDENT_DTM_NODE_BITS)
        + (nodeIdentity & DTMManager.IDENT_NODE_DEFAULT);
  }

  /**
   * Given a node handle, return a node identity. If extended addressing has been used (multiple DTM
   * IDs), we need to map the high bits of the identity into the proper DTM ID and thence find the
   * proper offset to add to the low bits of the identity
   *
   * <p>This has been made FINAL to facilitate inlining, since we do not expect any subclass of
   * DTMDefaultBase to ever change the algorithm. (I don't really like doing so, and would love to
   * have an excuse not to...)
   *
   * <p>%OPT% Performance is critical for this operation.
   *
   * <p>%REVIEW% Should this be exposed at the package/public layers?
   *
   * @param nodeHandle (external representation of node)
   * @return nodeIdentity Internal offset to this node's records.
   */
  public final int makeNodeIdentity(final int nodeHandle) {
    if (NULL == nodeHandle) return NULL;

    if (m_mgrDefault != null) {
      // Optimization: use the DTMManagerDefault's fast DTMID-to-offsets
      // table. I'm not wild about this solution but this operation
      // needs need extreme speed.

      final int whichDTMindex = nodeHandle >>> DTMManager.IDENT_DTM_NODE_BITS;

      // %REVIEW% Wish I didn't have to perform the pre-test, but
      // someone is apparently asking DTMs whether they contain nodes
      // which really don't belong to them. That's probably a bug
      // which should be fixed, but until it is:
      if (m_mgrDefault.m_dtms[whichDTMindex] != this) {
        return NULL;
      }
      return m_mgrDefault.m_dtm_offsets[whichDTMindex]
          | (nodeHandle & DTMManager.IDENT_NODE_DEFAULT);
    }

    final int whichDTMid = m_dtmIdent.indexOf(nodeHandle & DTMManager.IDENT_DTM_DEFAULT);
    return (whichDTMid == NULL)
        ? NULL
        : (whichDTMid << DTMManager.IDENT_DTM_NODE_BITS)
            + (nodeHandle & DTMManager.IDENT_NODE_DEFAULT);
  }

  /** {@inheritDoc} */
  @Override
  public int getFirstChild(final int nodeHandle) {

    final int identity = makeNodeIdentity(nodeHandle);
    final int firstChild = _firstch(identity);

    return makeNodeHandle(firstChild);
  }

  /** {@inheritDoc} */
  @Override
  public int getLastChild(final int nodeHandle) {

    final int identity = makeNodeIdentity(nodeHandle);
    int child = _firstch(identity);
    int lastChild = DTM.NULL;

    while (child != DTM.NULL) {
      lastChild = child;
      child = _nextsib(child);
    }

    return makeNodeHandle(lastChild);
  }

  /** {@inheritDoc} */
  @Override
  public abstract int getAttributeNode(int nodeHandle, final String namespaceURI, final String name);

  /** {@inheritDoc} */
  @Override
  public int getFirstAttribute(final int nodeHandle) {
    final int nodeID = makeNodeIdentity(nodeHandle);

    return makeNodeHandle(getFirstAttributeIdentity(nodeID));
  }

  /**
   * Given a node identity, get the index of the node's first attribute.
   *
   * @param identity int identity of the node.
   * @return Identity of first attribute, or DTM.NULL to indicate none exists.
   */
  protected int getFirstAttributeIdentity(int identity) {
    int type = _type(identity);

    if (DTM.ELEMENT_NODE == type) {
      // Assume that attributes and namespaces immediately follow the element.
      while (DTM.NULL != (identity = getNextNodeIdentity(identity))) {

        // Assume this can not be null.
        type = _type(identity);

        if (type == DTM.ATTRIBUTE_NODE) {
          return identity;
        } else if (DTM.NAMESPACE_NODE != type) {
          break;
        }
      }
    }

    return DTM.NULL;
  }

  /** {@inheritDoc} */
  @Override
  public int getNextSibling(final int nodeHandle) {
    if (nodeHandle == DTM.NULL) return DTM.NULL;
    return makeNodeHandle(_nextsib(makeNodeIdentity(nodeHandle)));
  }

  /** {@inheritDoc} */
  @Override
  public int getPreviousSibling(final int nodeHandle) {
    if (nodeHandle == DTM.NULL) return DTM.NULL;

    if (m_prevsib != null) {
      return makeNodeHandle(_prevsib(makeNodeIdentity(nodeHandle)));
    }
    // If the previous sibling array is not built, we get at
    // the previous sibling using the parent, firstch and
    // nextsib arrays.
    final int nodeID = makeNodeIdentity(nodeHandle);
    final int parent = _parent(nodeID);
    int node = _firstch(parent);
    int result = DTM.NULL;
    while (node != nodeID) {
      result = node;
      node = _nextsib(node);
    }
    return makeNodeHandle(result);
  }

  /** {@inheritDoc} */
  @Override
  public int getNextAttribute(final int nodeHandle) {
    final int nodeID = makeNodeIdentity(nodeHandle);

    if (_type(nodeID) == DTM.ATTRIBUTE_NODE) {
      return makeNodeHandle(getNextAttributeIdentity(nodeID));
    }

    return DTM.NULL;
  }

  /**
   * Given a node identity for an attribute, advance to the next attribute.
   *
   * @param identity int identity of the attribute node. This <strong>must</strong> be an attribute
   *     node.
   * @return int DTM node-identity of the resolved attr, or DTM.NULL to indicate none exists.
   */
  protected int getNextAttributeIdentity(int identity) {
    // Assume that attributes and namespace nodes immediately follow the element
    while (DTM.NULL != (identity = getNextNodeIdentity(identity))) {
      final int type = _type(identity);

      if (type == DTM.ATTRIBUTE_NODE) {
        return identity;
      } else if (type != DTM.NAMESPACE_NODE) {
        break;
      }
    }

    return DTM.NULL;
  }

  /**
   * Build table of namespace declaration locations during DTM construction. Table is a Vector of
   * SuballocatedIntVectors containing the namespace node HANDLES declared at that ID, plus an
   * SuballocatedIntVector of the element node INDEXES at which these declarations appeared.
   *
   * <p>NOTE: Since this occurs during model build, nodes will be encountered in doucment order and
   * thus the table will be ordered by element, permitting binary-search as a possible retrieval
   * optimization.
   *
   * <p>%REVIEW% Directly managed arrays rather than vectors? %REVIEW% Handles or IDs? Given usage,
   * I think handles.
   */
  protected void declareNamespaceInContext(final int elementNodeIndex, final int namespaceNodeIndex) {
    SuballocatedIntVector nsList = null;
    if (m_namespaceDeclSets == null) {

      // First
      m_namespaceDeclSetElements = new SuballocatedIntVector(32);
      m_namespaceDeclSetElements.addElement(elementNodeIndex);
      m_namespaceDeclSets = new ArrayList<>();
      nsList = new SuballocatedIntVector(32);
      m_namespaceDeclSets.add(nsList);
    } else {
      // Most recent. May be -1 (none) if DTM was pruned.
      // %OPT% Is there a lastElement() method? Should there be?
      final int last = m_namespaceDeclSetElements.size() - 1;

      if (last >= 0 && elementNodeIndex == m_namespaceDeclSetElements.elementAt(last)) {
        nsList = m_namespaceDeclSets.get(last);
      }
    }
    if (nsList == null) {
      m_namespaceDeclSetElements.addElement(elementNodeIndex);

      final SuballocatedIntVector inherited = findNamespaceContext(_parent(elementNodeIndex));

      if (inherited != null) {
        // %OPT% Count-down might be faster, but debuggability may
        // be better this way, and if we ever decide we want to
        // keep this ordered by expanded-type...
        final int isize = inherited.size();

        // Base the size of a new namespace list on the
        // size of the inherited list - but within reason!
        nsList = new SuballocatedIntVector(Math.max(Math.min(isize + 16, 2048), 32));

        for (int i = 0; i < isize; ++i) {
          nsList.addElement(inherited.elementAt(i));
        }
      } else {
        nsList = new SuballocatedIntVector(32);
      }

      m_namespaceDeclSets.add(nsList);
    }

    // Handle overwriting inherited.
    // %OPT% Keep sorted? (By expanded-name rather than by doc order...)
    // Downside: Would require insertElementAt if not found,
    // which has recopying costs. But these are generally short lists...
    final int newEType = _exptype(namespaceNodeIndex);

    for (int i = nsList.size() - 1; i >= 0; --i) {
      if (newEType == getExpandedTypeID(nsList.elementAt(i))) {
        nsList.setElementAt(makeNodeHandle(namespaceNodeIndex), i);
        return;
      }
    }
    nsList.addElement(makeNodeHandle(namespaceNodeIndex));
  }

  /**
   * Retrieve list of namespace declaration locations active at this node. List is an
   * SuballocatedIntVector whose entries are the namespace node HANDLES declared at that ID.
   *
   * <p>%REVIEW% Directly managed arrays rather than vectors? %REVIEW% Handles or IDs? Given usage,
   * I think handles.
   */
  protected SuballocatedIntVector findNamespaceContext(final int elementNodeIndex) {
    if (null != m_namespaceDeclSetElements) {
      // %OPT% Is binary-search really saving us a lot versus linear?
      // (... It may be, in large docs with many NS decls.)
      int wouldBeAt =
          findInSortedSuballocatedIntVector(m_namespaceDeclSetElements, elementNodeIndex);
      if (wouldBeAt >= 0) {
        // Found it
        return m_namespaceDeclSets.get(wouldBeAt);
      }
      if (wouldBeAt == -1) {
        // -1-wouldbeat == 0
        return null; // Not after anything; definitely not found
      }

      // Not found, but we know where it should have been.
      // Search back until we find an ancestor or run out.
      wouldBeAt = -1 - wouldBeAt;

      // Decrement wouldBeAt to find last possible ancestor
      int candidate = m_namespaceDeclSetElements.elementAt(--wouldBeAt);
      int ancestor = _parent(elementNodeIndex);

      // Special case: if the candidate is before the given node, and
      // is in the earliest possible position in the document, it
      // must have the namespace declarations we're interested in.
      if (wouldBeAt == 0 && candidate < ancestor) {
        final int rootHandle = getDocumentRoot(makeNodeHandle(elementNodeIndex));
        final int rootID = makeNodeIdentity(rootHandle);
        final int uppermostNSCandidateID;

        if (getNodeType(rootHandle) == DTM.DOCUMENT_NODE) {
          final int ch = _firstch(rootID);
          uppermostNSCandidateID = (ch != DTM.NULL) ? ch : rootID;
        } else {
          uppermostNSCandidateID = rootID;
        }

        if (candidate == uppermostNSCandidateID) {
          return m_namespaceDeclSets.get(wouldBeAt);
        }
      }

      while (wouldBeAt >= 0 && ancestor > 0) {
        if (candidate == ancestor) {
          // Found ancestor in list
          return m_namespaceDeclSets.get(wouldBeAt);
        } else if (candidate < ancestor) {
          // Too deep in tree
          do {
            ancestor = _parent(ancestor);
          } while (candidate < ancestor);
        } else if (wouldBeAt > 0) {
          // Too late in list
          candidate = m_namespaceDeclSetElements.elementAt(--wouldBeAt);
        } else break;
      }
    }

    return null; // No namespaces known at this node
  }

  /**
   * Subroutine: Locate the specified node within m_namespaceDeclSetElements, or the last element
   * which preceeds it in document order
   *
   * <p>%REVIEW% Inlne this into findNamespaceContext? Create SortedSuballocatedIntVector type?
   *
   * @param vector the vector
   * @param lookfor the lookfor
   * @return If positive or zero, the index of the found item. If negative, index of the point at
   *     which it would have appeared, encoded as -1-index and hence reconvertable by subtracting it
   *     from -1. (Encoding because I don't want to recompare the strings but don't want to burn
   *     bytes on a datatype to hold a flagged value.)
   */
  protected int findInSortedSuballocatedIntVector(final SuballocatedIntVector vector, final int lookfor) {
    // Binary search
    int i = 0;
    if (vector != null) {
      int first = 0;
      int last = vector.size() - 1;

      while (first <= last) {
        i = (first + last) / 2;
        final int test = lookfor - vector.elementAt(i);
        if (test == 0) {
          return i; // Name found
        } else if (test < 0) {
          last = i - 1; // looked too late
        } else {
          first = i + 1; // looked ot early
        }
      }

      if (first > i) {
        i = first; // Clean up at loop end
      }
    }

    return -1 - i; // not-found has to be encoded.
  }

  /** {@inheritDoc} */
  @Override
  public int getFirstNamespaceNode(final int nodeHandle, final boolean inScope) {
    if (inScope) {
      final int identity = makeNodeIdentity(nodeHandle);
      if (_type(identity) == DTM.ELEMENT_NODE) {
        final SuballocatedIntVector nsContext = findNamespaceContext(identity);
        if (nsContext == null || nsContext.size() < 1) return NULL;

        return nsContext.elementAt(0);
      }
      return NULL;
    }
    // Assume that attributes and namespaces immediately
    // follow the element.
    //
    // %OPT% Would things be faster if all NS nodes were built
    // before all Attr nodes? Some costs at build time for 2nd
    // pass...
    int identity = makeNodeIdentity(nodeHandle);
    if (_type(identity) == DTM.ELEMENT_NODE) {
      while (DTM.NULL != (identity = getNextNodeIdentity(identity))) {
        final int type = _type(identity);
        if (type == DTM.NAMESPACE_NODE) return makeNodeHandle(identity);
        else if (DTM.ATTRIBUTE_NODE != type) break;
      }
      return NULL;
    }
    return NULL;
  }

  /** {@inheritDoc} */
  @Override
  public int getNextNamespaceNode(final int baseHandle, final int nodeHandle, final boolean inScope) {
    if (inScope) {
      // Since we've been given the base, try direct lookup
      // (could look from nodeHandle but this is at least one
      // comparison/get-parent faster)
      // SuballocatedIntVector nsContext=findNamespaceContext(nodeHandle & m_mask);

      final SuballocatedIntVector nsContext = findNamespaceContext(makeNodeIdentity(baseHandle));

      if (nsContext == null) return NULL;
      final int i = 1 + nsContext.indexOf(nodeHandle);
      if (i <= 0 || i == nsContext.size()) return NULL;

      return nsContext.elementAt(i);
    }
    // Assume that attributes and namespace nodes immediately follow the element.
    int identity = makeNodeIdentity(nodeHandle);
    while (DTM.NULL != (identity = getNextNodeIdentity(identity))) {
      final int type = _type(identity);
      if (type == DTM.NAMESPACE_NODE) {
        return makeNodeHandle(identity);
      } else if (type != DTM.ATTRIBUTE_NODE) {
        break;
      }
    }
    return DTM.NULL;
  }

  /** {@inheritDoc} */
  @Override
  public int getParent(final int nodeHandle) {

    final int identity = makeNodeIdentity(nodeHandle);

    if (identity > 0) {
      return makeNodeHandle(_parent(identity));
    }
    return DTM.NULL;
  }

  /** {@inheritDoc} */
  @Override
  public int getDocument() {
    return m_dtmIdent.elementAt(0); // makeNodeHandle(0)
  }

  /** {@inheritDoc} */
  @Override
  public int getOwnerDocument(final int nodeHandle) {

    if (DTM.DOCUMENT_NODE == getNodeType(nodeHandle)) return DTM.NULL;

    return getDocumentRoot(nodeHandle);
  }

  /** {@inheritDoc} */
  @Override
  public int getDocumentRoot(final int nodeHandle) {
    return getManager().getDTM(nodeHandle).getDocument();
  }

  /** {@inheritDoc} */
  @Override
  public abstract XString getStringValue(int nodeHandle);

  /** {@inheritDoc} */
  @Override
  public int getExpandedTypeID(final int nodeHandle) {
    // %REVIEW% This _should_ only be null if someone asked the wrong DTM about the
    // node...
    // which one would hope would never happen...
    final int id = makeNodeIdentity(nodeHandle);
    if (id == NULL) return NULL;
    return _exptype(id);
  }

  /** {@inheritDoc} */
  @Override
  public int getExpandedTypeID(final String namespace, final String localName, final int type) {

    return m_expandedNameTable.getExpandedTypeID(namespace, localName, type);
  }

  /** {@inheritDoc} */
  @Override
  public abstract String getNodeName(int nodeHandle);

  /** {@inheritDoc} */
  @Override
  public String getNodeNameX(final int nodeHandle) {

    /* @todo: implement this org.apache.xml.dtm.DTMDefaultBase abstract method */
    error(XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_METHOD_NOT_SUPPORTED, null));

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public abstract String getLocalName(int nodeHandle);

  /** {@inheritDoc} */
  @Override
  public abstract String getPrefix(int nodeHandle);

  /** {@inheritDoc} */
  @Override
  public abstract String getNamespaceURI(int nodeHandle);

  /** {@inheritDoc} */
  @Override
  public abstract String getNodeValue(int nodeHandle);

  /** {@inheritDoc} */
  @Override
  public short getNodeType(final int nodeHandle) {
    if (nodeHandle == DTM.NULL) return DTM.NULL;
    return m_expandedNameTable.getType(_exptype(makeNodeIdentity(nodeHandle)));
  }

  /** {@inheritDoc} */
  @Override
  public abstract int getElementById(final String elementId);

  /** {@inheritDoc} */
  @Override
  public boolean isNodeAfter(final int nodeHandle1, final int nodeHandle2) {
    // These return NULL if the node doesn't belong to this document.
    final int index1 = makeNodeIdentity(nodeHandle1);
    final int index2 = makeNodeIdentity(nodeHandle2);

    return index1 != NULL && index2 != NULL && index1 <= index2;
  }

  /** {@inheritDoc} */
  @Override
  public Node getNode(final int nodeHandle) {
    return new DTMNodeProxy(this, nodeHandle);
  }

  /**
   * Simple error for asserts and the like.
   *
   * @param msg Error message to report.
   */
  protected void error(final String msg) {
    throw new DTMException(msg);
  }

  /**
   * Query which DTMManager this DTM is currently being handled by.
   *
   * <p>%REVEW% Should this become part of the base DTM API?
   *
   * @return a DTMManager, or null if this is a "stand-alone" DTM.
   */
  public DTMManager getManager() {
    return m_mgr;
  }
}
