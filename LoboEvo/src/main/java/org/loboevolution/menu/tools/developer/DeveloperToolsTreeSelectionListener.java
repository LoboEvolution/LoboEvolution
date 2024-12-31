/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
    public DeveloperToolsTreeSelectionListener(final DeveloperToolsWindow window) {
        this.window = window;
    }

    /** {@inheritDoc} */
    @Override
    public void valueChanged(final TreeSelectionEvent evt) {
        final TreePath path = evt.getPath();
        final DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if(node.getUserObject() instanceof ToolsInfo si){
            updateDeveloperPanel(si);
        }
    }

    private void updateDeveloperPanel(final ToolsInfo settingsInfo) {
        if (settingsInfo != null) {
            final AbstractToolsUI newUI = settingsInfo.createSettingsUI();
            this.window.getDeveloperPanel().setSettingsUI(newUI);
        } else {
            this.window.getDeveloperPanel().setSettingsUI(null);
        }
    }
}
