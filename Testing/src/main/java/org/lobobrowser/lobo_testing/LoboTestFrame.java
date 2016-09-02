/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 2 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    License along with this library; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.lobo_testing;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.lobobrowser.gui.FramePanel;
import org.lobobrowser.main.PlatformInit;

/**
 * The Class LoboTestFrame.
 */
public class LoboTestFrame extends JFrame {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception {
		// This optional step initializes logging so only.warns
		// are printed out.
		PlatformInit.getInstance().initLogging();

		// This step is necessary for extensions to work:
		PlatformInit.getInstance().init(false);

		// Create frame with a specific size.
		JFrame frame = new LoboTestFrame();
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setVisible(true);
	}

	public LoboTestFrame() throws Exception {
		FramePanel framePanel = new FramePanel();
		this.getContentPane().add(framePanel);
		framePanel.navigate("www.google.com");
	}
}
