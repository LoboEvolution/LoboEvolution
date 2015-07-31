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

import org.lobobrowser.gui.*;
import org.lobobrowser.main.*;
import javax.swing.*;

/**
 * The Class LoboTestFrame.
 */
public class LoboTestFrame extends JFrame {
    
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception {
        // This optional step initializes logging so only warnings
        // are printed out.
        PlatformInit.getInstance().initLogging(false);

        // This step is necessary for extensions to work:
        PlatformInit.getInstance().init(false, false);

        // Create frame with a specific size.
        JFrame frame = new LoboTestFrame();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    public LoboTestFrame() throws Exception {
        FramePanel framePanel = new FramePanel();
        this.getContentPane().add(framePanel);
        framePanel.navigate("www.google.com");
    }
}
