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
package org.loboevolution.primary.gui.prefs;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import org.loboevolution.primary.gui.AbstractSettingsUI;
import org.loboevolution.primary.info.SettingsInfo;

/**
 * The listener interface for receiving localTreeSelection events. The class
 * that is interested in processing a localTreeSelection event implements
 * this interface, and the object created with that class is registered with
 * a component using the component's
 * <code>addLocalTreeSelectionListener</code> method. When the
 * localTreeSelection event occurs, that object's appropriate method is
 * invoked.
 *
 * @see LocalTreeSelectionEvent
 */
public class LocalTreeSelectionListener implements TreeSelectionListener {
	
	private PreferencesDialog prefer;

	public LocalTreeSelectionListener(PreferencesDialog prefer) {
		this.prefer = prefer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.
	 * event. TreeSelectionEvent)
	 */
	@Override
	public void valueChanged(TreeSelectionEvent e) {
		TreePath path = e.getPath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		SettingsInfo si = node == null ? null : (SettingsInfo) node.getUserObject();
		updatePreferencesPanel(si);
	}
	
	/**
	 * Update preferences panel.
	 *
	 * @param settingsInfo
	 *            the settings info
	 */
	
	private void updatePreferencesPanel(SettingsInfo settingsInfo) {
		if (settingsInfo != null) {
			AbstractSettingsUI newUI = settingsInfo.createSettingsUI();
			prefer.getPreferencesPanel().setSettingsUI(newUI);
		} else {
			prefer.getPreferencesPanel().setSettingsUI(null);
		}
	}
}
