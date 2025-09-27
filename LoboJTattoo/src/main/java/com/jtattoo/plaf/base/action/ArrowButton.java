package com.jtattoo.plaf.base.action;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseTabbedPaneUI;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.swing.*;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class ArrowButton extends JButton implements SwingConstants {

    @Serial
    private static final long serialVersionUID = 1L;
    protected int direction;
    private BaseTabbedPaneUI ui;

    public ArrowButton(BaseTabbedPaneUI ui, final int direction) {
        this.direction = direction;
        setRequestFocusEnabled(false);
        if (ui.simpleButtonBorder) {
            final Color cLo = MetalLookAndFeel.getControlDarkShadow();
            final Color cHi = AbstractLookAndFeel.getTheme().getControlHighlight();
            setBorder(BorderFactory.createEtchedBorder(cHi, cLo));
        }
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(5, 5);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(17, 17);
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        // Draw the arrow
        final int w = getSize().width;
        final int h = getSize().height;
        int size = Math.min((h - 4) / 3, (w - 4) / 3);
        size = Math.max(size, 2);
        paintTriangle(g, (w - size) / 2 + 1, (h - size) / 2 + 1, size);
    }

    public void paintTriangle(final Graphics g, final int x, final int y, final int sizeTriangle) {
        final Color oldColor = g.getColor();
        final int mid;
        int i;
        int j;
        final int size = Math.max(sizeTriangle, 2);
        mid = size / 2 - 1;

        final Color enabledColor = AbstractLookAndFeel.getTheme().getButtonForegroundColor();
        final Color disabledColor = AbstractLookAndFeel.getTheme().getDisabledForegroundColor();

        g.translate(x, y);
        if (isEnabled()) {
            g.setColor(enabledColor);
        } else {
            g.setColor(disabledColor);
        }

        switch (direction) {
            case NORTH:
                for (i = 0; i < size; i++) {
                    g.drawLine(mid - i, i, mid + i, i);
                }
                break;
            case SOUTH:
                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(mid - i, j, mid + i, j);
                    j++;
                }
                break;
            case WEST:
                for (i = 0; i < size; i++) {
                    g.drawLine(i, mid - i, i, mid + i);
                }
                break;
            case EAST:
                j = 0;
                for (i = size - 1; i >= 0; i--) {
                    g.drawLine(j, mid - i, j, mid + i);
                    j++;
                }
                break;
            default:
                break;
        }
        g.translate(-x, -y);
        g.setColor(oldColor);
    }

}
