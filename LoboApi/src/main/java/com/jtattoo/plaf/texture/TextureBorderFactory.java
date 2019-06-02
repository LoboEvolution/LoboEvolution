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
package com.jtattoo.plaf.texture;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class TextureBorderFactory implements AbstractBorderFactory {

    private static TextureBorderFactory instance = null;

    private TextureBorderFactory() {
    }

    public static synchronized TextureBorderFactory getInstance() {
        if (instance == null) {
            instance = new TextureBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return TextureBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return TextureBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return TextureBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return TextureBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return TextureBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return TextureBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return TextureBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return TextureBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return TextureBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return TextureBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return TextureBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return TextureBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return TextureBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return TextureBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return TextureBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return TextureBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return TextureBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return TextureBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return TextureBorders.getDesktopIconBorder();
    }
    
} // end of class TextureBorderFactory
