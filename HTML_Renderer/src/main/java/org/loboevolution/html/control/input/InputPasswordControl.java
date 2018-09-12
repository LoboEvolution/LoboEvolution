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
/*
 * 
 */
package org.loboevolution.html.control.input;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;

import org.loboevolution.html.domimpl.HTMLBaseInputElement;
import org.loboevolution.util.Strings;


/**
 * The Class InputPasswordControl.
 */
public class InputPasswordControl extends BaseInputTextControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The str pattern. */
	private String strPattern;

	/**
	 * Instantiates a new input password control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputPasswordControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		JTextComponent pwd = (JTextComponent) this.widget;

		if (modelNode.getTitle() != null) {
			pwd.setToolTipText(modelNode.getTitle());
		}

		pwd.setVisible(modelNode.getHidden());
		pwd.applyComponentOrientation(direction(modelNode.getDir()));
		pwd.setEditable(Boolean.valueOf(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		pwd.setEnabled(!modelNode.getDisabled());
		strPattern = modelNode.getAttribute(PATTERN);
		if (!match(modelNode.getAttribute(VALUE), strPattern)) {
			pwd.setBorder(BorderFactory.createLineBorder(Color.RED));
		} else {
			pwd.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		//pwd.addActionListener(event -> GuiMouseImpl.getInstance().onEnterPressed(modelNode, null));
		pwd.addKeyListener(addKeyListener());
	}
	
	@Override
	protected JAutoTextField createTextField() {
		// TODO Auto-generated method stub
		return null;
	}

	

	/**
	 * Direction.
	 *
	 * @param dir
	 *            the dir
	 * @return the component orientation
	 */
	private ComponentOrientation direction(String dir) {

		if ("ltr".equalsIgnoreCase(dir)) {
			return ComponentOrientation.LEFT_TO_RIGHT;
		} else if ("rtl".equalsIgnoreCase(dir)) {
			return ComponentOrientation.RIGHT_TO_LEFT;
		} else {
			return ComponentOrientation.UNKNOWN;
		}
	}

	private KeyListener addKeyListener() {
		KeyAdapter keyAdapter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent keyEvent) {

				JPasswordField url = (JPasswordField) keyEvent.getSource();
				String srtPwd = "";
				for (int i = 0; i < url.getPassword().length; i++) {
					srtPwd += url.getPassword()[i];
				}

				if (!match(srtPwd, strPattern)) {
					url.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					url.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
		};
		return keyAdapter;
	}

	private boolean match(String value, String strPattern) {
		if (!Strings.isBlank(value) && !Strings.isBlank(strPattern)) {
			Pattern pattern = Pattern.compile(strPattern);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		} else {
			return true;
		}
	}
}
