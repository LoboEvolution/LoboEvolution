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

package org.loboevolution.tab;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.Icon;
import javax.swing.JFrame;

class OuterFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	protected Component comp;

	protected Icon icon;

	protected Component tab;

	protected String tabTitle;

	protected String tip;

	/**
	 * <p>Constructor for OuterFrame.</p>
	 *
	 * @param tabbed a {@link org.loboevolution.tab.DnDTabbedPane} object.
	 */
	public OuterFrame(DnDTabbedPane tabbed) {
		super();
		setSize(200, 300);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				tabbed.addTab(OuterFrame.this.tabTitle, OuterFrame.this.icon, OuterFrame.this.comp,
						OuterFrame.this.tip);
				final int idx = tabbed.indexOfComponent(OuterFrame.this.comp);
				tabbed.setTabComponentAt(idx, OuterFrame.this.tab);
				tabbed.setSelectedIndex(idx);
				removeWindowListener(this);
			}
		});
	}
}
