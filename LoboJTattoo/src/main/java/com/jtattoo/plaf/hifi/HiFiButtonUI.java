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
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseButtonUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>HiFiButtonUI class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class HiFiButtonUI extends BaseButtonUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(JComponent c) {
		return new HiFiButtonUI();
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(Graphics g, AbstractButton b) {
		if (!b.isContentAreaFilled() || b.getParent() instanceof JMenuBar) {
			return;
		}
		int width = b.getWidth();
		int height = b.getHeight();
		Graphics2D g2D = (Graphics2D) g;
		Shape savedClip = g.getClip();
		if (b.getBorder() != null && b.isBorderPainted() && b.getBorder() instanceof UIResource) {
			Area clipArea = new Area(new Rectangle2D.Double(1, 1, width - 2, height - 2));
			if (savedClip != null) {
				clipArea.intersect(new Area(savedClip));
			}
			g2D.setClip(clipArea);
		}
		super.paintBackground(g, b);
		g2D.setClip(savedClip);
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
		if (background instanceof ColorUIResource) {
			if (model.isPressed() && model.isArmed()) {
				foreground = AbstractLookAndFeel.getTheme().getSelectionForegroundColor();
			} else if (model.isRollover()) {
				foreground = AbstractLookAndFeel.getTheme().getRolloverForegroundColor();
			}
		}
		if (!model.isEnabled()) {
			foreground = AbstractLookAndFeel.getTheme().getDisabledForegroundColor();
		}
		if (ColorHelper.getGrayValue(foreground) > 64) {
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

} // end of class HiFiButtonUI
