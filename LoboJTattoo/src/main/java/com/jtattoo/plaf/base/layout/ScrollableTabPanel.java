package com.jtattoo.plaf.base.layout;

import com.jtattoo.plaf.base.BaseTabbedPaneUI;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.io.Serial;

public class ScrollableTabPanel extends JPanel implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;

    private final JTabbedPane tabPane;

    private BaseTabbedPaneUI ui;

    public ScrollableTabPanel(final BaseTabbedPaneUI ui, final JTabbedPane tabPane) {
        setLayout(null);
        setOpaque(false);
        this.ui = ui;
        this.tabPane = tabPane;
    }

    @Override
    public void doLayout() {
        if (getComponentCount() > 0) {
            final Component child = getComponent(0);
            child.setBounds(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public void paintComponent(final Graphics g) {
        super.paintComponent(g);
        ui.paintScrollContentBorder(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex(), 0, 0, getWidth(),
                getHeight());
        ui.paintTabArea(g, tabPane.getTabPlacement(), tabPane.getSelectedIndex());
    }
}