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
package org.loboevolution.apache.xml.utils;

import org.loboevolution.apache.xml.dtm.DTM;

/** A very simple table that stores a list of Nodes. */
public class NodeVector implements Cloneable {

  /**
   * Size of blocks to allocate.
   *
   * @serial
   */
  private final int m_blocksize;

  /**
   * Array of nodes this points to.
   *
   * @serial
   */
  private int[] m_map;

  /**
   * Number of nodes in this NodeVector.
   *
   * @serial
   */
  protected int m_firstFree = 0;

  /**
   * Size of the array this points to.
   *
   * @serial
   */
  private int m_mapSize; // lazy initialization

  /** Default constructor. */
  public NodeVector() {
    m_blocksize = 32;
    m_mapSize = 0;
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {

    NodeVector clone = (NodeVector) super.clone();

    if ((null != this.m_map) && (this.m_map == clone.m_map)) {
      clone.m_map = new int[this.m_map.length];

      System.arraycopy(this.m_map, 0, clone.m_map, 0, this.m_map.length);
    }

    return clone;
  }

  /**
   * Get the length of the list.
   *
   * @return Number of nodes in this NodeVector
   */
  public int size() {
    return m_firstFree;
  }

  /**
   * Append a Node onto the vector.
   *
   * @param value Node to add to the vector
   */
  public void addElement(int value) {

    if ((m_firstFree + 1) >= m_mapSize) {
      if (null == m_map) {
        m_map = new int[m_blocksize];
        m_mapSize = m_blocksize;
      } else {
        m_mapSize += m_blocksize;

        int[] newMap = new int[m_mapSize];

        System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

        m_map = newMap;
      }
    }

    m_map[m_firstFree] = value;

    m_firstFree++;
  }

  /**
   * Return the node at the top of the stack without popping the stack. Special purpose method for
   * TransformerImpl, pushElemTemplateElement. Performance critical.
   *
   * @return Node at the top of the stack or null if stack is empty.
   */
  public final int peepOrNull() {
    return ((null != m_map) && (m_firstFree > 0)) ? m_map[m_firstFree - 1] : DTM.NULL;
  }

  /**
   * Inserts the specified node in this vector at the specified index. Each component in this vector
   * with an index greater or equal to the specified index is shifted upward to have an index one
   * greater than the value it had previously.
   *
   * @param value Node to insert
   * @param at Position where to insert
   */
  public void insertElementAt(int value, int at) {

    if (null == m_map) {
      m_map = new int[m_blocksize];
      m_mapSize = m_blocksize;
    } else if ((m_firstFree + 1) >= m_mapSize) {
      m_mapSize += m_blocksize;

      int[] newMap = new int[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    if (at <= (m_firstFree - 1)) {
      System.arraycopy(m_map, at, m_map, at + 1, m_firstFree - at);
    }

    m_map[at] = value;

    m_firstFree++;
  }

  /** Set the length to zero, but don't clear the array. */
  public void RemoveAllNoClear() {

    if (null == m_map) return;

    m_firstFree = 0;
  }

  /**
   * Get the nth element.
   *
   * @param i Index of node to get
   * @return Node at specified index
   */
  public int elementAt(int i) {

    if (null == m_map) return DTM.NULL;

    return m_map[i];
  }

  /**
   * Tell if the table contains the given node.
   *
   * @param s Node to look for
   * @return True if the given node was found.
   */
  public boolean contains(int s) {

    if (null == m_map) return false;

    for (int i = 0; i < m_firstFree; i++) {
      int node = m_map[i];

      if (node == s) return true;
    }

    return false;
  }

  /**
   * Searches for the first occurence of the given argument, beginning the search at index, and
   * testing for equality using the equals method.
   *
   * @param elem Node to look for
   * @param index Index of where to start the search
   * @return the index of the first occurrence of the object argument in this vector at position
   *     index or later in the vector; returns -1 if the object is not found.
   */
  public int indexOf(int elem, int index) {

    if (null == m_map) return -1;

    for (int i = index; i < m_firstFree; i++) {
      int node = m_map[i];

      if (node == elem) return i;
    }

    return -1;
  }

  /**
   * Searches for the first occurence of the given argument, beginning the search at index, and
   * testing for equality using the equals method.
   *
   * @param elem Node to look for
   * @return the index of the first occurrence of the object argument in this vector at position
   *     index or later in the vector; returns -1 if the object is not found.
   */
  public int indexOf(int elem) {

    if (null == m_map) return -1;

    for (int i = 0; i < m_firstFree; i++) {
      int node = m_map[i];

      if (node == elem) return i;
    }

    return -1;
  }
}
