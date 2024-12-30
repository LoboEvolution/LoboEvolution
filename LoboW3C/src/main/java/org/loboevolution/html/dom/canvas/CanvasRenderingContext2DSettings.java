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
package org.loboevolution.html.dom.canvas;

/**
 *  The interface CanvasRenderingContext2D.
 *
 * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/getContext">CanvasRenderingContext2DSettings - MDN</a>
 */
public interface CanvasRenderingContext2DSettings {

  /**
   * A flag that indicates whether the canvas contains an alpha channel. If set to false, the browser now knows that the backdrop is always opaque, which can speed up drawing of transparent content and images.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/getContext">CanvasRenderingContext2DSettings.alpha - MDN</a>
   */
  Boolean getAlpha();

  /**
   * A flag that indicates whether the canvas contains an alpha channel. If set to false, the browser now knows that the backdrop is always opaque, which can speed up drawing of transparent content and images.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/getContext">CanvasRenderingContext2DSettings.alpha - MDN</a>
   */
  void setAlpha(Boolean alpha);
  
  String getColorSpace();

  void setColorSpace(String colorSpace);

  /**
   * A flag that provides a hint to the user agent to reduce the latency by desynchronizing the canvas paint cycle from the event loop.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/getContext">CanvasRenderingContext2DSettings.desynchronized - MDN</a>
   */
  Boolean desynchronized();

  /**
   * A flag that provides a hint to the user agent to reduce the latency by desynchronizing the canvas paint cycle from the event loop.
   *
   * @see <a href="https://developer.mozilla.org/en-US/docs/Web/API/HTMLCanvasElement/getContext">CanvasRenderingContext2DSettings.desynchronized - MDN</a>
   */
  void setDesynchronized(Boolean desynchronized);

  Boolean getWllReadFrequently();

  void setWillReadFrequently(Boolean willReadFrequently);
}
