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

package com.jtattoo.plaf.acryl;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;

import com.jtattoo.plaf.AbstractLookAndFeel;
import com.jtattoo.plaf.BaseComboBoxUI;
import com.jtattoo.plaf.ColorHelper;
import com.jtattoo.plaf.JTattooUtilities;

/**
 * <p>AcrylComboBoxUI class.</p>
 *
 * Author Michael Hagen
 *
 */
public class AcrylComboBoxUI extends BaseComboBoxUI {

	/** {@inheritDoc} */
	public static ComponentUI createUI(final JComponent c) {
		return new AcrylComboBoxUI();
	}

	/** {@inheritDoc} */
	@Override
	public JButton createArrowButton() {
		final ArrowButton button = new BaseComboBoxUI.ArrowButton();
		final Color borderColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50);
		if (JTattooUtilities.isLeftToRight(comboBox)) {
			final Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, borderColor);
			button.setBorder(border);
		} else {
			final Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, borderColor);
			button.setBorder(border);
		}
		return button;
	}

	/** {@inheritDoc} */
	@Override
	protected void setButtonBorder() {
		final Color borderColor = ColorHelper.brighter(AbstractLookAndFeel.getTheme().getFrameColor(), 50);
		if (JTattooUtilities.isLeftToRight(comboBox)) {
			final Border border = BorderFactory.createMatteBorder(0, 1, 0, 0, borderColor);
			arrowButton.setBorder(border);
		} else {
			final Border border = BorderFactory.createMatteBorder(0, 0, 0, 1, borderColor);
			arrowButton.setBorder(border);
		}
	}

} // end of class AcrylComboBoxUI
