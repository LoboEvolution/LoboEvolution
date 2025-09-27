package com.jtattoo.plaf.base.icon;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;

import javax.swing.*;
import javax.swing.plaf.UIResource;
import java.awt.*;

import static com.jtattoo.plaf.base.BaseIcons.*;

public class MacCloseIcon implements Icon, UIResource {

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
        final Icon closerIcon;
        Icon pearlIcon;
        if (AbstractLookAndFeel.getTheme().isSmallFontSize()) {
            closerIcon = CLOSER_SMALL;
            pearlIcon = PEARL_RED_SMALL;
            if (!JTattooUtilities.isActive(btn)) {
                pearlIcon = PEARL_GREY_SMALL;
            }
        } else if (AbstractLookAndFeel.getTheme().isMediumFontSize()) {
            closerIcon = CLOSER_MEDIUM;
            pearlIcon = PEARL_RED_MEDIUM;
            if (!JTattooUtilities.isActive(btn)) {
                pearlIcon = PEARL_GREY_MEDIUM;
            }
        } else {
            closerIcon = CLOSER_LARGE;
            pearlIcon = PEARL_RED_LARGE;
            if (!JTattooUtilities.isActive(btn)) {
                pearlIcon = PEARL_GREY_LARGE;
            }
        }
        x = (w - pearlIcon.getIconWidth()) / 2;
        y = (h - pearlIcon.getIconHeight()) / 2;
        pearlIcon.paintIcon(c, g, x, y);
        if (model.isRollover()) {
            x += (pearlIcon.getIconWidth() - closerIcon.getIconWidth()) / 2;
            y += (pearlIcon.getIconHeight() - closerIcon.getIconHeight()) / 2;
            closerIcon.paintIcon(c, g, x, y);
        }
    }

} // end of class MacCloseIcon
