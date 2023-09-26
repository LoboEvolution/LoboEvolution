/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import javax.swing.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>TextureTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureTitlePane extends BaseTitlePane {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for TextureTitlePane.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 * @param ui a {@link com.jtattoo.plaf.BaseRootPaneUI} object.
	 */
	public TextureTitlePane(final JRootPane root, final BaseRootPaneUI ui) {
		super(root, ui);
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
	public void paintText(final Graphics g, final int x, final int y, final String title) {
		final Graphics2D g2D = (Graphics2D) g;
		final Shape savedClip = g2D.getClip();
		final Color fc = AbstractLookAndFeel.getWindowTitleForegroundColor();
		if (fc.equals(Color.white)) {
			final Color bc = AbstractLookAndFeel.getWindowTitleColorDark();
			g2D.setColor(bc);
			JTattooUtilities.drawString(rootPane, g, title, x - 1, y - 1);
			g2D.setColor(ColorHelper.darker(bc, 30));
			JTattooUtilities.drawString(rootPane, g, title, x + 1, y + 1);
		}
		g.setColor(fc);

		Area clipArea = new Area(new Rectangle2D.Double(x, 0, getWidth(), getHeight() / 2));
		if (savedClip != null) {
			clipArea.intersect(new Area(savedClip));
		}
		g2D.setClip(clipArea);
		JTattooUtilities.drawString(rootPane, g, title, x, y);

		clipArea = new Area(new Rectangle2D.Double(x, getHeight() / 2, getWidth(), getHeight()));
		if (savedClip != null) {
			clipArea.intersect(new Area(savedClip));
		}
		g2D.setClip(clipArea);
		g.setColor(ColorHelper.darker(fc, 20));
		JTattooUtilities.drawString(rootPane, g, title, x, y);

		g2D.setClip(savedClip);
	}

} // end of class TextureTitlePane
