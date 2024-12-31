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
 * A DOMPoint object represents a 2D or 3D point in a coordinate system; it includes values for the coordinates in up to three dimensions, as well as an optional perspective value. DOMPoint is based on DOMPointReadOnly but allows its properties' values to be changed.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPoint">DOMPoint - MDN</a>
 * @see <a href="https://drafts.fxtf.org/geometry/#DOMPoint"># DOMPoint</a>
 */
public interface DOMPoint extends DOMPointReadOnly {

  /**
   * The DOMPoint interface's w property holds the point's perspective value, w, for a point in space.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPoint/w">DOMPoint.w - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompoint-w">w - Geometry Interfaces Module Level 1</a>
   */
  Double getW();

  /**
   * The DOMPoint interface's x property holds the horizontal coordinate, x, for a point in space.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPoint/x">DOMPoint.x - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompoint-x">x - Geometry Interfaces Module Level 1</a>
   */
  Double getX();

  /**
   * The DOMPoint interface's y property holds the vertical coordinate, y, for a point in space.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPoint/y">DOMPoint.y - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompoint-y">y - Geometry Interfaces Module Level 1</a>
   */
  Double getY();

  /**
   * The DOMPoint interface's z property specifies the depth coordinate of a point in space.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/DOMPoint/z">DOMPoint.z - MDN</a>
   * @see <a href="https://drafts.fxtf.org/geometry/#dom-dompoint-z">z - Geometry Interfaces Module Level 1</a>
   */
  Double getZ();

  DOMPoint fromPoint(DOMPointInit other);


  DOMPoint fromPoint();
}
