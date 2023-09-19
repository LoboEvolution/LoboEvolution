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

package org.loboevolution.html.dom.smil;

/**
 * The TimeList interface provides the abstraction of an ordered
 * collection of times, without defining or constraining how this collection is
 * implemented.
 * The items in the TimeList are accessible via an integral index,
 * starting from 0.
 */
public interface TimeList {
    /**
     * Returns the index th item in the collection. If
     * index is greater than or equal to the number of times in the
     * list, this returns null .
     *
     * @param index
     *            Index into the collection.
     * @return The time at the index th position in the
     *         TimeList , or null if that is not a
     *         valid index.
     */
    Time item(int index);

    /**
     * The number of times in the list. The range of valid child time indices is
     * 0 to length-1 inclusive.
     *
     * @return a int.
     */
    int getLength();

}
