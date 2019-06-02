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
 
package com.jtattoo.plaf.acryl;

import com.jtattoo.plaf.*;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

/**
 * @author Michael Hagen
 */
public class AcrylIcons extends BaseIcons {

    public static Icon getIconIcon() {
        if (iconIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                iconIcon = new MacIconIcon();
            } else {
                iconIcon = new TitleButtonIcon(TitleButtonIcon.ICON_ICON_TYP);
            }
        }
        return iconIcon;
    }

    public static Icon getMinIcon() {
        if (minIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                minIcon = new MacMinIcon();
            } else {
                minIcon = new TitleButtonIcon(TitleButtonIcon.MIN_ICON_TYP);
            }
        }
        return minIcon;
    }

    public static Icon getMaxIcon() {
        if (maxIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                maxIcon = new MacMaxIcon();
            } else {
                maxIcon = new TitleButtonIcon(TitleButtonIcon.MAX_ICON_TYP);
            }
        }
        return maxIcon;
    }

    public static Icon getCloseIcon() {
        if (closeIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                closeIcon = new MacCloseIcon();
            } else {
                closeIcon = new TitleButtonIcon(TitleButtonIcon.CLOSE_ICON_TYP);
            }
        }
        return closeIcon;
    }

    public static Icon getTreeExpandedIcon() {
        if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
            return BaseIcons.getTreeExpandedIcon();
        } else {
            if (treeExpandedIcon == null) {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    treeExpandedIcon = new LazyImageIcon("acryl/icons/small/tree_expanded_11x11.png");
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    treeExpandedIcon = new LazyImageIcon("acryl/icons/medium/tree_expanded_13x13.png");
                } else {
                    treeExpandedIcon = new LazyImageIcon("acryl/icons/large/tree_expanded_15x15.png");
                }
            }
            return treeExpandedIcon;
        }
    }
    
    public static Icon getTreeCollapsedIcon() {
        if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
            return BaseIcons.getTreeCollapsedIcon();
        } else {
            if (treeCollapsedIcon == null) {
                if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                    treeCollapsedIcon = new LazyImageIcon("acryl/icons/small/tree_collapsed_11x11.png");
                } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                    treeCollapsedIcon = new LazyImageIcon("acryl/icons/medium/tree_collapsed_13x13.png");
                } else {
                    treeCollapsedIcon = new LazyImageIcon("acryl/icons/large/tree_collapsed_15x15.png");
                }
            }
            return treeCollapsedIcon;
        }
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
        if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
            return BaseIcons.getThumbHorIcon();
        }

        if (thumbHorIcon == null) {
            thumbHorIcon = new LazyImageIcon("acryl/icons/thumb_hor.gif");
        }
        return thumbHorIcon;
    }

    public static Icon getThumbVerIcon() {
        if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
            return BaseIcons.getThumbVerIcon();
        }

        if (thumbVerIcon == null) {
            thumbVerIcon = new LazyImageIcon("acryl/icons/thumb_ver.gif");
        }
        return thumbVerIcon;
    }

    public static Icon getThumbHorIconRollover() {
        if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
            return BaseIcons.getThumbHorIconRollover();
        }

        if (thumbHorIconRollover == null) {
            thumbHorIconRollover = new LazyImageIcon("acryl/icons/thumb_hor_rollover.gif");
        }
        return thumbHorIconRollover;
    }

    public static Icon getThumbVerIconRollover() {
        if (!AbstractLookAndFeel.getControlColorLight().equals(new ColorUIResource(96, 98, 100))) {
            return BaseIcons.getThumbVerIconRollover();
        }

        if (thumbVerIconRollover == null) {
            thumbVerIconRollover = new LazyImageIcon("acryl/icons/thumb_ver_rollover.gif");
        }
        return thumbVerIconRollover;
    }

//----------------------------------------------------------------------------------------------------------------------
// inner classes    
//----------------------------------------------------------------------------------------------------------------------
    private static class TitleButtonIcon implements Icon {

        private static final Color EXTRA_LIGHT_GRAY = new Color(240, 240, 240);
        private static final Color CLOSER_COLOR_LIGHT = new Color(241, 172, 154);
        private static final Color CLOSER_COLOR_DARK = new Color(224, 56, 2);
        
        public static final int ICON_ICON_TYP = 0;
        public static final int MIN_ICON_TYP = 1;
        public static final int MAX_ICON_TYP = 2;
        public static final int CLOSE_ICON_TYP = 3;
        private int iconTyp = ICON_ICON_TYP;

        public TitleButtonIcon(int typ) {
            iconTyp = typ;
        }

        @Override
        public int getIconHeight() {
            return 20;
        }

        @Override
        public int getIconWidth() {
            return 20;
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            int w = c.getWidth();
            int h = c.getHeight();

            JButton b = (JButton) c;
            Graphics2D g2D = (Graphics2D) g;

            boolean isPressed = b.getModel().isPressed();
            boolean isArmed = b.getModel().isArmed();
            boolean isRollover = b.getModel().isRollover();

            Color cFrame = AbstractLookAndFeel.getTheme().getWindowBorderColor();
            Color cFrameInner = ColorHelper.brighter(cFrame, 60);
            Color cHi = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowTitleColorLight(), 40);
            Color cLo = ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowTitleColorDark(), 10);
            Color cShadow = Color.black;
            if (iconTyp == CLOSE_ICON_TYP) {
                cHi = CLOSER_COLOR_LIGHT;
                cLo = CLOSER_COLOR_DARK;
            }

            if (isPressed && isArmed) {
                Color cTemp = ColorHelper.darker(cLo, 10);
                cLo = ColorHelper.darker(cHi, 10);
                cHi = cTemp;
                g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
                g.fillRect(2, 2, w - 3, h - 3);
            } else if (isRollover) {
                cFrameInner = ColorHelper.brighter(cFrameInner, 50);
                if (iconTyp == CLOSE_ICON_TYP) {
                    cHi = CLOSER_COLOR_LIGHT;
                    cLo = CLOSER_COLOR_DARK;
                    cShadow = cLo;
                    g2D.setPaint(new GradientPaint(0, 0, cHi, w, h, cLo));
                    g2D.fillRect(2, 2, w - 3, h - 3);
                } else {
                    JTattooUtilities.fillHorGradient(g2D, AbstractLookAndFeel.getTheme().getRolloverColors(), 2, 2, w - 3, h - 3);
                }
            }

            g2D.setColor(cFrame);
            g2D.drawLine(1, 0, w - 2, 0);
            g2D.drawLine(1, h - 1, w - 2, h - 1);
            g2D.drawLine(0, 1, 0, h - 2);
            g2D.drawLine(w - 1, 1, w - 1, h - 2);

            g2D.setColor(cFrameInner);
            g2D.drawRect(1, 1, w - 3, h - 3);

            Icon icon = null;
            switch (iconTyp) {
                case ICON_ICON_TYP:
                    icon = new BaseIcons.IconSymbol(EXTRA_LIGHT_GRAY, cShadow, null, new Insets(0, 5, 0, 5));
                    break;
                case MIN_ICON_TYP:
                    icon = new BaseIcons.MinSymbol(EXTRA_LIGHT_GRAY, cShadow, null, new Insets(0, 4, 0, 4));
                    break;
                case MAX_ICON_TYP:
                    icon = new BaseIcons.MaxSymbol(EXTRA_LIGHT_GRAY, cShadow, null, new Insets(0, 4, 0, 4));
                    break;
                case CLOSE_ICON_TYP:
                    icon = new BaseIcons.CloseSymbol(Color.white, ColorHelper.darker(cShadow, 50), null, new Insets(0, 5, 0, 5));
                    break;
                default:
                    break;
            }
            if (icon != null) {
                icon.paintIcon(c, g, 0, 0);
            }
        }
    }

//----------------------------------------------------------------------------------------------------------------------
    private static class CheckBoxIcon implements Icon {

        private static final int GAP = 2;
        
        private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("acryl/icons/small/check_symbol_12x11.png");
        private static final Icon SMALL_CHECK_INVERSE_ICON = new LazyImageIcon("icons/small/check_symbol_10x10.png");
        //private static final Icon SMALL_CHECK_PRESSED_ICON = new LazyImageIcon("acryl/icons/small/check_symbol_pressed_10x10.png");
        private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon("icons/small/check_symbol_disabled_10x10.png");
        private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("acryl/icons/medium/check_symbol_14x13.png");
        private static final Icon MEDIUM_CHECK_INVERSE_ICON = new LazyImageIcon("icons/medium/check_symbol_12x12.png");
        //private static final Icon MEDIUM_CHECK_PRESSED_ICON = new LazyImageIcon("acryl/icons/medium/check_symbol_pressed_12x12.png");
        private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon("icons/medium/check_symbol_disabled_12x12.png");
        private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("acryl/icons/large/check_symbol_16x15.png");
        private static final Icon LARGE_CHECK_INVERSE_ICON = new LazyImageIcon("icons/large/check_symbol_14x14.png");
        //private static final Icon LARGE_CHECK_PRESSED_ICON = new LazyImageIcon("acryl/icons/large/check_symbol_pressed_14x14.png");
        private static final Icon LARGE_CHECK_DISABLED_ICON = new LazyImageIcon("icons/large/check_symbol_disabled_14x14.png");
        
        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (!JTattooUtilities.isLeftToRight(c)) {
                x += GAP;
            }
            int w = getIconWidth() - GAP;
            int h = getIconHeight();
            AbstractButton button = (AbstractButton) c;
            ButtonModel model = button.getModel();
            Color frameColor = AbstractLookAndFeel.getFrameColor();
            if (button.isEnabled()) {
                if ((button.isRolloverEnabled() && model.isRollover())) {
                    JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x + 1, y + 1, w - 2, h - 2);
                    frameColor = ColorHelper.brighter(frameColor, 30);
                } else {
                    if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x + 1, y + 1, w - 2, h - 2);
                    } else {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x + 1, y + 1, w - 2, h - 2);
                    }
                }
            } else {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x + 1, y + 1, w - 2, h - 2);
                frameColor = ColorHelper.brighter(frameColor, 40);
            }

            g.setColor(AbstractLookAndFeel.getTheme().getControlShadowColor());
            g.drawRect(x, y, w - 1, h - 1);
            g.setColor(frameColor);
            g.drawLine(x + 1, y, x + w - 2, y);
            g.drawLine(x + 1, y + h - 1, x + w - 2, y + h - 1);
            g.drawLine(x, y + 1, x, y + h - 2);
            g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 2);
            
            Icon checkIcon;
            Icon checkDisabledIcon;
            Icon checkInverseIcon;
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                checkIcon = SMALL_CHECK_ICON;
                checkDisabledIcon = SMALL_CHECK_DISABLED_ICON;
                checkInverseIcon = SMALL_CHECK_INVERSE_ICON;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                checkIcon = MEDIUM_CHECK_ICON;
                checkDisabledIcon = MEDIUM_CHECK_DISABLED_ICON;
                checkInverseIcon = MEDIUM_CHECK_INVERSE_ICON;
            } else {
                checkIcon = LARGE_CHECK_ICON;
                checkDisabledIcon = LARGE_CHECK_DISABLED_ICON;
                checkInverseIcon = LARGE_CHECK_INVERSE_ICON;
            }
            int gv = (model.isRollover() ? ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getRolloverColorDark()) : ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getControlColorDark()));
            int xi = x + ((w - checkIcon.getIconWidth()) / 2);
            int yi = y + ((h - checkIcon.getIconHeight()) / 2);
            if (model.isPressed() && model.isArmed()) {
                Color bc = gv > 128 ? AbstractLookAndFeel.getTheme().getSelectionBackgroundColor() : AbstractLookAndFeel.getTheme().getSelectionForegroundColor();
                Color fc = gv > 128 ? ColorHelper.darker(bc, 40) : ColorHelper.brighter(bc, 20);
                g.setColor(fc);
                g.drawRect(x + 4, y + 4, w - 9, h - 9);
                g.setColor(bc);
                g.fillRect(x + 5, y + 5, w - 10, h - 10);
            } else if (model.isSelected()) {
                if (!model.isEnabled()) {
                    checkDisabledIcon.paintIcon(c, g, xi + 1, yi + 1);
                } else {
                    if (gv > 128) {
                        checkInverseIcon.paintIcon(c, g, xi + 1, yi + 1);
                    } else {
                        checkIcon.paintIcon(c, g, xi, yi);
                    }
                }
            }
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

//----------------------------------------------------------------------------------------------------------------------
    private static class RadioButtonIcon implements Icon {

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
            Color frameColor = AbstractLookAndFeel.getFrameColor();
            Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            Shape savedClip = g.getClip();
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
            if (savedClip != null) {
                clipArea.intersect(new Area(savedClip));
            }
            g2D.setClip(clipArea);
            if (button.isEnabled()) {
                if ((button.isRolloverEnabled() && model.isRollover()) || (model.isPressed() && model.isArmed())) {
                    JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x, y, w, h);
                    frameColor = ColorHelper.brighter(frameColor, 30);
                } else {
                    if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x, y, w, h);
                    } else {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x, y, w, h);
                    }
                }
            } else {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x, y, w, h);
                frameColor = ColorHelper.brighter(frameColor, 40);
            }
            g2D.setClip(savedClip);
            g2D.setColor(frameColor);
            g2D.drawOval(x, y, w, h);
            if (model.isSelected()) {
                if (!model.isEnabled()) {
                    g.setColor(AbstractLookAndFeel.getTheme().getDisabledForegroundColor());
                    g.fillOval(x + (w / 2) - 2, y + (h / 2) - 2, 5, 5);
                } else {
                    int gv;
                    if (model.isRollover()) {
                        gv = ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getRolloverColorDark());
                    } else {
                        gv = ColorHelper.getGrayValue(AbstractLookAndFeel.getTheme().getControlColorDark());
                    }
                    if (gv > 128) {
                        g.setColor(Color.black);
                    } else {
                        g.setColor(Color.white);
                    }
                    if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                        g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
                    } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                        g2D.fillOval(x + 4, y + 4, w - 7, h - 7);
                    } else {
                        g2D.fillOval(x + 5, y + 5, w - 9, h - 9);
                    }
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
    
} // end of class AcrylIcons