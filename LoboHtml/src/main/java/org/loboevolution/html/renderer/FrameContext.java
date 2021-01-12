/*
 *
 *     GNU GENERAL LICENSE
 *     Copyright (C) 2014 - 2021 Lobo Evolution
 *
 *     This program is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation; either
 *     verion 3 of the License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     General License for more details.
 *
 *     You should have received a copy of the GNU General Public
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 *     Contact info: ivan.difrancesco@yahoo.it
 *
 */
package org.loboevolution.html.renderer;

import org.loboevolution.html.dom.domimpl.NodeImpl;

/**
 * Provides direct access to the GUI component where the document is rendered,
 * typically a browser frame of some sort.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface FrameContext {
	/**
	 * <p>delayedRelayout.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.domimpl.NodeImpl} object.
	 */
	void delayedRelayout(NodeImpl node);

	/**
	 * <p>expandSelection.</p>
	 *
	 * @param rpoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	void expandSelection(RenderableSpot rpoint);

	/**
	 * <p>resetSelection.</p>
	 *
	 * @param rpoint a {@link org.loboevolution.html.renderer.RenderableSpot} object.
	 */
	void resetSelection(RenderableSpot rpoint);
}
