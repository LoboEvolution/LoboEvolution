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
package com.jtattoo.plaf.mcwin;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class McWinBorderFactory implements AbstractBorderFactory {

    private static McWinBorderFactory instance = null;

    private McWinBorderFactory() {
    }

    public static synchronized McWinBorderFactory getInstance() {
        if (instance == null) {
            instance = new McWinBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return McWinBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return McWinBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return McWinBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return McWinBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return McWinBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return McWinBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return McWinBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return McWinBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return McWinBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return McWinBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return McWinBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return McWinBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return McWinBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return McWinBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return McWinBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return McWinBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return McWinBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return McWinBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return McWinBorders.getDesktopIconBorder();
    }
    
} // end of class McWinBorderFactory
