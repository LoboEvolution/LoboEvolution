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

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSeparatorUI;

import com.jtattoo.plaf.AbstractLookAndFeel;

/**
 * <p>TextureSeparatorUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureSeparatorUI extends BasicSeparatorUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(JComponent c) {
		return new TextureSeparatorUI();
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		if (((JSeparator) c).getOrientation() == SwingConstants.VERTICAL) {
			return new Dimension(1, 0);
		} else {
			return new Dimension(0, 1);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g, JComponent c) {
		if (c != null) {
			boolean horizontal = true;
			if (c instanceof JSeparator) {
				horizontal = ((JSeparator) c).getOrientation() == SwingConstants.HORIZONTAL;
			}
			if (horizontal) {
				int w = c.getWidth();
				g.setColor(AbstractLookAndFeel.getFrameColor());
				g.drawLine(0, 0, w, 0);
			} else {
				int h = c.getHeight();
				g.setColor(AbstractLookAndFeel.getFrameColor());
				g.drawLine(0, 0, 0, h);
			}
		}
	}
}
