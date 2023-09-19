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

/**
 * A class that implements traverses DTMAxisTraverser interface can traverse a set of nodes, usually
 * as defined by an XPath axis. It is different from an iterator, because it does not need to hold
 * state, and, in fact, must not hold any iteration-based state. It is meant to be implemented as an
 * inner class of a DTM, and returned by the getAxisTraverser(final int axis) function.
 *
 * <p>A DTMAxisTraverser can probably not traverse a reverse axis in document order.
 *
 * <p>Typical usage:
 *
 * <pre>
 * <code>
 * for(int nodeHandle=myTraverser.first(myContext);
 *     nodeHandle!=DTM.NULL;
 *     nodeHandle=myTraverser.next(myContext,nodeHandle))
 * { ... processing for node indicated by nodeHandle goes here ... }
 * </code>
 * </pre>
 *
 * @author Scott Boag
 */
public abstract class DTMAxisTraverser {

  /**
   * By the nature of the stateless traversal, the context node can not be returned or the iteration
   * will go into an infinate loop. So to traverse an axis, the first function must be used to get
   * the first node.
   *
   * <p>This method needs to be overloaded only by those axis that process the self node.
   *
   * @param context The context node of this traversal. This is the point that the traversal starts
   *     from.
   * @return the first node in the traversal.
   */
  public int first(int context) {
    return next(context, context);
  }

  /**
   * By the nature of the stateless traversal, the context node can not be returned or the iteration
   * will go into an infinate loop. So to traverse an axis, the first function must be used to get
   * the first node.
   *
   * <p>This method needs to be overloaded only by those axis that process the self node.
   *
   * @param context The context node of this traversal. This is the point of origin for the
   *     traversal -- its "root node" or starting point.
   * @param extendedTypeID The extended type ID that must match.
   * @return the first node in the traversal.
   */
  public int first(int context, int extendedTypeID) {
    return next(context, context, extendedTypeID);
  }

  /**
   * Traverse to the next node after the current node.
   *
   * @param context The context node of this traversal. This is the point of origin for the
   *     traversal -- its "root node" or starting point.
   * @param current The current node of the traversal. This is the last known location in the
   *     traversal, typically the node-handle returned by the previous traversal step. For the first
   *     traversal step, context should be set equal to current. Note that in order to test whether
   *     context is in the set, you must use the first() method instead.
   * @return the next node in the iteration, or DTM.NULL.
   * @see #first(int)
   */
  public abstract int next(int context, int current);

  /**
   * Traverse to the next node after the current node that is matched by the extended type ID.
   *
   * @param context The context node of this traversal. This is the point of origin for the
   *     traversal -- its "root node" or starting point.
   * @param current The current node of the traversal. This is the last known location in the
   *     traversal, typically the node-handle returned by the previous traversal step. For the first
   *     traversal step, context should be set equal to current. Note that in order to test whether
   *     context is in the set, you must use the first() method instead.
   * @param extendedTypeID The extended type ID that must match.
   * @return the next node in the iteration, or DTM.NULL.
   * @see #first(int,int)
   */
  public abstract int next(int context, int current, int extendedTypeID);
}
