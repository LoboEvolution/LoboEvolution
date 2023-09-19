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
package org.loboevolution.apache.xpath.axes;

import org.loboevolution.apache.xpath.NodeSetDTM;
import org.loboevolution.apache.xpath.XPathContext;
import org.loboevolution.apache.xpath.objects.XObject;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMFilter;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.dtm.DTMManager;
import org.loboevolution.apache.xml.utils.NodeVector;

/**
 * This class is the dynamic wrapper for a Xalan DTMIterator instance, and provides random access
 * capabilities.
 */
public class NodeSequence extends XObject implements DTMIterator, Cloneable, PathComponent {

  /** The index of the last node in the iteration. */
  protected int m_last = -1;

  /**
   * The index of the next node to be fetched. Useful if this is a cached iterator, and is being
   * used as random access NodeList.
   */
  protected int m_next = 0;

  /**
   * A cache of a list of nodes obtained from the iterator so far. This list is appended to until
   * the iterator is exhausted and the cache is complete.
   *
   * <p>Multiple NodeSequence objects may share the same cache.
   */
  private IteratorCache m_cache;

  /**
   * If this iterator needs to cache nodes that are fetched, they are stored in the Vector in the
   * generic object.
   */
  protected NodeVector getVector() {
    return (m_cache != null) ? m_cache.getVector() : null;
  }

  /**
   * Get the cache (if any) of nodes obtained from the iterator so far. Note that the cache keeps
   * growing until the iterator is walked to exhaustion, at which point the cache is "complete".
   */
  private IteratorCache getCache() {
    return m_cache;
  }

  /** Set the vector where nodes will be cached. */
  protected void SetVector(NodeVector v) {
    setObject(v);
  }

  /** If the iterator needs to cache nodes as they are fetched, then this method returns true. */
  public boolean hasCache() {
    final NodeVector nv = getVector();
    return nv != null;
  }

  /**
   * If this NodeSequence has a cache, and that cache is fully populated then this method returns
   * true, otherwise if there is no cache or it is not complete it returns false.
   */
  private boolean cacheComplete() {
    final boolean complete;
    if (m_cache != null) {
      complete = m_cache.isComplete();
    } else {
      complete = false;
    }
    return complete;
  }

  /**
   * If this NodeSequence has a cache, mark that it is complete. This method should be called after
   * the iterator is exhausted.
   */
  private void markCacheComplete() {
    NodeVector nv = getVector();
    if (nv != null) {
      m_cache.setCacheComplete(true);
    }
  }

  /** The functional iterator that fetches nodes. */
  protected DTMIterator m_iter;

  /**
   * Set the functional iterator that fetches nodes.
   *
   * @param iter The iterator that is to be contained.
   */
  public final void setIter(DTMIterator iter) {
    m_iter = iter;
  }

  /**
   * The DTMManager to use if we're using a NodeVector only. We may well want to do away with this,
   * and store it in the NodeVector.
   */
  protected DTMManager m_dtmMgr;

  // ==== Constructors ====

  /**
   * Create a new NodeSequence from a (already cloned) iterator.
   *
   * @param nodeVector
   */
  public NodeSequence(Object nodeVector) {
    super(nodeVector);
    if (nodeVector instanceof NodeVector) {
      SetVector((NodeVector) nodeVector);
    }
    if (null != nodeVector) {
      assertion(
          nodeVector instanceof NodeVector,
          "Must have a NodeVector as the object for NodeSequence!");
      if (nodeVector instanceof DTMIterator) {
        setIter((DTMIterator) nodeVector);
        m_last = ((DTMIterator) nodeVector).getLength();
      }
    }
  }

  /** Create a new NodeSequence in an invalid (null) state. */
  public NodeSequence() {}

  /** {@inheritDoc} */
  @Override
  public DTM getDTM(int nodeHandle) {
    DTMManager mgr = getDTMManager();
    if (null != mgr) {
      return getDTMManager().getDTM(nodeHandle);
    }
    assertion(false, "Can not get a DTM Unless a DTMManager has been set!");
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public DTMManager getDTMManager() {
    return m_dtmMgr;
  }

  /** {@inheritDoc} */
  @Override
  public int getRoot() {
    if (null != m_iter) {
      return m_iter.getRoot();
    }
    // NodeSetDTM will call this, and so it's not a good thing to throw
    // an assertion here.
    // assertion(false, "Can not get the root from a non-iterated NodeSequence!");
    return DTM.NULL;
  }

  /** {@inheritDoc} */
  @Override
  public void setRoot(int nodeHandle, Object environment) {
    if (null != m_iter) {
      XPathContext xctxt = (XPathContext) environment;
      m_dtmMgr = xctxt.getDTMManager();
      m_iter.setRoot(nodeHandle, environment);
      if (!m_iter.isDocOrdered()) {
        if (!hasCache()) setShouldCacheNodes(true);
        runTo(-1);
        m_next = 0;
      }
    } else assertion(false, "Can not setRoot on a non-iterated NodeSequence!");
  }

  /** {@inheritDoc} */
  @Override
  public void reset() {
    m_next = 0;
    // not resetting the iterator on purpose!!!
  }

  /** {@inheritDoc} */
  @Override
  public int getWhatToShow() {
    return hasCache()
        ? (DTMFilter.SHOW_ALL & ~DTMFilter.SHOW_ENTITY_REFERENCE)
        : m_iter.getWhatToShow();
  }

  /** {@inheritDoc} */
  @Override
  public boolean getExpandEntityReferences() {
    if (null != m_iter) {
      return m_iter.getExpandEntityReferences();
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public int nextNode() {
    // If the cache is on, and the node has already been found, then
    // just return from the list.
    NodeVector vec = getVector();
    if (null != vec) {
      // There is a cache
      if (m_next < vec.size()) {
        // The node is in the cache, so just return it.
        int next = vec.elementAt(m_next);
        m_next++;
        return next;
      } else if (cacheComplete() || (-1 != m_last) || (null == m_iter)) {
        m_next++;
        return DTM.NULL;
      }
    }

    if (null == m_iter) return DTM.NULL;

    int next = m_iter.nextNode();
    if (DTM.NULL != next) {
      if (hasCache()) {
        if (m_iter.isDocOrdered()) {
          getVector().addElement(next);
          m_next++;
        } else {
          int insertIndex = addNodeInDocOrder(next);
          if (insertIndex >= 0) m_next++;
        }
      } else m_next++;
    } else {
      // We have exhausted the iterator, and if there is a cache
      // it must have all nodes in it by now, so let the cache
      // know that it is complete.
      markCacheComplete();

      m_last = m_next;
      m_next++;
    }

    return next;
  }

  /** {@inheritDoc} */
  @Override
  public int previousNode() {
    if (hasCache()) {
      if (m_next <= 0) {
        return DTM.NULL;
      }

      m_next--;
      return item(m_next);
    }

    m_iter.previousNode();
    m_next = m_iter.getCurrentPos();
    return m_next;
  }

  /** {@inheritDoc} */
  @Override
  public void detach() {
    if (null != m_iter) m_iter.detach();
    super.detach();
  }

  /** {@inheritDoc} */
  @Override
  public void allowDetachToRelease(boolean allowRelease) {
    if (!allowRelease && !hasCache()) {
      setShouldCacheNodes(true);
    }

    if (null != m_iter) m_iter.allowDetachToRelease(allowRelease);
    super.allowDetachToRelease(allowRelease);
  }

  /** {@inheritDoc} */
  @Override
  public int getCurrentNode() {
    if (hasCache()) {
      int currentIndex = m_next - 1;
      NodeVector vec = getVector();
      if ((currentIndex >= 0) && (currentIndex < vec.size())) {
        return vec.elementAt(currentIndex);
      }
      return DTM.NULL;
    }

    if (null != m_iter) {
      return m_iter.getCurrentNode();
    }
    return DTM.NULL;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isFresh() {
    return 0 == m_next;
  }

  /** {@inheritDoc} */
  @Override
  public void setShouldCacheNodes(boolean b) {
    if (b) {
      if (!hasCache()) {
        SetVector(new NodeVector());
      }
    } else SetVector(null);
  }

  /** {@inheritDoc} */
  @Override
  public int getCurrentPos() {
    return m_next;
  }

  /** {@inheritDoc} */
  @Override
  public void runTo(int index) {
    if (-1 == index) {
      int pos = m_next;
      while (DTM.NULL != nextNode()) ;
      m_next = pos;
    } else if (m_next == index) {
    } else if (hasCache() && m_next < getVector().size()) {
      m_next = index;
    } else if ((null == getVector()) && (index < m_next)) {
      while ((m_next >= index) && DTM.NULL != previousNode()) ;
    } else {
      while ((m_next < index) && DTM.NULL != nextNode()) ;
    }
  }

  /** {@inheritDoc} */
  @Override
  public void setCurrentPos(int i) {
    runTo(i);
  }

  /** {@inheritDoc} */
  @Override
  public int item(int index) {
    setCurrentPos(index);
    int n = nextNode();
    m_next = index;
    return n;
  }

  /** {@inheritDoc} */
  @Override
  public int getLength() {
    IteratorCache cache = getCache();

    if (cache != null) {
      // Nodes from the iterator are cached
      if (cache.isComplete()) {
        // All of the nodes from the iterator are cached
        // so just return the number of nodes in the cache
        NodeVector nv = cache.getVector();
        return nv.size();
      }

      // If this NodeSequence wraps a mutable nodeset, then
      // m_last will not reflect the size of the nodeset if
      // it has been mutated...
      if (m_iter instanceof NodeSetDTM) {
        return m_iter.getLength();
      }

      if (-1 == m_last) {
        int pos = m_next;
        runTo(-1);
        m_next = pos;
      }
      return m_last;
    }

    return (-1 == m_last) ? (m_last = m_iter.getLength()) : m_last;
  }

  /** {@inheritDoc} */
  @Override
  public DTMIterator cloneWithReset() throws CloneNotSupportedException {
    NodeSequence seq = (NodeSequence) super.clone();
    seq.m_next = 0;

    return seq;
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {
    NodeSequence clone = (NodeSequence) super.clone();
    if (null != m_iter) clone.m_iter = (DTMIterator) m_iter.clone();

    return clone;
  }

  /** {@inheritDoc} */
  @Override
  public boolean isDocOrdered() {
    if (null != m_iter) {
      return m_iter.isDocOrdered();
    }

    return true; // can't be sure?
  }

  /** {@inheritDoc} */
  @Override
  public int getAxis() {
    if (null != m_iter) {
      return m_iter.getAxis();
    }

    assertion(false, "Can not getAxis from a non-iterated node sequence!");
    return 0;
  }

  /** {@inheritDoc} */
  @Override
  public int getAnalysisBits() {
    if ((null != m_iter) && (m_iter instanceof PathComponent)) {
      return ((PathComponent) m_iter).getAnalysisBits();
    }

    return 0;
  }

  /**
   * Add the node into a vector of nodes where it should occur in document order.
   *
   * @param node The node to be added.
   * @return insertIndex.
   * @throws RuntimeException thrown if this NodeSetDTM is not of a mutable type.
   */
  protected int addNodeInDocOrder(int node) {
    assertion(hasCache(), "addNodeInDocOrder must be done on a mutable sequence!");

    int insertIndex = -1;

    NodeVector vec = getVector();

    // This needs to do a binary search, but a binary search
    // is somewhat tough because the sequence test involves
    // two nodes.
    int size = vec.size(), i;

    for (i = size - 1; i >= 0; i--) {
      int child = vec.elementAt(i);

      if (child == node) {
        i = -2; // Duplicate, suppress insert

        break;
      }

      DTM dtm = m_dtmMgr.getDTM(node);
      if (!dtm.isNodeAfter(node, child)) {
        break;
      }
    }

    if (i != -2) {
      insertIndex = i + 1;

      vec.insertElementAt(node, insertIndex);
    }

    // checkDups();
    return insertIndex;
  } // end addNodeInDocOrder(Vector v, Object obj)

  /** {@inheritDoc} */
  @Override
  protected void setObject(Object obj) {
    if (obj instanceof NodeVector) {
      // Keep our superclass informed of the current NodeVector
      // ... if we don't the smoketest fails (don't know why).
      super.setObject(obj);

      // A copy of the code of what SetVector() would do.
      NodeVector v = (NodeVector) obj;
      if (m_cache != null) {
        m_cache.setVector(v);
      } else if (v != null) {
        m_cache = new IteratorCache();
        m_cache.setVector(v);
      }
    } else if (obj instanceof IteratorCache) {
      IteratorCache cache = (IteratorCache) obj;
      m_cache = cache;

      // Keep our superclass informed of the current NodeVector
      super.setObject(cache.getVector());
    } else {
      super.setObject(obj);
    }
  }

  /**
   * Each NodeSequence object has an iterator which is "walked". As an iterator is walked one
   * obtains nodes from it. As those nodes are obtained they may be cached, making the next walking
   * of a copy or clone of the iterator faster. This field (m_cache) is a reference to such a cache,
   * which is populated as the iterator is walked.
   *
   * <p>Note that multiple NodeSequence objects may hold a reference to the same cache, and also
   * (and this is important) the same iterator. The iterator and its cache may be shared among many
   * NodeSequence objects.
   *
   * <p>If one of the NodeSequence objects walks ahead of the others it fills in the cache. As the
   * others NodeSequence objects catch up they get their values from the cache rather than the
   * iterator itself, so the iterator is only ever walked once and everyone benefits from the cache.
   *
   * <p>At some point the cache may be complete due to walking to the end of one of the copies of
   * the iterator, and the cache is then marked as "complete". and the cache will have no more nodes
   * added to it.
   *
   * <p>Its use-count is the number of NodeSequence objects that use it.
   */
  private static final class IteratorCache {
    /**
     * A list of nodes already obtained from the iterator. As the iterator is walked the nodes
     * obtained from it are appended to this list.
     *
     * <p>Both an iterator and its corresponding cache can be shared by multiple NodeSequence
     * objects.
     *
     * <p>For example, consider three NodeSequence objects ns1, ns2 and ns3 doing such sharing, and
     * the nodes to be obtaind from the iterator being the sequence { 33, 11, 44, 22, 55 }.
     *
     * <p>If ns3.nextNode() is called 3 times the the underlying iterator will have walked through
     * 33, 11, 55 and these three nodes will have been put in the cache.
     *
     * <p>If ns2.nextNode() is called 2 times it will return 33 and 11 from the cache, leaving the
     * iterator alone.
     *
     * <p>If ns1.nextNode() is called 6 times it will return 33 and 11 from the cache, then get 44,
     * 22, 55 from the iterator, and appending 44, 22, 55 to the cache. On the sixth call it is
     * found that the iterator is exhausted and the cache is marked complete.
     *
     * <p>Should ns2 or ns3 have nextNode() called they will know that the cache is complete, and
     * they will obtain all subsequent nodes from the cache.
     *
     * <p>Note that the underlying iterator, though shared is only ever walked once.
     */
    private NodeVector m_vec2;

    /**
     * true if the associated iterator is exhausted and all nodes obtained from it are in the cache.
     */
    private boolean m_isComplete2;

    IteratorCache() {
      m_vec2 = null;
      m_isComplete2 = false;
    }

    /**
     * Sets the NodeVector that holds the growing list of nodes as they are appended to the cached
     * list.
     */
    private void setVector(NodeVector nv) {
      m_vec2 = nv;
    }

    /** Get the cached list of nodes obtained from the iterator so far. */
    private NodeVector getVector() {
      return m_vec2;
    }

    /**
     * Call this method with 'true' if the iterator is exhausted and the cached list is complete, or
     * no longer growing.
     */
    private void setCacheComplete(boolean b) {
      m_isComplete2 = b;
    }

    /** Returns true if no cache is complete and immutable. */
    private boolean isComplete() {
      return m_isComplete2;
    }
  }

  /**
   * Get the cached list of nodes appended with values obtained from the iterator as a NodeSequence
   * is walked when its nextNode() method is called.
   */
  protected IteratorCache getIteratorCache() {
    return m_cache;
  }
}
