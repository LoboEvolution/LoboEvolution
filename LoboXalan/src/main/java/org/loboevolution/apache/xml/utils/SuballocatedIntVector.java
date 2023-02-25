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
 * A very simple table that stores a list of int. Very similar API to our IntVector class (same
 * API); different internal storage.
 *
 * <p>This version uses an array-of-arrays solution. Read/write access is thus a bit slower than the
 * simple IntVector, and basic storage is a trifle higher due to the top-level array -- but
 * appending is O(1) fast rather than O(N**2) slow, which will swamp those costs in situations where
 * long vectors are being built up.
 *
 * <p>Known issues:
 *
 * <p>Some methods are private because they haven't yet been tested properly.
 *
 * <p>Retrieval performance is critical, since this is used at the core of the DTM model. (Append
 * performance is almost as important.) That's pushing me toward just letting reads from unset
 * indices throw exceptions or return stale data; safer behavior would have performance costs.
 */
public class SuballocatedIntVector {
  /** Size of blocks to allocate */
  protected final int m_blocksize;

  /** Bitwise addressing (much faster than div/remainder */
  protected int m_SHIFT;

  protected final int m_MASK;

  /** The default number of blocks to (over)allocate by */
  protected static final int NUMBLOCKS_DEFAULT = 32;

  /** The number of blocks to (over)allocate by */
  protected final int m_numblocks;

  /** Array of arrays of ints */
  protected int[][] m_map;

  /** Number of ints in array */
  protected int m_firstFree = 0;

  /** "Shortcut" handle to m_map[0]. Surprisingly helpful for short vectors. */
  protected final int[] m_map0;

  /** "Shortcut" handle to most recently added row of m_map. Very helpful during construction. */
  protected int[] m_buildCache;

  protected int m_buildCacheStartIndex;

  /**
   * Construct a IntVector, using the given block size and number of blocks. For efficiency, we will
   * round the requested size off to a power of two.
   *
   * @param blocksize Size of block to allocate
   * @param numblocks Number of blocks to allocate
   */
  public SuballocatedIntVector(int blocksize, int numblocks) {
    // m_blocksize = blocksize;
    for (m_SHIFT = 0; 0 != (blocksize >>>= 1); ++m_SHIFT) ;
    m_blocksize = 1 << m_SHIFT;
    m_MASK = m_blocksize - 1;
    m_numblocks = numblocks;

    m_map0 = new int[m_blocksize];
    m_map = new int[numblocks][];
    m_map[0] = m_map0;
    m_buildCache = m_map0;
    m_buildCacheStartIndex = 0;
  }

  /**
   * Construct a IntVector, using the given block size and the default number of blocks (32).
   *
   * @param blocksize Size of block to allocate
   */
  public SuballocatedIntVector(int blocksize) {
    this(blocksize, NUMBLOCKS_DEFAULT);
  }

  /**
   * Get the length of the list.
   *
   * @return length of the list
   */
  public int size() {
    return m_firstFree;
  }

  /**
   * Append a int onto the vector.
   *
   * @param value Int to add to the list
   */
  public void addElement(int value) {
    int indexRelativeToCache = m_firstFree - m_buildCacheStartIndex;

    // Is the new index an index into the cache row of m_map?
    if (indexRelativeToCache >= 0 && indexRelativeToCache < m_blocksize) {
      m_buildCache[indexRelativeToCache] = value;
      ++m_firstFree;
    } else {
      // Growing the outer array should be rare. We initialize to a
      // total of m_blocksize squared elements, which at the default
      // size is 4M integers... and we grow by at least that much each
      // time. However, attempts to microoptimize for this (assume
      // long enough and catch exceptions) yield no noticable
      // improvement.

      int index = m_firstFree >>> m_SHIFT;
      int offset = m_firstFree & m_MASK;

      if (index >= m_map.length) {
        int newsize = index + m_numblocks;
        int[][] newMap = new int[newsize][];
        System.arraycopy(m_map, 0, newMap, 0, m_map.length);
        m_map = newMap;
      }
      int[] block = m_map[index];
      if (null == block) block = m_map[index] = new int[m_blocksize];
      block[offset] = value;

      // Cache the current row of m_map. Next m_blocksize-1
      // values added will go to this row.
      m_buildCache = block;
      m_buildCacheStartIndex = m_firstFree - offset;

      ++m_firstFree;
    }
  }

  /**
   * Sets the component at the specified index of this vector to be the specified object. The
   * previous component at that position is discarded.
   *
   * <p>The index must be a value greater than or equal to 0 and less than the current size of the
   * vector.
   *
   * @param value object to set
   * @param at Index of where to set the object
   */
  public void setElementAt(int value, int at) {
    if (at < m_blocksize) m_map0[at] = value;
    else {
      int index = at >>> m_SHIFT;
      int offset = at & m_MASK;

      if (index >= m_map.length) {
        int newsize = index + m_numblocks;
        int[][] newMap = new int[newsize][];
        System.arraycopy(m_map, 0, newMap, 0, m_map.length);
        m_map = newMap;
      }

      int[] block = m_map[index];
      if (null == block) block = m_map[index] = new int[m_blocksize];
      block[offset] = value;
    }

    if (at >= m_firstFree) m_firstFree = at + 1;
  }

  /**
   * Get the nth element. This is often at the innermost loop of an application, so performance is
   * critical.
   *
   * @param i index of value to get
   * @return value at given index. If that value wasn't previously set, the result is undefined for
   *     performance reasons. It may throw an exception (see below), may return zero, or (if setSize
   *     has previously been used) may return stale data.
   * @throws ArrayIndexOutOfBoundsException if the index was _clearly_ unreasonable (negative, or
   *     past the highest block).
   * @throws NullPointerException if the index points to a block that could have existed (based on
   *     the highest index used) but has never had anything set into it. %REVIEW% Could add a catch
   *     to create the block in that case, or return 0. Try/Catch is _supposed_ to be nearly free
   *     when not thrown to. Do we believe that? Should we have a separate safeElementAt?
   */
  public int elementAt(int i) {
    // This is actually a significant optimization!
    if (i < m_blocksize) return m_map0[i];

    return m_map[i >>> m_SHIFT][i & m_MASK];
  }

  /**
   * Searches for the first occurence of the given argument, beginning the search at index, and
   * testing for equality using the equals method.
   *
   * @param elem object to look for
   * @param index Index of where to begin search
   * @return the index of the first occurrence of the object argument in this vector at position
   *     index or later in the vector; returns -1 if the object is not found.
   */
  public int indexOf(int elem, int index) {
    if (index >= m_firstFree) return -1;

    int bindex = index >>> m_SHIFT;
    int boffset = index & m_MASK;
    int maxindex = m_firstFree >>> m_SHIFT;
    int[] block;

    for (; bindex < maxindex; ++bindex) {
      block = m_map[bindex];
      if (block != null)
        for (int offset = boffset; offset < m_blocksize; ++offset)
          if (block[offset] == elem) return offset + bindex * m_blocksize;
      boffset = 0; // after first
    }
    // Last block may need to stop before end
    int maxoffset = m_firstFree & m_MASK;
    block = m_map[maxindex];
    for (int offset = boffset; offset < maxoffset; ++offset)
      if (block[offset] == elem) return offset + maxindex * m_blocksize;

    return -1;
  }

  /**
   * Searches for the first occurence of the given argument, beginning the search at index, and
   * testing for equality using the equals method.
   *
   * @param elem object to look for
   * @return the index of the first occurrence of the object argument in this vector at position
   *     index or later in the vector; returns -1 if the object is not found.
   */
  public int indexOf(int elem) {
    return indexOf(elem, 0);
  }
}
