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

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class HiFiBorderFactory implements AbstractBorderFactory {

    private static HiFiBorderFactory instance = null;

    private HiFiBorderFactory() {
    }

    public static synchronized HiFiBorderFactory getInstance() {
        if (instance == null) {
            instance = new HiFiBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return HiFiBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return HiFiBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return HiFiBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return HiFiBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return HiFiBorders.getSpinnerBorder();
    }

    public Border getTextFieldBorder() {
        return HiFiBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return HiFiBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return HiFiBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return HiFiBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return HiFiBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return HiFiBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return HiFiBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return HiFiBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return HiFiBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return HiFiBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return HiFiBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return HiFiBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return HiFiBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return HiFiBorders.getDesktopIconBorder();
    }
    
} // end of class HiFiBorderFactory
