/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
/*
 * Created on Mar 19, 2005
 */
package org.lobobrowser.util.gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * The Class WrapperLayout.
 *
 * @author J. H. S.
 */
public class WrapperLayout implements LayoutManager {
	
	/** The instance. */
	private static WrapperLayout instance = new WrapperLayout();
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#addLayoutComponent(String,
	 * java.awt.Component)
	 */
	@Override
	public void addLayoutComponent(String arg0, Component arg1) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
	 */
	@Override
	public void removeLayoutComponent(Component arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension preferredLayoutSize(Container arg0) {
		Insets insets = arg0.getInsets();
		int count = arg0.getComponentCount();
		if (count > 0) {
			Dimension d = arg0.getComponent(0).getPreferredSize();
			return new Dimension(d.width + insets.left + insets.right, d.height + insets.top + insets.bottom);
		} else {
			return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
	 */
	@Override
	public Dimension minimumLayoutSize(Container arg0) {
		Insets insets = arg0.getInsets();
		int count = arg0.getComponentCount();
		if (count > 0) {
			Dimension d = arg0.getComponent(0).getMinimumSize();
			return new Dimension(d.width + insets.left + insets.right, d.height + insets.top + insets.bottom);
		} else {
			return new Dimension(insets.left + insets.right, insets.top + insets.bottom);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
	 */
	@Override
	public void layoutContainer(Container arg0) {
		int count = arg0.getComponentCount();
		if (count > 0) {
			Component child = arg0.getComponent(0);
			Insets insets = arg0.getInsets();
			child.setBounds(insets.left, insets.top, arg0.getWidth() - insets.left - insets.right,
					arg0.getHeight() - insets.top - insets.bottom);
		}
	}

	/**
	 * Gets the instance.
	 *
	 * @return the instance
	 */
	public static WrapperLayout getInstance() {
		return instance;
	}
}
