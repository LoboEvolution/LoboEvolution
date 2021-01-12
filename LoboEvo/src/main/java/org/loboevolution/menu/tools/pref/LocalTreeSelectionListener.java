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

package org.loboevolution.menu.tools.pref;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * <p>LocalTreeSelectionListener class.</p>
 *
 * @author utente
 * @version $Id: $Id
 */
public class LocalTreeSelectionListener implements TreeSelectionListener {

	private final PreferenceWindow preferenceWindow;

	/**
	 * <p>Constructor for LocalTreeSelectionListener.</p>
	 *
	 * @param preferenceWindow a {@link org.loboevolution.menu.tools.pref.PreferenceWindow} object.
	 */
	public LocalTreeSelectionListener(PreferenceWindow preferenceWindow) {
		this.preferenceWindow = preferenceWindow;
	}

	private void updatePreferencesPanel(SettingsInfo settingsInfo) {
		if (settingsInfo != null) {
			final AbstractSettingsUI newUI = settingsInfo.createSettingsUI();
			this.preferenceWindow.getPreferencesPanel().setSettingsUI(newUI);
		} else {
			this.preferenceWindow.getPreferencesPanel().setSettingsUI(null);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void valueChanged(TreeSelectionEvent evt) {
		final TreePath path = evt.getPath();
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		final SettingsInfo si = node == null ? null : (SettingsInfo) node.getUserObject();
		updatePreferencesPanel(si);
	}
}
