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
package com.jtattoo.plaf.fast;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class FastBorderFactory implements AbstractBorderFactory {

    private static FastBorderFactory instance = null;

    private FastBorderFactory() {
    }

    public static synchronized FastBorderFactory getInstance() {
        if (instance == null) {
            instance = new FastBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return FastBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return FastBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return FastBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return FastBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return FastBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return FastBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return FastBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return FastBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return FastBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return FastBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return FastBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return FastBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return FastBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return FastBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return FastBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return FastBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return FastBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return FastBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return FastBorders.getDesktopIconBorder();
    }
    
} // end of class FastBorderFactory
