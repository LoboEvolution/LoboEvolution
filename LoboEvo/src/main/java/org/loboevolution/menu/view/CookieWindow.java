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

package org.loboevolution.menu.view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.loboevolution.info.CookieInfo;

/**
 * <p>CookieWindow class.</p>
 *
 *
 *
 */
public class CookieWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new text viewer window.
	 *
	 * @param cookieList a {@link java.util.List} object.
	 */
	public CookieWindow(List<CookieInfo> cookieList) {
		createAndShowGUI(cookieList);
	}

	private void createAndShowGUI(List<CookieInfo> cookieList) {
		final Object[] columnNames = { "Name", "Value", "Expires" };

		final List<String[]> values = new ArrayList<>();
		for (final CookieInfo info : cookieList) {
			values.add(new String[] { info.getName(), info.getValue(), info.getExpires() });
		}

		final DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
		final JTable jtable = new JTable(model);
		jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
		jtable.setShowGrid(false);
		add(new JScrollPane(jtable));
	}
}
