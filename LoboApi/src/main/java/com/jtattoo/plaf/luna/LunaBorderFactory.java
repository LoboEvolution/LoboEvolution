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
package com.jtattoo.plaf.luna;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class LunaBorderFactory implements AbstractBorderFactory {

    private static LunaBorderFactory instance = null;

    private LunaBorderFactory() {
    }

    public static synchronized LunaBorderFactory getInstance() {
        if (instance == null) {
            instance = new LunaBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return LunaBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return LunaBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return LunaBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return LunaBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return LunaBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return LunaBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return LunaBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return LunaBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return LunaBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return LunaBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return LunaBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return LunaBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return LunaBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return LunaBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return LunaBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return LunaBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return LunaBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return LunaBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return LunaBorders.getDesktopIconBorder();
    }
    
} // end of class LunaBorderFactory
