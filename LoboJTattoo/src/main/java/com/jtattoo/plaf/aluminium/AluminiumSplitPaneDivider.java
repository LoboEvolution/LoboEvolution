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

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseSplitPaneDivider;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AluminiumSplitPaneDivider class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AluminiumSplitPaneDivider extends BaseSplitPaneDivider {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for AluminiumSplitPaneDivider.</p>
	 *
	 * @param ui a {@link com.jtattoo.plaf.aluminium.AluminiumSplitPaneUI} object.
	 */
	public AluminiumSplitPaneDivider(AluminiumSplitPaneUI ui) {
		super(ui);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		if (JTattooUtilities.isMac() || !AbstractLookAndFeel.getTheme().isBackgroundPatternOn()) {
			super.paint(g);
		} else {
			AluminiumUtils.fillComponent(g, this);
			Graphics2D g2D = (Graphics2D) g;
			Composite composite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
			g2D.setComposite(alpha);
			super.paint(g);
			g2D.setComposite(composite);
		}
	}

} // end of class AluminiumSliderUI
