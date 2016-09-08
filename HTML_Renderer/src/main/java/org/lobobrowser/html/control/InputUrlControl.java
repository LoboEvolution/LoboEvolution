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

/**
 * The Class InputUrlControl.
 */
public class InputUrlControl extends BaseInputTextControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant URL_PATTERN. */
	private static final String URL_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
	
	/** The url. */
	private JTextFieldImpl url;
	
	/** The regex. */
	String regex = "";
	
	/** The str pattern. */
	private String strPattern;

	/**
	 * Instantiates a new input text control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputUrlControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		url = (JTextFieldImpl) this.widget;
		String value = modelNode.getAttribute(HtmlAttributeProperties.VALUE);
		strPattern = modelNode.getAttribute(HtmlAttributeProperties.PATTERN);
		if (!isUrl(value)) {
			url.setBorder(BorderFactory.createLineBorder(Color.RED));
		} else {
			url.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}
		url.addKeyListener(addKeyListener());
	}

	@Override
	protected JTextComponent createTextField() {
		return new JTextFieldImpl();
	}

	private KeyListener addKeyListener() {
		KeyListener keyListener = new KeyListener() {
			public void keyPressed(KeyEvent keyEvent) {

				JTextFieldImpl url = (JTextFieldImpl) keyEvent.getSource();
				if (!isUrl(url.getText())) {
					url.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					url.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}

			public void keyReleased(KeyEvent keyEvent) {
			}

			public void keyTyped(KeyEvent keyEvent) {
			}

		};
		return keyListener;
	}

	private boolean isUrl(String keyCode) {
		if (keyCode != null && keyCode.length() > 0) {
			Pattern pattern = Pattern.compile((strPattern!= null && strPattern.length()>0) ? strPattern : URL_PATTERN);
			Matcher matcher = pattern.matcher(keyCode);
			return matcher.matches();
		} else {
			return true;
		}
	}
}
