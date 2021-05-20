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
package com.jtattoo.plaf.smart;

import java.awt.Graphics;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseScrollButton;

/**
 * <p>SmartScrollButton class.</p>
 *
 * Author Michael Hagen
 *
 */
public class SmartScrollButton extends BaseScrollButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for SmartScrollButton.</p>
	 *
	 * @param direction a int.
	 * @param width a int.
	 */
	public SmartScrollButton(int direction, int width) {
		super(direction, width);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		if (getModel().isRollover()) {
			g.setColor(AbstractLookAndFeel.getFocusColor());
			g.drawLine(1, 1, getWidth() - 2, 1);
			g.drawLine(1, 2, getWidth() - 2, 2);
		}
	}

} // end of class SmartScrollButton
