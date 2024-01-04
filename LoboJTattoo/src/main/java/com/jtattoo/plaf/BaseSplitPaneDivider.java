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

package com.jtattoo.plaf;

import lombok.Getter;
import lombok.Setter;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/**
 * <p>BaseSplitPaneDivider class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseSplitPaneDivider extends BasicSplitPaneDivider {

	/**
	 * Used to layout a BasicSplitPaneDivider. Layout for the divider
	 * involves appropriately moving the left/right buttons around.
	 */
	protected class MyDividerLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(final String string, final Component c) {
		}

		@Override
		public void layoutContainer(final Container c) {
			if (leftButton != null && rightButton != null && c == BaseSplitPaneDivider.this) {
				if (splitPane.isOneTouchExpandable()) {
					final Insets insets = getInsets();
					int blockSize = 11;
					int xOffs = 0;
					int yOffs = 0;
					if (centerOneTouchButtons) {
						blockSize = 13;
						xOffs = (getWidth() - 2 * blockSize) / 2 - blockSize;
						yOffs = (getHeight() - 2 * blockSize) / 2 - blockSize;
					}

					if (orientation == JSplitPane.VERTICAL_SPLIT) {
						final int extraX = insets != null ? insets.left : 0;
						if (insets != null) {
							blockSize -= insets.top + insets.bottom;
							blockSize = Math.max(blockSize, 0);
						}
						final int y = (c.getSize().height - blockSize) / 2;
						leftButton.setBounds(xOffs + extraX, y, blockSize * 2, blockSize);
						rightButton.setBounds(xOffs + extraX + blockSize * 2 + 1, y, blockSize * 2, blockSize);
					} else {
						final int extraY = insets != null ? insets.top : 0;
						if (insets != null) {
							blockSize -= insets.left + insets.right;
							blockSize = Math.max(blockSize, 0);
						}
						final int x = (c.getSize().width - blockSize) / 2;
						leftButton.setBounds(x, yOffs + extraY, blockSize, blockSize * 2);
						rightButton.setBounds(x, yOffs + extraY + blockSize * 2 + 1, blockSize, blockSize * 2);
					}
				} else {
					leftButton.setBounds(-5, -5, 1, 1);
					rightButton.setBounds(-5, -5, 1, 1);
				}
			}
		}

		@Override
		public Dimension minimumLayoutSize(final Container c) {
			// NOTE: This isn't really used, refer to
			// BasicSplitPaneDivider.getPreferredSize for the reason.
			// I leave it in hopes of having this used at some point.
			if (c != BaseSplitPaneDivider.this || splitPane == null) {
				return new Dimension(0, 0);
			}
			Dimension buttonMinSize = null;

			if (splitPane.isOneTouchExpandable() && leftButton != null) {
				buttonMinSize = leftButton.getMinimumSize();
			}

			final Insets insets = getInsets();
			int width = getDividerSize();
			int height = width;

			if (orientation == JSplitPane.VERTICAL_SPLIT) {
				if (buttonMinSize != null) {
					int size = buttonMinSize.height;
					if (insets != null) {
						size += insets.top + insets.bottom;
					}
					height = Math.max(height, size);
				}
				width = 1;
			} else {
				if (buttonMinSize != null) {
					int size = buttonMinSize.width;
					if (insets != null) {
						size += insets.left + insets.right;
					}
					width = Math.max(width, size);
				}
				height = 1;
			}
			return new Dimension(width, height);
		}

		@Override
		public Dimension preferredLayoutSize(final Container c) {
			return minimumLayoutSize(c);
		}

		@Override
		public void removeLayoutComponent(final Component c) {
		}

	} // end of class MyDividerLayout

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	protected boolean centerOneTouchButtons = true;

	@Getter
	@Setter
	protected boolean flatMode = false;

	/**
	 * <p>Constructor for BaseSplitPaneDivider.</p>
	 *
	 * @param ui a {@link javax.swing.plaf.basic.BasicSplitPaneUI} object.
	 */
	public BaseSplitPaneDivider(final BasicSplitPaneUI ui) {
		super(ui);
		if (UIManager.get("SplitPane.centerOneTouchButtons") != null) {
			centerOneTouchButtons = UIManager.getBoolean("SplitPane.centerOneTouchButtons");
		}
		setLayout(new MyDividerLayout());
		final Object flatModeProperty = ui.getSplitPane().getClientProperty("flatMode");
		if (flatModeProperty instanceof Boolean) {
			flatMode = (Boolean) flatModeProperty;
		}
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createLeftOneTouchButton() {
		final JButton b = new JButton() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(final Graphics g) {
				final Color color = getBackground();
				final int w = getSize().width;
				final int h = getSize().height;
				if (getModel().isPressed() && getModel().isArmed()) {
					g.setColor(ColorHelper.darker(color, 40));
					g.fillRect(0, 0, w, h);
				} else if (getModel().isRollover()) {
					g.setColor(getRolloverColor());
					g.fillRect(0, 0, w, h);
				}
				final Icon icon;
				if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
					final AbstractLookAndFeel lf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
					if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
						icon = lf.getIconFactory().getSplitterLeftArrowIcon();
					} else {
						icon = lf.getIconFactory().getSplitterUpArrowIcon();
					}
					final int x = (w - icon.getIconWidth()) / 2;
					final int y = (h - icon.getIconHeight()) / 2;
					icon.paintIcon(this, g, x, y);
				}
				if (getModel().isArmed()) {
					if (getModel().isPressed()) {
						JTattooUtilities.draw3DBorder(g, ColorHelper.darker(color, 30), ColorHelper.brighter(color, 80),
								0, 0, w, h);
					} else {
						JTattooUtilities.draw3DBorder(g, ColorHelper.brighter(color, 80), ColorHelper.darker(color, 30),
								0, 0, w, h);
					}
				}
			}
		};
		b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setRolloverEnabled(true);
		return b;
	}

	/** {@inheritDoc} */
	@Override
	protected JButton createRightOneTouchButton() {
		final JButton b = new JButton() {

			/**
			 *
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(final Graphics g) {
				final Color color = getBackground();
				final int w = getSize().width;
				final int h = getSize().height;
				if (getModel().isPressed() && getModel().isArmed()) {
					g.setColor(ColorHelper.darker(color, 40));
					g.fillRect(0, 0, w, h);
				} else if (getModel().isRollover()) {
					g.setColor(getRolloverColor());
					g.fillRect(0, 0, w, h);
				}
				final Icon icon;
				if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
					final AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
					if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
						icon = laf.getIconFactory().getSplitterRightArrowIcon();
					} else {
						icon = laf.getIconFactory().getSplitterDownArrowIcon();
					}
					final int x = (w - icon.getIconWidth()) / 2;
					final int y = (h - icon.getIconHeight()) / 2;
					icon.paintIcon(this, g, x, y);
				}
				if (getModel().isArmed()) {
					if (getModel().isPressed()) {
						JTattooUtilities.draw3DBorder(g, ColorHelper.darker(color, 30), ColorHelper.brighter(color, 80),
								0, 0, w, h);
					} else {
						JTattooUtilities.draw3DBorder(g, ColorHelper.brighter(color, 80), ColorHelper.darker(color, 30),
								0, 0, w, h);
					}
				}
			}
		};
		b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setContentAreaFilled(false);
		b.setRolloverEnabled(true);
		return b;
	}

	/** {@inheritDoc} */
	@Override
	public Border getBorder() {
		return null;
	}

	/**
	 * <p>getRolloverColor.</p>
	 *
	 * @return a {@link java.awt.Color} object.
	 */
	public Color getRolloverColor() {
		return ColorHelper.darker(AbstractLookAndFeel.getTheme().getRolloverColor(), 16);
	}

	/** {@inheritDoc} */
	@Override
	public void paint(final Graphics g) {
		if (!isFlatMode()) {
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			final int width = getSize().width;
			final int height = getSize().height;
			int dx = 0;
			int dy = 0;
			if (width % 2 == 1) {
				dx = 1;
			}
			if (height % 2 == 1) {
				dy = 1;
			}
			final Color color = AbstractLookAndFeel.getBackgroundColor();
			final Color cHi = ColorHelper.brighter(color, 25);
			final Color cLo = ColorHelper.darker(color, 5);
			final Color[] colors = ColorHelper.createColorArr(cHi, cLo, 10);

			if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
				final AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
				if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
					JTattooUtilities.fillVerGradient(g, colors, 0, 0, width, height);
					final Icon horBumps = laf.getIconFactory().getSplitterHorBumpIcon();
					if (horBumps != null && width > horBumps.getIconWidth()) {
						final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
						g2D.setComposite(alpha);

						if (splitPane.isOneTouchExpandable() && centerOneTouchButtons) {
							final int centerY = height / 2;
							final int x = (width - horBumps.getIconWidth()) / 2 + dx;
							int y = centerY - horBumps.getIconHeight() - 40;
							horBumps.paintIcon(this, g, x, y);
							y = centerY + 40;
							horBumps.paintIcon(this, g, x, y);
						} else {
							final int x = (width - horBumps.getIconWidth()) / 2 + dx;
							final int y = (height - horBumps.getIconHeight()) / 2;
							horBumps.paintIcon(this, g, x, y);
						}
					}
				} else {
					JTattooUtilities.fillHorGradient(g, colors, 0, 0, width, height);
					final Icon verBumps = laf.getIconFactory().getSplitterVerBumpIcon();
					if (verBumps != null && height > verBumps.getIconHeight()) {
						final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);
						g2D.setComposite(alpha);
						if (splitPane.isOneTouchExpandable() && centerOneTouchButtons) {
							final int centerX = width / 2;
							int x = centerX - verBumps.getIconWidth() - 40;
							final int y = (height - verBumps.getIconHeight()) / 2 + dy;
							verBumps.paintIcon(this, g, x, y);
							x = centerX + 40;
							verBumps.paintIcon(this, g, x, y);
						} else {
							final int x = (width - verBumps.getIconWidth()) / 2;
							final int y = (height - verBumps.getIconHeight()) / 2 + dy;
							verBumps.paintIcon(this, g, x, y);
						}
					}
				}
			}
			g2D.setComposite(savedComposite);
		}
		paintComponents(g);
	}

} // end of class BaseSplitPaneDivieder
