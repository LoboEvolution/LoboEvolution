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

package org.loboevolution.component;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * <p>IWelcomePanel interface.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public interface IWelcomePanel {

	/**
	 * <p>setSize.</p>
	 *
	 * @param dim a {@link java.awt.Dimension} object.
	 */
	void setSize(Dimension dim);

	/**
	 * <p>setPreferredSize.</p>
	 *
	 * @param dim a {@link java.awt.Dimension} object.
	 */
	void setPreferredSize(Dimension dim);

	/**
	 * <p>repaint.</p>
	 */
	void repaint();
	
	/**
	 * <p>setBackground.</p>
	 *
	 * @param color a {@link java.awt.Color} object.
	 */
	void setBackground(Color color);
	
	/**
	 * <p>getWelocome.</p>
	 *
	 * @return a {@link javax.swing.JPanel} object.
	 */
	JPanel getWelocome();
}
