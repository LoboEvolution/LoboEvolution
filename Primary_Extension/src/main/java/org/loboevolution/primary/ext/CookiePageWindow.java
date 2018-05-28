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
package org.loboevolution.primary.ext;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.loboevolution.http.Cookie;

/**
 * The Class InfoPageWindow.
 */
public class CookiePageWindow extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new text viewer window.
	 */
	public CookiePageWindow(List<Cookie> cookieList, String address) {
		super("Cookies of: " + address);
		createAndShowGUI(cookieList);
	}

	private void createAndShowGUI(List<Cookie> cookieList)  {
		Object columnNames[] = { "Name", "Value", "Expires" };

		List<String[]> values = new ArrayList<String[]>();
		for (Cookie info : cookieList) {
			values.add(new String[] { info.getName(), info.getValue(), info.getExpires()  });
		}
		
		DefaultTableModel model = new DefaultTableModel(values.toArray(new Object[][] {}), columnNames);
		JTable jtable = new JTable(model);
		jtable.setPreferredScrollableViewportSize(jtable.getPreferredSize());
		jtable.setShowGrid(false);
		add(new JScrollPane(jtable));
	}

	@Override
	public boolean isVisible() {
		return true;
	}
}
