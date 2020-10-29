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
package com.jtattoo.plaf.graphite;

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
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.plaf.UIResource;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseBorders;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>GraphiteBorders class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class GraphiteBorders extends BaseBorders {

//------------------------------------------------------------------------------------
// Inner classes
//------------------------------------------------------------------------------------
	public static class ButtonBorder implements Border, UIResource {

		private static final Color DEFAULT_COLOR_HI = new Color(220, 230, 245);
		private static final Color DEFAULT_COLOR_LO = new Color(212, 224, 243);
		private static final Insets INSETS = new Insets(3, 8, 3, 8);

		@Override
		public Insets getBorderInsets(Component c) {
			return INSETS;
		}

		public Insets getBorderInsets(Component c, Insets borderInsets) {
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
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			Graphics2D g2D = (Graphics2D) g;
			AbstractButton button = (AbstractButton) c;
			Color frameColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 30);
			if (AbstractLookAndFeel.getTheme().doShowFocusFrame() && button.hasFocus()) {
				frameColor = AbstractLookAndFeel.getTheme().getFocusFrameColor();
			}

			if (AbstractLookAndFeel.getTheme().doDrawSquareButtons()) {
				Composite savedComposite = g2D.getComposite();
				AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g2D.setComposite(alpha);
				g2D.setColor(Color.white);
				g2D.drawRect(x, y, w - 1, h - 1);
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
					g2D.drawRect(x, y, w - 2, h - 2);
				}
			} else {
				Object savedRederingHint = g2D.getRenderingHint(RenderingHints.KEY_ANTIALIASING);
				g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				Composite savedComposite = g2D.getComposite();
				AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
				g2D.setComposite(alpha);
				g2D.setColor(Color.white);
				g2D.drawRoundRect(x, y, w - 1, h - 1, 6, 6);
				g2D.setComposite(savedComposite);

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

		public InternalFrameBorder() {
		}

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		@Override
		public Insets getBorderInsets(Component c, Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			int th = getTitleHeight(c);
			Color titleColor = AbstractLookAndFeel.getWindowTitleColorLight();
			Color borderColor = AbstractLookAndFeel.getWindowTitleColorDark();
			Color frameColor = AbstractLookAndFeel.getWindowBorderColor();
			if (!isActive(c)) {
				titleColor = AbstractLookAndFeel.getWindowInactiveTitleColorLight();
				borderColor = AbstractLookAndFeel.getWindowInactiveTitleColorDark();
				frameColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
			}

			g.setColor(titleColor);
			g.fillRect(x, y + 1, w, INSETS.top - 1);
			g.setColor(titleColor);
			g.fillRect(x + 1, y + h - DW, w - 2, DW - 1);

			if (isActive(c)) {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1,
						INSETS.top, DW, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), w - DW - 1,
						INSETS.top, DW, th + 1);
				g.setColor(borderColor);
				JTattooUtilities.fillInverseHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 1,
						INSETS.top + th + 1, DW - 1, h - th - DW);
				JTattooUtilities.fillInverseHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(),
						w - DW, INSETS.top + th + 1, DW - 1, h - th - DW);
			} else {
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1,
						INSETS.top, DW, th + 1);
				JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(),
						w - DW - 1, INSETS.top, DW, th + 1);
				g.setColor(borderColor);
				JTattooUtilities.fillInverseHorGradient(g,
						AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 1, INSETS.top + th + 1, DW - 1,
						h - th - DW);
				JTattooUtilities.fillInverseHorGradient(g,
						AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), w - DW, INSETS.top + th + 1,
						DW - 1, h - th - DW);
			}

			g.setColor(frameColor);
			g.drawRect(x, y, w - 1, h - 1);
			g.drawLine(x + DW - 1, y + INSETS.top + th, x + DW - 1, y + h - DW);
			g.drawLine(x + w - DW, y + INSETS.top + th, x + w - DW, y + h - DW);
			g.drawLine(x + DW - 1, y + h - DW, x + w - DW, y + h - DW);
		}

	} // end of class InternalFrameBorder

	public static class MenuItemBorder extends AbstractBorder implements UIResource {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Insets INSETS = new Insets(2, 2, 2, 2);

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		@Override
		public Insets getBorderInsets(Component c, Insets borderInsets) {
			borderInsets.left = INSETS.left;
			borderInsets.top = INSETS.top;
			borderInsets.right = INSETS.right;
			borderInsets.bottom = INSETS.bottom;
			return borderInsets;
		}

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			JMenuItem b = (JMenuItem) c;
			ButtonModel model = b.getModel();
			Color borderColor = ColorHelper.darker(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 20);
			g.setColor(borderColor);
			if (c.getParent() instanceof JMenuBar) {
				if (model.isArmed() || model.isSelected()) {
					g.drawLine(x, y, x + w - 1, y);
					g.drawLine(x, y + 1, x, y + h - 1);
					g.drawLine(x + w - 1, y + 1, x + w - 1, y + h - 1);
				}
			} else {
				if (model.isArmed() || c instanceof JMenu && model.isSelected()) {
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

		@Override
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			Color borderColor = ColorHelper.darker(AbstractLookAndFeel.getMenuSelectionBackgroundColor(), 20);
			if (JTattooUtilities.isLeftToRight(c)) {
				int dx = getBorderInsets(c).left;
				Color logoColorHi = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColorDark();
				Color logoColorLo = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor();
				Color[] colors = ColorHelper.createColorArr(logoColorHi, logoColorLo, 32);
				JTattooUtilities.fillHorGradient(g, colors, x, y, dx - 1, h - 1);
				paintLogo(c, g, x, y, w, h);
				// - highlight
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
				g.drawLine(x + dx, y + 1, x + w - 2, y + 1);
				// - outer frame
				g.setColor(borderColor);
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
				int dx = getBorderInsets(c).right;
				Color logoColorHi = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColorDark();
				Color logoColorLo = AbstractLookAndFeel.getTheme().getMenuSelectionBackgroundColor();
				Color[] colors = ColorHelper.createColorArr(logoColorHi, logoColorLo, 32);
				JTattooUtilities.fillHorGradient(g, colors, x + w - dx, y, dx, h - 1);
				paintLogo(c, g, x, y, w, h);
				// - highlight
				g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getMenuBackgroundColor(), 40));
				g.drawLine(x + 1, y + 1, x + w - dx - 1, y + 1);
				g.drawLine(x + 1, y + 1, x + 1, y + h - 2);
				// - outer frame
				g.setColor(borderColor);
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

	} // end of class PopupMenuBorder

	public static class RolloverToolButtonBorder implements Border, UIResource {

		private static final Insets INSETS = new Insets(1, 1, 1, 1);

		@Override
		public Insets getBorderInsets(Component c) {
			return new Insets(INSETS.top, INSETS.left, INSETS.bottom, INSETS.right);
		}

		public Insets getBorderInsets(Component c, Insets borderInsets) {
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
		public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
			AbstractButton button = (AbstractButton) c;
			ButtonModel model = button.getModel();
			Color loColor = ColorHelper.brighter(AbstractLookAndFeel.getFrameColor(), 50);
			if (model.isEnabled()) {
				if (model.isPressed() && model.isArmed() || model.isSelected()) {
					Graphics2D g2D = (Graphics2D) g;
					Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f);
					g2D.setComposite(alpha);
					g.setColor(Color.black);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				} else if (model.isRollover()) {
					Graphics2D g2D = (Graphics2D) g;
					Composite composite = g2D.getComposite();
					g.setColor(loColor);
					g.drawRect(x, y, w - 1, h - 1);
					AlphaComposite alpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f);
					g2D.setComposite(alpha);
					g.setColor(Color.white);
					g.fillRect(x + 1, y + 1, w - 2, h - 2);
					g2D.setComposite(composite);
				}
			}
		}

	} // end of class RolloverToolButtonBorder

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
			if (AbstractLookAndFeel.getTheme().isMenuOpaque()) {
				popupMenuBorder = new PopupMenuBorder();
			} else {
				popupMenuBorder = new BasePopupMenuShadowBorder();
			}
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

} // end of class GraphiteBorders
