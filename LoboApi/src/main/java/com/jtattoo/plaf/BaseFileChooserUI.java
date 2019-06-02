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

import java.awt.Dimension;
import java.awt.Window;
import java.io.File;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.filechooser.FileView;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalFileChooserUI;

/**
 * @author Michael Hagen
 */
public class BaseFileChooserUI extends MetalFileChooserUI {

    private FileView fileView = null;

    // Preferred and Minimum sizes for the dialog box
    private static final int PREF_WIDTH = 580;
    private static final int PREF_HEIGHT = 340;
    private static final Dimension PREF_SIZE = new Dimension(PREF_WIDTH, PREF_HEIGHT);
    
    private AncestorListener ancestorListener = null;

    public BaseFileChooserUI(JFileChooser fileChooser) {
        super(fileChooser);
        fileView = new BaseFileView();
    }

    public static ComponentUI createUI(JComponent c) {
        return new BaseFileChooserUI((JFileChooser) c);
    }

    @Override
    protected void installListeners(JFileChooser fc) {
        super.installListeners(fc);
        ancestorListener = new AncestorListener() {

            @Override
            public void ancestorAdded(AncestorEvent event) {
                Window w = SwingUtilities.getWindowAncestor(getFileChooser());
                if (w != null) {
                    w.setMinimumSize(getPreferredSize(getFileChooser()));
                }
            }

            @Override
            public void ancestorRemoved(AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(AncestorEvent event) {
            }
        };
                
        fc.addAncestorListener(ancestorListener);
    }

    @Override
    protected void uninstallListeners(JFileChooser fc) {
        super.uninstallListeners(fc); 
        fc.removeAncestorListener(ancestorListener);
    }

    /**
     * Returns the preferred size of the specified
     * <code>JFileChooser</code>.
     * The preferred size is at least as large,
     * in both height and width,
     * as the preferred size recommended
     * by the file chooser's layout manager.
     *
     * @param c  a <code>JFileChooser</code>
     * @return   a <code>Dimension</code> specifying the preferred
     *           width and height of the file chooser
     */
    @Override
    public Dimension getPreferredSize(JComponent c) {
        int prefWidth = PREF_SIZE.width;
        Dimension d = c.getLayout().preferredLayoutSize(c);
        if (d != null) {
            return new Dimension(d.width < prefWidth ? prefWidth : d.width,
                    d.height < PREF_SIZE.height ? PREF_SIZE.height : d.height);
        } else {
            return new Dimension(prefWidth, PREF_SIZE.height);
        }
    }

    @Override
    public FileView getFileView(JFileChooser fc) {
        return fileView;
    }

//------------------------------------------------------------------------------    
    protected class BaseFileView extends BasicFileView {

        @Override
        public Icon getIcon(File f) {
            Icon icon = getCachedIcon(f);
            if (icon != null) {
                return icon;
            }
            if (f != null) {
                icon = getFileChooser().getFileSystemView().getSystemIcon(f);
            }
            if (icon == null) {
                icon = super.getIcon(f);
            }
            cacheIcon(f, icon);
            return icon;
        }
        
    } // end of class BaseFileView
    
} // end of class BaseFileChooserUI
