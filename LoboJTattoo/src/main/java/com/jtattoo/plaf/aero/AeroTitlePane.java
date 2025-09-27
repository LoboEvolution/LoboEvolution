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

package com.jtattoo.plaf.aero;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRootPane;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.base.BaseRootPaneUI;
import com.jtattoo.plaf.base.BaseTitleButton;
import com.jtattoo.plaf.base.BaseTitlePane;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AeroTitlePane class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AeroTitlePane extends BaseTitlePane {

	// ------------------------------------------------------------------------------
	private static class TitleButton extends BaseTitleButton {

		private static final long serialVersionUID = 1L;

		public TitleButton(final Action action, final String accessibleName, final Icon icon) {
			super(action, accessibleName, icon, 1.0f);
		}

		@Override
		public void paint(final Graphics g) {
			final boolean isPressed = getModel().isPressed();
			final boolean isArmed = getModel().isArmed();
			final boolean isRollover = getModel().isRollover();
			final int width = getWidth();
			final int height = getHeight();
			Color[] colors = AbstractLookAndFeel.getTheme().getButtonColors();
			if (isRollover) {
				colors = AbstractLookAndFeel.getTheme().getRolloverColors();
			}
			if (isPressed && isArmed) {
				colors = AbstractLookAndFeel.getTheme().getPressedColors();
			}
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, width, height);
			g.setColor(Color.lightGray);
			g.drawLine(0, 0, 0, height);
			g.drawLine(0, height - 1, width, height - 1);
			g.setColor(Color.white);
			g.drawLine(1, 0, 1, height - 2);
			getIcon().paintIcon(this, g, 1, 0);
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * <p>Constructor for AeroTitlePane.</p>
	 *
	 * @param root a {@link javax.swing.JRootPane} object.
	 * @param ui a {@link BaseRootPaneUI} object.
	 */
	public AeroTitlePane(final JRootPane root, final BaseRootPaneUI ui) {
		super(root, ui);
	}

	/** {@inheritDoc} */
	@Override
	public void createButtons() {
		if (AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn()) {
			super.createButtons();
		} else {
			iconifyButton = new TitleButton(iconifyAction, ICONIFY, iconifyIcon);
			maxButton = new TitleButton(restoreAction, MAXIMIZE, maximizeIcon);
			closeButton = new TitleButton(closeAction, CLOSE, closeIcon);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected int getHorSpacing() {
		return AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn() ? 1 : 0;
	}

	/** {@inheritDoc} */
	@Override
	protected int getVerSpacing() {
		return AbstractLookAndFeel.getTheme().isMacStyleWindowDecorationOn() ? 3 : 0;
	}

	/** {@inheritDoc} */
	@Override
	public void paintBorder(final Graphics g) {
		if (isActive()) {
			g.setColor(ColorHelper.brighter(AbstractLookAndFeel.getWindowTitleColorDark(), 50));
		} else {
			g.setColor(ColorHelper.darker(AbstractLookAndFeel.getWindowInactiveTitleColorDark(), 10));
		}
		g.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
	}

	/** {@inheritDoc} */
	@Override
	public void paintText(final Graphics g, final int x, final int y, final String title) {
		if (isActive()) {
			final Color titleColor = AbstractLookAndFeel.getWindowTitleForegroundColor();
			if (ColorHelper.getGrayValue(titleColor) > 164) {
				g.setColor(Color.black);
			} else {
				g.setColor(Color.white);
			}
			JTattooUtilities.drawString(rootPane, g, title, x + 1, y + 1);
			g.setColor(titleColor);
			JTattooUtilities.drawString(rootPane, g, title, x, y);
		} else {
			g.setColor(AbstractLookAndFeel.getWindowInactiveTitleForegroundColor());
			JTattooUtilities.drawString(rootPane, g, title, x, y);
		}
	}

} // end of class AeroTitlePane
