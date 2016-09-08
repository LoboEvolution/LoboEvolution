/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */

package org.lobobrowser.html.renderer;

import java.util.Iterator;

/**
 * A {@link Renderable} with children.
 */
public interface RCollection extends BoundableRenderable {

	/**
	 * Gets the renderables.
	 *
	 * @return the renderables
	 */
	Iterator getRenderables();

	/**
	 * Update widget bounds.
	 *
	 * @param guiX
	 *            the gui x
	 * @param guiY
	 *            the gui y
	 */
	void updateWidgetBounds(int guiX, int guiY);

	/**
	 * Invalidates layout in all descendents.
	 */
	void invalidateLayoutDeep();

	/**
	 * Focus.
	 */
	void focus();

	/**
	 * Blur.
	 */
	void blur();
}
