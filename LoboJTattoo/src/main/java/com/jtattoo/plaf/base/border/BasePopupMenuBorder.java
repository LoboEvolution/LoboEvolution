package com.jtattoo.plaf.base.border;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.UIResource;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serial;

public class BasePopupMenuBorder extends AbstractBorder implements UIResource {

    @Serial
    private static final long serialVersionUID = 1L;
    protected static Font logoFont;
    protected static Insets leftLogoInsets;
    protected static Insets rightLogoInsets;
    protected static Insets insets;
    protected static int shadowSize;

    public BasePopupMenuBorder() {
        logoFont = new Font("Dialog", Font.BOLD, 12);
        leftLogoInsets = new Insets(2, 18, 1, 1);
        rightLogoInsets = new Insets(2, 2, 1, 18);
        insets = new Insets(2, 1, 1, 1);
        shadowSize = 0;
    }

    @Override
    public Insets getBorderInsets(final Component c) {
        if (hasLogo(c)) {
            if (JTattooUtilities.isLeftToRight(c)) {
                return new Insets(leftLogoInsets.top, leftLogoInsets.left, leftLogoInsets.bottom + shadowSize,
                        leftLogoInsets.right + shadowSize);
            } else {
                return new Insets(rightLogoInsets.top, rightLogoInsets.left, rightLogoInsets.bottom + shadowSize,
                        rightLogoInsets.right + shadowSize);
            }
        } else {
            return new Insets(insets.top, insets.left, insets.bottom + shadowSize, insets.right + shadowSize);
        }
    }

    @Override
    public Insets getBorderInsets(final Component c, final Insets borderInsets) {
        final Insets ins = getBorderInsets(c);
        borderInsets.left = ins.left;
        borderInsets.top = ins.top;
        borderInsets.right = ins.right;
        borderInsets.bottom = ins.bottom;
        return borderInsets;
    }

    public Color getLogoColorHi() {
        return Color.white;
    }

    public Color getLogoColorLo() {
        return ColorHelper.darker(AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor(), 20);
    }

    public boolean hasLogo(final Component c) {
        return AbstractLookAndFeel.getTheme().getLogoString() != null
                && AbstractLookAndFeel.getTheme().getLogoString().length() > 0;
    }

    public boolean isMenuBarPopup(final Component c) {
        boolean menuBarPopup = false;
        if (c instanceof JPopupMenu) {
            final JPopupMenu pm = (JPopupMenu) c;
            if (pm.getInvoker() != null) {
                menuBarPopup = pm.getInvoker().getParent() instanceof JMenuBar;
            }
        }
        return menuBarPopup;
    }

    @Override
    public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        final Color logoColor = AbstractLookAndFeel.getMenuSelectionBackgroundColor();
        final Color borderColorLo = AbstractLookAndFeel.getFrameColor();
        final Color borderColorHi = ColorHelper.brighter(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 40);
        g.setColor(logoColor);
        if (JTattooUtilities.isLeftToRight(c)) {
            final int dx = getBorderInsets(c).left;
            g.fillRect(x, y, dx - 1, h - 1);
            paintLogo(c, g, x, y, w, h);
            // - highlight
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
            g.drawLine(x + dx, y + 1, x + w - 2, y + 1);
            g.setColor(borderColorHi);
            g.drawLine(x + 1, y, x + 1, y + h - 2);
            // - outer frame
            g.setColor(borderColorLo);
            if (isMenuBarPopup(c)) {
                // top
                g.drawLine(x + dx - 1, y, x + w, y);
                // left
                g.drawLine(x, y, x, y + h - 1);
                // bottom
                g.drawLine(x, y + h - 1, x + w, y + h - 1);
                // right
                g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
            } else {
                g.drawRect(x, y, w - 1, h - 1);
            }
            // - logo separator
            g.drawLine(x + dx - 1, y + 1, x + dx - 1, y + h - 1);
        } else {
            final int dx = getBorderInsets(c).right;
            g.fillRect(x + w - dx, y, dx, h - 1);
            paintLogo(c, g, x, y, w, h);
            // - highlight
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
            g.drawLine(x + 1, y + 1, x + w - dx - 1, y + 1);
            g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
            // - outer frame
            g.setColor(borderColorLo);
            if (isMenuBarPopup(c)) {
                // top
                g.drawLine(x, y, x + w - dx, y);
                // left
                g.drawLine(x, y, x, y + h - 1);
                // bottom
                g.drawLine(x, y + h - 1, x + w, y + h - 1);
                // right
                g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
            } else {
                g.drawRect(x, y, w - 1, h - 1);
            }
            // - logo separator
            g.drawLine(x + w - dx, y + 1, x + w - dx, y + h - 1);
        }
    }

    public void paintLogo(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
        if (hasLogo(c)) {
            final Graphics2D g2D = (Graphics2D) g;

            final Font savedFont = g2D.getFont();
            g.setFont(logoFont);
            final FontMetrics fm = JTattooUtilities.getFontMetrics((JComponent) c, g, c.getFont());
            final String logo = JTattooUtilities.getClippedText(AbstractLookAndFeel.getTheme().getLogoString(), fm,
                    h - 16);

            final AffineTransform savedTransform = g2D.getTransform();

            final Color fc = getLogoColorHi();
            final Color bc = getLogoColorLo();

            if (JTattooUtilities.isLeftToRight(c)) {
                g2D.translate(fm.getAscent() + 1, h - shadowSize - 4);
                g2D.rotate(Math.toRadians(-90));
                g2D.setColor(bc);
                JTattooUtilities.drawString((JComponent) c, g, logo, 0, 1);
                g2D.setColor(fc);
                JTattooUtilities.drawString((JComponent) c, g, logo, 1, 0);
            } else {
                g2D.translate(w - shadowSize - 4, h - shadowSize - 4);
                g2D.rotate(Math.toRadians(-90));
                g2D.setColor(bc);
                JTattooUtilities.drawString((JComponent) c, g, logo, 0, 1);
                g2D.setColor(fc);
                JTattooUtilities.drawString((JComponent) c, g, logo, 1, 0);
            }

            g2D.setTransform(savedTransform);
            g2D.setFont(savedFont);
        }
    }

} // class PopupMenuBorder

