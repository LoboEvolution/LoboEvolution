/*
 * GNU GENERAL LICENSE
 * Copyright (C) 2014 - 2021 Lobo Evolution
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * verion 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General License for more details.
 *
 * You should have received a copy of the GNU General Public
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact info: ivan.difrancesco@yahoo.it
 */

package org.loboevolution.html.dom.input;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.loboevolution.common.ArrayUtilities;
import org.loboevolution.common.Strings;
import org.loboevolution.component.input.Autocomplete;
import org.loboevolution.html.control.InputControl;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.dom.domimpl.HTMLInputElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.renderer.HtmlController;
import org.loboevolution.store.InputStore;

/**
 * <p>InputText class.</p>
 *
 *
 *
 */
public class InputText {
	
	private static final Logger logger = Logger.getLogger(InputText.class.getName());

	private static final float DEFAULT_FONT_SIZE = 14.0f;
		
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
		final boolean autocomplete = modelNode.getAutocomplete();
		final String type = modelNode.getType();
		
		final Font font = iText.getFont();
		iText.setFont(font.deriveFont(DEFAULT_FONT_SIZE));
		iText.setDocument(new LimitedDocument());
		iText.setText(modelNode.getValue());
		iText.setSelectionColor(Color.BLUE);
		final Dimension ps = iText.getPreferredSize();
		final int size = modelNode.getSize();
		final int width = (128/15) * size;
		iText.setPreferredSize(new Dimension(width, ps.height));
		final String baseUrl = modelNode.getBaseURI();

		if (autocomplete) {
			List<String> list = suggestionList(type, "", baseUrl);
			if (ArrayUtilities.isNotBlank(list)) {
				Autocomplete.setupAutoComplete(iText, list);
			}
		}

		if (Strings.isNotBlank(modelNode.getPlaceholder())) {
			placeholder(modelNode.getPlaceholder());
		}
		
		iText.setEnabled(!modelNode.isDisabled());
		iText.setEditable(!modelNode.isReadOnly());

		iText.addFocusListener(new FocusAdapter() {
			
			@Override
			public void focusGained(FocusEvent e) {
				if (modelNode.getOnfocus() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnfocus(), null, new Object[] {});
				}
				
				if (modelNode.getOnfocusin() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnfocusin(), null, new Object[] {});
				}
			};
			
			
			@Override
			public void focusLost(FocusEvent event) {
				
				String selectedText = iText.getSelectedText();
				if(Strings.isNotBlank(selectedText)) {
					Pattern word = Pattern.compile(selectedText);
					Matcher match = word.matcher(modelNode.getValue());
					
					while (match.find()) {
					     modelNode.setSelectionRange(match.start(), match.end()-1);
					}
				}
				
				if (autocomplete) {
					final String text = iText.getText();
					final boolean isNavigation = modelNode.getUserAgentContext().isNavigationEnabled();
					InputStore.deleteInput(text, baseUrl);
					InputStore.insertLogin(type, text, baseUrl, isNavigation);
				}
				
				if (modelNode.getOnblur() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnblur(), null, new Object[] {});
				}
				
				if (modelNode.getOnfocusout() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnfocusout(), null, new Object[] {});
				}
			}
		});
		
		KeyAdapter key = new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (modelNode.getOnkeyup() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnkeyup(), null, new Object[] {});
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (modelNode.getOnkeydown() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnkeydown(), null, new Object[] {});
				}
				
				if (modelNode.getOnkeypress() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnkeypress(), null, new Object[] {});
				}
				
				if (modelNode.getOninput() != null) {
					Executor.executeFunction(modelNode, modelNode.getOninput(), null, new Object[] {});
				}
			}
		};
		
		
		iText.addKeyListener(key);
		

		iText.addActionListener(event -> HtmlController.getInstance().onEnterPressed(modelNode));

		MouseInputAdapter mouseHandler = new MouseInputAdapter() {

			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), null, new Object[] {});
				}
			}
			
			public void mousePressed(MouseEvent e) {
				if (modelNode.getOnkeypress() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnkeypress(), null, new Object[] {});
				}
				
				if (modelNode.getOnkeydown() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnkeydown(), null, new Object[] {});
				}
			};
			
			public void mouseReleased(MouseEvent e) {
				if (modelNode.getOnkeyup() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnkeyup(), null, new Object[] {});
				}
			};
			
		};
		iText.addMouseListener(mouseHandler);
		
		
		iText.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				final int dot = e.getDot();
				final int mark = e.getMark();

				if (dot != mark && modelNode.getOnselect() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnselect(), null, new Object[] {});
				}
			}
		});

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
	public void setSelectionRange(int start, int end){
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

	private List<String> suggestionList(String type, String text, String baseUrl) {
		List<String> list = InputStore.autocomplete(type, text, baseUrl);
		if (ArrayUtilities.isNotBlank(list))
			return list;
		return new ArrayList<>();
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
