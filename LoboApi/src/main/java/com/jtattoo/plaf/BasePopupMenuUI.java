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

package com.jtattoo.plaf;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPopupMenuUI;

/**
 * @author Michael Hagen
 */
public class BasePopupMenuUI extends BasicPopupMenuUI {

    protected static Robot robot = null;
    protected BufferedImage screenImage = null;
    protected MyPopupMenuListener myPopupListener = null;

    public static ComponentUI createUI(JComponent c) {
        return new BasePopupMenuUI();
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        popupMenu.setOpaque(false);
        popupMenu.setLightWeightPopupEnabled(false);
    }

    @Override
    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        c.setOpaque(true);
    }

    @Override
    public void installListeners() {
        super.installListeners();
        if (!isMenuOpaque()) {
            myPopupListener = new MyPopupMenuListener(this);
            popupMenu.addPopupMenuListener(myPopupListener);
        }
    }

    @Override
    public void uninstallListeners() {
        if (!isMenuOpaque()) {
            popupMenu.removePopupMenuListener(myPopupListener);
        }
        super.uninstallListeners();
    }

    private boolean isMenuOpaque() {
        return (AbstractLookAndFeel.getTheme().isMenuOpaque() || (getRobot() == null));
    }

    private Robot getRobot() {
        if (robot == null) {
            try {
                robot = new Robot();
            } catch (AWTException ex) {
            }
        }
        return robot;
    }

    @Override
    public Popup getPopup(JPopupMenu popupMenu, int x, int y) {
        Popup popup = super.getPopup(popupMenu, x, y);
        if (!isMenuOpaque()) {
            try {
                Dimension size = popupMenu.getPreferredSize();
                if (size.width > 0 && size.height > 0) {
                    Rectangle screenRect = new Rectangle(x, y, size.width, size.height);
                    screenImage = getRobot().createScreenCapture(screenRect);
                }
                for (int i = 0; i < popupMenu.getComponentCount(); i++) {
                    if (popupMenu.getComponent(i) instanceof JPanel) {
                        JPanel panel = (JPanel)popupMenu.getComponent(i);
                        panel.setOpaque(true);
                    }
                }
            } catch (Exception ex) {
                screenImage = null;
            }
        }
        return popup;
    }

    private void resetScreenImage() {
        screenImage = null;
    }

    @Override
    public void update(Graphics g, JComponent c) {
        if (screenImage != null) {
            g.drawImage(screenImage, 0, 0, null);
        } else {
            g.setColor(AbstractLookAndFeel.getMenuBackgroundColor());
            g.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
    }

//----------------------------------------------------------------------------------------    
// inner classes    
//----------------------------------------------------------------------------------------    
    public static class MyPopupMenuListener implements PopupMenuListener {

        private BasePopupMenuUI popupMenuUI = null;

        public MyPopupMenuListener(BasePopupMenuUI aPopupMenuUI) {
            popupMenuUI = aPopupMenuUI;
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            if (popupMenuUI.screenImage != null) {
                JPopupMenu popup = (JPopupMenu) e.getSource();
                JRootPane root = popup.getRootPane();
                if (popup.isShowing() && root.isShowing()) {
                    Point ptPopup = popup.getLocationOnScreen();
                    Point ptRoot = root.getLocationOnScreen();
                    Graphics g = popup.getRootPane().getGraphics();
                    g.drawImage(popupMenuUI.screenImage, ptPopup.x - ptRoot.x, ptPopup.y - ptRoot.y, null);
                    popupMenuUI.resetScreenImage();
                }
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }
        
    } // end of class MyPopupMenuListener
    
} // end of class BasePopupMenuUI

