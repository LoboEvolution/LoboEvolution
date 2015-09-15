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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.JTextFieldImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;

public class InputEmailControl extends BaseInputTextControl {

	private static final long serialVersionUID = 1L;
	private JTextFieldImpl email;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	
	private String strPattern;

	public InputEmailControl(HTMLBaseInputElement modelNode) {
		super(modelNode);
		email = (JTextFieldImpl) this.widget;
		String value = modelNode.getAttribute(HtmlAttributeProperties.VALUE);
		strPattern = modelNode.getAttribute(HtmlAttributeProperties.PATTERN);
		if (!isEmail(value)) {
			email.setBorder(BorderFactory.createLineBorder(Color.RED));
		} else {
			email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		email.addKeyListener(addKeyListener());
	}

	@Override
	protected JTextComponent createTextField() {
		return new JTextFieldImpl();
	}

	private KeyListener addKeyListener() {
		KeyListener keyListener = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {

				JTextFieldImpl email = (JTextFieldImpl) keyEvent.getSource();
				if (!isEmail(email.getText())) {
					email.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					email.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}

		};
		return keyListener;
	}

	private boolean isEmail(String keyCode) {
		if (keyCode != null && keyCode.length() > 0) {
			Pattern pattern = Pattern.compile((strPattern!= null && strPattern.length()>0) ? strPattern : EMAIL_PATTERN);
			Matcher matcher = pattern.matcher(keyCode);
			return matcher.matches();
		} else {
			return true;
		}
	}
}
