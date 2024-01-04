/*
 * MIT License
 *
 * Copyright (c) 2014 - 2024 LoboEvolution
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 * Contact info: ivan.difrancesco@yahoo.it
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
 * Author Michael Hagen
 *
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
	public TextureInternalFrameTitlePane(final JInternalFrame f) {
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
	public void paintBackground(final Graphics g) {
		TextureUtils.fillComponent(g, this, TextureUtils.WINDOW_TEXTURE_TYPE);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
	}

	/** {@inheritDoc} */
	@Override
	public void paintPalette(final Graphics g) {
		TextureUtils.fillComponent(g, this, TextureUtils.WINDOW_TEXTURE_TYPE);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(final Graphics g, final int x, final int y, final String title) {
		final Graphics2D g2D = (Graphics2D) g;
		final Shape savedClip = g2D.getClip();
		final Color fc = AbstractLookAndFeel.getWindowTitleForegroundColor();
		if (fc.equals(Color.white)) {
			final Color bc = AbstractLookAndFeel.getWindowTitleColorDark();
			g2D.setColor(bc);
			JTattooUtilities.drawString(frame, g, title, x - 1, y - 1);
			g2D.setColor(ColorHelper.darker(bc, 30));
			JTattooUtilities.drawString(frame, g, title, x + 1, y + 1);
		}
		g.setColor(fc);
		JTattooUtilities.drawString(frame, g, title, x, y);

		final Area clipArea = new Area(new Rectangle2D.Double(x, getHeight() / 2, getWidth(), getHeight()));
		if (savedClip != null) {
			clipArea.intersect(new Area(savedClip));
		}
		g2D.setClip(clipArea);
		g.setColor(ColorHelper.darker(fc, 20));
		JTattooUtilities.drawString(frame, g, title, x, y);

		g2D.setClip(savedClip);
	}

} // end of class TextureInternalFrameTitlePane
