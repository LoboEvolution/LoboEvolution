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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseToggleButtonUI;

/**
 * <p>McWinToggleButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class McWinToggleButtonUI extends BaseToggleButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new McWinToggleButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(Graphics g, AbstractButton b) {
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}
		super.paintBackground(g, b);
		int width = b.getWidth();
		int height = b.getHeight();
		Graphics2D g2D = (Graphics2D) g;
		Composite composite = g2D.getComposite();
		g2D.setColor(Color.lightGray);
		g2D.drawRect(0, 0, width - 2, height - 1);
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
		g2D.setComposite(alpha);
		g2D.setColor(Color.white);
		g2D.drawLine(width - 1, 0, width - 1, height - 1);
		g2D.setComposite(composite);
	}

} // end of class McWinToggleButtonUI
