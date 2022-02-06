package org.sexydock;

import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import java.awt.*;

public class SwingUtils {

    public static JTabbedPane getJTabbedPaneAncestor(Component c) {
        while (c != null) {
            if (c instanceof JTabbedPane) {
                return (JTabbedPane) c;
            }
            c = c.getParent();
        }
        return null;
    }

    public static JhromeTabbedPaneUI getJTabbedPaneAncestorUI(Component c) {
        JTabbedPane tabbedPane = getJTabbedPaneAncestor(c);
        if (tabbedPane != null && tabbedPane.getUI() instanceof JhromeTabbedPaneUI) {
            return (JhromeTabbedPaneUI) tabbedPane.getUI();
        }
        return null;
    }
}
