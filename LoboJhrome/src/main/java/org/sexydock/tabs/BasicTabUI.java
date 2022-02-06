/*
Copyright 2012 James Edwards

This file is part of Jhrome.

Jhrome is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Jhrome is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with Jhrome.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sexydock.tabs;

import org.sexydock.SwingUtils;
import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The UI for a {@link Tab}.
 *
 * @author andy.edwards
 */
public class BasicTabUI extends TabUI {
    public static final String CLOSE_BUTTON_LISTENER = "sexydock.closeButtonListener";

    public static final String CLOSE_BUTTON_VISIBLE = "sexydock.closeButtonVisible";
    Tab tab;
    JLabel label;
    Component displayedTabComponent;
    JButton closeButton;
    final Color disabledForeground = Color.GRAY;
    final Color rolloverForeground = new Color(80, 80, 80);
    Color unselectedForeground = new Color(80, 80, 80);
    final Color selectedForeground = Color.BLACK;
    final Color disabledBackground = new Color(191, 191, 202);
    final Color rolloverBackground = Color.RED;                    // new Color( 231 , 231 , 239 );
    final Color unselectedBackground = new Color(211, 211, 222);
    final Color selectedBackground = new Color(248, 248, 248);
    final PropertyChangeHandler propertyChangeHandler = new PropertyChangeHandler();

    public BasicTabUI() {
        init();
    }

    public static BasicTabUI createUI(JComponent c) {
        return new BasicTabUI();
    }

    private void init() {
        label = new JLabel();
        closeButton = new JButton("X");

        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        label.setForeground(unselectedForeground);
        label.setOpaque(false);

        closeButton.addActionListener(e -> {
            JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(tab);
            ITabCloseButtonListener closeButtonListener = PropertyGetter.get(ITabCloseButtonListener.class, tab, CLOSE_BUTTON_LISTENER, JTabbedPane.class, JhromeTabbedPaneUI.TAB_CLOSE_BUTTON_LISTENER);
            if (closeButtonListener != null) {
                int index = tabbedPane.indexOfComponent(tab.getContent());
                if (index >= 0) {
                    closeButtonListener.tabCloseButtonPressed(tabbedPane, index);
                }
            }
        });
    }

    protected Tab getTab() {
        return tab;
    }

    protected void onHighlightTimerEvent(ActionEvent e) {
        if (tab != null) {
            tab.repaint();
        }
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        tab = (Tab) c;

        tab.setLayout(new TabLayout());
        tab.add(label, BorderLayout.CENTER);
        tab.add(closeButton, BorderLayout.EAST);

        tab.addPropertyChangeListener(propertyChangeHandler);
    }

    @Override
    public void uninstallUI(JComponent c) {
        if (c != tab) {
            throw new IllegalArgumentException("c is not the Tab this UI is bound to");
        }

        super.uninstallUI(c);

        tab = (Tab) c;

        tab.removePropertyChangeListener(propertyChangeHandler);
        tab.remove(label);
        tab.remove(closeButton);
        tab.setLayout(null);

        tab = null;
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        tab = (Tab) c;
        update();
        super.paint(g, c);
    }

    protected void update() {
        label.setText(tab.getTitle());
        label.setIcon(tab.getIcon());
        label.setDisplayedMnemonic(tab.getMnemonic());
        label.setDisplayedMnemonicIndex(tab.getDisplayedMnemonicIndex());

        JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(tab);

        boolean enabled = tab.isEnabled() && (tabbedPane == null || tabbedPane.isEnabled());

        closeButton.setVisible(PropertyGetter.get(Boolean.class, tab, CLOSE_BUTTON_VISIBLE, JTabbedPane.class, JhromeTabbedPaneUI.TAB_CLOSE_BUTTONS_VISIBLE, false));
        closeButton.setEnabled(enabled);

        if (tab.getTabComponent() != displayedTabComponent) {
            if (displayedTabComponent != null) {
                tab.remove(displayedTabComponent);
            }

            displayedTabComponent = tab.getTabComponent();

            if (displayedTabComponent != null) {
                tab.remove(label);
                tab.add(displayedTabComponent, BorderLayout.CENTER);
            } else {
                tab.add(label, BorderLayout.CENTER);
            }
        }

        if (!enabled) {
            label.setForeground(getDisabledForeground());
        } else if (tab.isSelected()) {
            label.setForeground(getSelectedForeground());
        } else if (tab.isRollover()) {
            label.setForeground(getRolloverForeground());
        } else {
            label.setForeground(getUnselectedForeground());
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jhrome.IJhromeTab#isDraggableAt(java.awt.Point)
     */
    @Override
    public boolean isDraggableAt(Tab tab, Point p) {
        return isHoverableAt(tab, p) && !closeButton.contains(SwingUtilities.convertPoint(tab, p, closeButton));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jhrome.IJhromeTab#isSelectableAt(java.awt.Point)
     */
    @Override
    public boolean isSelectableAt(Tab tab, Point p) {
        return isDraggableAt(tab, p);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jhrome.IJhromeTab#isHoverableAt(java.awt.Point)
     */
    @Override
    public boolean isHoverableAt(Tab tab, Point p) {
        Insets insets = tab.getInsets();
        Rectangle bounds = tab.getBounds();
        bounds.x = insets.left;
        bounds.width -= insets.left + insets.right;
        bounds.y = insets.top;
        bounds.height -= insets.top + insets.bottom;
        return bounds.contains(p);
    }

    public Color getUnselectedLabelColor() {
        return unselectedForeground;
    }

    public void setUnselectedLabelColor(Color unselectedLabelColor) {
        this.unselectedForeground = unselectedLabelColor;
    }

    public JButton getCloseButton() {
        return closeButton;
    }

    public JLabel getLabel() {
        return label;
    }

    public Color getDisabledForeground() {
        return disabledForeground != null ? disabledForeground : getUnselectedForeground();
    }

    public Color getRolloverForeground() {
        return rolloverForeground != null ? rolloverForeground : getSelectedForeground();
    }

    public Color getUnselectedForeground() {
        return unselectedForeground != null ? unselectedForeground : getSelectedForeground();
    }

    public Color getSelectedForeground() {
        return selectedForeground;
    }

    public Color getDisabledBackground() {
        return disabledBackground != null ? disabledBackground : getUnselectedBackground();
    }

    public Color getRolloverBackground() {
        return rolloverBackground != null ? rolloverBackground : getSelectedBackground();
    }

    public Color getUnselectedBackground() {
        return unselectedBackground != null ? unselectedBackground : getSelectedBackground();
    }

    public Color getSelectedBackground() {
        return selectedBackground;
    }

    private static class TabLayout extends BorderLayout {
        @Override
        public Dimension minimumLayoutSize(Container target) {
            Dimension minSize = super.minimumLayoutSize(target);
            if (minSize != null) {
                Insets insets = target.getInsets();
                minSize.width = insets.left + insets.right;
            }
            return minSize;
        }
    }

    private class PropertyChangeHandler implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            update();
        }
    }
}
