/*
 * GNU LESSER GENERAL PUBLIC LICENSE Copyright (C) 2006 The Lobo Project.
 * Copyright (C) 2014 - 2015 Lobo Evolution This library is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser
 * General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version. This
 * library is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details. You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA
 * Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.JTextFieldImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

public class InputNumberControl extends BaseInputTextControl {

	private static final long serialVersionUID = 1L;

	private JTextFieldImpl numeric;
	private String min = "";
	private String max = "";

	public InputNumberControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		numeric = (JTextFieldImpl) this.widget;
		String value = modelNode.getAttribute(HtmlAttributeProperties.VALUE);
		min = modelNode.getAttribute(HtmlAttributeProperties.MIN);
		max = modelNode.getAttribute(HtmlAttributeProperties.MAX);
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

					if ((min != null && min.length() > 0) && (max != null && max.length() > 0)) {
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
			if (keyCode == null || (keyCode != null && keyCode.length() == 0)) {
				return true;
			}
			Integer.parseInt(keyCode);
			return true;
		} catch (Exception ex) {
			return false;
		}
	}
}
