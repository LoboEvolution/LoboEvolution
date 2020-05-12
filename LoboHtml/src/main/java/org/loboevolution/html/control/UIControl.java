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
 * Created on Nov 5, 2005
 */
package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import org.loboevolution.html.AlignValues;

/**
 * <p>Abstract UIControl interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public abstract interface UIControl {
	/**
	 * <p>getBackgroundColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	Color getBackgroundColor();

	/**
	 * <p>getComponent.</p>
	 *
	 * @return a {@link java.awt.Component} object.
	 */
	Component getComponent();

	/**
	 * <p>getPreferredSize.</p>
	 *
	 * @return a {@link java.awt.Dimension} object.
	 */
	Dimension getPreferredSize();

	/**
	 * <p>getVAlign.</p>
	 *
	 * @return a int.
	 */
	default int getVAlign() {
		return AlignValues.BASELINE.getValue();
	}

	/**
	 * <p>invalidate.</p>
	 */
	void invalidate();

	/**
	 * <p>paint.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	void paint(Graphics g);

	/**
	 * <p>reset.</p>
	 *
	 * @param availWidth a int.
	 * @param availHeight a int.
	 */
	void reset(int availWidth, int availHeight);

	/**
	 * <p>setBounds.</p>
	 *
	 * @param x a int.
	 * @param y a int.
	 * @param width a int.
	 * @param height a int.
	 */
	void setBounds(int x, int y, int width, int height);

	/**
	 * <p>setRUIControl.</p>
	 *
	 * @param ruicontrol a {@link org.loboevolution.html.control.RUIControl} object.
	 */
	void setRUIControl(RUIControl ruicontrol);
}
