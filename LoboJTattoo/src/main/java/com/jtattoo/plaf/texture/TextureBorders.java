/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;
import com.jtattoo.plaf.LazyImageIcon;

/**
 * <p>TextureBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class TextureBorders extends BaseBorders {

//------------------------------------------------------------------------------------
// Inner classes
//------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Color DEFAULT_COLOR_HI = new Color(220, 230, 245);
		private static final Color DEFAULT_COLOR_LO = new Color(212, 224, 243);
		private static final Insets INSETS = new Insets(3, 4, 3, 4);

		@Override
		public Insets getBorderInsets(final Component c) {
			return INSETS;
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			final AbstractButton button = (AbstractButton) c;
			Color frameColor = AbstractLookAndFeel.getTheme().getFrameColor();
			if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
				frameColor = AbstractLookAndFeel.getTheme().getFocusFrameColor();
			}
			if (!button.isEnabled()) {
				frameColor = ColorHelper.brighter(frameColor, 20);
			} else if (button.getModel().isRollover()) {
				frameColor = ColorHelper.darker(frameColor, 20);
			}
			if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
				final Composite savedComposite = g2D.getComposite();
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g2D.setComposite(alpha);
				g2D.setColor(Color.white);
				g2D.drawLine(x, h - 1, x + w, h - 1);
				g2D.setComposite(savedComposite);
				if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())
						&& !button.hasFocus()) {
					g2D.setColor(ColorHelper.darker(frameColor, 20));
					g2D.drawRect(x, y, w - 1, h - 2);
					if (!button.getModel().isRollover()) {
						g2D.setColor(DEFAULT_COLOR_HI);
						g2D.drawRect(x + 1, y + 1, w - 3, h - 4);
						g2D.setColor(DEFAULT_COLOR_LO);
						g2D.drawRect(x + 2, y + 2, w - 5, h - 6);
					}
				} else {
					g2D.setColor(frameColor);
					g2D.drawRect(x, y, w - 1, h - 2);
				}
			} else {

				final Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				final Composite saveComposite = g2D.getComposite();
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f);
				g2D.setComposite(alpha);
				g2D.setColor(Color.white);
				g2D.drawRoundRect(x, y, w - 1, h - 1, 6, 6);
				g2D.setComposite(saveComposite);

				if (button.getRootPane() != null && button.equals(button.getRootPane().getDefaultButton())
						&& !button.hasFocus()) {
					g2D.setColor(ColorHelper.darker(frameColor, 20));
					g2D.drawRoundRect(x, y, w - 1, h - 2, 6, 6);
					if (!button.getModel().isRollover()) {
						g2D.setColor(DEFAULT_COLOR_HI);
						g2D.drawRoundRect(x + 1, y + 1, w - 3, h - 4, 6, 6);
						g2D.setColor(DEFAULT_COLOR_LO);
						g2D.drawRoundRect(x + 2, y + 2, w - 5, h - 6, 6, 6);
					}
				} else {
					g2D.setColor(frameColor);
					g2D.drawRoundRect(x, y, w - 1, h - 2, 6, 6);
					g2D.setColor(ColorHelper.brighter(frameColor, 20));
					g2D.drawLine(x + 2, y, w - 3, y);
					g2D.setColor(ColorHelper.darker(frameColor, 10));
					g2D.drawLine(x + 2, h - 2, w - 3, h - 2);
				}

				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, savedRederingHint);
			}
		}

	} // end of class ButtonBorder

	public static class InternalFrameBorder extends BaseInternalFrameBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Color FRAME_BORDER_COLOR = new Color(128, 128, 128);
		private static final Color[] FRAME_COLORS = new Color[] { new Color(144, 144, 144), new Color(180, 180, 180),
				new Color(216, 216, 216), new Color(236, 236, 236), new Color(164, 164, 164), new Color(196, 196, 196),
				new Color(184, 184, 184), new Color(172, 172, 172) };

		@Override
		public Insets getBorderInsets(final Component c) {
			if (isResizable(c)) {
				return new Insets(5, 8, 6, 8);
			} else {
				return new Insets(PALETTE_INSETS.top, PALETTE_INSETS.left, PALETTE_INSETS.bottom, PALETTE_INSETS.right);
			}
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			boolean isJFrameBorder = false;
			if (c instanceof JRootPane) {
				final JRootPane jp = (JRootPane) c;
				if (jp.getParent() instanceof JFrame) {
					isJFrameBorder = true;
				}
			}
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
//            if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
//                if (isActive(c)) {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
//                } else {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
//                }
//                g.drawRect(0, 0, w - 1, h - 1);
//                if (isActive(c)) {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowTitleBackgroundColor());
//                } else {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveTitleBackgroundColor());
//                }
//                g.drawRect(1, 1, w - 3, h - 3);
//                g.drawRect(2, 2, w - 5, h - 5);
//                g.drawRect(3, 3, w - 7, h - 7);
//                if (isActive(c)) {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
//                } else {
//                    g.setColor(AbstractLookAndFeel.getTheme().getWindowInactiveBorderColor());
//                }
//                g.drawRect(4, 4, w - 9, h - 9);
//                return;
//            } else
			if (!isJFrameBorder) {
				TextureUtils.fillComponent(g, c, 1, 1, w - 1, h - 1, TextureUtils.WINDOW_TEXTURE_TYPE);
				g.setColor(AbstractLookAndFeel.getTheme().getWindowBorderColor());
				g.drawRect(0, 0, w - 1, h - 1);
			} else {
				final Insets bi = getBorderInsets(c);
				final Color frameColor = AbstractLookAndFeel.getTheme().getWindowBorderColor();
				// top
				g.setColor(frameColor);
				g.drawLine(x, y, w, y);
				TextureUtils.fillComponent(g, c, 1, 1, w, bi.top - 1, TextureUtils.WINDOW_TEXTURE_TYPE);
				// bottom
				g.setColor(frameColor);
				g.drawLine(x, y + h - 1, w, y + h - 1);
				TextureUtils.fillComponent(g, c, 1, h - bi.bottom, w, bi.bottom - 1, TextureUtils.WINDOW_TEXTURE_TYPE);

				g.setColor(FRAME_BORDER_COLOR);
				g.drawLine(x, y, x, y + h);
				g.drawLine(x + w - 1, y, x + w - 1, y + h);
				// left
				for (int i = 1; i < FRAME_COLORS.length; i++) {
					g2D.setColor(FRAME_COLORS[i]);
					g2D.drawLine(i, 0, i, h);
				}
				// right
				for (int i = 0; i < FRAME_COLORS.length - 1; i++) {
					g2D.setColor(FRAME_COLORS[i]);
					g2D.drawLine(w - 8 + i, 0, w - 8 + i, h);
				}
				g.setColor(ColorHelper.brighter(FRAME_BORDER_COLOR, 20));
				g.drawLine(x, y, x + bi.left - 1, y);
				g.drawLine(x + w - bi.right, y, x + w - 1, y);

			} // JFrame border
			if (isResizable(c)) {
				// top
				float alphaValue = 0.4f;
				float alphaDelta = 0.1f;
				g2D.setColor(Color.white);
				if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
					alphaValue = 0.8f;
					alphaDelta = 0.2f;
				}
				for (int i = 1; i < 5; i++) {
					g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
					g2D.drawLine(1, i, w - 2, i);
					alphaValue -= alphaDelta;
				}
				// bottom
				alphaValue = 0.3f;
				alphaDelta = 0.05f;
				g2D.setColor(Color.black);
				if (!AbstractLookAndFeel.getTheme().isDarkTexture()) {
					alphaValue = 0.14f;
					alphaDelta = 0.02f;
				}
				for (int i = 1; i < 6; i++) {
					g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
					g2D.drawLine(1, h - i, w - 2, h - i);
					alphaValue -= alphaDelta;
				}
			}
			g2D.setComposite(savedComposite);
		}

	} // end of class InternalFrameBorder

	public static class MenuItemBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(2, 2, 2, 2);

		@Override
		public Insets getBorderInsets(final Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return false;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final JMenuItem b = (JMenuItem) c;
			final ButtonModel model = b.getModel();
			final Color frameColor = AbstractLookAndFeel.getFrameColor();
			if (c.getParent() instanceof JMenuBar) {
				if (model.isArmed() || model.isSelected()) {
					g.setColor(frameColor);
					g.drawLine(x, y, x + w - 1, y);
					g.drawLine(x, y, x, y + h - 1);
					g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
				}
			} else {
				if (model.isArmed() || c instanceof JMenu && model.isSelected()) {
					g.setColor(frameColor);
					g.drawLine(x, y, x + w - 1, y);
					g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
				}
			}
		}

	} // end of class MenuItemBorder

	public static class PopupMenuBorder extends BasePopupMenuBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final float[] SHOW_ALPHA = { 0.6f, 0.4f, 0.2f, 0.1f };

		public PopupMenuBorder() {
			shadowSize = 4;
			leftLogoInsets = new Insets(1, 18, 1, 1);
			rightLogoInsets = new Insets(1, 1, 1, 18);
			insets = new Insets(1, 1, 1, 1);
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			final Color frameColor = AbstractLookAndFeel.getFrameColor();
			g.setColor(frameColor);
			if (JTattooUtilities.isLeftToRight(c)) {
				final int dx = getBorderInsets(c).left;
				// Top

				if (hasLogo(c)) {
					TextureUtils.fillComponent(g, c, x, y, dx, h - 1 - shadowSize, TextureUtils.ROLLOVER_TEXTURE_TYPE);
					paintLogo(c, g, x, y, w, h);
				}
				g.setColor(frameColor);
				if (isMenuBarPopup(c)) {
					g.drawLine(x + dx, y, x + w - shadowSize - 1, y);
				} else {
					g.drawLine(x, y, x + w - shadowSize - 1, y);
				}
				// Left
				g.drawLine(x, y, x, y + h - shadowSize - 1);
				// Bottom
				g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
				// Right
				g.drawLine(x + w - shadowSize - 1, y, x + w - shadowSize - 1, y + h - shadowSize - 1);
			} else {
				final int dx = getBorderInsets(c).right - shadowSize;
				// Top
				if (hasLogo(c)) {
					TextureUtils.fillComponent(g, c, x + w - dx - shadowSize, y, dx - 1, h - 1 - shadowSize,
							TextureUtils.ROLLOVER_TEXTURE_TYPE);
					paintLogo(c, g, x, y, w, h);
				}
				g.setColor(frameColor);
				if (isMenuBarPopup(c)) {
					g.drawLine(x, y, x + w - dx - shadowSize - 1, y);
				} else {
					g.drawLine(x, y, x + w - shadowSize - 1, y);
				}

				// Left
				g.drawLine(x, y, x, y + h - shadowSize - 1);
				// Bottom
				g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
				// Right
				g.drawLine(x + w - shadowSize - 1, y, x + w - shadowSize - 1, y + h - shadowSize - 1);
			}

			// paint the shadow
			final Composite savedComposite = g2D.getComposite();
			g2D.setColor(Color.black);
			for (int i = 0; i < shadowSize; i++) {
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, SHOW_ALPHA[i]);
				g2D.setComposite(alpha);
				// bottom
				g.drawLine(x + shadowSize, y + h - shadowSize + i, x + w - shadowSize + i, y + h - shadowSize + i);
				// right
				g.drawLine(x + w - shadowSize + i, y + shadowSize, x + w - shadowSize + i, y + h - shadowSize - 1 + i);
			}
			g2D.setComposite(savedComposite);
		}

	} // end of class PopupMenuTextureBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(1, 1, 1, 1);

		@Override
		public Insets getBorderInsets(final Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public boolean isBorderOpaque() {
			return true;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final AbstractButton button = (AbstractButton) c;
			final ButtonModel model = button.getModel();
			final Color loColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 50);
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed() || model.isSelected()) {
					final Graphics2D g2D = (Graphics2D) g;
					final Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
					g2D.setComposite(alpha);
					g.setColor(Color.black);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				} else if (model.isRollover()) {
					final Graphics2D g2D = (Graphics2D) g;
					final Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g2D.setComposite(alpha);
					g.setColor(Color.white);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				}
			}
		}

	} // end of class RolloverToolButtonBorder

	public static class ToolBarBorder extends AbstractBorder implements UIResource, SwingConstants {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final LazyImageIcon HOR_RUBBER_ICON = new LazyImageIcon("texture/icons/HorRubber.gif");
		private static final LazyImageIcon VER_RUBBER_ICON = new LazyImageIcon("texture/icons/VerRubber.gif");

		@Override
		public Insets getBorderInsets(final Component c) {
			final Insets insets = new Insets(2, 2, 2, 2);
			if (((JToolBar) c).isFloatable()) {
				if (((JToolBar) c).getOrientation() == HORIZONTAL) {
					if (JTattooUtilities.isLeftToRight(c)) {
						insets.left = 15;
					} else {
						insets.right = 15;
					}
				} else {
					insets.top = 15;
				}
			}
			final Insets margin = ((JToolBar) c).getMargin();
			if (margin != null) {
				insets.left += margin.left;
				insets.top += margin.top;
				insets.right += margin.right;
				insets.bottom += margin.bottom;
			}
			return insets;
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			final Insets insets = getBorderInsets(c);
			borderInsets.left = insets.left;
			borderInsets.top = insets.top;
			borderInsets.right = insets.right;
			borderInsets.bottom = insets.bottom;
			return borderInsets;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			if (((JToolBar) c).isFloatable()) {
				if (((JToolBar) c).getOrientation() == HORIZONTAL) {
					final int x1 = 4;
					final int y1 = (h - HOR_RUBBER_ICON.getIconHeight()) / 2;
					HOR_RUBBER_ICON.paintIcon(c, g, x1, y1);
				} else {
					final int x1 = (w - VER_RUBBER_ICON.getIconWidth()) / 2 + 2;
					final int y1 = 4;
					VER_RUBBER_ICON.paintIcon(c, g, x1, y1);
				}
			}
		}
	} // end of class ToolBarBorder

	// ------------------------------------------------------------------------------------
// Lazy access methods
//------------------------------------------------------------------------------------
	/**
	 * <p>getButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getButtonBorder() {
		if (buttonBorder == null) {
			buttonBorder = new ButtonBorder();
		}
		return buttonBorder;
	}

	/**
	 * <p>getInternalFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getInternalFrameBorder() {
		if (internalFrameBorder == null) {
			internalFrameBorder = new InternalFrameBorder();
		}
		return internalFrameBorder;
	}

	/**
	 * <p>getMenuItemBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getMenuItemBorder() {
		if (menuItemBorder == null) {
			menuItemBorder = new MenuItemBorder();
		}
		return menuItemBorder;
	}

	/**
	 * <p>getPopupMenuBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getPopupMenuBorder() {
		if (popupMenuBorder == null) {
			popupMenuBorder = new PopupMenuBorder();
		}
		return popupMenuBorder;
	}

	/**
	 * <p>getRolloverToolButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getRolloverToolButtonBorder() {
		if (rolloverToolButtonBorder == null) {
			rolloverToolButtonBorder = new RolloverToolButtonBorder();
		}
		return rolloverToolButtonBorder;
	}

	/**
	 * <p>getToggleButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToggleButtonBorder() {
		return getButtonBorder();
	}

	/**
	 * <p>getToolBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToolBarBorder() {
		if (toolBarBorder == null) {
			toolBarBorder = new ToolBarBorder();
		}
		return toolBarBorder;
	}

} // end of class TextureBorders
