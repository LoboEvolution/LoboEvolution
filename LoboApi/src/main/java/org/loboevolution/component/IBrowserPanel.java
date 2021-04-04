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

package org.loboevolution.component;

import javax.swing.JScrollPane;

import org.loboevolution.tab.DnDTabbedPane;

/**
 * <p>IBrowserPanel interface.</p>
 *
 *
 *
 */
public interface IBrowserPanel {
	
	/**
	 * <p>getTabbedPane.</p>
	 *
	 * @return a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	DnDTabbedPane getTabbedPane();
	
	/**
	 * <p>getScroll.</p>
	 *
	 * @return a {@link javax.swing.JScrollPane} object.
	 */
	JScrollPane getScroll();
	
	/**
	 * <p>getBrowserFrame.</p>
	 *
	 * @return a {@link org.loboevolution.component.IBrowserFrame} object.
	 */
	IBrowserFrame getBrowserFrame();
	
	/**
	 * <p>getWelcome.</p>
	 *
	 * @return a {@link org.loboevolution.component.IWelcomePanel} object.
	 */
	IWelcomePanel getWelcome();
	
	/**
	 * <p>getWidth.</p>
	 *
	 * @return a int object.
	 */
	int getWidth();
	
	/**
	 * <p>getWelcome.</p>
	 *
	 * @return a int object.
	 */
	int getHeight();

}
