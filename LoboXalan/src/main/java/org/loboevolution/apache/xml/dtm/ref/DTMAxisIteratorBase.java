/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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
package org.loboevolution.apache.xml.dtm.ref;

import org.loboevolution.apache.xml.utils.WrappedRuntimeException;
import org.loboevolution.apache.xml.dtm.DTMAxisIterator;

/** This class serves as a default base for implementations of mutable DTMAxisIterators. */
public abstract class DTMAxisIteratorBase implements DTMAxisIterator {

  /**
   * The position of the current node within the iteration, as defined by XPath. Note that this is
   * _not_ the node's handle within the DTM!
   */
  protected int _position = 0;

  /**
   * The handle to the start, or root, of the iteration. Set this to END to construct an empty
   * iterator.
   */
  protected int _startNode = DTMAxisIterator.END;

  /**
   * True if the start node should be considered part of the iteration. False will cause it to be
   * skipped.
   */
  protected boolean _includeSelf = false;

  /**
   * True if this iteration can be restarted. False otherwise (eg, if we are iterating over a stream
   * that can not be re-scanned, or if the iterator was produced by cloning another iterator.)
   */
  protected boolean _isRestartable = true;

  /** */
  @Override
  public void reset() {

    final boolean temp = _isRestartable;

    _isRestartable = true;

    setStartNode(_startNode);

    _isRestartable = temp;
  }

  /**
   * Set the flag to include the start node in the iteration.
   *
   * @return This default method returns just returns this DTMAxisIterator, after setting the flag.
   *     (Returning "this" permits C++-style chaining of method calls into a single expression.)
   */
  public DTMAxisIterator includeSelf() {

    _includeSelf = true;

    return this;
  }

  /** @return true if this iterator has a reversed axis, else false */
  @Override
  public boolean isReverse() {
    return false;
  }

  /**
   * Returns a deep copy of this iterator. Cloned iterators may not be restartable. The iterator
   * being cloned may or may not become non-restartable as a side effect of this operation.
   *
   * @return a deep copy of this iterator.
   */
  @Override
  public DTMAxisIterator cloneIterator() {

    try {
      final DTMAxisIteratorBase clone = (DTMAxisIteratorBase) super.clone();

      clone._isRestartable = false;

      // return clone.reset();
      return clone;
    } catch (CloneNotSupportedException e) {
      throw new WrappedRuntimeException(e);
    }
  }

  /**
   * Do any final cleanup that is required before returning the node that was passed in, and then
   * return it. The intended use is <br>
   * <code>return returnNode(node);</code> %REVIEW% If we're calling it purely for side effects,
   * should we really be bothering with a return value? Something like <br>
   * <code> accept(node); return node; </code> <br>
   * would probably optimize just about as well and avoid questions about whether what's returned
   * could ever be different from what's passed in.
   *
   * @param node Node handle which iteration is about to yield.
   * @return The node handle passed in.
   */
  protected final int returnNode(final int node) {
    _position++;

    return node;
  }

  /**
   * Reset the position to zero. NOTE that this does not change the iteration state, only the
   * position number associated with that state.
   *
   * <p>%REVIEW% Document when this would be used?
   */
  protected final void resetPosition() {

    _position = 0;
  }
}
