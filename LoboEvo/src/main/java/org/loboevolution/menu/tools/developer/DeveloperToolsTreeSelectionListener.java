/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2023 Lobo Evolution
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

package org.loboevolution.menu.tools.developer;

import org.loboevolution.menu.tools.AbstractToolsUI;
import org.loboevolution.menu.tools.ToolsInfo;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 * <p>DeveloperToolsTreeSelectionListener class.</p>
 */
public class DeveloperToolsTreeSelectionListener implements TreeSelectionListener {

    private final DeveloperToolsWindow window;

    /**
     * <p>Constructor for LocalTreeSelectionListener.</p>
     *
     * @param window a {@link org.loboevolution.menu.tools.developer.DeveloperToolsWindow} object.
     */
    public DeveloperToolsTreeSelectionListener(DeveloperToolsWindow window) {
        this.window = window;
    }

    /** {@inheritDoc} */
    @Override
    public void valueChanged(TreeSelectionEvent evt) {
        final TreePath path = evt.getPath();
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if(node.getUserObject() instanceof ToolsInfo){
            final ToolsInfo si = node == null ? null : (ToolsInfo) node.getUserObject();
            updateDeveloperPanel(si);
        }
    }

    private void updateDeveloperPanel(ToolsInfo settingsInfo) {
        if (settingsInfo != null) {
            final AbstractToolsUI newUI = settingsInfo.createSettingsUI();
            this.window.getDeveloperPanel().setSettingsUI(newUI);
        } else {
            this.window.getDeveloperPanel().setSettingsUI(null);
        }
    }
}
