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
 
package com.jtattoo.plaf.aero;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class AeroBorderFactory implements AbstractBorderFactory {
    
    private static AeroBorderFactory instance = null;
    
    private AeroBorderFactory() {
    }
    
    public static synchronized AeroBorderFactory getInstance() {
        if (instance == null)
            instance = new AeroBorderFactory();
        return instance;
    }
    
    @Override
    public Border getFocusFrameBorder() {
        return AeroBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return AeroBorders.getButtonBorder(); 
    }
    
    @Override
    public Border getToggleButtonBorder() {
        return AeroBorders.getToggleButtonBorder(); 
    }
    
    @Override
    public Border getTextBorder() {
        return AeroBorders.getTextBorder(); 
    }
    
    @Override
    public Border getSpinnerBorder() {
        return AeroBorders.getSpinnerBorder();
    }
    
    @Override
    public Border getTextFieldBorder() {
        return AeroBorders.getTextFieldBorder(); 
    }
    
    @Override
    public Border getComboBoxBorder() {
        return AeroBorders.getComboBoxBorder(); 
    }
    
    @Override
    public Border getTableHeaderBorder() {
        return AeroBorders.getTableHeaderBorder(); 
    }
    
    @Override
    public Border getTableScrollPaneBorder() {
        return AeroBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return AeroBorders.getScrollPaneBorder(); 
    }
    
    @Override
    public Border getTabbedPaneBorder() {
        return AeroBorders.getTabbedPaneBorder(); 
    }
    
    @Override
    public Border getMenuBarBorder() {
        return AeroBorders.getMenuBarBorder(); 
    }
    
    @Override
    public Border getMenuItemBorder() {
        return AeroBorders.getMenuItemBorder(); 
    }
    
    @Override
    public Border getPopupMenuBorder() {
        return AeroBorders.getPopupMenuBorder(); 
    }
    
    @Override
    public Border getInternalFrameBorder() {
        return AeroBorders.getInternalFrameBorder(); 
    }
    
    @Override
    public Border getPaletteBorder() {
        return AeroBorders.getPaletteBorder(); 
    }
    
    @Override
    public Border getToolBarBorder() {
        return AeroBorders.getToolBarBorder(); 
    }
    
    @Override
    public Border getProgressBarBorder() {
        return AeroBorders.getProgressBarBorder(); 
    }
    
    @Override
    public Border getDesktopIconBorder() {
        return AeroBorders.getDesktopIconBorder(); 
    }
    
} // end of class AeroBorderFactory

