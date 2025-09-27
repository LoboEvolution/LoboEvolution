package com.jtattoo.plaf.base.action;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

import static javax.swing.SwingConstants.SOUTH;

public class DownAction extends AbstractAction {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JTabbedPane pane = (JTabbedPane) e.getSource();
        final BaseTabbedPaneUI ui = (BaseTabbedPaneUI) pane.getUI();
        ui.navigateSelectedTab(SOUTH);
    }
}
