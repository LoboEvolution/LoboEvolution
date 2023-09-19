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
