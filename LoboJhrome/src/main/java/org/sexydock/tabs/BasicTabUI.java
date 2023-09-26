/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.sexydock.tabs;

import org.sexydock.SwingUtils;
import org.sexydock.tabs.jhrome.JhromeTabbedPaneUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    public static BasicTabUI createUI(final JComponent c) {
        return new BasicTabUI();
    }

    private void init() {
        label = new JLabel();
        closeButton = new JButton("X");

        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        label.setForeground(unselectedForeground);
        label.setOpaque(false);

        closeButton.addActionListener(e -> {
            final JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(tab);
            final ITabCloseButtonListener closeButtonListener = PropertyGetter.get(ITabCloseButtonListener.class, tab, CLOSE_BUTTON_LISTENER, JTabbedPane.class, JhromeTabbedPaneUI.TAB_CLOSE_BUTTON_LISTENER);
            if (closeButtonListener != null) {
                final int index = tabbedPane.indexOfComponent(tab.getContent());
                if (index >= 0) {
                    closeButtonListener.tabCloseButtonPressed(tabbedPane, index);
                }
            }
        });
    }

    protected Tab getTab() {
        return tab;
    }

    protected void onHighlightTimerEvent(final ActionEvent e) {
        if (tab != null) {
            tab.repaint();
        }
    }

    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        tab = (Tab) c;

        tab.setLayout(new TabLayout());
        tab.add(label, BorderLayout.CENTER);
        tab.add(closeButton, BorderLayout.EAST);

        tab.addPropertyChangeListener(propertyChangeHandler);
    }

    @Override
    public void uninstallUI(final JComponent c) {
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
    public void paint(final Graphics g, final JComponent c) {
        tab = (Tab) c;
        update();
        super.paint(g, c);
    }

    protected void update() {
        label.setText(tab.getTitle());
        label.setIcon(tab.getIcon());
        label.setDisplayedMnemonic(tab.getMnemonic());
        label.setDisplayedMnemonicIndex(tab.getDisplayedMnemonicIndex());

        final JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(tab);

        final boolean enabled = tab.isEnabled() && (tabbedPane == null || tabbedPane.isEnabled());

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

    @Override
    public boolean isDraggableAt(final Tab tab, final Point p) {
        return isHoverableAt(tab, p) && !closeButton.contains(SwingUtilities.convertPoint(tab, p, closeButton));
    }

    @Override
    public boolean isSelectableAt(final Tab tab, final Point p) {
        return isDraggableAt(tab, p);
    }

    @Override
    public boolean isHoverableAt(final Tab tab, final Point p) {
        final Insets insets = tab.getInsets();
        final Rectangle bounds = tab.getBounds();
        bounds.x = insets.left;
        bounds.width -= insets.left + insets.right;
        bounds.y = insets.top;
        bounds.height -= insets.top + insets.bottom;
        return bounds.contains(p);
    }

    public Color getUnselectedLabelColor() {
        return unselectedForeground;
    }

    public void setUnselectedLabelColor(final Color unselectedLabelColor) {
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
        public Dimension minimumLayoutSize(final Container target) {
            final Dimension minSize = super.minimumLayoutSize(target);
            if (minSize != null) {
                final Insets insets = target.getInsets();
                minSize.width = insets.left + insets.right;
            }
            return minSize;
        }
    }

    private final class PropertyChangeHandler implements PropertyChangeListener {
        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            update();
        }
    }
}
