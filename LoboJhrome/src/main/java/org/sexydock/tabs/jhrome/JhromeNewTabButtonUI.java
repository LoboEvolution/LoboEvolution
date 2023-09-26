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

package org.sexydock.tabs.jhrome;

import org.sexydock.SwingUtils;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class JhromeNewTabButtonUI extends BasicButtonUI {
    AbstractButton button = null;
    JhromeNewTabButtonBorder border;
    float highlight = 0f;
    final float highlightSpeed = 0.2f;
    Timer highlightTimer;

    public JhromeNewTabButtonUI() {
        init();
    }

    public static JhromeNewTabButtonUI createUI(final JComponent c) {
        return new JhromeNewTabButtonUI();
    }

    public static Icon createNewTabButtonIcon() {
        final BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        final Path2D path = new Path2D.Double();
        path.moveTo(3, 0);
        path.lineTo(6, 0);
        path.lineTo(6, 3);
        path.lineTo(9, 3);
        path.lineTo(9, 6);
        path.lineTo(6, 6);
        path.lineTo(6, 9);
        path.lineTo(3, 9);
        path.lineTo(3, 6);
        path.lineTo(0, 6);
        path.lineTo(0, 3);
        path.lineTo(3, 3);
        path.lineTo(3, 0);

        final Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(Color.WHITE);
        g2.fill(path);
        g2.setColor(JhromeTabBorderAttributes.UNSELECTED_BORDER.outlineColor);
        g2.setStroke(new BasicStroke(1.1f));
        g2.draw(path);

        return new ImageIcon(image);
    }

    @Override
    public void installUI(final JComponent c) {
        super.installUI(c);
        button = (AbstractButton) c;
        button.setIcon(JhromeNewTabButtonUI.createNewTabButtonIcon());
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        button.setBorder(border);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
    }

    @Override
    public void uninstallUI(final JComponent c) {
        super.uninstallUI(c);
        button.setIcon(null);
        button.setFocusable(true);
        button.setContentAreaFilled(true);
        button.setBorder(null);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
    }

    private void init() {
        border = new JhromeNewTabButtonBorder();

        highlightTimer = new javax.swing.Timer(30, e -> {
            if (button != null) {
                button.repaint();
            }
        });
    }

    @Override
    public void paint(final Graphics g, final JComponent c) {
        button = (AbstractButton) c;
        update((AbstractButton) c);
        paintFill(g, c);
        super.paint(g, c);
    }

    public void paintFill(final Graphics g, final JComponent c) {
        border.paint(c, g, 0, 0, c.getWidth(), c.getHeight(), false, true);
    }

    protected float animate(final float curr, final float target) {
        float current = curr;
        if (current < target) {
            current = Math.min(target, current + highlightSpeed);
        } else if (current > target) {
            current = Math.max(target, current - highlightSpeed);
        }
        return current;
    }

    protected void update(final AbstractButton button) {
        final JTabbedPane tabbedPane = SwingUtils.getJTabbedPaneAncestor(button);
        if (tabbedPane != null) {
            border.flip = tabbedPane.getTabPlacement() == SwingConstants.BOTTOM || tabbedPane.getTabPlacement() == SwingConstants.RIGHT;
        }
        if (button.getModel().isPressed()) {
            border.attrs.copy(JhromeNewTabButtonBorder.PRESSED_ATTRIBUTES);
            highlightTimer.stop();
        } else {
            final float targetHighlight = button.getModel().isRollover() ? 1 : 0;
            if (highlight != targetHighlight) {
                highlight = animate(highlight, targetHighlight);
                highlightTimer.start();
            } else {
                highlightTimer.stop();
            }
            border.attrs.copy(JhromeNewTabButtonBorder.UNSELECTED_ATTRIBUTES);
            border.attrs.interpolateColors(JhromeNewTabButtonBorder.UNSELECTED_ATTRIBUTES, JhromeNewTabButtonBorder.ROLLOVER_ATTRIBUTES, highlight);
        }
    }

    @Override
    public Dimension getMinimumSize(final JComponent c) {
        return new Dimension(26, 16);
    }

    @Override
    public Dimension getPreferredSize(final JComponent c) {
        return new Dimension(26, 16);
    }

    @Override
    public Dimension getMaximumSize(final JComponent c) {
        return new Dimension(26, 16);
    }
}
