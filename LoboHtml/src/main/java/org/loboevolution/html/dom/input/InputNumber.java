/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.input;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;

/**
 * <p>InputNumber class.</p>
 *
 *
 *
 */
public class InputNumber extends InputText {

	private final JTextField numeric;
	private String min = "";
	private String max = "";

	/**
	 * <p>Constructor for InputNumber.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputNumber(HTMLInputElementImpl modelNode, InputControl ic) {
		super(modelNode, ic);
		numeric = this.iText;
        String value = modelNode.getValue();
        min = modelNode.getAttribute("min");
        max = modelNode.getAttribute("max");
        if (!isNumeric(value)) {
            numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
        } else {
            numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        numeric.addKeyListener(addKeyListener());
	}

	private KeyListener addKeyListener() {
		KeyListener keyListener = new KeyAdapter() {
			public void keyPressed(KeyEvent keyEvent) {
				JTextField num = (JTextField) keyEvent.getSource();
				if (!isNumeric(num.getText())) {
					numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));

					if (Strings.isNotBlank(min) && Strings.isNotBlank(max)) {
						try {
							int intText = Integer.parseInt(num.getText());
							int intMin = Integer.parseInt(min);
							int intMax = Integer.parseInt(max);

							if (intText < intMin || intText > intMax) {
								numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
							} else {
								numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));
							}
						} catch (NumberFormatException ex) {
							numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
						}
					}
				}
			}
		};
		return keyListener;
	}

	private boolean isNumeric(String keyCode) {
		try {
			if (keyCode == null || (keyCode != null && keyCode.length() == 0))
				return true;
			Integer.parseInt(keyCode);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

}
