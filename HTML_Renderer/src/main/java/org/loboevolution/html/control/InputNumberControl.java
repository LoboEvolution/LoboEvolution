/*
    GNU GENERAL LICENSE
    Copyright (C) 2014 - 2018 Lobo Evolution

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU General Public
    License as published by the Free Software Foundation; either
    verion 3 of the License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
    General License for more details.

    You should have received a copy of the GNU General Public
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    

    Contact info: ivan.difrancesco@yahoo.it
 */
package org.loboevolution.html.control;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

import org.loboevolution.html.dombl.JTextFieldImpl;
import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.util.Strings;

/**
 * The Class InputNumberControl.
 */
public class InputNumberControl extends BaseInputTextControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The numeric. */
	private JTextFieldImpl numeric;

	/** The min. */
	private String min = "";

	/** The max. */
	private String max = "";

	public InputNumberControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		numeric = (JTextFieldImpl) this.widget;
		String value = modelNode.getAttribute(VALUE);
		min = modelNode.getAttribute(MIN);
		max = modelNode.getAttribute(MAX);
		if (!isNumeric(value)) {
			numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
		} else {
			numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		numeric.addKeyListener(addKeyListener());
	}

	@Override
	protected JTextComponent createTextField() {
		return new JTextFieldImpl();
	}

	private KeyListener addKeyListener() {
		KeyListener keyListener = new KeyListener() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {

				JTextFieldImpl num = (JTextFieldImpl) keyEvent.getSource();

				if (!isNumeric(num.getText())) {
					numeric.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					numeric.setBorder(BorderFactory.createLineBorder(Color.BLACK));

					if (!Strings.isBlank(min) && !Strings.isBlank(max)) {
						try {
							int intText = new Integer(num.getText());
							int intMin = new Integer(min);
							int intMax = new Integer(max);

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

			@Override
			public void keyReleased(KeyEvent keyEvent) {
			}

			@Override
			public void keyTyped(KeyEvent keyEvent) {
			}

		};
		return keyListener;
	}

	private boolean isNumeric(String keyCode) {
		try {
			if (keyCode == null || keyCode != null && keyCode.length() == 0) {
				return true;
			}
			Integer.parseInt(keyCode);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
