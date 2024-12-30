/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.loboevolution.html.dom;


/**
 * The DOMPointReadOnly interface specifies the coordinate and perspective fields used by DOMPoint to define a 2D or 3D point in a coordinate system.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly">DOMPointReadOnly - MDN</a>
 * @see <a href="https://drafts.fxtf.org/geometry/#DOMPoint"># DOMPoint</a>
 */
public interface DOMPointReadOnly {
  

  /**
   * The DOMPointReadOnly interface's w property holds the point's perspective value, w, for a read-only point in space.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly/w">DOMPointReadOnly.w - MDN</a>
   */
  Double getW();

  /**
   * The DOMPointReadOnly interface's x property holds the horizontal coordinate, x, for a read-only point in space. This property cannot be changed by JavaScript code in this read-only version of the DOMPoint object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly/x">DOMPointReadOnly.x - MDN</a>
   */
  Double getX();

  /**
   * The DOMPointReadOnly interface's y property holds the vertical coordinate, y, for a read-only point in space.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly/y">DOMPointReadOnly.y - MDN</a>
   */
  Double getY();

  /**
   * The DOMPointReadOnly interface's z property holds the depth coordinate, z, for a read-only point in space.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly/z">DOMPointReadOnly.z - MDN</a>
   */
  Double getZ();

  /**
   * The static DOMPointReadOnly method fromPoint() creates and returns a new DOMPointReadOnly object given a source point.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly/fromPoint">DOMPointReadOnly.fromPoint - MDN</a>
   */
  DOMPointReadOnly fromPoint(DOMPointInit other);

  /**
   * The static DOMPointReadOnly method fromPoint() creates and returns a new DOMPointReadOnly object given a source point.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly/fromPoint">DOMPointReadOnly.fromPoint - MDN</a>
   */
  DOMPointReadOnly fromPoint();

  DOMPoint matrixTransform(DOMMatrixInit matrix);

  DOMPoint matrixTransform();

  /**
   * The DOMPointReadOnly method toJSON() returns a DOMPointInit object giving the JSON form of the point object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointReadOnly/toJSON">DOMPointReadOnly.toJSON - MDN</a>
   */
  Object toJSON();
}
