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

package com.jtattoo.plaf.acryl;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.Toolkit;

import javax.swing.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseRootPaneUI;
import com.jtattoo.plaf.BaseTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AcrylTitlePane class.</p>
 *
 * @author Michael Hagen
 * @version $Id: $Id
 */
public class AcrylTitlePane extends BaseTitlePane {

	// -----------------------------------------------------------------------------------------------
	protected class TitlePaneLayout implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component c) {
		}

		protected int computeHeight() {
			FontMetrics fm = JTattooUtilities.getFontMetrics(AcrylTitlePane.this, null, getFont());
			return fm.getHeight() + 5;
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
			boolean leftToRight = isLeftToRight();

			int spacing = getHorSpacing();
			int w = getWidth();
			int h = getHeight();

			// assumes all buttons have the same dimensions these dimensions include the
			// borders
			int btnHeight = h - getVerSpacing();
			int btnWidth = btnHeight + 10;

			if (menuBar != null) {
				int mw = menuBar.getPreferredSize().width;
				int mh = menuBar.getPreferredSize().height;
				if (leftToRight) {
					menuBar.setBounds(2, (h - mh) / 2, mw, mh);
				} else {
					menuBar.setBounds(getWidth() - mw, (h - mh) / 2, mw, mh);
				}
			}

			int x = leftToRight ? w - spacing : 0;
			int y = Math.max(0, (h - btnHeight) / 2 - 1);

			if (closeButton != null) {
				x += leftToRight ? -btnWidth : spacing;
				closeButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			if (maxButton != null && maxButton.getParent() != null) {
				if (Toolkit.getDefaultToolkit().isFrameStateSupported(BaseRootPaneUI.MAXIMIZED_BOTH)) {
					x += leftToRight ? -spacing - btnWidth : spacing;
					maxButton.setBounds(x, y, btnWidth, btnHeight);
					if (!leftToRight) {
						x += btnWidth;
					}
				}
			}

			if (iconifyButton != null && iconifyButton.getParent() != null) {
				x += leftToRight ? -spacing - btnWidth : spacing;
				iconifyButton.setBounds(x, y, btnWidth, btnHeight);
				if (!leftToRight) {
					x += btnWidth;
				}
			}

			buttonsWidth = leftToRight ? w - x : x;

			if (customTitlePanel != null) {
				int maxWidth = w - buttonsWidth - spacing - 20;
				if (menuBar != null) {
					maxWidth -= menuBar.getPreferredSize().width;
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
			int btnHeight = h - getVerSpacing();
			int btnWidth = btnHeight;

			int x = 0;
			int y = 0;

			if (closeButton != null) {
				closeButton.setBounds(x, y, btnWidth, btnHeight);
				x += btnWidth + spacing;
			}
			if (iconifyButton != null && iconifyButton.getParent() != null) {
				iconifyButton.setBounds(x, y, btnWidth, btnHeight);
				x += btnWidth + spacing;
			}
			if (maxButton != null && maxButton.getParent() != null) {
				if (Toolkit.getDefaultToolkit().isFrameStateSupported(BaseRootPaneUI.MAXIMIZED_BOTH)) {
					maxButton.setBounds(x, y, btnWidth, btnHeight);
					x += btnWidth + spacing;
				}
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
			return preferredLayoutSize(c);
		}

		@Override
		public Dimension preferredLayoutSize(Container c) {
			int height = computeHeight();
			return new Dimension(height, height);
		}

		@Override
		public void removeLayoutComponent(Component c) {
		}

	} // end of class TitlePaneLayout

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for AcrylTitlePane.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 * @param ui a {@link com.jtattoo.plaf.BaseRootPaneUI} object.
	 */
	public AcrylTitlePane(JRootPane root, BaseRootPaneUI ui) {
		super(root, ui);
	}

	/** {@inheritDoc} */
	@Override
	public LayoutManager createLayout() {
		return new TitlePaneLayout();
	}

	/** {@inheritDoc} */
	@Override
	protected int getHorSpacing() {
		return 1;
	}

	/** {@inheritDoc} */
	@Override
	protected int getVerSpacing() {
		return 3;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(Graphics g) {
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowBorderColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveBorderColor());
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(Graphics g, int x, int y, String title) {
		Color shadowColor = AbstractLookAndFeel.getWindowTitleColorDark();
		if (isActive()) {
			shadowColor = ColorHelper.darker(shadowColor, 30);
		}
		g.setColor(shadowColor);
		JTattooUtilities.drawString(rootPane, g, title, x - 1, y - 1);
		JTattooUtilities.drawString(rootPane, g, title, x - 1, y + 1);
		JTattooUtilities.drawString(rootPane, g, title, x + 1, y - 1);
		JTattooUtilities.drawString(rootPane, g, title, x + 1, y + 1);
		if (isActive()) {
			g.setColor(AbstractLookAndFeel.getWindowTitleForegroundColor());
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
		}
		JTattooUtilities.drawString(rootPane, g, title, x, y);
	}

} // end of class AcrylTitlePane
