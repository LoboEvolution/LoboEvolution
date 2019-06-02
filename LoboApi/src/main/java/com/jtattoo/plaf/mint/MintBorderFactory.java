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
package com.jtattoo.plaf.mint;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class MintBorderFactory implements AbstractBorderFactory {

    private static MintBorderFactory instance = null;

    private MintBorderFactory() {
    }

    public static synchronized MintBorderFactory getInstance() {
        if (instance == null) {
            instance = new MintBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return MintBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return MintBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return MintBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return MintBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return MintBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return MintBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return MintBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return MintBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return MintBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return MintBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return MintBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return MintBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return MintBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return MintBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return MintBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return MintBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return MintBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return MintBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return MintBorders.getDesktopIconBorder();
    }
    
} // end of class MintBorderFactory
