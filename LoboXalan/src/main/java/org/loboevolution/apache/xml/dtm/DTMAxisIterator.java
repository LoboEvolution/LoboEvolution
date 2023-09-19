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

/** This class iterates over a single XPath Axis, and returns node handles. */
public interface DTMAxisIterator extends Cloneable {

  /** Specifies the end of the iteration, and is the same as DTM.NULL. */
  int END = DTM.NULL;

  /**
   * Get the next node in the iteration.
   *
   * @return The next node handle in the iteration, or END.
   */
  int next();

  /** Resets the iterator to the last start node. */
  void reset();

  /**
   * Set start to END should 'close' the iterator, i.e. subsequent call to next() should return END.
   *
   * @param node Sets the root of the iteration.
   */
  void setStartNode(int node);

  /** @return true if this iterator has a reversed axis, else false. */
  boolean isReverse();

  /**
   * @return a deep copy of this iterator. The clone should not be reset from its current position.
   */
  DTMAxisIterator cloneIterator();
}
