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

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.Icon;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseSplitPaneDivider;

/**
 * <p>TextureSplitPaneDivider class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureSplitPaneDivider extends BaseSplitPaneDivider {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for TextureSplitPaneDivider.</p>
	 *
	 * @param ui a {@link com.jtattoo.plaf.texture.TextureSplitPaneUI} object.
	 */
	public TextureSplitPaneDivider(TextureSplitPaneUI ui) {
		super(ui);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		if (!isFlatMode()) {
			TextureUtils.fillComponent(g, this, TextureUtils.getTextureType(splitPane));

			Graphics2D g2D = (Graphics2D) g;
			Composite savedComposite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
			g2D.setComposite(alpha);

			int width = getSize().width;
			int height = getSize().height;
			int dx = 0;
			int dy = 0;
			if (width % 2 == 1) {
				dx = 1;
			}
			if (height % 2 == 1) {
				dy = 1;
			}

			Icon horBumps = null;
			Icon verBumps = null;
			if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
				AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
				horBumps = laf.getIconFactory().getSplitterHorBumpIcon();
				verBumps = laf.getIconFactory().getSplitterVerBumpIcon();
			}
			if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
				if (horBumps != null && width > horBumps.getIconWidth()) {
					if (splitPane.isOneTouchExpandable() && centerOneTouchButtons) {
						int centerY = height / 2;
						int x = (width - horBumps.getIconWidth()) / 2 + dx;
						int y = centerY - horBumps.getIconHeight() - 40;
						horBumps.paintIcon(this, g, x, y);
						y = centerY + 40;
						horBumps.paintIcon(this, g, x, y);
					} else {
						int x = (width - horBumps.getIconWidth()) / 2 + dx;
						int y = (height - horBumps.getIconHeight()) / 2;
						horBumps.paintIcon(this, g, x, y);
					}
				}
			} else {
				if (verBumps != null && height > verBumps.getIconHeight()) {
					if (splitPane.isOneTouchExpandable() && centerOneTouchButtons) {
						int centerX = width / 2;
						int x = centerX - verBumps.getIconWidth() - 40;
						int y = (height - verBumps.getIconHeight()) / 2 + dy;
						verBumps.paintIcon(this, g, x, y);
						x = centerX + 40;
						verBumps.paintIcon(this, g, x, y);
					} else {
						int x = (width - verBumps.getIconWidth()) / 2;
						int y = (height - verBumps.getIconHeight()) / 2 + dy;
						verBumps.paintIcon(this, g, x, y);
					}
				}
			}
			g2D.setComposite(savedComposite);
		}
		paintComponents(g);
	}

} // end of class TextureSplitPaneDivider
