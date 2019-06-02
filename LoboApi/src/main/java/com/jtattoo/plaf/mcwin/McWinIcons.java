/*
* Copyright (c) 2002 and later by MH Software-Entwicklung. All Rights Reserved.
*  
* JTattoo is multiple licensed. If your are an open source developer you can use
* it under the terms and conditions of the GNU General Public License version 2.0
* or later as published by the Free Software Foundation.
*  
* see: gpl-2.0.txt
* 
* If you pay for a license you will become a registered user who could use the
* software under the terms and conditions of the GNU Lesser General Public License
* version 2.0 or later with classpath exception as published by the Free Software
* Foundation.
* 
* see: lgpl-2.0.txt
* see: classpath-exception.txt
* 
* Registered users could also use JTattoo under the terms and conditions of the 
* Apache License, Version 2.0 as published by the Apache Software Foundation.
*  
* see: APACHE-LICENSE-2.0.txt
 */
package com.jtattoo.plaf.mcwin;

import com.jtattoo.plaf.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.plaf.UIResource;

/**
 * @author Michael Hagen
 */
public class McWinIcons extends BaseIcons {

    public static Icon getIconIcon() {
        if (iconIcon == null) {
            iconIcon = new MacIconIcon();
        }
        return iconIcon;
    }

    public static Icon getMinIcon() {
        if (minIcon == null) {
            minIcon = new MacMinIcon();
        }
        return minIcon;
    }

    public static Icon getMaxIcon() {
        if (maxIcon == null) {
            maxIcon = new MacMaxIcon();
        }
        return maxIcon;
    }

    public static Icon getCloseIcon() {
        if (closeIcon == null) {
            closeIcon = new MacCloseIcon();
        }
        return closeIcon;
    }

    public static Icon getRadioButtonIcon() {
        if (radioButtonIcon == null) {
            radioButtonIcon = new RadioButtonIcon();
        }
        return radioButtonIcon;
    }

    public static Icon getCheckBoxIcon() {
        if (checkBoxIcon == null) {
            checkBoxIcon = new CheckBoxIcon();
        }
        return checkBoxIcon;
    }

    public static Icon getThumbHorIcon() {
        if (thumbHorIcon == null) {
            thumbHorIcon = new ThumbIcon(false);
        }
        return thumbHorIcon;
    }

    public static Icon getThumbVerIcon() {
        if (thumbVerIcon == null) {
            thumbVerIcon = new ThumbIcon(false);
        }
        return thumbVerIcon;
    }

    public static Icon getThumbHorIconRollover() {
        if (thumbHorIconRollover == null) {
            thumbHorIconRollover = new ThumbIcon(true);
        }
        return thumbHorIconRollover;
    }

    public static Icon getThumbVerIconRollover() {
        if (thumbVerIconRollover == null) {
            thumbVerIconRollover = new ThumbIcon(true);
        }
        return thumbVerIconRollover;
    }

//--------------------------------------------------------------------------------------------------------
    private static class CheckBoxIcon implements Icon, UIResource, Serializable {

        private static final int GAP = 4;
        private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("mcwin/icons/small/check_symbol_16x11.png");
        private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon("mcwin/icons/small/check_symbol_disabled_16x11.png");
        private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("mcwin/icons/medium/check_symbol_18x13.png");
        private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon("mcwin/icons/medium/check_symbol_disabled_18x13.png");
        private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("mcwin/icons/large/check_symbol_20x15.png");
        private static final Icon LARGE_CHECK_DISABLED_ICON = new LazyImageIcon("mcwin/icons/large/check_symbol_disabled_20x15.png");

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (!JTattooUtilities.isLeftToRight(c)) {
                x += GAP;
            }
            int w = getIconWidth() - GAP;
            int h = getIconHeight();
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color colors[];
            Color frameColor = AbstractLookAndFeel.getFrameColor();
            if (button.isEnabled()) {
                if ((button.isRolloverEnabled() && model.isRollover())) {
                    colors = AbstractLookAndFeel.getTheme().getRolloverColors();
                } else if (!JTattooUtilities.isFrameActive(button)) {
                    colors = AbstractLookAndFeel.getTheme().getInActiveColors();
                } else if (button.isSelected()) {
                    colors = AbstractLookAndFeel.getTheme().getDefaultColors();
                } else {
                    colors = AbstractLookAndFeel.getTheme().getCheckBoxColors();
                }
            } else {
                colors = AbstractLookAndFeel.getTheme().getDisabledColors();
                frameColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 20);
            }
            JTattooUtilities.fillHorGradient(g, colors, x + 1, y + 1, w - 1, h - 1);
            g.setColor(frameColor);
            g.drawRect(x, y, w, h);

            if (button.isEnabled() && !model.isRollover() && !model.isPressed() && !model.isSelected()) {
                g.setColor(Color.white);
                g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
                g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
            }

            Icon checkIcon;
            Icon checkDisabledIcon;
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                checkIcon = SMALL_CHECK_ICON;
                checkDisabledIcon = SMALL_CHECK_DISABLED_ICON;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                checkIcon = MEDIUM_CHECK_ICON;
                checkDisabledIcon = MEDIUM_CHECK_DISABLED_ICON;
            } else {
                checkIcon = LARGE_CHECK_ICON;
                checkDisabledIcon = LARGE_CHECK_DISABLED_ICON;
            }
            if (model.isPressed() && model.isArmed()) {
                Color bc = AbstractLookAndFeel.getTheme().getSelectionBackgroundColor();
                Color fc = ColorHelper.darker(bc, 40);
                g.setColor(fc);
                g.drawRect(x + 3, y + 3, w - 6, h - 6);
                g.setColor(bc);
                g.fillRect(x + 4, y + 4, w - 7, h - 7);
            } else if (model.isSelected()) {
                int xi = x + ((w - checkIcon.getIconWidth()) / 2) + 4;
                int yi = y + ((h - checkIcon.getIconHeight()) / 2);
                if (model.isEnabled()) {
                    checkIcon.paintIcon(c, g, xi, yi);
                } else {
                    checkDisabledIcon.paintIcon(c, g, xi, yi);
                }
            }
        }

        @Override
        public int getIconWidth() {
            int w;
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                w = 13;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                w = 15;
            } else {
                w = 17;
            }
            return w + GAP;
        }

        @Override
        public int getIconHeight() {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                return 13;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                return 15;
            } else {
                return 17;
            }
        }
    }

    private static class RadioButtonIcon implements Icon, UIResource, Serializable {

        private static final int GAP = 2;

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (!JTattooUtilities.isLeftToRight(c)) {
                x += GAP;
            }
            int w = getIconWidth() - GAP;
            int h = getIconHeight();
            Graphics2D g2D = (Graphics2D) g;
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color colors[];
            if (button.isEnabled()) {
                if ((button.isRolloverEnabled() && model.isRollover()) || (model.isPressed() && model.isArmed())) {
                    colors = AbstractLookAndFeel.getTheme().getRolloverColors();
                } else if (!JTattooUtilities.isFrameActive(button)) {
                    colors = AbstractLookAndFeel.getTheme().getInActiveColors();
                } else if (button.isSelected()) {
                    colors = AbstractLookAndFeel.getTheme().getDefaultColors();
                } else {
                    colors = AbstractLookAndFeel.getTheme().getCheckBoxColors();
                }
            } else {
                colors = AbstractLookAndFeel.getTheme().getDisabledColors();
            }

            Shape savedClip = g.getClip();
            Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
            if (savedClip != null) {
                clipArea.intersect(new Area(savedClip));
            }
            g2D.setClip(clipArea);
            JTattooUtilities.fillHorGradient(g, colors, x, y, w, h);
            g2D.setClip(savedClip);

            if (button.isEnabled()) {
                g2D.setColor(AbstractLookAndFeel.getFrameColor());
            } else {
                g2D.setColor(ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 20));
            }
            Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.drawOval(x, y, w, h);

            if (model.isSelected()) {
                if (model.isEnabled()) {
                    int gv = ColorHelper.getGrayValue(colors[0]);
                    if (gv > 128) {
                        g.setColor(Color.black);
                    } else {
                        g.setColor(Color.white);
                    }
                } else {
                    g.setColor(AbstractLookAndFeel.getTheme().getDisabledForegroundColor());
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

        @Override
        public int getIconWidth() {
            int w;
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
        public int getIconHeight() {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                return 14;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                return 16;
            } else {
                return 18;
            }
        }
    }

    private static class ThumbIcon implements Icon, UIResource, Serializable {

        private final static int WIDTH = 15;
        private final static int HEIGHT = 15;
        private boolean isRollover = false;

        public ThumbIcon(boolean isRollover) {
            this.isRollover = isRollover;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            Graphics2D g2D = (Graphics2D) g;
            Color colors[];
            if (isRollover) {
                colors = AbstractLookAndFeel.getTheme().getRolloverColors();
            } else {
                if (AbstractLookAndFeel.getTheme().isBrightMode()) {
                    colors = AbstractLookAndFeel.getTheme().getButtonColors();
                } else {
                    colors = AbstractLookAndFeel.getTheme().getSelectedColors();
                }
            }

            Shape savedClip = g2D.getClip();
            if (savedClip != null) {
                Area clipArea = new Area(new Ellipse2D.Double(x + 1, y + 1, WIDTH, HEIGHT));
                clipArea.intersect(new Area(savedClip));
                g2D.setClip(clipArea);
                JTattooUtilities.fillHorGradient(g, colors, x + 1, y + 1, WIDTH, HEIGHT);
                g2D.setClip(savedClip);
            } else {
                Area ellipseArea = new Area(new Ellipse2D.Double(x + 1, y + 1, WIDTH, HEIGHT));
                g2D.setClip(ellipseArea);
                JTattooUtilities.fillHorGradient(g, colors, x, y, WIDTH, HEIGHT);
                g2D.setClip(null);
            }
            g2D.setColor(AbstractLookAndFeel.getFrameColor());
            Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2D.drawOval(x + 1, y + 1, WIDTH - 1, HEIGHT - 1);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
        }

        @Override
        public int getIconWidth() {
            return WIDTH + 2;
        }

        @Override
        public int getIconHeight() {
            return HEIGHT + 2;
        }
    }
    
} // end of class McWinIcons
