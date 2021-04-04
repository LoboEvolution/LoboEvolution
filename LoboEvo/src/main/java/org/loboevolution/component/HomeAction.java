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

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.tab.DnDTabbedPane;
import org.loboevolution.tab.TabbedPanePopupMenu;
import org.loboevolution.welcome.WelcomePanel;

/**
 * <p>HomeAction class.</p>
 *
 *
 *
 */
public class HomeAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private final IBrowserPanel panel;

	/**
	 * <p>Constructor for HomeAction.</p>
	 *
	 * @param panel a {@link org.loboevolution.component.IBrowserPanel} object.
	 */
	public HomeAction(IBrowserPanel panel) {
		this.panel = panel;
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent evt) {
		final int indexPanel = this.panel.getTabbedPane().getSelectedIndex();
		this.panel.getTabbedPane().remove(indexPanel);
		final DnDTabbedPane tabbedPane = this.panel.getTabbedPane();
		tabbedPane.setComponentPopupMenu(new TabbedPanePopupMenu(this.panel));
		tabbedPane.insertTab("Welcome", null, new WelcomePanel(panel), "Welcome", indexPanel);
		tabbedPane.setSelectedIndex(indexPanel);
	}
}
