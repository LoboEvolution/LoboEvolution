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
package org.loboevolution.html.control.input;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.util.Strings;

/**
 * The Class InputEmailControl.
 */
public class InputEmailControl extends BaseInputTextControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant EMAIL_PATTERN. */
	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	/** The str pattern. */
	private String strPattern;

	public InputEmailControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		JTextFieldImpl email = (JTextFieldImpl) this.widget;
		String value = modelNode.getAttribute(VALUE);
		strPattern = modelNode.getAttribute(PATTERN);
		if (!isEmail(value)) {
			email.setBorder(BorderFactory.createLineBorder(Color.RED));
		} else {
			email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		email.addKeyListener(addKeyListener());
	}

	@Override
	protected JAutoTextField createTextField() {
		return new JAutoTextField();
	}

	private KeyListener addKeyListener() {
		KeyAdapter keyAdapter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {

				JTextFieldImpl email = (JTextFieldImpl) keyEvent.getSource();
				if (!isEmail(email.getText())) {
					email.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
		};
		return keyAdapter;
	}

	private boolean isEmail(String keyCode) {
		if (!Strings.isBlank(keyCode)) {
			Pattern pattern = Pattern
					.compile(!Strings.isBlank(strPattern) ? strPattern : EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(keyCode);
			return matcher.matches();
		} else {
			return true;
		}
	}
}
