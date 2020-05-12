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
/*
 * Created on Apr 16, 2005
 */
package org.loboevolution.html.renderer;

import java.awt.Graphics;
import java.util.Iterator;

import org.loboevolution.html.dom.HTMLHtmlElement;
import org.loboevolution.html.dom.domimpl.ModelNode;

/**
 * Represents a renderer (view) node.
 *
 * @author utente
 * @version $Id: $Id
 */
public interface Renderable {
	/** Constant EMPTY_ARRAY */
	Renderable[] EMPTY_ARRAY = new Renderable[0];

	/**
	 * <p>getModelNode.</p>
	 *
	 * @return a {@link org.loboevolution.html.dom.domimpl.ModelNode} object.
	 */
	ModelNode getModelNode();

	/**
	 * <p>paint.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	void paint(Graphics g);

	/**
	 * <p>isFixed.</p>
	 *
	 * @return a boolean.
	 */
	default public boolean isFixed() {
		return false;
	}
	
	/**
	 * <p>findHtmlRenderable.</p>
	 *
	 * @param root a {@link org.loboevolution.html.renderer.RCollection} object.
	 * @return a {@link org.loboevolution.html.renderer.Renderable} object.
	 */
	default Renderable findHtmlRenderable(RCollection root) {
		final Iterator<? extends Renderable> rs = root.getRenderables();
		if (rs != null) {
			while (rs.hasNext()) {
				final Renderable r = rs.next();
				if (r.getModelNode() instanceof HTMLHtmlElement) {
					return r;
				}
			}
		}
		return null;
	}
}
