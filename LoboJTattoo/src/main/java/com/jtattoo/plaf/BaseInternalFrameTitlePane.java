/*
 * Copyright 2003 Sun Microsystems, Inc. All rights reserved.
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

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

package com.jtattoo.plaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

/**
 * This class is a modified copy of the
 * javax.swing.plaf.metal.MetalInternalFrameTitlePane
 *
 * Class that manages a JLF title bar
 * <p>
 *
 * @author Steve Wilson
 * @author Brian Beck
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class BaseInternalFrameTitlePane extends BasicInternalFrameTitlePane implements ActionListener {

	class BasePropertyChangeHandler extends BasicInternalFrameTitlePane.PropertyChangeHandler {

		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			String prop = evt.getPropertyName();
			if (prop.equals(JInternalFrame.IS_SELECTED_PROPERTY)) {
				Boolean b = (Boolean) evt.getNewValue();
				iconButton.putClientProperty(PAINT_ACTIVE, b);
				closeButton.putClientProperty(PAINT_ACTIVE, b);
				maxButton.putClientProperty(PAINT_ACTIVE, b);
				if (b) {
					activateFrame();
				} else {
					deactivateFrame();
				}
				repaint();
			}
			super.propertyChange(evt);
		}
	}

	// ------------------------------------------------------------------------------
// inner classes
//------------------------------------------------------------------------------
	class BaseTitlePaneLayout extends TitlePaneLayout {

		@Override
		public void addLayoutComponent(String name, Component c) {
		}

		@Override
		public void layoutContainer(Container c) {
			if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
				layoutMacStyle(c);
			} else {
				layoutDefault(c);
			}
		}

		public void layoutDefault(Container c) {
			boolean leftToRight = JTattooUtilities.isLeftToRight(frame);

			int spacing = getHorSpacing();
			int w = getWidth();
			int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			int btnHeight = h - getVerSpacing();
			int btnWidth = btnHeight;

			int x = leftToRight ? w - spacing : 0;
			int y = Math.max(0, (h - btnHeight) / 2 - 1);

			if (frame.isClosable()) {
				x += leftToRight ? -btnWidth : spacing;
				closeButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			if (frame.isMaximizable() && !isPalette) {
				x += leftToRight ? -spacing - btnWidth : spacing;
				maxButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			if (frame.isIconifiable() && !isPalette) {
				x += leftToRight ? -spacing - btnWidth : spacing;
				iconButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			buttonsWidth = leftToRight ? w - x : x;

			if (customTitlePanel != null) {
				int maxWidth = w - buttonsWidth - spacing - 20;
				Icon icon = frame.getFrameIcon();
				if (icon != null) {
					maxWidth -= icon.getIconWidth();
					maxWidth -= spacing;
				}
				int cpw = Math.min(maxWidth, customTitlePanel.getPreferredSize().width);
				int cph = h;
				int cpx = leftToRight ? w - buttonsWidth - cpw : buttonsWidth;
				int cpy = 0;
				customTitlePanel.setBounds(cpx, cpy, cpw, cph);
				buttonsWidth += customTitlePanel.getPreferredSize().width;
			}
		}

		private void layoutMacStyle(Container c) {
			int spacing = getHorSpacing();
			int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			int btnHeight = h - getVerSpacing() - 1;
			int btnWidth = btnHeight;

			int x = 2;
			int y = centerButtons() ? Math.max(0, (h - btnHeight) / 2) : 0;

			if (frame.isClosable()) {
				closeButton.setBounds(x, y, btnWidth, btnHeight);
				x += spacing + btnWidth;
			}
			if (frame.isIconifiable() && !isPalette) {
				iconButton.setBounds(x, y, btnWidth, btnHeight);
				x += spacing + btnWidth;
			}
			if (frame.isMaximizable() && !isPalette) {
				maxButton.setBounds(x, y, btnWidth, btnHeight);
				x += spacing + btnWidth;
			}

			buttonsWidth = x;

			if (customTitlePanel != null) {
				int cpx = buttonsWidth + 5;
				int cpy = 0;
				int cpw = customTitlePanel.getPreferredSize().width;
				int cph = h;
				customTitlePanel.setBounds(cpx, cpy, cpw, cph);
				buttonsWidth += cpw + 5;
			}
		}

		@Override
		public Dimension minimumLayoutSize(Container c) {
			int width = 30;
			if (frame.isClosable()) {
				width += 21;
			}
			if (frame.isMaximizable()) {
				width += 16 + (frame.isClosable() ? 10 : 4);
			}
			if (frame.isIconifiable()) {
				width += 16 + (frame.isMaximizable() ? 2 : frame.isClosable() ? 10 : 4);
			}
			FontMetrics fm = JTattooUtilities.getFontMetrics(BaseInternalFrameTitlePane.this, null, getFont());
			String frameTitle = frame.getTitle();
			int title_w = frameTitle != null ? fm.stringWidth(frameTitle) : 0;
			int title_length = frameTitle != null ? frameTitle.length() : 0;

			if (title_length > 2) {
				int subtitle_w = fm.stringWidth(frame.getTitle().substring(0, 2) + "...");
				width += title_w < subtitle_w ? title_w : subtitle_w;
			} else {
				width += title_w;
			}

			int height = paletteTitleHeight;
			if (!isPalette) {
				Icon icon = isMacStyleWindowDecoration() ? null : frame.getFrameIcon();
				if (icon == null) {
					height = Math.max(fm.getHeight() + 6, 16);
				} else {
					height = Math.max(fm.getHeight() + 6, Math.min(icon.getIconHeight(), 24));
				}
			}
			return new Dimension(width, height);
		}

		@Override
		public Dimension preferredLayoutSize(Container c) {
			return minimumLayoutSize(c);
		}

		@Override
		public void removeLayoutComponent(Component c) {
		}
	} // end of class BaseTitlePaneLayout

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	/** Constant PAINT_ACTIVE="paintActive" */
	public static final String PAINT_ACTIVE = "paintActive";
	/** Constant ICONIFY="Iconify" */
	public static final String ICONIFY = "Iconify";
	/** Constant MAXIMIZE="Maximize" */
	public static final String MAXIMIZE = "Maximize";
	/** Constant CLOSE="Close" */
	public static final String CLOSE = "Close";
	protected boolean isPalette = false;
	protected Icon paletteCloseIcon;
	protected int paletteTitleHeight;

	protected int buttonsWidth = 0;

	protected JPanel customTitlePanel;

	/**
	 * <p>Constructor for BaseInternalFrameTitlePane.</p>
	 *
	 * @param f a {@link javax.swing.JInternalFrame} object.
	 */
	public BaseInternalFrameTitlePane(JInternalFrame f) {
		super(f);
	}

	/** {@inheritDoc} */
	@Override
	public void actionPerformed(ActionEvent e) {
		AbstractButton button = (AbstractButton) e.getSource();
		button.getModel().setRollover(false);
	}

	/**
	 * <p>activateFrame.</p>
	 */
	public void activateFrame() {
	}

	/** {@inheritDoc} */
	@Override
	protected void addSubComponents() {
		add(iconButton);
		add(maxButton);
		add(closeButton);
	}

	/** {@inheritDoc} */
	@Override
	protected void addSystemMenuItems(JMenu systemMenu) {
	}

	/** {@inheritDoc} */
	@Override
	protected void assembleSystemMenu() {
	}

	/**
	 * <p>centerButtons.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean centerButtons() {
		return true;
	}

	/** {@inheritDoc} */
	@Override
	protected void createButtons() {
		iconButton = new BaseTitleButton(iconifyAction, ICONIFY, iconIcon, 1.0f);
		maxButton = new BaseTitleButton(maximizeAction, MAXIMIZE, maxIcon, 1.0f);
		closeButton = new BaseTitleButton(closeAction, CLOSE, closeIcon, 1.0f);
		setButtonIcons();
	}

	/** {@inheritDoc} */
	@Override
	protected LayoutManager createLayout() {
		return new BaseTitlePaneLayout();
	}

	/** {@inheritDoc} */
	@Override
	protected PropertyChangeListener createPropertyChangeListener() {
		return new BasePropertyChangeHandler();
	}

	/**
	 * <p>deactivateFrame.</p>
	 */
	public void deactivateFrame() {
	}

	/** {@inheritDoc} */
	@Override
	protected void enableActions() {
		super.enableActions();
		maximizeAction.setEnabled(frame.isMaximizable());
	}

	/**
	 * <p>getHorSpacing.</p>
	 *
	 * @return a int.
	 */
	protected int getHorSpacing() {
		return 3;
	}

	/**
	 * <p>getIconWidth.</p>
	 *
	 * @return a int.
	 */
	protected int getIconWidth() {
		Image image = iconToImage(frame.getFrameIcon());
		if (image != null) {
			int h = getHeight();
			int ih = image.getHeight(null);
			int iw = image.getWidth(null);
			if (ih > h) {
				double fac = (double) iw / (double) ih;
				ih = h - 1;
				iw = (int) (fac * ih);
			}
			return iw;
		}
		return 0;
	}

	/**
	 * <p>getVerSpacing.</p>
	 *
	 * @return a int.
	 */
	protected int getVerSpacing() {
		return 3;
	}

	private Image iconToImage(Icon icon) {
		if (icon instanceof ImageIcon) {
			return ((ImageIcon) icon).getImage();
		} else if (icon != null) {
			int w = icon.getIconWidth();
			int h = icon.getIconHeight();
			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			icon.paintIcon(null, g, 0, 0);
			g.dispose();
			return image;
		}
		return null;
	}

	/** {@inheritDoc} */
	@Override
	protected void installDefaults() {
		super.installDefaults();
		setFont(UIManager.getFont("InternalFrame.font"));
		paletteTitleHeight = UIManager.getInt("InternalFrame.paletteTitleHeight");
		paletteCloseIcon = UIManager.getIcon("InternalFrame.paletteCloseIcon");
		iconIcon = UIManager.getIcon("InternalFrame.iconifyIcon");
		minIcon = UIManager.getIcon("InternalFrame.minimizeIcon");
		maxIcon = UIManager.getIcon("InternalFrame.maximizeIcon");
		closeIcon = UIManager.getIcon("InternalFrame.closeIcon");
		if (frame.getClientProperty("customTitlePanel") instanceof JPanel) {
			setCustomizedTitlePanel((JPanel) frame.getClientProperty("customTitlePanel"));
		}
	}

	/**
	 * <p>isActive.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isActive() {
		return JTattooUtilities.isActive(this);
	}

	/**
	 * <p>isMacStyleWindowDecoration.</p>
	 *
	 * @return a boolean.
	 */
	protected boolean isMacStyleWindowDecoration() {
		return AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn();
	}

	/**
	 * <p>isPalette.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isPalette() {
		return isPalette;
	}

	/**
	 * <p>paintBackground.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	public void paintBackground(Graphics g) {
		if (isActive()) {
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 0, 0, getWidth(),
					getHeight());
		} else {
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 0, 0,
					getWidth(), getHeight());
		}
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		Color borderColor = AbstractLookAndFeel.getWindowInactiveBorderColor();
		if (isActive() || isPalette) {
			borderColor = AbstractLookAndFeel.getWindowBorderColor();
		}
		JTattooUtilities.draw3DBorder(g, ColorHelper.brighter(borderColor, 20), ColorHelper.darker(borderColor, 10), 0,
				0, getWidth(), getHeight());
	}

	/** {@inheritDoc} */
	@Override
	public void paintComponent(Graphics g) {
		if (isPalette) {
			paintPalette(g);
			return;
		}

		paintBackground(g);

		g.setFont(getFont());
		FontMetrics fm = JTattooUtilities.getFontMetrics(this, g, getFont());
		int width = getWidth();
		int height = getHeight();
		int x = 0;
		int y = (height - fm.getHeight()) / 2 + fm.getAscent();
		int titleWidth = width - buttonsWidth - 4;
		String frameTitle = frame.getTitle();
		if (JTattooUtilities.isLeftToRight(frame)) {
			if (isMacStyleWindowDecoration()) {
				int iconWidth = getIconWidth();
				titleWidth -= iconWidth + 4;
				frameTitle = JTattooUtilities.getClippedText(frameTitle, fm, titleWidth);
				int titleLength = fm.stringWidth(frameTitle);
				x += buttonsWidth + (titleWidth - titleLength) / 2;
				paintIcon(g, x);
				x += iconWidth + 4;
			} else {
				int iconWidth = paintIcon(g, x);
				titleWidth -= iconWidth + 4;
				frameTitle = JTattooUtilities.getClippedText(frameTitle, fm, titleWidth);
				if (AbstractLookAndFeel.getTheme().isCenterWindowTitleOn()) {
					int titleLength = fm.stringWidth(frameTitle);
					x += iconWidth + 4;
					x += (titleWidth - titleLength) / 2;
				} else {
					x += iconWidth + 4;
				}
			}
		} else {
			int iconWidth = getIconWidth();
			if (isMacStyleWindowDecoration()) {
				titleWidth -= iconWidth + 4;
				frameTitle = JTattooUtilities.getClippedText(frameTitle, fm, titleWidth);
				int titleLength = fm.stringWidth(frameTitle);
				x = buttonsWidth + 4 + (titleWidth - titleLength) / 2;
				paintIcon(g, x + titleLength + 4);
			} else {
				x = width - iconWidth;
				paintIcon(g, x);
				titleWidth -= iconWidth + 4;
				frameTitle = JTattooUtilities.getClippedText(frameTitle, fm, titleWidth);
				int titleLength = fm.stringWidth(frameTitle);
				if (AbstractLookAndFeel.getTheme().isCenterWindowTitleOn()) {
					x = buttonsWidth + 4 + (titleWidth - titleLength) / 2;
				} else {
					x = width - iconWidth - 4 - titleLength;
				}
			}
		}
		paintText(g, x, y, frameTitle);

		paintBorder(g);
	}

	/**
	 * <p>paintIcon.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x a int.
	 * @return a int.
	 */
	protected int paintIcon(Graphics g, int x) {
		Image image = iconToImage(frame.getFrameIcon());
		if (image != null) {
			Graphics2D g2D = (Graphics2D) g;
			Object savedHint = g2D.getRenderingHint(RenderingHints.KEY_INTERPOLATION);
			g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			int h = getHeight();
			int ih = image.getHeight(null);
			int iw = image.getWidth(null);
			if (ih <= h) {
				g2D.drawImage(image, x, (h - ih) / 2, iw, ih, null);
			} else {
				double fac = (double) iw / (double) ih;
				ih = h;
				iw = (int) (fac * ih);
				g2D.drawImage(image, x, 0, iw, ih, null);
			}
			if (savedHint != null) {
				g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, savedHint);
			}
			return iw;
		}
		return 0;
	}

	/**
	 * <p>paintPalette.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 */
	public void paintPalette(Graphics g) {
		int width = getWidth();
		int height = getHeight();
		if (JTattooUtilities.isFrameActive(this)) {
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowTitleColors(), 0, 0, width,
					height);
		} else {
			JTattooUtilities.fillHorGradient(g, AbstractLookAndFeel.getTheme().getWindowInactiveTitleColors(), 0, 0,
					width, height);
		}
	}

	/**
	 * <p>paintText.</p>
	 *
	 * @param g a {@link java.awt.Graphics} object.
	 * @param x a int.
	 * @param y a int.
	 * @param title a {@link java.lang.String} object.
	 */
	public void paintText(Graphics g, int x, int y, String title) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
		}
		JTattooUtilities.drawString(frame, g, title, x, y);
	}

	/** {@inheritDoc} */
	@Override
	protected void setButtonIcons() {
		super.setButtonIcons();
		iconButton.setToolTipText(null);
		maxButton.setToolTipText(null);
		closeButton.setToolTipText(null);
	}

	/**
	 * <p>setCustomizedTitlePanel.</p>
	 *
	 * @param panel a {@link javax.swing.JPanel} object.
	 */
	public void setCustomizedTitlePanel(JPanel panel) {
		if (customTitlePanel != null) {
			remove(customTitlePanel);
			customTitlePanel = null;
		}
		if (panel != null) {
			customTitlePanel = panel;
			add(customTitlePanel);
		}
		frame.putClientProperty("customTitlePanel", customTitlePanel);
		revalidate();
		repaint();
	}

	/**
	 * <p>setPalette.</p>
	 *
	 * @param b a boolean.
	 */
	public void setPalette(boolean b) {
		isPalette = b;
		if (isPalette) {
			closeButton.setIcon(paletteCloseIcon);
			if (frame.isMaximizable()) {
				remove(maxButton);
			}
			if (frame.isIconifiable()) {
				remove(iconButton);
			}
		} else {
			closeButton.setIcon(closeIcon);
			if (frame.isMaximizable()) {
				add(maxButton);
			}
			if (frame.isIconifiable()) {
				add(iconButton);
			}
		}
		revalidate();
		repaint();
	}

} // end of class BaseInternalFrameTitlePane
