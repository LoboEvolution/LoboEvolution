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
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderer;

import java.util.ArrayList;
import java.util.List;

final class MarkupUtilities {

  private MarkupUtilities() {
    super();
  }

  /**
   * <p>findRenderable.</p>
   *
   * @param renderables an array of {@link org.loboevolution.html.renderer.Renderable} objects.
   * @param x a {@link java.lang.Integer} object.
   * @param y a {@link java.lang.Integer} object.
   * @param vertical a boolean.
   * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
   */
  public static BoundableRenderable findRenderable(final Renderable[] renderables, final int x, final int y, final boolean vertical) {
    return findRenderable(renderables, x, y, 0, renderables.length, vertical);
  }

  private static BoundableRenderable findRenderable(final Renderable[] renderables, final int x, final int y, final int firstIndex,
      final int length, final boolean vertical) {
    for (int i = firstIndex + length - 1; i >= firstIndex; i--) {
      if (renderables[i] instanceof BoundableRenderable br2) {
          if ((!br2.isDelegated()) && br2.contains(x, y)) {
          return br2;
        }
      }
    }
    return null;
  }

  // Linear scan version
  /**
   * <p>findRenderables.</p>
   *
   * @param renderables an array of {@link org.loboevolution.html.renderer.Renderable} objects.
   * @param x a {@link java.lang.Integer} object.
   * @param y a {@link java.lang.Integer} object.
   * @param vertical a boolean.
   * @return a {@link java.util.List} object.
   */
  public static List<BoundableRenderable> findRenderables(final Renderable[] renderables, final int x, final int y, final boolean vertical) {
    List<BoundableRenderable> found = null;
    for (final Renderable renderable : renderables) {
      if (renderable instanceof BoundableRenderable br) {
          if ((!br.isDelegated()) && br.contains(x, y)) {
          if (found == null) {
            found = new ArrayList<>();
          }
          found.add(br);
        }
      }
    }
    return found;
  }
}
