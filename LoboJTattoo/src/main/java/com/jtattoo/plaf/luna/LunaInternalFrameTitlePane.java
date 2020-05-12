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
package com.jtattoo.plaf.luna;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>LunaInternalFrameTitlePane class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class LunaInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Color FRAME_COLOR = new Color(0, 25, 207);

	/**
	 * <p>Constructor for LunaInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public LunaInternalFrameTitlePane(JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	protected int getHorSpacing() {
		return 2;
	}

	/** {@inheritDoc} */
	@Override
	protected int getVerSpacing() {
		return 5;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		if (!JTattooUtilities.isActive(this)) {
			g.setColor(ColorHelper.brighter(FRAME_COLOR, 20));
		} else {
			g.setColor(FRAME_COLOR);
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(Graphics g, int x, int y, String title) {
		if (isActive()) {
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getWindowBorderColor(), 10));
			JTattooUtilities.drawString(frame, g, title, x - 1, y - 2);
			g.setColor(ColorHelper.darker(AbstractLookAndFeel.getTheme().getWindowBorderColor(), 25));
			JTattooUtilities.drawString(frame, g, title, x + 1, y);
			g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
		}
		JTattooUtilities.drawString(frame, g, title, x, y - 1);
	}

} // end of class LunaInternalFrameTitlePane
