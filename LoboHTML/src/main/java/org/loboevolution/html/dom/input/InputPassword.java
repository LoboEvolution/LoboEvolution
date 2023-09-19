/*
 * MIT License
 *
 * Copyright (c) 2014 - 2023 LoboEvolution
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

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.config.HtmlRendererConfig;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.renderer.HtmlController;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * <p>InputPassword class.</p>
 */
public class InputPassword extends BasicInput {
	
	private final HTMLInputElementImpl modelNode;
	
	private final JPasswordField pwd = new JPasswordField();

	/**
	 * <p>Constructor for InputPassword.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputPassword(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		setElement(this.modelNode);
		setjComponent(pwd);
		final String type = modelNode.getType();
		
		if (modelNode.getTitle() != null)
			pwd.setToolTipText(modelNode.getTitle());
		
		pwd.setVisible(!modelNode.isHidden());
		pwd.applyComponentOrientation(ic.direction(modelNode.getDir()));
		pwd.setEditable(Boolean.parseBoolean(modelNode.getContentEditable()));
		pwd.setEnabled(!modelNode.isDisabled());
		pwd.setEditable(!modelNode.isReadOnly());
		pwd.setDocument(new LimitedDocument());
		pwd.addActionListener(event -> HtmlController.getInstance().onEnterPressed(modelNode));
		pwd.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
		final String baseUrl = modelNode.getBaseURI();

		List<String> list = suggestionList(type, "", baseUrl);
		Autocomplete.setupAutoComplete(pwd, list);

		pwd.addFocusListener(this);
		pwd.addKeyListener(this);
		pwd.addCaretListener(this);
		pwd.addMouseListener(this);
		ic.add(pwd);
	}
	
	
	private List<String> suggestionList(String type, String text, String baseUrl) {
		final HtmlRendererConfig config = modelNode.getHtmlRendererConfig();
		List<String> list =  config.autocomplete(type, text, baseUrl);
		if (ArrayUtilities.isNotBlank(list)) {
			return list;
		}
		
		list = new ArrayList<>();
		list.add(generatePassword());
		return list;
	}	
	
	private class LimitedDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			int max = (int)modelNode.getMaxLength();

			final int docLength = getLength();
			if (docLength >= max) {
				return;
			}
			final int strLen = str.length();
			if (docLength + strLen > max) {
				final String shorterStr = str.substring(0, max - docLength);
				super.insertString(offs, shorterStr, a);
			} else {
				super.insertString(offs, str, a);
			}
		}
	}

	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		pwd.setText("");
	}
		
	private String generatePassword() {
		try {
			String password = Strings.randomAlphaNumeric(8);
			byte[] salt = SecureRandom.getInstance("SHA1PRNG").generateSeed(32);
			return Base64.getEncoder().encodeToString(salt) + "$" + Strings.hash(password, salt);
		} catch (Exception e) {
			return "";
		}
	}
}
