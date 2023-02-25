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
package org.loboevolution.apache.xml.utils;

/**
 * A very simple table that stores a list of objects.
 *
 * <p>This version is based on a "realloc" strategy -- a simle array is used, and when more storage
 * is needed, a larger array is obtained and all existing data is recopied into it. As a result,
 * read/write access to existing nodes is O(1) fast but appending may be O(N**2) slow.
 */
public class ObjectVector implements Cloneable {

  /** Size of blocks to allocate */
  protected final int m_blocksize;

  /** Array of objects */
  protected Object[] m_map;

  /** Number of ints in array */
  protected int m_firstFree = 0;

  /** Size of array */
  protected int m_mapSize;

  /**
   * Construct a IntVector, using the given block size.
   *
   * @param blocksize Size of block to allocate
   * @param increaseSize the size to inc
   */
  public ObjectVector(int blocksize, int increaseSize) {

    m_blocksize = increaseSize;
    m_mapSize = blocksize;
    m_map = new Object[blocksize];
  }

  /**
   * Copy constructor for ObjectVector
   *
   * @param v Existing ObjectVector to copy
   */
  public ObjectVector(ObjectVector v) {
    m_map = new Object[v.m_mapSize];
    m_mapSize = v.m_mapSize;
    m_firstFree = v.m_firstFree;
    m_blocksize = v.m_blocksize;
    System.arraycopy(v.m_map, 0, m_map, 0, m_firstFree);
  }

  /**
   * Get the length of the list.
   *
   * @return length of the list
   */
  public final int size() {
    return m_firstFree;
  }

  /**
   * Append an object onto the vector.
   *
   * @param value Object to add to the list
   */
  public final void addElement(Object value) {

    if ((m_firstFree + 1) >= m_mapSize) {
      m_mapSize += m_blocksize;

      Object[] newMap = new Object[m_mapSize];

      System.arraycopy(m_map, 0, newMap, 0, m_firstFree + 1);

      m_map = newMap;
    }

    m_map[m_firstFree] = value;

    m_firstFree++;
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
  public final void setElementAt(Object value, int index) {
    m_map[index] = value;
  }

  /**
   * Get the nth element.
   *
   * @param i index of object to get
   * @return object at given index
   */
  public final Object elementAt(int i) {
    return m_map[i];
  }

  /*
   * Reset the array to the supplied size.
   *
   * @param size
   */
  public final void setToSize(int size) {

    Object[] newMap = new Object[size];

    System.arraycopy(m_map, 0, newMap, 0, m_firstFree);
    m_mapSize = size;

    m_map = newMap;
  }

  /** {@inheritDoc} */
  @Override
  public Object clone() throws CloneNotSupportedException {
    return new ObjectVector(this);
  }
}
