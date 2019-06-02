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
 
package com.jtattoo.plaf.aluminium;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class AluminiumBorderFactory implements AbstractBorderFactory {

    private static AluminiumBorderFactory instance = null;

    private AluminiumBorderFactory() {
    }

    public static synchronized AluminiumBorderFactory getInstance() {
        if (instance == null) {
            instance = new AluminiumBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return AluminiumBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return AluminiumBorders.getButtonBorder();
    }

    /**
     *
     * @return
     */
    @Override
    public Border getToggleButtonBorder() {
        return AluminiumBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return AluminiumBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return AluminiumBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return AluminiumBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return AluminiumBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return AluminiumBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return AluminiumBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return AluminiumBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return AluminiumBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return AluminiumBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return AluminiumBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return AluminiumBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return AluminiumBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return AluminiumBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return AluminiumBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return AluminiumBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return AluminiumBorders.getDesktopIconBorder();
    }
    
} // end of class AluminiumBorderFactory

