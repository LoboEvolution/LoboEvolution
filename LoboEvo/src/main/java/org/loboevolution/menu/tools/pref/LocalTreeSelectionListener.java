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
