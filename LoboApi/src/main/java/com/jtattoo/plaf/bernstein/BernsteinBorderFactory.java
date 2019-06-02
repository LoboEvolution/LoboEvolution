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
package com.jtattoo.plaf.bernstein;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class BernsteinBorderFactory implements AbstractBorderFactory {

    private static BernsteinBorderFactory instance = null;

    private BernsteinBorderFactory() {
    }

    public static synchronized BernsteinBorderFactory getInstance() {
        if (instance == null) {
            instance = new BernsteinBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return BernsteinBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return BernsteinBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return BernsteinBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return BernsteinBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return BernsteinBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return BernsteinBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return BernsteinBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return BernsteinBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return BernsteinBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return BernsteinBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return BernsteinBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return BernsteinBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return BernsteinBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return BernsteinBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return BernsteinBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return BernsteinBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return BernsteinBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return BernsteinBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return BernsteinBorders.getDesktopIconBorder();
    }
    
} // end of class BernsteinBorderFactory
