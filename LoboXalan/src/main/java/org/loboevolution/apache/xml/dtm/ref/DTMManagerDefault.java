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
package org.loboevolution.apache.xml.dtm.ref;

import org.loboevolution.html.node.Attr;
import org.loboevolution.javax.xml.transform.Source;
import org.loboevolution.javax.xml.transform.dom.DOMSource;

import org.loboevolution.apache.xml.dtm.ref.dom2dtm.DOM2DTM;
import org.loboevolution.apache.xml.dtm.ref.dom2dtm.DOM2DTMdefaultNamespaceDeclarationNode;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMException;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.dtm.DTMManager;
import org.loboevolution.apache.xml.utils.PrefixResolver;
import org.loboevolution.html.node.Node;

/**
 * The default implementation for the DTMManager.
 *
 * <p>%REVIEW% There is currently a reentrancy issue, since the finalizer for XRTreeFrag (which runs
 * in the GC thread) wants to call DTMManager.release(), and may do so at the same time that the
 * main transformation thread is accessing the manager. Our current solution is to make most of the
 * manager's methods <code>synchronized</code>. Early tests suggest that doing so is not causing a
 * significant performance hit in Xalan. However, it should be noted that there is a possible
 * alternative solution: rewrite release() so it merely posts a request for release onto a
 * threadsafe queue, and explicitly process that queue on an infrequent basis during main-thread
 * activity (eg, when getDTM() is invoked). The downside of that solution would be a greater delay
 * before the DTM's storage is actually released for reuse.
 */
public class DTMManagerDefault extends DTMManager {
  // static final boolean JKESS_XNI_EXPERIMENT=true;

  /** Set this to true if you want a dump of the DTM after creation. */
  private static final boolean DUMPTREE = false;

  /** Set this to true if you want a basic diagnostics. */
  private static final boolean DEBUG = false;

  /**
   * Map from DTM identifier numbers to DTM objects that this manager manages. One DTM may have
   * several prefix numbers, if extended node indexing is in use; in that case, m_dtm_offsets[] will
   * used to control which prefix maps to which section of the DTM.
   *
   * <p>This array grows as necessary; see addDTM().
   *
   * <p>This array grows as necessary; see addDTM(). Growth is uncommon... but access needs to be
   * blindingly fast since it's used in node addressing.
   */
  protected DTM[] m_dtms = new DTM[256];

  /**
   * Map from DTM identifier numbers to offsets. For small DTMs with a single identifier, this will
   * always be 0. In overflow addressing, where additional identifiers are allocated to access nodes
   * beyond the range of a single Node Handle, this table is used to map the handle's node field
   * into the actual node identifier.
   *
   * <p>This array grows as necessary; see addDTM().
   *
   * <p>This array grows as necessary; see addDTM(). Growth is uncommon... but access needs to be
   * blindingly fast since it's used in node addressing. (And at the moment, that includes accessing
   * it from DTMDefaultBase, which is why this is not Protected or Private.)
   */
  int[] m_dtm_offsets = new int[256];

  /**
   * Add a DTM to the DTM table.
   *
   * @param dtm Should be a valid reference to a DTM.
   * @param id Integer DTM ID to be bound to this DTM.
   * @param offset Integer addressing offset. The internal DTM Node ID is obtained by adding this
   *     offset to the node-number field of the public DTM Handle. For the first DTM ID accessing
   *     each DTM, this is 0; for overflow addressing it will be a multiple of
   *     1&lt;&lt;IDENT_DTM_NODE_BITS.
   */
  public synchronized void addDTM(DTM dtm, int id, int offset) {
    if (id >= IDENT_MAX_DTMS) {
      throw new DTMException(
          XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NO_DTMIDS_AVAIL, null));
    }

    // We used to just allocate the array size to IDENT_MAX_DTMS.
    // But we expect to increase that to 16 bits, and I'm not willing
    // to allocate that much space unless needed. We could use one of our
    // handy-dandy Fast*Vectors, but this will do for now.
    // %REVIEW%
    int oldlen = m_dtms.length;
    if (oldlen <= id) {
      // Various growth strategies are possible. I think we don't want
      // to over-allocate excessively, and I'm willing to reallocate
      // more often to get that. See also Fast*Vector classes.
      //
      // %REVIEW% Should throw a more diagnostic error if we go over the max...
      int newlen = Math.min(id + 256, IDENT_MAX_DTMS);

      DTM[] new_m_dtms = new DTM[newlen];
      System.arraycopy(m_dtms, 0, new_m_dtms, 0, oldlen);
      m_dtms = new_m_dtms;
      int[] new_m_dtm_offsets = new int[newlen];
      System.arraycopy(m_dtm_offsets, 0, new_m_dtm_offsets, 0, oldlen);
      m_dtm_offsets = new_m_dtm_offsets;
    }

    m_dtms[id] = dtm;
    m_dtm_offsets[id] = offset;
    // The DTM should have been told who its manager was when we created it.
    // Do we need to allow for adopting DTMs _not_ created by this manager?
  }

  /** @return the first free DTM ID available. %OPT% Linear search is inefficient! */
  public synchronized int getFirstFreeDTMID() {
    int n = m_dtms.length;
    for (int i = 1; i < n; i++) {
      if (null == m_dtms[i]) {
        return i;
      }
    }
    return n; // count on addDTM() to throw exception if out of range
  }

  /** The default table for exandedNameID lookups. */
  private final ExpandedNameTable m_expandedNameTable = new ExpandedNameTable();

  /** Constructor DTMManagerDefault */
  public DTMManagerDefault() {}

  @Override
  public synchronized DTM getDTM(
      Source source, boolean unique, boolean incremental, boolean doIndexing) {

    if (DEBUG && null != source)
      System.out.println(
          "Starting " + (unique ? "UNIQUE" : "shared") + " source: " + source.getSystemId());

    int dtmPos = getFirstFreeDTMID();
    int documentID = dtmPos << IDENT_DTM_NODE_BITS;

    if ((null != source) && source instanceof DOMSource) {
      DOM2DTM dtm = new DOM2DTM(this, (DOMSource) source, documentID, doIndexing);

      addDTM(dtm, dtmPos, 0);
      return dtm;
    }

    // It should have been handled by a derived class or the caller
    // made a mistake.
    throw new DTMException(
        XPATHMessages.createXPATHMessage(
            XPATHErrorResources.ER_NOT_SUPPORTED, new Object[] {source}));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized int getDTMHandleFromNode(Node node) {
    if (null == node)
      throw new IllegalArgumentException(
          XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_NODE_NON_NULL, null));

    if (node instanceof DTMNodeProxy)
      return ((DTMNodeProxy) node).getDTMNodeNumber();
    else {
      // Find the DOM2DTMs wrapped around this Document (if any)
      // and check whether they contain the Node in question.
      //
      // NOTE that since a DOM2DTM may represent a subtree rather
      // than a full document, we have to be prepared to check more
      // than one -- and there is no guarantee that we will find
      // one that contains ancestors or siblings of the node we're
      // seeking.
      //
      // %REVIEW% We could search for the one which contains this
      // node at the deepest level, and thus covers the widest
      // subtree, but that's going to entail additional work
      // checking more DTMs... and getHandleOfNode is not a
      // cheap operation in most implementations.
      //
      // TODO: %REVIEW% If overflow addressing, we may recheck a DTM
      // already examined. Ouch. But with the increased number of DTMs,
      // scanning back to check this is painful.
      // POSSIBLE SOLUTIONS:
      // Generate a list of _unique_ DTM objects?
      // Have each DTM cache last DOM node search?
      for (DTM thisDTM : m_dtms) {
        if ((null != thisDTM) && thisDTM instanceof DOM2DTM) {
          int handle = ((DOM2DTM) thisDTM).getHandleOfNode(node);
          if (handle != DTM.NULL) return handle;
        }
      }

      // Not found; generate a new DTM.
      //
      // %REVIEW% Is this really desirable, or should we return null
      // and make folks explicitly instantiate from a DOMSource? The
      // latter is more work but gives the caller the opportunity to
      // explicitly add the DTM to a DTMManager... and thus to know when
      // it can be discarded again, which is something we need to pay much
      // more attention to. (Especially since only DTMs which are assigned
      // to a manager can use the overflow addressing scheme.)
      //
      // %BUG% If the source node was a DOM2DTM$defaultNamespaceDeclarationNode
      // and the DTM wasn't registered with this DTMManager, we will create
      // a new DTM and _still_ not be able to find the node (since it will
      // be resynthesized). Another reason to push hard on making all DTMs
      // be managed DTMs.

      // Since the real root of our tree may be a DocumentFragment, we need to
      // use getParent to find the root, instead of getOwnerDocument. Otherwise
      // DOM2DTM#getHandleOfNode will be very unhappy.
      Node root = node;
      Node p =
          (root.getNodeType() == Node.ATTRIBUTE_NODE)
              ? ((Attr) root).getOwnerElement()
              : root.getParentNode();
      for (; p != null; p = p.getParentNode()) {
        root = p;
      }

      DOM2DTM dtm =
          (DOM2DTM) getDTM(new DOMSource(root), false, true, true);

      int handle;

      if (node
          instanceof
              DOM2DTMdefaultNamespaceDeclarationNode) {
        // Can't return the same node since it's unique to a specific DTM,
        // but can return the equivalent node -- find the corresponding
        // Document Element, then ask it for the xml: namespace decl.
        handle = dtm.getHandleOfNode(((Attr) node).getOwnerElement());
        handle = dtm.getAttributeNode(handle, node.getNamespaceURI(), node.getLocalName());
      } else handle = dtm.getHandleOfNode(node);

      if (DTM.NULL == handle)
        throw new RuntimeException(
            XPATHMessages.createXPATHMessage(XPATHErrorResources.ER_COULD_NOT_RESOLVE_NODE, null));

      return handle;
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized DTM getDTM(int nodeHandle) {
    try {
      // Performance critical function.
      return m_dtms[nodeHandle >>> IDENT_DTM_NODE_BITS];
    } catch (ArrayIndexOutOfBoundsException e) {
      if (nodeHandle == DTM.NULL) return null; // Accept as a special case.
      else throw e; // Programming error; want to know about it.
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized DTMIterator createDTMIterator(
      int whatToShow, DTMFilter filter, boolean entityReferenceExpansion) {

    return null;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized DTMIterator createDTMIterator(String xpathString, PrefixResolver presolver) {
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public synchronized DTMIterator createDTMIterator(Object xpathCompiler, int pos) {
    return null;
  }

  /**
   * @return the expanded name table.
   *     <p>NEEDSDOC @param dtm
   *     <p>NEEDSDOC ($objectName$) @return
   */
  public ExpandedNameTable getExpandedNameTable() {
    return m_expandedNameTable;
  }
}
