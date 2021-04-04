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
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.control.TextAreaControl;
import org.loboevolution.html.dom.domimpl.HTMLTextAreaElementImpl;
import org.loboevolution.html.js.Executor;

/**
 * <p>TextArea class.</p>
 *
 *
 *
 */
public class TextArea {
	
	private static final float DEFAULT_FONT_SIZE = 14.0f;
	
	private final JTextArea  jtArea = new JTextArea();
	
	private HTMLTextAreaElementImpl modelNode;
	
	/**
	 * <p>Constructor for InputText.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLTextAreaElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.TextAreaControl} object.
	 */
	public TextArea(HTMLTextAreaElementImpl modelNode, TextAreaControl ic) {
		this.modelNode = modelNode;
		this.modelNode = modelNode;
		
		final Font font = jtArea.getFont();
		jtArea.setFont(font.deriveFont(DEFAULT_FONT_SIZE));
		jtArea.setDocument(new LimitedDocument());
		jtArea.setText(modelNode.getValue());
		jtArea.setSelectionColor(Color.BLUE);
		jtArea.setPreferredSize(getPreferredSize());
		
		jtArea.setEnabled(!modelNode.isDisabled());
		jtArea.setEditable(!modelNode.isReadOnly());
		
		MouseInputAdapter mouseHandler = new MouseInputAdapter() {
			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), null, new Object[] {});
				}
			}
		};
		
		jtArea.addMouseListener(mouseHandler);
		
		jtArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent event) {
				String selectedText = jtArea.getSelectedText();
				if(Strings.isNotBlank(selectedText)) {
					Pattern word = Pattern.compile(selectedText);
					Matcher match = word.matcher(modelNode.getValue());
					
					while (match.find()) {
					     modelNode.setSelectionRange(match.start(), match.end()-1);
					}
				}
			}
		});
		
		RUIControl ruiControl = ic.getRUIControl();
		final Insets borderInsets = ruiControl.getBorderInsets();
		
		jtArea.setMargin(new Insets(ruiControl.getMarginTop(), ruiControl.getMarginLeft(), ruiControl.getMarginBottom(), ruiControl.getMarginRight()));
		
		if (borderInsets.top == 0 && borderInsets.left == 0 && borderInsets.bottom == 0 && borderInsets.right == 0) {
			jtArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		} else {
			jtArea.setBorder(BorderFactory.createMatteBorder(borderInsets.top, borderInsets.left, borderInsets.bottom, borderInsets.right, Color.BLACK));
		}
		
		if (modelNode.getSelectionStart() > 0 && modelNode.getSelectionEnd() > 0 && modelNode.isFocusable()) {
			SwingUtilities.invokeLater(() -> {
					jtArea.select(modelNode.getSelectionStart(), modelNode.getSelectionEnd());
					jtArea.requestFocus();
			});
		}

		ic.add(jtArea);
	}
	
	/**
	 * <p>selectAll.</p>
	 */
	public void selectAll() {
		jtArea.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		jtArea.selectAll();
		jtArea.requestFocus();
	}
	
	/**
	 * <p>focus.</p>
	 */
	public void focus() {
		jtArea.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
		jtArea.select(modelNode.getSelectionStart(), modelNode.getSelectionEnd());
		jtArea.requestFocus();
	}
	
	/**
	 * <p>blur.</p>
	 */
	public void blur() {
		jtArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		jtArea.setFocusable(false);
	}
	
	
	private Dimension getPreferredSize() {
		int pw;
		int cols =  modelNode.getCols();
		if (cols == -1) {
			pw = 200;
		} else {
			Font f = this.jtArea.getFont();
			FontMetrics fm = this.jtArea.getFontMetrics(f);
			Insets insets = this.jtArea.getInsets();
			pw = insets.left + insets.right + fm.charWidth('*') * cols;
		}
		int ph;
		int rows = modelNode.getRows();
		if (rows == -1) {
			ph = 100;
		} else {
			Font f = this.jtArea.getFont();
			FontMetrics fm = this.jtArea.getFontMetrics(f);
			Insets insets = this.jtArea.getInsets();
			ph = insets.top + insets.bottom + fm.getHeight() * rows;
		}
		return new Dimension(pw, ph);

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
}
