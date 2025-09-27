package com.jtattoo.plaf.base.icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;

import static com.jtattoo.plaf.base.BaseIcons.*;

public class MacMinIcon implements Icon, UIResource {

    @Override
    public int getIconHeight() {
        return 24;
    }

    @Override
    public int getIconWidth() {
        return 24;
    }

    @Override
    public void paintIcon(final Component c, final Graphics g, final int iconX, final int iconY) {
        int x;
        int y;
        final AbstractButton btn = (AbstractButton) c;
        final ButtonModel model = btn.getModel();
        final int w = c.getWidth();
        final int h = c.getHeight();
        final Icon minimizerIcon;
        Icon pearlIcon;
        if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                minimizerIcon = MINIMIZER_SMALL;
                pearlIcon = PEARL_GREEN_SMALL;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_SMALL;
                }
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                minimizerIcon = MINIMIZER_MEDIUM;
                pearlIcon = PEARL_GREEN_MEDIUM;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_MEDIUM;
                }
            } else {
                minimizerIcon = MINIMIZER_LARGE;
                pearlIcon = PEARL_GREEN_LARGE;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_LARGE;
                }
            }
        } else {
            if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
                minimizerIcon = MINIMIZER_SMALL;
                pearlIcon = PEARL_YELLOW_SMALL;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_SMALL;
                }
            } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
                minimizerIcon = MINIMIZER_MEDIUM;
                pearlIcon = PEARL_YELLOW_MEDIUM;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_MEDIUM;
                }
            } else {
                minimizerIcon = MINIMIZER_LARGE;
                pearlIcon = PEARL_YELLOW_LARGE;
                if (!JTattooUtilities.isActive(btn)) {
                    pearlIcon = PEARL_GREY_LARGE;
                }
            }
        }
        x = (w - pearlIcon.getIconWidth()) / 2;
        y = (h - pearlIcon.getIconHeight()) / 2;
        pearlIcon.paintIcon(c, g, x, y);
        if (model.isRollover()) {
            x += (pearlIcon.getIconWidth() - minimizerIcon.getIconWidth()) / 2;
            y += (pearlIcon.getIconHeight() - minimizerIcon.getIconHeight()) / 2;
            minimizerIcon.paintIcon(c, g, x, y);
        }
    }

} // end of class MacMinIcon
