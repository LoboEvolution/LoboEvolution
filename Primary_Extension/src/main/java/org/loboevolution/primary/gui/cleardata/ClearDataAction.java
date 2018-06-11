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
package org.loboevolution.primary.gui.cleardata;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.loboevolution.primary.gui.CheckBoxPanel;

public class ClearDataAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	/** The cache panel. */
	private CheckBoxPanel cachePanel;
	
	/** The cookie panel. */
	private CheckBoxPanel cookiePanel;
	
	/** The navigation panel. */
	private CheckBoxPanel navigationPanel;
	
	/** The bookmark panel. */
	private CheckBoxPanel bookmarkPanel;

	public ClearDataAction(CheckBoxPanel cachePanel, CheckBoxPanel cookiePanel, CheckBoxPanel navigationPanel, CheckBoxPanel bookmarkPanel) {
		this.cachePanel = cachePanel;
		this.cookiePanel = cookiePanel;
		this.navigationPanel = navigationPanel;
		this.bookmarkPanel = bookmarkPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		ClearHistory ch = new ClearHistory();
		
		if (cachePanel.isSelected()) {
			ch.clearCache();
		}

		if (cookiePanel.isSelected()) {
			ch.clearCookies();
		}
		
		if (navigationPanel.isSelected()) {
			ch.clearNavigation();
		}
		
		if (bookmarkPanel.isSelected()) {
			ch.clearBookmarks();
		}	
	}
}
