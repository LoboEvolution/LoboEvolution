package com.jtattoo.plaf.base.action;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;
import com.jtattoo.plaf.base.button.ScrollablePopupMenuTabButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

public  class ScrollTabsPopupMenuAction extends AbstractAction {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(final ActionEvent e) {
        JTabbedPane tabbedPane = null;
        final Object src = e.getSource();
        if (src instanceof JTabbedPane) {
            tabbedPane = (JTabbedPane) src;
        } else if (src instanceof ScrollablePopupMenuTabButton) {
            tabbedPane = (JTabbedPane) ((ScrollablePopupMenuTabButton) src).getParent();
        } else {
            return; // shouldn't happen
        }
        final BaseTabbedPaneUI ui = (BaseTabbedPaneUI) tabbedPane.getUI();
        if (ui.scrollableTabLayoutEnabled()) {
            final JPopupMenu popup = new JPopupMenu();
            for (int i = 0; i < tabbedPane.getTabCount(); i++) {
                final JMenuItem item = new JMenuItem(tabbedPane.getTitleAt(i));
                item.addActionListener(new ScrollTabsPopupMenuItemAction(tabbedPane, i));
                item.setEnabled(tabbedPane.isEnabledAt(i));
                popup.add(item);
            }
            popup.show(ui.tabScroller.popupMenuButton, 0, 0);
            if (ui.tabScroller.popupMenuButton.isShowing()) {
                final Point pt = ui.tabScroller.popupMenuButton.getLocationOnScreen();
                final int x = -popup.getWidth() + ui.tabScroller.popupMenuButton.getWidth();
                final int y = ui.tabScroller.popupMenuButton.getHeight() - 1;
                popup.setLocation(pt.x + x, pt.y + y);
            }
        }
    }
}