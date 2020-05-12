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
package com.jtattoo.plaf.fast;

import java.awt.Graphics;

import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>FastInternalFrameTitlePane class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class FastInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for FastInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public FastInternalFrameTitlePane(JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleBackgroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor());
		}
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void paintPalette(Graphics g) {
		if (JTattooUtilities.isFrameActive(this)) {
			g.setColor(AbstractLookAndFeel.getWindowTitleBackgroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleBackgroundColor());
		}
		g.fillRect(0, 0, getWidth(), getHeight());
	}

} // end of class FastInternalFrameTitlePane
