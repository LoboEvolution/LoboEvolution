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
package com.jtattoo.plaf.hifi;

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
public class HiFiIcons extends BaseIcons {

    public static Icon getComboBoxIcon() {
        return getDownArrowIcon();
    }

    public static Icon getIconIcon() {
        if (iconIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                iconIcon = new MacIconIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                iconIcon = new BaseIcons.IconSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(-1, -1, 0, 0));
            }
        }
        return iconIcon;
    }

    public static Icon getMinIcon() {
        if (minIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                minIcon = new MacMinIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                minIcon = new BaseIcons.MinSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(-1, -1, 0, 0));
            }
        }
        return minIcon;
    }

    public static Icon getMaxIcon() {
        if (maxIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                maxIcon = new MacMaxIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                maxIcon = new BaseIcons.MaxSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(-1, -1, 0, 0));
            }
        }
        return maxIcon;
    }

    public static Icon getCloseIcon() {
        if (closeIcon == null) {
            if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
                closeIcon = new MacCloseIcon();
            } else {
                Color iconColor = AbstractLookAndFeel.getTheme().getWindowIconColor();
                Color iconShadowColor = AbstractLookAndFeel.getTheme().getWindowIconShadowColor();
                Color iconRolloverColor = AbstractLookAndFeel.getTheme().getWindowIconRolloverColor();
                closeIcon = new BaseIcons.CloseSymbol(iconColor, iconShadowColor, iconRolloverColor, new Insets(-1, -1, 0, 0));
            }
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

    public static Icon getTreeExpandedIcon() {
        if (treeExpandedIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                treeExpandedIcon = new LazyImageIcon("hifi/icons/small/tree_expanded_9x9.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                treeExpandedIcon = new LazyImageIcon("hifi/icons/medium/tree_expanded_11x11.png");
            } else {
                treeExpandedIcon = new LazyImageIcon("hifi/icons/large/tree_expanded_14x14.png");
            }
        }
        return treeExpandedIcon;
    }

    public static Icon getTreeCollapsedIcon() {
        if (treeCollapsedIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                treeCollapsedIcon = new LazyImageIcon("hifi/icons/small/tree_collapsed_9x9.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                treeCollapsedIcon = new LazyImageIcon("hifi/icons/medium/tree_collapsed_11x11.png");
            } else {
                treeCollapsedIcon = new LazyImageIcon("hifi/icons/large/tree_collapsed_14x14.png");
            }
        }
        return treeCollapsedIcon;
    }

    public static Icon getMenuArrowIcon() {
        if (menuArrowIcon == null) {
            menuArrowIcon = new LazyMenuArrowImageIcon("hifi/icons/medium/arrow_right_8x9.png", "hifi/icons/medium/arrow_left8x9.png");
        }
        return menuArrowIcon;
    }

    public static Icon getSplitterUpArrowIcon() {
        if (splitterUpArrowIcon == null) {
            splitterUpArrowIcon = new LazyImageIcon("hifi/icons/SplitterUpArrow.gif");
        }
        return splitterUpArrowIcon;
    }

    public static Icon getSplitterDownArrowIcon() {
        if (splitterDownArrowIcon == null) {
            splitterDownArrowIcon = new LazyImageIcon("hifi/icons/SplitterDownArrow.gif");
        }
        return splitterDownArrowIcon;
    }

    public static Icon getSplitterLeftArrowIcon() {
        if (splitterLeftArrowIcon == null) {
            splitterLeftArrowIcon = new LazyImageIcon("hifi/icons/SplitterLeftArrow.gif");
        }
        return splitterLeftArrowIcon;
    }

    public static Icon getSplitterRightArrowIcon() {
        if (splitterRightArrowIcon == null) {
            splitterRightArrowIcon = new LazyImageIcon("hifi/icons/SplitterRightArrow.gif");
        }
        return splitterRightArrowIcon;
    }

    public static Icon getSplitterHorBumpIcon() {
        if (splitterHorBumpIcon == null) {
            splitterHorBumpIcon = new LazyImageIcon("hifi/icons/SplitterHorBumps.gif");
        }
        return splitterHorBumpIcon;
    }

    public static Icon getSplitterVerBumpIcon() {
        if (splitterVerBumpIcon == null) {
            splitterVerBumpIcon = new LazyImageIcon("hifi/icons/SplitterVerBumps.gif");
        }
        return splitterVerBumpIcon;
    }

    public static Icon getThumbHorIcon() {
        if (thumbHorIcon == null) {
            thumbHorIcon = new LazyImageIcon("hifi/icons/thumb_hor.gif");
        }
        return thumbHorIcon;
    }

    public static Icon getThumbVerIcon() {
        if (thumbVerIcon == null) {
            thumbVerIcon = new LazyImageIcon("hifi/icons/thumb_ver.gif");
        }
        return thumbVerIcon;
    }

    public static Icon getThumbHorIconRollover() {
        if (thumbHorIconRollover == null) {
            thumbHorIconRollover = new LazyImageIcon("hifi/icons/thumb_hor_rollover.gif");
        }
        return thumbHorIconRollover;
    }

    public static Icon getThumbVerIconRollover() {
        if (thumbVerIconRollover == null) {
            thumbVerIconRollover = new LazyImageIcon("hifi/icons/thumb_ver_rollover.gif");
        }
        return thumbVerIconRollover;
    }

    public static Icon getUpArrowIcon() {
        if (upArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                upArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_up_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                upArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_up_9x8.png");
            } else {
                upArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_up_11x10.png");
            }
        }
        return upArrowIcon;
    }

    public static Icon getDownArrowIcon() {
        if (downArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                downArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_down_7x6.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                downArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_down_9x8.png");
            } else {
                downArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_down_11x10.png");
            }
        }
        return downArrowIcon;
    }

    public static Icon getLeftArrowIcon() {
        if (leftArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                leftArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_left_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                leftArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_left_8x9.png");
            } else {
                leftArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_left_10x11.png");
            }
        }
        return leftArrowIcon;
    }

    public static Icon getRightArrowIcon() {
        if (rightArrowIcon == null) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                rightArrowIcon = new LazyImageIcon("hifi/icons/small/arrow_right_6x7.png");
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                rightArrowIcon = new LazyImageIcon("hifi/icons/medium/arrow_right_8x9.png");
            } else {
                rightArrowIcon = new LazyImageIcon("hifi/icons/large/arrow_right_10x11.png");
            }
        }
        return rightArrowIcon;
    }

//----------------------------------------------------------------------------------------------------------------------
// inner classes    
//----------------------------------------------------------------------------------------------------------------------
    private static class CheckBoxIcon implements Icon, UIResource, Serializable {

        private static final int GAP = 2;
        private static final Icon SMALL_CHECK_ICON = new LazyImageIcon("hifi/icons/small/check_symbol_12x11.png");
        private static final Icon SMALL_CHECK_INVERSE_ICON = new LazyImageIcon("icons/small/check_symbol_10x10.png");
        private static final Icon SMALL_CHECK_DISABLED_ICON = new LazyImageIcon("icons/small/check_symbol_disabled_10x10.png");
        private static final Icon MEDIUM_CHECK_ICON = new LazyImageIcon("hifi/icons/medium/check_symbol_14x13.png");
        private static final Icon MEDIUM_CHECK_INVERSE_ICON = new LazyImageIcon("icons/medium/check_symbol_12x12.png");
        private static final Icon MEDIUM_CHECK_DISABLED_ICON = new LazyImageIcon("icons/medium/check_symbol_disabled_12x12.png");
        private static final Icon LARGE_CHECK_ICON = new LazyImageIcon("hifi/icons/large/check_symbol_16x15.png");
        private static final Icon LARGE_CHECK_INVERSE_ICON = new LazyImageIcon("icons/large/check_symbol_14x14.png");
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
            Graphics2D g2D = (Graphics2D) g;
            if (button.isEnabled()) {
                if ((button.isRolloverEnabled() && model.isRollover())) {
                    JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getRolloverColors(), x + 1, y + 1, w - 1, h - 1);
                } else {
                    if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getFocusColors(), x + 1, y + 1, w - 1, h - 1);
                    } else {
                        JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getCheckBoxColors(), x + 1, y + 1, w - 1, h - 1);
                    }
                }
            } else {
                JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getDisabledColors(), x + 1, y + 1, w - 1, h - 1);
            }

            Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 6);
            Color loFrameColor = ColorHelper.darker(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 50);

            g.setColor(frameColor);
            g.drawRect(x, y, w, h);
            Composite savedComposite = g2D.getComposite();
            AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
            g2D.setComposite(alpha);
            g.setColor(loFrameColor);
            g.drawLine(x + 1, y + 1, x + w - 1, y + 1);
            g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
            g2D.setComposite(savedComposite);

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
            int xi = x + ((w - checkIcon.getIconWidth()) / 2) + 1;
            int yi = y + ((h - checkIcon.getIconHeight()) / 2);
            int gv = ColorHelper.getGrayValue(AbstractLookAndFeel.getButtonForegroundColor());
            if (model.isPressed() && model.isArmed()) {
                Color bc = gv > 128 ? AbstractLookAndFeel.getTheme().getSelectionForegroundColor() : AbstractLookAndFeel.getTheme().getSelectionBackgroundColor();
                Color fc = gv > 128 ? ColorHelper.brighter(bc, 20) : ColorHelper.darker(bc, 40);
                g.setColor(fc);
                g.drawRect(x + 4, y + 4, w - 8, h - 8);
                g.setColor(bc);
                g.fillRect(x + 5, y + 5, w - 9, h - 9);
            } else if (model.isSelected()) {
                if (!model.isEnabled()) {
                    checkDisabledIcon.paintIcon(c, g, xi + 1, yi);
                } else {
                    if (gv > 128) {
                        checkIcon.paintIcon(c, g, xi, yi);
                    } else {
                        checkInverseIcon.paintIcon(c, g, xi + 1, yi + 1);
                    }
                }
            }
        }

        @Override
        public int getIconWidth() {
            int w;
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                w = 15;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                w = 17;
            } else {
                w = 19;
            }
            return w + GAP;
        }

        @Override
        public int getIconHeight() {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                return 15;
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                return 17;
            } else {
                return 19;
            }
        }
    }

//----------------------------------------------------------------------------------------------------------------------
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

            Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getButtonBackgroundColor(), 6);
            Shape savedClip = g.getClip();
            Area clipArea = new Area(new Ellipse2D.Double(x, y, w + 1, h + 1));
            if (savedClip != null) {
                clipArea.intersect(new Area(savedClip));
            }
            g2D.setClip(clipArea);
            JTattooUtilities.fillHorGradient(g, colors, x, y, w, h);
            g2D.setClip(savedClip);

            Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
            g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(frameColor);
            g.drawOval(x, y, w, h);

            if (model.isSelected()) {
                if (model.isEnabled()) {
                    Color fc = AbstractLookAndFeel.getForegroundColor();
                    if (ColorHelper.getGrayValue(colors[0]) < 128) {
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
    
} // end of class HiFiIcons
