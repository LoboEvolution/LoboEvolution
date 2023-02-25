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
