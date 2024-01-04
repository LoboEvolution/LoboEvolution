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

package org.loboevolution.html.dom.input;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * <p>InputNumber class.</p>
 */
public class InputNumber extends InputText {

	private final JTextField numeric;
	private final String min;
	private final String max;

	/**
	 * <p>Constructor for InputNumber.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputNumber(final HTMLInputElementImpl modelNode, final InputControl ic) {
		super(modelNode, ic);
		numeric = this.iText;
        final String value = modelNode.getValue();
        min = modelNode.getAttribute("min");
        max = modelNode.getAttribute("max");
        if (!Strings.isNumeric(value)) {
            numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
		numeric.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
        numeric.addKeyListener(addKeyListener());
	}

	private KeyListener addKeyListener() {
        return new KeyAdapter() {
			public void keyPressed(final KeyEvent keyEvent) {
				final JTextField num = (JTextField) keyEvent.getSource();
				if (!Strings.isNumeric(num.getText())) {
					numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));

					if (Strings.isNotBlank(min) && Strings.isNotBlank(max)) {
						try {
							final int intText = Integer.parseInt(num.getText());
							final int intMin = Integer.parseInt(min);
							final int intMax = Integer.parseInt(max);

							if (intText < intMin || intText > intMax) {
								numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
							} else {
								numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							}
						} catch (final NumberFormatException ex) {
							numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
						}
					}
				}
			}
		};
	}
}
