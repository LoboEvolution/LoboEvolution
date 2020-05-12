/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2020 Lobo Evolution

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
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.img;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/* This layout manager ensures that the ImageComponent and all the overlays fill
* the container exactly.
*/
/**
 * <p>OverlayLayout class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class OverlayLayout implements LayoutManager {
	
	private transient LayeredImageView layeredImageView;
	
	/**
	 * <p>Constructor for OverlayLayout.</p>
	 *
	 * @param layeredImageView a {@link org.loboevolution.img.LayeredImageView} object.
	 */
	public OverlayLayout(LayeredImageView layeredImageView) {
		this.layeredImageView = layeredImageView;
	}

	/** {@inheritDoc} */
	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	/** {@inheritDoc} */
	@Override
	public void removeLayoutComponent(Component comp) {
	}

	/** {@inheritDoc} */
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return layeredImageView.getTheImage().getPreferredSize();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return layeredImageView.getTheImage().getMinimumSize();
	}

	/** {@inheritDoc} */
	@Override
	public void layoutContainer(Container parent) {
		for (int i = 0; i < parent.getComponentCount(); i++) {
			parent.getComponent(i).setBounds(0, 0, parent.getWidth(), parent.getHeight());
		}
	}

}
