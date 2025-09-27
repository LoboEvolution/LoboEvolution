package com.jtattoo.plaf.base.action;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;
import com.jtattoo.plaf.base.button.ScrollableTabButton;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

public class ScrollTabsForwardAction extends AbstractAction {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JTabbedPane pane;
        final Object src = e.getSource();
        if (src instanceof JTabbedPane) {
            pane = (JTabbedPane) src;
        } else if (src instanceof ScrollableTabButton) {
            pane = (JTabbedPane) ((ScrollableTabButton) src).getParent();
        } else {
            return; // shouldn't happen
        }
        final BaseTabbedPaneUI ui = (BaseTabbedPaneUI) pane.getUI();

        if (ui.scrollableTabLayoutEnabled()) {
            ui.tabScroller.scrollForward(pane.getTabPlacement());
        }
    }
}
