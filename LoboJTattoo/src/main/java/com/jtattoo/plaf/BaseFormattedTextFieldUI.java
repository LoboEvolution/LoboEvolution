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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicFormattedTextFieldUI;

/**
 * <p>BaseFormattedTextFieldUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseFormattedTextFieldUI extends BasicFormattedTextFieldUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseFormattedTextFieldUI();
	}

	private Border orgBorder = null;

	private FocusListener focusListener = null;

	/** {@inheritDoc} */
	@Override
	protected void installListeners() {
		super.installListeners();

		if (AbstractLookAndFeel.getTheme().doShowFocusFrame()) {
			focusListener = new FocusListener() {

				@Override
				public void focusGained(final FocusEvent e) {
					if (getComponent() != null) {
						orgBorder = getComponent().getBorder();
						final LookAndFeel laf = UIManager.getLookAndFeel();
						if (laf instanceof AbstractLookAndFeel && orgBorder instanceof UIResource) {
							final Border focusBorder = ((AbstractLookAndFeel) laf).getBorderFactory().getFocusFrameBorder();
							getComponent().setBorder(focusBorder);
						}
						getComponent().invalidate();
						getComponent().repaint();
					}
				}

				@Override
				public void focusLost(final FocusEvent e) {
					if (getComponent() != null) {
						if (orgBorder instanceof UIResource) {
							getComponent().setBorder(orgBorder);
							getComponent().invalidate();
							getComponent().repaint();
						}
					}
				}
			};
			getComponent().addFocusListener(focusListener);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void paintBackground(final Graphics g) {
		g.setColor(getComponent().getBackground());
		if (AbstractLookAndFeel.getTheme().doShowFocusFrame()) {
			if (getComponent().hasFocus() && getComponent().isEditable()) {
				g.setColor(AbstractLookAndFeel.getTheme().getFocusBackgroundColor());
			}
		}
		g.fillRect(0, 0, getComponent().getWidth(), getComponent().getHeight());
	}

	/** {@inheritDoc} */
	@Override
	protected void paintSafely(final Graphics g) {
		final Graphics2D g2D = (Graphics2D) g;
		Object savedRenderingHint = null;
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			savedRenderingHint = g2D.getRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING);
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
					AbstractLookAndFeel.getTheme().getTextAntiAliasingHint());
		}
		super.paintSafely(g);
		if (AbstractLookAndFeel.getTheme().isTextAntiAliasingOn()) {
			g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, savedRenderingHint);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallListeners() {
		getComponent().removeFocusListener(focusListener);
		focusListener = null;
		super.uninstallListeners();
	}

} // end of class BaseFormattedTextFieldUI
