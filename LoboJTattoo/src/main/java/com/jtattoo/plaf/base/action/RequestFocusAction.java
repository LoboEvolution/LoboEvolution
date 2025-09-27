package com.jtattoo.plaf.base.action;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

public class RequestFocusAction extends AbstractAction {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JTabbedPane pane = (JTabbedPane) e.getSource();
        pane.requestFocus();
    }
}