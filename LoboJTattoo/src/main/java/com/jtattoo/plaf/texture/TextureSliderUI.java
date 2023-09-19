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

import java.awt.Component;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseSliderUI;

/**
 * <p>TextureSliderUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureSliderUI extends BaseSliderUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureSliderUI((JSlider) c);
	}

	/**
	 * <p>Constructor for TextureSliderUI.</p>
	 *
	 * @param slider a {@link javax.swing.JSlider} object.
	 */
	public TextureSliderUI(JSlider slider) {
		super(slider);
	}

	/** {@inheritDoc} */
	@Override
	public void paintBackground(Graphics g, JComponent c) {
		if (c.isOpaque()) {
			Component parent = c.getParent();
			if (parent != null && parent.getBackground() instanceof ColorUIResource) {
				TextureUtils.fillComponent(g, c, TextureUtils.BACKGROUND_TEXTURE_TYPE);
			} else {
				if (parent != null) {
					g.setColor(parent.getBackground());
				} else {
					g.setColor(c.getBackground());
				}
				g.fillRect(0, 0, c.getWidth(), c.getHeight());
			}
		}
	}

} // TextureSliderUI
