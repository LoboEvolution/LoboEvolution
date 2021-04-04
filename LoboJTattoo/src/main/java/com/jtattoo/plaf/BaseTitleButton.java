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

package com.jtattoo.plaf;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * <p>BaseTitleButton class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseTitleButton extends NoFocusButton {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private float alpha = 1.0f;

	/**
	 * <p>Constructor for BaseTitleButton.</p>
	 *
	 * @param action a {@link javax.swing.Action} object.
	 * @param accessibleName a {@link java.lang.String} object.
	 * @param icon a {@link javax.swing.Icon} object.
	 * @param alpha a float.
	 */
	public BaseTitleButton(Action action, String accessibleName, Icon icon, float alpha) {
		setContentAreaFilled(false);
		setBorderPainted(false);
		setAction(action);
		setText(null);
		setIcon(icon);
		putClientProperty("paintActive", Boolean.TRUE);
		getAccessibleContext().setAccessibleName(accessibleName);
		this.alpha = Math.max(0.2f, alpha);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(Graphics g) {
		if (JTattooUtilities.isActive(this) || alpha >= 1.0) {
			super.paint(g);
		} else {
			Graphics2D g2D = (Graphics2D) g;
			Composite savedComposite = g2D.getComposite();
			AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha);
			g2D.setComposite(alphaComposite);
			super.paint(g);
			g2D.setComposite(savedComposite);
		}
	}

} // end of class BaseTitleButton
