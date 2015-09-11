/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The XAMJ Project This
 * library is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
 * General Public License for more details. You should have received a copy of
 * the GNU Lesser General Public License along with this library; if not, write
 * to the Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston,
 * MA 02110-1301 USA Contact info: lobochief@users.sourceforge.net
 */
package org.lobobrowser.lobo_testing;

import java.awt.event.*;

import javax.swing.*;
import org.lobobrowser.gui.*;


/**
 * The Class TestWindow.
 */
public class TestWindow extends JFrame {
    
    
	private static final long serialVersionUID = 1L;
	/** The browser panel. */
    private final BrowserPanel browserPanel;

    public TestWindow() {
        super("Lobo Test...");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final BrowserPanel panel = new BrowserPanel();
        this.browserPanel = panel;
        this.getContentPane().add(panel);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                panel.windowClosing();
            }
        });
    }

    public static BrowserPanel newWindow() {
        TestWindow window = new TestWindow();
        window.setSize(700, 500);
        window.setVisible(true);
        return window.browserPanel;
    }
}
