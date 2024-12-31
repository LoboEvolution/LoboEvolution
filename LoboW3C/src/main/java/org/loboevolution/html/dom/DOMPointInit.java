/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
 * The static DOMPoint method fromPoint() creates and returns a new mutable DOMPoint object given a source point.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit">DOMPointInit - MDN</a>
 * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompoint-frompoint"># dom-dompoint-frompoint</a>
 */
public interface DOMPointInit {

  /**
   * The DOMPointInit dictionary's w property is used to specify the w perspective value of a point in space when either creating or serializing to JSON a DOMPoint or DOMPointReadOnly object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/w">DOMPointInit.w - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-w">w - Geometry Interfaces Module Level 1</a>
   */
  double getW();

  /**
   * The DOMPointInit dictionary's w property is used to specify the w perspective value of a point in space when either creating or serializing to JSON a DOMPoint or DOMPointReadOnly object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/w">DOMPointInit.w - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-w">w - Geometry Interfaces Module Level 1</a>
   */
  void setW(double w);

  /**
   * The DOMPointInit dictionary's x property is used to specify the x component of a point in 2D or 3D space when either creating or serializing a DOMPoint or DOMPointReadOnly.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/x">DOMPointInit.x - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-x">x - Geometry Interfaces Module Level 1</a>
   */
  double getX();

  /**
   * The DOMPointInit dictionary's x property is used to specify the x component of a point in 2D or 3D space when either creating or serializing a DOMPoint or DOMPointReadOnly.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/x">DOMPointInit.x - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-x">x - Geometry Interfaces Module Level 1</a>
   */
  void setX(double x);

  /**
   * The DOMPointInit dictionary's y property is used to specify the y-coordinate of a point in 2D or 3D space when either creating or serializing to JSON a DOMPoint or DOMPointReadOnly object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/y">DOMPointInit.y - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-y">y - Geometry Interfaces Module Level 1</a>
   */
  double getY();

  /**
   * The DOMPointInit dictionary's y property is used to specify the y-coordinate of a point in 2D or 3D space when either creating or serializing to JSON a DOMPoint or DOMPointReadOnly object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/y">DOMPointInit.y - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-y">y - Geometry Interfaces Module Level 1</a>
   */
  void setY(double y);

  /**
   * The DOMPointInit dictionary's z property is used to specify the z-coordinate of a point in 2D or 3D space when either creating or serializing to JSON a DOMPoint or DOMPointReadOnly object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/z">DOMPointInit.z - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-z">z - Geometry Interfaces Module Level 1</a>
   */
  double getZ();

  /**
   * The DOMPointInit dictionary's z property is used to specify the z-coordinate of a point in 2D or 3D space when either creating or serializing to JSON a DOMPoint or DOMPointReadOnly object.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPointInit/z">DOMPointInit.z - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompointinit-z">z - Geometry Interfaces Module Level 1</a>
   */
  void setZ(double z);
}
