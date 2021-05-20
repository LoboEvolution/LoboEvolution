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
package com.jtattoo.plaf.mcwin;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>McWinUtils class.</p>
 *
 * Author Michael Hagen
 *
 */
public final class McWinUtils {

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

	/**
	 * <p>fillComponent.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param c a {@link java.awt.Component} object.
	 * @param colors an array of {@link java.awt.Color} objects.
	 */
	public static void fillComponent(Graphics g, Component c, Color[] colors) {
		int w = c.getWidth();
		int h = c.getHeight();
		JTattooUtilities.fillHorGradient(g, colors, 0, 0, w, h);
		if (AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			Point p = JTattooUtilities.getRelLocation(c);
			int y = 2 - p.y % 3;
			Color lc = AbstractLookAndFeel.getTheme().getBackgroundColorDark();
			Color lineColor = ColorHelper.brighter(ColorHelper.median(lc, colors[colors.length - 1]), 50);
			while (y < h) {
				g.setColor(lineColor);
				g.drawLine(0, y, w, y);
				lineColor = ColorHelper.darker(lineColor, 1.5);
				y += 3;
			}
		}
	}

	private McWinUtils() {
	}

} // end of class McWinUtils
