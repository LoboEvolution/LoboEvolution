package com.jtattoo.plaf.base.action;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.Serial;

/**
 * Selects a tab in the JTabbedPane based on the String of the action command.
 * The tab selected is based on the first tab that has a mnemonic matching the
 * first character of the action command.
 */
public class SetSelectedIndexAction extends AbstractAction {

    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void actionPerformed(final ActionEvent e) {
        final JTabbedPane pane = (JTabbedPane) e.getSource();

        if (pane != null && pane.getUI() instanceof BaseTabbedPaneUI) {
            final BaseTabbedPaneUI ui = (BaseTabbedPaneUI) pane.getUI();
            final String command = e.getActionCommand();

            if (command != null && command.length() > 0) {
                int mnemonic = e.getActionCommand().charAt(0);
                if (mnemonic >= 'a' && mnemonic <= 'z') {
                    mnemonic -= 'a' - 'A';
                }
                final Integer index = ui.mnemonicToIndexMap.get(mnemonic);
                if (index != null && pane.isEnabledAt(index)) {
                    pane.setSelectedIndex(index);
                }
            }
        }
    }
}
