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

import java.awt.Graphics;

import org.loboevolution.html.dom.nodeimpl.ModelNode;

final class RFloatInfo implements Renderable {
	private final BoundableRenderable element;
	private final boolean leftFloat;
	private final ModelNode modelNode;

	/**
	 * <p>Constructor for RFloatInfo.</p>
	 *
	 * @param node a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 * @param element a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 * @param leftFloat a boolean.
	 */
	public RFloatInfo(ModelNode node, BoundableRenderable element, boolean leftFloat) {
		this.modelNode = node;
		this.element = element;
		this.leftFloat = leftFloat;
	}

	/** {@inheritDoc} */
	@Override
	public ModelNode getModelNode() {
		return this.modelNode;
	}

	/**
	 * <p>getRenderable.</p>
	 *
	 * @return a {@link org.loboevolution.html.renderer.BoundableRenderable} object.
	 */
	public BoundableRenderable getRenderable() {
		return this.element;
	}

	/**
	 * <p>isLeftFloat.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isLeftFloat() {
		return this.leftFloat;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		// nop
	}
}
