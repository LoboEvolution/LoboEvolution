package com.jtattoo.plaf.base.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

public class ScrollTabsPopupMenuItemAction extends AbstractAction {

    @Serial
    private static final long serialVersionUID = 1L;
    private JTabbedPane tabbedPane = null;
    private int selectIndex = 0;

    public ScrollTabsPopupMenuItemAction(final JTabbedPane pane, final int index) {
        tabbedPane = pane;
        selectIndex = index;
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        tabbedPane.setSelectedIndex(selectIndex);
    }
}