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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import javax.swing.JInternalFrame;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseInternalFrameTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>TextureInternalFrameTitlePane class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class TextureInternalFrameTitlePane extends BaseInternalFrameTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for TextureInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public TextureInternalFrameTitlePane(JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	protected boolean centerButtons() {
		return false;
	}

	/** {@inheritDoc} */
	@Override
	protected int getHorSpacing() {
		return AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn() ? 1 : 0;
	}

	/** {@inheritDoc} */
	@Override
	protected int getVerSpacing() {
		return AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn() ? 3 : 0;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(Graphics g) {
		TextureUtils.fillComponent(g, this, TextureUtils.WINDOW_TEXTURE_TYPE);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
	}

	/** {@inheritDoc} */
	@Override
	public void paintPalette(Graphics g) {
		TextureUtils.fillComponent(g, this, TextureUtils.WINDOW_TEXTURE_TYPE);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(Graphics g, int x, int y, String title) {
		Graphics2D g2D = (Graphics2D) g;
		Shape savedClip = g2D.getClip();
		Color fc = AbstractLookAndFeel.getWindowTitleForegroundColor();
		if (fc.equals(Color.white)) {
			Color bc = AbstractLookAndFeel.getWindowTitleColorDark();
			g2D.setColor(bc);
			JTattooUtilities.drawString(frame, g, title, x - 1, y - 1);
			g2D.setColor(ColorHelper.darker(bc, 30));
			JTattooUtilities.drawString(frame, g, title, x + 1, y + 1);
		}
		g.setColor(fc);
		JTattooUtilities.drawString(frame, g, title, x, y);

		Area clipArea = new Area(new Rectangle2D.Double(x, getHeight() / 2, getWidth(), getHeight()));
		if (savedClip != null) {
			clipArea.intersect(new Area(savedClip));
		}
		g2D.setClip(clipArea);
		g.setColor(ColorHelper.darker(fc, 20));
		JTattooUtilities.drawString(frame, g, title, x, y);

		g2D.setClip(savedClip);
	}

} // end of class TextureInternalFrameTitlePane
