/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
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

package org.sexydock.tabs.jhrome;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.sexydock.SwingUtils;
import org.sexydock.tabs.BasicTabUI;
import org.sexydock.tabs.Tab;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The UI for a {@link Tab}.
 *
 * @author andy.edwards
 */
@EqualsAndHashCode(callSuper = true)
@Data
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
    PropertyChangeHandler changeHandler;

    public JhromeTabUI() {
        super();
        init();
    }

    public static JhromeTabUI createUI(final JComponent c) {
        return new JhromeTabUI();
    }

    private void init() {
        innerBorder = new EmptyBorder(5, 5, 5, 0);
        outerBorder = new JhromeTabBorder();
        outerBorder.attrs.copyAttributes(normalAttributes);
        compoundBorder = new CompoundBorder(outerBorder, innerBorder);

        highlightTimer = new javax.swing.Timer(30, this::onHighlightTimerEvent);

        final JLabel label = getLabel();
        label.setFont(label.getFont().deriveFont(Font.PLAIN));
        label.setForeground(unselectedLabelColor);

        final JButton closeButton = getCloseButton();
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

    protected void onHighlightTimerEvent(final ActionEvent e) {
        final Tab tab = getTab();
        if (tab != null) {
            tab.repaint();
        }
    }

    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        final Tab tab = getTab();

        tab.setBorder(compoundBorder);
        tab.setOpaque(false);

        changeHandler = new PropertyChangeHandler();
        getTab().addPropertyChangeListener(changeHandler);
    }

    public void update(final Graphics g, final JComponent c) {
        paint(g, c);
    }

    @Override
    public void uninstallUI(final JComponent c) {
        final Tab tab = getTab();
        if (c != tab) {
            throw new IllegalArgumentException("c is not the Tab this UI is bound to");
        }

        super.uninstallUI(c);

        tab.setBorder(null);

        tab.removePropertyChangeListener(changeHandler);
        changeHandler = null;
    }

    protected void update() {
        super.update();

        final Tab tab = getTab();
        final JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(tab);

        if (tabbedPane != null) {
            outerBorder.setFlip(tabbedPane.getTabPlacement() == SwingConstants.BOTTOM || tabbedPane.getTabPlacement() == SwingConstants.RIGHT);
        }

        if (tab.isSelected()) {
            outerBorder.attrs.copyAttributes(selectedAttributes);
            highlightTimer.stop();
        } else {
            final float targetHighlight = tab.isRollover() ? 1f : 0f;
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

    protected float animate(final float current, final float target) {
        float crt = current;
        if (crt < target) {
            crt = Math.min(target, crt + highlightSpeed);
        } else if (current > target) {
            crt = Math.max(target, crt - highlightSpeed);
        }
        return crt;
    }

    @Override
    public boolean isHoverableAt(final Tab tab, final Point p) {
        return outerBorder.contains(p);
    }


    private final class PropertyChangeHandler implements PropertyChangeListener {
        @Override
        public void propertyChange(final PropertyChangeEvent evt) {
            if (evt.getSource() == getTab()) {
                if ("rollover".equals(evt.getPropertyName()) || "selected".equals(evt.getPropertyName())) {
                    update();
                }
            }
        }
    }
}
