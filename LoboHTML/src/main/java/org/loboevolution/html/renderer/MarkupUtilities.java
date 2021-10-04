/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
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
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderer;
import java.awt.Rectangle;
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
   * @param x a int.
   * @param y a int.
   * @param vertical a boolean.
   * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
   */
  public static BoundableRenderable findRenderable(final Renderable[] renderables, final int x, final int y, final boolean vertical) {
    return findRenderable(renderables, x, y, 0, renderables.length, vertical);
  }

  private static BoundableRenderable findRenderable(final Renderable[] renderables, final int x, final int y, final int firstIndex,
      final int length, final boolean vertical) {
    for (int i = firstIndex + length - 1; i >= firstIndex; i--) {
      if (renderables[i] instanceof BoundableRenderable) {
        final BoundableRenderable br2 = (BoundableRenderable) renderables[i];
        if (br2.contains(x, y)) {
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
   * @param x a int.
   * @param y a int.
   * @param vertical a boolean.
   * @return a {@link java.util.List} object.
   */
  public static List<BoundableRenderable> findRenderables(final Renderable[] renderables, final int x, final int y, final boolean vertical) {
    List<BoundableRenderable> found = null;
    for (Renderable renderable : renderables) {
      if (renderable instanceof BoundableRenderable) {
        final BoundableRenderable br = (BoundableRenderable) renderable;
        if (br.contains(x, y)) {
          if (found == null) {
            found = new ArrayList<>();
          }
          found.add(br);
        }
      }
    }
    return found;
  }
  
  /**
   * <p>findRenderables.</p>
   *
   * @param renderables an array of {@link org.loboevolution.html.renderer.Renderable} objects.
   * @param clipArea a {@link java.awt.Rectangle} object.
   * @param vertical a boolean.
   * @return a {@link org.loboevolution.html.renderer.Range} object.
   */
  public static Range findRenderables(final Renderable[] renderables, final Rectangle clipArea, final boolean vertical) {
    return findRenderables(renderables, clipArea, 0, renderables.length, vertical);
  }

  private static Range findRenderables(final Renderable[] renderables, final Rectangle clipArea, final int firstIndex, final int length,
      final boolean vertical) {
    if (length == 0) {
      return new Range(0, 0);
    }
    int offset1 = findFirstIndex(renderables, clipArea, firstIndex, length, vertical);
    int offset2 = findLastIndex(renderables, clipArea, firstIndex, length, vertical);
    if ((offset1 == -1) && (offset2 == -1)) {
      return new Range(0, 0);
    }
    if (offset1 == -1) {
      offset1 = firstIndex;
    }
    if (offset2 == -1) {
      offset2 = (firstIndex + length) - 1;
    }
    return new Range(offset1, (offset2 - offset1) + 1);
  }
  
  private static int findFirstIndex(final Renderable[] renderables, final Rectangle clipArea, final int index, final int length,
      final boolean vertical) {
    for (int i = index; i < length; i++) {
      final Renderable ri = renderables[i];
      if (ri instanceof BoundableRenderable) {
        final BoundableRenderable br = (BoundableRenderable) ri;
        if (intersects(clipArea, br.getVisualBounds(), vertical)) {
          return i;
        }
      }
    }
    return -1;
  }

  private static int findLastIndex(final Renderable[] renderables, final Rectangle clipArea, final int index, final int length,
      final boolean vertical) {
    for (int i = index + length - 1; i >= index; i--) {
      final Renderable ri = renderables[i];
      if (ri instanceof BoundableRenderable) {
        final BoundableRenderable br = (BoundableRenderable) ri;
        if (intersects(clipArea, br.getVisualBounds(), vertical)) {
          return i;
        }
      }
    }
    return -1;
  }
  
  private static boolean intersects(final Rectangle rect1, final Rectangle rect2, final boolean vertical) {
    if (vertical) {
      return !((rect1.y > (rect2.y + rect2.height)) || (rect2.y > (rect1.y + rect1.height)));
    } else {
      return !((rect1.x > (rect2.x + rect2.width)) || (rect2.x > (rect1.x + rect1.width)));
    }
  }
}
