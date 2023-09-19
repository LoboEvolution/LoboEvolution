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
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.renderer.HtmlController;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * <p>InputText class.</p>
 */
public class InputText extends BasicInput {
	
	private static final Logger logger = Logger.getLogger(InputText.class.getName());

	protected final JTextField iText = new JTextField();
	
	private boolean textWrittenIn;
	
	private final HTMLInputElementImpl modelNode;

	/**
	 * <p>Constructor for InputText.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLInputElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.InputControl} object.
	 */
	public InputText(HTMLInputElementImpl modelNode, InputControl ic) {
		this.modelNode = modelNode;
		setElement(this.modelNode);
		setjComponent(iText);
		final String type = modelNode.getType();
		final Font font = iText.getFont();
		iText.setFont(font.deriveFont(modelNode.getHtmlRendererConfig().getFontSize()));
		iText.setDocument(new LimitedDocument());
		iText.setText(modelNode.getValue());
		iText.setSelectionColor(Color.BLUE);
		iText.setPreferredSize(new Dimension(modelNode.getClientWidth(), modelNode.getClientHeight()));
		final String baseUrl = modelNode.getBaseURI();

		if (modelNode.isAutocomplete()) {
			List<String> list = suggestionList(type, baseUrl);
			if (ArrayUtilities.isNotBlank(list)) {
				Autocomplete.setupAutoComplete(iText, list);
			}
		}

		if (Strings.isNotBlank(modelNode.getPlaceholder())) {
			placeholder(modelNode.getPlaceholder());
		}
		
		iText.setEnabled(!modelNode.isDisabled());
		iText.setEditable(!modelNode.isReadOnly());

		iText.addFocusListener(this);
		iText.addKeyListener(this);
		iText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				modelNode.setValue(modelNode.getValue().concat(String.valueOf(e.getKeyChar())));
			}
		});

		iText.addCaretListener(this);
		iText.addMouseListener(this);
		iText.addActionListener(event -> HtmlController.getInstance().onEnterPressed(modelNode));

		RUIControl ruiControl = ic.getRUIControl();
		final Insets borderInsets = ruiControl.getBorderInsets();
		
		iText.setMargin(new Insets(ruiControl.getMarginTop(), ruiControl.getMarginLeft(), ruiControl.getMarginBottom(), ruiControl.getMarginRight()));
		if (borderInsets.top == 0 && borderInsets.left == 0 && borderInsets.bottom == 0 && borderInsets.right == 0) {
			iText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			iText.setBorder(BorderFactory.createMatteBorder(borderInsets.top, borderInsets.left, borderInsets.bottom, borderInsets.right, Color.BLACK));
		}
		
		if (modelNode.getSelectionStart() > 0 && modelNode.getSelectionEnd() > 0 && modelNode.isFocusable()) {
			SwingUtilities.invokeLater(() -> {
				iText.select(modelNode.getSelectionStart(), modelNode.getSelectionEnd());
				iText.requestFocus();
			});
			
			if (modelNode.getOnselect() != null) {
				Executor.executeFunction(modelNode, modelNode.getOnselect(), null, new Object[] {});
			}
		}

		ic.add(iText);
	}
	
	/**
	 * <p>selectAll.</p>
	 */
	public void selectAll() {
		iText.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		iText.selectAll();
		iText.requestFocus();
	}
	
	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		iText.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		iText.setFocusable(true);
		iText.requestFocus();
	}
	
	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		iText.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		iText.setFocusable(false);
	}
	
	/**
	 * <p>setSelectionRange.</p>
	 *
	 * @param start a int.
	 * @param end a int.
	 */
	public void setSelectionRange(int start, int end) {
		iText.setSelectionStart(start);
		iText.setSelectionEnd(end);
	}
	
	/**
	 * <p>reset.</p>
	 */
	public void reset() {
		iText.setText("");
	}
	
	/**
	 * <p>setText.</p>
	 *
	 * @param value a {@link java.lang.String} object.
	 */
	public void setText(String value) {
		iText.setText(value);
	}
	
	/**
	 * <p>setRangeText.</p>
	 *
	 * @param start a int.
	 * @param end a int.
	 * @param text a {@link java.lang.String} object.
	 */
	public void setRangeText(int start, int end, String text) {
		try {
			iText.getDocument().insertString(start, text, null);
			setSelectionRange(start, end);
		} catch (BadLocationException e) {
			logger.severe(e.getMessage());
		}
	}
	
	private void placeholder(String text) {
		this.customizeText(text);

		iText.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (!isTextWrittenIn()) {
					iText.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (Strings.isBlank(iText.getText())) {
					customizeText(text);
				}
			}
		});
	}

	private void customizeText(String text) {
		iText.setText(text);
		iText.setForeground(new Color(160, 160, 160));
		setTextWrittenIn(false);
	}

	private List<String> suggestionList(String type, String baseUrl) {
		final HtmlRendererConfig config = modelNode.getHtmlRendererConfig();
		List<String> list = config.autocomplete(type, "", baseUrl);
		if (ArrayUtilities.isNotBlank(list))
			return list;
		return new ArrayList<>();
	}

	private class LimitedDocument extends PlainDocument {

		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
			int max = modelNode.getMaxLength();

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
	 * <p>isTextWrittenIn.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isTextWrittenIn() {
		return textWrittenIn;
	}

	/**
	 * <p>Setter for the field textWrittenIn.</p>
	 *
	 * @param textWrittenIn a boolean.
	 */
	public void setTextWrittenIn(boolean textWrittenIn) {
		this.textWrittenIn = textWrittenIn;
	}
}
