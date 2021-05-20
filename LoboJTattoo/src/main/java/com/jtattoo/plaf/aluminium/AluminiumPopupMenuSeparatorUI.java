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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

/**
 * <p>AluminiumPopupMenuSeparatorUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumPopupMenuSeparatorUI extends BasicSeparatorUI {

	private static final Dimension SIZE = new Dimension(1, 1);

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AluminiumPopupMenuSeparatorUI();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		return SIZE;
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g, final JComponent c) {
		if (c != null) {
			boolean horizontal = true;
			if (c instanceof JSeparator) {
				JSeparator sep = (JSeparator) c;
				horizontal = sep.getOrientation() == SwingConstants.HORIZONTAL;
			}
			if (horizontal) {
				g.setColor(Color.lightGray);
				g.drawLine(0, 0, c.getWidth(), 0);
			} else {
				g.setColor(Color.lightGray);
				g.drawLine(0, 0, 0, c.getHeight());
			}
		}
	}

} // end of class AluminiumPopupMenuSeparatorUI
