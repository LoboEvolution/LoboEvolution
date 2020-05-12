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
 * Created on Mar 19, 2005
 */
package org.loboevolution.common;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * <p>WrapperLayout class.</p>
 *
 * @author J. H. S.
 * @version $Id: $Id
 */
public class WrapperLayout implements LayoutManager {
	private static WrapperLayout instance = new WrapperLayout();

	/**
	 * <p>Getter for the field instance.</p>
	 *
	 * @return a {@link org.loboevolution.common.WrapperLayout} object.
	 */
	public static WrapperLayout getInstance() {
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String,
	 * java.awt.Component)
	 */
	/** {@inheritDoc} */
	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	/** {@inheritDoc} */
	@Override
	public void layoutContainer(Container arg0) {
		final int count = arg0.getComponentCount();
		if (count > 0) {
			final Component child = arg0.getComponent(0);
			final java.awt.Insets insets = arg0.getInsets();
			child.setBounds(insets.left, insets.top, arg0.getWidth() - insets.left - insets.right,
					arg0.getHeight() - insets.top - insets.bottom);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	/** {@inheritDoc} */
	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		final java.awt.Insets insets = arg0.getInsets();
		final int count = arg0.getComponentCount();
		if (count > 0) {
			final Dimension d = arg0.getComponent(0).getMinimumSize();
			return new Dimension(d.width + insets.left + insets.right, d.height + insets.top + insets.bottom);
		} else {
			return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	/** {@inheritDoc} */
	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		final java.awt.Insets insets = arg0.getInsets();
		final int count = arg0.getComponentCount();
		if (count > 0) {
			final Dimension d = arg0.getComponent(0).getPreferredSize();
			return new Dimension(d.width + insets.left + insets.right, d.height + insets.top + insets.bottom);
		} else {
			return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	/** {@inheritDoc} */
	@Override
	public void removeLayoutComponent(Component arg0) {
	}
}
