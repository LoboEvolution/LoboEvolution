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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRadioButtonUI;

/**
 * <p>TextureRadioButtonUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureRadioButtonUI extends BaseRadioButtonUI {

	private static TextureRadioButtonUI radioButtonUI = null;

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		if (radioButtonUI == null) {
			radioButtonUI = new TextureRadioButtonUI();
		}
		return radioButtonUI;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(final Graphics g, final JComponent c) {
		if (c.isOpaque()) {
			if (c.getBackground() instanceof ColorUIResource
					&& c.getBackground().equals(AbstractLookAndFeel.getBackgroundColor())) {
				TextureUtils.fillComponent(g, c, TextureUtils.BACKGROUND_TEXTURE_TYPE);
			} else {
				g.setColor(c.getBackground());
				g.fillRect(0, 0, c.getWidth(), c.getHeight());
			}
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintFocus(final Graphics g, final Rectangle t, final Dimension d) {
		g.setColor(AbstractLookAndFeel.getFocusColor());
		BasicGraphicsUtils.drawDashedRect(g, t.x - 3, t.y - 1, t.width + 6, t.height + 2);
		BasicGraphicsUtils.drawDashedRect(g, t.x - 2, t.y, t.width + 4, t.height);
	}

} // end of class TextureRadioButtonUI
