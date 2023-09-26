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

import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.BaseScrollPaneUI;

/**
 * <p>TextureScrollPaneUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureScrollPaneUI extends BaseScrollPaneUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new TextureScrollPaneUI();
	}

	/** {@inheritDoc} */
	@Override
	public void installDefaults(final JScrollPane p) {
		super.installDefaults(p);
		p.getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
	}

	/** {@inheritDoc} */
	@Override
	public void update(final Graphics g, final JComponent c) {
		if (c.getBackground() instanceof ColorUIResource) {
			if (c.isOpaque()) {
				TextureUtils.fillComponent(g, c, TextureUtils.getTextureType(c));
			}
		} else {
			super.update(g, c);
		}
	}

} // end of class TextureScrollPaneUI
