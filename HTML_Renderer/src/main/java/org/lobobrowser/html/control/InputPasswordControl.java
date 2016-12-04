/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2016 Lobo Evolution

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
    

    Contact info: lobochief@users.sourceforge.net; ivan.difrancesco@yahoo.it
 */
/*
 * Created on Jan 15, 2006
 */
package org.lobobrowser.html.control;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JPasswordField;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.renderer.HtmlController;

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
		JPasswordField pwd = (JPasswordField) this.widget;

		if (modelNode.getTitle() != null) {
			pwd.setToolTipText(modelNode.getTitle());
		}

		pwd.setVisible(modelNode.getHidden());
		pwd.applyComponentOrientation(direction(modelNode.getDir()));
		pwd.setEditable(Boolean.valueOf(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		pwd.setEnabled(!modelNode.getDisabled());
		strPattern = modelNode.getAttribute(HtmlAttributeProperties.PATTERN);
		if (!match(modelNode.getAttribute(HtmlAttributeProperties.VALUE), strPattern)) {
			pwd.setBorder(BorderFactory.createLineBorder(Color.RED));
		} else {
			pwd.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		pwd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				HtmlController.getInstance().onEnterPressed(modelNode, null);
			}
		});
		pwd.addKeyListener(addKeyListener());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.lobobrowser.html.render.InputTextControl#createTextField(String
	 * )
	 */
	@Override
	protected JTextComponent createTextField() {
		return new JPasswordField();
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
		KeyListener keyListener = new KeyListener() {
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

			@Override
			public void keyReleased(KeyEvent keyEvent) {
			}

			@Override
			public void keyTyped(KeyEvent keyEvent) {
			}

		};
		return keyListener;
	}

	private boolean match(String value, String strPattern) {
		if (value != null && value.length() > 0 && strPattern != null && strPattern.length() > 0) {
			Pattern pattern = Pattern.compile(strPattern);
			Matcher matcher = pattern.matcher(value);
			return matcher.matches();
		} else {
			return true;
		}
	}
}
