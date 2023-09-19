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

import org.loboevolution.javax.xml.transform.Source;

import org.loboevolution.apache.xml.dtm.Axis;
import org.loboevolution.apache.xml.dtm.DTM;
import org.loboevolution.apache.xml.dtm.DTMManager;
import org.loboevolution.apache.xml.utils.NodeVector;
import org.loboevolution.apache.xpath.res.XPATHErrorResources;
import org.loboevolution.apache.xpath.res.XPATHMessages;
import org.loboevolution.apache.xml.dtm.DTMAxisIterator;
import org.loboevolution.apache.xml.dtm.DTMAxisTraverser;
import org.loboevolution.apache.xml.dtm.DTMException;

/** This class implements the traversers for DTMDefaultBase. */
public abstract class DTMDefaultBaseIterators extends DTMDefaultBaseTraversers {

  /**
   * Construct a DTMDefaultBaseTraversers object from a DOM node.
   *
   * @param mgr The DTMManager who owns this DTM.
   * @param source The object that is used to specify the construction source.
   * @param dtmIdentity The DTM identity ID for this DTM.
   * @param doIndexing true if the caller considers it worth it to use indexing schemes.
   */
  public DTMDefaultBaseIterators(
          DTMManager mgr, Source source, int dtmIdentity, boolean doIndexing) {
    super(mgr, source, dtmIdentity, doIndexing);
  }

  /** {@inheritDoc} */
  @Override
  public DTMAxisIterator getAxisIterator(final int axis) {

    DTMAxisIterator iterator;

    switch (axis) {
      case Axis.SELF:
        iterator = new SingletonIterator();
        break;
      case Axis.CHILD:
        iterator = new ChildrenIterator();
        break;
      case Axis.PARENT:
        return new ParentIterator();
      case Axis.ANCESTOR:
        return new AncestorIterator();
      case Axis.ANCESTORORSELF:
        return (new AncestorIterator()).includeSelf();
      case Axis.ATTRIBUTE:
        return new AttributeIterator();
      case Axis.DESCENDANT:
        iterator = new DescendantIterator();
        break;
      case Axis.DESCENDANTORSELF:
        iterator = (new DescendantIterator()).includeSelf();
        break;
      case Axis.FOLLOWING:
        iterator = new FollowingIterator();
        break;
      case Axis.PRECEDING:
        iterator = new PrecedingIterator();
        break;
      case Axis.FOLLOWINGSIBLING:
        iterator = new FollowingSiblingIterator();
        break;
      case Axis.PRECEDINGSIBLING:
        iterator = new PrecedingSiblingIterator();
        break;
      case Axis.NAMESPACE:
        iterator = new NamespaceIterator();
        break;
      case Axis.ROOT:
        iterator = new RootIterator();
        break;
      default:
        throw new DTMException(
            XPATHMessages.createXPATHMessage(
                XPATHErrorResources.ER_ITERATOR_AXIS_NOT_IMPLEMENTED,
                new Object[] {Axis.getNames(axis)}));
    }

    return iterator;
  }

  /**
   * Abstract superclass defining behaviors shared by all DTMDefault's internal implementations of
   * DTMAxisIterator. Subclass this (and override, if necessary) to implement the specifics of an
   * individual axis iterator.
   *
   * <p>Currently there isn't a lot here
   */
  public abstract static class InternalAxisIteratorBase extends DTMAxisIteratorBase {

    // %REVIEW% We could opt to share _nodeType and setNodeType() as
    // well, and simply ignore them in iterators which don't use them.
    // But Scott's worried about the overhead involved in cloning
    // these, and wants them to have as few fields as possible. Note
    // that we can't create a TypedInternalAxisIteratorBase because
    // those are often based on the untyped versions and Java doesn't
    // support multiple inheritance. <sigh/>

    /**
     * Current iteration location. Usually this is the last location returned (starting point for
     * the next() search); for single-node iterators it may instead be initialized to point to that
     * single node.
     */
    protected int _currentNode;
  } // end of InternalAxisIteratorBase

  /** Iterator that returns all immediate children of a given node */
  public final class ChildrenIterator extends InternalAxisIteratorBase {

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        _startNode = node;
        _currentNode = (node == DTM.NULL) ? DTM.NULL : _firstch(makeNodeIdentity(node));

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {
      if (_currentNode != DTM.NULL) {
        int node = _currentNode;
        _currentNode = _nextsib(node);
        return returnNode(makeNodeHandle(node));
      }

      return END;
    }
  } // end of ChildrenIterator

  /**
   * Iterator that returns the parent of a given node. Note that this delivers only a single node;
   * if you want all the ancestors, see AncestorIterator.
   */
  public final class ParentIterator extends InternalAxisIteratorBase {

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        _startNode = node;
        _currentNode = getParent(node);

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {
      int result = _currentNode;

      /* The extended type ID that was requested. */

      _currentNode = END;

      return returnNode(result);
    }
  } // end of ParentIterator

  /**
   * Iterator that returns the namespace nodes as defined by the XPath data model for a given node.
   */
  public class NamespaceIterator extends InternalAxisIteratorBase {

    /** Constructor NamespaceAttributeIterator */
    public NamespaceIterator() {

      super();
    }

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        _startNode = node;
        _currentNode = getFirstNamespaceNode(node, true);

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {

      int node = _currentNode;

      if (DTM.NULL != node) _currentNode = getNextNamespaceNode(_startNode, node, true);

      return returnNode(node);
    }
  } // end of NamespaceIterator

  /** Iterator that returns the root node as defined by the XPath data model for a given node. */
  public class RootIterator extends InternalAxisIteratorBase {

    /** Constructor RootIterator */
    public RootIterator() {

      super();
    }

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {

      if (_isRestartable) {
        _startNode = getDocumentRoot(node);
        _currentNode = DTM.NULL;

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {
      if (_startNode == _currentNode) return DTM.NULL;

      _currentNode = _startNode;

      return returnNode(_startNode);
    }
  } // end of RootIterator

  /** Iterator that returns all siblings of a given node. */
  public class FollowingSiblingIterator extends InternalAxisIteratorBase {

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        _startNode = node;
        _currentNode = makeNodeIdentity(node);

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {
      _currentNode = (_currentNode == DTM.NULL) ? DTM.NULL : _nextsib(_currentNode);
      return returnNode(makeNodeHandle(_currentNode));
    }
  } // end of FollowingSiblingIterator

  /** Iterator that returns attribute nodes (of what nodes?) */
  public final class AttributeIterator extends InternalAxisIteratorBase {

    // assumes caller will pass element nodes

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        _startNode = node;
        _currentNode = getFirstAttributeIdentity(makeNodeIdentity(node));

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {

      final int node = _currentNode;

      if (node != DTM.NULL) {
        _currentNode = getNextAttributeIdentity(node);
        return returnNode(makeNodeHandle(node));
      }

      return DTM.NULL;
    }
  } // end of AttributeIterator

  /** Iterator that returns preceding siblings of a given node */
  public class PrecedingSiblingIterator extends InternalAxisIteratorBase {

    /** The node identity of _startNode for this iterator */
    protected int _startNodeID;

    /** {@inheritDoc} */
    @Override
    public boolean isReverse() {
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        _startNode = node;
        node = _startNodeID = makeNodeIdentity(node);

        if (node == DTM.NULL) {
          _currentNode = node;
          resetPosition();
          return;
        }

        int type = m_expandedNameTable.getType(_exptype(node));
        if (ExpandedNameTable.ATTRIBUTE == type || ExpandedNameTable.NAMESPACE == type) {
          _currentNode = node;
        } else {
          // Be careful to handle the Document node properly
          _currentNode = _parent(node);
          if (DTM.NULL != _currentNode) _currentNode = _firstch(_currentNode);
          else _currentNode = node;
        }

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {

      if (_currentNode == _startNodeID || _currentNode == DTM.NULL) {
        return DTM.NULL;
      }
      final int node = _currentNode;
      _currentNode = _nextsib(node);

      return returnNode(makeNodeHandle(node));
    }
  } // end of PrecedingSiblingIterator

  /**
   * Iterator that returns preceding nodes of a given node. This includes the node set {root+1,
   * start-1}, but excludes all ancestors, attributes, and namespace nodes.
   */
  public class PrecedingIterator extends InternalAxisIteratorBase {

    /** The max ancestors, but it can grow... */
    private final int _maxAncestors = 8;

    /** The stack of start node + ancestors up to the root of the tree, which we must avoid. */
    protected int[] _stack = new int[_maxAncestors];

    /** (not sure yet... -sb) */
    protected int _sp, _oldsp;

    /** {@inheritDoc} */
    @Override
    public boolean isReverse() {
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public DTMAxisIterator cloneIterator() {
      _isRestartable = false;

      try {
        final PrecedingIterator clone = (PrecedingIterator) super.clone();
        final int[] stackCopy = new int[_stack.length];
        System.arraycopy(_stack, 0, stackCopy, 0, _stack.length);

        clone._stack = stackCopy;

        // return clone.reset();
        return clone;
      } catch (CloneNotSupportedException e) {
        throw new DTMException(
            XPATHMessages.createXPATHMessage(
                XPATHErrorResources.ER_ITERATOR_CLONE_NOT_SUPPORTED, null));
      }
    }

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        node = makeNodeIdentity(node);

        // iterator is not a clone
        int parent, index;

        if (_type(node) == DTM.ATTRIBUTE_NODE) node = _parent(node);

        _startNode = node;
        _stack[index = 0] = node;

        parent = node;
        while ((parent = _parent(parent)) != DTM.NULL) {
          if (++index == _stack.length) {
            final int[] stack = new int[index + 4];
            System.arraycopy(_stack, 0, stack, 0, index);
            _stack = stack;
          }
          _stack[index] = parent;
        }
        if (index > 0) --index; // Pop actual root node (if not start) back off the stack

        _currentNode = _stack[index]; // Last parent before root node

        _oldsp = _sp = index;

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {
      // Bugzilla 8324: We were forgetting to skip Attrs and NS nodes.
      // Also recoded the loop controls for clarity and to flatten out
      // the tail-recursion.
      for (++_currentNode; _sp >= 0; ++_currentNode) {
        if (_currentNode < _stack[_sp]) {
          if (_type(_currentNode) != DTM.ATTRIBUTE_NODE && _type(_currentNode) != DTM.NAMESPACE_NODE)
            return returnNode(makeNodeHandle(_currentNode));
        } else --_sp;
      }
      return DTM.NULL;
    }

    // redefine DTMAxisIteratorBase's reset

    /** {@inheritDoc} */
    @Override
    public void reset() {

      _sp = _oldsp;

      resetPosition();
    }
  } // end of PrecedingIterator

  /** Iterator that returns following nodes of for a given node. */
  public class FollowingIterator extends InternalAxisIteratorBase {
    final DTMAxisTraverser m_traverser; // easier for now

    public FollowingIterator() {
      m_traverser = getAxisTraverser(Axis.FOLLOWING);
    }

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        _startNode = node;
        _currentNode = m_traverser.first(node);

        // _currentNode precedes possible following(node) nodes
        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {

      int node = _currentNode;

      _currentNode = m_traverser.next(_startNode, _currentNode);

      return returnNode(node);
    }
  } // end of FollowingIterator

  /**
   * Iterator that returns the ancestors of a given node in document order. (NOTE! This was changed
   * from the XSLTC code!)
   */
  public class AncestorIterator extends InternalAxisIteratorBase {
    final NodeVector m_ancestors =
        new NodeVector();

    int m_ancestorsPos;

    /** {@inheritDoc} */
    @Override
    public final boolean isReverse() {
      return true;
    }

    /** {@inheritDoc} */
    @Override
    public DTMAxisIterator cloneIterator() {
      _isRestartable = false; // must set to false for any clone

      try {
        final AncestorIterator clone = (AncestorIterator) super.clone();

        clone._startNode = _startNode;

        // return clone.reset();
        return clone;
      } catch (CloneNotSupportedException e) {
        throw new DTMException(
            XPATHMessages.createXPATHMessage(
                XPATHErrorResources.ER_ITERATOR_CLONE_NOT_SUPPORTED, null));
      }
    }

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();

      if (_isRestartable) {
        int nodeID = makeNodeIdentity(node);

        if (!_includeSelf && node != DTM.NULL) {
          nodeID = _parent(nodeID);
          node = makeNodeHandle(nodeID);
        }

        _startNode = node;

        while (nodeID != END) {
          m_ancestors.addElement(node);
          nodeID = _parent(nodeID);
          node = makeNodeHandle(nodeID);
        }
        m_ancestorsPos = m_ancestors.size() - 1;

        _currentNode = (m_ancestorsPos >= 0) ? m_ancestors.elementAt(m_ancestorsPos) : DTM.NULL;

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {

      m_ancestorsPos = m_ancestors.size() - 1;

      _currentNode = (m_ancestorsPos >= 0) ? m_ancestors.elementAt(m_ancestorsPos) : DTM.NULL;

      resetPosition();
    }

    /** {@inheritDoc} */
    @Override
    public int next() {

      int next = _currentNode;

      int pos = --m_ancestorsPos;

      _currentNode = (pos >= 0) ? m_ancestors.elementAt(m_ancestorsPos) : DTM.NULL;

      return returnNode(next);
    }
  } // end of AncestorIterator

  /** Iterator that returns the descendants of a given node. */
  public class DescendantIterator extends InternalAxisIteratorBase {

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isRestartable) {
        node = makeNodeIdentity(node);
        _startNode = node;

        if (_includeSelf) node--;

        _currentNode = node;

        resetPosition();
      }
    }

    /**
     * Tell if this node identity is a descendant. Assumes that the node info for the element has
     * already been obtained.
     *
     * <p>This one-sided test works only if the parent has been previously tested and is known to be
     * a descendent. It fails if the parent is the _startNode's next sibling, or indeed any node
     * that follows _startNode in document order. That may suffice for this iterator, but it's not
     * really an isDescendent() test. %REVIEW% rename?
     *
     * @param identity The index number of the node in question.
     * @return true if the index is a descendant of _startNode.
     */
    protected boolean isDescendant(int identity) {
      return (_parent(identity) >= _startNode) || (_startNode == identity);
    }

    /** {@inheritDoc} */
    @Override
    public int next() {
      if (_startNode == DTM.NULL) {
        return DTM.NULL;
      }

      if (_includeSelf && (_currentNode + 1) == _startNode)
        return returnNode(makeNodeHandle(++_currentNode)); // | m_dtmIdent);

      int node = _currentNode;
      int type;

      do {
        node++;
        type = _type(node);

        if (DTM.NULL == type || !isDescendant(node)) {
          _currentNode = DTM.NULL;
          return END;
        }
      } while (DTM.ATTRIBUTE_NODE == type || DTM.TEXT_NODE == type || DTM.NAMESPACE_NODE == type);

      _currentNode = node;
      return returnNode(makeNodeHandle(node)); // make handle.
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {

      final boolean temp = _isRestartable;

      _isRestartable = true;

      setStartNode(makeNodeHandle(_startNode));

      _isRestartable = temp;
    }
  } // end of DescendantIterator

  /** Class SingletonIterator. */
  public class SingletonIterator extends InternalAxisIteratorBase {

    /** (not sure yet what this is. -sb) (sc & sb remove final to compile in JDK 1.1.8) */
    private final boolean _isConstant;

    /** Constructor SingletonIterator */
    public SingletonIterator() {
      this(Integer.MIN_VALUE, false);
    }

    /**
     * Constructor SingletonIterator
     *
     * @param node the node handle to return.
     * @param constant (Not sure what this is yet. -sb)
     */
    public SingletonIterator(int node, boolean constant) {
      _currentNode = _startNode = node;
      _isConstant = constant;
    }

    /** {@inheritDoc} */
    @Override
    public void setStartNode(int node) {
      // %HZ%: Added reference to DTMDefaultBase.ROOTNODE back in, temporarily
      if (node == DTMDefaultBase.ROOTNODE) node = getDocument();
      if (_isConstant) {
        _currentNode = _startNode;

        resetPosition();
      } else if (_isRestartable) {
        _currentNode = _startNode = node;

        resetPosition();
      }
    }

    /** {@inheritDoc} */
    @Override
    public void reset() {

      if (_isConstant) {
        _currentNode = _startNode;

        resetPosition();
      } else {
        final boolean temp = _isRestartable;

        _isRestartable = true;

        setStartNode(_startNode);

        _isRestartable = temp;
      }
    }

    /** {@inheritDoc} */
    @Override
    public int next() {

      final int result = _currentNode;

      _currentNode = END;

      return returnNode(result);
    }
  }
}
