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

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.UIResource;
import javax.swing.plaf.basic.BasicComboBoxUI;

/**
 * <p>BaseComboBoxUI class.</p>
 *
 *
 *
 */
public class BaseComboBoxUI extends BasicComboBoxUI {

	public static class ArrowButton extends NoFocusButton {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void paint(final Graphics g) {
			Dimension size = getSize();
			Color[] colors;
			if (isEnabled()) {
				if (getModel().isArmed() && getModel().isPressed()) {
					colors = AbstractLookAndFeel.getTheme().getPressedColors();
				} else if (getModel().isRollover()) {
					colors = AbstractLookAndFeel.getTheme().getRolloverColors();
				} else {
					colors = AbstractLookAndFeel.getTheme().getButtonColors();
				}
			} else {
				colors = AbstractLookAndFeel.getTheme().getDisabledColors();
			}
			JTattooUtilities.fillHorGradient(g, colors, 0, 0, size.width, size.height);

			boolean inverse = ColorHelper.getGrayValue(colors) < 128;

			Icon icon = inverse ? BaseIcons.getComboBoxInverseIcon() : BaseIcons.getComboBoxIcon();
			int x = (size.width - icon.getIconWidth()) / 2;
			int y = (size.height - icon.getIconHeight()) / 2;

			Graphics2D g2D = (Graphics2D) g;
			Composite savedComposite = g2D.getComposite();
			g2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			if (getModel().isPressed() && getModel().isArmed()) {
				icon.paintIcon(this, g, x + 2, y + 1);
			} else {
				icon.paintIcon(this, g, x + 1, y);
			}
			g2D.setComposite(savedComposite);
			paintBorder(g2D);

		}
	}

	public class PropertyChangeHandler implements PropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent e) {
			String name = e.getPropertyName();
			if (name.equals("componentOrientation")) {
				setButtonBorder();
			}
		}
	}

//-----------------------------------------------------------------------------    
	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new BaseComboBoxUI();
	}

	private PropertyChangeListener myPpropertyChangeListener = null;

	private FocusListener myFocusListener = null;

	private Border orgBorder = null;

	private Color orgBackgroundColor = null;

	/** {@inheritDoc} */
	@Override
	public JButton createArrowButton() {
		JButton button = new ArrowButton();
		if (JTattooUtilities.isLeftToRight(comboBox)) {
			Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		} else {
			Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, AbstractLookAndFeel.getFrameColor());
			button.setBorder(border);
		}
		return button;
	}

	/** {@inheritDoc} */
	@Override
	public Dimension getPreferredSize(JComponent c) {
		Dimension size = super.getPreferredSize(c);
		if (comboBox.getGraphics() != null) {
			FontMetrics fm = JTattooUtilities.getFontMetrics(comboBox, comboBox.getGraphics(), comboBox.getFont());
			size.height = fm.getHeight() + 2;
			if (UIManager.getLookAndFeel() instanceof AbstractLookAndFeel) {
				AbstractLookAndFeel laf = (AbstractLookAndFeel) UIManager.getLookAndFeel();
				size.height = Math.max(size.height, laf.getIconFactory().getDownArrowIcon().getIconHeight() + 2);
			}
		}
		return new Dimension(size.width + 2, size.height + 2);
	}

	/** {@inheritDoc} */
	@Override
	protected void installListeners() {
		super.installListeners();
		myPpropertyChangeListener = new PropertyChangeHandler();
		comboBox.addPropertyChangeListener(myPpropertyChangeListener);

		if (AbstractLookAndFeel.getTheme().doShowFocusFrame()) {
			myFocusListener = new FocusListener() {

				@Override
				public void focusGained(FocusEvent e) {
					if (comboBox != null) {
						orgBorder = comboBox.getBorder();
						orgBackgroundColor = comboBox.getBackground();
						LookAndFeel laf = UIManager.getLookAndFeel();
						if (laf instanceof AbstractLookAndFeel) {
							if (orgBorder instanceof UIResource) {
								Border focusBorder = ((AbstractLookAndFeel) laf).getBorderFactory()
										.getFocusFrameBorder();
								comboBox.setBorder(focusBorder);
							}
							Color backgroundColor = AbstractLookAndFeel.getTheme().getFocusBackgroundColor();
							comboBox.setBackground(backgroundColor);
						}
					}
				}

				@Override
				public void focusLost(FocusEvent e) {
					if (comboBox != null) {
						if (orgBorder instanceof UIResource) {
							comboBox.setBorder(orgBorder);
						}
						comboBox.setBackground(orgBackgroundColor);
					}
				}
			};
			comboBox.addFocusListener(myFocusListener);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		comboBox.setRequestFocusEnabled(true);
		comboBox.setLightWeightPopupEnabled(false);
		if (comboBox.getEditor() != null) {
			if (comboBox.getEditor().getEditorComponent() instanceof JTextField) {
				((JTextField) comboBox.getEditor().getEditorComponent())
						.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
			}
		}
	}

	/**
	 * <p>setButtonBorder.</p>
	 */
	protected void setButtonBorder() {
		if (JTattooUtilities.isLeftToRight(comboBox)) {
			Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, AbstractLookAndFeel.getFrameColor());
			arrowButton.setBorder(border);
		} else {
			Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, AbstractLookAndFeel.getFrameColor());
			arrowButton.setBorder(border);
		}
	}

	/** {@inheritDoc} */
	@Override
	protected void uninstallListeners() {
		comboBox.removePropertyChangeListener(myPpropertyChangeListener);
		comboBox.removeFocusListener(myFocusListener);
		myPpropertyChangeListener = null;
		myFocusListener = null;
		super.uninstallListeners();
	}

} // end of class BaseComboBoxUI
