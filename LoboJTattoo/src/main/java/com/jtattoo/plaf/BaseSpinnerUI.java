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

package com.jtattoo.plaf;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 * <p>BaseSpinnerUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class BaseSpinnerUI extends BasicSpinnerUI {
	// --------------------------------------------------------------------------
	// inner classes
	// --------------------------------------------------------------------------
	private static class MyLayoutManager implements LayoutManager {

		private Component nextButton = null;
		private Component previousButton = null;
		private Component editor = null;

		@Override
		public void addLayoutComponent(String name, Component c) {
			if ("Next".equals(name)) {
				nextButton = c;
			} else if ("Previous".equals(name)) {
				previousButton = c;
			} else if ("Editor".equals(name)) {
				editor = c;
			}
		}

		@Override
		public void layoutContainer(Container parent) {
			int width = parent.getWidth();
			int height = parent.getHeight();

			Insets insets = parent.getInsets();
			Dimension nextD = preferredSize(nextButton);
			Dimension previousD = preferredSize(previousButton);
			int buttonsWidth = Math.max(nextD.width, previousD.width);
			int editorHeight = height - (insets.top + insets.bottom);

			// The arrowButtonInsets value is used instead of the JSpinner's
			// insets if not null. Defining this to be (0, 0, 0, 0) causes the
			// buttons to be aligned with the outer edge of the spinner's
			// border, and leaving it as "null" places the buttons completely
			// inside the spinner's border.
			Insets buttonInsets = UIManager.getInsets("Spinner.arrowButtonInsets");
			if (buttonInsets == null) {
				buttonInsets = insets;
			}

			// Deal with the spinner's componentOrientation property.
			int editorX, editorWidth, buttonsX;
			if (parent.getComponentOrientation().isLeftToRight()) {
				editorX = insets.left;
				editorWidth = width - insets.left - buttonsWidth - buttonInsets.right;
				buttonsX = width - buttonsWidth - buttonInsets.right;
			} else {
				buttonsX = buttonInsets.left;
				editorX = buttonsX + buttonsWidth;
				editorWidth = width - buttonInsets.left - buttonsWidth - insets.right;
			}

			int nextY = buttonInsets.top;
			int nextHeight = height / 2 + height % 2 - nextY;
			int previousY = buttonInsets.top + nextHeight;
			int previousHeight = height - previousY - buttonInsets.bottom;

			setBounds(editor, editorX, insets.top, editorWidth, editorHeight);
			setBounds(nextButton, buttonsX, nextY, buttonsWidth, nextHeight);
			setBounds(previousButton, buttonsX, previousY, buttonsWidth, previousHeight);
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return preferredLayoutSize(parent);
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			Dimension nextD = preferredSize(nextButton);
			Dimension previousD = preferredSize(previousButton);
			Dimension editorD = preferredSize(editor);

			// Force the editors height to be a multiple of 2
			editorD.height = (editorD.height + 1) / 2 * 2;

			Dimension size = new Dimension(editorD.width, editorD.height);
			size.width += Math.max(nextD.width, previousD.width);
			Insets insets = parent.getInsets();
			size.width += insets.left + insets.right;
			size.height += insets.top + insets.bottom + 4;
			return size;
		}

		private Dimension preferredSize(Component c) {
			return c == null ? zeroSize : c.getPreferredSize();
		}

		@Override
		public void removeLayoutComponent(Component c) {
			if (c == nextButton) {
				nextButton = null;
			} else if (c == previousButton) {
				previousButton = null;
			} else if (c == editor) {
				editor = null;
			}
		}

		private void setBounds(Component c, int x, int y, int width, int height) {
			if (c != null) {
				c.setBounds(x, y, width, height);
			}
		}

	} // end of class MyLayoutManager

	// -----------------------------------------------------------------------------------------
// inner classes
//-----------------------------------------------------------------------------------------
	public static class SpinButton extends NoFocusButton {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		private static final Dimension minSize = new Dimension(14, 12);
		private int direction = SwingConstants.NORTH;

		public SpinButton(int aDirection) {
			super();
			setInheritsPopupMenu(true);
			direction = aDirection;
		}

		@Override
		public Dimension getPreferredSize() {
			Dimension size = super.getPreferredSize();
			size.width = Math.max(size.width, minSize.width);
			size.height = Math.max(size.height, minSize.height);
			return size;
		}

		@Override
		public void paint(final Graphics g) {
			Color[] colors;
			if (isEnabled()) {
				if (getModel().isPressed() && getModel().isArmed()) {
					colors = AbstractLookAndFeel.getTheme().getPressedColors();
				} else {
					if (getModel().isRollover()) {
						colors = AbstractLookAndFeel.getTheme().getRolloverColors();
					} else if (JTattooUtilities.isFrameActive(this)) {
						colors = AbstractLookAndFeel.getTheme().getButtonColors();
					} else {
						colors = AbstractLookAndFeel.getTheme().getInActiveColors();
					}
				}
			} else {
				colors = AbstractLookAndFeel.getTheme().getDisabledColors();
			}
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, getWidth(), getHeight());
			paintBorder(g);
			g.setColor(getForeground());
			int w = 4;
			int h = 3;
			int x = (getWidth() - w) / 2;
			int y = (getHeight() - h) / 2;
			if (direction == SwingConstants.NORTH) {
				for (int i = 0; i < h; i++) {
					g.drawLine(x + h - i - 1, y + i, x + w - (h - i) + 1, y + i);
				}
			} else {
				for (int i = 0; i < h; i++) {
					g.drawLine(x + i, y + i, x + w - i, y + i);
				}
			}
		}

	}

	/**
	 * Used by the default LayoutManager class - SpinnerLayout for missing (null)
	 * editor/nextButton/previousButton children.
	 */
	private static final Dimension zeroSize = new Dimension(0, 0);

	/**
	 * {@inheritDoc}
	 *
	 * Returns a new instance of BaseSpinnerUI. SpinnerListUI delegates are
	 * allocated one per JSpinner.
	 * @see ComponentUI#createUI
	 */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseSpinnerUI();
	}

	private MyLayoutManager myLayoutManager = null;

	/**
	 * {@inheritDoc}
	 *
	 * Create a LayoutManager that manages the editor,
	 * nextButton, and previousButton children of the
	 * JSpinner. These three children must be added with a constraint that
	 * identifies their role: "Editor", "Next", and "Previous". The default layout
	 * manager can handle the absence of any of these children.
	 * @see #createNextButton
	 * @see #createPreviousButton
	 * @see #createEditor
	 */
	@Override
	protected LayoutManager createLayout() {
		if (myLayoutManager == null) {
			myLayoutManager = new MyLayoutManager();
		}
		return myLayoutManager;
	}

	/** {@inheritDoc} */
	@Override
	protected Component createNextButton() {
		JButton button = new SpinButton(SwingConstants.NORTH);
		if (JTattooUtilities.isLeftToRight(spinner)) {
			Border border = BorderFactory.createMatteBorder(0, 1, 1, 0, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		} else {
			Border border = BorderFactory.createMatteBorder(0, 0, 1, 1, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		}
		installNextButtonListeners(button);
		return button;
	}

	/** {@inheritDoc} */
	@Override
	protected Component createPreviousButton() {
		JButton button = new SpinButton(SwingConstants.SOUTH);
		if (JTattooUtilities.isLeftToRight(spinner)) {
			Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		} else {
			Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		}
		installPreviousButtonListeners(button);
		return button;
	}

} // end of class BaseSpinnerUI
