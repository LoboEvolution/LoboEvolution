/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

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
package org.loboevolution.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box.Filler;

import org.loboevolution.util.gui.WrapperLayout;

/**
 * A component used in conjunction with <code>BoxLayot</code>.
 *
 */
public class FillerComponent extends Filler {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new filler component.
	 *
	 * @param wrappedComponent
	 *            the wrapped component
	 * @param forMax
	 *            the for max
	 */
	public FillerComponent(Component wrappedComponent, boolean forMax) {
		super(new Dimension(0, 0), forMax ? new Dimension(0, 0) : new Dimension(Short.MAX_VALUE, Short.MAX_VALUE),
				new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
		createAndShowGUI(wrappedComponent);
	}

	private void createAndShowGUI(Component wrappedComponent) {
		this.setLayout(WrapperLayout.getInstance());
		this.add(wrappedComponent);
	}

	/**
	 * Instantiates a new filler component.
	 *
	 * @param wrappedComponent
	 *            the wrapped component
	 * @param minSize
	 *            the min size
	 * @param prefSize
	 *            the pref size
	 * @param maxSize
	 *            the max size
	 */
	public FillerComponent(Component wrappedComponent, Dimension minSize, Dimension prefSize, Dimension maxSize) {
		super(minSize, prefSize, maxSize);
		this.setLayout(WrapperLayout.getInstance());
		this.add(wrappedComponent);
	}

}
