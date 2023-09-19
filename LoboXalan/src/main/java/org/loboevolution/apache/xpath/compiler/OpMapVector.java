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
package org.loboevolution.apache.xpath.compiler;

/**
 * Like IntVector, but used only for the OpMap array. Length of array is kept in the m_lengthPos
 * position of the array. Only the required methods are in included here.
 */
public class OpMapVector {

  /** Size of blocks to allocate */
  protected final int m_blocksize;

  /** Array of ints */
  protected int[] m_map; // IntStack is trying to see this directly

  /** Position where size of array is kept */
  protected int m_lengthPos = 0;

  /** Size of array */
  protected int m_mapSize;

  /**
   * Construct a OpMapVector, using the given block size.
   *
   * @param blocksize Size of block to allocate
   */
  public OpMapVector(int blocksize, int increaseSize, int lengthPos) {

    m_blocksize = increaseSize;
    m_mapSize = blocksize;
    m_lengthPos = lengthPos;
    m_map = new int[blocksize];
  }

  /**
   * Get the nth element.
   *
   * @param i index of object to get
   * @return object at given index
   */
  public final int elementAt(int i) {
    return m_map[i];
  }

  /**
   * Sets the component at the specified index of this vector to be the specified object. The
   * previous component at that position is discarded.
   *
   * <p>The index must be a value greater than or equal to 0 and less than the current size of the
   * vector.
   *
   * @param value object to set
   * @param index Index of where to set the object
   */
  public final void setElementAt(int value, int index) {
    if (index >= m_mapSize) {
      int oldSize = m_mapSize;

      m_mapSize += m_blocksize;

      int[] newMap = new int[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, oldSize);

      m_map = newMap;
    }

    m_map[index] = value;
  }

  /*
   * Reset the array to the supplied size. No checking is done.
   *
   * @param size The size to trim to.
   */
  public final void setToSize(int size) {

    int[] newMap = new int[size];

    System.arraycopy(m_map, 0, newMap, 0, m_map[m_lengthPos]);

    m_mapSize = size;
    m_map = newMap;
  }
}
