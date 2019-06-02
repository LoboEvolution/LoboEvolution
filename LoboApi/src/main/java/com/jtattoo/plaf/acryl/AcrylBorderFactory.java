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

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class AcrylBorderFactory implements AbstractBorderFactory {

    private static AcrylBorderFactory instance = null;

    private AcrylBorderFactory() {
    }

    public static synchronized AcrylBorderFactory getInstance() {
        if (instance == null) {
            instance = new AcrylBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return AcrylBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return AcrylBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return AcrylBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return AcrylBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return AcrylBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return AcrylBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return AcrylBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return AcrylBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return AcrylBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return AcrylBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return AcrylBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return AcrylBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return AcrylBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return AcrylBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return AcrylBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return AcrylBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return AcrylBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return AcrylBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return AcrylBorders.getDesktopIconBorder();
    }
    
} // end of class AcrylBorderFactory

