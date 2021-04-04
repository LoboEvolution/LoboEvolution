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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>TextureLabelUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureLabelUI extends BasicLabelUI {

	private static TextureLabelUI hifiLabelUI = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(JComponent c) {
		if (hifiLabelUI == null) {
			hifiLabelUI = new TextureLabelUI();
		}
		return hifiLabelUI;
	}

	/** {@inheritDoc} */
	@Override
	protected void paintDisabledText(JLabel l, Graphics g, String s, int textX, int textY) {
		int mnemIndex = l.getDisplayedMnemonicIndex();
		Graphics2D g2D = (Graphics2D) g;
		Composite savedComposite = g2D.getComposite();
		AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
		g2D.setComposite(alpha);
		Color fc = l.getForeground();
		if (ColorHelper.getGrayValue(fc) > 164) {
			fc = ColorHelper.brighter(AbstractLookAndFeel.getDisabledForegroundColor(), 40);
			g.setColor(Color.black);
		} else {
			fc = AbstractLookAndFeel.getDisabledForegroundColor();
			g.setColor(Color.white);
		}
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY + 1);
		g2D.setComposite(savedComposite);
		g.setColor(fc);
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

	/** {@inheritDoc} */
	@Override
	protected void paintEnabledText(JLabel l, Graphics g, String s, int textX, int textY) {
		int mnemIndex = l.getDisplayedMnemonicIndex();
		Color fc = l.getForeground();
		if (AbstractLookAndFeel.getTheme().isTextShadowOn() && ColorHelper.getGrayValue(fc) > 164) {
			g.setColor(Color.black);
			JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY + 1);
		} else {
			Object sc = l.getClientProperty("shadowColor");
			if (sc instanceof Color) {
				g.setColor((Color) sc);
				JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY + 1);
			}
		}
		g.setColor(fc);
		JTattooUtilities.drawStringUnderlineCharAt(l, g, s, mnemIndex, textX, textY);
	}

} // end of class TextureLabelUI
