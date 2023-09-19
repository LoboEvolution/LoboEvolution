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

import java.util.ArrayList;
import org.loboevolution.apache.xml.dtm.DTMIterator;
import org.loboevolution.apache.xml.utils.WrappedRuntimeException;

/** Pool of object of a given type to pick from to help memory usage */
public final class IteratorPool {

  /** Type of objects in this pool. */
  private final DTMIterator m_orig;

  /** Stack of given objects this points to. */
  private final ArrayList<DTMIterator> m_freeStack;

  /**
   * Constructor IteratorPool
   *
   * @param original The original iterator from which all others will be cloned.
   */
  public IteratorPool(DTMIterator original) {
    m_orig = original;
    m_freeStack = new ArrayList<>();
  }

  /**
   * Get an instance of the given object in this pool
   *
   * @return An instance of the given object
   */
  public synchronized DTMIterator getInstanceOrThrow() throws CloneNotSupportedException {
    // Check if the pool is empty.
    if (m_freeStack.isEmpty()) {

      // Create a new object if so.
      return (DTMIterator) m_orig.clone();
    }
    // Remove object from end of free pool.
    return m_freeStack.remove(m_freeStack.size() - 1);
  }

  /**
   * Get an instance of the given object in this pool
   *
   * @return An instance of the given object
   */
  public synchronized DTMIterator getInstance() {
    // Check if the pool is empty.
    if (m_freeStack.isEmpty()) {

      // Create a new object if so.
      try {
        return (DTMIterator) m_orig.clone();
      } catch (Exception ex) {
        throw new WrappedRuntimeException(ex);
      }
    }
    // Remove object from end of free pool.
    return m_freeStack.remove(m_freeStack.size() - 1);
  }

  /**
   * Add an instance of the given object to the pool
   *
   * @param obj Object to add.
   */
  public synchronized void freeInstance(DTMIterator obj) {
    m_freeStack.add(obj);
  }
}
