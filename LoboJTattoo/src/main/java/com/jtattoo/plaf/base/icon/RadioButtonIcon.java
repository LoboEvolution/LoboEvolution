package com.jtattoo.plaf.base.icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class RadioButtonIcon implements Icon {

    private static final int GAP = 2;

    @Override
    public int getIconHeight() {
        if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
            return 14;
        } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
            return 16;
        } else {
            return 18;
        }
    }

    @Override
    public int getIconWidth() {
        final int w;
        if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
            w = 14;
        } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
            w = 16;
        } else {
            w = 18;
        }
        return w + GAP;
    }

    @Override
    public void paintIcon(final Component c, final Graphics g, final int iconX, final int y) {
        int x = iconX;
        if (!JTattooUtilities.isLeftToRight(c)) {
            x += GAP;
        }
        final int w = getIconWidth() - GAP;
        final int h = getIconHeight();
        final Graphics2D g2D = (Graphics2D) g;
        final AbstractButton button = (AbstractButton) c;
        final ButtonModel model = button.getModel();
        Color cHi = Color.white;
        Color cLo = Color.white;
        if (!(c instanceof JRadioButtonMenuItem)) {
            final Color[] colors;
            if (button.isEnabled()) {
                if (button.isRolloverEnabled() && model.isRollover() || model.isPressed() && model.isArmed()) {
                    colors = AbstractLookAndFeel.getTheme().getRolloverColors();
                } else {
                    if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                        colors = AbstractLookAndFeel.getTheme().getFocusColors();
                    } else {
                        colors = AbstractLookAndFeel.getTheme().getCheckBoxColors();
                    }
                }
            } else {
                colors = AbstractLookAndFeel.getTheme().getDisabledColors();
            }
            cHi = colors[0];
            cLo = colors[colors.length - 1];
        }
        final Paint savedPaint = g2D.getPaint();
        g2D.setPaint(new GradientPaint(0, 0, cHi, 0, h, cLo));
        g2D.fillOval(x, y, w, h);
        g2D.setPaint(savedPaint);

        final Shape savedClip = g.getClip();
        // Area clipArea = new Area(new Ellipse2D.Double(x + 1, y + 1, w - 1, h - 1));
        final Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
        if (savedClip != null) {
            clipArea.intersect(new Area(savedClip));
        }
        g2D.setClip(clipArea);
        if (c instanceof JRadioButtonMenuItem) {
            g.setColor(Color.white);
            g.fillRect(x, y, w, h);
        } else {
            if (button.isEnabled()) {
                if (button.isRolloverEnabled() && model.isRollover() || model.isPressed() && model.isArmed()) {
                    JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w,
                            h);
                } else {
                    if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x, y,
                                w, h);
                    } else {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x,
                                y, w, h);
                    }
                }
            } else {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x, y, w, h);
            }
        }
        g2D.setClip(savedClip);

        final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (!model.isRollover()) {
            final Composite savedComposite = g2D.getComposite();
            final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
            g2D.setComposite(alpha);
            g2D.setColor(Color.white);
            g2D.drawOval(x + 1, y + 1, w - 2, h - 2);
            g2D.setComposite(savedComposite);
        }
        if (button.isEnabled()) {
            if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                final Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 30);
                final Color loColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 20);
                g.setColor(hiColor);
                g.drawOval(x - 1, y - 1, w + 2, h + 2);
                g.setColor(loColor);
                g2D.drawOval(x, y, w, h);
            } else {
                g.setColor(AbstractLookAndFeel.getFrameColor());
                g2D.drawOval(x, y, w, h);
            }
        } else {
            g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 40));
            g2D.drawOval(x, y, w, h);
        }

        if (model.isSelected()) {
            if (button.isEnabled()) {
                final Color fc = AbstractLookAndFeel.getForegroundColor();
                if (ColorHelper.getGrayValue(cLo) < 128) {
                    if (ColorHelper.getGrayValue(fc) < 128) {
                        g2D.setColor(Color.white);
                    } else {
                        g2D.setColor(fc);
                    }
                } else {
                    if (ColorHelper.getGrayValue(fc) > 128) {
                        g2D.setColor(Color.black);
                    } else {
                        g2D.setColor(fc);
                    }
                }
            } else {
                g.setColor(AbstractLookAndFeel.getDisabledForegroundColor());
            }
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
            } else {
                g2D.fillOval(x + 5, y + 5, w - 9, h - 9);
            }
        }
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
    }

} // end of class RadioButtonIcon
