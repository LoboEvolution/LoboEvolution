/*
 * MIT License
 *
 * Copyright (c) 2014 - 2025 LoboEvolution
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

import org.loboevolution.common.Strings;
import org.loboevolution.html.control.RUIControl;
import org.loboevolution.html.control.TextAreaControl;
import org.loboevolution.html.dom.domimpl.HTMLTextAreaElementImpl;
import org.loboevolution.html.js.Executor;
import org.loboevolution.html.js.WindowImpl;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.io.Serial;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>TextArea class.</p>
 */
public class TextArea extends BasicInput {

	private final JTextArea  jtArea = new JTextArea();
	
	private final HTMLTextAreaElementImpl modelNode;
	
	/**
	 * <p>Constructor for InputText.</p>
	 *
	 * @param modelNode a {@link org.loboevolution.html.dom.domimpl.HTMLTextAreaElementImpl} object.
	 * @param ic a {@link org.loboevolution.html.control.TextAreaControl} object.
	 */
	public TextArea(final HTMLTextAreaElementImpl modelNode, final TextAreaControl ic) {
		this.modelNode = modelNode;
		setElement(modelNode);
		setJComponent(jtArea);
		
		final Font font = jtArea.getFont();
		jtArea.setFont(font.deriveFont(modelNode.getHtmlRendererConfig().getFontSize()));
		jtArea.setDocument(new LimitedDocument());
		jtArea.setText(modelNode.getValue());
		jtArea.setSelectionColor(Color.BLUE);
		jtArea.setPreferredSize(getPreferredSize(modelNode));
		
		jtArea.setEnabled(!modelNode.isDisabled());
		jtArea.setEditable(!modelNode.isReadOnly());
		
		final MouseInputAdapter mouseHandler = new MouseInputAdapter() {
			@Override
			public void mouseEntered(final MouseEvent e) {
				if (modelNode.getOnmouseover() != null) {
					final WindowImpl win = (WindowImpl) modelNode.getDocumentNode().getDefaultView();
					Executor.executeFunction(modelNode, modelNode.getOnmouseover(), new Object[] {}, win.getContextFactory());
				}
			}
		};
		
		jtArea.addMouseListener(mouseHandler);
		
		jtArea.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(final FocusEvent event) {
				final String selectedText = jtArea.getSelectedText();
				if (Strings.isNotBlank(selectedText)) {
					final Pattern word = Pattern.compile(selectedText);
					final Matcher match = word.matcher(modelNode.getValue());
					
					while (match.find()) {
					     modelNode.setSelectionRange(match.start(), match.end()-1);
					}
				}
			}
		});

		jtArea.addFocusListener(this);
		jtArea.addKeyListener(this);
		jtArea.addCaretListener(this);
		jtArea.addMouseListener(this);
		
		final RUIControl ruiControl = ic.getRUIControl();
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
	
	
	private Dimension getPreferredSize(final HTMLTextAreaElementImpl modelNode) {
		final int pw;
		final int cols =  this.modelNode.getCols();
		if (cols == -1) {
			pw = modelNode.getClientWidth();
		} else {
			final Font f = this.jtArea.getFont();
			final FontMetrics fm = this.jtArea.getFontMetrics(f);
			final Insets insets = this.jtArea.getInsets();
			pw = insets.left + insets.right + fm.charWidth('*') * cols;
		}
		final int ph;
		final int rows = this.modelNode.getRows();
		if (rows == -1) {
			ph = modelNode.getClientHeight();
		} else {
			final Font f = this.jtArea.getFont();
			final FontMetrics fm = this.jtArea.getFontMetrics(f);
			final Insets insets = this.jtArea.getInsets();
			ph = insets.top + insets.bottom + fm.getHeight() * rows;
		}
		return new Dimension(pw, ph);

	}

	
	private final class LimitedDocument extends PlainDocument {

		@Serial
        private static final long serialVersionUID = 1L;

		@Override
		public void insertString(final int offs, final String str, final AttributeSet a) throws BadLocationException {
			final int max = modelNode.getMaxLength();

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
