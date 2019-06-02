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
package com.jtattoo.plaf.graphite;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseTabbedPaneUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 * @author Michael Hagen
 */
public class GraphiteTabbedPaneUI extends BaseTabbedPaneUI {

    private Color sepColors[] = null;

    public static ComponentUI createUI(JComponent c) {
        return new GraphiteTabbedPaneUI();
    }

    @Override
    protected void installComponents() {
        simpleButtonBorder = true;
        super.installComponents();
    }

    @Override
    protected Color getLoBorderColor(int tabIndex) {
        if ((tabIndex == tabPane.getSelectedIndex()) && tabPane.getBackgroundAt(tabIndex) instanceof ColorUIResource) {
            return AbstractLookAndFeel.getControlColorDark();
        }
        return AbstractLookAndFeel.getControlShadow();
    }

    @Override
    protected Color[] getContentBorderColors(int tabPlacement) {
        if (sepColors == null) {
            sepColors = new Color[5];
            sepColors[0] = getLoBorderColor(0);
            sepColors[1] = AbstractLookAndFeel.getControlColorDark();
            sepColors[2] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 4);
            sepColors[3] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 8);
            sepColors[4] = ColorHelper.darker(AbstractLookAndFeel.getControlColorDark(), 12);
        }
        return sepColors;
    }

    @Override
    protected Font getTabFont(boolean isSelected) {
        if (isSelected) {
            return super.getTabFont(isSelected).deriveFont(Font.BOLD);
        } else {
            return super.getTabFont(isSelected);
        }
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        if (AbstractLookAndFeel.getTheme().isTextShadowOn()) {
            if (isSelected && tabPane.isEnabledAt(tabIndex) && (tabPane.getBackgroundAt(tabIndex) instanceof ColorUIResource) && (getTextViewForTab(tabIndex) == null)) {
                g.setFont(font);
                Color selColor = AbstractLookAndFeel.getTabSelectionForegroundColor();
                if (ColorHelper.getGrayValue(selColor) > 164) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(Color.white);
                }
                int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
                JTattooUtilities.drawStringUnderlineCharAt(tabPane, g, title, mnemIndex, textRect.x, textRect.y + 1 + metrics.getAscent());
            }
        }
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        if (tabPane.isRequestFocusEnabled() && tabPane.hasFocus() && isSelected && tabIndex >= 0 && textRect.width > 8) {
            g.setColor(AbstractLookAndFeel.getTheme().getFocusColor());
            BasicGraphicsUtils.drawDashedRect(g, textRect.x - 4, textRect.y, textRect.width + 8, textRect.height);
            BasicGraphicsUtils.drawDashedRect(g, textRect.x - 3, textRect.y + 1, textRect.width + 6, textRect.height - 2);
        }
    }

} // edn of class GraphiteTabbedPaneUI
