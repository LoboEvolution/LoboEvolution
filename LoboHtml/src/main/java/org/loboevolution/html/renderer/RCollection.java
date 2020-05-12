/*
    GNU LESSER GENERAL PUBLIC LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 Lobo Evolution

    This library is free software; you can redistribute it and/or
    modify it under the terms of the GNU Lesser General Public
    License as published by the Free Software Foundation; either
    version 2.1 of the License, or (at your option) any later version.

    This library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
*/

package org.loboevolution.html.renderer;

import java.awt.Rectangle;
import java.util.Iterator;

/**
 * A {@link org.loboevolution.html.renderer.Renderable} with children.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface RCollection extends BoundableRenderable {
	/**
	 * <p>blur.</p>
	 */
	void blur();

	/**
	 * <p>focus.</p>
	 */
	void focus();

	/**
	 * <p>invalidateLayoutDeep.</p>
	 */
	void invalidateLayoutDeep();

	/**
	 * <p>updateWidgetBounds.</p>
	 *
	 * @param guiX a int.
	 * @param guiY a int.
	 */
	void updateWidgetBounds(int guiX, int guiY);
	
	/**
	 * <p>getRenderables.</p>
	 *
	 * @return a {@link java.util.Iterator} object.
	 */
	Iterator<Renderable> getRenderables();
	
	/**
	 * <p>getRenderable.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 */
	BoundableRenderable getRenderable(final int x, final int y);
	
	/**
	 * <p>getClipBounds.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getClipBounds();
	
	/**
	 * <p>getClipBoundsWithoutInsets.</p>
	 *
	 * @return a {@link java.awt.Rectangle} object.
	 */
	Rectangle getClipBoundsWithoutInsets();
}
