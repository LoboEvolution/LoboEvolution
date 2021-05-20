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
 * Created on Apr 17, 2005
 */
package org.loboevolution.html.renderer;

import java.awt.Graphics;

import org.loboevolution.html.dom.nodeimpl.ModelNode;
import org.loboevolution.html.renderstate.RenderState;

/**
 * Author J. H. S.
 */
final class RStyleChanger extends BaseRenderable {
	
	private final ModelNode modelNode;

	/**
	 * <p>Constructor for RStyleChanger.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.nodeimpl.ModelNode} object.
	 */
	public RStyleChanger(ModelNode modelNode) {
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public ModelNode getModelNode() {
		return this.modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		final RenderState rs = this.modelNode.getRenderState();
		g.setColor(rs.getColor());
		g.setFont(rs.getFont());
	}
}
