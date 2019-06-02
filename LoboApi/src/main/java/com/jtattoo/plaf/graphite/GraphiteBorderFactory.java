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
package com.jtattoo.plaf.graphite;

import com.jtattoo.plaf.AbstractBorderFactory;
import javax.swing.border.Border;

/**
 * @author Michael Hagen
 */
public class GraphiteBorderFactory implements AbstractBorderFactory {

    private static GraphiteBorderFactory instance = null;

    private GraphiteBorderFactory() {
    }

    public static synchronized GraphiteBorderFactory getInstance() {
        if (instance == null) {
            instance = new GraphiteBorderFactory();
        }
        return instance;
    }

    @Override
    public Border getFocusFrameBorder() {
        return GraphiteBorders.getFocusFrameBorder();
    }

    @Override
    public Border getButtonBorder() {
        return GraphiteBorders.getButtonBorder();
    }

    @Override
    public Border getToggleButtonBorder() {
        return GraphiteBorders.getToggleButtonBorder();
    }

    @Override
    public Border getTextBorder() {
        return GraphiteBorders.getTextBorder();
    }

    @Override
    public Border getSpinnerBorder() {
        return GraphiteBorders.getSpinnerBorder();
    }

    @Override
    public Border getTextFieldBorder() {
        return GraphiteBorders.getTextFieldBorder();
    }

    @Override
    public Border getComboBoxBorder() {
        return GraphiteBorders.getComboBoxBorder();
    }

    @Override
    public Border getTableHeaderBorder() {
        return GraphiteBorders.getTableHeaderBorder();
    }

    @Override
    public Border getTableScrollPaneBorder() {
        return GraphiteBorders.getTableScrollPaneBorder();
    }

    @Override
    public Border getScrollPaneBorder() {
        return GraphiteBorders.getScrollPaneBorder();
    }

    @Override
    public Border getTabbedPaneBorder() {
        return GraphiteBorders.getTabbedPaneBorder();
    }

    @Override
    public Border getMenuBarBorder() {
        return GraphiteBorders.getMenuBarBorder();
    }

    @Override
    public Border getMenuItemBorder() {
        return GraphiteBorders.getMenuItemBorder();
    }

    @Override
    public Border getPopupMenuBorder() {
        return GraphiteBorders.getPopupMenuBorder();
    }

    @Override
    public Border getInternalFrameBorder() {
        return GraphiteBorders.getInternalFrameBorder();
    }

    @Override
    public Border getPaletteBorder() {
        return GraphiteBorders.getPaletteBorder();
    }

    @Override
    public Border getToolBarBorder() {
        return GraphiteBorders.getToolBarBorder();
    }

    @Override
    public Border getProgressBarBorder() {
        return GraphiteBorders.getProgressBarBorder();
    }

    @Override
    public Border getDesktopIconBorder() {
        return GraphiteBorders.getDesktopIconBorder();
    }
    
} // end of class GraphiteBorderFactory
