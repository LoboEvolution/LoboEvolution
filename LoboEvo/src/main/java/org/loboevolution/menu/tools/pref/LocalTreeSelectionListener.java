/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.menu.tools.pref;

import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.menu.tools.ToolsInfo;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * <p>LocalTreeSelectionListener class.</p>
 */
public class LocalTreeSelectionListener implements TreeSelectionListener {

	private final PreferenceWindow preferenceWindow;

	/**
	 * <p>Constructor for LocalTreeSelectionListener.</p>
	 *
	 * @param preferenceWindow a {@link org.loboevolution.menu.tools.pref.PreferenceWindow} object.
	 */
	public LocalTreeSelectionListener(final PreferenceWindow preferenceWindow) {
		this.preferenceWindow = preferenceWindow;
	}

	/** {@inheritDoc} */
	@Override
	public void valueChanged(final TreeSelectionEvent evt) {
		final TreePath path = evt.getPath();
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		final ToolsInfo si = node == null ? null : (ToolsInfo) node.getUserObject();
		updatePreferencesPanel(si);
	}

	private void updatePreferencesPanel(final ToolsInfo settingsInfo) {
		if (settingsInfo != null) {
			final AbstractToolsUI newUI = settingsInfo.createSettingsUI();
			this.preferenceWindow.getPreferencesPanel().setSettingsUI(newUI);
		} else {
			this.preferenceWindow.getPreferencesPanel().setSettingsUI(null);
		}
	}
}
