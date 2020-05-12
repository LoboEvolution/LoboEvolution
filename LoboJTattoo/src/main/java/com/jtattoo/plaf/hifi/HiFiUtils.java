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
package com.jtattoo.plaf.hifi;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiUtils class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class HiFiUtils {

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 */
	public static void fillComponent(Graphics g, Component c) {
		if (AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			int w = c.getWidth();
			int h = c.getHeight();
			Point p = JTattooUtilities.getRelLocation(c);
			int y = 2 - p.y % 3;
			g.setColor(AbstractLookAndFeel.getTheme().getBackgroundColorLight());
			g.fillRect(0, 0, w, h);
			g.setColor(AbstractLookAndFeel.getTheme().getBackgroundColorDark());
			while (y < h) {
				g.drawLine(0, y, w, y);
				y += 3;
			}
		} else {
			g.setColor(c.getBackground());
			g.fillRect(0, 0, c.getWidth(), c.getHeight());
		}
	}

	private HiFiUtils() {
	}

} // end of class HiFiUtils
