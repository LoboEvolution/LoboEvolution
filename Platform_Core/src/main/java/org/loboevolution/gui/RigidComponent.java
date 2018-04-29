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

/**
 * A component used in conjunction with <code>BoxLayout</code>.
 */
public class RigidComponent extends Filler {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new rigid component.
	 *
	 * @param wrappedComponent
	 *            the wrapped component
	 * @param d
	 *            the d
	 */
	public RigidComponent(Component wrappedComponent, Dimension d) {
		super(d, d, d);
		createAndShowGUI(wrappedComponent, d);
	}

	private void createAndShowGUI(Component wrappedComponent, Dimension d) {
		this.setLayout(org.loboevolution.util.gui.WrapperLayout.getInstance());
		this.add(wrappedComponent);
	}
}
