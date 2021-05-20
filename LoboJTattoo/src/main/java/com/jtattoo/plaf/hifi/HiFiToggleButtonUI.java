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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseToggleButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiToggleButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class HiFiToggleButtonUI extends BaseToggleButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new HiFiToggleButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintText(Graphics g, AbstractButton b, Rectangle textRect, String text) {
		ButtonModel model = b.getModel();
		FontMetrics fm = JTattooUtilities.getFontMetrics(b, g, b.getFont());
		int mnemIndex = b.getDisplayedMnemonicIndex();
		int offs = 0;
		if (model.isArmed() && model.isPressed()) {
			offs = 1;
		}

		Graphics2D g2D = (Graphics2D) g;
		Composite composite = g2D.getComposite();
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
		g2D.setComposite(alpha);
		Color foreground = b.getForeground();
		Color background = b.getBackground();
		if (model.isPressed() && model.isArmed() || model.isSelected()) {
			if (foreground instanceof ColorUIResource && background instanceof ColorUIResource) {
				foreground = AbstractLookAndFeel.getTheme().getPressedForegroundColor();
			}
		}
		if (!model.isEnabled()) {
			foreground = AbstractLookAndFeel.getTheme().getDisabledForegroundColor();
		}
		if (ColorHelper.getGrayValue(foreground) > 128) {
			g2D.setColor(Color.black);
		} else {
			g2D.setColor(Color.white);
		}
		JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs + 1,
				textRect.y + offs + fm.getAscent() + 1);
		g2D.setComposite(composite);
		g2D.setColor(foreground);
		JTattooUtilities.drawStringUnderlineCharAt(b, g, text, mnemIndex, textRect.x + offs,
				textRect.y + offs + fm.getAscent());
	}

} // end of class HiFiToggleButtonUI
