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

package com.jtattoo.plaf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.UIResource;

/**
 * <p>BaseBorders class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseBorders {

	public static class BaseInternalFrameBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		protected final int DW = 5;
		protected final int TRACK_WIDTH = 22;
		protected final Insets INSETS = new Insets(DW, DW, DW, DW);
		protected final Insets PALETTE_INSETS = new Insets(3, 3, 3, 3);

		public BaseInternalFrameBorder() {
		}

		@Override
		public Insets getBorderInsets(final Component c) {
			if (isResizable(c)) {
				return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
			} else {
				return new Insets(PALETTE_INSETS.top, PALETTE_INSETS.left, PALETTE_INSETS.bottom, PALETTE_INSETS.right);
			}
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			final Insets ins = getBorderInsets(c);
			borderInsets.left = ins.left;
			borderInsets.top = ins.top;
			borderInsets.right = ins.right;
			borderInsets.bottom = ins.bottom;
			return borderInsets;
		}

		public int getTitleHeight(final Component c) {
			int th = 21;
			final int fh = getBorderInsets(c).top + getBorderInsets(c).bottom;
			if (c instanceof JDialog) {
				final JDialog dialog = (JDialog) c;
				th = dialog.getSize().height - dialog.getContentPane().getSize().height - fh - 1;
				if (dialog.getJMenuBar() != null) {
					th -= dialog.getJMenuBar().getSize().height;
				}
			} else if (c instanceof JInternalFrame) {
				final JInternalFrame frame = (JInternalFrame) c;
				th = frame.getSize().height - frame.getRootPane().getSize().height - fh - 1;
				if (frame.getJMenuBar() != null) {
					th -= frame.getJMenuBar().getSize().height;
				}
			} else if (c instanceof JRootPane) {
				final JRootPane jp = (JRootPane) c;
				if (jp.getParent() instanceof JFrame) {
					final JFrame frame = (JFrame) c.getParent();
					th = frame.getSize().height - frame.getContentPane().getSize().height - fh - 1;
					if (frame.getJMenuBar() != null) {
						th -= frame.getJMenuBar().getSize().height;
					}
				} else if (jp.getParent() instanceof JDialog) {
					final JDialog dialog = (JDialog) c.getParent();
					th = dialog.getSize().height - dialog.getContentPane().getSize().height - fh - 1;
					if (dialog.getJMenuBar() != null) {
						th -= dialog.getJMenuBar().getSize().height;
					}
				}
			}
			return th;
		}

		public boolean isActive(final Component c) {
			if (c instanceof JComponent) {
				return JTattooUtilities.isActive((JComponent) c);
			} else {
				return true;
			}
		}

		public boolean isResizable(final Component c) {
			boolean resizable = true;
			if (c instanceof JDialog) {
				final JDialog dialog = (JDialog) c;
				resizable = dialog.isResizable();
			} else if (c instanceof JInternalFrame) {
				final JInternalFrame frame = (JInternalFrame) c;
				resizable = frame.isResizable();
			} else if (c instanceof JRootPane) {
				final JRootPane jp = (JRootPane) c;
				if (jp.getParent() instanceof JFrame) {
					final JFrame frame = (JFrame) c.getParent();
					resizable = frame.isResizable();
				} else if (jp.getParent() instanceof JDialog) {
					final JDialog dialog = (JDialog) c.getParent();
					resizable = dialog.isResizable();
				}
			}
			return resizable;
		}

	} // end of class BaseInternalFrameBorder

	public static class BasePopupMenuBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		protected static Font logoFont;
		protected static Insets leftLogoInsets;
		protected static Insets rightLogoInsets;
		protected static Insets insets;
		protected static int shadowSize;

		public BasePopupMenuBorder() {
			logoFont = new Font("Dialog", Font.BOLD, 12);
			leftLogoInsets = new Insets(2, 18, 1, 1);
			rightLogoInsets = new Insets(2, 2, 1, 18);
			insets = new Insets(2, 1, 1, 1);
			shadowSize = 0;
		}

		@Override
		public Insets getBorderInsets(final Component c) {
			if (hasLogo(c)) {
				if (JTattooUtilities.isLeftToRight(c)) {
					return new Insets(leftLogoInsets.top, leftLogoInsets.left, leftLogoInsets.bottom + shadowSize,
							leftLogoInsets.right + shadowSize);
				} else {
					return new Insets(rightLogoInsets.top, rightLogoInsets.left, rightLogoInsets.bottom + shadowSize,
							rightLogoInsets.right + shadowSize);
				}
			} else {
				return new Insets(insets.top, insets.left, insets.bottom + shadowSize, insets.right + shadowSize);
			}
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			final Insets ins = getBorderInsets(c);
			borderInsets.left = ins.left;
			borderInsets.top = ins.top;
			borderInsets.right = ins.right;
			borderInsets.bottom = ins.bottom;
			return borderInsets;
		}

		public Color getLogoColorHi() {
			return Color.white;
		}

		public Color getLogoColorLo() {
			return ColorHelper.darker(AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor(), 20);
		}

		public boolean hasLogo(final Component c) {
			return AbstractLookAndFeel.getTheme().getLogoString() != null
					&& AbstractLookAndFeel.getTheme().getLogoString().length() > 0;
		}

		public boolean isMenuBarPopup(final Component c) {
			boolean menuBarPopup = false;
			if (c instanceof JPopupMenu) {
				final JPopupMenu pm = (JPopupMenu) c;
				if (pm.getInvoker() != null) {
					menuBarPopup = pm.getInvoker().getParent() instanceof JMenuBar;
				}
			}
			return menuBarPopup;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Color logoColor = AbstractLookAndFeel.getMenuSelectionBackgroundColor();
			final Color borderColorLo = AbstractLookAndFeel.getFrameColor();
			final Color borderColorHi = ColorHelper.brighter(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 40);
			g.setColor(logoColor);
			if (JTattooUtilities.isLeftToRight(c)) {
				final int dx = getBorderInsets(c).left;
				g.fillRect(x, y, dx - 1, h - 1);
				paintLogo(c, g, x, y, w, h);
				// - highlight
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
				g.drawLine(x + dx, y + 1, x + w - 2, y + 1);
				g.setColor(borderColorHi);
				g.drawLine(x + 1, y, x + 1, y + h - 2);
				// - outer frame
				g.setColor(borderColorLo);
				if (isMenuBarPopup(c)) {
					// top
					g.drawLine(x + dx - 1, y, x + w, y);
					// left
					g.drawLine(x, y, x, y + h - 1);
					// bottom
					g.drawLine(x, y + h - 1, x + w, y + h - 1);
					// right
					g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
				} else {
					g.drawRect(x, y, w - 1, h - 1);
				}
				// - logo separator
				g.drawLine(x + dx - 1, y + 1, x + dx - 1, y + h - 1);
			} else {
				final int dx = getBorderInsets(c).right;
				g.fillRect(x + w - dx, y, dx, h - 1);
				paintLogo(c, g, x, y, w, h);
				// - highlight
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
				g.drawLine(x + 1, y + 1, x + w - dx - 1, y + 1);
				g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
				// - outer frame
				g.setColor(borderColorLo);
				if (isMenuBarPopup(c)) {
					// top
					g.drawLine(x, y, x + w - dx, y);
					// left
					g.drawLine(x, y, x, y + h - 1);
					// bottom
					g.drawLine(x, y + h - 1, x + w, y + h - 1);
					// right
					g.drawLine(x + w - 1, y, x + w - 1, y + h - 1);
				} else {
					g.drawRect(x, y, w - 1, h - 1);
				}
				// - logo separator
				g.drawLine(x + w - dx, y + 1, x + w - dx, y + h - 1);
			}
		}

		public void paintLogo(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			if (hasLogo(c)) {
				final Graphics2D g2D = (Graphics2D) g;

				final Font savedFont = g2D.getFont();
				g.setFont(logoFont);
				final FontMetrics fm = JTattooUtilities.getFontMetrics((JComponent) c, g, c.getFont());
				final String logo = JTattooUtilities.getClippedText(AbstractLookAndFeel.getTheme().getLogoString(), fm,
						h - 16);

				final AffineTransform savedTransform = g2D.getTransform();

				final Color fc = getLogoColorHi();
				final Color bc = getLogoColorLo();

				if (JTattooUtilities.isLeftToRight(c)) {
					g2D.translate(fm.getAscent() + 1, h - shadowSize - 4);
					g2D.rotate(Math.toRadians(-90));
					g2D.setColor(bc);
					JTattooUtilities.drawString((JComponent) c, g, logo, 0, 1);
					g2D.setColor(fc);
					JTattooUtilities.drawString((JComponent) c, g, logo, 1, 0);
				} else {
					g2D.translate(w - shadowSize - 4, h - shadowSize - 4);
					g2D.rotate(Math.toRadians(-90));
					g2D.setColor(bc);
					JTattooUtilities.drawString((JComponent) c, g, logo, 0, 1);
					g2D.setColor(fc);
					JTattooUtilities.drawString((JComponent) c, g, logo, 1, 0);
				}

				g2D.setTransform(savedTransform);
				g2D.setFont(savedFont);
			}
		}

	} // class PopupMenuBorder

	public static class BasePopupMenuShadowBorder extends BasePopupMenuBorder {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public BasePopupMenuShadowBorder() {
			shadowSize = 4;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					AbstractLookAndFeel.getTheme().getMenuAlpha());
			g2D.setComposite(alpha);
			final Color logoColor = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor();
			final Color borderColorLo = AbstractLookAndFeel.getFrameColor();
			final Color borderColorHi = ColorHelper.brighter(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 40);
			g.setColor(logoColor);
			if (JTattooUtilities.isLeftToRight(c)) {
				final int dx = getBorderInsets(c).left;
				g.fillRect(x, y, dx - 1, h - 1 - shadowSize);
				paintLogo(c, g, x, y, w, h);
				// - highlight
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
				g.drawLine(x + dx, y + 1, x + w - shadowSize - 2, y + 1);
				g.setColor(borderColorHi);
				g.drawLine(x + 1, y, x + 1, y + h - shadowSize - 2);
				// - outer frame
				g.setColor(borderColorLo);
				if (isMenuBarPopup(c)) {
					// top
					g.drawLine(x + dx - 1, y, x + w - shadowSize - 1, y);
					// left
					g.drawLine(x, y, x, y + h - shadowSize - 1);
					// bottom
					g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
					// right
					g.drawLine(x + w - shadowSize - 1, y + 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
				} else {
					g.drawRect(x, y, w - shadowSize - 1, h - shadowSize - 1);
				}
				// - logo separator
				g.drawLine(x + dx - 1, y + 1, x + dx - 1, y + h - shadowSize - 1);
			} else {
				final int dx = getBorderInsets(c).right - shadowSize;
				g.fillRect(x + w - dx - shadowSize, y, dx - 1, h - 1 - shadowSize);
				paintLogo(c, g, x, y, w, h);
				// - highlight
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
				g.drawLine(x + 1, y + 1, x + w - dx - shadowSize - 1, y + 1);
				g.drawLine(x + 1, y + 1, x + 1, y + h - shadowSize - 2);
				// - outer frame
				g.setColor(borderColorLo);
				if (isMenuBarPopup(c)) {
					// top
					g.drawLine(x, y, x + w - dx - shadowSize, y);
					// left
					g.drawLine(x, y, x, y + h - shadowSize - 1);
					// bottom
					g.drawLine(x, y + h - shadowSize - 1, x + w - shadowSize - 1, y + h - shadowSize - 1);
					// right
					g.drawLine(x + w - shadowSize - 1, y, x + w - shadowSize - 1, y + h - shadowSize - 1);
				} else {
					g.drawRect(x, y, w - shadowSize - 1, h - shadowSize - 1);
				}
				// - logo separator
				g.drawLine(x + w - dx - shadowSize, y + 1, x + w - dx - shadowSize, y + h - shadowSize - 1);
			}

			// paint the shadow
			g2D.setColor(AbstractLookAndFeel.getTheme().getShadowColor());
			float alphaValue = 0.4f;
			for (int i = 0; i < shadowSize; i++) {
				alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue);
				g2D.setComposite(alpha);
				g.drawLine(x + w - shadowSize + i, y + shadowSize, x + w - shadowSize + i, y + h - shadowSize - 1 + i);
				g.drawLine(x + shadowSize, y + h - shadowSize + i, x + w - shadowSize + i, y + h - shadowSize + i);
				alphaValue -= alphaValue / 2;
			}

			g2D.setComposite(savedComposite);
		}

	} // end of class PopupMenuShadowBorder

	public static class ComboBoxBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(1, 1, 1, 1);

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
			g.setColor(AbstractLookAndFeel.getTheme().getFrameColor());
			g.drawRect(x, y, width - 1, height - 1);
		}

	} // end of class ComboBoxBorder

	public static class Down3DBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(1, 1, 1, 1);

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Color frameColor = AbstractLookAndFeel.getTheme().getBackgroundColor();
			JTattooUtilities.draw3DBorder(g, ColorHelper.darker(frameColor, 20), ColorHelper.brighter(frameColor, 80),
					x, y, w, h);
		}

	} // end of class Down3DBorder
		// ------------------------------------------------------------------------------------
		// Implementation of border classes
		// ------------------------------------------------------------------------------------

	public static class FocusFrameBorder extends AbstractBorder implements UIResource {

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
			final Color hiColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFocusFrameColor(), 60);
			final Color loColor = AbstractLookAndFeel.getTheme().getFocusFrameColor();
			g.setColor(loColor);
			g.drawRect(x, y, width - 1, height - 1);
			g.setColor(hiColor);
			g.drawRect(x + 1, y + 1, width - 3, height - 3);
		}

	} // end of class FocusFrameBorder

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final JMenuItem b = (JMenuItem) c;
			final ButtonModel model = b.getModel();
			final Color borderColorLo = AbstractLookAndFeel.getFrameColor();
			final Color borderColorHi = ColorHelper.brighter(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 40);
			if (c.getParent() instanceof JMenuBar) {
				if (model.isArmed() || model.isSelected()) {
					g.setColor(borderColorLo);
					g.drawLine(x, y, x + w - 1, y);
					g.drawLine(x, y, x, y + h - 1);
					g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
					g.setColor(borderColorHi);
					g.drawLine(x + 1, y + 1, x + w - 2, y + 1);
					g.drawLine(x + 1, y + 1, x + 1, y + h - 1);
				}
			} else {
				if (model.isArmed() || c instanceof JMenu && model.isSelected()) {
					g.setColor(borderColorLo);
					g.drawLine(x, y, x + w - 1, y);
					g.drawLine(x, y + h - 1, x + w - 1, y + h - 1);
					g.setColor(borderColorHi);
					g.drawLine(x, y + 1, x + w - 2, y + 1);
				}
			}
		}

	} // end of class MenuItemBorder

	public static class PaletteBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(1, 1, 1, 1);

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			if (JTattooUtilities.isFrameActive(c)) {
				g.setColor(AbstractLookAndFeel.getWindowBorderColor());
			} else {
				g.setColor(AbstractLookAndFeel.getWindowInactiveBorderColor());
			}
			g.drawRect(x, y, w - 1, h - 1);
		}

	} // class PaletteBorder

	public static class ScrollPaneBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(2, 2, 2, 2);
		private static final Insets TABLE_INSETS = new Insets(1, 1, 1, 1);

		private boolean tableBorder = false;

		public ScrollPaneBorder(final boolean tableBorder) {
			this.tableBorder = tableBorder;
		}

		@Override
		public Insets getBorderInsets(final Component c) {
			if (tableBorder) {
				return new Insets(TABLE_INSETS.top, TABLE_INSETS.left, TABLE_INSETS.bottom, TABLE_INSETS.right);
			} else {
				return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
			}
		}

		@Override
		public Insets getBorderInsets(final Component c, final Insets borderInsets) {
			final Insets ins = getBorderInsets(c);
			borderInsets.left = ins.left;
			borderInsets.top = ins.top;
			borderInsets.right = ins.right;
			borderInsets.bottom = ins.bottom;
			return borderInsets;
		}

		@Override
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			g.setColor(AbstractLookAndFeel.getTheme().getFrameColor());
			g.drawRect(x, y, w - 1, h - 1);
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getTheme().getBackgroundColor(), 50));
			g.drawRect(x + 1, y + 1, w - 3, h - 3);
		}

	} // end of class ScrollPaneBorder

	public static class SpinnerBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(1, 1, 1, 1);

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
			g.setColor(AbstractLookAndFeel.getTheme().getFrameColor());
			g.drawRect(x, y, width - 1, height - 1);
		}

	} // end of class SpinnerBorder

	public static class TableHeaderBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(2, 2, 2, 0);

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int w, final int h) {
			final Graphics2D g2D = (Graphics2D) g;
			final Composite savedComposite = g2D.getComposite();
			final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
			g2D.setComposite(alpha);
			final Color cHi = AbstractLookAndFeel.getTheme().getControlHighlightColor();
			final Color cLo = AbstractLookAndFeel.getTheme().getControlShadowColor();
			JTattooUtilities.draw3DBorder(g, cHi, cLo, x, y, w, h);
			g2D.setComposite(savedComposite);
		}

	} // end of class TableHeaderBorder

	public static class TextFieldBorder extends AbstractBorder implements UIResource {

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
		public void paintBorder(final Component c, final Graphics g, final int x, final int y, final int width, final int height) {
			g.setColor(AbstractLookAndFeel.getTheme().getFrameColor());
			g.drawRect(x, y, width - 1, height - 1);
		}

	} // end of class TextFieldBorder

	public static class ToolBarBorder extends AbstractBorder implements UIResource, SwingConstants {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Color SHADOW_COLOR = new Color(160, 160, 160);

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
		public void paintBorder(final Component c, final Graphics g, final int borderX, final int y, final int w, final int h) {
			int x = borderX;
			if (((JToolBar) c).isFloatable()) {
				final Graphics2D g2D = (Graphics2D) g;
				final Composite savedComposite = g2D.getComposite();
				final AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g2D.setComposite(alpha);
				if (((JToolBar) c).getOrientation() == HORIZONTAL) {
					if (!JTattooUtilities.isLeftToRight(c)) {
						x += w - 15;
					}
					g.setColor(Color.WHITE);
					g.drawLine(x + 3, y + 4, x + 3, h - 5);
					g.drawLine(x + 6, y + 3, x + 6, h - 4);
					g.drawLine(x + 9, y + 4, x + 9, h - 5);
					g.setColor(SHADOW_COLOR);
					g.drawLine(x + 4, y + 4, x + 4, h - 5);
					g.drawLine(x + 7, y + 3, x + 7, h - 4);
					g.drawLine(x + 10, y + 4, x + 10, h - 5);
				} else {
					// vertical
					g.setColor(Color.WHITE);
					g.drawLine(x + 3, y + 3, w - 4, y + 3);
					g.drawLine(x + 3, y + 6, w - 4, y + 6);
					g.drawLine(x + 3, y + 9, w - 4, y + 9);
					g.setColor(SHADOW_COLOR);
					g.drawLine(x + 3, y + 4, w - 4, y + 4);
					g.drawLine(x + 3, y + 7, w - 4, y + 7);
					g.drawLine(x + 3, y + 10, w - 4, y + 10);
				}
				g2D.setComposite(savedComposite);
			}
		}

	} // end of class ToolBarBorder

	public static class ToolButtonBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(2, 2, 2, 2);

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
			final Color frameColor = AbstractLookAndFeel.getToolbarBackgroundColor();
			final Color frameHiColor = ColorHelper.brighter(frameColor, 10);
			final Color frameLoColor = ColorHelper.darker(frameColor, 30);
			JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x, y, w, h);
			if (model.isPressed() && model.isArmed() || model.isSelected()) {
				JTattooUtilities.draw3DBorder(g, frameLoColor, frameHiColor, x, y, w, h);
			} else {
				JTattooUtilities.draw3DBorder(g, frameLoColor, frameHiColor, x, y, w, h);
				JTattooUtilities.draw3DBorder(g, frameHiColor, frameLoColor, x + 1, y + 1, w - 2, h - 2);
			}
		}

	} // end of class ToolButtonBorder

	/** Constant buttonBorder */
	protected static Border buttonBorder = null;
	/** Constant focusFrameBorder */
	protected static Border focusFrameBorder = null;
	/** Constant textFieldBorder */
	protected static Border textFieldBorder = null;
	/** Constant spinnerBorder */
	protected static Border spinnerBorder = null;

	/** Constant comboBoxBorder */
	protected static Border comboBoxBorder = null;

	/** Constant progressBarBorder */
	protected static Border progressBarBorder = null;

	/** Constant tableHeaderBorder */
	protected static Border tableHeaderBorder = null;

	/** Constant popupMenuBorder */
	protected static Border popupMenuBorder = null;

	/** Constant menuItemBorder */
	protected static Border menuItemBorder = null;

	/** Constant toolBarBorder */
	protected static Border toolBarBorder = null;

	/** Constant toolButtonBorder */
	protected static Border toolButtonBorder = null;

	/** Constant rolloverToolButtonBorder */
	protected static Border rolloverToolButtonBorder = null;

	/** Constant internalFrameBorder */
	protected static Border internalFrameBorder = null;

	/** Constant paletteBorder */
	protected static Border paletteBorder = null;

	/** Constant scrollPaneBorder */
	protected static Border scrollPaneBorder = null;

	/** Constant tableScrollPaneBorder */
	protected static Border tableScrollPaneBorder = null;

	/** Constant tabbedPaneBorder */
	protected static Border tabbedPaneBorder = null;

	/** Constant desktopIconBorder */
	protected static Border desktopIconBorder = null;

	/**
	 * <p>Getter for the field comboBoxBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getComboBoxBorder() {
		if (comboBoxBorder == null) {
			comboBoxBorder = new ComboBoxBorder();
		}
		return comboBoxBorder;
	}

	/**
	 * <p>Getter for the field desktopIconBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getDesktopIconBorder() {
		if (desktopIconBorder == null) {
			desktopIconBorder = new BorderUIResource.CompoundBorderUIResource(
					new LineBorder(AbstractLookAndFeel.getWindowBorderColor(), 1),
					new MatteBorder(2, 2, 1, 2, AbstractLookAndFeel.getWindowBorderColor()));
		}
		return desktopIconBorder;
	}

	// ------------------------------------------------------------------------------------
	// Lazy access methods
	// ------------------------------------------------------------------------------------
	/**
	 * <p>Getter for the field focusFrameBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getFocusFrameBorder() {
		if (focusFrameBorder == null) {
			focusFrameBorder = new FocusFrameBorder();
		}
		return focusFrameBorder;
	}

	/**
	 * <p>getMenuBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getMenuBarBorder() {
		return BorderFactory.createEmptyBorder(1, 1, 1, 1);
	}

	/**
	 * <p>Getter for the field menuItemBorder.</p>
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
	 * <p>Getter for the field paletteBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getPaletteBorder() {
		if (paletteBorder == null) {
			paletteBorder = new PaletteBorder();
		}
		return paletteBorder;
	}

	/**
	 * <p>Getter for the field popupMenuBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getPopupMenuBorder() {
		if (popupMenuBorder == null) {
			if (AbstractLookAndFeel.getTheme().isMenuOpaque()) {
				popupMenuBorder = new BasePopupMenuBorder();
			} else {
				popupMenuBorder = new BasePopupMenuShadowBorder();
			}
		}
		return popupMenuBorder;
	}

	/**
	 * <p>Getter for the field progressBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getProgressBarBorder() {
		if (progressBarBorder == null) {
			progressBarBorder = BorderFactory
					.createLineBorder(ColorHelper.darker(AbstractLookAndFeel.getBackgroundColor(), 30));
		}
		return progressBarBorder;
	}

	/**
	 * <p>Getter for the field scrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getScrollPaneBorder() {
		if (scrollPaneBorder == null) {
			scrollPaneBorder = new ScrollPaneBorder(false);
		}
		return scrollPaneBorder;
	}

	/**
	 * <p>Getter for the field spinnerBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getSpinnerBorder() {
		if (spinnerBorder == null) {
			spinnerBorder = new SpinnerBorder();
		}
		return spinnerBorder;
	}

	/**
	 * <p>Getter for the field tabbedPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTabbedPaneBorder() {
		if (tabbedPaneBorder == null) {
			tabbedPaneBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		}
		return tabbedPaneBorder;
	}

	/**
	 * <p>Getter for the field tableHeaderBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTableHeaderBorder() {
		if (tableHeaderBorder == null) {
			tableHeaderBorder = new TableHeaderBorder();
		}
		return tableHeaderBorder;
	}

	/**
	 * <p>Getter for the field tableScrollPaneBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTableScrollPaneBorder() {
		if (tableScrollPaneBorder == null) {
			tableScrollPaneBorder = new ScrollPaneBorder(true);
		}
		return tableScrollPaneBorder;
	}

	// ------------------------------------------------------------------------------------
	// Lazy access methods
	// ------------------------------------------------------------------------------------
	/**
	 * <p>getTextBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTextBorder() {
		if (textFieldBorder == null) {
			textFieldBorder = new TextFieldBorder();
		}
		return textFieldBorder;
	}

	/**
	 * <p>Getter for the field textFieldBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getTextFieldBorder() {
		return getTextBorder();
	}

	/**
	 * <p>Getter for the field toolBarBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToolBarBorder() {
		if (toolBarBorder == null) {
			toolBarBorder = new ToolBarBorder();
		}
		return toolBarBorder;
	}

	/**
	 * <p>Getter for the field toolButtonBorder.</p>
	 *
	 * @return a {@link javax.swing.border.Border} object.
	 */
	public static Border getToolButtonBorder() {
		if (toolButtonBorder == null) {
			toolButtonBorder = new ToolButtonBorder();
		}
		return toolButtonBorder;
	}

	/**
	 * <p>initDefaults.</p>
	 */
	public static void initDefaults() {
		buttonBorder = null;
		textFieldBorder = null;
		spinnerBorder = null;
		comboBoxBorder = null;
		progressBarBorder = null;
		tableHeaderBorder = null;
		popupMenuBorder = null;
		menuItemBorder = null;
		toolBarBorder = null;
		toolButtonBorder = null;
		rolloverToolButtonBorder = null;
		paletteBorder = null;
		internalFrameBorder = null;
		scrollPaneBorder = null;
		tableScrollPaneBorder = null;
		tabbedPaneBorder = null;
		desktopIconBorder = null;
	}

} // end of class BaseBorders
