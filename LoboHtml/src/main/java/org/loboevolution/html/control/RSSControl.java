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

package org.loboevolution.html.control;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.loboevolution.html.dom.rss.RSSElementImpl;

/**
 * <p>RSSControl class.</p>
 *
 *
 *
 */
public class RSSControl extends BaseControl {

	private static final long serialVersionUID = 1L;
	
	private final RSSElementImpl modelNode;

	/**
	 * <p>Constructor for RSSControl.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.rss.RSSElementImpl} object.
	 */
	public RSSControl(RSSElementImpl modelNode) {
		super(modelNode);
		this.modelNode = modelNode;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		modelNode.draw(g2d);
	}
}
