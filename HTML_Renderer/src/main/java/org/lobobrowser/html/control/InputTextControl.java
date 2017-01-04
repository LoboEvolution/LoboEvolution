/*
    GNU GENERAL LICENSE
    Copyright (C) 2006 The Lobo Project. Copyright (C) 2014 - 2017 Lobo Evolution

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
import java.awt.ComponentOrientation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.text.JTextComponent;

import org.lobobrowser.html.HtmlAttributeProperties;
import org.lobobrowser.html.dombl.JTextFieldImpl;
import org.lobobrowser.html.domimpl.HTMLBaseInputElement;
import org.lobobrowser.html.renderer.HtmlController;

/**
 * The Class InputTextControl.
 */
public class InputTextControl extends BaseInputTextControl {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The str pattern. */
	private String strPattern = "";
	
	/**
	 * Instantiates a new input text control.
	 *
	 * @param modelNode
	 *            the model node
	 */
	public InputTextControl(final HTMLBaseInputElement modelNode) {
		super(modelNode);
		JTextFieldImpl text = (JTextFieldImpl) this.widget;

		if (modelNode.getTitle() != null) {
			text.setToolTipText(modelNode.getTitle());
		}

		text.setVisible(modelNode.getHidden());
		text.applyComponentOrientation(direction(modelNode.getDir()));
		text.setEditable(new Boolean(modelNode.getContentEditable() == null ? "true" : modelNode.getContentEditable()));
		text.setEnabled(!modelNode.getDisabled());		
		text.setPlaceholder(modelNode.getPlaceholder());
		text.setSelectionColor(Color.BLUE);
		strPattern = modelNode.getAttribute(HtmlAttributeProperties.PATTERN);

		if (!match(modelNode.getAttribute(HtmlAttributeProperties.VALUE), strPattern)) {
			text.setBorder(BorderFactory.createLineBorder(Color.RED));
		} else {
			text.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		}

		text.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				HtmlController.getInstance().onEnterPressed(modelNode, null);
			}
		});
		text.addKeyListener(addKeyListener());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.lobobrowser.html.render.BaseInputTextControl#createTextField(java.
	 * lang .String)
	 */
	@Override
	protected JTextComponent createTextField() {
		return new JTextFieldImpl();
	}
	
	@Override
	public void reset(int availWidth, int availHeight) {
		super.reset(availWidth, availHeight);
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

				JTextFieldImpl url = (JTextFieldImpl) keyEvent.getSource();
				if (!match(url.getText(), strPattern)) {
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
