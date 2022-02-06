package org.sexydock.tabs;

import javax.swing.*;

public class DefaultTabCloseButtonListener implements ITabCloseButtonListener {

    @Override
    public void tabCloseButtonPressed(JTabbedPane tabbedPane, int tabIndex) {
        tabbedPane.removeTabAt(tabIndex);
    }
}
