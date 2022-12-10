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

package org.sexydock.tabs.jhrome;

import org.sexydock.SwingUtils;
import org.sexydock.tabs.BasicTabUI;
import org.sexydock.tabs.Tab;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
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
public class JhromeTabUI extends BasicTabUI {
    CompoundBorder compoundBorder;
    JhromeTabBorder outerBorder;
    JhromeTabBorderAttributes selectedAttributes = JhromeTabBorderAttributes.SELECTED_BORDER.clone();
    JhromeTabBorderAttributes rolloverAttributes = JhromeTabBorderAttributes.UNSELECTED_ROLLOVER_BORDER.clone();
    JhromeTabBorderAttributes normalAttributes = JhromeTabBorderAttributes.UNSELECTED_BORDER.clone();
    EmptyBorder innerBorder;
    float highlightAmount = 0f;
    float highlightSpeed = 0.1f;
    javax.swing.Timer highlightTimer;
    Color selectedLabelColor = Color.BLACK;
    Color unselectedLabelColor = new Color(80, 80, 80);
    PropertyChangeHandler propertyChangeHandler;

    public JhromeTabUI() {
        super();
        init();
    }

    public static JhromeTabUI createUI(JComponent c) {
        return new JhromeTabUI();
    }

    private void init() {
        innerBorder = new EmptyBorder(5, 5, 5, 0);
        outerBorder = new JhromeTabBorder();
        outerBorder.attrs.copyAttributes(normalAttributes);
        compoundBorder = new CompoundBorder(outerBorder, innerBorder);

        highlightTimer = new javax.swing.Timer(30, this::onHighlightTimerEvent);

        JLabel label = getLabel();
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        label.setForeground(unselectedLabelColor);

        JButton closeButton = getCloseButton();
        closeButton.setText("");
        closeButton.setUI(new BasicButtonUI());
        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setOpaque(false);
        closeButton.setIcon(JhromeTabCloseButtonIcons.getJhromeNormalIcon());
        closeButton.setRolloverIcon(JhromeTabCloseButtonIcons.getJhromeRolloverIcon());
        closeButton.setPressedIcon(JhromeTabCloseButtonIcons.getJhromePressedIcon());
        closeButton.setPreferredSize(new Dimension(closeButton.getIcon().getIconWidth() + 1, closeButton.getIcon().getIconHeight() + 1));
    }

    protected void onHighlightTimerEvent(ActionEvent e) {
        Tab tab = getTab();
        if (tab != null) {
            tab.repaint();
        }
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        Tab tab = getTab();

        tab.setBorder(compoundBorder);
        tab.setOpaque(false);

        propertyChangeHandler = new PropertyChangeHandler();
        getTab().addPropertyChangeListener(propertyChangeHandler);
    }

    public void update(Graphics g, JComponent c) {
        paint(g, c);
    }

    @Override
    public void uninstallUI(JComponent c) {
        Tab tab = getTab();
        if (c != tab) {
            throw new IllegalArgumentException("c is not the Tab this UI is bound to");
        }

        super.uninstallUI(c);

        tab.setBorder(null);

        tab.removePropertyChangeListener(propertyChangeHandler);
        propertyChangeHandler = null;
    }

    protected void update() {
        super.update();

        Tab tab = getTab();
        JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(tab);

        if (tabbedPane != null) {
            outerBorder.setFlip(tabbedPane.getTabPlacement() == JTabbedPane.BOTTOM || tabbedPane.getTabPlacement() == JTabbedPane.RIGHT);
        }

        if (tab.isSelected()) {
            outerBorder.attrs.copyAttributes(selectedAttributes);
            highlightTimer.stop();
        } else {
            float targetHighlight = tab.isRollover() ? 1f : 0f;
            if (highlightAmount != targetHighlight) {
                highlightAmount = animate(highlightAmount, targetHighlight);
                highlightTimer.start();
            } else {
                highlightTimer.stop();
            }
            outerBorder.attrs.copyAttributes(rolloverAttributes);
            outerBorder.attrs.interpolateColors(normalAttributes, rolloverAttributes, highlightAmount);
        }
    }

    protected float animate(float current, float target) {
        if (current < target) {
            current = Math.min(target, current + highlightSpeed);
        } else if (current > target) {
            current = Math.max(target, current - highlightSpeed);
        }
        return current;
    }

    @Override
    public boolean isHoverableAt(Tab tab, Point p) {
        return outerBorder.contains(p);
    }

    public JhromeTabBorderAttributes getSelectedAttributes() {
        return selectedAttributes;
    }

    public void setSelectedAttributes(JhromeTabBorderAttributes selectedAttributes) {
        this.selectedAttributes = selectedAttributes;
    }

    public JhromeTabBorderAttributes getRolloverAttributes() {
        return rolloverAttributes;
    }

    public void setRolloverAttributes(JhromeTabBorderAttributes rolloverAttributes) {
        this.rolloverAttributes = rolloverAttributes;
    }

    public JhromeTabBorderAttributes getNormalAttributes() {
        return normalAttributes;
    }

    public void setNormalAttributes(JhromeTabBorderAttributes normalAttributes) {
        this.normalAttributes = normalAttributes;
    }

    public float getHighlightSpeed() {
        return highlightSpeed;
    }

    public void setHighlightSpeed(float highlightSpeed) {
        this.highlightSpeed = highlightSpeed;
    }

    public Color getSelectedLabelColor() {
        return selectedLabelColor;
    }

    public void setSelectedLabelColor(Color selectedLabelColor) {
        this.selectedLabelColor = selectedLabelColor;
    }

    public Color getUnselectedLabelColor() {
        return unselectedLabelColor;
    }

    public void setUnselectedLabelColor(Color unselectedLabelColor) {
        this.unselectedLabelColor = unselectedLabelColor;
    }

    private class PropertyChangeHandler implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getSource() == getTab()) {
                if ("rollover".equals(evt.getPropertyName()) || "selected".equals(evt.getPropertyName())) {
                    update();
                }
            }
        }
    }
}
